package classes;

public class Agency {
    private String agency_code;
    private String correspondent_id;
    private String description;
    private boolean disabled;
    private Double distance;
    private String id;
    private String payment_method_id;
    private String phone;
    private String site_id;
    private boolean terminal;
    private Address address;

    public Agency() {
    }

    public String getAgencyCode() {
        return agency_code;
    }

    public void setAgencyCode(String agency_code) {
        this.agency_code = agency_code;
    }

    public String getCorrespondentId() {
        return correspondent_id;
    }

    public void setCorrespondentId(String correspondent_id) {
        this.correspondent_id = correspondent_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentMethodId() {
        return payment_method_id;
    }

    public void setPaymentMethodId(String payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
