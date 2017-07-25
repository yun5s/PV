package model;

import controller.Controller;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;


/**
 * Created by MaiwandMaidanwal on 24/07/2017.
 */
public class Model {

    private static Controller controller;

    public Model(){

        controller = new Controller();
    }


     public void setXsltFile(){







//                    Source xslt = new StreamSource(new File(backwards-csrs.xsl));
//                    Transformer transformer = factory.newTransformer(xslt);
//
//                }
//
//
//            case(userselectsForwards )
//                    Source xslt = new StreamSource(new File(backwards-csrs.xsl));
//                Transformer transformer = factory.newTransformer(xslt);
//
//                etc...
//
//            Source text = new StreamSource(new File(controller.getChosenInputFile()));
//            transformer.transform(text, new StreamResult(new File("output.xml")));
//







//
//                TransformerFactory factory = TransformerFactory.newInstance();     //THE BELOW STUFF WORKS!!!!!
//                Source xslt = new StreamSource(new File("downgrade-icsr.xsl"));
//                Transformer transformer = factory.newTransformer(xslt);
//
//                Source text = new StreamSource(new File("utr3-01.xml"));
//                transformer.transform(text, new StreamResult(new File("blank-utr2-01.xml")));
        }


    public void transform() throws IOException, URISyntaxException, TransformerException {

    }

}
