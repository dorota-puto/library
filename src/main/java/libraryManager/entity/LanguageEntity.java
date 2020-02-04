package libraryManager.entity;

public class LanguageEntity {
    private Long languageID;
    private String name;

    public LanguageEntity(Long languageID, String name){
        this.languageID=languageID;
        this.name=name;
    }

    public Long getLanguageID() {
        return languageID;
    }

    public void setLanguageID(Long languageID) {
        this.languageID = languageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
