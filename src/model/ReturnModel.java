package model;

import java.sql.Date;

public class ReturnModel {
	private String returnId;
	private String borrowId;
	private String stuId;
	private Date returnedAt;
	private int returnedQty;
	private int lateFine;
	private int totalFine;
	
	private String stuName;
	
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
	
	public Date getReturnedAt() {
		return returnedAt;
	}
	public void setReturnedAt(Date returnedAt) {
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
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	
	
}
