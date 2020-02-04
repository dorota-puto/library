package libraryManager.entity;

import java.sql.Date;

public class ReservedBookInfoEntity {
    private Long ID;
    private String rfidTag;
    private Integer borrowerAccountID;
    private Date reservationDate;
    private Date dueDate;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getRfidTag() {
        return rfidTag;
    }

    public void setRfidTag(String rfidTag) {
        this.rfidTag = rfidTag;
    }

    public Integer getBorrowerAccountID() {
        return borrowerAccountID;
    }

    public void setBorrowerAccountID(Integer borrowerAccountID) {
        this.borrowerAccountID = borrowerAccountID;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
