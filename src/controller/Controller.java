package controller;

import model.Model;
import java.util.Scanner;

/**
 * Created by MaiwandMaidanwal on 21/07/2017.
 */
public class Controller{

    private Model model;


    public Controller(Model model){

        this.model = model;
    }


        private Scanner x;
        private int calendarInfo;


        public void openCalendarFile(){  // the method for getting the file to use scanner on

            try{
                x = new Scanner(Controller.class.getResourceAsStream("/ConversionCount/conversionCount.txt"));

            }
            catch(Exception e){
                System.out.println(("Could not find file"));
            }
        }


        public void readCalendarFile(){                         //read the next line with the scanner

            try {
                while (x.hasNext()) {                           //keep reading if there is a next line
                    calendarInfo = x.nextInt();
                }
            } catch (Exception e){
                System.out.println("Could not read from file");
            }
        }


        public int getCalendarInfo(){
            return calendarInfo;
        }


        public void closeFileX(){			// close the scanner
            x.close();
        }


    }
