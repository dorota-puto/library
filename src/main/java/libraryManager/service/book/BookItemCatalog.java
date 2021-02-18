package libraryManager.service.book;

import libraryManager.entity.full.FullBook;
import libraryManager.entity.full.FullBookItem;
import libraryManager.repository.BookRepository;
import libraryManager.repository.FullBookItemRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BookItemCatalog implements ISearchBookCatalog, ISearchBookItemCatalog {

//    private Map<String, List<BookItemDTO>> bookItemsByTitle = new HashMap<>();
//    private Map<Author, List<BookItemDTO>> bookItemsByAuthor = new HashMap<>();
//    private Map<Long, List<BookItemDTO>> bookItemsByIsbn = new HashMap<>();
//    private Map<String, BookItemDTO> bookItemsByRfidTag = new HashMap<>();

    private FullBookItemRepository fullBookItemRepository;
    private BookRepository bookRepository;

    public BookItemCatalog(FullBookItemRepository fullBookItemRepository, BookRepository bookRepository) {
        this.fullBookItemRepository = fullBookItemRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<FullBookItem> findByTitle(String title) {
        return fullBookItemRepository.findByTitle(title);
    }

    @Override
    public List<FullBookItem> findByAuthor(String name, String surname) {
        return fullBookItemRepository.findByAuthor(name, surname);
    }

    @Override
    public List<FullBookItem> findByIsbn(Long isbn) {
        return fullBookItemRepository.findByIsbn(isbn);
    }

    @Override
    public FullBookItem findByRfidTag(String rfid) {
        return fullBookItemRepository.findByRfidTag(rfid).orElse(null);
    }


    public List<FullBookItem> listAll() {
        return fullBookItemRepository.listAll();
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    @Override

    public List<FullBook> findBookByAuthor(String name, String surname) {

        return Optional.ofNullable(findByAuthor(name, surname))
                .map(bookItems -> bookItems.stream()
                        .filter(distinctByKey(FullBook::getBookIsbn))
                        .collect(Collectors.<FullBook>toList()))
                .orElse(new ArrayList<>());
    }

    @Override
    public List<FullBook> findBookByTitle(String title) {
        Set<Long> uniqueIsbns = new HashSet<>();
        List<FullBook> result = new ArrayList<>();
        List<FullBookItem> foundBooks = findByTitle(title);
        if (foundBooks != null) {
            for (FullBookItem book : foundBooks) {
                if (!uniqueIsbns.contains(book.getBookIsbn())) {
                    result.add(book);
                    uniqueIsbns.add(book.getBookIsbn());
                }
            }
        }
        return result;
    }

    @Override
    public FullBook findBookByIsbn(Long isbn) {
        if (findByIsbn(isbn) != null) {
            return findByIsbn(isbn).get(0);
        } else return null;
    }


    @Override
    public Set<String> getRfidTags() {
      return fullBookItemRepository.getRfidTAgs();
    }

}
