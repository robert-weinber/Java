package Modell;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Csoport extends JPanel {

    private int ID;
    private String nev;
    private String leiras;
    private String poszt;
    private JLabel status;

    ///    ADMIN
    public Csoport(int id, String nev, String leiras) {
        this.ID = id;
        this.nev = nev;
        this.leiras = leiras;
        //this.emberek = emberek;
        this.setLayout(new GridLayout(1, 7));
        this.add(new JLabel(ID + ""));
        this.add(new JLabel(nev));
        this.add(new JLabel(leiras));
    }

    ///    DEV
    public Csoport(int id, String nev, String leiras, String poszt) {
        this.ID = id;
        this.nev = nev;
        this.leiras = leiras;
        this.poszt = poszt;
        this.setLayout(new GridLayout(1, 7));
        this.add(new JLabel(nev));
        this.add(new JLabel(leiras));
        this.add(new JLabel(poszt));
        status = new JLabel("Nincs olvasatlan üzenet");
        this.add(status);
    }

    public void setOlvasatlan() {
        status.setText("Van olvasatlan üzenet");
    }

    public int getID() {
        return ID;
    }

    public String getNev() {
        return nev;
    }

    public String getLeiras() {
        return leiras;
    }

    public Object[] getAdatok() {
        return new Object[]{ID + "", nev, leiras};
    }

    @Override
    public String toString() {
        return nev;
    }

}
