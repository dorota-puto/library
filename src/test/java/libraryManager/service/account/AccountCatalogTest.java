package libraryManager.service.account;

import libraryManager.model.Account;
import libraryManager.model.AccountState;
import org.testng.annotations.Test;

import static libraryManager.model.AccountState.ACTIVE;
import static libraryManager.model.AccountState.SUSPENDED;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountCatalogTest {

    @Test
    public void findingAccountByIdTest() {
        //given
        AccountCatalog accountCatalog = new AccountCatalog();

        //when

        Account account1 = new Account(1L, "Pan Kleks", AccountState.ACTIVE);
        accountCatalog.add(account1);

        //then
        assertThat(accountCatalog.findById(1L)).isEqualTo(account1);

    }

    @Test
    public void suspendAccountTest() {
        //given
        AccountCatalog accountCatalog = new AccountCatalog();

        //when

        Account account1 = new Account(1L, "Pan Kleks", AccountState.ACTIVE);
        accountCatalog.add(account1);
        accountCatalog.suspend(1L);

        //then
        assertThat(accountCatalog.findById(1L).getState()).isEqualTo(SUSPENDED);
    }

    @Test
    public void unSuspendAccountTest() {
        //given
        AccountCatalog accountCatalog = new AccountCatalog();

        //when

        Account account1 = new Account(1L, "Pan Kleks", SUSPENDED);
        accountCatalog.add(account1);
        accountCatalog.unSuspend(1L);

        //then
        assertThat(accountCatalog.findById(1L).getState()).isEqualTo(ACTIVE);
    }
}