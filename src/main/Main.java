package main;

import view.View;
import controller.Controller;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
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
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(700, 460));
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();


        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to close this window?", "Really Closing?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {


                    if (view.getConvertClicked() == 0 && !(model.getfolderFilePath() == null) || view.getConvertClicked() > 0 && view.getSuccessCheck()==false) {


                        for (File file : model.getOutputFiles()) {

                            file.delete();
                        }

                    }

                    System.exit(0);

                }
            }
        });

    }
}

