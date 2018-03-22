package db;


import License.LicenseGen;
import License.PassGen;
import at.gadermaier.argon2.Argon2;
import at.gadermaier.argon2.Argon2Factory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import java.awt.Window;

public class Forgotten {
    DBconnect connect = new DBconnect();
    LicenseGen generate = new LicenseGen();

    public JFrame frame;
    private JTextField firstnameField;
    private JTextField surnameField;
    private JTextField emailField;
    //private JTextField userField;
    private JPasswordField passwordField;
    private JTextField amountField;
    private JTextField companyField;




    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Forgotten window = new Forgotten();
                    window.frame.setLocationRelativeTo(null);
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    Mailing email1 = new Mailing();
    /**
     *
     * Launch the application.
     */

    /**
     * Create the application.
     */
    public Forgotten() {
        initialize();
    }

    /**
     * Initialise the contents of the frame.
     */

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(200, 200, 650, 220);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setTitle("Forgotten Password?");

        JLabel lblRegistration = new JLabel("Please enter the following details to reset your password.");
        lblRegistration.setBounds(24, 6, 500, 16);
        frame.getContentPane().add(lblRegistration);

        JLabel lblFirstname = new JLabel("Firstname:");
        lblFirstname.setBounds(24, 40, 78, 16);
        frame.getContentPane().add(lblFirstname);

        JLabel lblSurname = new JLabel("Surname: ");
        lblSurname.setBounds(24, 70, 78, 16);
        frame.getContentPane().add(lblSurname);

        JLabel lblEmailAddress = new JLabel("Email Address:");
        lblEmailAddress.setBounds(24, 100, 93, 16);
        frame.getContentPane().add(lblEmailAddress);

        JLabel company = new JLabel("Company:");
        company.setBounds(285, 40, 93, 16);
        frame.getContentPane().add(company);

        firstnameField = new JTextField();
        firstnameField.setBounds(140, 40, 134, 28);
        frame.getContentPane().add(firstnameField);
        firstnameField.setColumns(10);


        surnameField = new JTextField();
        surnameField.setBounds(140, 70, 134, 28);
        frame.getContentPane().add(surnameField);
        surnameField.setColumns(10);


        emailField = new JTextField();
        emailField.setBounds(140, 100, 134, 28);
        frame.getContentPane().add(emailField);
        emailField.setColumns(10);


		/*passwordField = new JPasswordField();
		passwordField.setBounds(140, 130, 134, 28);
		frame.getContentPane().add(passwordField);
		*/

        companyField = new JTextField();
        companyField.setBounds(425, 40, 134, 28);
        frame.getContentPane().add(companyField);
        companyField.setColumns(10);

        JButton btnSubmit = new JButton("Submit");

        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fname = firstnameField.getText();
                String sname = surnameField.getText();
                String email = emailField.getText();
                String company = companyField.getText();

                //@SuppressWarnings("deprecation")
                //String pass = password; // get string from text field
                //int amount= Integer.parseInt(amountField.getText());

                String type = "";


                ;
                /*if(connect.checkUser(email)==false) {
                    JOptionPane.showMessageDialog(null, "Submitted!");
                    frame.dispose();

                    connect.register(fname,sname,email,company,pass,amount);
                    String val = connect.getValues(email);
                    val = generate.createLicenseKey(val,fname,type);
                    connect.updateData(email,val);
                    System.out.println(val);
					/*email1.sendmail(email,"Thank you for using PVpharm Converter ","Dear "+fname
							+ "\n\nThank you for purchasing our product, you License key is "+ "\n\n" +val
							+ "\n\n" + "The license wil expire on "+ connect.getDate(email)
							+ " For anyother enquires, please visit www.pvpharm.com or Email yun@PVpharm.com"
							+ "\n\n\n"
							+ "\n Yun \n  PVpharm Technology Team"
					);


*/
                DBconnect db = new DBconnect();


                if (db.confirm(fname, sname, email, company)) {
                    JOptionPane.showMessageDialog(null, "Your new password will be sent to " + email + ". Please make sure to check your junk mail as well.");

                    Mailing mail = new Mailing();

                    PassGen passwordGenerator = new PassGen.PasswordGeneratorBuilder()
                            .useDigits(true)
                            .useLower(true)
                            .useUpper(true)
                            .build();
                    final String newpassword = passwordGenerator.generate(8);


                    Argon2 argon = new Argon2();
                    char[] password = newpassword.toCharArray();

                    // Generate salt
                    String salt = argon.generateSalt();
                    connect.updateSalt(email, salt);

                    // Hash password - Builder pattern
                    String hash = Argon2Factory.create()
                            .setIterations(2)
                            .setMemory(14)
                            .setParallelism(1)
                            .hash(password, salt);
                    connect.updatePass(email, hash);

                    mail.sendmail(email, "Password rest Request.", "Dear " + fname
                            + "\n\n Your new password is " + newpassword + ". Please Keep it safe!");
                    frame.dispose();
                }
                else {
                    System.out.println("wrong details");
                }
            }

        });
        btnSubmit.setBounds(309, 160, 117, 29);
        frame.getContentPane().add(btnSubmit);


        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        btnCancel.setBounds(19, 160, 117, 29);
        frame.getContentPane().add(btnCancel);
    }
}
