package libraryManager.entity;

public class Account {

    private Long accountID;
    private String name;
    private Boolean active;

    public Account(){};
    public Account(String name, boolean active) {
        this.name=name;
        this.active=active;
    }

    public Account(Long accountID, String name, boolean active) {
        this.accountID=accountID;
        this.name=name;
        this.active=active;
    }

    public Long getAccountID() {
        return accountID;
    }

    public void setAccountID(Long accountID) {
        this.accountID = accountID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
