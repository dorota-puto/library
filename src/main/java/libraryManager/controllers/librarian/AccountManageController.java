package libraryManager.controllers.librarian;

import libraryManager.model.Account;
import libraryManager.service.account.AccountCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountManageController {

    @Autowired
    private AccountCatalog accountCatalog;

    @PostMapping("/library/account")
    ResponseEntity<Void> newAccount(@RequestBody Account newAccount) {
        if (accountCatalog.add(newAccount)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/library/accounts")
    List<Account> all() {
        return accountCatalog.listAll();
    }

    @GetMapping("/library/account/{id}")
    Account findAccount (@PathVariable Long id) throws Exception {
        if (accountCatalog.findById(id) != null) {
            return accountCatalog.findById(id);
        } else throw new Exception();
    }

    @PutMapping("/library/account/{id}/suspend")
    ResponseEntity<Void> suspend(@PathVariable Long id) throws Exception {
        if (accountCatalog.suspend(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/library/account/{id}/unsuspend")
    ResponseEntity<Void> unSuspend(@PathVariable Long id) throws Exception {
        if (accountCatalog.unSuspend(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
