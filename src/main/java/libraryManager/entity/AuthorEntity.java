package libraryManager.entity;

public class AuthorEntity {
    private Long authorID;
    private String firstName;
    private String lastName;

    public AuthorEntity(Long authorID, String firstName, String lastName){
        this.authorID=authorID;
        this.firstName=firstName;
        this.lastName=lastName;
    }


    public Long getAuthorID() {
        return authorID;
    }

    public void setAuthorID(Long authorID) {
        this.authorID = authorID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
