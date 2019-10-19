package libraryManager.service.Book;

import libraryManager.model.Book;
import libraryManager.model.BookItem;

import java.util.*;

public class BookItemCatalog implements IManageBookItemCatalog, ISearchBookCatalog, ISearchBookItemCatalog {

    private Map<String, List<BookItem>> bookItemsByTitle = new HashMap<>();
    private Map<String, List<BookItem>> bookItemsByAuthor = new HashMap<>();
    private Map<Long, List<BookItem>> bookItemsByIsbn = new HashMap<>();
    private Map<String, BookItem> bookItemsByRfidTag = new HashMap<>();


    @Override
    public List<BookItem> findByTitle(String title) {
        return bookItemsByTitle.get(title);
    }

    @Override
    public List<BookItem> findByAuthor(String author) {
        return bookItemsByAuthor.get(author);
    }

    @Override
    public List<BookItem> findByIsbn(Long isbn) {
        return bookItemsByIsbn.get(isbn);
    }

    @Override
    public BookItem findByRfidTag(String rfid) {
        return bookItemsByRfidTag.getOrDefault(rfid, null);
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        Set<Book> temp = new HashSet<>();
        for (BookItem book : findByTitle(title)) {
            if()

        }

    }

    @Override
    public Book findBookByIsbn(Long isbn){
        return findByIsbn(isbn).get(0);
    }


    @Override
    public Boolean add(BookItem book) {
        if (!bookItemsByRfidTag.containsValue(book)) {
            bookItemsByRfidTag.put(book.getRfidTag(), book);
            bookItemsByIsbn.get(book.getBookIsbn()).add(book);
            bookItemsByAuthor.get(book.getAuthor()).add(book);
            bookItemsByTitle.get(book.getTitle()).add(book);
            return true;
        }
        return false;
    }

    @Override
    public Boolean remove(String rfidTag) {
        if (bookItemsByRfidTag.containsKey(rfidTag)) {
            bookItemsByRfidTag.remove(rfidTag);
            BookItem bookToRemove = findByRfidTag(rfidTag);
            bookItemsByIsbn.remove(bookToRemove.getBookIsbn());
            bookItemsByAuthor.remove(bookToRemove.getAuthor());
            bookItemsByTitle.remove(bookToRemove.getTitle());
            return true;
        }
        return false;
    }

}
