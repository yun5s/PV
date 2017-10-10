package view;

/**
 * Created by MaiwandMaidanwal on 28/08/2017.
 */


import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SplashScreen;


/**
 * This class is for the introductory splash screen... It is fairly simplistic, and I thought it would make the
 * application more professional, and is a good starting point from a development point of view.
 */

public class Splash {


    static SplashScreen mySplash;
    static Graphics2D splashGraphics;
    static Rectangle2D.Double splashTextArea;
    static Rectangle2D.Double splashProgressArea;
    static Font font;


    public Splash() {

        splashInit();           //calling methods below within constructor.

        appInit();


        if (mySplash != null) {

            mySplash.close();
        }
    }


    /**
     * I wanted to give the application a quick loading effect, and this was achieved by the
     * for loop in this method and by the quick delay. The numbers that I have set give
     * the sense of a quick loading animation.
     */
    private static void appInit()
    {
        for (int i = 1; i <= 30; i++)
        {
            int j = i * 3;
            splashText("Checking Key...");
            splashProgress(j);
            try
            {
                Thread.sleep(80);                                   //quick delay
            }
            catch (InterruptedException localInterruptedException)
            {
                break;
            }
        }
    }

    /**
     * //Init's for the splash screen, basic outline for it.
     */
    private static void splashInit()
    {
        mySplash = SplashScreen.getSplashScreen();
        if (mySplash != null)
        {
            Dimension localDimension = mySplash.getSize();
            int i = (int) localDimension.getHeight();
            int j = (int) localDimension.getWidth();
            splashTextArea = new Rectangle2D.Double(15.0D, i * 0.85D, j * 0.40D, 20.0D);
            splashProgressArea = new Rectangle2D.Double(j * 0.55D, i * 0.88D, j * 0.4D, 12.0D);
            splashGraphics = mySplash.createGraphics();
            font = new Font("Dialog", 0, 14);
            splashGraphics.setFont(font);
            splashText("Starting");
            splashProgress(0);
        }
    }


    /**
     * Setting up the text box for the splash screen.
     * @param paramString
     */
    public static void splashText(String paramString)
    {
        if ((mySplash != null) && (mySplash.isVisible()))
        {
            splashGraphics.setPaint(Color.white);
            splashGraphics.fill(splashTextArea);
            splashGraphics.setPaint(Color.BLACK);
            splashGraphics.drawString(paramString, (int)(splashTextArea.getX() + 10.0D), (int)(splashTextArea.getY() + 15.0D));
            mySplash.update();
        }
    }


    /**
     * designing the progress bar below, and the style of progression.
     * @param paramInt
     */
    public static void splashProgress(int paramInt)
    {
        if ((mySplash != null) && (mySplash.isVisible()))
        {
            splashGraphics.setPaint(Color.LIGHT_GRAY);
            splashGraphics.fill(splashProgressArea);
            splashGraphics.setPaint(Color.BLUE);
            splashGraphics.draw(splashProgressArea);
            int i = (int)splashProgressArea.getMinX();
            int j = (int)splashProgressArea.getMinY();
            int k = (int)splashProgressArea.getWidth();
            int m = (int)splashProgressArea.getHeight();
            int n = Math.round(paramInt * k / 100.0F);
            n = Math.max(0, Math.min(n, k - 1));
            splashGraphics.setPaint(Color.BLUE);
            splashGraphics.fillRect(i, j + 1, n, m - 1);
            mySplash.update();
        }
    }
}