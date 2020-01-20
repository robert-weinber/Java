package UI;

import Controller.DBConnector;
import Modell.Csoport;
import Modell.Ember;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class modTagok extends JPanel{
    
      JTextField nev = new JTextField(5);
      JTextArea leiras = new JTextArea(5,10);
      private List<Ember> TagLista;
      int ID;
    
    
    Csoport e=null;
    
    public modTagok(Csoport e) {
        this.setLayout(new GridLayout(0,2));
        this.e=e;
      ID=e.getID();
      TagLista=null;
   TagLista=new ArrayList<>();
   this.setLayout(new GridLayout(0,2));

   String sql="CALL selectTagsag("+ID+");";
            List rs = DBConnector.DBQuery(sql);
            System.out.println(ID);
            for (Object o : rs) {
                String[] row = (String[]) o;
                JLabel labelTag=new JLabel(row[3]+", "+row[2]);
                this.add(labelTag);
                JButton delTag=new JButton("Törlés");
                delTag.addActionListener((ActionEvent f) -> {
                    String sqldel = "CALL delTagsag("+ID+","+row[1]+")";
            DBConnector.DBUpdate(sqldel);
            this.remove(delTag);
            this.remove(labelTag);
            this.validate();
        });
                this.add(delTag);
            }
            JButton addTag=new JButton("Felvétel");
                addTag.addActionListener((ActionEvent f) -> {
           newTag myPanel = new newTag(ID);
      int result = JOptionPane.showConfirmDialog(null, myPanel, 
               "Tag felvétele", JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION) {
          String newTagName=myPanel.AddNewTag();
          if (newTagName != null) {
              JLabel labelTag=new JLabel(newTagName);
                this.add(labelTag,this.getComponentCount()-1);
                JButton delTag=new JButton("Törlés");
                delTag.addActionListener((ActionEvent g) -> {
                    
                    int resultdel = JOptionPane.showConfirmDialog(null, "Biztosan törölni akarja?", 
               "Törlés", JOptionPane.OK_CANCEL_OPTION);
      if (resultdel == JOptionPane.OK_OPTION) {
                     String sqldel = "CALL delTagsag("+ID+","+newTagName+")";
            System.out.println(sqldel);
            DBConnector.DBUpdate(sqldel);
            this.remove(delTag);
            this.remove(labelTag);
            this.validate();
      }
        });
            this.add(delTag,this.getComponentCount()-1);
            this.validate();              
          }
      }
        });
                this.add(addTag);
        
    } 
    public void refresh(){ 
        while (this.getComponents().length!=0)   {
            this.remove(this.getComponents()[0]);    
        }
          this.setLayout(new GridLayout(0,2));
      TagLista=null;
   TagLista=new ArrayList<>();
   this.setLayout(new GridLayout(0,2));

            String sql="CALL selectTagsag("+ID+");";
            List rs = DBConnector.DBQuery(sql);
            System.out.println(ID);
            for (Object o : rs) {
                String[] row = (String[]) o;
                JLabel labelTag=new JLabel(row[3]);
                this.add(labelTag);
                JButton delTag=new JButton("Törlés");
                delTag.addActionListener((ActionEvent f) -> {
            String sqldel = "CALL delTagsag("+ID+","+row[1]+")";
            DBConnector.DBUpdate(sqldel);
            this.remove(delTag);
            this.remove(labelTag);
            refresh();
            System.out.println("delete");
        });
                this.add(delTag);
            }
            JButton addTag=new JButton("Felvétel");
                addTag.addActionListener((ActionEvent f) -> {
        });
                this.add(addTag);
            this.validate();
    
    }
        
}
