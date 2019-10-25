package libraryManager.service.LendingManager;

import libraryManager.model.*;
import libraryManager.service.Account.ISearchAccountCatalog;
import libraryManager.service.Book.ISearchBookItemCatalog;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.time.LocalDate;

public class BookLendingManagerTest {

    @Test
    public void lendTest() {
        //given
        ISearchAccountCatalog accountCatalogMock = mock(ISearchAccountCatalog.class);
        ISearchBookItemCatalog bookCatalogMock = mock(ISearchBookItemCatalog.class);
        BookItem book1 = new BookItem(1L, "Krzyżacy", "Sienkiewicz", "Zysk i Ska", 350, Language.POLISH, "aaa");
        BookItem book2 = new BookItem(1L, "Krzyżacy", "Sienkiewicz", "Zysk i Ska", 350, Language.POLISH, "bbb");
        BookItem book3 = new BookItem(2L, "Pan Tadeusz", "Mickiewicz", "Zysk i Ska", 200, Language.POLISH, "ccc");

        BookLendingManager bookLendingManager = new BookLendingManager(accountCatalogMock, bookCatalogMock);
        given(accountCatalogMock.findById(111L)).willReturn(new Account(111L, "Edmund Elefant", AccountState.ACTIVE));
        given(bookCatalogMock.findByIsbn(1L)).willReturn(Arrays.asList(book1, book2));

        LentBookInfo expectedLentBookInfo = new LentBookInfo("aaa", 111L, LocalDate.of(2019, 10, 25), LocalDate.of(2019, 11, 24));

        //when
        LentBookInfo lentBookInfo = bookLendingManager.lend(111L, 1L);

        //then
        assertThat(lentBookInfo).isEqualTo(expectedLentBookInfo);
    }

}