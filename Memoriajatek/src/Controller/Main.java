package Controller;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        AdatModell.Modell modell = new AdatModell.Modell();

        GUI.Ablak b = new GUI.Ablak();
        b.initComponents(b.getContentPane());
        b.setLocation(500, 250);
        b.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        b.pack();
        b.setVisible(true);
    }
}
