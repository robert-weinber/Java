package Modell;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Message extends JPanel {

    private String nev;
    private String uzenet;
    private Color hatter;

    public Message(String nev, String uzenet, Color szin) {
        this.nev = nev;
        this.uzenet = uzenet;
        this.hatter = szin;
        this.setLayout(new GridLayout(1, 7));
        this.add(new JLabel(nev));
        this.add(new JLabel(uzenet));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(15, 15);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.setColor(hatter);
        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        graphics.setColor(getForeground());
        graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
    }

    public String getNev() {
        return nev;
    }

}
