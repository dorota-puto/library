package libraryManager.model;

public abstract class Book {
    private Long bookId;
    private String title;
    private String author;
    private String publisher;
    private Integer numberOfPages;
    private Language language;

    public Long getBookId() { return bookId; }

    public void setBookId(Long bookId) { this.bookId = bookId; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public String getPublisher() { return publisher; }

    public void setPublisher(String publisher) { this.publisher = publisher; }

    public Integer getNumberOfPages() { return numberOfPages; }

    public void setNumberOfPages(Integer numberOfPages) { this.numberOfPages = numberOfPages; }

    public Language getLanguage() { return language; }

    public void setLanguage(Language language) { this.language = language; }
}