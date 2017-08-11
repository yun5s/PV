package view;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import model.Model;
import org.xml.sax.SAXException;

import static com.sun.tools.internal.xjc.reader.Ring.add;

/**
 * Created by MaiwandMaidanwal on 20/07/2017.
 */
public class View {
    private JButton convertButton;
    private JPanel panel1;
    private JButton inputButton;
    private JButton outputFolderButton;
    private JProgressBar progressBar;
    private JRadioButton backwardsICSR;
    private JRadioButton forwardsICSR;
    private JLabel chooseConversion;
    private JTabbedPane tabbedPane1;
    private JRadioButton backwardsACK;
    private JRadioButton forwardsACK;
    private JLabel yourSelectedInputFile;
    private JLabel logoLabel;
    private JLabel outputDestinationMessage;
    private JLabel welcomeLabel;
    private JLabel helpLabel;
    private Model model;
    private Controller controller;
    private int convertClicked;
    private boolean successCheck;

    public ButtonGroup getRadioGroup() {
        return radioGroup;
    }

    private ButtonGroup radioGroup;


    public View(Model model, Controller controller) throws IOException {

        Font font = welcomeLabel.getFont();

        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        welcomeLabel.setFont(font.deriveFont(attributes));

        this.model = model;
        this.controller = controller;

        radioGroup = new ButtonGroup();
        radioGroup.add(forwardsICSR);
        radioGroup.add(backwardsICSR);
        radioGroup.add(forwardsACK);
        radioGroup.add(backwardsACK);


//        logo = new Logo();
//        logo.setPreferredSize(new Dimension(300,100));	//create the size of the boat panel
//    tabbedPane1.setopaque(true);							//not opaque
        tabbedPane1.setBackground(new Color(226, 235, 220, 80));

//        logoLabel.add(logo);

//        try {
//          setBackgroundImage();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        setLogoImage();
        setInputImage();
        setFolderImage();
        setHelpImage();


//        java.util.Timer t = new Timer()

//        ImageIcon  = new ImageIcon(http://megaicons.net/static/img/icons_sizes/8/178/256/folders-folder-icon.png));
//        jLabel2.setIcon(imgThisImg);


        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    model.pickInputFile();
                    if (model.getChosenInputFile() == null || (model.getChosenInputFile() != null && ("".equals(model.getChosenInputFile())))) {
                        yourSelectedInputFile.setText("Please Select An Input File");
                    } else {
                        yourSelectedInputFile.setText(model.getChosenInputFileNames());
                        progressBar.setValue(40);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


            }

        });


        outputFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.pickFolder();
                    if (!convertButton.isSelected()) {
                        boolean b = model.getFileToSave().createNewFile() == false;
                    }
                    outputDestinationMessage.setText("You have chosen your output folder");
                    progressBar.setValue(75);

//                    model.createNewBlankFile();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });


        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!(backwardsICSR.isSelected() || forwardsICSR.isSelected() || backwardsACK.isSelected() || forwardsACK.isSelected())) {

                    JOptionPane.showMessageDialog(null, "Please select your conversion type");

                } else if ((model.getChosenInputFile() == null) || (model.getfolderFilePath() == null)) {

                    JOptionPane.showMessageDialog(null, "Please select your input and output files");

                } else {

                    try {
                        readInputFiles();
                        convertClicked++;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }
            }

        });


        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }


        backwardsICSR.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                progressBar.setValue(20);

            }
        });
        forwardsICSR.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                progressBar.setValue(20);

            }
        });
        backwardsACK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                progressBar.setValue(20);

            }
        });
        forwardsACK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                progressBar.setValue(20);

            }
        });
    }


    public javax.swing.JPanel getPanel1() {

        return panel1;
    }

    public JButton getConvertButton() {
        return convertButton;
    }


//
//            public void setBackgroundImage() throws IOException {
//
//
//                JLabel label = new JLabel(new ImageIcon(ImageIO.read()));
//                label.setLayout(new BorderLayout());
//                panel1.add(label);
//
//            }
//


    public void setLogoImage() {

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/images/pvpharm.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        logoLabel.setSize(220, 90);
        Image dimg = img.getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(),
                Image.SCALE_SMOOTH);

        ImageIcon imageIcon = new ImageIcon(dimg);

        logoLabel.setIcon(imageIcon);

    }

    public void setInputImage() {

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/images/input.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        inputButton.setSize(70, 70);
        Image dimg = img.getScaledInstance(inputButton.getWidth(), inputButton.getHeight(),
                Image.SCALE_SMOOTH);

        ImageIcon imageIcon = new ImageIcon(dimg);

        inputButton.setIcon(imageIcon);

    }

    public void setFolderImage() {

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/images/folder.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        outputFolderButton.setSize(70, 70);
        Image dimg = img.getScaledInstance(outputFolderButton.getWidth(), outputFolderButton.getHeight(),
                Image.SCALE_SMOOTH);

        ImageIcon imageIcon = new ImageIcon(dimg);

        outputFolderButton.setIcon(imageIcon);

    }

    public void setHelpImage() {

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/images/help.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        helpLabel.setSize(30, 30);
        Image dimg = img.getScaledInstance(helpLabel.getWidth(), helpLabel.getHeight(),
                Image.SCALE_SMOOTH);

        ImageIcon imageIcon = new ImageIcon(dimg);

        helpLabel.setIcon(imageIcon);

    }


    public boolean readInputFiles() throws IOException {

        successCheck = false;

        for (File file : model.getInputFiles()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

            String line = null;


            while ((line = reader.readLine()) != null) {

                if (line.contains("<MCCI_IN200101UV01 ITSVersion=\"XML_1.0\" xsi:schemaLocation=\"urn:hl7-org:v3 multicacheschemas/MCCI_IN200101UV01.xsd\" xmlns=\"urn:hl7-org:v3\" xmlns:fo=\"http://www.w3.org/1999/XSL/Format\" xmlns:mif=\"urn:hl7-org:v3/mif\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">")) {

                    if (!backwardsACK.isSelected()) {

                        JOptionPane.showMessageDialog(null, "<html>Your input file   " + file.getName() +
                                "   is an Acknowledgement's file in R3 format" +
                                "<br/><br/>What to do?<br/>" +
                                "<br/>Either select your conversion type to be:     Acknowledgement's Backwards" +
                                "<br/> Or change your input file to match your selected conversion type<html/>");
                    } else {


                        try {
                            model.transformerDownAck();
                            JOptionPane.showMessageDialog(null, "Conversion is successful!");
                            progressBar.setValue(100);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (TransformerException e1) {
                            e1.printStackTrace();
                        } catch (SAXException e1) {
                            e1.printStackTrace();
                        } catch (ParserConfigurationException e1) {
                            e1.printStackTrace();
                        }
                            successCheck = true;

                    }
                }



                else if (line.contains("ich-icsrack-v1_1.dtd")) {
                    if (!forwardsACK.isSelected()) {

                        JOptionPane.showMessageDialog(null, "<html>Your input file   " + file.getName() +
                                "   is an Acknowledgement's file in R2 format" +
                                "<br/><br/>What to do?<br/>" +
                                "<br/>Either select your conversion type to be:     Acknowledgement Forwards" +
                                "<br/> Or change your input file to match your selected conversion type<html/>");

                        break;
                    } else {


                        try {
                            model.transformerUpAck();
                            progressBar.setValue(100);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (TransformerException e1) {
                            e1.printStackTrace();
                        } catch (SAXException e1) {
                            e1.printStackTrace();
                        } catch (ParserConfigurationException e1) {
                            e1.printStackTrace();
                        }
                        successCheck = true;

                    }

                } else if (line.contains("ich-icsr-v2-1.dtd")) {
                    if (!forwardsICSR.isSelected()) {
                        JOptionPane.showMessageDialog(null, "<html>Your input file   " + file.getName() +
                                "   is an ICSR file in R2 format" +
                                "<br/><br/>What to do?<br/>" +
                                "<br/>Either select your conversion type to be:     ICSR Forwards" +
                                "<br/> Or change your input file to match your selected conversion type<html/>");
                    } else {

                        try {
                            model.transformerUpICSR();
                            JOptionPane.showMessageDialog(null, "Conversion is successful!");
                            progressBar.setValue(100);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (TransformerException e1) {
                            e1.printStackTrace();
                        } catch (SAXException e1) {
                            e1.printStackTrace();
                        } catch (ParserConfigurationException e1) {
                            e1.printStackTrace();
                        }
                        successCheck = true;


                    }
                } else if (line.contains("<MCCI_IN200100UV01 ITSVersion=\"XML_1.0\" xsi:schemaLocation=\"urn:hl7-org:v3 multicacheschemas/MCCI_IN200100UV01.xsd\" xmlns=\"urn:hl7-org:v3\" xmlns:mif=\"urn:hl7-org:v3/mif\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">")) {
                    if (!backwardsICSR.isSelected()) {
                        JOptionPane.showMessageDialog(null, "<html>Your input file   " + file.getName() +
                                "   is an ISCR file in R3 format" +
                                "<br/><br/>What to do?<br/>" +
                                "<br/>Either select your conversion type to be:     ICSR Backwards" +
                                "<br/> Or change your input file to match your selected conversion type<html/>");
                    } else {

                        try {
                            model.transformerDownICSR();
                            JOptionPane.showMessageDialog(null, "Conversion is successful!");
                            progressBar.setValue(100);
                        } catch (TransformerException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (SAXException e1) {
                            e1.printStackTrace();
                        } catch (ParserConfigurationException e1) {
                            e1.printStackTrace();
                        }
                        successCheck = true;

                    }
                }


            }
            reader.close();
        }

        if(successCheck==true){
            JOptionPane.showMessageDialog(null, "Conversion is successful!");
        }
        return false;

    }


    public int getConvertClicked() {
        return convertClicked;
    }

}

