package view;

import controller.Controller;

import javax.swing.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import model.Model;

/**
 * Created by MaiwandMaidanwal on 20/07/2017.
 */
public class View {
    private JButton convertButton;
    private JPanel panel1;
    private JTextField welcomeTextField;
    private JButton inputButton;
    private JButton outputButton;
    private JButton outputFolderButton;
    private JProgressBar progressBar1;
    private JRadioButton backwards;
    private JRadioButton forwards;
    private JLabel chooseConversion;
    private JTabbedPane tabbedPane1;
    private JRadioButton backwardsR3ToR2RadioButton;
    private JRadioButton forwardsR2ToR3RadioButton;
    private Model model;

    public View() {

        Font font = welcomeTextField.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        welcomeTextField.setFont(font.deriveFont(attributes));

        model = new Model();

        ButtonGroup group = new ButtonGroup();
        group.add(forwards);
        group.add(backwards);

//        java.util.Timer t = new Timer()

//        ImageIcon  = new ImageIcon(http://megaicons.net/static/img/icons_sizes/8/178/256/folders-folder-icon.png));
//        jLabel2.setIcon(imgThisImg);

        ImageIcon water = new ImageIcon("http://megaicons.net/static/img/icons_sizes/8/178/256/folders-folder-icon.png");
        outputFolderButton = new JButton(water);

        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(null, "You have clicked the button");
            }
        });

        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Controller of = new Controller();

                try {
                    of.pickFile();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        });
        outputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Controller of = new Controller();

                try {
                    of.pickFile();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });


        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    model.transform();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                } catch (TransformerException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public javax.swing.JPanel getPanel1(){

        return panel1;
    }

    public JRadioButton getBackwards() {
        return backwards;
    }

    public JRadioButton getForwards() {
        return forwards;
    }


    public void conversionType() throws IOException, URISyntaxException, TransformerException {

        if(backwards.isSelected()){

            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(new File("downgrade-icsr.xsl"));
            Transformer transformer = factory.newTransformer(xslt);
        }

        else if(forwards.isSelected())


    }
}
