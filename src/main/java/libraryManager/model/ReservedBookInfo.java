package libraryManager.model;

import java.time.LocalDate;
import java.util.Objects;

public class ReservedBookInfo {
    private String rfidTag;
    private Long borrowerAccountId;
    private LocalDate reservationDate;
    private LocalDate dueDate;

    public ReservedBookInfo(String rfidTag, Long borrowerAccountId, LocalDate reservationDate, LocalDate dueDate) {
        this.rfidTag = rfidTag;
        this.borrowerAccountId = borrowerAccountId;
        this.reservationDate = reservationDate;
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

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LentBookInfo that = (LentBookInfo) o;
        return getRfidTag().equals(that.getRfidTag()) &&
                getBorrowerAccountId().equals(that.getBorrowerAccountId()) &&
                getReservationDate().equals(that.getBorrowDate()) &&
                getDueDate().equals(that.getDueDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRfidTag(), getBorrowerAccountId(), getReservationDate(), getDueDate());
    }
}
