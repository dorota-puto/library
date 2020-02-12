package libraryManager.service.book;

import libraryManager.entity.Author;
import libraryManager.model.BookDTO;

import java.util.List;

public interface ISearchBookCatalog {
    List<BookDTO> findBookByTitle(String title);
    List<BookDTO> findBookByAuthor(Author author);
    BookDTO findBookByIsbn(Long isbn);
}
