package UI;

import Controller.DBConnector;
import Modell.Pozicio;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class newEmber extends JPanel{
    
        JTextField veznev = new JTextField(5);
      JTextField kernev = new JTextField(5);
      JTextField kezdes = new JTextField(5);
      JComboBox poz = new JComboBox();
      JTextField ber = new JTextField(5);
    
    
    public newEmber() {
        this.setLayout(new GridLayout(0,2));
      String sql = "CALL selectPoz();";
            List rs = DBConnector.DBQuery(sql);
            for (int i = 0; i < rs.size(); i++) {
			String[] row = (String[]) rs.get(i);
        Pozicio p = new Pozicio(Integer.parseInt(row[0]),row[1],row[2]);
                poz.addItem(p);
		}
      kezdes.setText(java.time.LocalDate.now().toString());
        this.add(new JLabel("Vezetéknév:"));
      this.add(veznev);
      this.add(new JLabel("Keresztnév:"));
      this.add(kernev);
      this.add(new JLabel("Kezdés:"));
      this.add(kezdes);
      this.add(new JLabel("Pozíció:"));
      this.add(poz);
      this.add(new JLabel("Órabér:"));
      this.add(ber);
    }
    public void AddNewEmber(){
          System.out.println(poz.getSelectedItem().toString());
          Pozicio pozid = (Pozicio)poz.getSelectedItem();
          String sql ="CALL `cegesmunkak`.`insertEmber`('"+veznev.getText()+"', '"+kernev.getText()+"', '"+pozid.getID()+"', '"+ber.getText()+"');";
          System.out.println(sql);
          String errormsg=DBConnector.DBUpdate(sql);
          System.out.println(errormsg);
    }
    
    
    
        
}
