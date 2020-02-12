package libraryManager.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Book {
    private Long isbn;
    private String title;
    private Integer pageCount;
    private Long authorID;
    private Long publisherID;
    private Long languageID;

    public Book(){};

    public Book(Long isbn, String title, Integer pageCount, Long authorID, Long publisherID, Long languageID) {
        this.isbn = isbn;
        this.title = title;
        this.pageCount = pageCount;
        this.authorID = authorID;
        this.publisherID = publisherID;
        this.languageID = languageID;
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

    public Long getAuthorID() {
        return authorID;
    }

    public void setAuthorID(Long authorID) {
        this.authorID = authorID;
    }

    public Long getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(Long publisherID) {
        this.publisherID = publisherID;
    }

    public Long getLanguageID() {
        return languageID;
    }

    public void setLanguageID(Long languageID) {
        this.languageID = languageID;
    }
}
