package libraryManager.entity;

public class BookReadEntity {
    private Long isbn;
    private String title;
    private Integer pageCount;
    private String authorFirstName;
    private String authorLastName;
    private String publisherName;
    private String language;


    public BookReadEntity(Long isbn, String title, Integer pageCount, String authorFirstName, String authorLastName, String publisherName, String language) {
        this.isbn = isbn;
        this.title = title;
        this.pageCount = pageCount;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.publisherName = publisherName;
        this.language = language;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


}
