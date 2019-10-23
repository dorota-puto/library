package libraryManager.service.LendingManager;

import com.sun.org.apache.xpath.internal.operations.Bool;
import libraryManager.model.AccountState;
import libraryManager.model.BookItem;
import libraryManager.model.LentBookInfo;
import libraryManager.service.Account.AccountCatalog;
import libraryManager.service.Book.BookItemCatalog;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookLendingManager {

    private AccountCatalog accountCatalog;
    private BookItemCatalog bookItemCatalog;

    public BookLendingManager(AccountCatalog accountCatalog, BookItemCatalog bookItemCatalog) {
        this.accountCatalog = accountCatalog;
        this.bookItemCatalog = bookItemCatalog;
    }

    private Map<Long, List<LentBookInfo>> lentBookInfoByAccountId = new HashMap<>();
    private Map<String, LentBookInfo> lentBookInfoByRfidTag = new HashMap<>();

    public int checkBookAvailability(Long isbn) {
        return bookItemCatalog.findByIsbn(isbn).size();
    }

    private int checkNumberOfBooksBorrowedBy(Long accountId) {
        return lentBookInfoByAccountId.get(accountId).size();
    }

    private Boolean hasOverDueBook(Long accountId) {
        for (LentBookInfo info : lentBookInfoByAccountId.get(accountId)) {
            if (info.getDueDate().isBefore(LocalDate.now())) {
                return true;
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

        if (accountCatalog.findById(accountId).getState().equals(AccountState.ACTIVE) &&
                lentBookInfoByAccountId.get(accountId).size() < 4 &&
                !hasOverDueBook(isbn) &&
                checkBookAvailability(isbn) > 0 &&
                bookToLent(isbn) != null) {

            BookItem book = bookToLent(isbn);
            LocalDate today = LocalDate.now();
            LocalDate returnDay = today.plusDays(30);
            LentBookInfo lentBookInfo = new LentBookInfo(book.getRfidTag(), accountId, today, returnDay);

            lentBookInfoByRfidTag.put(book.getRfidTag(), lentBookInfo);

            List<LentBookInfo> list = lentBookInfoByAccountId.get(accountId);
            list.add(lentBookInfo);
            lentBookInfoByAccountId.put(accountId, list);

            return lentBookInfo;
        }
        throw new IllegalArgumentException();
    }

    public Boolean returnBook(Long accountId, String rfidTag){
        if(bookItemCatalog.findByRfidTag(rfidTag)!=null &&
        lentBookInfoByRfidTag.get(rfidTag)!=null){

            lentBookInfoByRfidTag.remove(rfidTag);

            List<LentBookInfo> list=lentBookInfoByAccountId.get(accountId);
            for (LentBookInfo info:list) {
                if(info.getRfidTag().equals(rfidTag)){
                    list.remove(info);
                    return true;
                }
            }
        }
        return false;
    }
}
