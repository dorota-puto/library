package libraryManager.service.reservationManager;

import libraryManager.model.BookItem;
import libraryManager.model.ReservedBookInfo;
import libraryManager.service.account.ISearchAccountCatalog;
import libraryManager.service.book.ISearchBookItemCatalog;

import java.time.LocalDate;
import java.util.*;


public class BookReservationManager {
    private ISearchAccountCatalog accountCatalog;
    private ISearchBookItemCatalog bookItemCatalog;
    private static final int NUMBER_OF_BOOKS_POSSIBLE_TO_RESERVE = 4;
    private static final int NUMBER_OF_DAYS_WHEN_RESERVATION_IS_KEPT = 30;

    Set<String> allowedRfidTags;
    Map<String, ReservedBookInfo> reservedBookInfosByRfidTag = new HashMap<>();
    Map<Long, List<ReservedBookInfo>> reservedBookInfosByAccountId = new HashMap<>();


    public BookReservationManager(ISearchAccountCatalog accountCatalog, ISearchBookItemCatalog bookItemCatalog) {
        this.accountCatalog = accountCatalog;
        this.bookItemCatalog = bookItemCatalog;
        allowedRfidTags = bookItemCatalog.getRfidTags();

    }


    public void addToReservationCatalog(String rfidTag) {
        allowedRfidTags.add(rfidTag);
    }

    public Boolean removeFromReservationCatalog(String rfidTag) {
        if (bookItemCatalog.findByRfidTag(rfidTag) != null) {
            return allowedRfidTags.remove(rfidTag);
        }
        return false;
    }


    private Boolean canReserveMoreBooks(Long accountId) {
        return (reservedBookInfosByAccountId.get(accountId) == null || reservedBookInfosByAccountId.get(accountId).size() < NUMBER_OF_BOOKS_POSSIBLE_TO_RESERVE);
    }

    public ReservedBookInfo reserve(Long accountId, Long isbn) {
        for (BookItem book : bookItemCatalog.findByIsbn(isbn)) {
            if (allowedRfidTags.contains(book.getRfidTag()) && canReserveMoreBooks(accountId)) {
                allowedRfidTags.remove(book.getRfidTag());

                LocalDate today = LocalDate.now();
                LocalDate dueDate = today.plusDays(NUMBER_OF_DAYS_WHEN_RESERVATION_IS_KEPT);
                ReservedBookInfo info = new ReservedBookInfo(book.getRfidTag(), accountId, today, dueDate);
                reservedBookInfosByRfidTag.put(book.getRfidTag(), info);

                List<ReservedBookInfo> list = reservedBookInfosByAccountId.get(accountId);
                if (list == null) {
                    list = new ArrayList<>();
                    reservedBookInfosByAccountId.put(accountId, list);
                }
                list.add(info);
                return info;
            }
        }
        return null;
    }

    public Boolean isReservedForThisAccount(Long accountId, String rfidTag) {
        if (reservedBookInfosByAccountId.get(accountId) != null) {
            for (ReservedBookInfo info : reservedBookInfosByAccountId.get(accountId)) {
                if (info.getRfidTag().equals(rfidTag)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean isAllowed(String rfidTag) {
        return allowedRfidTags.contains(rfidTag);
    }

    public void cancelReservationIfOverDue() {


        for (Long id : reservedBookInfosByAccountId.keySet()) {
            List<ReservedBookInfo> toBeRemoved = new ArrayList<>();

            for (ReservedBookInfo info : reservedBookInfosByAccountId.get(id)) {
                if (info.getDueDate().isBefore(LocalDate.now())) {
                    toBeRemoved.add(info);
                }
            }
            reservedBookInfosByAccountId.get(id).removeAll(toBeRemoved);
        }
        Set<String> toBeRemoved = new HashSet<>();
        for (Map.Entry<String, ReservedBookInfo> e : reservedBookInfosByRfidTag.entrySet()) {
            if (e.getValue().getDueDate().isBefore(LocalDate.now())) {
                toBeRemoved.add(e.getKey());
            }
        }
        reservedBookInfosByRfidTag.keySet().removeAll(toBeRemoved);

        allowedRfidTags.addAll(toBeRemoved);
    }
}



