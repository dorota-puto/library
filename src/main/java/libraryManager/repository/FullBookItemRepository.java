package libraryManager.repository;

import libraryManager.entity.full.FullBookItem;

import java.util.List;
import java.util.Set;

public interface FullBookItemRepository {
    List<FullBookItem> findByTitle(String title);

    List<FullBookItem> findByAuthor(String name, String surname);

    List<FullBookItem> findByIsbn(Long isbn);

    FullBookItem findByRfidTag(String rfidTag);

    List<FullBookItem> listAll();

    Set<String> getRfidTAgs();
}
