package Test;

import db.DBconnect;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.*;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import static org.junit.Assert.*;

public class DBconnectTest {

    private static DBconnect db = new DBconnect();
    private static Connection myConn= null;
    private static Statement myStmt = null;
    private static PreparedStatement pStmt = null;
    private static ResultSet myRs = null;

    private static int tid;
    private static String fname;
    private static String lname;
    private static String email;
    private static String company;
    private static String pHash;
    private static int days;
    private static int amount;

   // private static String injection ="+ password +";

    @BeforeClass
    public static void register() {
        fname = "Jonny";
        lname= "smith";
        email= "JS1@live.com";
        company="Jonny.Ltd";
        pHash="666";
        days=6;
        amount=6;
        db.register(fname,lname,email,company,pHash,amount);
        //need to check if the user is created , then use tear down to delete the created user
        assertNotNull(db.getId("JS1@live.com"));
        tid = Integer.parseInt(db.getId("JS1@live.com"));
        assertEquals(db.getFirstname(tid),"Jonny");
        assertEquals(db.getSurname(tid),"smith");
        assertEquals(db.getEmail(tid),"JS1@live.com");
        assertEquals(db.getCompany(tid),"Jonny.Ltd");
    }



    @Test
    public void getDbInfo() {
        //assertNotNull(myConn);
    }

    @Test
    public void testgetSalt() {
        assertNotNull(db.getSalt(email));
    }

    @Test
    public void getValues() {
        assertNotNull(db.getValues(email));
    }

    @Test
    public void getLimit() {
        assertNotNull(db.getLimit(email));
    }

    @Test
    public void getCount() {
        assertNotNull(db.getCount(email));
    }



    @Test
    public void sendCount() {
        db.sendCount(2,email);
        db.getCount(email);
        assertEquals(2, db.getCount(email));
        //need to use tear down perhaps

        //boundary check
    }

    @Ignore
    public void updateSalt() {
        db.updateSalt(email,"21321");
        assertEquals("21321",db.getSalt(email));
    }

    @Test
    public void updateFirstname() {
        db.updateFirstname(tid,"Nike");
        assertEquals(db.getFirstname(tid), "Nike");
    }

    @Test
    public void updateCompany() {
        db.updateCompany(tid,"ea");
        db.getCompany(tid);
    }

    @Test
    public void updateSurname() {
        db.updateSurname(tid ,"Pade");
        assertEquals("Pade",db.getSurname(tid));
    }

    @Ignore
    public void updateEmail(){
        db.updateEmail(tid,"gogoo@123.com17");
        System.out.println("id is"+tid);
        email= db.getEmail(tid);
        assertEquals("gogoo@123.com17",email);

    }

    @Test
    public void updateLimit() {
        db.updateLimit(tid,"213");
        assertEquals(213,db.getLimit(email));
    }

    @Test
    public void updateCurrentCount() {
        db.updateCurrentCount(tid,4);
        System.out.println("count id is:"+tid);
        assertEquals(4,db.getCount(email));
    }

    @Test
    public void remaining() {
        db.getCount(email);


    }

    @Test
    public void checkLogin() {
        assertTrue(db.checkLogin(email,pHash));
    }

    @Test
    public void updateData() {
    }

    @Test
    public void updatePass() {
        db.updatePass("gogoo@123.com17","newpass");
        assertEquals("newpass", pHash);
    }


    @AfterClass
    public static void tearDown() throws Exception {
        db.deleteData(tid);
        System.out.println("deleted");
    }

}