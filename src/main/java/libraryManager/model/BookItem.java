package libraryManager.model;

public class BookItem extends Book {
    private String rfidTag;

    public BookItem(Long bookIsbn, String title, String author, String publisher, Integer numberOfPages, Language language, String rfidTag) {
        super(bookIsbn, title, author, publisher, numberOfPages, language);
        this.rfidTag=rfidTag;
    }

    public String getRfidTag() {
        return rfidTag;
    }

    public void setRfidTag(String rfidTag) {
        this.rfidTag = rfidTag;
    }
}
