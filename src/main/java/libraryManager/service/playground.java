package libraryManager.service;

import libraryManager.model.Account;
import libraryManager.model.AccountState;
import libraryManager.model.BookItem;
import libraryManager.model.Language;
import libraryManager.service.account.AccountCatalog;
import libraryManager.service.book.BookItemCatalog;
import libraryManager.service.historyManager.HistoryManager;
import libraryManager.service.lendingManager.BookLendingManager;
import libraryManager.service.reservationManager.BookReservationManager;

import java.awt.*;
import java.time.LocalDate;
import java.util.Date;

public class playground {
    public static void main(String[] args) {

        BookItemCatalog bookItemCatalog = new BookItemCatalog();
        AccountCatalog accountCatalog = new AccountCatalog();
        HistoryManager historyManager = new HistoryManager();
        BookReservationManager reservationManager = new BookReservationManager(accountCatalog, bookItemCatalog);
        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalog, bookItemCatalog, historyManager, reservationManager);

        Account account1 = new Account(111L, "Jon Snow", AccountState.ACTIVE);
        Account account2 = new Account(222L, "Samwell Tarly", AccountState.ACTIVE);
        Account account3 = new Account(333L, "Littlefinger", AccountState.ACTIVE);

        accountCatalog.add(account1);
        accountCatalog.add(account2);
        accountCatalog.add(account3);

        accountCatalog.suspend(333L);

        System.out.println("Littlefinger's account is " + account3.getState());
        System.out.println("Account with id number 111L owns to "+ account1.getAccountName());

        BookItem book0 = new BookItem(1L, "Krzyżacy", "Sienkiewicz", "Zysk i Ska", 350, Language.POLISH, "aaa");
        BookItem book1 = new BookItem(1L, "Krzyżacy", "Sienkiewicz", "Zysk i Ska", 350, Language.POLISH, "bbb");
        BookItem book2 = new BookItem(2L, "Pan Tadeusz", "Mickiewicz", "Zysk i Ska", 200, Language.POLISH, "ccc");
        BookItem book3 = new BookItem(3L, "Potop", "Sienkiewicz", "Zysk i Ska", 200, Language.POLISH, "ddd");
        BookItem book4 = new BookItem(4L, "Wesele", "Wyspiański", "Zysk i Ska", 200, Language.POLISH, "eee");

        bookItemCatalog.add(book0);
        bookItemCatalog.add(book1);
        bookItemCatalog.add(book2);
        bookItemCatalog.add(book3);
        bookItemCatalog.add(book4);

        reservationManager.reserve(111L,1L);
        System.out.println();


    }
}
