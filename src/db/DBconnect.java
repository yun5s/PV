package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

public class DBconnect {
	private Connection myConn= null;
	private Statement myStmt = null;
	private PreparedStatement pStmt = null;
	private ResultSet myRs = null;
	
	//DB connection 
	public DBconnect(){
		try {
			Class.forName("com.mysql.jdbc.Driver");

			//jdbc:mysql://address:port/yourdatabase
			myConn = DriverManager.getConnection();
			System.out.println("connected to database");

			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from Membership");
			
			}catch(Exception ex){
			System.out.println(" Error "+ ex);
		}
	}
	//getters
	public void getActKey() {
		try {
			String query = "select * from Membership";
			myRs = myStmt.executeQuery(query);
			while (myRs.next()) {
			    System.out.println(myRs.getString("ActKey"));
			   }
			
			}catch(Exception ex){
				System.out.println(ex);
		}
	}
	
	public Boolean checkLogin(String email, String pass) {
		try {
		        if (email != null && pass != null) {
	
		        	 String sql = "Select * from Membership Where email='" + email + "' and PassHash='" + pass + "'";
		             myRs = myStmt.executeQuery(sql);
		             if (myRs.next()) {
		            	 	return true;
		             } else {
		            	 	return false;
		             }
		         }
			
			}catch(Exception ex){
				System.out.println("does not exist");
		}
        return false;
    }

	
	//method for inserting data to the database
	public void insertData() {
		try {
			String query = "insert into Membersship " +
							" (FirstName,LastName,Email,PassHash,ActKey)"+
							" values('Brown', 'Dope','BBD', 'brown@mail.com','21323','Actt')";
			myStmt.executeUpdate(query);
			System.out.println("inserted");
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	//method for updating data to DB
	public void updateData(String email, String act){
		try {
			String query = "update Membership "
						+ " set ActKey = '" + act + "'"
						+ " where Email = '" + email + "'";
			myStmt.executeUpdate(query);
			System.out.println("update compelete");
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}

	public void updateAct(String email, String act){
		try {
			String query = "update Membership "
					+ " set ActKey = '" + act + "'"
					+ " where Email = '" + email + "'";
			myStmt.executeUpdate(query);
			System.out.println("update compelete");
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}


	public Boolean loggedIn(){
	    return false;
    }
	//method for deleting data from DB
	public void deleteData() {
		try {
			String query = "delete from Membership "
						+ " where LastName = 'Dope'";
			
			int rowsAffected = myStmt.executeUpdate(query);
			System.out.println("rows affected:" + rowsAffected);
			System.out.println("delete compelete");
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	
	public Boolean checkUser(String email) {
		try {
	        if (email != null) {

	        	 String sql = "Select * from Membership Where email='" + email + "'";
	             myRs = myStmt.executeQuery(sql);
	             if (myRs.next()) {
	            	 	JOptionPane.showMessageDialog(null, "Email already exist");
	            	 	return true;
	             } else {
	             }
	         }
		}catch(Exception ex){
			System.out.println(ex);
		}
			return false;
	}	
	
	public void check(String email, String password) {
		try {
			String query = "select Email,PassHash from Membership where Email='"+email+"'";
			myRs= myStmt.executeQuery(query);

				if(password.equals(myRs.getString("PassHash"))) {
					System.out.println(password);
				}else {
					System.out.println("what tf");
				}
			}catch(Exception ex){
				System.out.println(ex);
		}
	}
	public String getDate(String email) {
			try {
				String query = "select ExpiryDate from Membership where Email='" + email + "'";
				myRs = myStmt.executeQuery(query);
				while (myRs.next()) {
					String value = myRs.getString("ExpiryDate");
					return value;
				}

			} catch (Exception ex) {
				System.out.println(ex);
		}
		return null;

	}

//update-----------------------------------------------------------------------------------------------------------------------------------
	public String getValues(String email){
        try {
            try {
                String query ="select FirstName,LastName,Email,ActDate, PayType,ActKey from Membership where Email='"+email+"'";
                myRs = myStmt.executeQuery(query);
                while (myRs.next()) {
                    String value =
                    myRs.getString("FirstName")+
                            myRs.getString("LastName")+
                            myRs.getString("Email")+
                            myRs.getString("ActDate")+
                            myRs.getString("PayType")+
                            myRs.getString("ActKey");
                    return value;
                }

            }catch(Exception ex){
                System.out.println(ex);
            }

        }catch(Exception ex) {
            System.out.println(ex);
        }
        return null;

    }


	//Prepared statement to prevent SQL injection
	public void register(String fName,String lName, String email, String pHash,String type,int days) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar current = new GregorianCalendar();
		Calendar late = new GregorianCalendar();
		late.add(Calendar.DAY_OF_MONTH,days);

		try {
			String query = "insert into Membership " +
					" (FirstName,LastName,Email,PassHash,PayType,ActKey,ActDate,ExpiryDate,Activation)"+
					" values(?, ?, ?, ?, ?, '213',? ,?,?)";
			pStmt =  myConn.prepareStatement(query);
			pStmt.setString(1,fName);
			pStmt.setString(2,lName);
			pStmt.setString(3,email);
			pStmt.setString(4,pHash);
			pStmt.setString(5,type);
			pStmt.setString(6,formatter.format(current.getTime()));
			pStmt.setDate(7,new java.sql.Date(late.getTimeInMillis()));
			pStmt.setBoolean(8, true);
			pStmt.execute();
			System.out.println("prep compelete");
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}
	

	
	
}
	