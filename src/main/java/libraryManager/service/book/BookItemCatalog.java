package libraryManager.service.book;

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
    public List<Book> findBookByAuthor(String author) {
        List<Book> temp = new ArrayList<>();
        List<BookItem> foundBooks = findByAuthor(author);
        if (foundBooks != null) {
            for (BookItem book1 : foundBooks) {
                if (temp.size() == 0) {
                    temp.add(book1);
                }
                for (Book book2 : temp) {
                    if (book1.getBookIsbn() != book2.getBookIsbn()) {
                        temp.add(book1);
                    }
                }
            }
            return temp;
        } else return null;
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        List<Book> temp = new ArrayList<>();
        List<BookItem> foundBooks = findByTitle(title);
        if (foundBooks != null) {
            for (BookItem book1 : foundBooks) {
                if (temp.size() == 0) {
                    temp.add(book1);
                }
                for (Book book2 : temp) {
                    if (book1.getBookIsbn() != book2.getBookIsbn()) {
                        temp.add(book1);
                    }
                }
            }
            return temp;
        } else return null;

    }

    @Override
    public Book findBookByIsbn(Long isbn) {
        if (findByIsbn(isbn) != null) {
            return findByIsbn(isbn).get(0);
        } else return null;
    }

    private void addToBookItemsByIsbn(BookItem book) {
        if (bookItemsByIsbn.containsKey(book.getBookIsbn())) {
            bookItemsByIsbn.get(book.getBookIsbn()).add(book);
        } else {
            List<BookItem> bookItems = new ArrayList<>();
            bookItems.add(book);
            bookItemsByIsbn.put(book.getBookIsbn(), bookItems);
        }
    }

    private void addToBookItemsByAuthor(BookItem book) {
        if (bookItemsByAuthor.containsKey(book.getAuthor())) {
            bookItemsByAuthor.get(book.getAuthor()).add(book);
        } else {
            List<BookItem> bookItems = new ArrayList<>();
            bookItems.add(book);
            bookItemsByAuthor.put(book.getAuthor(), bookItems);
        }
    }

    private void addToBookItemsByTitle(BookItem book) {
        if (bookItemsByTitle.containsKey(book.getTitle())) {
            bookItemsByTitle.get(book.getTitle()).add(book);
        } else {
            List<BookItem> bookItems = new ArrayList<>();
            bookItems.add(book);
            bookItemsByTitle.put(book.getTitle(), bookItems);
        }
    }

    @Override
    public Boolean add(BookItem book) {

        if (!bookItemsByRfidTag.containsValue(book)) {
            bookItemsByRfidTag.put(book.getRfidTag(), book);

            addToBookItemsByAuthor(book);
            addToBookItemsByIsbn(book);
            addToBookItemsByTitle(book);

            return true;
        }
        return false;
    }

    @Override
    public Boolean remove(String rfidTag) {
        if (bookItemsByRfidTag.containsKey(rfidTag)) {
            BookItem bookToRemove = findByRfidTag(rfidTag);
            bookItemsByRfidTag.remove(rfidTag);
            bookItemsByIsbn.get(bookToRemove.getBookIsbn()).remove(bookToRemove);
            bookItemsByAuthor.get(bookToRemove.getAuthor()).remove(bookToRemove);
            bookItemsByTitle.get(bookToRemove.getTitle()).remove(bookToRemove);
            return true;
        }
        return false;
    }

    @Override
    public Set<String> getRfidTagsFromCatalog() {
        Set<String> setOfRfidTags = new HashSet<>();
        setOfRfidTags.addAll(bookItemsByRfidTag.keySet());
        return setOfRfidTags;
    }

}
