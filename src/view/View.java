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

import db.DBconnect;
import db.Filewr;
import db.Login;
import db.Registration;
import model.Model;
import org.xml.sax.SAXException;

/**
 * Created by MaiwandMaidanwal on 20/07/2017.
 */


/**
 * This is the View class, where everything to do with the Interface for the user is created and done.
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
    private JButton loginButton;
    private JPanel bpanel;
    private Model model;
    private int convertClicked;
    private boolean successCheck;
    private boolean noticeCheck;
    private ButtonGroup radioGroup;
    private JFrame frame;
    private Controller controller;
    private int previousCount;
    private int currentCount;

    Filewr ff = new Filewr();


    //URL link to the pvpharm website, when you click the logo.
    final URI uri = new URI("https://www.pvpharm.com/");

    class OpenUrlAction implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            open(uri);
        }
    }


    /**
     * There are several methods being called here in the constructor. I also have many listeners here
     * I understand that this is not strictly following the orthodox MVC style, (since they should all be in the controller class)
     * but during the development of the application I found it to be easier and the code to work better if I had the listeners
     * here in the constructor.
     */
    public View(Model model, Controller controller)  throws IOException, URISyntaxException {

        model.monthlyCleanse();         //clean the conversion count at start of each month.

       // controller.openCalendarFile();
      //  controller.readCalendarFile();
        //controller.closeFileX();

        previousCount = Integer.parseInt(model.getCount());       //count of conversions that have happened so far.

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


        radioGroup = new ButtonGroup();            //grouping buttons, so only 1 is selected at a time.
        radioGroup.add(forwardsICSR);
        radioGroup.add(backwardsICSR);
        radioGroup.add(forwardsACK);
        radioGroup.add(backwardsACK);


        tabbedPane1.setBackground(new Color(226, 235, 220, 80));


        setLogoImage();                     //all image setting methods are called here.
        setInputImage();
        setFolderImage();
        setHelpImage();
        setConvertRollover();
        setWelcomeImage();
        setKeyImage();
        setTransformsImage();


        /**
         * Do what is inside following the click of the input button.
         */
        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    //Calling this method for picking the inputs.
                    model.pickInputFile();

                    //I have conditions in place, below saying basically if no input file is selected.
                    if (model.getChosenInputFile() == null || (model.getChosenInputFile() != null && ("".equals(model.getChosenInputFile())))) {
                        yourSelectedInputFile.setText("Please Select An Input File");
                    } else {


                        //but if it is selected then.... state the number of files.
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


        /**
         * there are a few conditions which I have set, before the actual conversion happens.
         */
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBconnect db = new DBconnect();
                int i = db.getActStatus(ff.mRead());//get value from database, checks for the data file here. if found, use the data for validation
                int j = db.getKeyStatus(ff.fRead());

            //TODO*****

             /*   if(i ==0 && j ==0) {

                    if(i ==0 ) {

                        JTextField xField = new JTextField(5);
                        JTextField yField = new JTextField(5);

                        JPanel myPanel = new JPanel();
                        myPanel.add(new JLabel("To activate the product, please enter the activation key"));
                        myPanel.add(xField);
                        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                        myPanel.add(new JLabel("Please verify your email:"));
                        myPanel.add(yField);

                        int result = JOptionPane.showConfirmDialog(null, myPanel,
                                "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
                        String key = xField.getText();
                        String email = yField.getText();

                        System.out.println(key);
                        System.out.println(email);



                        if (db.checkKeymail(key, email) == true) {
                            i = 1;
                            JOptionPane.showMessageDialog(frame,
                                    "Verified.",
                                    "Verification",
                                    JOptionPane.INFORMATION_MESSAGE);
                            ff.kWrite(key);
                        } else {
                            JOptionPane.showMessageDialog(frame,
                                    "Invalid key or email address!",
                                    "Verification",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }

                        i=1;
                }else{

                   */ //First at least one of the radio buttons must be selected.
                    if (!(backwardsICSR.isSelected() || forwardsICSR.isSelected() || backwardsACK.isSelected() || forwardsACK.isSelected())) {

                        JOptionPane.showMessageDialog(null, "Please select your conversion type");
                        //also the input files must be selected, and the selected output place must exist.
                    } else if ((model.getInputFiles() == null) || (model.getfolderFilePaths() == null)) {

                        JOptionPane.showMessageDialog(null, "Please select your input and output files");

                    } else {
                        int a = model.getNumberOfInputFiles();
                        currentCount = Integer.parseInt(model.getCount())+a;
                        //now you can convert.
                        try {
                            readInputFiles();
                            convertClicked++;
                            model.writeCount(currentCount);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    }
                }


        });


        //set the look and feel to the default way, of the operating system this application is being run on.
        //I spent more time on the actual program, than the GUI, so this is helpful.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }


        //setting the progress bar after an option is clicked

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


        // must use this way for getting an image, so that it also loads with ought issue in JAR

        try {
            URL resource = frame.getClass().getResource("/GUI_images/pvpharmIcon.png");
            BufferedImage image = ImageIO.read(resource);
            frame.setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setContentPane(getPanel1());

        //so that clicking "No" and the X button wont close the application
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack();

        //to open application in centre screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        exitingFrame();


        /**
         * Open up the JFrame for asking user for their license key.
         */
        keyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBconnect db = new DBconnect();
                String ss = JOptionPane.showInputDialog(frame, "To activate the product, please enter the activation key");
                if (db.checkKey(ss) == true) {
                    JOptionPane.showMessageDialog(frame,
                            "Verified.",
                            "Verification",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Invalid!.",
                            "Verification",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }

        });
        transformsImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // controller.openCalendarFile();
                //controller.readCalendarFile();
                //count = String.valueOf(controller.getCalendarInfo());

                JOptionPane.showMessageDialog(null, "Files transformed this month:   "+ model.getCount());

            }
        });
        loginButton.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.resetCount();
                    System.out.println("pre4 is " +previousCount);
                    System.out.println("reseted, count is "+ model.getCount());

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

               /* Login log = new Login();s
                log.frame.setVisible(true);
                log.frame.setLocationRelativeTo(null);
                log.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                //TODO*
                // if correct, remove log , add "welcome - user" , else
              /*  if(log.getLog()){
                    loginButton.setVisible(false);
                }*/
            }
        });
    }



    public javax.swing.JPanel getPanel1() {

        return panel1;
    }



    public void setLogoImage() {

        BufferedImage img = null;
        try {
            img = ImageIO.read(View.class.getResource("/GUI_images/pvpharm.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        //make sure the image is relative to the button dimensions, and scaled accordingly.
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


    /**
     * This method is important because it reads the input files and is responsible for sorting them and giving the user the output message.
     *
     * It checks the specific lines I have set with which it can tell if its an R2 or R3 file, ICSR or Acknowdedgement.
     *
     * currently I have set it so that if the user selects the incorrect option e.g ICSR forward....but a few of the files
     * are in ICSR backwards... then it will only display the message one time, for one of the files.
     * I trust that the user should know which file is which format and what they wish to convert.
     * * @throws IOException
     */

    public void readInputFiles() throws IOException {

        successCheck = false;

        for (File file : model.getInputFiles()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

            String line = null;


            while ((line = reader.readLine()) != null) {

                //check this specific line, because then it is a backwards acknowledgement file.
                // R3 ack
                if (line.contains("<MCCI_IN200101UV01 ITSVersion=\"XML_1.0\" xsi:schemaLocation=\"urn:hl7-org:v3 multicacheschemas/MCCI_IN200101UV01.xsd\" xmlns=\"urn:hl7-org:v3\" xmlns:fo=\"http://www.w3.org/1999/XSL/Format\" xmlns:mif=\"urn:hl7-org:v3/mif\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">")||line.contains("ichicsrack11xml.dtd")) {

                    //but if that button isnt selected
                    if (!backwardsACK.isSelected()) {

                        JOptionPane.showMessageDialog(null, "<html>Your input file   " + file.getName() +
                                "   is an Acknowledgement's file in R3 format" +
                                "<br/><br/>What to do?<br/>" +
                                "<br/><li>Either select your conversion type to be:     Acknowledgement's Backwards</li>" +
                                "<br/><li>Or change your input file to match your selected conversion type</li><html/>");

                        //we only need the notice one time, for usability purposes
                        noticeCheck = true;
                    } else {


                        //but if its all good, then run the transformation.

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


                // The exact same way has been done for the other 3 types of files.
                //ACk R2
                else if (line.contains("ich-icsrack-v1_1.dtd")||line.contains("ichicsrack11xml.dtd")) {
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

                    //R2 ICSR
                } else if (line.contains("ich-icsr-v2-1.dtd")||line.contains("icsr21xml.dtd")) {
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

                    //R3 ICSR
                } else if (line.contains("<MCCI_IN200100UV01 ITSVersion=\"XML_1.0\" xsi:schemaLocation=\"urn:hl7-org:v3 multicacheschemas/MCCI_IN200100UV01.xsd\" xmlns=\"urn:hl7-org:v3\" xmlns:mif=\"urn:hl7-org:v3/mif\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">")||line.contains("http://eudravigilance.ema.europa.eu/XSD/multicacheschemas/MCCI_IN200100UV01.xsd")) {
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



            //if the transformation was a success, delete the incorrect (blank files) conversions.

        if(successCheck==true) {

            model.deletingWrongConversions();


            // reset everything

            progressBar.setValue(100);
            JOptionPane.showMessageDialog(null, "Conversion is successful!");
            progressBar.setValue(0);
            outputDestinationMessage.setText("");
            yourSelectedInputFile.setText("");
            radioGroup.clearSelection();
            break;
        }else{
            System.out.println("conversion failed");

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


    /**
     * This method is for the closing of the program and making sure that when closing,
     * the program will automatically delete the blank files that in has created in the output destination
     * ONLY when those files have not been converted.
     *
     * This way it will NEVER delete the converted files.
     */

    public void exitingFrame(){

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {


                if (JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to close this window?", "Really Closing?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

                    // If the convert button hasnt even been clicked and the user is exiting...
                    //or if the convert button has been clicked, but the conversion has failed... then do this

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

