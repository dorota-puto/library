package libraryManager.service.lendingManager;

import libraryManager.model.AccountState;
import libraryManager.model.BookItem;
import libraryManager.model.LentBookInfo;
import libraryManager.service.account.ISearchAccountCatalog;
import libraryManager.service.book.ISearchBookItemCatalog;
import libraryManager.service.historyManager.HistoryManager;
import libraryManager.service.reservationManager.BookReservationManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookLendingManager {

    private ISearchAccountCatalog accountCatalog;
    private ISearchBookItemCatalog bookItemCatalog;
    private HistoryManager historyManager;
    private BookReservationManager reservationManager;
    private static final int NUMBER_OF_BOOKS_POSSIBLE_TO_LENT = 4;
    private static final int NUMBER_OF_DAYS_WHEN_BOOK_CAN_BE_KEPT = 30;

    Map<Long, List<LentBookInfo>> lentBookInfoByAccountId = new HashMap<>();
    Map<String, LentBookInfo> lentBookInfoByRfidTag = new HashMap<>();

    public BookLendingManager(ISearchAccountCatalog accountCatalog, ISearchBookItemCatalog bookItemCatalog, HistoryManager historyManager, BookReservationManager reservationManager) {
        this.accountCatalog = accountCatalog;
        this.bookItemCatalog = bookItemCatalog;
        this.historyManager = historyManager;
        this.reservationManager = reservationManager;
    }

    private Boolean hasOverDueBook(Long accountId) {
        if (lentBookInfoByAccountId.get(accountId) != null) {
            for (LentBookInfo info : lentBookInfoByAccountId.get(accountId)) {
                if (info.getDueDate().isBefore(LocalDate.now())) {
                    return true;
                }
            }
        }
        return false;
    }

    public int checkBookAvailability(Long isbn) {
        List<BookItem> byIsbn = bookItemCatalog.findByIsbn(isbn);
        if (byIsbn != null) {
            return byIsbn.size();
        }
        return 0;
    }

    private BookItem bookToLent(Long accountId, Long isbn) {

        for (BookItem book : bookItemCatalog.findByIsbn(isbn)) {
            if (lentBookInfoByRfidTag.get(book.getRfidTag()) == null &&
                    reservationManager.isReservedForThisAccount(accountId, book.getRfidTag())) {
                return book;
            }
        }
        for (BookItem book : bookItemCatalog.findByIsbn(isbn)) {
            if (lentBookInfoByRfidTag.get(book.getRfidTag()) == null &&
                    reservationManager.isAllowed(book.getRfidTag())) {
                return book;
            }
        }
        return null;
    }

    private Boolean isAccountActive(Long accountId) {
        return accountCatalog.findById(accountId).getState().equals(AccountState.ACTIVE);
    }

    private Boolean canLendMoreBooks(Long accountId) {
        return (lentBookInfoByAccountId.get(accountId) == null || lentBookInfoByAccountId.get(accountId).size() < NUMBER_OF_BOOKS_POSSIBLE_TO_LENT);
    }

    public LentBookInfo lend(Long accountId, Long isbn) {
        reservationManager.cancelReservationIfOverDue();
        BookItem book = bookToLent(accountId, isbn);
        if (isAccountActive(accountId) &&
                !hasOverDueBook(accountId) &&
                checkBookAvailability(isbn) > 0 &&
                canLendMoreBooks(accountId) &&
                book != null) {

            LocalDate today = LocalDate.now();
            LocalDate returnDay = today.plusDays(NUMBER_OF_DAYS_WHEN_BOOK_CAN_BE_KEPT);
            LentBookInfo lentBookInfo = new LentBookInfo(book.getRfidTag(), accountId, today, returnDay);

            lentBookInfoByRfidTag.put(book.getRfidTag(), lentBookInfo);

            List<LentBookInfo> list = new ArrayList<>();
            if (lentBookInfoByAccountId.get(accountId) != null) {
                list = lentBookInfoByAccountId.get(accountId);
            }
            list.add(lentBookInfo);
            lentBookInfoByAccountId.put(accountId, list);
            reservationManager.removeFromReservationCatalog(book.getRfidTag());
            return lentBookInfo;
        }
        throw new IllegalArgumentException();
    }

    public LentBookInfo changeInfoWhenReturning(LentBookInfo info) {
        info.setDueDate(LocalDate.now());
        return info;
    }

    public Boolean returnBook(Long accountId, String rfidTag) {
        if (bookItemCatalog.findByRfidTag(rfidTag) != null &&
                lentBookInfoByRfidTag.get(rfidTag) != null) {

            LentBookInfo newInfo = lentBookInfoByRfidTag.remove(rfidTag);
            historyManager.add(accountId, changeInfoWhenReturning(newInfo));
            reservationManager.addToReservationCatalog(rfidTag);

            List<LentBookInfo> list = lentBookInfoByAccountId.get(accountId);
            for (LentBookInfo info : list) {
                if (info.getRfidTag().equals(rfidTag)) {
                    list.remove(info);
                    return true;
                }
            }
        }
        return false;
    }
}
