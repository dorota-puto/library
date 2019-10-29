package libraryManager.service.book;

import libraryManager.model.Book;

import java.util.List;

public interface ISearchBookCatalog {
    List<Book> findBookByTitle(String title);
    List<Book> findBookByAuthor(String author);
    Book findBookByIsbn(Long isbn);
}
