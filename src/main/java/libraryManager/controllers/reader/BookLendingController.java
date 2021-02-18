package libraryManager.controllers.reader;

import libraryManager.dto.LendRequestDTO;
import libraryManager.dto.ReturnRequestDTO;
import libraryManager.model.LentBookInfo;
import libraryManager.service.lendingManager.BookLendingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookLendingController {

    @Autowired
    private BookLendingManager bookLendingManager;

    @PostMapping("/library/lend")
    ResponseEntity<LentBookInfo> info(@RequestBody LendRequestDTO newDTO) {
        LentBookInfo lentBookInfo= bookLendingManager.lend(newDTO.getAccountId(),newDTO.getIsbn());
        if (lentBookInfo!=null) {
            return new ResponseEntity<>(lentBookInfo, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/library/return")
    ResponseEntity<Void> ret(@RequestBody ReturnRequestDTO newDTO) {
        if (bookLendingManager.returnBook(newDTO.getAccountId(),newDTO.getRfidTag())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
