package libraryManager.model;

import java.time.LocalDate;
import java.util.Date;

public class LentBookInfo {
    private String rfidTag;
    private Long borrowerAccountId;
    private LocalDate borrowDate;
    private LocalDate dueDate;

    public LentBookInfo(String rfidTag, Long borrowerAccountId, LocalDate borrowDate, LocalDate dueDate) {
        this.rfidTag = rfidTag;
        this.borrowerAccountId = borrowerAccountId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public String getRfidTag() {
        return rfidTag;
    }

    public void setRfidTag(String rfidTag) {
        this.rfidTag = rfidTag;
    }

    public Long getBorrowerAccountId() {
        return borrowerAccountId;
    }

    public void setBorrowerAccountId(Long borrowerAccountId) {
        this.borrowerAccountId = borrowerAccountId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
