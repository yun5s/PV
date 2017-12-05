package db;

import at.gadermaier.argon2.Argon2;
import at.gadermaier.argon2.Argon2Factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import javax.swing.JOptionPane;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class DBconnect {
	private Connection myConn= null;
	private Statement myStmt = null;
	private PreparedStatement pStmt = null;
	private ResultSet myRs = null;

	public String dbDriver;
	public String dbHost;
	public String dbUser;
	public String dbPwd;

	//DB connection

    public Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName(dbDriver);
        return DriverManager.getConnection(dbHost, dbUser, dbPwd);
    }


	public DBconnect(){

		try {
			getDbInfo();
			Class.forName(dbDriver);

			myConn = DriverManager.getConnection(dbHost, dbUser, dbPwd);
			System.out.println("connected to database");

			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from Membership");
			}catch(Exception ex){
			System.out.println(" Error "+ ex);
		}
	}
    //----------------------------------<CONNECTION>------------------------------------------------------------->

    // connection
    public void getDbInfo(){
        Properties pro = new Properties();
        InputStream input = null;

        try{
            input = new FileInputStream("resources/config.properties");
            pro.load(input);
            StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
            decryptor.setPassword("mySecretPassword");

            dbDriver = decryptor.decrypt(pro.getProperty("DB_DRIVER"));
            dbHost =decryptor.decrypt(pro.getProperty("DB_HOST"));
            dbUser =decryptor.decrypt(pro.getProperty("DB_USER"));
            dbPwd =decryptor.decrypt(pro.getProperty("DB_PWD"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //----------------------------------<GETTERs>------------------------------------------------------------->

    //check the activation status
    public int getActStatus(String email){

        try {
            String query = "select Activation from Membership where email ='"+email+"' ";
            myRs = myStmt.executeQuery(query);
            while (myRs.next()) {
                return myRs.getInt("Activation");
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }
    public static void hi(){
        System.out.println("hello you ");
    }
    // get the activation key
    public String getActKey(String email) {
        try {
            String query = "select ActKey from Membership where email ='"+email+"' ";
            myRs = myStmt.executeQuery(query);
            while (myRs.next()) {
                return myRs.getString("ActKey");
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
        return null;
    }

    // check the if key is valid
    public int getKeyStatus(String key){
        try {
            String query = "select Activation from Membership where ActKey ='"+key+"' ";
            myRs = myStmt.executeQuery(query);
            while (myRs.next()) {
                return myRs.getInt("Activation");
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }

    // get the salt for password encryption
    public String getSalt(String email){
        try {
            String query = "select Salt from Membership where email ='"+email+"' ";
            myRs = myStmt.executeQuery(query);
            while (myRs.next()) {
                return myRs.getString("Salt");
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
        return null;
    }

    //get
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

    public String getId(String email){
        try {
            String query = "select ID from Membership where email ='"+email+"' ";
            myRs = myStmt.executeQuery(query);
            while (myRs.next()) {
                return myRs.getString("ID");
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
        return null;
    }


    //----------------------------------<SETTER>------------------------------------------------------------->

    //Prepared statement to prevent SQL injection
    //set data into the database
    public void register(String fName,String lName, String email, String pHash,String type,int days) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar current = new GregorianCalendar();
        Calendar late = new GregorianCalendar();
        late.add(Calendar.DAY_OF_MONTH,days);

//TODO remove the activation key, set to null
        try {
            String query = "insert into Membership " +
                    " (FirstName,LastName,Email,PassHash,PayType,ActKey,ActDate,ExpiryDate,Activation)"+
                    " values(?, ?, ?, ?, ?, 0,? ,?,?)";
            pStmt =  myConn.prepareStatement(query);
            pStmt.setString(1,fName);
            pStmt.setString(2,lName);
            pStmt.setString(3,email);
            pStmt.setString(4,pHash);
            pStmt.setString(5,type);
            pStmt.setString(6,formatter.format(current.getTime()));// TODO need to remove for author change
            pStmt.setDate(7,new java.sql.Date(late.getTimeInMillis()));
            pStmt.setBoolean(8, true);
            pStmt.execute();
            System.out.println("prep compelete");
        }catch(Exception ex) {
            System.out.println(ex);
        }
    }

    //----------------------------------<UPDATES>------------------------------------------------------------->

    // TODO check the DATE and Converted first, if reached limite, call this function
    public void markExpired (String email){
        try {
            String query = "update Membership "
                    + " set Act = 0 where Email = '" + email + "'";
            myStmt.executeUpdate(query);
            System.out.println("update compelete");
        }catch(Exception ex) {
            System.out.println(ex);
        }
    }
    public void updateSalt(String email, String salt){
        try {
            String query = "update Membership "
                    + " set Salt = '" + salt + "'"
                    + " where Email = '" + email + "'";
            myStmt.executeUpdate(query);
            System.out.println("update compelete");
        }catch(Exception ex) {
            System.out.println(ex);
        }
    }

    public void updateAct(){
        try {
            String query = "update Membership "
                    + " set Salt = '" + "0" + "'"
                    + " where Email = '" + "ss" + "'";
            myStmt.executeUpdate(query);
            System.out.println("update compelete");
        }catch(Exception ex) {
            System.out.println(ex);
        }
    }
    //----------------------------------<CHECKERS>------------------------------------------------------------->

    public Boolean checkKey(String key){
        try {
            String query = "select * from Membership where ActKey ='"+key+"' ";
            myRs = myStmt.executeQuery(query);
            while (myRs.next()) {
                return true;
            }
        }catch(Exception ex){
            System.out.println(ex);
        }

        return false;
    }

    public Boolean checkKeymail(String key, String email){
        try {
            String query = "select * from Membership where ActKey ='"+key+"' and Email ='"+email+"' ";
            myRs = myStmt.executeQuery(query);
            while (myRs.next()) {
                return true;
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
        return false;
    }

    public Boolean checkLogin(String email, String pass) {
        char[] password = pass.toCharArray();

        // Generate salt
        String salt = getSalt(email);
        // Hash password - Builder pattern
        String hash = Argon2Factory.create()
                .setIterations(2)
                .setMemory(14)
                .setParallelism(1)
                .hash(password, salt);
        try {
            if (email != null && pass != null) {

                String sql = "Select * from Membership Where email='" + email + "' and PassHash='" + hash + "'";
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



    //Result set Function
	//Execute Statement function
	// a bunch of setters
	public void setActivate(String email){


	}

	//a bunch of getters
	
	//EXAMPLES
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

	public void updatePass(String email,String pass){
		try {
			String query = "update Membership "
					+ " set PassHash = '" + pass + "'"
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
}
	