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
public class AdminPage extends JPanel{
    
    //private JPanel content;
    private JPanel jpMenu;
    private JPanel jpTables;
    private JButton btCsoportok;
    private JButton btEmberek;
    private JButton btMunkak;
    private JButton btLogout;
    private JLabel lbTitle;
    private ContentWindow holder;
    private int myid;
    
    
    public AdminPage(ContentWindow holder,int myid) {
        this.myid=myid;
       this.holder=holder;
            initComponents();
    }
    private void initComponents() {
        
//        content=new JPanel();
        jpTables = new JPanel();
        jpMenu=new JPanel();
        btCsoportok = new JButton();
        btMunkak = new JButton();
        btEmberek = new JButton();
        btLogout = new JButton("Kilépés");
        lbTitle = new JLabel();
        
        jpTables.setLayout(new BorderLayout());
        
 



        btCsoportok.setText("Csoportok");
        btCsoportok.addActionListener((ActionEvent evt) -> {
          jpTables.removeAll();
          Csoportok csop = new Csoportok();
           jpTables.add(csop, BorderLayout.CENTER);
           holder.pack();
//           this.setTitle("Csoportok");
        });   
        
         btMunkak.setText("Munkák");
        btMunkak.addActionListener((ActionEvent evt) -> {
          jpTables.removeAll(); 
          Munkak mun = new Munkak(5);
          jpTables.add(mun);
           jpTables.add(mun, BorderLayout.CENTER);
           holder.pack();
//           this.setTitle("Munkák");    
        });   

        btEmberek.setText("Emberek");
        btEmberek.addActionListener((ActionEvent e) -> {
          jpTables.removeAll();
          Emberek emb = new Emberek();
           jpTables.add(emb, BorderLayout.CENTER);
           holder.pack();
//           this.setTitle("Emberek");
        });
        
        
        btLogout.addActionListener((ActionEvent e) -> {
            holder.logout();
        });
        
        jpMenu.setLayout(new GridLayout(0,1));
        jpMenu.add(new JLabel("Kategóriák"));
        jpMenu.add(btEmberek);
        jpMenu.add(btCsoportok);
        jpMenu.add(btMunkak);
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
