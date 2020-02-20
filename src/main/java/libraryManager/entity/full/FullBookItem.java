package libraryManager.entity.full;

import libraryManager.entity.Author;
import libraryManager.entity.Language;

public class FullBookItem extends FullBook {
    private String rfidTag;

    // todo: use builder pattern
    public FullBookItem(Long bookIsbn, String title, Author author, String publisher, Integer numberOfPages, Language language, String rfidTag) {
        super(bookIsbn, title, author, publisher, numberOfPages, language);
        this.rfidTag = rfidTag;
    }



    public String getRfidTag() {
        return rfidTag;
    }

    public void setRfidTag(String rfidTag) {
        this.rfidTag = rfidTag;
    }
}
