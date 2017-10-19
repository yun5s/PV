package db;


import java.io.*;

import static java.lang.System.out;


public class Filewr {

 DBconnect db = new DBconnect();




    public  String fWrite(String email ){
        FileWriter fw = null;
        String dd = db.getActKey(email);

        try {
            fw = new FileWriter("data.txt");
            PrintWriter pw = new PrintWriter(fw);
            pw.println(dd);
            pw.close();
            return email;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  void mWrite(String email){
        FileWriter fw = null;
        try {
            fw = new FileWriter("email.txt");
            PrintWriter pw = new PrintWriter(fw);
            pw.println(email);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String mRead(){
        FileReader fr = null;
        try {
            fr = new FileReader("email.txt");
            BufferedReader br = new BufferedReader(fr);

            String str;
            while((str = br.readLine())!=null){
                return str;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String fRead(){
        FileReader fr = null;
        try {
            fr = new FileReader("data.txt");
            BufferedReader br = new BufferedReader(fr);

            String str;
            while((str = br.readLine())!=null){
                return str;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
