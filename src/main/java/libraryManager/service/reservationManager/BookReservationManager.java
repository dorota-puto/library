package libraryManager.service.reservationManager;

import libraryManager.model.BookItem;
import libraryManager.model.ReservedBookInfo;
import libraryManager.service.account.ISearchAccountCatalog;
import libraryManager.service.book.ISearchBookItemCatalog;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;


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

                List<ReservedBookInfo> list = reservedBookInfosByAccountId.computeIfAbsent(accountId, k -> new ArrayList<>());
                list.add(info);
                return info;
            }
        }
        return null;
    }

    public Boolean isReservedForThisAccount(Long accountId, String rfidTag) {
        return Optional.ofNullable(reservedBookInfosByAccountId.get(accountId))
                .map(reservedBookInfos -> reservedBookInfos.stream()
                        .anyMatch(info -> info.getRfidTag().equals(rfidTag)))
                .orElse(false);
    }

    public Boolean isAllowed(String rfidTag) {
        return allowedRfidTags.contains(rfidTag);
    }

    public void cancelReservationIfOverDue() {

        reservedBookInfosByAccountId = reservedBookInfosByAccountId.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue()
                                .stream()
                                .filter(info -> !info.getDueDate().isBefore(LocalDate.now()))
                                .collect(Collectors.toList())));



        Set<String> toBeRemoved = reservedBookInfosByRfidTag.entrySet()
                .stream()
                .filter(e -> e.getValue().getDueDate().isBefore(LocalDate.now()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        reservedBookInfosByRfidTag.keySet().removeAll(toBeRemoved);
        allowedRfidTags.addAll(toBeRemoved);
    }
}



