package model;

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
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    private ArrayList<String> names;
    private int name;
    private Iterator<String> namesIterator;
    private Iterator<String> pathsIterator;
    private Iterator<File> inputFilesIterator;
    private ArrayList<File> outputFiles;


    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd.HH.mm");


    public Model(){


    }
    public void pickInputFile() throws Exception {

        inputFiles = new ArrayList<File>();


        chooserInput = new JFileChooser();

        chooserInput.setMultiSelectionEnabled(true);

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "XML inputFiles", "XML", "XSL");

//        java.io.File file = chooserInput.getSelectedFile();

        chooserInput.setFileFilter(filter);
        int returnVal = chooserInput.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            int n = chooserInput.getSelectedFiles().length;

            System.out.println("length of input..." + n);

            for (File file : chooserInput.getSelectedFiles()) {
                inputFiles.add(file);
            }

            System.out.println("input files be like...." + inputFiles);
            System.out.println("Your input file: " +
                    inputFiles.toString());

        }

    }


    public void pickFolder() throws Exception {


        namesIterator = names.iterator();
        inputFilesIterator = inputFiles.iterator();
        folderFilePaths = new ArrayList<String>();
        outputFiles = new ArrayList<File>();


        if (inputFiles.size() > 1) {
            JOptionPane.showMessageDialog(null, "<html>PLEASE NOTE:    You have chosen to convert multiple inputFiles: <br/><br/>" +
                    "then please continue pressing ok to save each file to your output folder.<br/> You do not have to name your file unless you wish to<html/>");
        }


            chooserFolder.setDialogTitle("Specify your save location");
            chooserFolder.setDialogType(JFileChooser.SAVE_DIALOG);
            chooserFolder.setSelectedFile(new File("CONVERT_"));


            chooserFolder.setFileFilter(new FileNameExtensionFilter("xml file", "xml"));

        if (chooserFolder.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

            String selectedDestination = String.valueOf(chooserFolder.getSelectedFile().getAbsolutePath());

            for (File file : inputFiles) {

                chooserFolder.setSelectedFile(new File(selectedDestination + file.getName()));

                fileToSave = chooserFolder.getSelectedFile();

                if (fileToSave.createNewFile()) {
                    System.out.println("File is created!");
                } else {
                    JOptionPane.showMessageDialog(null, "File already exists.");
                }

                System.out.println("Save as file: " + fileToSave.getAbsolutePath());

                folderFilePaths.add(fileToSave.getAbsolutePath());
                outputFiles.add(chooserFolder.getSelectedFile().getAbsoluteFile());
            }

        }

        System.out.println("file paths......" + folderFilePaths.size());
        System.out.println(folderFilePaths);
        System.out.println("inputFiles....." + inputFiles.size());
        System.out.println("output files..."+outputFiles);

    }


    public File getFileToSave() {
        return fileToSave;
    }

    public ArrayList<File> getOutputFiles() {
        return outputFiles;
    }




    public String getChosenInputFile() {

        return String.valueOf(chooserInput.getSelectedFile());     //returns file location
    }

//    public String getAllInputFiles(){
//
//            return
//    }

    public ArrayList<File> getInputFiles() {
        return inputFiles;
    }


    public ArrayList<String> getfolderFilePath() {
        return folderFilePaths;
    }


    public String getChosenInputFileNames() {

        names = new ArrayList<String>();


        for (File file : inputFiles) {           //for number of input inputFiles

            String n = file.getName().replaceAll(".xml", "");
            names.add(n);      //get the input file names and store in arraylist.
        }

        return String.valueOf(names);


    }


    public void transformerDownICSR() throws ParserConfigurationException, IOException, TransformerException, SAXException {

        pathsIterator = folderFilePaths.iterator();

        if(inputFiles.size()==1) {

            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(new File("downgrade-icsr.xsl"));
            Transformer transformer = factory.newTransformer(xslt);


            Source text = new StreamSource(new File(getChosenInputFile()));
            transformer.transform(text, new StreamResult(new File(String.valueOf(fileToSave.getAbsolutePath()))));


        } else{
            System.out.println("number of files are..." + inputFiles);

            for (File file : inputFiles) {

                System.out.println("this is chosen input..." + file.getName());

                TransformerFactory factory = TransformerFactory.newInstance();
                Source xslt = new StreamSource(new File("downgrade-icsr.xsl"));
                Transformer transformer = factory.newTransformer(xslt);


                if (pathsIterator.hasNext()) {
                    Source text = new StreamSource(new File(String.valueOf(file)));
                    transformer.transform(text, new StreamResult(new File(String.valueOf(pathsIterator.next()))));
                }
            }
        }
    }


    public void transformerDownAck() throws ParserConfigurationException, IOException, TransformerException, SAXException {

        pathsIterator = folderFilePaths.iterator();

        if(inputFiles.size()==1) {

            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(new File("downgrade-ack.xsl"));
            Transformer transformer = factory.newTransformer(xslt);


            Source text = new StreamSource(new File(getChosenInputFile()));

            transformer.transform(text, new StreamResult(new File(String.valueOf(fileToSave.getAbsolutePath()))));

        } else{

            System.out.println("number of files are..." + inputFiles);

            for (File file : inputFiles) {

                System.out.println("this is chosen input..." + file.getName());

                TransformerFactory factory = TransformerFactory.newInstance();
                Source xslt = new StreamSource(new File("downgrade-ack.xsl"));
                Transformer transformer = factory.newTransformer(xslt);


                if (pathsIterator.hasNext()) {
                    Source text = new StreamSource(new File(String.valueOf(file)));
                    transformer.transform(text, new StreamResult(new File(String.valueOf(pathsIterator.next()))));
                }
            }
        }
    }




    public void transformerUpICSR() throws ParserConfigurationException, IOException, TransformerException, SAXException {

        pathsIterator = folderFilePaths.iterator();

        if (inputFiles.size() == 1) {

            ignoreDOCTYPE();
            Document doc = db.parse(new FileInputStream(getChosenInputFile()));

            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(new File("upgrade-icsr.xsl"));
            Transformer transformer = factory.newTransformer(xslt);

            transformer.transform(
                    new DOMSource(doc.getDocumentElement()),
                    new StreamResult(new File(String.valueOf(fileToSave.getAbsolutePath()))));

        } else{

            System.out.println("number of files are..." + inputFiles);

            for (File file : inputFiles) {

                System.out.println("this is chosen input..." + file.getName());

                ignoreDOCTYPE();
                Document doc = db.parse(new FileInputStream(file));

                TransformerFactory factory = TransformerFactory.newInstance();
                Source xslt = new StreamSource(new File("upgrade-icsr.xsl"));
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
            Source xslt = new StreamSource(new File("upgrade-ack.xsl"));
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
                Source xslt = new StreamSource(new File("upgrade-ack.xsl"));
                Transformer transformer = factory.newTransformer(xslt);

                    if (pathsIterator.hasNext()) {
                    transformer.transform(
                            new DOMSource(doc.getDocumentElement()),
                            new StreamResult(new File(pathsIterator.next())));
                }
            }
        }
    }













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


}
