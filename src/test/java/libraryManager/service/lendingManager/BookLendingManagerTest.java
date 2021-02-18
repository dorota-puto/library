package libraryManager.service.lendingManager;

import libraryManager.entity.Account;
import libraryManager.entity.Author;
import libraryManager.entity.Language;
import libraryManager.entity.full.FullBookItem;
import libraryManager.model.*;
import libraryManager.service.account.ISearchAccountCatalog;
import libraryManager.service.book.ISearchBookItemCatalog;
import libraryManager.service.historyManager.HistoryManager;
import libraryManager.service.reservationManager.BookReservationManager;
import org.mockito.ArgumentCaptor;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

public class BookLendingManagerTest {

    @Test
    public void lendTest() {
        //given
        ISearchAccountCatalog accountCatalogMock = mock(ISearchAccountCatalog.class);
        ISearchBookItemCatalog bookCatalogMock = mock(ISearchBookItemCatalog.class);
        HistoryManager historyManagerMock = mock(HistoryManager.class);
        BookReservationManager reservationManagerMock = mock(BookReservationManager.class);

        FullBookItem book1 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "aaa");
        FullBookItem book2 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "bbb");

        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock, historyManagerMock, reservationManagerMock);
        given(accountCatalogMock.findById(111L)).willReturn(new Account(111L, "Edmund Elefant", true));
        given(bookCatalogMock.findByIsbn(1L)).willReturn(Arrays.asList(book1, book2));
        given(reservationManagerMock.isAllowed("aaa")).willReturn(true);
        given(reservationManagerMock.isReservedForThisAccount(111L, "aaa")).willReturn(false);
        given(reservationManagerMock.isAllowed("bbb")).willReturn(true);
        given(reservationManagerMock.isReservedForThisAccount(111L, "bbb")).willReturn(false);
        LentBookInfo expectedLentBookInfo = new LentBookInfo("aaa", 111L, LocalDate.now(), LocalDate.now().plusDays(30));

        //when
        LentBookInfo lentBookInfo = bookLendingManager.lend(111L, 1L);

        //then
        assertThat(lentBookInfo).isEqualTo(expectedLentBookInfo);
    }

    @Test
    public void lendBookWhenReservedForThisAccountTest() {
        //given
        ISearchAccountCatalog accountCatalogMock = mock(ISearchAccountCatalog.class);
        ISearchBookItemCatalog bookCatalogMock = mock(ISearchBookItemCatalog.class);
        HistoryManager historyManagerMock = mock(HistoryManager.class);
        BookReservationManager reservationManagerMock = mock(BookReservationManager.class);

        FullBookItem book1 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "aaa");
        FullBookItem book2 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "bbb");

        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock, historyManagerMock, reservationManagerMock);
        given(accountCatalogMock.findById(111L)).willReturn(new Account(111L, "Edmund Elefant", true));
        given(bookCatalogMock.findByIsbn(1L)).willReturn(Arrays.asList(book1, book2));
        given(reservationManagerMock.isAllowed("aaa")).willReturn(true);
        given(reservationManagerMock.isReservedForThisAccount(111L, "aaa")).willReturn(false);
        given(reservationManagerMock.isAllowed("bbb")).willReturn(false);
        given(reservationManagerMock.isReservedForThisAccount(111L, "bbb")).willReturn(true);
        LentBookInfo expectedLentBookInfo = new LentBookInfo("bbb", 111L, LocalDate.now(), LocalDate.now().plusDays(30));

        //when
        LentBookInfo lentBookInfo = bookLendingManager.lend(111L, 1L);

        //then
        assertThat(lentBookInfo).isEqualTo(expectedLentBookInfo);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void lendBookWhenReservedTest() {
        //given
        ISearchAccountCatalog accountCatalogMock = mock(ISearchAccountCatalog.class);
        ISearchBookItemCatalog bookCatalogMock = mock(ISearchBookItemCatalog.class);
        HistoryManager historyManagerMock = mock(HistoryManager.class);
        BookReservationManager reservationManagerMock = mock(BookReservationManager.class);

        FullBookItem book1 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "aaa");

        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock, historyManagerMock, reservationManagerMock);
        given(accountCatalogMock.findById(111L)).willReturn(new Account(111L, "Edmund Elefant", true));
        given(bookCatalogMock.findByIsbn(1L)).willReturn(Arrays.asList(book1));
        given(reservationManagerMock.isAllowed("aaa")).willReturn(false);
        given(reservationManagerMock.isReservedForThisAccount(111L, "aaa")).willReturn(false);

        //when
        bookLendingManager.lend(111L, 1L);
    }

    @Test
    public void lendSecondBookItemTest() {
        //given
        ISearchAccountCatalog accountCatalogMock = mock(ISearchAccountCatalog.class);
        ISearchBookItemCatalog bookCatalogMock = mock(ISearchBookItemCatalog.class);
        HistoryManager historyManagerMock = mock(HistoryManager.class);
        BookReservationManager reservationManagerMock = mock(BookReservationManager.class);

        FullBookItem book1 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "aaa");
        FullBookItem book2 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "bbb");

        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock, historyManagerMock, reservationManagerMock);
        given(accountCatalogMock.findById(111L)).willReturn(new Account(111L, "Edmund Elefant", true));
        given(bookCatalogMock.findByIsbn(1L)).willReturn(Arrays.asList(book1, book2));
        given(reservationManagerMock.isAllowed("aaa")).willReturn(true);
        given(reservationManagerMock.isReservedForThisAccount(111L, "aaa")).willReturn(false);
        given(reservationManagerMock.isAllowed("bbb")).willReturn(true);
        given(reservationManagerMock.isReservedForThisAccount(111L, "bbb")).willReturn(false);

        LentBookInfo expectedLentBookInfo = new LentBookInfo("bbb", 111L, LocalDate.now(), LocalDate.now().plusDays(30));

        //when
        LentBookInfo lentBookInfo1 = bookLendingManager.lend(111L, 1L);
        LentBookInfo lentBookInfo2 = bookLendingManager.lend(111L, 1L);


        //then
        assertThat(lentBookInfo2).isEqualTo(expectedLentBookInfo);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void lendWhenHasOverDueBooksTest() {
        //given
        ISearchAccountCatalog accountCatalogMock = mock(ISearchAccountCatalog.class);
        ISearchBookItemCatalog bookCatalogMock = mock(ISearchBookItemCatalog.class);
        HistoryManager historyManagerMock = mock(HistoryManager.class);
        BookReservationManager reservationManagerMock = mock(BookReservationManager.class);

        FullBookItem book1 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "aaa");
        FullBookItem book2 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "bbb");

        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock, historyManagerMock, reservationManagerMock);
        given(accountCatalogMock.findById(111L)).willReturn(new Account(111L, "Edmund Elefant", true));
        given(bookCatalogMock.findByIsbn(1L)).willReturn(Arrays.asList(book1, book2));
        given(reservationManagerMock.isAllowed("aaa")).willReturn(true);
        given(reservationManagerMock.isReservedForThisAccount(111L, "aaa")).willReturn(false);
        given(reservationManagerMock.isAllowed("bbb")).willReturn(true);
        given(reservationManagerMock.isReservedForThisAccount(111L, "bbb")).willReturn(false);

        LentBookInfo info = new LentBookInfo("aaa", 111L, LocalDate.of(2018, 12, 12), LocalDate.of(2018, 12, 12).plusDays(30));
        bookLendingManager.lentBookInfoByAccountId.put(111L, new ArrayList<>(Arrays.asList(info)));

        //when
        bookLendingManager.lend(111L, 1L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void lendMoreThanFourBooks() {
        //given
        ISearchAccountCatalog accountCatalogMock = mock(ISearchAccountCatalog.class);
        ISearchBookItemCatalog bookCatalogMock = mock(ISearchBookItemCatalog.class);
        HistoryManager historyManagerMock = mock(HistoryManager.class);
        BookReservationManager reservationManagerMock = mock(BookReservationManager.class);

        FullBookItem book0 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "aaa");
        FullBookItem book1 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "bbb");
        FullBookItem book2 = new FullBookItem(2L, "Pan Tadeusz", new Author("Adam","Mickiewicz"), "Zysk i Ska", 200, new Language("polish"), "ccc");
        FullBookItem book3 = new FullBookItem(3L, "Potop", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 200, new Language("polish"), "ddd");
        FullBookItem book4 = new FullBookItem(4L, "Wesele", new Author("Stanisław","Wyspiański"), "Zysk i Ska", 200, new Language("polish"), "eee");

        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock, historyManagerMock, reservationManagerMock);
        given(accountCatalogMock.findById(111L)).willReturn(new Account(111L, "Edmund Elefant", true));
        given(bookCatalogMock.findByIsbn(1L)).willReturn(Arrays.asList(book0, book1));
        given(bookCatalogMock.findByIsbn(2L)).willReturn(Arrays.asList(book2));
        given(bookCatalogMock.findByIsbn(3L)).willReturn(Arrays.asList(book3));
        given(bookCatalogMock.findByIsbn(4L)).willReturn(Arrays.asList(book4));

        //when
        bookLendingManager.lend(111L, 1L);
        bookLendingManager.lend(111L, 1L);
        bookLendingManager.lend(111L, 2L);
        bookLendingManager.lend(111L, 3L);
        bookLendingManager.lend(111L, 4L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void lendWhenAccoundIsSuspendedTest() {
        //given
        ISearchAccountCatalog accountCatalogMock = mock(ISearchAccountCatalog.class);
        ISearchBookItemCatalog bookCatalogMock = mock(ISearchBookItemCatalog.class);
        HistoryManager historyManagerMock = mock(HistoryManager.class);
        BookReservationManager reservationManagerMock = mock(BookReservationManager.class);

        FullBookItem book2 = new FullBookItem(2L, "Pan Tadeusz", new Author("Adam","Mickiewicz"), "Zysk i Ska", 200, new Language("polish"), "ccc");

        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock, historyManagerMock, reservationManagerMock);
        given(accountCatalogMock.findById(111L)).willReturn(new Account(111L, "Edmund Elefant", false));
        given(bookCatalogMock.findByIsbn(2L)).willReturn(Arrays.asList(book2));

        //when
        bookLendingManager.lend(111L, 2L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void lendNotAvailableBookItemTest() {
        //given
        ISearchAccountCatalog accountCatalogMock = mock(ISearchAccountCatalog.class);
        ISearchBookItemCatalog bookCatalogMock = mock(ISearchBookItemCatalog.class);
        HistoryManager historyManagerMock = mock(HistoryManager.class);
        BookReservationManager reservationManagerMock = mock(BookReservationManager.class);

        FullBookItem book1 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "aaa");
        FullBookItem book2 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "bbb");

        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock, historyManagerMock, reservationManagerMock);
        given(accountCatalogMock.findById(111L)).willReturn(new Account(111L, "Edmund Elefant", true));
        given(bookCatalogMock.findByIsbn(2L)).willReturn(Arrays.asList(book2));

        //when
        bookLendingManager.lend(111L, 1L);
        bookLendingManager.lend(111L, 1L);
        bookLendingManager.lend(111L, 1L);
    }

    @Test
    public void returnBookTest() {
        //given
        ISearchAccountCatalog accountCatalogMock = mock(ISearchAccountCatalog.class);
        ISearchBookItemCatalog bookCatalogMock = mock(ISearchBookItemCatalog.class);
        HistoryManager historyManagerMock = mock(HistoryManager.class);
        BookReservationManager reservationManagerMock = mock(BookReservationManager.class);

        FullBookItem book1 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "aaa");

        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock, historyManagerMock, reservationManagerMock);
        given(accountCatalogMock.findById(111L)).willReturn(new Account(111L, "Edmund Elefant", true));
        given(bookCatalogMock.findByRfidTag("aaa")).willReturn(book1);
        given(bookCatalogMock.findByIsbn(1L)).willReturn(Arrays.asList(book1));
        given(reservationManagerMock.isAllowed("aaa")).willReturn(true);
        given(reservationManagerMock.isReservedForThisAccount(111L, "aaa")).willReturn(false);

        //when
        bookLendingManager.lend(111L, 1L);
        Boolean isReturned = bookLendingManager.returnBook(111L, "aaa");

        //then
        assertThat(isReturned).isEqualTo(true);
    }

    @Test
    public void addingLentBookInfoToHistoryManagerWhenReturnTest() {
        //given
        ISearchAccountCatalog accountCatalogMock = mock(ISearchAccountCatalog.class);
        ISearchBookItemCatalog bookCatalogMock = mock(ISearchBookItemCatalog.class);
        HistoryManager historyManagerMock = mock(HistoryManager.class);
        BookReservationManager reservationManagerMock = mock(BookReservationManager.class);

        FullBookItem book1 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "aaa");

        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock, historyManagerMock, reservationManagerMock);
        given(accountCatalogMock.findById(111L)).willReturn(new Account(111L, "Edmund Elefant", true));
        given(bookCatalogMock.findByRfidTag("aaa")).willReturn(book1);
        given(bookCatalogMock.findByIsbn(1L)).willReturn(Arrays.asList(book1));
        given(reservationManagerMock.isAllowed("aaa")).willReturn(true);
        given(reservationManagerMock.isReservedForThisAccount(111L, "aaa")).willReturn(false);

        ArgumentCaptor<LentBookInfo> argument = ArgumentCaptor.forClass(LentBookInfo.class);

        //when
        bookLendingManager.lend(111L, 1L);
        bookLendingManager.returnBook(111L, "aaa");

        //then

        verify(historyManagerMock).add(eq(111L), argument.capture());
        assertThat("aaa").isEqualTo(argument.getValue().getRfidTag());
        assertThat(111L).isEqualTo(argument.getValue().getBorrowerAccountId());
        assertThat(LocalDate.now()).isEqualTo(argument.getValue().getBorrowDate());
        assertThat(argument.getValue().getDueDate()).isNotNull();

    }

    @Test
    public void returnNotLentBookTest() {
        //given
        ISearchAccountCatalog accountCatalogMock = mock(ISearchAccountCatalog.class);
        ISearchBookItemCatalog bookCatalogMock = mock(ISearchBookItemCatalog.class);
        HistoryManager historyManagerMock = mock(HistoryManager.class);
        BookReservationManager reservationManagerMock = mock(BookReservationManager.class);

        FullBookItem book1 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "aaa");

        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock, historyManagerMock, reservationManagerMock);
        given(accountCatalogMock.findById(111L)).willReturn(new Account(111L, "Edmund Elefant", true));
        given(bookCatalogMock.findByRfidTag("aaa")).willReturn(book1);

        //when

        Boolean isReturned = bookLendingManager.returnBook(111L, "aaa");

        //then
        assertThat(isReturned).isEqualTo(false);
    }

    @Test
    public void checkBookAvailabilityTest() {
        //given
        ISearchAccountCatalog accountCatalogMock = mock(ISearchAccountCatalog.class);
        ISearchBookItemCatalog bookCatalogMock = mock(ISearchBookItemCatalog.class);
        HistoryManager historyManagerMock = mock(HistoryManager.class);
        BookReservationManager reservationManagerMock = mock(BookReservationManager.class);

        FullBookItem book0 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "aaa");
        FullBookItem book1 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "bbb");
        FullBookItem book2 = new FullBookItem(2L, "Pan Tadeusz", new Author("Adam","Mickiewicz"), "Zysk i Ska", 200, new Language("polish"), "ccc");
        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock, historyManagerMock, reservationManagerMock);
        given(bookCatalogMock.findByIsbn(1L)).willReturn(Arrays.asList(book0, book1));

        //when

        int availability = bookLendingManager.checkBookAvailability(1L);

        //then
        assertThat(availability).isEqualTo(2);
    }

    @Test
    public void checkBookAvailabilityWhenNoAvailableBooksTest() {
        //given
        ISearchAccountCatalog accountCatalogMock = mock(ISearchAccountCatalog.class);
        ISearchBookItemCatalog bookCatalogMock = mock(ISearchBookItemCatalog.class);
        HistoryManager historyManagerMock = mock(HistoryManager.class);
        BookReservationManager reservationManagerMock = mock(BookReservationManager.class);

        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock, historyManagerMock, reservationManagerMock);
        given(bookCatalogMock.findByIsbn(1L)).willReturn(null);

        //when

        int availability = bookLendingManager.checkBookAvailability(1L);

        //then
        assertThat(availability).isEqualTo(0);
    }
}