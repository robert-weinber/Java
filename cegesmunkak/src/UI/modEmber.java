package UI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Controller.DBConnector;
import Modell.Ember;
import Modell.Pozicio;
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
public class modEmber extends JPanel{
    
        JTextField veznev = new JTextField(5);
      JTextField kernev = new JTextField(5);
      JTextField kezdes = new JTextField(5);
      JComboBox poz = new JComboBox();
      JTextField ber = new JTextField(5);
    
    
    //private AdatModell.Modell modell;
    //WindowController controller;
    Ember e=null;
    
    public modEmber(Ember e) {
        this.setLayout(new GridLayout(0,2));
        this.e=e;
        String sql = "CALL selectPoz();";
      //String sql = "SELECT id, nev, leiras FROM poziciok";
            List rs = DBConnector.DBQuery(sql);
            for (int i = 0; i < rs.size(); i++) {
			String[] row = (String[]) rs.get(i);
        Pozicio p = new Pozicio(Integer.parseInt(row[0]),row[1],row[2]);
                poz.addItem(p);
                if(p.getNev().equals(e.getPozicio()))
                poz.setSelectedItem(p);
		}
            veznev.setText(e.getVeznev());
            kernev.setText(e.getKernev());
            kezdes.setText(e.getKezdes());
            ber.setText(e.getOraber()+"");
      //JComboBox poz = new JComboBox(poziciok);
      //JTextField ber = new JTextField(5);
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
    public void modThisEmber(){
        //System.out.println("x value: " + veznev.getText());
        // System.out.println("y value: " + kernev.getText());
          System.out.println(poz.getSelectedItem().toString());
          Pozicio pozid = (Pozicio)poz.getSelectedItem();
          String sql ="CALL updateEmber('"+veznev.getText()+
                  "','"+kernev.getText()+"',"+pozid.getID()+
                  ",'"+kezdes.getText()+"','"+ber.getText()+"',"+e.getID()+");";
          /*String sql ="update emberek set veznev='"+veznev.getText()+"', kernev='"+kernev.getText()+"', pozicio='"+pozid.getID()+"', kezdes='"+kezdes.getText()+"', oraber='"+ber.getText()
                  +"' where id='"+e.getID()+"';";*/
          System.out.println(sql);
          String errormsg=DBConnector.DBUpdate(sql);
          System.out.println(errormsg);
    }
    
    
    
        
}
