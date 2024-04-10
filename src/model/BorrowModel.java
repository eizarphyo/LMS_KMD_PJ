package model;

public class BorrowModel {
	private String borrowId;
	private String stuId;
	private String borrowAt;
	private int borrowQty;
	
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
	public String getBorrowAt() {
		return borrowAt;
	}
	public void setBorrowAt(String borrowAt) {
		this.borrowAt = borrowAt;
	}
	public int getBorrowQty() {
		return borrowQty;
	}
	public void setBorrowQty(int borrowQty) {
		this.borrowQty = borrowQty;
	}
	
	
	
}
