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
import java.io.File;
import java.io.IOException;
import java.util.*;
import model.Model;
import org.xml.sax.SAXException;

import static com.sun.tools.internal.xjc.reader.Ring.add;

/**
 * Created by MaiwandMaidanwal on 20/07/2017.
 */
public class View{
    private JButton convertButton;
    private JPanel panel1;
    private JTextField welcomeTextField;
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
    setFolderImage();


//        java.util.Timer t = new Timer()

//        ImageIcon  = new ImageIcon(http://megaicons.net/static/img/icons_sizes/8/178/256/folders-folder-icon.png));
//        jLabel2.setIcon(imgThisImg);


        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    model.pickInputFile();
                    if(model.getChosenInputFile() == null || (model.getChosenInputFile() != null && ("".equals(model.getChosenInputFile()))))
                    {
                        yourSelectedInputFile.setText("Please Select An Input File");
                    }
                    else{ yourSelectedInputFile.setText(model.getChosenInputFileName());
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

                if(!(backwardsICSR.isSelected() || forwardsICSR.isSelected() || backwardsACK.isSelected() || forwardsACK.isSelected())){

                    JOptionPane.showMessageDialog(null, "Please select your conversion type");

                }
                else if((model.getChosenInputFile() == null) || (model.getfolderFilePath() == null)) {

                    JOptionPane.showMessageDialog(null, "Please select your input and output files");

                }else {

                    if (getBackwardsICSR().isSelected()) {
                        try {
                            model.transformerDownICSR();
                            JOptionPane.showMessageDialog(null, "Conversion is successful!");
                        } catch (TransformerException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (SAXException e1) {
                            e1.printStackTrace();
                        } catch (ParserConfigurationException e1) {
                            e1.printStackTrace();
                        }
//

                    } else if (getForwardsICSR().isSelected()) {

                        try {
                            model.transformerUpICSR();
//                        model.readAndWriteToNewFile();
                            JOptionPane.showMessageDialog(null, "Conversion is successful!");

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (TransformerException e1) {
                            e1.printStackTrace();
                        } catch (SAXException e1) {
                            e1.printStackTrace();
                        } catch (ParserConfigurationException e1) {
                            e1.printStackTrace();
                        }
                    } else if (getBackwardsACK().isSelected()) {

                        try {
                            model.transformerDownAck();
                            JOptionPane.showMessageDialog(null, "Conversion is successful!");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (TransformerException e1) {
                            e1.printStackTrace();
                        } catch (SAXException e1) {
                            e1.printStackTrace();
                        } catch (ParserConfigurationException e1) {
                            e1.printStackTrace();
                        }

                    } else if (getForwardsACK().isSelected()) {

                        try {
                            model.transformerUpAck();
                            JOptionPane.showMessageDialog(null, "Conversion is successful!");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (TransformerException e1) {
                            e1.printStackTrace();
                        } catch (SAXException e1) {
                            e1.printStackTrace();
                        } catch (ParserConfigurationException e1) {
                            e1.printStackTrace();
                        }

                    }

                    progressBar.setValue(100);


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

        inputButton.setSize(70,70);
        Image dimg = img.getScaledInstance(inputButton.getWidth(), inputButton.getHeight(),
                Image.SCALE_SMOOTH);

        ImageIcon imageIcon = new ImageIcon(dimg);

        inputButton.setIcon(imageIcon);

    }

    public void setFolderImage(){

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/images/folder.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        outputFolderButton.setSize(70,70);
        Image dimg = img.getScaledInstance(outputFolderButton.getWidth(), outputFolderButton.getHeight(),
                Image.SCALE_SMOOTH);

        ImageIcon imageIcon = new ImageIcon(dimg);

        outputFolderButton.setIcon(imageIcon);

    }


}
