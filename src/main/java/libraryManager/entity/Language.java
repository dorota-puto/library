package libraryManager.entity;

public class Language {
    private Long languageID;
    private String name;

    public Language(){};

    public Language(String name){
        this.name=name;
    }

    public Language(Long languageID, String name){
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
