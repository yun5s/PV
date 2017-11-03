package db;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class Test {
    public static void main(String[] args) {

        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("config.properties");

            // set the properties value
            prop.setProperty("DB_DRIVER","com.mysql.jdbc.Driver");
            prop.setProperty("DB_HOST", "jdbc:mysql://qym848.pvpharm.com:3306/qym848");
            prop.setProperty("DB_USER", "qym848");
            prop.setProperty("DB_PWD", "PVpharm123");

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
