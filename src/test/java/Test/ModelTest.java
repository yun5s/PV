package Test;

import model.Model;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ModelTest {

    private Model model;
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
    public void pickInputFile() throws Exception {

        model.pickInputFile();

    }

    @Test
    public void pickFolder() throws Exception {
        model.pickFolder();
        //test for null value
        //test true
        //test for invalid value
    }

    @Test
    public void getfileExists() {

        model.getfileExists();
        //test for true
        //test for null
        //test for invalid
    }

    @Test
    public void getNumberOfInputFiles() {
        model.getfileExists();
        //test for true
        //test for null
        //test for invalid
    }

    @Test
    public void getFileToSave() {

        model.getFileToSave();
        //test for true
        //test for null
        //test for invalid
    }

    @Test
    public void getOutputFiles() {


        model.getOutputFiles();

        //test for true
        //test for null
        //test for invalid
        //test for exceptions
    }

    @Test
    public void writeCount() throws IOException {

        model.writeCount(1);
        //test for true
        //test for null
        //test for invalid
        //test for exceptions
        //test for possible inserts
        //test for

    }

    @Test
    public void getCount() {
        model.getCount();
        //test for true
        //test for null
        //test for invalid
        //test for exceptions
        //test for possible inserts
    }

    @Ignore
    public void writeToConversionsFile() {

        //test for true
        //test for null
        //test for invalid
        //test for exceptions
        //test for possible inserts
    }

    @Ignore
    public void reset2() {
        //test for rest
        //test not callable if different ID
        //test to get the
    }

    @Ignore
    public void getUsername() {
        //test not null
        //test get file
    }

    @Ignore
    public void setUsername() {

    }

    @Test
    public void createFolder() {
        model.createFolder("666");
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
        model.getUser();

    }

    @Ignore
    public void resetCount() {
        //reset count from
    }

    @Test
    public void getChosenInputFile() {
        //get the selected file
        //test for not null
        //check for exception

        //get value ? string
        model.getChosenInputFile();

    }

    @Test
    public void getInputFiles() {
        //get the inputed files
        model.getInputFiles();
    }

    @Test
    public void getfolderFilePaths() {
        model.getfolderFilePaths();

    }


    @Test
    public void transformerDownICSR() throws ParserConfigurationException, TransformerException, SAXException, IOException {
        model.transformerDownICSR();
        //test the file is not
    }

    @Test
    public void transformerDownAck() throws ParserConfigurationException, TransformerException, SAXException, IOException {
        model.transformerDownAck();
    }

    @Test
    public void transformerUpICSR() throws ParserConfigurationException, TransformerException, SAXException, IOException {
        model.transformerUpICSR();
    }

    @Test
    public void transformerUpAck() throws ParserConfigurationException, TransformerException, SAXException, IOException {
        model.transformerUpAck();
    }

    @Test
    public void ignoreDOCTYPE() {
    }

    @Test
    public void deletingWrongConversions() throws IOException {
        model.deletingWrongConversions();
    }
}