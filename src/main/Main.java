package main;

import db.DBconnect;
import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import view.Splash;
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

    /**
     *   I have documented all the code that I have written for this project, This has been fully explained and documented
     *   so that whoever may continue from this point onwards will understand the purpose and relevance of all written code.
     *
     *   Also I have not commented every single line, as that is unnecessary but only the relevant parts that need
     *   explaining.
     *
     * I have used the MVC (Model, View, Controller) structure to code, as I believe this was the most effective
     * approach for this project.
     */

    public static void main(String[] args) throws IOException, URISyntaxException {

        /*below I have the code where only one instance of this application can be opened on desktop at the same time
         * this avoids multiple instances being open each time you click on the application.
         */

        String appId = "PVpharmConverter";
        boolean alreadyRunning;

        try {
            JUnique.acquireLock(appId);                         //using a JUnique JAR to lock once the application is opened once.
            alreadyRunning = false;
        } catch (AlreadyLockedException e) {                    // If it has been already opened (locked), then exit.
            alreadyRunning = true;

            JOptionPane.showMessageDialog(null, "Application is already running");
            System.exit(1);
        }
        if (!alreadyRunning) {

        }


        //Everything is run from here.

        Splash splash = new Splash();

        //load splash screen first.
        //checking for data file, if not exist load main
        //load login screen - if successful, go to main


        Model model = new Model();
        Controller controller = new Controller(model);
        View view = new View(model, controller);



    }
}

