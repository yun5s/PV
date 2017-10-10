package model;

import com.sun.codemodel.internal.fmt.JTextFile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.channels.FileLock;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by MaiwandMaidanwal on 24/07/2017.
 */
public class Model {

    private DocumentBuilder db;
    private ArrayList<String> folderFilePaths;
    private JFileChooser chooserInput;
    JFileChooser chooserFolder = new JFileChooser();
    private File fileToSave;
    private ArrayList<File> inputFiles;
    private ArrayList<File> convertedFiles;
    private Iterator<String> pathsIterator;
    private ArrayList<File> outputFiles;
    private boolean fileExists;



    public Model(){

        convertedFiles = new ArrayList<File>();             //initialise arraylist
    }

    /**
     * This method is called when the input button is pressed, It allows the user to open up the file chooser,
     * locate the input file/files they want , and then select them for conversion.
     * @throws Exception
     */
    public void pickInputFile() throws Exception {


        inputFiles = new ArrayList<File>();                                 //initialise here.

        chooserInput = new JFileChooser();
        chooserInput.setMultiSelectionEnabled(true);                        //for multiple files.

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "XML inputFiles", "XML", "XSL");       //added an extension filter to make searching easier.


        chooserInput.setFileFilter(filter);
        int returnVal = chooserInput.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            int n = chooserInput.getSelectedFiles().length;

            System.out.println("length of input..." + n);

            for (File file : chooserInput.getSelectedFiles()) {             //for every file, add to arraylist.
                inputFiles.add(file);
            }

        }

    }


    /**
     * This method is more longer, as it is for picking the output folder. This method allows the user to not only pick
     * the output destination but also it does not require the user to have output files, instead automatically
     * creating output files for the user.
     * @throws Exception
     */
    public void pickFolder() throws Exception {


        folderFilePaths = new ArrayList<String>();                                  //the file paths arraylist
        outputFiles = new ArrayList<File>();

            chooserFolder.setDialogTitle("Specify your save location");
            chooserFolder.setDialogType(JFileChooser.SAVE_DIALOG);
            chooserFolder.setSelectedFile(new File("CONVERT_"));            //default title set for user.


            chooserFolder.setFileFilter(new FileNameExtensionFilter("xml file", "xml"));


            //what to do if the user selects ok after selecting files.
        if (chooserFolder.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

            //get the path of where to save these files, and place the "CONVERT_" in front.
            String selectedDestination = String.valueOf(chooserFolder.getSelectedFile().getAbsolutePath());

            //For every file within the array List.
            for (File file : inputFiles) {

                fileExists = false;

                //Make sure the file is saved as "CONVERT_" followed by the input file name.
                chooserFolder.setSelectedFile(new File(selectedDestination + file.getName()));

                fileToSave = chooserFolder.getSelectedFile();

                if (fileToSave.createNewFile()) {           //check if you can create the new file
                } else {
                    fileExists = true;                      //if you cannot, the file already exists
                }

                folderFilePaths.add(fileToSave.getAbsolutePath());                     //add each filepath to the arraylist.
                outputFiles.add(chooserFolder.getSelectedFile().getAbsoluteFile());     //same for output files.
            }


            if(fileExists == true){
                JOptionPane.showMessageDialog(null, "File already exists. Program will refresh.");
            }
        }

        System.out.println("inputFiles....." + inputFiles.size());              //for confirmation
        System.out.println("output files..."+outputFiles);
        System.out.println("folderFile Paths....."+folderFilePaths);

    }



    public boolean getfileExists(){

        return fileExists;
    }


    /*
     * getter methods below.
     */

    public Integer getNumberOfInputFiles(){
        return inputFiles.size();
    }
    public File getFileToSave() {
        return fileToSave;
    }
    public ArrayList<File> getOutputFiles() {
        return outputFiles;
    }


    /**
     * This method takes int as a parameter so I can use it in the "View" class and parse in the number of conversions.
     * The point of this is so I can write out to the text file, the number of conversions there is.
     *
     * @throws IOException
     */
    public void writeToConversionsFile(int i) throws IOException {

        int I = convertedFiles.size();
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("conversionCount.txt"), "utf-8"))) {

            if ("conversionCount.txt".isEmpty()) {          //file is empty, write out the total you currently have to it.
                writer.write("" + I);
            } else{

                int newCount = i + I;                       //if not empty, add the total you have now to the existing total.
                writer.write(""+ newCount);
            }
        }
    }


    /**
     * method to be carried out once a month, so at the beginning of every month the total can go back to "0". This is
     * a good way for the user to track their conversions but also can be one way of implementing a
     * free trial version... for instance 30 conversion each month (to understand the product).
     */

    public void monthlyCleanse(){

        LocalDate localDate = LocalDate.now();

        if(localDate.getDayOfMonth() == 1){             //if the day is 1st, do the following...


            PrintWriter writer = null;
            try {
                writer = new PrintWriter("conversionCount.txt", "UTF-8");       //set title, and encoding.
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            writer.println("");                 //empty the file.
                writer.close();

        }
    }


/*
getter methods below
 */
    public String getChosenInputFile() {
        return String.valueOf(chooserInput.getSelectedFile());
    }
    public ArrayList<File> getInputFiles() {
        return inputFiles;
    }

    public ArrayList<String> getfolderFilePaths() {
        return folderFilePaths;
    }


    /**
     * The following are the transformation methods, there are 4 main transformation methods for the 4 different types
     * of transformation, although this could have been done in one method, this way allows more freedom and
     * is more beneficial for future development.
     *
     */
    public void transformerDownICSR() throws ParserConfigurationException, IOException, TransformerException, SAXException {

        pathsIterator = folderFilePaths.iterator();     //initialise the Iterator, to iterate through the file paths


        // do the following if there if only 1 input file selected.
        if(inputFiles.size()==1) {

            /*
            initialise transformer factory, and set the xslt source.
            MUST make sure to use "Model.class.getResource()" because the application is exported as a JAR.
             */
            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(String.valueOf(Model.class.getResource("/conversionXSLs/downgrade-icsr.xsl")));
            Transformer transformer = factory.newTransformer(xslt);

            Source text = new StreamSource(new File(getChosenInputFile()));

            //carry out the transformation,and streamresult to the chosen file path.
            transformer.transform(text, new StreamResult(new File(String.valueOf(fileToSave.getAbsolutePath()))));


        } else{         //if there are several files selected... do this


            for (File file : inputFiles) {    //for each file:


                /*
                doing the exact same as above with slight differences.
                 */
                TransformerFactory factory = TransformerFactory.newInstance();
                Source xslt = new StreamSource(String.valueOf(Model.class.getResource("/conversionXSLs/downgrade-icsr.xsl")));
                Transformer transformer = factory.newTransformer(xslt);

                //using the iterator this time, for several paths.
                if (pathsIterator.hasNext()) {
                    Source text = new StreamSource(new File(String.valueOf(file)));
                    transformer.transform(text,new StreamResult(new File(String.valueOf(pathsIterator.next()))));

                }
            }
        }
    }


    public void transformerDownAck() throws ParserConfigurationException, IOException, TransformerException, SAXException {

        pathsIterator = folderFilePaths.iterator();

        if(inputFiles.size()==1) {

            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(String.valueOf(Model.class.getResource("/conversionXSLs/downgrade-ack.xsl")));
            Transformer transformer = factory.newTransformer(xslt);


            Source text = new StreamSource(new File(getChosenInputFile()));

            transformer.transform(text, new StreamResult(new File(String.valueOf(fileToSave.getAbsolutePath()))));

        } else{


            for (File file : inputFiles) {


                TransformerFactory factory = TransformerFactory.newInstance();
                Source xslt = new StreamSource(String.valueOf(Model.class.getResource("/conversionXSLs/downgrade-ack.xsl")));
                Transformer transformer = factory.newTransformer(xslt);


                if (pathsIterator.hasNext()) {
                    Source text = new StreamSource(new File(String.valueOf(file)));
                    transformer.transform(text, new StreamResult(new File(String.valueOf(pathsIterator.next()))));
                }
            }
        }
    }


    /**
     * for the forwards conversion methods, I have decided to ignore the doctypes, because they were not ensuring
     * accurate transformation. I cannot however call ignoreDOCTYPE(); for the backwards conversions.
     *
     * Therefore this means the code for the following two methods is slightly different. Further explained below.
     */
    public void transformerUpICSR() throws ParserConfigurationException, IOException, TransformerException, SAXException {

        pathsIterator = folderFilePaths.iterator();

        if (inputFiles.size() == 1) {

            ignoreDOCTYPE();            //calling ignore DTD.

            //instead using document builder
            Document doc = db.parse(new FileInputStream(getChosenInputFile()));

            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(String.valueOf(Model.class.getResource("/conversionXSLs/upgrade-icsr.xsl")));

            Transformer transformer = factory.newTransformer(xslt);

            //using .getDocumentElement() instead of source because this way works when ignoring the Doctypes.
            transformer.transform(
                    new DOMSource(doc.getDocumentElement()),
                    new StreamResult(new File(String.valueOf(fileToSave.getAbsolutePath()))));

        } else{


            for (File file : inputFiles) {


                ignoreDOCTYPE();                                                //calling this method
                Document doc = db.parse(new FileInputStream(file));             //parse in the input files in document builder.

                TransformerFactory factory = TransformerFactory.newInstance();
                Source xslt = new StreamSource(String.valueOf(Model.class.getResource("/conversionXSLs/upgrade-icsr.xsl")));


                Transformer transformer = factory.newTransformer(xslt);

                if (pathsIterator.hasNext()) {
                    transformer.transform(
                            new DOMSource(doc.getDocumentElement()),
                            new StreamResult(new File(pathsIterator.next())));
                }
            }

        }
    }



    public void transformerUpAck() throws ParserConfigurationException, IOException, TransformerException, SAXException {

        pathsIterator = folderFilePaths.iterator();

        if (inputFiles.size() == 1) {

            ignoreDOCTYPE();
            Document doc = db.parse(new FileInputStream(getChosenInputFile()));

            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(String.valueOf(Model.class.getResource("/conversionXSLs/upgrade-ack.xsl")));
            Transformer transformer = factory.newTransformer(xslt);

            transformer.transform(
                    new DOMSource(doc.getDocumentElement()),
                    new StreamResult(new File(String.valueOf(fileToSave.getAbsolutePath()))));
        } else {

            System.out.println("number of files are..." + inputFiles);

            for (File file : inputFiles) {


                System.out.println("this is chosen input..." + file.getName());

                ignoreDOCTYPE();
                Document doc = db.parse(new FileInputStream(file));

                TransformerFactory factory = TransformerFactory.newInstance();
                Source xslt = new StreamSource(String.valueOf(Model.class.getResource("/conversionXSLs/upgrade-ack.xsl")));




                Transformer transformer = factory.newTransformer(xslt);

                    if (pathsIterator.hasNext()) {
                    transformer.transform(
                            new DOMSource(doc.getDocumentElement()),
                            new StreamResult(new File(pathsIterator.next())));
                }
            }
        }
    }


    /**
     * this method was necessary to be written, because the conversions were not happening accurately for the forwards
     * conversions. Therefore I figured out the problem was to do with the DTD (Doctypes) and this method ignores them.
     * @throws ParserConfigurationException
     */

    public void ignoreDOCTYPE() throws ParserConfigurationException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setValidating(false);
        dbf.setNamespaceAware(true);
        dbf.setFeature("http://xml.org/sax/features/namespaces", false);
        dbf.setFeature("http://xml.org/sax/features/validation", false);
        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

        db = dbf.newDocumentBuilder();
    }


    /**
     * The purpose here is to remove all the unused automatically created output files (after the pickFolder() method is called)
     * because if the output files have not converted properly, then they are removed.
     * How I can tell if they havent converted is simple, if the lines of the output file after conversion are less than 2
     * you know that something is wrong. Therefore delete the file. Because then the user is left with unecessry blank files
     * in their output destination.
     * @throws IOException
     */
    public void deletingWrongConversions() throws IOException {

        for(File file: getOutputFiles()) {                                          //for every output file

            BufferedReader reader = new BufferedReader(new FileReader(file));

            int lines = 0;
            while (reader.readLine() != null) {                                     //read the file
                lines++;
            }

            if (lines < 2) {                                                        // less than 2 lines?
                file.delete();                                                      //delete it
            }else{
                convertedFiles.add(file);          //otherwise add to another arraylist, for successful converted files.
            }
            reader.close();
        }
    }







}
