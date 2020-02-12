package libraryManager.service.book;

import libraryManager.entity.Author;
import libraryManager.model.BookItemDTO;

import java.util.List;
import java.util.Set;

public interface ISearchBookItemCatalog {
    List<BookItemDTO> findByTitle(String title);
    List<BookItemDTO> findByAuthor(Author author);
    List<BookItemDTO> findByIsbn(Long isbn);
    BookItemDTO findByRfidTag(String rfidTag);
    Set<String> getRfidTags();
}
