package in.nic.assam.udyogmitra;

public class Visitor {

    private String visitorName;
    private String organisationName;
    private String visitorNumber;
    private String telephoneNumber;
    private String district_name;
    private String purpose;
    private String remarks;

    public Visitor() {

    }


    public Visitor(String visitorName, String organisationName, String visitorNumber, String telephoneNumber, String district_name, String purpose, String remarks) {
        this.visitorName = visitorName;
        this.organisationName = organisationName;
        this.visitorNumber = visitorNumber;
        this.telephoneNumber = telephoneNumber;
        this.district_name = district_name;
        this.purpose = purpose;
        this.remarks = remarks;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public String getVisitorNumber() {
        return visitorNumber;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getDistrict_name() { return district_name; }

    public String getRemarks() {
        return remarks;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public void setVisitorNumber(String visitorNumber) {
        this.visitorNumber = visitorNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
