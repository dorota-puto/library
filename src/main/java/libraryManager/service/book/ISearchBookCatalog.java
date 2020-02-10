package libraryManager.service.book;

import libraryManager.entity.Author;
import libraryManager.model.Book;

import java.util.List;

public interface ISearchBookCatalog {
    List<Book> findBookByTitle(String title);
    List<Book> findBookByAuthor(Author author);
    Book findBookByIsbn(Long isbn);
}
