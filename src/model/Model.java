package model;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Created by MaiwandMaidanwal on 24/07/2017.
 */
public class Model{

    private DocumentBuilder db;
    private String folderFilePath;
    private JFileChooser chooserInput;
    JFileChooser chooserFolder = new JFileChooser();
    private File fileToSave;
    private File[] files;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");


    public void pickInputFile() throws Exception{

        chooserInput = new JFileChooser();

        chooserInput.setMultiSelectionEnabled(true);

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "XML files", "XML", "XSL");

//        java.io.File file = chooserInput.getSelectedFile();

        chooserInput.setFileFilter(filter);
        int returnVal = chooserInput.showOpenDialog(null);

        if(returnVal == JFileChooser.APPROVE_OPTION) {

            files = chooserInput.getSelectedFiles();


            System.out.println("Your input file: " +
                    files.toString());

        }

    }


    public void pickFolder() throws Exception{

        int numbers = 0;

        chooserFolder.setDialogTitle("Specify your save location");
        chooserFolder.setDialogType(JFileChooser.SAVE_DIALOG);
//Set an extension filter, so the user sees other XML files
     chooserFolder.setSelectedFile(new File("myFile.xml"));
        chooserFolder.setFileFilter(new FileNameExtensionFilter("xml file","xml"));

        int userSelection = chooserFolder.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {

            for (File file : files) {


//                chooserFolder.setSelectedFile(new File(chooserFolder.getSelectedFile().getAbsolutePath()+ numbers++ + ".xml"));

                boolean f = chooserFolder.getSelectedFile().renameTo(new File(chooserFolder.getSelectedFile().getAbsolutePath() + numbers++ + ".xml"));

                fileToSave = chooserFolder.getSelectedFile();
                if (fileToSave.createNewFile()){
                    System.out.println("File is created!");
                    fileToSave = chooserFolder.getSelectedFile();

                }else{
                    JOptionPane.showMessageDialog(null, "File already exists.");
                }

                System.out.println("Save as file: " + fileToSave.getAbsolutePath());

               }

            folderFilePath = fileToSave.getAbsolutePath();

        }

        }

        public File getFileToSave(){
        return fileToSave;
        }







    public String getChosenInputFile() {

        return String.valueOf(chooserInput.getSelectedFile());     //returns file location
    }





    public String getfolderFilePath(){
return folderFilePath;    }


    public String getChosenInputFileName() {

        ArrayList<String> names = new ArrayList<String>();

        for (File file : files) {
            System.out.println(file.getName());

            names.add(file.getName());
        }

        return String.valueOf(names);
    }





    public void transformerDownICSR() throws ParserConfigurationException, IOException, TransformerException, SAXException {


        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("downgrade-icsr.xsl"));
        Transformer transformer = factory.newTransformer(xslt);


        Source text = new StreamSource(new File(getChosenInputFile()));

        transformer.transform(text, new StreamResult(new File(folderFilePath)));
    }


    public void transformerUpICSR() throws ParserConfigurationException, IOException, TransformerException, SAXException {

        ignoreDOCTYPE();
        Document doc = db.parse(new FileInputStream(getChosenInputFile()));

        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("upgrade-icsr.xsl"));
        Transformer transformer = factory.newTransformer(xslt);

        transformer.transform(
                new DOMSource(doc.getDocumentElement()),
                new StreamResult(new File(folderFilePath)));
    }



    public void transformerDownAck() throws ParserConfigurationException, IOException, TransformerException, SAXException {


        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("downgrade-ack.xsl"));
        Transformer transformer = factory.newTransformer(xslt);


       Source text = new StreamSource(new File(getChosenInputFile()));

        transformer.transform( text, new StreamResult(new File(folderFilePath)));
    }

    public void transformerUpAck() throws ParserConfigurationException, IOException, TransformerException, SAXException {

        ignoreDOCTYPE();
        Document doc = db.parse(new FileInputStream(getChosenInputFile()));

        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("upgrade-ack.xsl"));
        Transformer transformer = factory.newTransformer(xslt);

        transformer.transform(
                new DOMSource(doc.getDocumentElement()),
                new StreamResult(new File(folderFilePath)));

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
