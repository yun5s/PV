package model;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;


/**
 * Created by MaiwandMaidanwal on 24/07/2017.
 */
public class Model{

    JFileChooser chooserInput = new JFileChooser();
    JFileChooser chooserOutput = new JFileChooser();
    //private Transformer transformer;


    public void pickInputFile() throws Exception{

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "XML files", "XML", "XSL");

        java.io.File file = chooserInput.getSelectedFile();

        chooserInput.setFileFilter(filter);
        int returnVal = chooserInput.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Your input file: " +
                    chooserInput.getSelectedFile().getName());

            System.out.println("chosen Input method returns --> " + getChosenInputFile());



        }

    }


    public void pickOutputFile() throws Exception{


        java.io.File file = chooserOutput.getSelectedFile();



        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "XML files", "XML", "XSL");


        chooserOutput.setFileFilter(filter);
        int returnVal = chooserOutput.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {

    System.out.println("chosen output method returns --> "+ getChosenOutputFile());


        }

    }


    public String getChosenInputFile() {

        return String.valueOf(chooserInput.getSelectedFile());
    }


    public String getChosenOutputFile() {

        return String.valueOf(chooserOutput.getSelectedFile());
    }





    public void transformerDownICSR() throws IOException, URISyntaxException, TransformerException {

        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("downgrade-icsr.xsl"));
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File(getChosenInputFile()));
        transformer.transform(text, new StreamResult(new File(getChosenOutputFile())));

    }


    public void transformerUpICSR() throws IOException, URISyntaxException, TransformerException {

        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("upgrade-icsr.xsl"));
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File(getChosenInputFile()));
        transformer.transform(text, new StreamResult(new File(getChosenOutputFile())));
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
