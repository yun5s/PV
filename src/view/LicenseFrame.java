package view;

import com.oracle.tools.packager.IOUtils;
import db.DBconnect;
import db.Filewr;
import jdk.nashorn.internal.scripts.JO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by MaiwandMaidanwal on 08/09/2017.
 */

/**
 * This is another class for the JFrame that is called when the key icon is selected on the main JFrame.
 * This class is for license key validation, but is incomplete.
 */



public class LicenseFrame extends JFrame{
    private JButton activateButton;
    private JPanel panel1;
    private JTextField textField1;
    DBconnect db = new DBconnect();
    Filewr ff = new Filewr();



    public LicenseFrame() {
        activateButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             String email = ff.mRead();
             String dd = db.getActKey(email);
             String tt = textField1.getText();

             if(db.checkKey(tt)==true){

             }else{
                 System.out.println();
             }

             try {
                 try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                         new FileOutputStream("data1.txt"), "utf-8"))) {

                     if ("data1.txt".isEmpty()) {          //file is empty, write out the total you currently have to it.
                         writer.write("" + dd);
                     } else {
                         tt = ff.fRead();
                     }
                 }
             } catch (IOException e1) {
                 e1.printStackTrace();
             }


             if (tt.equals(dd)) {

                 JOptionPane.showMessageDialog(null, "correct");
                 dispose();
             } else {
                 JOptionPane.showMessageDialog(null, "incorrect");
             }

         }}
        );
    }
     public JPanel getPanel1() {
                                                 return panel1;
                                             }



}







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



