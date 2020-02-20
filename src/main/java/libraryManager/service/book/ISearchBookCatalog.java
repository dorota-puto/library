package libraryManager.service.book;

import libraryManager.entity.Author;
import libraryManager.entity.full.FullBook;

import java.util.List;

public interface ISearchBookCatalog {
    List<FullBook> findBookByTitle(String title);
    List<FullBook> findBookByAuthor(String name, String surname);
    FullBook findBookByIsbn(Long isbn);
}
