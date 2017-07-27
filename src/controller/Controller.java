package controller;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Observable;
import model.Model;

/**
 * Created by MaiwandMaidanwal on 21/07/2017.
 */
public class Controller{

    private Model model;



    public Controller(Model model){

        this.model = model;
    }


    public void testConversions()  throws IOException, URISyntaxException, TransformerException {

        System.out.println(" will call the models transformation now.");

//        model.transformerDownICSR();


        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("downgrade-icsr.xsl"));
        Transformer transformer = factory.newTransformer(xslt);


        Source text = new StreamSource(new File(model.getChosenInputFile()));
        System.out.println("chosen input file" + text);


        transformer.transform(text, new StreamResult(new File(model.getChosenOutputFile())));
        System.out.println("chosen output file" + model.getChosenOutputFile());


    }





//
//            setChanged();
//            notifyObservers();








//        else if(view.getForwardsICSR().isSelected()){
//
//            TransformerFactory factory = TransformerFactory.newInstance();
//            Source xslt = new StreamSource(new File("upgrade-icsr.xsl"));
//            Transformer transformer = factory.newTransformer(xslt);
//            }
//
//        else if (view.getBackwardsACK().isSelected()){
//
//            TransformerFactory factory = TransformerFactory.newInstance();
//            Source xslt = new StreamSource(new File("downgrade-ack.xsl"));
//            Transformer transformer = factory.newTransformer(xslt);
//        }
//        else //(view.getForwardsACK().isSelected()){
//
//        { TransformerFactory factory = TransformerFactory.newInstance();
//            Source xslt = new StreamSource(new File("upgrade-ack.xsl"));
//            Transformer transformer = factory.newTransformer(xslt);
//        }
//

}
