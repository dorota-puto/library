package libraryManager.entity;

import java.util.Objects;

public class Author {
    private Long authorID;
    private String firstName;
    private String lastName;

    public Author(String firstName, String lastName){
        this.firstName=firstName;
        this.lastName=lastName;
    }

    public Author(Long authorID, String firstName, String lastName){
        this.authorID=authorID;
        this.firstName=firstName;
        this.lastName=lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return getAuthorID().equals(author.getAuthorID()) &&
                getFirstName().equals(author.getFirstName()) &&
                getLastName().equals(author.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthorID(), getFirstName(), getLastName());
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
