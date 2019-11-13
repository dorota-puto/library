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

    private Set<String> allowedBookItemsByRfidTag;
    private Map<String, ReservedBookInfo> reservedBookItemsByRfidTag = new HashMap<>();
    private Map<Long, List<ReservedBookInfo>> reservedBookItemsByAccountId = new HashMap<>();


    public BookReservationManager(ISearchAccountCatalog accountCatalog, ISearchBookItemCatalog bookItemCatalog) {
        this.accountCatalog = accountCatalog;
        this.bookItemCatalog = bookItemCatalog;
        allowedBookItemsByRfidTag = bookItemCatalog.getRfidTagsFromCatalog();

    }


    public void addToReservationCatalog(String rfidTag) {
        allowedBookItemsByRfidTag.add(rfidTag);
    }

    public Boolean removeFromReservationCatalog(String rfidTag) {
        if (bookItemCatalog.findByRfidTag(rfidTag) != null) {
            return allowedBookItemsByRfidTag.remove(rfidTag);
        }
        return false;
    }


    private Boolean canReserveMoreBooks(Long accountId) {
        return (reservedBookItemsByAccountId.get(accountId) == null || reservedBookItemsByAccountId.get(accountId).size() < 4);
    }

    public ReservedBookInfo reserve(Long accountId, String rfidTag) {

        if (allowedBookItemsByRfidTag.contains(rfidTag) && canReserveMoreBooks(accountId)) {
            allowedBookItemsByRfidTag.remove(rfidTag);

            LocalDate today = LocalDate.now();
            LocalDate dueDate = today.plusDays(30);
            ReservedBookInfo info = new ReservedBookInfo(rfidTag, accountId, today, dueDate);
            reservedBookItemsByRfidTag.put(rfidTag, info);

            List<ReservedBookInfo> list = reservedBookItemsByAccountId.get(accountId);
            if (list == null) {
                list = new ArrayList<>();
                reservedBookItemsByAccountId.put(accountId, list);
            }
            list.add(info);
            return info;
        }
        return null;
    }

    public Boolean isReservedForThisAccount(Long accountId, String rfidTag) {

        for (ReservedBookInfo info : reservedBookItemsByAccountId.get(accountId)) {
            if (info.getRfidTag().equals(rfidTag)) {
                return true;
            }
        }
        return false;
    }

    public Boolean isAllowed(String rfidTag) {

        if (allowedBookItemsByRfidTag.contains(rfidTag)) {
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
        Set<String> toBeRemoved = new HashSet<>();
        for (Map.Entry<String, ReservedBookInfo> e : reservedBookItemsByRfidTag.entrySet()) {
            if (e.getValue().getDueDate().isAfter(LocalDate.now())) {
                toBeRemoved.add(e.getKey());
            }
        }
        reservedBookItemsByRfidTag.keySet().removeAll(toBeRemoved);

        allowedBookItemsByRfidTag.addAll(toBeRemoved);
    }
}



