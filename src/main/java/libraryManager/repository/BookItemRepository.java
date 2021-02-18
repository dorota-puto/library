package libraryManager.repository;

import libraryManager.entity.BookItem;

import java.util.List;
import java.util.Optional;

public interface BookItemRepository {
    boolean save(BookItem bookItem);


    Optional<BookItem> findByRfidTag(String rfigTag);

    List<BookItem> findAll();

      int deleteByRfidTag(String rfidTag);
}
