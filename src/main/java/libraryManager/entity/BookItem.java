package libraryManager.entity;

public class BookItem {
    private String rfidTag;
    private Long isbn;

    public BookItem() {}

    public BookItem(String rfidTag, Long isbn) {
        this.rfidTag = rfidTag;
        this.isbn = isbn;
    }

    public String getRfidTag() {
        return rfidTag;
    }

    public void setRfidTag(String rfidTag) {
        this.rfidTag = rfidTag;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }
}
