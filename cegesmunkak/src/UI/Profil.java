package UI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Controller.DBConnector;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Wolfram
 */
public class Profil extends JPanel{
    
    private JPanel jpData;
    private JPanel jpCommands;
    private JButton btBack;
    
    private int myID;
    
    public Profil(int id) {
       this.myID=id;
            initComponents();

refreshTable();
        
    }
    
private void refreshTable(){

            String sql = "SELECT e.id, e.veznev,e.kernev ,e.kezdes, p.nev as pozicio, e.oraber "
                    + "FROM emberek as e join poziciok as p on p.id=e.pozicio "
                    + "where e.id="+myID+";";
            List rs = DBConnector.DBQuery(sql);
            jpData.setLayout(new GridLayout(0, 2));
            for (Object o : rs) {
			String[] row = (String[]) o;
                jpData.add(new JLabel("Név"));
                jpData.add(new JLabel(row[1]+" "+row[2]));
                jpData.add(new JLabel("Kezdés"));
                jpData.add(new JLabel(row[3]));
                jpData.add(new JLabel("Pozíció"));
                jpData.add(new JLabel(row[4]));
                jpData.add(new JLabel("Órabér"));
                jpData.add(new JLabel(row[5]));
		}

}
    
private void newEmber(){
      
}

    
    
    private void initComponents() {
        jpCommands = new JPanel();
        btBack = new JButton();
        jpData = new JPanel();
        


        btBack.setText("Vissza");
        btBack.addActionListener((ActionEvent e) -> {
            newEmber();
        });
        
        GroupLayout jpCommandsLayout = new GroupLayout(jpCommands);
        jpCommands.setLayout(jpCommandsLayout);
        jpCommandsLayout.setHorizontalGroup(
            jpCommandsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jpCommandsLayout.createSequentialGroup()
                .addGroup(jpCommandsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(btBack)
//                    .addComponent(btDelete)
//                    .addComponent(btModEmber)
                )
                .addGap(0, 20, Short.MAX_VALUE))
        );
        jpCommandsLayout.setVerticalGroup(
            jpCommandsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jpCommandsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btBack)
//                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
//                .addComponent(btDelete)
//                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
//                .addComponent(btModEmber)
                .addContainerGap(343, Short.MAX_VALUE))
        );
        
        GroupLayout thisLayout = new GroupLayout(this);
        this.setLayout(thisLayout);
        thisLayout.setHorizontalGroup(
            thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(thisLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpData, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jpCommands, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        thisLayout.setVerticalGroup(
            thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, thisLayout.createSequentialGroup()
                .addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jpData)
                    .addComponent(jpCommands, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    
    }    
}
