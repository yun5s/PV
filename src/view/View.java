package view;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import model.Model;

import static com.sun.tools.internal.xjc.reader.Ring.add;

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
    private JProgressBar progressBar;
    private JRadioButton backwardsICSR;
    private JRadioButton forwardsICSR;
    private JLabel chooseConversion;
    private JTabbedPane tabbedPane1;
    private JRadioButton backwardsACK;
    private JRadioButton forwardsACK;
    private JLabel yourSelectedInputFile;
    private JLabel yourSelectedOutputFile;
    private JLabel logoLabel;
    private JLabel inputLabel;
    private Model model;
    private Controller controller;


    public View(Model model, Controller controller) throws IOException {

        Font font = welcomeTextField.getFont();

        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        welcomeTextField.setFont(font.deriveFont(attributes));

        this.model = model;
        this.controller = controller;

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(forwardsICSR);
        radioGroup.add(backwardsICSR);
        radioGroup.add(forwardsACK);
        radioGroup.add(backwardsACK);



//        logo = new Logo();
//        logo.setPreferredSize(new Dimension(300,100));	//create the size of the boat panel
//    tabbedPane1.setopaque(true);							//not opaque
        tabbedPane1.setBackground( new Color(226,235,220, 80) );

//        logoLabel.add(logo);

//        try {
//          setBackgroundImage();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        setLogoImage();
    setInputImage();


//        java.util.Timer t = new Timer()

//        ImageIcon  = new ImageIcon(http://megaicons.net/static/img/icons_sizes/8/178/256/folders-folder-icon.png));
//        jLabel2.setIcon(imgThisImg);


        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    model.pickInputFile();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                if(model.getChosenInputFile() == null || (model.getChosenInputFile() != null && ("".equals(model.getChosenOutputFile()))))
                        {
                    yourSelectedInputFile.setText("Please Select An Input File");
                }
                else{ yourSelectedInputFile.setText(model.getChosenInputFileName());
                    progressBar.setValue(40);
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

                if(model.getChosenOutputFile() == null){
                    yourSelectedOutputFile.setText("Please Select An Output File");
                }
                else{ yourSelectedOutputFile.setText(model.getChosenOutputFileName());
                    progressBar.setValue(60);
                }
            }
        });




        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if(!(model.getChosenInputFile() == null) && !(model.getChosenOutputFile() == null)) {

                    progressBar.setValue(100);

                    JOptionPane.showMessageDialog(null, "Transformation has been successful!");

                }else if (!inputButton.isSelected() && !outputButton.isSelected()) {

                    JOptionPane.showMessageDialog(null, "Please select your input and output files");

                }else{
                    JOptionPane.showMessageDialog(null, "Transformation has failed please restart application.");}






                if (getBackwardsICSR().isSelected()) {
                    System.out.println(" backwardsICSR has been selected fam.");
                    try {
                        model.transformerDownICSR();
                        System.out.println("Conversion has been successful");
                    } catch (TransformerException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    }
//

                }

                else if(getForwardsICSR().isSelected()){
                    System.out.println(" ForwardsICSR has been selected fam.");

                    try {
                        model.transformerUpICSR();
                        System.out.println("Conversion has been successful");
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
                        System.out.println("Conversion has been successful");
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
                        System.out.println("Conversion has been successful");
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




    public void setLogoImage(){

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/images/pvpharm.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        logoLabel.setSize(220,90);
        Image dimg = img.getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(),
                Image.SCALE_SMOOTH);

        ImageIcon imageIcon = new ImageIcon(dimg);

        logoLabel.setIcon(imageIcon);

    }

    public void setInputImage(){

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/images/input.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        inputButton.setSize(60,60);
        Image dimg = img.getScaledInstance(inputButton.getWidth(), inputButton.getHeight(),
                Image.SCALE_SMOOTH);

        ImageIcon imageIcon = new ImageIcon(dimg);

        inputButton.setIcon(imageIcon);

    }


}
