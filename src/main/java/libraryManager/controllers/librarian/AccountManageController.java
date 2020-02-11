package libraryManager.controllers.librarian;

import libraryManager.entity.Account;
import libraryManager.repository.AccountRepository;
import libraryManager.service.account.AccountCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AccountManageController {

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/library/account")
    ResponseEntity<Void> newAccount(@RequestBody Account newAccount) {
        if (accountRepository.save(newAccount)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/library/accounts")
    List<Account> all() {
        return accountRepository.findAll();
    }

    @GetMapping("/library/account/{id}")
    ResponseEntity<Account> account(@PathVariable Long id) {
        return accountRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/library/account/{id}/suspend")
    ResponseEntity<Void> suspend(@PathVariable Long id) {
        if (accountRepository.updateActive(id,false)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/library/account/{id}/unsuspend")
    ResponseEntity<Void> unSuspend(@PathVariable Long id) {
        if (accountRepository.updateActive(id, true)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/library/account/{id}")
    void deleteAccount(@PathVariable Long id){
        accountRepository.deleteById(id);
    }
}
