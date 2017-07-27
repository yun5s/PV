package view;

import controller.Controller;

import javax.swing.*;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import model.Model;

/**
 * Created by MaiwandMaidanwal on 20/07/2017.
 */
public class View{
    private JButton convertButton;
    private JPanel panel1;
    private JTextField welcomeTextField;
    private JButton inputButton;
    private JButton outputButton;
    private JButton outputFolderButton;
    private JProgressBar progressBar1;
    private JRadioButton backwardsICSR;
    private JRadioButton forwardsICSR;
    private JLabel chooseConversion;
    private JTabbedPane tabbedPane1;
    private JRadioButton backwardsACK;
    private JRadioButton forwardsACK;
    private Model model;
    private Controller controller;

    public View(Model model, Controller controller) {

        Font font = welcomeTextField.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        welcomeTextField.setFont(font.deriveFont(attributes));

        this.model = model;
        this.controller = controller;

        ButtonGroup groupICSR = new ButtonGroup();
        groupICSR.add(forwardsICSR);
        groupICSR.add(backwardsICSR);

        ButtonGroup groupACK = new ButtonGroup();
        groupACK.add(forwardsACK);
        groupACK.add(backwardsACK);


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

                try {
                    model.pickInputFile();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        });

        outputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    model.pickOutputFile();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });


        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if (getBackwardsICSR().isSelected()) {
                    System.out.println(" backwardsICSR has been selected fam.");
                    try {
                        model.transformerDownICSR();
                    } catch (TransformerException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    }
//                    if (!inputButton.isSelected() && !outputButton.isSelected()) {
//                        System.out.println("Please select your input and output files");
//                    }

                }

                else if(getForwardsICSR().isSelected()){
                    System.out.println(" ForwardsICSR has been selected fam.");

                    try {
                        model.transformerUpICSR();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    } catch (TransformerException e1) {
                        e1.printStackTrace();
                    }
                }

                else if (getBackwardsACK().isSelected()){

                    try {
                        model.transformerDownAck();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    } catch (TransformerException e1) {
                        e1.printStackTrace();
                    }

                }

                else if (getForwardsACK().isSelected()){

                    try {
                        model.transformerUpAck();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    } catch (TransformerException e1) {
                        e1.printStackTrace();
                    }

                } else{
            System.out.println("Please select your option first.");
              }

            }
        });



    }



            public javax.swing.JPanel getPanel1() {

                return panel1;
            }

            public JRadioButton getBackwardsICSR() {
                return backwardsICSR;
            }

            public JRadioButton getForwardsICSR() {
                return forwardsICSR;
            }


            public JRadioButton getBackwardsACK() {
                return backwardsACK;
            }

            public JRadioButton getForwardsACK() {
                return forwardsACK;
            }


}
