package model;

public class ReturnModel {
	private String returnId;
	private String borrowId;
	private String stuId;
	private String returnedAt;
	private int returnedQty;
	private int lateFine;
	private int totalFine;
	public String getReturnId() {
		return returnId;
	}
	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}
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
	public String getReturnedAt() {
		return returnedAt;
	}
	public void setReturnedAt(String returnedAt) {
		this.returnedAt = returnedAt;
	}
	public int getReturnedQty() {
		return returnedQty;
	}
	public void setReturnedQty(int returnedQty) {
		this.returnedQty = returnedQty;
	}
	public int getLateFine() {
		return lateFine;
	}
	public void setLateFine(int lateFine) {
		this.lateFine = lateFine;
	}
	public int getTotalFine() {
		return totalFine;
	}
	public void setTotalFine(int totalFine) {
		this.totalFine = totalFine;
	}
	
	
}