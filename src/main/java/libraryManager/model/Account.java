package libraryManager.model;

public class Account {

    private Long accountId;
    private String accountName;
    private AccountState state;

    public Account(Long accountId, String accountName, AccountState state) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.state = state;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public AccountState getState() {
        return state;
    }

    public void setState(AccountState state) {
        this.state = state;
    }
}
