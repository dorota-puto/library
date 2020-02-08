package libraryManager.config;

import libraryManager.repository.AccountRepository;
import libraryManager.service.account.AccountCatalog;
import libraryManager.service.book.BookItemCatalog;
import libraryManager.service.historyManager.HistoryManager;
import libraryManager.service.lendingManager.BookLendingManager;
import libraryManager.service.reservationManager.BookReservationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(name = "bookItemCatalog")
    public BookItemCatalog bookItemCatalog() {
        return new BookItemCatalog();
    }

    @Bean(name="accountCatalog")
    public AccountCatalog accountCatalog(AccountRepository accountRepository){
        return new AccountCatalog(accountRepository);
    }

    @Bean(name="bookLendingManager")
    public BookLendingManager bookLendingManager(AccountCatalog accountCatalog, BookItemCatalog bookItemCatalog){
        BookReservationManager reservationManager= new BookReservationManager(accountCatalog,bookItemCatalog);
        return new BookLendingManager(accountCatalog, bookItemCatalog, new HistoryManager(), reservationManager);
    }
}

