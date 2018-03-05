package Test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ModelTest {

    @Before
    public void setUp() throws Exception {
        //array of files
        //location
        //user
        //database connection
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void pickInputFile() {
        //test for null value
        //test true
        //test for invalid value

    }

    @Test
    public void pickFolder() {
        //test for null value
        //test true
        //test for invalid value
    }

    @Test
    public void getfileExists() {
        //test for true
        //test for null
        //test for invalid
    }

    @Test
    public void getNumberOfInputFiles() {
        //test for true
        //test for null
        //test for invalid
    }

    @Test
    public void getFileToSave() {
        //test for true
        //test for null
        //test for invalid
    }

    @Test
    public void getOutputFiles() {
        //test for true
        //test for null
        //test for invalid
        //test for exceptions
    }

    @Test
    public void writeCount() {
        //test for true
        //test for null
        //test for invalid
        //test for exceptions
        //test for possible inserts
        //test for

    }

    @Test
    public void getCount() {
        //test for true
        //test for null
        //test for invalid
        //test for exceptions
        //test for possible inserts
    }

    @Test
    public void writeToConversionsFile() {
        //test for true
        //test for null
        //test for invalid
        //test for exceptions
        //test for possible inserts
    }

    @Test
    public void reset2() {
        //test for rest
        //test not callable if different ID
        //test to get the
    }

    @Test
    public void getUsername() {
        //test not null
        //test get file
    }

    @Test
    public void setUsername() {


    }

    @Test
    public void createFolder() {
        //test creating location
        //location unknown to others
    }

    @Test
    public void checkExists() {
        String directory = (System.getProperty("user.home") + "/PVpharmC");
        String file = "Data";
        File dir = new File(directory);
        File[] dir_contents = dir.listFiles();
        String temp = file + ".txt";
        boolean check = new File(directory, temp).exists();


        assertEquals(true,check);
        //test with the database
    }

    @Test
    public void getUser() {
        //getting user from the txt file

    }

    @Test
    public void resetCount() {
        //reset count from
    }

    @Test
    public void getChosenInputFile() {
        //get the selected file
        //test for not null
        //check for exception

        //get value ? string


    }

    @Test
    public void getInputFiles() {
        //get the inputed files
    }

    @Test
    public void getfolderFilePaths() {

    }


    @Test
    public void transformerDownICSR() {
        //test the file is not
    }

    @Test
    public void transformerDownAck() {
    }

    @Test
    public void transformerUpICSR() {
    }

    @Test
    public void transformerUpAck() {
    }

    @Test
    public void ignoreDOCTYPE() {
    }

    @Test
    public void deletingWrongConversions() {
    }
}