package libraryManager.entity.full;

import libraryManager.entity.Author;
import libraryManager.entity.Language;

public abstract class FullBook {
    private Long bookIsbn;
    private String title;
    private Author author;
    private String publisher;
    private Integer numberOfPages;
    private Language language;

    public FullBook(Long bookIsbn, String title, Author author, String publisher, Integer numberOfPages, Language language) {
        this.bookIsbn = bookIsbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.numberOfPages = numberOfPages;
        this.language = language;
    }

    public Long getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(Long bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}