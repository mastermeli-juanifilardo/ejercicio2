package classes;

import classes.Exceptions.CriterionDoesNotExistException;
import classes.Exceptions.MissingParamsException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static spark.Spark.get;

public class Main {

    public static void main(String[] args) {

        final String api_url = "https://api.mercadolibre.com";

        Logger logger = initLogger("IT Academy", "api.log");


        get("/agencias/", (request, response) -> {
            response.type("application/json");
            logger.info("Requested resource " + request.url() + request.raw().getQueryString());

            try {

                String site = request.queryParams("site");
                String payment_method = request.queryParams("payment_method");
                String agency_id = request.queryParams("agency_id");

                if (site == null || payment_method == null) {
                    throw new MissingParamsException("Faltan parámetros obligatorios.");
                }

                String resource = "/sites/" + site + "/payment_methods/" + payment_method + "/agencies/";

                if (agency_id == null) {
                    String limit = request.queryParams("limit");
                    String offset = request.queryParams("offset");

                    boolean isFirst = true; // Para ver cuál entró primero: a ese le corresponde "?" y no un "&"

                    if (limit != null) {
                        resource += ( isFirst ? "?" : "&" ) + "limit=" + limit;
                        isFirst = false;
                    }
                    if (offset != null) {
                        resource += ( isFirst ? "?" : "&" ) + "offset=" + offset;
                        isFirst = false;
                    }

                } else {
                    resource += agency_id;
                }

                String full_url = api_url + resource;

                String json_string = readUrl(full_url);
                Agency[] agencies = readAgencies(json_string);


                if (agency_id == null) {
                    // Si hay más de una agencia, ordenar
                    String sort_by = request.queryParams("sort_by");

                    if (sort_by != null) {
                        Agency.setCriterion(sort_by);
                        String order = request.queryParams("order");

                        // Por defecto ordenar ascendentemente
                        if (order == null || order != "desc") {
                            Ordenador.sortArray(agencies);
                        } else {
                            Ordenador.sortArray(agencies);
                            Collections.reverse(Arrays.asList(agencies));
                        }
                    }
                }


                return new Gson().toJson(new StandardResponse(
                        ResponseStatus.SUCCESS,
                        new Gson().toJsonTree(agencies)
                ));

            } catch (MissingParamsException e) {
                e.printStackTrace();
                String msg = e.getMessage();
                System.out.println(msg);
                logger.severe(msg);

                return new Gson().toJson(new StandardResponse(
                        ResponseStatus.ERROR,
                        new Gson().toJson(msg)
                ));
            }
            catch (IOException e) {
                e.printStackTrace();
                String msg = "Ocurrió un error al traer las agencias.";
                System.out.println(msg);
                logger.severe(msg);

                return new Gson().toJson(new StandardResponse(
                        ResponseStatus.ERROR,
                        new Gson().toJson(msg)
                ));
            } catch (CriterionDoesNotExistException e) {
                e.printStackTrace();
                String msg = e.getMessage();
                System.out.println(msg);
                logger.severe(msg);

                return new Gson().toJson(new StandardResponse(
                        ResponseStatus.ERROR,
                        new Gson().toJson(msg)
                ));
            }
        });
    }

    private static Logger initLogger(String name, String path) {
        Logger logger = Logger.getLogger(name);
        FileHandler fh;

        try {

            fh = new FileHandler(path);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.info("Starting logs at " + new Date());
            logger.info("___  ___     _     _    ___  ______ \n" +
                    "|  \\/  |    | |   (_)  / _ \\ | ___ \\_   _|\n" +
                    "| .  . | ___| |    _  / /_\\ \\| |_/ / | |\n" +
                    "| |\\/| |/ _ \\ |   | | |  _  ||  __/  | |\n" +
                    "| |  | |  __/ |___| | | | | || |    _| |_\n" +
                    "\\_|  |_/\\___\\_____/_| \\_| |_/\\_|    \\___/\n" +
                    "\n" +
                    "                                          ");


        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logger;
    }

    private static Agency[] readAgencies(String json_string) {

        Gson gson = new Gson();
        JsonElement jelem = gson.fromJson(json_string, JsonElement.class);
        JsonObject jobj = jelem.getAsJsonObject();
        String data = jobj.get("results").toString();
        Agency[] agencies = new Gson().fromJson(data, Agency[].class);

        return agencies;
    }

    private static String readUrl(String urlString) throws IOException {

        BufferedReader reader = null; //new BufferedReader();

        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "Chrome/73.0.3683.103");

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            int read = 0;

            StringBuffer buffer = new StringBuffer();
            char[] chars = new char[1024];

            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }

            return buffer.toString();

        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
