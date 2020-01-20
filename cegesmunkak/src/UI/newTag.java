package UI;

import Controller.DBConnector;
import Modell.Ember;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class newTag extends JPanel{
    
      int ID;
      JComboBox nev;
      JComboBox feladat;
      private List<Ember> TagLista= new ArrayList<>();
      private String[] NevLista;
      private List<String[]> FeladatLista=new ArrayList<>();
      private String[] FeladatNevLista;
    
    
    public newTag(int id) {
      ID=id;
        String sqlemberek = "CALL selectNewTag("+ID+")";
            List rsember = DBConnector.DBQuery(sqlemberek);
            int i=0;
            NevLista= new String[rsember.size()];
            for (Object o : rsember) {
			String[] row = (String[]) o;
                Ember ember = new Ember(Integer.parseInt(row[0]),row[1],row[2],row[3],row[4],Integer.parseInt(row[5]));
                System.out.println(ember.getNev());
                System.out.println(Integer.parseInt(row[0])+row[1]+row[2]+row[3]+row[4]+Integer.parseInt(row[5]));
                TagLista.add(ember);
                NevLista[i]=ember.getNev();
            i++;
		}
        String sqlfeladatok = "CALL selectFeladat();";
            List rsfeladat = DBConnector.DBQuery(sqlfeladatok);
            FeladatNevLista= new String[rsfeladat.size()];
            i=0;
            for (Object o : rsfeladat) {
			String[] row = (String[]) o;
                FeladatLista.add(row);
                FeladatNevLista[i]=row[1];
            i++;
		}
        nev = new JComboBox(NevLista);
        feladat = new JComboBox(FeladatNevLista);
        this.add(new JLabel("Név:"));
      this.add(nev);
      this.add(new JLabel("Leírás:"));
      this.add(feladat);
    }
    public String AddNewTag(){
        if (nev.getItemCount()!=0) {
          String sql ="CALL insertTag('"+ID+"', '"+TagLista.get(nev.getSelectedIndex()).getID()+"', '"+FeladatLista.get(feladat.getSelectedIndex())[0]+"');";
          System.out.println(sql);
          String errormsg=DBConnector.DBUpdate(sql);
          System.out.println(errormsg);
          return TagLista.get(nev.getSelectedIndex()).getNev();
        }else return null;
    }
}
