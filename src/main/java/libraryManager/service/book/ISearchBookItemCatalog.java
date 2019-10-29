package libraryManager.service.book;

import libraryManager.model.BookItem;

import java.util.List;

public interface ISearchBookItemCatalog {
    List<BookItem> findByTitle(String title);
    List<BookItem> findByAuthor(String author);
    List<BookItem> findByIsbn(Long isbn);
    BookItem findByRfidTag(String rfidTag);
}
