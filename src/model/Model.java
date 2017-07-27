package model;

import controller.Controller;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


/**
 * Created by MaiwandMaidanwal on 24/07/2017.
 */
public class Model{

    private static Controller controller;
    JFileChooser chooserInput = new JFileChooser();
    JFileChooser chooserOutput = new JFileChooser();


    public void pickInputFile() throws Exception{

        System.out.println("file");
        System.out.println("inside input file" + chooserInput.getSelectedFile());

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "XML files", "XML", "XSL");

        java.io.File file = chooserInput.getSelectedFile();

        chooserInput.setFileFilter(filter);
        int returnVal = chooserInput.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Your input file: " +
                    chooserInput.getSelectedFile().getName());


        }

    }


    public void pickOutputFile() throws Exception{

        System.out.println("inside out file" + chooserOutput.getSelectedFile());       //TESTING WHATS INSIDE

        java.io.File file = chooserOutput.getSelectedFile();



        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "XML files", "XML", "XSL");


        chooserOutput.setFileFilter(filter);
        int returnVal = chooserOutput.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Your output file: " +
                    chooserOutput.getSelectedFile().getName());



        }

    }


    public String getChosenInputFile() {

        return String.valueOf(chooserInput.getSelectedFile());
    }


    public String getChosenOutputFile() {

        return String.valueOf(chooserOutput.getSelectedFile());
    }



    public void transformerDownICSR() throws TransformerException, FileNotFoundException {

        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("downgrade-icsr.xsl"));
        Transformer transformer = factory.newTransformer(xslt);


        Source text = new StreamSource(new File((getChosenInputFile())));
        transformer.transform(text, new StreamResult(new File(getChosenOutputFile())));


    }



//
//     public void setXsltFile(){
//
//
//
//                TransformerFactory factory = TransformerFactory.newInstance();     //THE BELOW STUFF WORKS!!!!!
//                Source xslt = new StreamSource(new File("downgrade-icsr.xsl"));
//                Transformer transformer = factory.newTransformer(xslt);
//
//
//        }

}
