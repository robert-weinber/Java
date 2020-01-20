package UI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

/**
 *
 * @author Wolfram
 */
public class SalesPage extends JPanel{
    
    //private JPanel content;
    private JPanel jpMenu;
    private JPanel jpTables;
    private JButton btMunkaim;
    private JButton btNewMunka;
    private JButton btLogout;
    private JLabel lbTitle;
    private ContentWindow holder;
    private int myid;
    
    
    public SalesPage(ContentWindow holder,int myid) {
        this.myid=myid;
       this.holder=holder;
            initComponents();
    }
    
    private void newMunka(){
      newMunka myPanel = new newMunka(myid);
      int result = JOptionPane.showConfirmDialog(null, myPanel, 
               "Új munka felvétele", JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION) {
          myPanel.AddNewMunka();
      }
}
    
    private void initComponents() {
        
//        content=new JPanel();
        jpTables = new JPanel();
        jpMenu=new JPanel();
        btMunkaim = new JButton();
        btNewMunka = new JButton();
        btLogout = new JButton("Kilépés");
        lbTitle = new JLabel();
        
        jpTables.setLayout(new BorderLayout());
        
 



        
   btMunkaim.setText("Munkáim");
        btMunkaim.addActionListener((ActionEvent evt) -> {
          jpTables.removeAll(); 
          Munkaim mun = new Munkaim(myid);
          jpTables.add(mun);
           jpTables.add(mun, BorderLayout.CENTER);
           holder.pack();
//           this.setTitle("Munkák");    
        });   

       
        
        
        btLogout.addActionListener((ActionEvent e) -> {
            holder.logout();
        });
        
        

        btNewMunka.setText("Új Munka");
        btNewMunka.addActionListener((ActionEvent e) -> {
            newMunka();
        });
        
        jpMenu.setLayout(new GridLayout(0,1));
        jpMenu.add(new JLabel("Kategóriák"));
        jpMenu.add(btNewMunka);
        jpMenu.add(btMunkaim);
        jpMenu.add(btLogout);
        
        GroupLayout contentLayout = new GroupLayout(this);
        this.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(contentLayout.createSequentialGroup()
                .addComponent(jpMenu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpTables, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jpTables, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpMenu, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        this.setPreferredSize(this.getPreferredSize());
        //this.setContentPane(content);
        
    
    }    
}
