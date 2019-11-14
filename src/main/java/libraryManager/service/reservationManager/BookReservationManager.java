package libraryManager.service.reservationManager;

import libraryManager.model.ReservedBookInfo;
import libraryManager.service.account.ISearchAccountCatalog;
import libraryManager.service.book.ISearchBookItemCatalog;

import java.time.LocalDate;
import java.util.*;


public class BookReservationManager {
    private ISearchAccountCatalog accountCatalog;
    private ISearchBookItemCatalog bookItemCatalog;

    Set<String> allowedRfidTags;
    Map<String, ReservedBookInfo> reservedBookInfosByRfidTag = new HashMap<>();
    Map<Long, List<ReservedBookInfo>> reservedBookInfosByAccountId = new HashMap<>();


    public BookReservationManager(ISearchAccountCatalog accountCatalog, ISearchBookItemCatalog bookItemCatalog) {
        this.accountCatalog = accountCatalog;
        this.bookItemCatalog = bookItemCatalog;
        allowedRfidTags = bookItemCatalog.getRfidTagsFromCatalog();

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
        return (reservedBookInfosByAccountId.get(accountId) == null || reservedBookInfosByAccountId.get(accountId).size() < 4);
    }

    public ReservedBookInfo reserve(Long accountId, String rfidTag) {

        if (allowedRfidTags.contains(rfidTag) && canReserveMoreBooks(accountId)) {
            allowedRfidTags.remove(rfidTag);

            LocalDate today = LocalDate.now();
            LocalDate dueDate = today.plusDays(30);
            ReservedBookInfo info = new ReservedBookInfo(rfidTag, accountId, today, dueDate);
            reservedBookInfosByRfidTag.put(rfidTag, info);

            List<ReservedBookInfo> list = reservedBookInfosByAccountId.get(accountId);
            if (list == null) {
                list = new ArrayList<>();
                reservedBookInfosByAccountId.put(accountId, list);
            }
            list.add(info);
            return info;
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
        if (allowedRfidTags.contains(rfidTag)) {
            return true;
        }
        return false;
    }

    public void cancelReservationIfOverDue() {


        for (Long id : reservedBookInfosByAccountId.keySet()) {
            for (ReservedBookInfo info : reservedBookInfosByAccountId.get(id)) {
                List<ReservedBookInfo> toBeRemoved = new ArrayList<>();
                if (info.getDueDate().isBefore(LocalDate.now())) {
                    toBeRemoved.add(info);
                }
                reservedBookInfosByAccountId.values().removeAll(toBeRemoved);
            }
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



