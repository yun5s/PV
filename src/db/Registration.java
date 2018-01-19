package db;


import License.LicenseGen;
import License.PassGen;
import at.gadermaier.argon2.Argon2;
import at.gadermaier.argon2.Argon2Factory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import java.awt.Window;

public class Registration {
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



	Mailing email1 = new Mailing();
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
		frame.setBounds(200, 200, 650, 220);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblRegistration = new JLabel("Registration Form");
		lblRegistration.setBounds(24, 6, 112, 16);
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

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(24, 130, 78, 16);
		frame.getContentPane().add(lblPassword);


		JLabel amount = new JLabel("Limit Amount:");
		amount.setBounds(285, 70, 93, 16);

		frame.getContentPane().add(amount);

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

		PassGen passwordGenerator = new PassGen.PasswordGeneratorBuilder()
				.useDigits(true)
				.useLower(true)
				.useUpper(true)
				.build();
		String password = passwordGenerator.generate(8);

        JTextField pass=new JTextField(password);
        pass.setEditable(false);
        pass.setBounds(140, 130, 140, 16);
        pass.setBackground(null);
        pass.setBorder(null);
		frame.getContentPane().add(pass);


		/*passwordField = new JPasswordField();
		passwordField.setBounds(140, 130, 134, 28);
		frame.getContentPane().add(passwordField);
		*/

		amountField = new JTextField();
		amountField.setBounds(425, 70, 134, 28);
		frame.getContentPane().add(amountField);
		amountField.setColumns(10);

		companyField = new JTextField();
		companyField.setBounds(425, 40, 134, 28);
		frame.getContentPane().add(companyField);
		companyField.setColumns(10);

		JLabel lblSubscriptionType = new JLabel("Subscription Type");
		lblSubscriptionType.setBounds(285, 6, 124, 16);
		frame.getContentPane().add(lblSubscriptionType);

		/*JRadioButton rdbtnTrial = new JRadioButton("Trial-7 days");
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
		*/


		JButton btnSubmit = new JButton("Submit");
		
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fname = firstnameField.getText();
				String sname = surnameField.getText();
				String email = emailField.getText();
				String company = companyField.getText();

				@SuppressWarnings("deprecation")
				String pass = password; // get string from text field
				int amount= Integer.parseInt(amountField.getText());

				String type = "";
				int days = 0;

				;
				if(connect.checkUser(email)==false) {
					JOptionPane.showMessageDialog(null, "Submitted!");
					frame.dispose();

					connect.register(fname,sname,email,company,pass,days,amount);
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
