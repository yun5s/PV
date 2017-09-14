package view;

import com.oracle.tools.packager.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by MaiwandMaidanwal on 08/09/2017.
 */
public class LicenseFrame extends JFrame{
    private JButton activateButton;
    private JPanel panel1;
    private JTextField textField1;


    public LicenseFrame() {
        activateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                /** below is code, where I tried to use JSoup to read from and write to an online text file, on
                 * which i have stored license keys,this would have been a great way to ensure that each person has
                 * a unique license key. Also the keys would not be used twice. I was unable to figure out how to
                 * do this, maybe if i had more time i would but below is the starting point...
                 */

//                Document doc = null;
//                try {
//
//                    doc = Jsoup.connect("http://www.writeurl.com/publish/98dmj5pz8obnk39ebrhk").get();
//
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//
//                String text = doc.body().text(); // "An example link"
//
//
//
//                Element div = doc.select("div").first(); // <div></div>
//                div.html("<p>lorem ipsum</p>"); // <div><p>lorem ipsum</p></div>
//                div.prepend("<p>First</p>");
//                div.append("<p>Last</p>");
//
//
//                System.out.println("this is the text"+text);
//
//
//                if(text.contains("hello")){
//                        System.out.println("online file has been read.");
//                    }else{
//                        System.out.println("reading has failed.");}


                JOptionPane.showMessageDialog(null, "Unique license key system is currently" +
                        " in development, coming soon.");


            }
        });
    }

    public JPanel getPanel1(){
        return panel1;
    }


}
