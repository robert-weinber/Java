package UI;

import Controller.DBConnector;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class newLog extends JPanel{
    
        JTextField hours = new JTextField(5);
    public newLog(/*WindowController c*/) {
        this.setLayout(new GridLayout(0,2));
      
        this.add(new JLabel("Dolgozott órák:"));
      this.add(hours);
    }
    public void AddNewLog(int munka,int alany){
          String sql ="CALL `cegesmunkak`.`addOraber`('"+munka+"', '"+alany+"', '"+hours.getText()+"');";
          System.out.println(sql);
          String errormsg=DBConnector.DBUpdate(sql);
          System.out.println(errormsg);
    }
}
