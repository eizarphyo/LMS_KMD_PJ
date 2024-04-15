package model;


import java.sql.Date;
import java.time.LocalDate;
//import java.util.Date;

public class BorrowModel {
	private String borrowId;
	private String stuId;
//	private String borrowAt;
	private Date borrowAt;
	private int borrowQty;
	private int qtyToBeReturned;
	private boolean isAllReturned;

	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	

	public Date getBorrowAt() {
		return borrowAt;
	}

	public void setBorrowAt(Date borrowAt) {
		this.borrowAt = borrowAt;
	}

	// public String getBorrowAt() {
//		return borrowAt;
//	}
//	public void setBorrowAt(String borrowAt) {
//		this.borrowAt = borrowAt;
//	}
	public int getBorrowQty() {
		return borrowQty;
	}

	public void setBorrowQty(int borrowQty) {
		this.borrowQty = borrowQty;
	}

	public int getQtyToBeReturned() {
		return qtyToBeReturned;
	}

	public void setQtyToBeReturned(int qtyToBeReturned) {
		this.qtyToBeReturned = qtyToBeReturned;
	}

	public boolean isAllReturned() {
		return isAllReturned;
	}

	public void setAllReturned(boolean isAllReturned) {
		this.isAllReturned = isAllReturned;
	}

}
