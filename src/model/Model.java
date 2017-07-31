package model;

import jdk.internal.org.xml.sax.InputSource;
import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;

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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;


/**
 * Created by MaiwandMaidanwal on 24/07/2017.
 */
public class Model{

    private DocumentBuilder db;
    JFileChooser chooserInput = new JFileChooser();
    JFileChooser chooserOutput = new JFileChooser();
    JFileChooser chooserFolder = new JFileChooser();


    public void pickInputFile() throws Exception{

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "XML files", "XML", "XSL");

        java.io.File file = chooserInput.getSelectedFile();

        chooserInput.setFileFilter(filter);
        int returnVal = chooserInput.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Your input file: " +
                    chooserInput.getSelectedFile().getName());

            System.out.println("chosen Input method returns --> " + getChosenInputFileName());



        }

    }

    public JFileChooser getChooserInput(){
        return chooserInput;
    }



    public void pickOutputFile() throws Exception{


        java.io.File file = chooserOutput.getSelectedFile();



        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "XML files", "XML", "XSL");


        chooserOutput.setFileFilter(filter);
        int returnVal = chooserOutput.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {

    System.out.println("chosen output method returns --> "+ getChosenOutputFileName());


        }if(returnVal == JFileChooser.CANCEL_OPTION){

            System.out.println ("cancelled");
        }

    }


    public void pickFolder() throws Exception{


        chooserFolder.setDialogTitle("Specify your save location");
        int file = chooserFolder.showSaveDialog(null);

        int userSelection = chooserFolder.showSaveDialog(null);


        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = chooserFolder.getSelectedFile();
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());


        }

    }




    public String getChosenInputFile() {
        return String.valueOf(chooserInput.getSelectedFile());     //returns file location
    }
    public String getChosenOutputFile() {
        return String.valueOf(chooserOutput.getSelectedFile());
    }


    public String getChosenInputFileName(){
        return chooserInput.getSelectedFile().getName();   //returns file name
    }
    public String getChosenOutputFileName(){
        return chooserOutput.getSelectedFile().getName();
    }





    public void transformerDownICSR() throws IOException, URISyntaxException, TransformerException {

        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("downgrade-icsr.xsl"));
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File(getChosenInputFile()));
        transformer.transform(text, new StreamResult(new File(getChosenOutputFile())));
    }


    public void transformerUpICSR() throws IOException, URISyntaxException, TransformerException, ParserConfigurationException, org.xml.sax.SAXException {


        ignoreDOCTYPE();
        Document doc = db.parse(new FileInputStream(getChosenInputFile()));

        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("upgrade-icsr.xsl"));
        Transformer transformer = factory.newTransformer(xslt);

//        Source text = new StreamSource(new File(getChosenInputFile()));

        Document newdoc = db.newDocument();
        Result XmlResult = new DOMResult(newdoc);

        transformer.transform(
                new DOMSource(doc.getDocumentElement()),
//                XmlResult); //    option1:  to store into a variable.

        new StreamResult(new File(getChosenOutputFile())));     //option2 : to print into chosen output file

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













    public void transformerDownAck() throws IOException, URISyntaxException, TransformerException {

        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("downgrade-ack.xsl"));
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File(getChosenInputFile()));
        transformer.transform(text, new StreamResult(new File(getChosenOutputFile())));

    }

    public void transformerUpAck() throws IOException, URISyntaxException, TransformerException {

        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("upgrade-ack.xsl"));
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File(getChosenInputFile()));
        transformer.transform(text, new StreamResult(new File(getChosenOutputFile())));

    }











    }
