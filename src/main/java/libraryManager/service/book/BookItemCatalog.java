package libraryManager.service.book;

import libraryManager.entity.Author;
import libraryManager.model.Book;
import libraryManager.model.BookItem;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BookItemCatalog implements IManageBookItemCatalog, ISearchBookCatalog, ISearchBookItemCatalog {

    private Map<String, List<BookItem>> bookItemsByTitle = new HashMap<>();
    private Map<Author, List<BookItem>> bookItemsByAuthor = new HashMap<>();
    private Map<Long, List<BookItem>> bookItemsByIsbn = new HashMap<>();
    private Map<String, BookItem> bookItemsByRfidTag = new HashMap<>();


    @Override
    public List<BookItem> findByTitle(String title) {
        return bookItemsByTitle.get(title);
    }

    @Override
    public List<BookItem> findByAuthor(Author author) {
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


    public List<BookItem> listAll() {
        return new ArrayList<>(bookItemsByRfidTag.values());
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    @Override
    public List<Book> findBookByAuthor(Author author) {

        return Optional.ofNullable(findByAuthor(author))
                .map(bookItems -> bookItems.stream()
                        .filter(distinctByKey(Book::getBookIsbn))
                        .collect(Collectors.<Book>toList()))
                .orElse(new ArrayList<>());
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        Set<Long> uniqueIsbns = new HashSet<>();
        List<Book> result = new ArrayList<>();
        List<BookItem> foundBooks = findByTitle(title);
        if (foundBooks != null) {
            for (BookItem book : foundBooks) {
                if (!uniqueIsbns.contains(book.getBookIsbn())) {
                    result.add(book);
                    uniqueIsbns.add(book.getBookIsbn());
                }
            }
        }
        return result;
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
    public Set<String> getRfidTags() {
        Set<String> rfidTags = new HashSet<>();
        rfidTags.addAll(bookItemsByRfidTag.keySet());
        return rfidTags;
    }

}
