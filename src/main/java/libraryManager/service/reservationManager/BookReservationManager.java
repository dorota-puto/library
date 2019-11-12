package libraryManager.service.reservationManager;

import libraryManager.model.BookItem;
import libraryManager.model.ReservedBookInfo;
import libraryManager.service.account.ISearchAccountCatalog;
import libraryManager.service.book.ISearchBookItemCatalog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class BookReservationManager {
    private ISearchAccountCatalog accountCatalog;
    private ISearchBookItemCatalog bookItemCatalog;

    private Map<Long, List<BookItem>> allowedBookItemsByIsbn = new HashMap<>();
    private Map<String, ReservedBookInfo> reservedBookItemsByRfidTag = new HashMap<>();
    private Map<Long, List<ReservedBookInfo>> reservedBookItemsByAccountId = new HashMap<>();


    public void addToReservationCatalog(BookItem book) {
        List<BookItem> list = allowedBookItemsByIsbn.get(book.getBookIsbn());
        if (list == null) {
            list = new ArrayList<>();
            allowedBookItemsByIsbn.put(book.getBookIsbn(), list);
        }
        list.add(book);
    }

    public Boolean removeFromReservationCatalog(BookItem book) {
        if (bookItemCatalog.findByIsbn(book.getBookIsbn()) != null) {
            List<BookItem> list = allowedBookItemsByIsbn.get(book.getBookIsbn());
            for (BookItem bookItem : list) {
                if (bookItem.getRfidTag().equals(book.getRfidTag())) {
                    list.remove(book);
                    return true;
                }
            }
        }
        return false;
    }


    private Boolean canReserveMoreBooks(Long accountId) {
        return (reservedBookItemsByAccountId.get(accountId) == null || reservedBookItemsByAccountId.get(accountId).size() < 4);
    }

    public ReservedBookInfo reserve(Long accountId, Long isbn) {
        List<BookItem> list = allowedBookItemsByIsbn.get(isbn);
        if (list.size() != 0 && canReserveMoreBooks(accountId)) {
            BookItem bookToReservation = list.get(0);
            list.remove(bookToReservation);

            LocalDate today = LocalDate.now();
            LocalDate dueDate = today.plusDays(30);
            ReservedBookInfo info = new ReservedBookInfo(bookToReservation.getRfidTag(), accountId, today, dueDate);
            reservedBookItemsByRfidTag.put(bookToReservation.getRfidTag(), info);

            List<ReservedBookInfo> list2 = reservedBookItemsByAccountId.get(accountId);
            if (list2 == null) {
                list2 = new ArrayList<>();
                reservedBookItemsByAccountId.put(accountId, list2);
            }
            list2.add(info);
            return info;
        }
        return null;
    }

    public Boolean isAllowedOrReservedForThisAccount(Long accountId, BookItem book) {

        for (ReservedBookInfo info : reservedBookItemsByAccountId.get(accountId)) {
            if (info.getRfidTag().equals(book.getRfidTag())) {
                return true;
            }
        }
        if (allowedBookItemsByIsbn.get(book.getBookIsbn()).contains(book)) {
            return true;
        }
        return false;
    }


    public void cancelReservationIfOverDue() {
        for (Long id : reservedBookItemsByAccountId.keySet()) {
            for (List<ReservedBookInfo> list : reservedBookItemsByAccountId.values()) {

                List<ReservedBookInfo> toBeRemoved = new ArrayList<>();
                for (ReservedBookInfo info : list) {
                    if (info.getDueDate().isAfter(LocalDate.now())) {

                        toBeRemoved.add(info);
                    }
                }
                list.removeAll(toBeRemoved);
            }
        }

        for (String rfidTag : reservedBookItemsByRfidTag.keySet()) {
            for (ReservedBookInfo info : reservedBookItemsByRfidTag.values()) {
                if (info.getDueDate().isAfter(LocalDate.now())) {
                    addToReservationCatalog(bookItemCatalog.findByRfidTag(rfidTag));
                    reservedBookItemsByRfidTag.remove(rfidTag);
                }
            }
        }
    }
}
