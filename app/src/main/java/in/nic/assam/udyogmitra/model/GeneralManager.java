package in.nic.assam.udyogmitra.model;

public class GeneralManager {

    private String name;
    private String Id;
    private String designation;
    private String phoneNumber;

    public GeneralManager(String name, String designation, String phoneNumber) {
        this.name = name;
        this.designation = designation;
        this.phoneNumber = phoneNumber;
    }

    public GeneralManager() {

    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
