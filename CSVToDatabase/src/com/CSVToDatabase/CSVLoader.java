package com.CSVToDatabase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sqlite.util.StringUtils;

public class CSVLoader {

	private static String JDBC_CONNECTION_URL = 
			"jdbc:sqlite:test.db";

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 String csvFile = "/home/kunal/Softwares/Eclipse/eclipse/git/CSVToDatabase/Student.csv";
	        BufferedReader br = null;
	        String line = "";
	        String cvsSplitBy = ",";
	        boolean HeadRowExists = true;
	        int AcceptedNumberofColumns = 8;
	        int IncorrectRecords = 0;
	        String[] student = null;
	      //Create List for holding Employee objects
            List<Student> StuList = new ArrayList<Student>();
	        
	        try {
				br = new BufferedReader(new FileReader(csvFile));
				
				           
	            if(HeadRowExists) {
	            	String HeadRow = br.readLine();
	         
	            	if(HeadRow==null || HeadRow.isEmpty()) {
	            		throw new FileNotFoundException(
	        					"No columns defined in given CSV file." +
	        					"Please check the CSV file format.");
	            	}
	            }
	            
				 while ((line = br.readLine()) != null) {

		                // use comma as separator
		                student = line.split(cvsSplitBy);
		                
		                if(student.length > 0 && student.length == AcceptedNumberofColumns)
		                {
		                    //Save the employee details in Employee object
		                    Student stu = new Student(Integer.parseInt(student[0]),
		                    		student[1],student[2],student[3],
		                            Integer.parseInt(student[4]),Integer.parseInt(student[5]),student[6],student[7]);
		                    StuList.add(stu);
		                }
		                else {
		                	
		                	IncorrectRecords++;
		                }

		                //System.out.println("Country [code= " + student[4] + " , name=" + student[5] + "]");   
				 }
				 
				   /*//Lets print the Employee List
	                for(Student e : StuList)
	                {
	                    System.out.println(e.getStudentId()+"	"+e.getFirstname()+"	"
	                    		+e.getLastname()+"	"+e.getCourse()+"	"+e.getGreScore()
	                    		+"	"+e.getToeflScore()+"	"+e.getCountryCode()+" "+e.getCountry());
	                }*/
	                
	                LoadCSVintoDatabase(StuList);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        
	        
	        System.out.println("Following are the statistics :\n#"+
	        		student.length+" of records received.\n#"+
	        		StuList.size()+" of records processed.\n#"+
	        		IncorrectRecords+" of records failed.");
	}

	private static void LoadCSVintoDatabase(List<Student> stuList) {
		// TODO Auto-generated method stub
		
		Connection connection = null;
		boolean tableExists = true;
		boolean truncateTable = true;
		
		try {
			//Class.forName("org.sqlite.JDBC");
			
			connection = DriverManager.getConnection(JDBC_CONNECTION_URL);
			
			if(tableExists != true) {
				connection.createStatement().execute("create table student(studentId, firstname, lastname, course, greScore, toeflScore, countryCode, country)");
			}
			
			if(truncateTable == true) {
				connection.createStatement().execute("delete from student");
			}
				
			
			PreparedStatement stmt =
					connection.prepareStatement("insert into student (studentId, firstname, lastname, course, greScore, toeflScore, countryCode, country) values (?, ?, ?, ?, ?, ?, ?, ?)");
			
			for(Student e : stuList)
            {
				
				stmt.setInt(1, e.getStudentId());
				stmt.setString(2, e.getFirstname());
				stmt.setString(3, e.getLastname());
				stmt.setString(4, e.getCourse());
				stmt.setInt(5, e.getGreScore());
				stmt.setInt(6, e.getToeflScore());
				stmt.setString(7, e.getCountryCode());
				stmt.setString(8, e.getCountry());
				
				stmt.executeUpdate();
            }
			
			System.out.println("Result of SELECT\n");
			
			ResultSet rs = connection.createStatement().executeQuery("select * from student");
			
			while(rs.next()) {
				int studentId = rs.getInt(1);
				String firstname = rs.getString(2);
				String lastname = rs.getString(3);
				String course = rs.getString(4);
				int greScore = rs.getInt(5);
				int toeflScore = rs.getInt(6);
				String countryCode = rs.getString(7);
				String country = rs.getString(8);
				
				System.out.println(studentId+"\t"+firstname+"\t"+lastname+"\t"+course+"\t"+greScore+"\t"
				+toeflScore+"\t"+countryCode+"\t"+country);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
