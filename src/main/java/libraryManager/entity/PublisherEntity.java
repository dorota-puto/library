package libraryManager.entity;

public class PublisherEntity {
    private Long publisherID;
    private String name;

    public PublisherEntity(Long publisherID, String name){
        this.publisherID=publisherID;
        this.name=name;
    }

    public Long getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(Long publisherID) {
        this.publisherID = publisherID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
