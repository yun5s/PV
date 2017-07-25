package controller;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Created by MaiwandMaidanwal on 21/07/2017.
 */
public class Controller {

    JFileChooser chooser = new JFileChooser();
    StringBuilder sb = new StringBuilder();

    public void pickFile() throws Exception{

            java.io.File file = chooser.getSelectedFile();



        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "XML files", "XML", "XSL");

        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Your input file:: " +
                    chooser.getSelectedFile().getName());
        }

    }



    public String getChosenInputFile() {

        return String.valueOf(chooser.getSelectedFile());
    }

}
