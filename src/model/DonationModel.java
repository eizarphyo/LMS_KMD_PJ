package model;

public class DonationModel {
	private String donationId;
	private String donatorId;
//	private String donatorName;
	private String date;
	public String getDonationId() {
		return donationId;
	}
	public void setDonationId(String donationId) {
		this.donationId = donationId;
	}
	public String getDonatorId() {
		return donatorId;
	}
	public void setDonatorId(String donatorId) {
		this.donatorId = donatorId;
	}
	
//	public String getDonatorName() {
//		return donatorName;
//	}
//	public void setDonatorName(String donatorName) {
//		this.donatorName = donatorName;
//	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}