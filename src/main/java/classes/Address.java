package classes;

public class Address {
    private String address_line;
    private String city;
    private String country;
    private String location;
    private String other_info;
    private String state;
    private String zip_code;

    public Address() {
    }

    public String getAddressLine() {
        return address_line;
    }

    public void setAddressLine(String address_line) {
        this.address_line = address_line;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOtherInfo() {
        return other_info;
    }

    public void setOtherInfo(String other_info) {
        this.other_info = other_info;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zip_code;
    }

    public void setZipCode(String zip_code) {
        this.zip_code = zip_code;
    }
}
