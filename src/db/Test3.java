package db;

import model.Model;

import javax.swing.*;
import java.io.*;

public class Test3 {
    public static void main(String args []){
        System.out.println(System.getProperty("user.home")+"/omg");
        Model.checkExists();
        try {
            String Directory = System.getProperty("user.home")+"/omg";
            File file = new File(Directory+"/data.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while((st=br.readLine()) != null){
                System.out.println(st);
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        DBconnect db = new DBconnect();
        int k = db.getLimit("haigui222")-db.getCount("haigui222");
        System.out.println( "Files transformed this month:   "+ db.getCount("haigui222")+" "+db.getLimit("haigui222")+" remaining  "+k);
    }

}
