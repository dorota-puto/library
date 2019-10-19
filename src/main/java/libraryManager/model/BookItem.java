package libraryManager.model;

public class BookItem extends Book {
    String rfidTag;

    public String getRfidTag(){ return rfidTag; }

    public void setRfidTag(String rfidTag){ this.rfidTag=rfidTag; }
}
