package UI;

import Controller.DBConnector;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class newCsoport extends JPanel{
    
      JTextField nev = new JTextField(5);
      JTextField leiras = new JTextField(5);
    
    
    public newCsoport() {
        this.setLayout(new GridLayout(0,2));
        this.add(new JLabel("Név:"));
      this.add(nev);
      this.add(new JLabel("Leírás:"));
      this.add(leiras);
    }
    public void AddNewCsoport(){
          String sql ="CALL insertCsop('"+nev.getText()
                  +"','"+leiras.getText()+"');";
          System.out.println(sql);
          String errormsg=DBConnector.DBUpdate(sql);
          System.out.println(errormsg);
    }
}
