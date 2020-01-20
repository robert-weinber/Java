package UI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Controller.DBConnector;
import Modell.Csoport;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Wolfram
 */
public class modCsoport extends JPanel{
    
      JTextField nev = new JTextField(5);
      JTextField leiras = new JTextField(10);
    
    
    Csoport e=null;
    
    public modCsoport(Csoport e) {
        this.setLayout(new GridLayout(0,2));
        this.e=e;
      
            nev.setText(e.getNev());
            leiras.setText(e.getLeiras());
      //JComboBox poz = new JComboBox(poziciok);
      //JTextField ber = new JTextField(5);
        this.add(new JLabel("Név:"));
      this.add(nev);
      this.add(new JLabel("Leírás:"));
      this.add(leiras);
    }
    public void modThisCsoport(){
        String sql;
        if(!nev.getText().isEmpty() && !leiras.getText().isEmpty()){
            sql = "CALL updateCsop('"+nev.getText()+"','"+leiras.getText()+"',"+e.getID()+");";
        /*sql ="update csoportok set nev='"+nev.getText()+"', leiras='"+leiras.getText()
                  +"' where id='"+e.getID()+"';";*/
          System.out.println(sql);
          String errormsg=DBConnector.DBUpdate(sql);
          System.out.println(errormsg);
        }
    }
    
    
    
        
}
