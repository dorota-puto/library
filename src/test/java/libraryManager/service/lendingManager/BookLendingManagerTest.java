package libraryManager.service.lendingManager;

import libraryManager.model.*;
import libraryManager.service.account.ISearchAccountCatalog;
import libraryManager.service.book.ISearchBookItemCatalog;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

public class BookLendingManagerTest {

    @Test
    public void lendTest() {
        //given
        ISearchAccountCatalog accountCatalogMock = mock(ISearchAccountCatalog.class);
        ISearchBookItemCatalog bookCatalogMock = mock(ISearchBookItemCatalog.class);
        BookItem book1 = new BookItem(1L, "Krzyżacy", "Sienkiewicz", "Zysk i Ska", 350, Language.POLISH, "aaa");
        BookItem book2 = new BookItem(1L, "Krzyżacy", "Sienkiewicz", "Zysk i Ska", 350, Language.POLISH, "bbb");

        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock);
        given(accountCatalogMock.findById(111L)).willReturn(new Account(111L, "Edmund Elefant", AccountState.ACTIVE));
        given(bookCatalogMock.findByIsbn(1L)).willReturn(Arrays.asList(book1, book2));

        LentBookInfo expectedLentBookInfo = new LentBookInfo("aaa", 111L, LocalDate.of(2019, 10, 29), LocalDate.of(2019, 11, 28));

        //when
        LentBookInfo lentBookInfo = bookLendingManager.lend(111L, 1L);

        //then
        assertThat(lentBookInfo).isEqualTo(expectedLentBookInfo);
    }

    @Test
    public void lendSecondBookItemTest() {
        //given
        ISearchAccountCatalog accountCatalogMock = mock(ISearchAccountCatalog.class);
        ISearchBookItemCatalog bookCatalogMock = mock(ISearchBookItemCatalog.class);
        BookItem book1 = new BookItem(1L, "Krzyżacy", "Sienkiewicz", "Zysk i Ska", 350, Language.POLISH, "aaa");
        BookItem book2 = new BookItem(1L, "Krzyżacy", "Sienkiewicz", "Zysk i Ska", 350, Language.POLISH, "bbb");

        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock);
        given(accountCatalogMock.findById(111L)).willReturn(new Account(111L, "Edmund Elefant", AccountState.ACTIVE));
        given(bookCatalogMock.findByIsbn(1L)).willReturn(Arrays.asList(book1, book2));

        LentBookInfo expectedLentBookInfo = new LentBookInfo("bbb", 111L, LocalDate.of(2019, 10, 29), LocalDate.of(2019, 11, 28));

        //when
        LentBookInfo lentBookInfo1 = bookLendingManager.lend(111L, 1L);
        LentBookInfo lentBookInfo2 = bookLendingManager.lend(111L, 1L);


        //then
        assertThat(lentBookInfo2).isEqualTo(expectedLentBookInfo);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void lendMoreThanFourBooks() {
        //given
        ISearchAccountCatalog accountCatalogMock = mock(ISearchAccountCatalog.class);
        ISearchBookItemCatalog bookCatalogMock = mock(ISearchBookItemCatalog.class);

        BookItem book0 = new BookItem(1L, "Krzyżacy", "Sienkiewicz", "Zysk i Ska", 350, Language.POLISH, "aaa");
        BookItem book1 = new BookItem(1L, "Krzyżacy", "Sienkiewicz", "Zysk i Ska", 350, Language.POLISH, "bbb");
        BookItem book2 = new BookItem(2L, "Pan Tadeusz", "Mickiewicz", "Zysk i Ska", 200, Language.POLISH, "ccc");
        BookItem book3 = new BookItem(3L, "Potop", "Sienkiewicz", "Zysk i Ska", 200, Language.POLISH, "ddd");
        BookItem book4 = new BookItem(4L, "Wesele", "Wyspiański", "Zysk i Ska", 200, Language.POLISH, "eee");

        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock);
        given(accountCatalogMock.findById(111L)).willReturn(new Account(111L, "Edmund Elefant", AccountState.ACTIVE));
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

        BookItem book2 = new BookItem(2L, "Pan Tadeusz", "Mickiewicz", "Zysk i Ska", 200, Language.POLISH, "ccc");

        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock);
        given(accountCatalogMock.findById(111L)).willReturn(new Account(111L, "Edmund Elefant", AccountState.SUSPENDED));
        given(bookCatalogMock.findByIsbn(2L)).willReturn(Arrays.asList(book2));

        //when
        bookLendingManager.lend(111L, 2L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void lendNotAvailableBookItemTest() {
        //given
        ISearchAccountCatalog accountCatalogMock = mock(ISearchAccountCatalog.class);
        ISearchBookItemCatalog bookCatalogMock = mock(ISearchBookItemCatalog.class);

        BookItem book1 = new BookItem(1L, "Krzyżacy", "Sienkiewicz", "Zysk i Ska", 350, Language.POLISH, "aaa");
        BookItem book2 = new BookItem(1L, "Krzyżacy", "Sienkiewicz", "Zysk i Ska", 350, Language.POLISH, "bbb");

        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock);
        given(accountCatalogMock.findById(111L)).willReturn(new Account(111L, "Edmund Elefant", AccountState.ACTIVE));
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

        BookItem book1 = new BookItem(1L, "Krzyżacy", "Sienkiewicz", "Zysk i Ska", 350, Language.POLISH, "aaa");

        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock);
        given(accountCatalogMock.findById(111L)).willReturn(new Account(111L, "Edmund Elefant", AccountState.ACTIVE));
        given(bookCatalogMock.findByRfidTag("aaa")).willReturn(book1);
        given(bookCatalogMock.findByIsbn(1L)).willReturn(Arrays.asList(book1));

        //when
        bookLendingManager.lend(111L, 1L);
        Boolean isReturned = bookLendingManager.returnBook(111L, "aaa");

        //then
        assertThat(isReturned).isEqualTo(true);
    }

    @Test
    public void returnNotLentBookTest() {
        //given
        ISearchAccountCatalog accountCatalogMock = mock(ISearchAccountCatalog.class);
        ISearchBookItemCatalog bookCatalogMock = mock(ISearchBookItemCatalog.class);

        BookItem book1 = new BookItem(1L, "Krzyżacy", "Sienkiewicz", "Zysk i Ska", 350, Language.POLISH, "aaa");

        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock);
        given(accountCatalogMock.findById(111L)).willReturn(new Account(111L, "Edmund Elefant", AccountState.ACTIVE));
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

        BookItem book0 = new BookItem(1L, "Krzyżacy", "Sienkiewicz", "Zysk i Ska", 350, Language.POLISH, "aaa");
        BookItem book1 = new BookItem(1L, "Krzyżacy", "Sienkiewicz", "Zysk i Ska", 350, Language.POLISH, "bbb");
        BookItem book2 = new BookItem(2L, "Pan Tadeusz", "Mickiewicz", "Zysk i Ska", 200, Language.POLISH, "ccc");
        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock);
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
        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock);
        given(bookCatalogMock.findByIsbn(1L)).willReturn(null);

        //when

        int availability = bookLendingManager.checkBookAvailability(1L);

        //then
        assertThat(availability).isEqualTo(0);
    }
}