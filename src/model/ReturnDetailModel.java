package model;

public class ReturnDetailModel {
	private String returnId;
	private String bookId;
	private boolean isDamaged;
	private int damageFees;
	public String getReturnId() {
		return returnId;
	}
	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public boolean isDamaged() {
		return isDamaged;
	}
	public void setDamaged(boolean isDamaged) {
		this.isDamaged = isDamaged;
	}
	public int getDamageFees() {
		return damageFees;
	}
	public void setDamageFees(int damageFees) {
		this.damageFees = damageFees;
	}
	
	
}
