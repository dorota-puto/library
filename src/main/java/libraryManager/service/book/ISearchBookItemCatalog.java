package libraryManager.service.book;

import libraryManager.entity.Author;
import libraryManager.entity.full.FullBookItem;

import java.util.List;
import java.util.Set;

public interface ISearchBookItemCatalog {
    List<FullBookItem> findByTitle(String title);
    List<FullBookItem> findByAuthor(String name, String surname);
    List<FullBookItem> findByIsbn(Long isbn);
    FullBookItem findByRfidTag(String rfidTag);
    Set<String> getRfidTags();
}
