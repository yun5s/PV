package main;

import view.View;
import controller.Controller;
import model.Model;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
	// write your code here


        Model model = new Model();
        Controller controller = new Controller(model);
        View view = new View(model, controller);

//
//        model.addObserver(view);

        JFrame frame = new JFrame("Backwards and Forwards E2B Converter");
        frame.setContentPane(view.getPanel1());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMaximumSize(new Dimension(700,460));
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(700, 460));
        frame.pack();





    }
}
