package main;

import view.View;
import controller.Controller;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.JOptionPane;


public class Main {

    public static void main(String[] args) throws IOException {
        // write your code here


        Model model = new Model();
        Controller controller = new Controller(model);
        View view = new View(model, controller);

//
//        model.addObserver(view);

        JFrame frame = new JFrame("Backwards and Forwards E2B Converter");
        frame.setContentPane(view.getPanel1());
        frame.setMaximumSize(new Dimension(700, 460));
        frame.setMinimumSize(new Dimension(700, 460));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);



        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {


                if (JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to close this window?", "Really Closing?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {


                    if (view.getConvertClicked() == 0 && !(model.getfolderFilePaths() == null) || view.getConvertClicked() > 0 && view.getSuccessCheck()==false) {


                        for (File file : model.getOutputFiles()) {

                            BufferedReader br = null;
                            try {
                                br = new BufferedReader(new FileReader(file));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }                                   //if the file is empty, Delete it.
                            try {
                                if (br.readLine() == null) {
                                    file.delete();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            file.delete();            
                        }

                    }

                    System.exit(0);

                }

            }
        });

    }
}

