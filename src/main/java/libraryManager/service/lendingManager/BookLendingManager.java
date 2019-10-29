package libraryManager.service.lendingManager;

import libraryManager.model.AccountState;
import libraryManager.model.BookItem;
import libraryManager.model.LentBookInfo;
import libraryManager.service.account.ISearchAccountCatalog;
import libraryManager.service.book.ISearchBookItemCatalog;

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
        List<BookItem> byIsbn = bookItemCatalog.findByIsbn(isbn);
        if (byIsbn != null) {
            return byIsbn.size();
        }
        return 0;
    }

    private int checkNumberOfBooksBorrowedBy(Long accountId) {
        List<LentBookInfo> infos = lentBookInfoByAccountId.get(accountId);
        if (infos != null) {
            return infos.size();
        }
        return 0;
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

    private Boolean isAccountActive(Long accountId) {
        return accountCatalog.findById(accountId).getState().equals(AccountState.ACTIVE);
    }

    private Boolean canLendMoreBooks(Long accountId) {
        return (lentBookInfoByAccountId.get(accountId) == null || lentBookInfoByAccountId.get(accountId).size() < 4);
    }

    public LentBookInfo lend(Long accountId, Long isbn) {

        if (isAccountActive(accountId) &&
                !hasOverDueBook(isbn) &&
                checkBookAvailability(isbn) > 0 &&
                canLendMoreBooks(accountId) &&
                bookToLent(isbn) != null) {

            BookItem book = bookToLent(isbn);
            LocalDate today = LocalDate.now();
            LocalDate returnDay = today.plusDays(30);
            LentBookInfo lentBookInfo = new LentBookInfo(book.getRfidTag(), accountId, today, returnDay);

            lentBookInfoByRfidTag.put(book.getRfidTag(), lentBookInfo);

            List<LentBookInfo> list = new ArrayList<>();
            if (lentBookInfoByAccountId.get(accountId) != null) {
                list = lentBookInfoByAccountId.get(accountId);
            }
            list.add(lentBookInfo);
            lentBookInfoByAccountId.put(accountId, list);
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