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
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

/**
 *
 * @author Wolfram
 */
public class DeveloperPage extends JPanel{
    
    //private JPanel content;
    private JPanel jpMenu;
    private JPanel jpTables;
    private JButton btAdataim;
    private JButton btMunkaim;
    private JButton btUjMunkak;
    private JButton btLogout;
    private JButton btCsoportjaim;
    private JLabel lbTitle;
    private ContentWindow holder;
    private int myid;
    
    
    public DeveloperPage(ContentWindow holder,int myid) {
        this.myid=myid;
       this.holder=holder;
            initComponents();
    }
    private void initComponents() {
        
//        content=new JPanel();
        jpTables = new JPanel();
        jpMenu=new JPanel();
        btMunkaim = new JButton();
        btUjMunkak = new JButton();
        btAdataim = new JButton();
        btCsoportjaim = new JButton();
        btLogout = new JButton("Kilépés");
        lbTitle = new JLabel();
        
        jpTables.setLayout(new BorderLayout());
        
 

        btCsoportjaim.setText("Csoportjaim");
        btCsoportjaim.addActionListener((ActionEvent evt) -> {
          jpTables.removeAll(); 
          Csoportjaim csop = new Csoportjaim(myid);
          jpTables.add(csop);
           jpTables.add(csop, BorderLayout.CENTER);
           holder.pack();
//           this.setTitle("Munkák");    
        });   

        
         btMunkaim.setText("Munkáim");
        btMunkaim.addActionListener((ActionEvent evt) -> {
          jpTables.removeAll(); 
          Munkaim mun = new Munkaim(myid);
          jpTables.add(mun);
           jpTables.add(mun, BorderLayout.CENTER);
           holder.pack();
//           this.setTitle("Munkák");    
        });   
        
        btUjMunkak.setText("Szabad Munkák");
        btUjMunkak.addActionListener((ActionEvent evt) -> {
          jpTables.removeAll(); 
          UjMunkak mun = new UjMunkak(myid);
          jpTables.add(mun);
           jpTables.add(mun, BorderLayout.CENTER);
           holder.pack();
//           this.setTitle("Munkák");    
        });  

        btAdataim.setText("Adataim");
        btAdataim.addActionListener((ActionEvent e) -> {
          jpTables.removeAll();
          Profil me = new Profil(myid);
           jpTables.add(me, BorderLayout.CENTER);
           holder.pack();
//           this.setTitle("Emberek");
        });
        
        
        btLogout.addActionListener((ActionEvent e) -> {
            holder.logout();
            holder.setTitle("Bejelentkezés");
        });
        
        jpMenu.setLayout(new GridLayout(0,1));
        jpMenu.add(new JLabel("Kategóriák"));
        jpMenu.add(btAdataim);
        jpMenu.add(btMunkaim);
        jpMenu.add(btUjMunkak);
        jpMenu.add(btCsoportjaim);
        jpMenu.add(btLogout);
        /*GroupLayout jpMenuLayout = new GroupLayout(jpMenu);
        jpMenu.setLayout(jpMenuLayout);
        jpMenuLayout.setHorizontalGroup(
            jpMenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jpMenuLayout.createSequentialGroup()
                .addGroup(jpMenuLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(btEmberek)
                    .addComponent(btCsoportok)
                    .addComponent(btMunkak))
                .addGap(0, 20, Short.MAX_VALUE))
        );
        jpMenuLayout.setVerticalGroup(
            jpMenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jpMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btEmberek)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btCsoportok)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btMunkak)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );*/
        
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
