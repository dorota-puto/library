package libraryManager.service.LendingManager;

import libraryManager.model.AccountState;
import libraryManager.model.BookItem;
import libraryManager.model.LentBookInfo;
import libraryManager.service.Account.AccountCatalog;
import libraryManager.service.Account.ISearchAccountCatalog;
import libraryManager.service.Book.BookItemCatalog;
import libraryManager.service.Book.ISearchBookItemCatalog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookLendingManager {

    private ISearchAccountCatalog accountCatalog;
    private ISearchBookItemCatalog bookItemCatalog;

    private Map<Long, List<LentBookInfo>> lentBookInfoByAccountId = new HashMap<>();
    private Map<String, LentBookInfo> lentBookInfoByRfidTag = new HashMap<>();

    public BookLendingManager(ISearchAccountCatalog accountCatalog, ISearchBookItemCatalog bookItemCatalog) {
        this.accountCatalog = accountCatalog;
        this.bookItemCatalog = bookItemCatalog;
    }


    public int checkBookAvailability(Long isbn) {
        // todo: what if isbn does not exists - add unit test
        return bookItemCatalog.findByIsbn(isbn).size();
    }

    private int checkNumberOfBooksBorrowedBy(Long accountId) {
        // todo: what if isbn does not exists - add unit test
        return lentBookInfoByAccountId.get(accountId).size();
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

    private BookItem bookToLent(Long isbn) {
        for (BookItem book : bookItemCatalog.findByIsbn(isbn)) {
            if (lentBookInfoByRfidTag.get(book.getRfidTag()) == null) {
                return book;
            }
        }
        return null;
    }

    public LentBookInfo lend(Long accountId, Long isbn) {
        // todo: improve readability of the condition in 'if' statement
        if (accountCatalog.findById(accountId).getState().equals(AccountState.ACTIVE) &&
                !hasOverDueBook(isbn) &&
                checkBookAvailability(isbn) > 0 &&
                (lentBookInfoByAccountId.get(accountId) == null || lentBookInfoByAccountId.get(accountId).size() < 4) &&
                bookToLent(isbn) != null) {

            BookItem book = bookToLent(isbn);
            LocalDate today = LocalDate.now();
            LocalDate returnDay = today.plusDays(30);
            LentBookInfo lentBookInfo = new LentBookInfo(book.getRfidTag(), accountId, today, returnDay);

            lentBookInfoByRfidTag.put(book.getRfidTag(), lentBookInfo);

            // todo: common parts of if-else branches can be extracted out of if-else
            if (lentBookInfoByAccountId.get(accountId) != null) {
                List<LentBookInfo> list = lentBookInfoByAccountId.get(accountId);
                list.add(lentBookInfo);
                lentBookInfoByAccountId.put(accountId, list);
            } else {
                List<LentBookInfo> list = new ArrayList<>();
                list.add(lentBookInfo);
                lentBookInfoByAccountId.put(accountId, list);
            }
            return lentBookInfo;
        }
        throw new IllegalArgumentException();
    }

    public Boolean returnBook(Long accountId, String rfidTag) {
        if (bookItemCatalog.findByRfidTag(rfidTag) != null &&
                lentBookInfoByRfidTag.get(rfidTag) != null) {

            lentBookInfoByRfidTag.remove(rfidTag);

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
