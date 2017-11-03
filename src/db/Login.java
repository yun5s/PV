
package db;

import view.View;

import java.awt.EventQueue;

import javax.swing.*;
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Login {

	public JFrame frame;
	private JTextField userField;
	private JPasswordField passwordField;
	/**
	 * Launch the application.
	 */
	//DBconnect connect1 = new DBconnect();
	public DBconnect connect =new DBconnect();
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private Boolean a  ;

    public void setA(Boolean a) {
        this.a = a;
    }


    public Boolean getLog(){
	    return a;
    }

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 200, 450, 300);
	//	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel logining_in = new JLabel("Please Login");
		logining_in.setBounds(173, 30, 94, 16);
		frame.getContentPane().add(logining_in);
		
		JLabel txtUsername = new JLabel("Email:");
		txtUsername.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		txtUsername.setBounds(67, 91, 81, 16);
		frame.getContentPane().add(txtUsername);
		
		JLabel txtPassword = new JLabel("Password:");
		txtPassword.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		txtPassword.setBounds(67, 131, 81, 16);
		frame.getContentPane().add(txtPassword);
		
		userField = new JTextField();
		userField.setBounds(173, 85, 134, 28);
		frame.getContentPane().add(userField);
		userField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(174, 125, 133, 28);
		frame.getContentPane().add(passwordField);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("deprecation")
				String password = passwordField.getText();
				String username = userField.getText();


				String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(username);
				if (!matcher.matches()) {
					JOptionPane.showMessageDialog(null,"Email not valid ");

				}
				//------------------------------------------------------------
				//disable/ enable login button
		/*		userField.getDocument().addDocumentListener(new DocumentListener() {
					  public void changedUpdate(DocumentEvent e) {
					    changed();
					  }
					  public void removeUpdate(DocumentEvent e) {
					    changed();
					  }
					  public void insertUpdate(DocumentEvent e) {
					    changed();
					  }

					  public void changed() {
					     if (userField.getText().equals("")){
					       btnLogin.setEnabled(false);
					     }
					     else {
					       btnLogin.setEnabled(true);
					    }

					  }
					});*/
				//------------------------------------------------------------
				if(connect.checkLogin(username,password)== true) {
					JOptionPane.showMessageDialog(null, "You have logged in!");
					setA(true);
					frame.dispose();
				}else{
				    setA(false);
					JOptionPane.showMessageDialog(null, "Wrong Email or Password, please try again?!");
				}
			}
		});



		btnLogin.setBounds(173, 165, 117, 29);
		frame.getContentPane().add(btnLogin);
		
		JButton btnReset = new JButton("Clear");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userField.setText(null);
				passwordField.setText(null);
			}
		});
		btnReset.setBounds(269, 217, 117, 29);
		frame.getContentPane().add(btnReset);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Registration rgf = new Registration();
				rgf.frame.setVisible(true);
				rgf.frame.setLocationRelativeTo(null);
				rgf.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		btnRegister.setBounds(67, 217, 117, 29);
		frame.getContentPane().add(btnRegister);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(6, 193, 438, 12);
		frame.getContentPane().add(separator);
	}
}
