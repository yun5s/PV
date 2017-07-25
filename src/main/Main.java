package main;

import view.View;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
	// write your code here

        View view = new View();

        JFrame frame = new JFrame("Backwards and Forwards E2B Converter");
        frame.setContentPane(view.getPanel1());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setMaximumSize(new Dimension(1000,500));
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(700, 400));
        frame.pack();



    }
}
