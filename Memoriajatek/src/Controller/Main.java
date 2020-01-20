package Controller;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Wolfram
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AdatModell.Modell modell = new AdatModell.Modell();
//        WindowController c =new WindowController();

        GUI.Ablak b = new GUI.Ablak();
        b.initComponents(b.getContentPane());
//        b.setPreferredSize(new Dimension(500, 500));
//        b.setSize(/*pnKirakós.getWidth()*/400, 700);
        //pnKirakós.getHeight()+pnKijelző.getHeight()+btFeladás.getHeight());
        b.setLocation(500, 250);
        b.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        b.pack();
        b.setVisible(true);
    }
}
