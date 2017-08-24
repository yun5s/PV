package main;

import view.View;
import controller.Controller;
import model.Model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.JOptionPane;


public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        // write your code here


        //Everthing is run from here.

        Model model = new Model();
        Controller controller = new Controller(model);
        View view = new View(model, controller);


    }
}

