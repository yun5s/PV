package model;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;



/**
 * Created by MaiwandMaidanwal on 24/07/2017.
 */
public class Model{

    JFileChooser chooserInput = new JFileChooser();
    JFileChooser chooserOutput = new JFileChooser();
    //private Transformer transformer;


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

            System.out.println("inside out file after" + chooserInput.getSelectedFile());       //TESTING WHATS INSIDE

            System.out.println("chosen Input method returns --> " + getChosenInputFile());



        }

    }


    public void pickOutputFile() throws Exception{

        System.out.println("inside out file before" + chooserOutput.getSelectedFile());       //TESTING WHATS INSIDE

        java.io.File file = chooserOutput.getSelectedFile();



        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "XML files", "XML", "XSL");


        chooserOutput.setFileFilter(filter);
        int returnVal = chooserOutput.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Your output file: " +
                    chooserOutput.getSelectedFile().getName());

            System.out.println("inside out file after" + chooserOutput.getSelectedFile());       //TESTING WHATS INSIDE

    System.out.println("chosen output method returns --> "+ getChosenOutputFile());


        }

    }


    public String getChosenInputFile() {

        return String.valueOf(chooserInput.getSelectedFile());
    }


    public String getChosenOutputFile() {

        return String.valueOf(chooserOutput.getSelectedFile());
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
