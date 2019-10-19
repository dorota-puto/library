package libraryManager.model;

import java.util.Date;

public class LentBookInfo {
    private String rfidTag;
    private Long borrowerAccountId;
    private Date borrowDate;
    private Date dueDate;

    public String getRfidTag() { return rfidTag; }

    public void setRfidTag(String rfidTag) { this.rfidTag = rfidTag; }

    public Long getBorrowerAccountId() { return borrowerAccountId; }

    public void setBorrowerAccountId(Long borrowerAccountId) { this.borrowerAccountId = borrowerAccountId; }

    public Date getBorrowDate() { return borrowDate; }

    public void setBorrowDate(Date borrowDate) { this.borrowDate = borrowDate; }

    public Date getDueDate() { return dueDate; }

    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
}
