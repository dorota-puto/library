package libraryManager.service.lendingManager;

import libraryManager.model.BookItemDTO;
import libraryManager.model.LentBookInfo;
import libraryManager.service.account.ISearchAccountCatalog;
import libraryManager.service.book.ISearchBookItemCatalog;
import libraryManager.service.historyManager.HistoryManager;
import libraryManager.service.reservationManager.BookReservationManager;

import java.time.LocalDate;
import java.util.*;

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
        return Optional.ofNullable(lentBookInfoByAccountId.get(accountId))
                .map(lentBookInfos -> lentBookInfos.stream()
                        .map(LentBookInfo::getDueDate)
                        .anyMatch(date -> date.isBefore(LocalDate.now())))
                .orElse(false);
    }


    public int checkBookAvailability(Long isbn) {
        List<BookItemDTO> byIsbn = bookItemCatalog.findByIsbn(isbn);
        if (byIsbn != null) {
            return byIsbn.size();
        }
        return 0;
    }

    private BookItemDTO bookToLent(Long accountId, Long isbn) {
        List<BookItemDTO> byIsbn = bookItemCatalog.findByIsbn(isbn);

        return bookItemCatalog.findByIsbn(isbn).stream()
                .filter(book -> lentBookInfoByRfidTag.get(book.getRfidTag()) == null)
                .filter(book -> reservationManager.isReservedForThisAccount(accountId, book.getRfidTag()))
                .findAny()
                .orElse(bookItemCatalog.findByIsbn(isbn).stream()
                        .filter(book -> lentBookInfoByRfidTag.get(book.getRfidTag()) == null)
                        .filter(book -> reservationManager.isAllowed(book.getRfidTag()))
                        .findAny()
                        .orElse(null));
    }

    private Boolean isAccountActive(Long accountId) {
        return accountCatalog.findById(accountId).getActive();
    }

    private Boolean canLendMoreBooks(Long accountId) {
        return (lentBookInfoByAccountId.get(accountId) == null || lentBookInfoByAccountId.get(accountId).size() < NUMBER_OF_BOOKS_POSSIBLE_TO_LENT);
    }

    public LentBookInfo lend(Long accountId, Long isbn) {
        reservationManager.cancelReservationIfOverDue();
        BookItemDTO book = bookToLent(accountId, isbn);
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

            lentBookInfoByAccountId.get(accountId)
                    .removeIf(info -> info.getRfidTag().equals(rfidTag));
            return true;
        }
        return false;
    }
}
