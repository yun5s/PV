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
        String resourceName = "config.properties"; // could also be a constant
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties pro = new Properties();
        try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
            pro.load(resourceStream);

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
            String query = "select Activation from Membership where email =?";
            pStmt =  myConn.prepareStatement(query);
            pStmt.setString(1,email);
            pStmt.execute();
            while (myRs.next()) {
                return myRs.getInt("Activation");
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }

    // get the activation key
    public String getActKey(String email) {
        try {
            String query = "select ActKey from Membership where email =?";
            pStmt =  myConn.prepareStatement(query);
            pStmt.setString(1,email);
            pStmt.execute();
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
            String query = "select Activation from Membership where ActKey =?";
            pStmt =  myConn.prepareStatement(query);
            pStmt.setString(1,key);
            pStmt.execute();
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
            String query = "select Salt from Membership where email =?";
            pStmt =  myConn.prepareStatement(query);
            pStmt.setString(1,email);
            pStmt.execute();
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
            String query ="select FirstName,LastName,Email,ActDate,ActKey from Membership where Email=?";
            pStmt =  myConn.prepareStatement(query);
            pStmt.setString(1,email);
            pStmt.execute();
            while (myRs.next()) {
                String value =
                        myRs.getString("FirstName")+
                        myRs.getString("LastName")+
                        myRs.getString("Email")+
                        myRs.getString("ActDate")+
                        myRs.getString("ActKey");
                return value;
                }
        }catch(Exception ex){
            System.out.println(ex);
        }
        return null;
    }

    public String getId(String email){
        try {
            String query = "select ID from Membership where email =?";
            pStmt =  myConn.prepareStatement(query);
            pStmt.setString(1,email);
            pStmt.execute();
            while (myRs.next()) {
                return myRs.getString("ID");
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
        return null;
    }

    public String getCurrent(String email){
        try {
            String query = "select ID from Membership where email =?";
            pStmt =  myConn.prepareStatement(query);
            pStmt.setString(1,email);
            pStmt.execute();
            while (myRs.next()) {
                return myRs.getString("ID");
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
        return null;
    }

    public int getLimit(String email){
        try {
            String query = "select ConvLimit from Membership where email =? ";
            pStmt =  myConn.prepareStatement(query);
            pStmt.setString(1,email);
            pStmt.execute();
            while (myRs.next()) {
                return myRs.getInt("ConvLimit");
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }
    public int getCount(String email){
        try {
            String query = "select CurrenCount from Membership where email =?";
            pStmt =  myConn.prepareStatement(query);
            pStmt.setString(1,email);
            pStmt.execute();
            while (myRs.next()) {
                return myRs.getInt("CurrenCount");
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
        return 0;

    }

    //----------------------------------<SETTER>------------------------------------------------------------->



    //Prepared statement to prevent SQL injection
    //set data into the database
    public void register(String fName,String lName, String email,String company, String pHash,int days,int amount) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar current = new GregorianCalendar();
        Calendar late = new GregorianCalendar();
        late.add(Calendar.DAY_OF_MONTH,days);

        //TODO remove the activation key, set to null
        try {
            String query = "insert into Membership " +
                    " (FirstName,LastName,Email,PassHash,AmountType,ActKey,ActDate,Activation,Salt,ConvLimit,Company)"+
                    " values(?, ?, ?, ?, 0, 0,?,?,0,?,?)";
            pStmt =  myConn.prepareStatement(query);
            pStmt.setString(1,fName);
            pStmt.setString(2,lName);
            pStmt.setString(3,email);
            pStmt.setString(4,pHash);
            pStmt.setString(5,formatter.format(current.getTime()));// TODO need to remove for author change
            /*pStmt.setDate(6,new java.sql.Date(late.getTimeInMillis()));*/
            pStmt.setBoolean(6, true);
            pStmt.setInt(7,amount);
            pStmt.setString(8,company);

            pStmt.execute();
            System.out.println("Registration completed");
        }catch(Exception ex) {
            System.out.println(ex);
        }
    }

    //----------------------------------<UPDATES>------------------------------------------------------------->



//update count
    public void sendCount(int count, String email1) {
        try {
            String query = "update  Membership set CurrenCount =? where email =?";
            pStmt =  myConn.prepareStatement(query);
            pStmt.setInt(1,count);
            pStmt.setString(2,email1);

            pStmt.executeUpdate();
            System.out.println("count"+count +" send  compelete");

    }catch (Exception ex){
            System.out.println(ex);
        }
    }
    // TODO check the DATE and Converted first, if reached limite, call this function
    public void markExpired (String email){
        try {
            String query = "update Membership "
                    + " set Act = 0 where Email=?";
            pStmt =  myConn.prepareStatement(query);
            pStmt.setString(1,email);
            pStmt.executeUpdate();
            System.out.println("Expiration updated");
        }catch(Exception ex) {
            System.out.println(ex);
        }
    }
    public void updateSalt(String email, String salt){
        try {
            String query = "update Membership "
                    + " set Salt = ?"
                    + " where Email = ?";
            pStmt =  myConn.prepareStatement(query);
            pStmt.setString(1,salt);
            pStmt.setString(2,email);
            pStmt.executeUpdate();
            System.out.println("Salt updated ");
        }catch(Exception ex) {
            System.out.println(ex);
        }
    }
    public void updateFirstname(int id, String firstname){
        try {
            String query = "update Membership "
                    + " set FirstName = ?"
                    + " where ID = ?";
            pStmt =  myConn.prepareStatement(query);
            pStmt.setString(1,firstname);
            pStmt.setInt(2,id);
            pStmt.executeUpdate();

            System.out.println("Firstname updated ");
        }catch(Exception ex) {
            System.out.println(ex);
        }
    }

    public void updateCompany(int id, String company){
        try {
            String query = "update Membership "
                    + " set Company = ? "
                    + " where ID = ?";
            pStmt =  myConn.prepareStatement(query);
            pStmt.setInt(1,id);
            pStmt.setString(2,company);
            System.out.println("Company updated");
        }catch(Exception ex) {
            System.out.println(ex);
        }
    }


    public void updateStatus(int id, int status){
        try {
            String query = "update Membership "
                    + " set Activation = ?"
                    + " where ID = ?";
            pStmt =  myConn.prepareStatement(query);
            pStmt.setInt(1,status);
            pStmt.setInt(2,id);
            pStmt.executeUpdate();
            System.out.println("Status updated");
        }catch(Exception ex) {
            System.out.println(ex);
        }
    }


    public void updateSurname(int id, String surname){
        try {
            String query = "update Membership "
                    + " set LastName = ?"
                    + " where ID = ?";
            pStmt =  myConn.prepareStatement(query);
            pStmt.setString(1,surname);
            pStmt.setInt(2,id);
            pStmt.executeUpdate();
            System.out.println("Surname updated");
        }catch(Exception ex) {
            System.out.println(ex);
        }
    }

    public void updateEmail(int id, String email){
        try {
            String query = "update Membership "
                    + " set Email = ?"
                    + " where ID = ?";
            pStmt =  myConn.prepareStatement(query);
            pStmt.setString(1,email);
            pStmt.setInt(2,id);
            pStmt.executeUpdate();
            System.out.println("Email updated");
        }catch(Exception ex) {
            System.out.println(ex);
        }
    }

    public void updateLimit(int id, String Limit){
        try {
            String query = "update Membership "
                    + " set ConvLimit = ?"
                    + " where ID = ?";
            pStmt =  myConn.prepareStatement(query);
            pStmt.setString(1, Limit);
            pStmt.setInt(2,id);
            pStmt.executeUpdate();
            System.out.println("Limit Updated");
        }catch(Exception ex) {
            System.out.println(ex);
        }
    }

    public void updateCurrentCount(int id, int count){
        try {
            String query = "update Membership "
                    + " set CurrenCount = ?"
                    + " where ID =?";
            pStmt =  myConn.prepareStatement(query);
            pStmt.setInt(1,count);
            pStmt.setInt(2,id);
            pStmt.executeUpdate();
            System.out.println("Current count Updated");
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
            System.out.println("Activation updated");
        }catch(Exception ex) {
            System.out.println(ex);
        }
    }
    //----------------------------------<CHECKERS>------------------------------------------------------------->


    public Boolean checkRemaining(String email){
        if(getCount(email)>getLimit(email)){
            System.out.println("more ");
            return false;
        }
        return true;
    }

    public int remaining(String email){
        int k = getLimit(email)-getCount(email);
        return k;
    }

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

                String query = "Select * from Membership Where email=? and PassHash=?";
                pStmt =  myConn.prepareStatement(query);
                pStmt.setString(1,email);
                pStmt.setString(2,pass);
                pStmt.executeUpdate();                if (myRs.next()) {
                    return true;
                } else {
                    System.out.println("Email or password is not valid");
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
                    return true;

                } else {
                    System.out.println("Good to good, Email not Registered yet");

                    return false;
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

	//a bunch of getters

	
	//method for updating data to DB
	public void updateData(String email, String act){
		try {
			String query = "update Membership "
						+ " set ActKey = '" + act + "'"
						+ " where Email = '" + email + "'";
			myStmt.executeUpdate(query);
			System.out.println("Activation Key Updated");
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

	public void deleteData(int id) {
		try {
			String query = "delete from Membership "
						+ " where ID = '" + id + "'";
			
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
	