package db;


import License.Cryptography;
import License.LicenseGen;
import at.gadermaier.argon2.Argon2;
import at.gadermaier.argon2.Argon2Factory;
import view.LicenseFrame;

import java.awt.EventQueue;
//import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class Registration {
	DBconnect connect = new DBconnect();
	LicenseGen generate = new LicenseGen();

	public JFrame frame;
	private JTextField firstnameField;
	private JTextField surnameField;
	private JTextField emailField;
	//private JTextField userField;
	private JPasswordField passwordField;

	Filewr ff = new Filewr();
	Mailing email1 = new Mailing();
	Cryptography crypto = new Cryptography();
	/**
	 *
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */
	public Registration() {
		initialize();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private ButtonGroup group = new ButtonGroup();



	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 200, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblRegistration = new JLabel("Registration Form");
		lblRegistration.setBounds(24, 6, 112, 16);
		frame.getContentPane().add(lblRegistration);
		
		JLabel lblFirstname = new JLabel("Firstname:");
		lblFirstname.setBounds(24, 47, 78, 16);
		frame.getContentPane().add(lblFirstname);
		
		JLabel lblSurname = new JLabel("Surname: ");
		lblSurname.setBounds(24, 75, 78, 16);
		frame.getContentPane().add(lblSurname);
		
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(24, 198, 78, 16);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblEmailAddress = new JLabel("Email Address:");
		lblEmailAddress.setBounds(24, 110, 93, 16);
		frame.getContentPane().add(lblEmailAddress);
		
		firstnameField = new JTextField();
		firstnameField.setBounds(139, 41, 134, 28);
		frame.getContentPane().add(firstnameField);
		firstnameField.setColumns(10);

		
		surnameField = new JTextField();
		surnameField.setBounds(139, 75, 134, 28);
		frame.getContentPane().add(surnameField);
		surnameField.setColumns(10);

		
		emailField = new JTextField();
		emailField.setBounds(139, 104, 134, 28);
		frame.getContentPane().add(emailField);
		emailField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(139, 192, 134, 28);
		frame.getContentPane().add(passwordField);


		JLabel lblSubscriptionType = new JLabel("Subscription Type");
		lblSubscriptionType.setBounds(285, 6, 124, 16);
		frame.getContentPane().add(lblSubscriptionType);
		
		JRadioButton rdbtnTrial = new JRadioButton("Trial-7 days");
		rdbtnTrial.setBounds(285, 43, 141, 23);
		frame.getContentPane().add(rdbtnTrial);
		
		JRadioButton rdbtnMonthly = new JRadioButton("Monthly-30 days");
		rdbtnMonthly.setBounds(285, 106, 141, 23);
		frame.getContentPane().add(rdbtnMonthly);
		
		JRadioButton rdbtnAnually = new JRadioButton("Annually-360 days");
		rdbtnAnually.setBounds(288, 174, 156, 23);
		frame.getContentPane().add(rdbtnAnually);
		
		JLabel lblFree = new JLabel("Free");
		lblFree.setBounds(309, 64, 61, 16);
		frame.getContentPane().add(lblFree);
		
		JLabel lblPerMonth = new JLabel("€30 per Month ");
		lblPerMonth.setBounds(310, 127, 99, 16);
		frame.getContentPane().add(lblPerMonth);
		
		JLabel lblPerYear = new JLabel("€330 per Year");
		lblPerYear.setBounds(309, 198, 99, 16);
		frame.getContentPane().add(lblPerYear);
		
		group.add(rdbtnTrial);
		group.add(rdbtnMonthly);
		group.add(rdbtnAnually);
		
		JButton btnSubmit = new JButton("Submit");
		
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fname = firstnameField.getText();
				String sname = surnameField.getText();
				String email = emailField.getText();
				@SuppressWarnings("deprecation")
				String pass = passwordField.getText(); // get string from text field
				String type = "";
				int days = 0;
				
				if(rdbtnTrial.isSelected()) {
					type = "T";
					days = 7;
				}
				if(rdbtnMonthly.isSelected()) {
					type = "M";
					days = 30;
				}
				if(rdbtnAnually.isSelected()) {
					type = "Y";
					days = 360;
				}
				;
				if(connect.checkUser(email)==false) {
					JOptionPane.showMessageDialog(null, "Submitted!");
					frame.dispose();

					connect.register(fname,sname,email,pass,type,days);
					String val = connect.getValues(email);
					val = generate.createLicenseKey(val,fname,type);
					connect.updateData(email,val);
					System.out.println(val);
					email1.sendmail(email,"Thank you for using PVpharm Converter ","Dear "+fname
							+ "\n\nThank you for purchasing our product, you License key is "+ "\n\n" +val
							+ "\n\n" + "The license wil expire on "+ connect.getDate(email)
							+ " For anyother enquires, please visit www.pvpharm.com or Email yun@PVpharm.com"
							+ "\n\n\n"
							+ "\n Yun \n  PVpharm Technology Team"
					);

					ff.mWrite(email);
					ff.fWrite(email);
					Argon2 argon = new Argon2();
					char[] password = pass.toCharArray();

					// Generate salt
					String salt = argon.generateSalt();
					connect.updateSalt(email,salt);

					// Hash password - Builder pattern
					String hash = Argon2Factory.create()
							.setIterations(2)
							.setMemory(14)
							.setParallelism(1)
							.hash(password, salt);
					connect.updatePass(email,hash);

				}else {
					JOptionPane.showConfirmDialog(null, "Invalid input Details", "Registration Error", JOptionPane.ERROR_MESSAGE);

					emailField.setText(null);
					passwordField.setText(null);

				}				
			}
		});
		btnSubmit.setBounds(309, 243, 117, 29);
		frame.getContentPane().add(btnSubmit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnCancel.setBounds(19, 243, 117, 29);
		frame.getContentPane().add(btnCancel);
	}
}
