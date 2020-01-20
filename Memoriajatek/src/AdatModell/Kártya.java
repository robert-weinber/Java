package AdatModell;

/**
 *
 * @author Wolfram
 */
import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Kártya extends JButton{
    private int id;
    private int párSzáma;
    private boolean felfordítva = false;
    private boolean párosítva = false;
    private ImageIcon háttér;
    private ImageIcon szimbólum;
    


    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }
    
    public void setPárSzáma(int párSzáma){
        this.párSzáma = párSzáma;
    }

    public int getPárSzáma(){
        return this.párSzáma;
    }


    public void setPárosítva(boolean párosítva){
        this.párosítva = párosítva;
        this.setEnabled(!párosítva);
    }

    public boolean getPárosítva(){
        return this.párosítva;
    }
    
    public void setSzimbólum(ImageIcon szimbólum){
        this.szimbólum = szimbólum;
    }
    
    public void setHáttér(ImageIcon háttér){
        this.háttér = háttér;
        this.setIcon(háttér);
//JLabel label = new JLabel("", háttér, JLabel.CENTER);
//this.add( label, BorderLayout.CENTER );
    }

    public void setFelfordít(){
        this.felfordítva=true;
        this.setIcon(szimbólum);
//JLabel label = new JLabel("", szimbólum, JLabel.CENTER);
//this.add( label, BorderLayout.CENTER );
    }
    
    public void setVisszafordít(){
        this.felfordítva=false;
        this.setIcon(háttér);
//JLabel label = new JLabel("", háttér, JLabel.CENTER);
//this.add( label, BorderLayout.CENTER );
    }
    
    public boolean getFelfordítva(){
        return this.felfordítva;
    }
}