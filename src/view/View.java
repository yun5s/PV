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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import model.Model;
import org.xml.sax.SAXException;

/**
 * Created by MaiwandMaidanwal on 20/07/2017.
 */
public class View extends JFrame{


    private JButton convertButton;
    private JPanel panel1;
    private JButton inputButton;
    private JButton outputFolderButton;
    private JProgressBar progressBar;
    private JRadioButton backwardsICSR;
    private JRadioButton forwardsICSR;
    private JTabbedPane tabbedPane1;
    private JRadioButton backwardsACK;
    private JRadioButton forwardsACK;               //declare all fields
    private JLabel yourSelectedInputFile;
    private JLabel outputDestinationMessage;
    private JLabel welcomeLabel;
    private JButton logoButton;
    private JButton helpButton;
    private JLabel thumb;
    private JPanel panelInner;
    private JLabel chooseCon;
    private JLabel chooseOut;
    private JLabel chooseIn;
    private JLabel clickCon;
    private JSeparator northSep;
    private JSeparator eastSep;
    private JSeparator southSep;
    private JSeparator westSep;
    private String count;
    private JButton keyButton;
    private JButton transformsImageButton;
    private Model model;
    private int convertClicked;
    private boolean successCheck;
    private boolean noticeCheck;
    private ButtonGroup radioGroup;
    private JFrame frame;
    private Controller controller;
    private int previousCount;




    final URI uri = new URI("https://www.pvpharm.com/");

    class OpenUrlAction implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            open(uri);
        }
    }



    public View(Model model, Controller controller)  throws IOException, URISyntaxException {

        model.monthlyCleanse();         //clean the conversion count at start of each month.

        controller.openCalendarFile();
        controller.readCalendarFile();
        controller.closeFileX();

        previousCount = controller.getCalendarInfo();       //count of conversions that have happened so far.

        System.out.println("previous count is..."+ previousCount);

        count = String.valueOf(controller.getCalendarInfo());


        ImageIcon imageIcon = new ImageIcon(String.valueOf(Color.getHSBColor(195,41, 100)));

        convertButton.setIcon(imageIcon);

        
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


        tabbedPane1.setBackground(new Color(226, 235, 220, 80));


        setLogoImage();
        setInputImage();
        setFolderImage();
        setHelpImage();
        setConvertRollover();
        setWelcomeImage();
        setKeyImage();
        setTransformsImage();



        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    model.pickInputFile();
                    if (model.getChosenInputFile() == null || (model.getChosenInputFile() != null && ("".equals(model.getChosenInputFile())))) {
                        yourSelectedInputFile.setText("Please Select An Input File");
                    } else {

                        yourSelectedInputFile.setText("Number Of Files:   " + model.getNumberOfInputFiles());

                        progressBar.setValue(50);
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
                    model.pickFolder();                                     //firstly create blank output files

//                    outputDestinationMessage.setText(model.setOutputDestinationMessage());
                    outputDestinationMessage.setText("Output Destination Confirmed");

                    progressBar.setValue(75);

                        if (model.getfileExists() == true) {    //if output file exists, reset the GUI.

                            progressBar.setValue(0);
                            yourSelectedInputFile.setText("");
                            radioGroup.clearSelection();
                            outputDestinationMessage.setText("");
                        }

                    if (!convertButton.isSelected()) {
                        boolean b = model.getFileToSave().createNewFile() == false;
                    }


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

                } else if ((model.getInputFiles() == null) || (model.getfolderFilePaths() == null)) {

                    JOptionPane.showMessageDialog(null, "Please select your input and output files");

                } else {

                    try {
                        readInputFiles();
                        convertClicked++;
                        model.writeToConversionsFile(previousCount);
                        controller.openCalendarFile();
                        controller.readCalendarFile();
                        controller.closeFileX();

                        count = String.valueOf(controller.getCalendarInfo());


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

                progressBar.setValue(25);

            }
        });
        forwardsICSR.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                progressBar.setValue(25);

            }
        });
        backwardsACK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                progressBar.setValue(25);

            }
        });
        forwardsACK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                progressBar.setValue(25);

            }
        });


        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(null, "<HTML><h1 style=\"color:blue;\"><b>Welcome to the help section:</b></h1>" +
                        "<br><br> Below I will outline the necessary information about this program and explain step by step how to use it." +
                        "<br><br>" +
                        "The primary function of this program is to serve as a simple way to convert E2B files from R2 to R3" +
                        "<br>and vice versa. You may convert a single file or convert multiple files simultaneously." +
                        "<br><br> <b>IMPORTANT NOTICE:</b>  If you are converting multiple files make sure they are of the same type!" +
                        "<br><br><br><ol><li><b>Choosing conversion type:</b><br><br>Firstly you must select your conversion type. In order to do this you must find out if" +
                        "<br>you are converting ICSR files or Acknowledgement files. Next you should know if you" +
                        "<br> want forwards or backwards conversion.<br><br> <b>e.g.</b>" +
                        "    If you wish to convert an ICSR file from R2 to R3 files you must select 'ICSR Forwards.'</li>" +
                        "<br><br><li> <b>Choosing your input file:</b> <br><br> Select the files that you want to convert, they will most likely be in <b>XML</b> format." +
                        "<br>Bear in mind that all your input files MUST be of the same file type.Otherwise <br>Incorrect conversions will occur.</li>" +
                        "<br><br><li><b>Choose Output Destination:</b><br><br>" +
                        "Once you select the folder icon you can then navigate to your desired output location." +
                        "<br>This is where your output files will be created and stored<br<br><b>e.g.</b>  There is a default name 'CONVERT_' followed by your input file name" +
                        "<br>(you may change this if you wish)" +
                        "<br>If you want to save your output files to 'Desktop', you should navigate to that folder and press ok.</li>" +
                        "<br><br><li><b>Finally Click To Convert</b>, and you are done!");


            }
        });


        frame = new JFrame("Backwards and Forwards E2B Converter");


        try {
            URL resource = frame.getClass().getResource("/GUI_images/pvpharmIcon.png");
            BufferedImage image = ImageIO.read(resource);
            frame.setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setContentPane(getPanel1());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        exitingFrame();




        keyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                LicenseFrame licenseFrame = new LicenseFrame();

                licenseFrame.setContentPane(licenseFrame.getPanel1());
                licenseFrame.isOpaque();
                licenseFrame.pack();
                licenseFrame.setLocationRelativeTo(null);
                licenseFrame.setResizable(false);
                licenseFrame.setVisible(true);

            }
        });
        transformsImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(null, "Files transformed this month:   "+ count);

            }
        });
    }



    public javax.swing.JPanel getPanel1() {

        return panel1;
    }



    public void setLogoImage() {

        BufferedImage img = null;
        try {

//            img = ImageIO.read(new File(getClass().getResourceAsStream("/GUI_images/pvpharm.png").toURI()));

            img = ImageIO.read(View.class.getResource("/GUI_images/pvpharm.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        logoButton.setSize(220, 90);
        Image dimg = img.getScaledInstance(logoButton.getWidth(), logoButton.getHeight(),
                Image.SCALE_SMOOTH);

        ImageIcon imageIcon = new ImageIcon(dimg);

        logoButton.setIcon(imageIcon);
        logoButton.setBorderPainted(false);
        logoButton.setOpaque(false);
        logoButton.setBackground(Color.WHITE);
        logoButton.setToolTipText(uri.toString());
        logoButton.addActionListener(new OpenUrlAction());

    }

    public void setWelcomeImage(){

        BufferedImage img = null;
        try {
            img = ImageIO.read(View.class.getResource("/GUI_images/welcome.png"));

        }catch(IOException e){
            e.printStackTrace();
        }

        welcomeLabel.setSize(380, 30);

        Image dimg = img.getScaledInstance(welcomeLabel.getWidth(), welcomeLabel.getHeight(),
                Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(dimg);

        welcomeLabel.setIcon(image);

    }


    public void setConvertRollover(){

        BufferedImage img =null;
        BufferedImage img1 = null;

        try {
            img = ImageIO.read(View.class.getResource("/GUI_images/convert.png"));

            img1 = ImageIO.read(View.class.getResource("/GUI_images/convert copy.png"));



        } catch (IOException e) {
            e.printStackTrace();
        }

        convertButton.setSize(220, 75);

        Image dimg = img.getScaledInstance(convertButton.getWidth(), convertButton.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        convertButton.setIcon(imageIcon);

        Image dimg1 = img1.getScaledInstance(convertButton.getWidth(), convertButton.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon imageIcon1 = new ImageIcon(dimg1);
        convertButton.setRolloverIcon(imageIcon1);


    }

    public void setInputImage() {

        BufferedImage img = null;
        BufferedImage img1 = null;
        try {

            img = ImageIO.read(View.class.getResource("/GUI_images/input.png"));

            img1 = ImageIO.read(View.class.getResource("/GUI_images/input copy.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        inputButton.setSize(70, 70);
        Image dimg = img.getScaledInstance(inputButton.getWidth(), inputButton.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        inputButton.setIcon(imageIcon);


        Image dimg1 = img1.getScaledInstance(inputButton.getWidth(), inputButton.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon imageIcon1 = new ImageIcon(dimg1);

        inputButton.setRolloverIcon(imageIcon1);

    }

    public void setFolderImage() {

        BufferedImage img = null;
        BufferedImage img1 = null;
        try {

            img = ImageIO.read(View.class.getResource("/GUI_images/folder.png"));

            img1 = ImageIO.read(View.class.getResource("/GUI_images/folder copy.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }

        outputFolderButton.setSize(80, 70);
        Image dimg = img.getScaledInstance(outputFolderButton.getWidth(), outputFolderButton.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        outputFolderButton.setIcon(imageIcon);

        Image dimg1 = img1.getScaledInstance(outputFolderButton.getWidth(), outputFolderButton.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon imageIcon1 = new ImageIcon(dimg1);
        outputFolderButton.setRolloverIcon(imageIcon1);

    }

    public void setHelpImage() {

        BufferedImage img = null;
        try {

            img = ImageIO.read(View.class.getResource("/GUI_images/help.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }

        helpButton.setSize(20, 20);
        Image dimg = img.getScaledInstance(helpButton.getWidth(), helpButton.getHeight(),
                Image.SCALE_SMOOTH);

        ImageIcon imageIcon = new ImageIcon(dimg);

        helpButton.setIcon(imageIcon);
        helpButton.setBorderPainted(false);

    }


    public void setKeyImage() {

        BufferedImage img = null;
        try {

            img = ImageIO.read(View.class.getResource("/GUI_images/key.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }

        keyButton.setSize(20, 20);
        Image dimg = img.getScaledInstance(keyButton.getWidth(), keyButton.getHeight(),
                Image.SCALE_SMOOTH);

        ImageIcon imageIcon = new ImageIcon(dimg);

        keyButton.setIcon(imageIcon);
        keyButton.setBorderPainted(false);

    }

    public void setTransformsImage() {

        BufferedImage img = null;
        try {

            img = ImageIO.read(View.class.getResource("/GUI_images/transforms.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }

        transformsImageButton.setSize(20, 20);
        Image dimg = img.getScaledInstance(transformsImageButton.getWidth(), transformsImageButton.getHeight(),
                Image.SCALE_SMOOTH);

        ImageIcon imageIcon = new ImageIcon(dimg);

        transformsImageButton.setIcon(imageIcon);
        transformsImageButton.setBorderPainted(false);

    }






    public void readInputFiles() throws IOException {

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
                                "<br/><li>Either select your conversion type to be:     Acknowledgement's Backwards</li>" +
                                "<br/><li>Or change your input file to match your selected conversion type</li><html/>");

                        noticeCheck = true;
                    } else {


                        try {
                            model.transformerDownAck();
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
                                "<br/><li>Either select your conversion type to be:     Acknowledgement Forwards</li>" +
                                "<br/><li>Or change your input file to match your selected conversion type</li><html/>");
                        noticeCheck = true;

                    } else {


                        try {
                            model.transformerUpAck();
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
                                "<br/><li>Either select your conversion type to be:     ICSR Forwards</li>" +
                                "<br/><li> Or change your input file to match your selected conversion type</li><html/>");
                        noticeCheck = true;

                    } else {

                        try {
                            model.transformerUpICSR();
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
                                "<br/><li>Either select your conversion type to be:     ICSR Backwards</li>" +
                                "<br/><li> Or change your input file to match your selected conversion type</li><html/>");
                        noticeCheck = true;

                    } else {

                        try {
                            model.transformerDownICSR();
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




        if(successCheck==true){

             model.deletingWrongConversions();

            progressBar.setValue(100);
            JOptionPane.showMessageDialog(null, "Conversion is successful!");
            progressBar.setValue(0);
            outputDestinationMessage.setText("");
            yourSelectedInputFile.setText("");
            radioGroup.clearSelection();
            break;
            }

            if(noticeCheck == true){
                break;}

        }

    }




    public boolean getSuccessCheck(){
        return successCheck;
    }


    public int getConvertClicked() {
        return convertClicked;
    }


    private static void open(URI uri) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(uri);
            } catch (IOException e) { /* TODO: error handling */ }
        } else { /* TODO: error handling */ }
    }




    public void exitingFrame(){

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {


                if (JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to close this window?", "Really Closing?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {


                    if (getConvertClicked() == 0 && !(model.getfolderFilePaths() == null) || getConvertClicked() > 0 && getSuccessCheck()==false) {


                        for (File file : model.getOutputFiles()) {

                            BufferedReader br = null;

                            try {
                                br = new BufferedReader(new FileReader(file));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            //if the file is empty, Delete it.
                            try {
                                if (br.readLine() == null) {
                                    file.delete();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                    System.exit(0);

                }
            }
        });
    }

}

