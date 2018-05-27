package com.CSVToDatabase;

public class Student {
	
	private String firstname;
	private String lastname;
	private String emailId;
	private String gender;
	private String image;
	private String creditCard;
	private String amount;
	private boolean column8;
	private boolean column9;
	private String city;
	
	public Student(String firstname,String lastname,String emailId,
	 String gender,String image,String creditCard,String amount,
	 boolean column8,boolean column9,String city) {
		
		super();
		this.firstname=firstname;
		this.lastname=lastname;
		this.emailId= emailId;
		this.gender=gender;
		this.image=image;
		this.creditCard=creditCard;
		this.amount=amount;
		this.column8=column8;
		this.column9=column9;
		this.city=city;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public boolean isColumn8() {
		return column8;
	}

	public void setColumn8(boolean column8) {
		this.column8 = column8;
	}

	public boolean isColumn9() {
		return column9;
	}

	public void setColumn9(boolean column9) {
		this.column9 = column9;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}	
	

}
