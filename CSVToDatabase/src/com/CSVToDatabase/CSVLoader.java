package com.CSVToDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CSVLoader {

	private static char DEFAULT_SEPARATOR = ',';
	private static char DEFAULT_QUOTE = '"';
	private static String JDBC_CONNECTION_URL = 
			"jdbc:sqlite:test.db";

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 String csvFile = "/home/kunal/Softwares/Eclipse/eclipse/git/CSVToDatabase/ms3Interview.csv";
	     boolean HeadRowExists = true;
	     int AcceptedNumberofColumns = 10;
	     int IncorrectRecords = 0;
	     int TotalRecords = 0;

	      //Create List for holding Student objects
            List<Student> StuList = new ArrayList<Student>();
            
            Pattern alphabets = Pattern.compile("[^a-zA-Z]");
            List<String> line = new ArrayList<String>();
            
            try {
				Scanner sc=new Scanner(new File(csvFile));
				
				if(HeadRowExists) {
	            	String HeadRow = sc.nextLine();
	         
	            	if(HeadRow==null || HeadRow.isEmpty()) {
	            		throw new FileNotFoundException(
	        					"No columns defined in given CSV file." +
	        					"Please check the CSV file format.");
	            	}
	            }
				
				
				while(sc.hasNext()) {
					line = 	parseLine(sc.nextLine(), DEFAULT_SEPARATOR, DEFAULT_QUOTE);
					TotalRecords++;
					
					if(!line.isEmpty() && line.size() == AcceptedNumberofColumns) {
						if(alphabets.matcher(line.get(5)).find()) {
							Student stu = new Student(line.get(0),
		                    		line.get(1),line.get(2),line.get(3),
		                    		line.get(4),line.get(5),line.get(6),Boolean.parseBoolean(line.get(7)),Boolean.parseBoolean(line.get(8)),line.get(9));

		                    StuList.add(stu);
						}
						else
						{
							IncorrectRecords++;
						}
					}
					else
					{
						IncorrectRecords++;
						
						System.out.println("Line Size "+line.size());
					}
				}
				
				LoadCSVintoSQLite(StuList);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        
	        System.out.println("Following are the statistics :\n#"+
	        		TotalRecords+" of records received.\n#"+
	        		StuList.size()+" of records processed.\n#"+
	        		IncorrectRecords+" of records failed.\n");
	}

	private static void LoadCSVintoSQLite(List<Student> StuList) {
		// TODO Auto-generated method stub
		
		Connection connection = null;
		boolean tableExists = true;
		boolean truncateTable = true;
		
		try {
			//Class.forName("org.sqlite.JDBC");
			
			connection = DriverManager.getConnection(JDBC_CONNECTION_URL);
			
			/*if(tableExists) {
				connection.createStatement().execute("drop table student");
				tableExists = false;
			}*/
			
			if(tableExists != true) {
				connection.createStatement().execute("create table student(firstname, lastname, emailId, gender, image, creditCard, amount, column8 INTEGER DEFAULT 0, column9 INTEGER DEFAULT 0, city)");
				tableExists = true;
				truncateTable = false;
			}
			
			if(truncateTable == true) {
				connection.createStatement().execute("delete from student");
			}
			
			PreparedStatement pstmt =
					connection.prepareStatement("insert into student (firstname, lastname, emailId, gender, image, creditCard, amount, column8, column9, city) values (?, ?, ?, ?, ?, ?, ?, ?,?,?)");
			connection.setAutoCommit(false);
			for(Student e : StuList)
            {
				pstmt.setString(1,e.getFirstname());
				pstmt.setString(2,e.getLastname());
				pstmt.setString(3,e.getEmailId());
				pstmt.setString(4,e.getGender());
				pstmt.setString(5,e.getImage());
				pstmt.setString(6,e.getCreditCard());
				pstmt.setString(7,e.getAmount());
				pstmt.setBoolean(8, e.isColumn8());;
				pstmt.setBoolean(9, e.isColumn9());;
				pstmt.setString(10,e.getCity());
				
				pstmt.addBatch();
            }
			int[] result=pstmt.executeBatch();
			System.out.println("The number of rows inserted: "+ result.length);
			connection.commit();
			
			System.out.println("Result of SELECT\n");
			
			ResultSet rs = connection.createStatement().executeQuery("select * from student");
			
			while(rs.next()) {
				String firstname = rs.getString(1);
				String lastname = rs.getString(2);
				String emailId = rs.getString(3);
				String gender = rs.getString(4);
				String image = rs.getString(5);
				String creditCard = rs.getString(6);
				String amount = rs.getString(7);
				boolean col8 = rs.getBoolean(8);
				boolean col9 = rs.getBoolean(9);
				String city = rs.getString(10);
				
				System.out.println(firstname+"\t"+lastname+"\t"+emailId+"\t"+gender+"\t"+creditCard+"\t"
				+amount+"\t"+col8+"\t"+col9+"\t"+city);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static List<String> parseLine(String cvsLine, char separators, char customQuote) {

        List<String> result = new ArrayList<>();

        //if empty, return!
        if (cvsLine == null && cvsLine.isEmpty()) {
            return result;
        }

        if (customQuote == ' ') {
            customQuote = DEFAULT_QUOTE;
        }

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {

            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == customQuote) {

                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == separators) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }

}
