package UI;

import Controller.DBConnector;
import Modell.Ember;
import Modell.Tipus;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class newMunka extends JPanel{
    
      JTextField megrendelo = new JTextField(5);
      JTextField leiras = new JTextField(5);
      JComboBox tip = new JComboBox();
      JComboBox fel = new JComboBox();
      int persid;
    
    
    public newMunka(int perid) {
        persid=perid;
        this.setLayout(new GridLayout(0,2));
        this.add(new JLabel("Megrendelő:"));
      this.add(megrendelo);
      this.add(new JLabel("Megnevezés:"));
      this.add(leiras);
      this.setLayout(new GridLayout(0,2));
      String sql = "CALL selectTipus();";
            List rs = DBConnector.DBQuery(sql);
            for (int i = 0; i < rs.size(); i++) {
			String[] row = (String[]) rs.get(i);
        Tipus p = new Tipus(Integer.parseInt(row[0]),row[1],row[2]);
                tip.addItem(p);
		}
      this.add(new JLabel("Típus:"));
      this.add(tip);
      String sqlfel = "CALL selectEmberCreate();";
            List rsfel = DBConnector.DBQuery(sqlfel);
            for (int i = 0; i < rsfel.size(); i++) {
			String[] row = (String[]) rsfel.get(i);
        Ember p = new Ember(Integer.parseInt(row[0]),row[1],row[2],row[3]);
                fel.addItem(p);
		}
      this.add(new JLabel("Felelős:"));
      this.add(fel);
    }
    public void AddNewMunka(){
        Tipus seltip=(Tipus)tip.getSelectedItem();
        Ember selectedfelelos=(Ember)fel.getSelectedItem();
        Calendar today = Calendar.getInstance();
today.set(Calendar.HOUR_OF_DAY, 0);
          String sql ="CALL insertMunka('"+today.get(Calendar.YEAR)+"-"+today.get(Calendar.MONTH)+"-"+today.get(Calendar.DAY_OF_MONTH)+"','"+megrendelo.getText()
                  +"','"+leiras.getText()+"','"+selectedfelelos.getID()+"','"+seltip.getID()+"','"+persid+"');";
          System.out.println(sql);
          String errormsg=DBConnector.DBUpdate(sql);
          System.out.println(errormsg);
    }
}
