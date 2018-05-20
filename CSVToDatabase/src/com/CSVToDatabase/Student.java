package com.CSVToDatabase;

public class Student {
	
	private int studentId;
	private String firstname;
	private String lastname;
	private String course;
	private int greScore;
	private int toeflScore;
	private String countryCode;
	private String country;
	
	public Student(int studentId,String firstname,String lastname,
	 String course,int greScore,int toeflScore,String countryCode,
	 String country) {
		
		super();
		this.studentId= studentId;
		this.firstname=firstname;
		this.lastname=lastname;
		this.course=course;
		this.greScore=greScore;
		this.toeflScore=toeflScore;
		this.countryCode=countryCode;
		this.country=country;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
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

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public int getGreScore() {
		return greScore;
	}

	public void setGreScore(int greScore) {
		this.greScore = greScore;
	}

	public int getToeflScore() {
		return toeflScore;
	}

	public void setToeflScore(int toeflScore) {
		this.toeflScore = toeflScore;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	

}
