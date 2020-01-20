package UI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Controller.DBConnector;
import Modell.Csoport;
import Modell.Munka;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 *
 * @author Wolfram
 */
public class kiosztMunka extends JPanel{
    
      JComboBox poz = new JComboBox();
      Csoport selected;
    
    
    //private AdatModell.Modell modell;
    //WindowController controller;
    Munka e=null;
    
    public kiosztMunka(Munka e) {
        this.setLayout(new GridLayout(0,2));
        this.e=e;
      String sql = "SELECT id, nev, leiras, lastMsg FROM csoportok";
            List rs = DBConnector.DBQuery(sql);
            for (int i = 0; i < rs.size(); i++) {
			String[] row = (String[]) rs.get(i);
        Csoport p = new Csoport(Integer.parseInt(row[0]),row[1],row[2],row[3]);
                poz.addItem(p);
                if(p.getNev().equals(e.getCsoport())){
                    selected=p;
                }
		}
            poz.setSelectedItem(selected);
      this.add(poz);
    }
    public void munkaKioszt(){
        //System.out.println("x value: " + veznev.getText());
        // System.out.println("y value: " + kernev.getText());
          System.out.println(poz.getSelectedItem().toString());
          Csoport csopid = (Csoport)poz.getSelectedItem();
          String sql ="update munkak set csoport='"+csopid.getID()+"', status='2' where id='"+e.getID()+"';";
          System.out.println(sql);
          String errormsg=DBConnector.DBUpdate(sql);
          System.out.println(errormsg);
          
    }
    
    
    
        
}
