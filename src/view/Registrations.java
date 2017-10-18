package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Registrations extends JFrame{
    private JPanel RegistrationPane;
    private JPanel westLabelPanel;
    private JPanel eastTexFieldPanel;
    private JPanel southButtonsPanel;

    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton cancelButton;
    private JButton submittButton;
    private final static List<Contact> contactBook = new ArrayList<Contact>();

    public Registrations(){
        super("Registration Panel");
        firstNameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        firstNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contactBook.add(new Contact(firstNameField.getText(),lastNameField.getText(),emailField.getText(),passwordField.getText()));
                JOptionPane.showMessageDialog(null,"first name is : "+contactBook.get(0).getFirstname(),
                        "Contact #1",JOptionPane.PLAIN_MESSAGE);
            }
        });

        submittButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fname = firstNameField.getText();
                String sname = lastNameField.getText();
                String email = emailField.getText();
                @SuppressWarnings("deprecation")
                String pass = passwordField.getText(); // get string from text field
            }
        });
    }
}
