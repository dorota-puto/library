package libraryManager.service.reservationManager;

import libraryManager.model.BookItem;
import libraryManager.model.Language;
import libraryManager.service.account.AccountCatalog;
import libraryManager.service.book.BookItemCatalog;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.*;

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
        BookItem book1 = new BookItem(1L, "Krzyżacy", "Sienkiewicz", "Zysk i Ska", 350, Language.POLISH, "aaa");
        given(bookItemCatalogMock.getRfidTagsFromCatalog()).willReturn(new HashSet<>(Arrays.asList("aaa")));
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
        BookItem book1 = new BookItem(1L, "Krzyżacy", "Sienkiewicz", "Zysk i Ska", 350, Language.POLISH, "aaa");
        given(bookItemCatalogMock.getRfidTagsFromCatalog()).willReturn(new HashSet<>(Arrays.asList("aaa")));
        BookReservationManager reservationManager = new BookReservationManager(accountCatalogMock, bookItemCatalogMock);


    }

    @Test
    public void testIsReservedForThisAccount() {
    }

    @Test
    public void testIsAllowed() {
    }

    @Test
    public void testCancelReservationIfOverDue() {
    }
}