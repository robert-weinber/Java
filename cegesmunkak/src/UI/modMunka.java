package UI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Controller.DBConnector;
import Modell.Ember;
import Modell.Munka;
import Modell.Tipus;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Wolfram
 */
public class modMunka extends JPanel{
    
        JTextField megrendelo = new JTextField(5);
      JTextField megnevezes = new JTextField(5);
      JComboBox fel = new JComboBox();
      JComboBox tip = new JComboBox();
    
    
    //private AdatModell.Modell modell;
    //WindowController controller;
    Munka e=null;
    
    public modMunka(Munka e) {
        this.setLayout(new GridLayout(0,2));
        this.e=e;
      String sqlfel = "CALL selectEmberCreate();";
            List rsfel = DBConnector.DBQuery(sqlfel);
            for (int i = 0; i < rsfel.size(); i++) {
			String[] row = (String[]) rsfel.get(i);
        Ember p = new Ember(Integer.parseInt(row[0]),row[1],row[2],row[3]);
                fel.addItem(p);
                if(p.toString().equals(e.getFelelos()))
                fel.setSelectedItem(p);
		}
            String sqltip = "CALL selectTipus();";
            List rstip = DBConnector.DBQuery(sqltip);
            for (int i = 0; i < rstip.size(); i++) {
			String[] row = (String[]) rstip.get(i);
        Tipus p = new Tipus(Integer.parseInt(row[0]),row[1],row[2]);
                tip.addItem(p);
                if(p.toString().equals(e.getTipus()))
                tip.setSelectedItem(p);
		}
            megrendelo.setText(e.getMegrendelo());
            megnevezes.setText(e.getMegnevezes());
      //JComboBox poz = new JComboBox(poziciok);
      //JTextField ber = new JTextField(5);
        this.add(new JLabel("Megrendelő:"));
      this.add(megrendelo);
      this.add(new JLabel("Megnevezés:"));
      this.add(megnevezes);
      this.add(new JLabel("Felelős:"));
      this.add(fel);
      this.add(new JLabel("Típus:"));
      this.add(tip);
    }
    public void modThisEmber(){
          Ember felid = (Ember)fel.getSelectedItem();
          Tipus tipid = (Tipus)tip.getSelectedItem();
          String sql ="CALL updateEmber('"+megrendelo.getText()+"','"+megnevezes.getText()+"','"+felid.getID()+"','"+tipid.getID()+"',"+e.getID()+");";
          System.out.println(sql);
          String errormsg=DBConnector.DBUpdate(sql);
          System.out.println(errormsg);
    }
    
    
    
        
}
