package libraryManager.service.reservationManager;

import libraryManager.entity.Author;
import libraryManager.entity.Language;
import libraryManager.entity.full.FullBookItem;
import libraryManager.model.ReservedBookInfo;
import libraryManager.service.account.AccountCatalog;
import libraryManager.service.book.BookItemCatalog;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class BookReservationManagerTest {

    @Test
    public void testAddToReservationCatalog() {
        //given
        BookItemCatalog bookItemCatalogMock = mock(BookItemCatalog.class);
        AccountCatalog accountCatalogMock = mock(AccountCatalog.class);
        BookReservationManager reservationManager = new BookReservationManager(accountCatalogMock, bookItemCatalogMock);
        //when
        reservationManager.addToReservationCatalog("aaa");
        //then
        assertThat(reservationManager.isAllowed("aaa")).isEqualTo(true);
    }

    @Test
    public void testRemoveFromReservationCatalog() {
        //given
        BookItemCatalog bookItemCatalogMock = mock(BookItemCatalog.class);
        AccountCatalog accountCatalogMock = mock(AccountCatalog.class);
        FullBookItem book1 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "aaa");
        given(bookItemCatalogMock.getRfidTags()).willReturn(new HashSet<>(Arrays.asList("aaa")));
        BookReservationManager reservationManager = new BookReservationManager(accountCatalogMock, bookItemCatalogMock);

        given(bookItemCatalogMock.findByRfidTag("aaa")).willReturn(book1);
        //when
        Boolean isRemoved = reservationManager.removeFromReservationCatalog("aaa");
        //then
        assertThat(isRemoved).isEqualTo(true);
    }

    @Test
    public void testReserve() {
        //given
        BookItemCatalog bookItemCatalogMock = mock(BookItemCatalog.class);
        AccountCatalog accountCatalogMock = mock(AccountCatalog.class);

        given(bookItemCatalogMock.getRfidTags()).willReturn(new HashSet<>(Arrays.asList("aaa","bbb")));

        FullBookItem book1 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "aaa");
        FullBookItem book2 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "bbb");

        given(bookItemCatalogMock.findByIsbn(1L)).willReturn(Arrays.asList(book1, book2));
        BookReservationManager reservationManager = new BookReservationManager(accountCatalogMock, bookItemCatalogMock);
        ReservedBookInfo expectedInfo = new ReservedBookInfo("bbb", 111L, LocalDate.now(), LocalDate.now().plusDays(30));
        //when
        reservationManager.reserve(222L,1L);
        ReservedBookInfo info = reservationManager.reserve(111L,1L);
        //then
        assertThat(expectedInfo).isEqualTo(info);
    }

    @Test
    public void testReserveNotAvailableBookItem() {
        //given
        BookItemCatalog bookItemCatalogMock = mock(BookItemCatalog.class);
        AccountCatalog accountCatalogMock = mock(AccountCatalog.class);

        given(bookItemCatalogMock.getRfidTags()).willReturn(new HashSet<>(Arrays.asList("aaa")));
        FullBookItem book1 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "aaa");
        given(bookItemCatalogMock.findByIsbn(1L)).willReturn(Arrays.asList(book1));

        BookReservationManager reservationManager = new BookReservationManager(accountCatalogMock, bookItemCatalogMock);
        //when
        reservationManager.reserve(111L, 1L);
        ReservedBookInfo expectedInfo = reservationManager.reserve(222L, 1L);

        //then
        assertThat(expectedInfo).isEqualTo(null);
    }

    @Test
    public void testIsReservedForThisAccount() {
        //given
        BookItemCatalog bookItemCatalogMock = mock(BookItemCatalog.class);
        AccountCatalog accountCatalogMock = mock(AccountCatalog.class);

        given(bookItemCatalogMock.getRfidTags()).willReturn(new HashSet<>(Arrays.asList("aaa")));
        FullBookItem book1 = new FullBookItem(1L, "Krzyżacy", new Author("Henryk","Sienkiewicz"), "Zysk i Ska", 350, new Language("polish"), "aaa");
        given(bookItemCatalogMock.findByIsbn(1L)).willReturn(Arrays.asList(book1));

        BookReservationManager reservationManager = new BookReservationManager(accountCatalogMock, bookItemCatalogMock);

        //when
        reservationManager.reserve(111L, 1L);
        Boolean isReservedForThisAccount = reservationManager.isReservedForThisAccount(111L, "aaa");

        //then
        assertThat(isReservedForThisAccount).isEqualTo(true);

    }

    @Test
    public void testIsReservedForThisAccount2() {
        //given
        BookItemCatalog bookItemCatalogMock = mock(BookItemCatalog.class);
        AccountCatalog accountCatalogMock = mock(AccountCatalog.class);


        given(bookItemCatalogMock.getRfidTags()).willReturn(new HashSet<>(Arrays.asList("aaa")));
        BookReservationManager reservationManager = new BookReservationManager(accountCatalogMock, bookItemCatalogMock);

        //when
        Boolean isReservedForThisAccount = reservationManager.isReservedForThisAccount(111L, "aaa");

        //then
        assertThat(isReservedForThisAccount).isEqualTo(false);
    }

    @Test
    public void testCancelReservationIfOverDue() {
        //given
        BookItemCatalog bookItemCatalogMock = mock(BookItemCatalog.class);
        AccountCatalog accountCatalogMock = mock(AccountCatalog.class);

        BookReservationManager reservationManager = new BookReservationManager(accountCatalogMock, bookItemCatalogMock);

        ReservedBookInfo info1 = new ReservedBookInfo("aaa", 111L, LocalDate.now(), LocalDate.now().plusDays(30));
        ReservedBookInfo info2 = new ReservedBookInfo("bbb", 111L, LocalDate.of(2018, 12, 10), LocalDate.of(2018, 12, 10).plusDays(30));
        ReservedBookInfo info3 = new ReservedBookInfo("ccc", 222L, LocalDate.of(2018, 12, 10), LocalDate.of(2018, 12, 10).plusDays(30));

        reservationManager.reservedBookInfosByRfidTag.put("aaa",info1);
        reservationManager.reservedBookInfosByRfidTag.put("bbb",info2);
        reservationManager.reservedBookInfosByRfidTag.put("ccc",info3);
        reservationManager.reservedBookInfosByAccountId.put(111L,new ArrayList<>(Arrays.asList(info1,info2)));
        reservationManager.reservedBookInfosByAccountId.put(222L,new ArrayList<>(Arrays.asList(info3)));

        //when
        reservationManager.cancelReservationIfOverDue();

        //then
        assertThat(reservationManager.isAllowed("aaa")).isEqualTo(false);
        assertThat(reservationManager.isAllowed("bbb")).isEqualTo(true);
        assertThat(reservationManager.isAllowed("ccc")).isEqualTo(true);
        assertThat(reservationManager.reservedBookInfosByAccountId.get(111L)).isEqualTo(Arrays.asList(info1));
        assertThat(reservationManager.reservedBookInfosByAccountId.get(222L)).isEmpty();
        assertThat(reservationManager.reservedBookInfosByRfidTag.get("aaa")).isEqualTo(info1);
        assertThat(reservationManager.reservedBookInfosByRfidTag.get("bbb")).isEqualTo(null);
        assertThat(reservationManager.reservedBookInfosByRfidTag.get("ccc")).isEqualTo(null);
    }
}