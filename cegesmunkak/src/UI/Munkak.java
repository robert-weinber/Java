package UI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Controller.DBConnector;
import Modell.Munka;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Wolfram
 */
public class Munkak extends JPanel{
    
    private JPanel jpCommands;
    private JButton btDelete;
    private JScrollPane spMunkak;
    public static JTable tableMunkak;
    private JButton btNewMunka;
    private JButton btModMunka;
    private JButton btKioszt;
    private JLabel lbTitle;
    
    DefaultTableModel dtm;
    Object[] oszlopnevek;
    List<Munka> MunkaLista;
    private int persid;
    
    
    public Munkak(int perid/*WindowController c*/) {
        persid=perid;
       
            initComponents();
            /*this.setPreferredSize(new java.awt.Dimension(500,500));
            this.setLocation(500, 250);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.pack();*/
            
            oszlopnevek =new Object[]{/*"raw",*/"ID","FelID","ÜzlID","Megrendelő","Megnevezés","Típus","Státusz","Összköltség","Csoport","Felelős","Üzletkötő","Dátum"};

refreshTable();
        
    }
    
private void refreshTable(){
   //RowSorter sorter = tableEmberek.getRowSorter();
   dtm=null;
   MunkaLista=null;
   MunkaLista=new ArrayList<>();
    dtm = new DefaultTableModel(oszlopnevek,0);

            //String sql = "SELECT e.id, e.veznev,e.kernev ,e.kezdes, p.nev as pozicio, e.oraber FROM emberek as e join poziciok as p on p.id=e.pozicio";
            String sql = "CALL selectMunka();";
                       List rs = DBConnector.DBQuery(sql);
            for (Object o : rs) {
			String[] row = (String[]) o;
                Munka e = new Munka(Integer.parseInt(row[0]),row[1],row[2],row[3],row[4],row[5],row[6],row[7],row[8],Integer.parseInt(row[9]),Integer.parseInt(row[10]),Integer.parseInt(row[11]));
              //   public Munka(int ID, String csoport, String datum, String megrendelo, String megnevezes, String felelos, String uzletkoto, String tipus, String status, int osszkoltseg) {
   //       return new Object[]{ID+"",csoport,datum,megrendelo,megnevezes,felelos,uzletkoto,tipus,status,osszkoltseg+""};
                MunkaLista.add(e);
                dtm.addRow(e.getAdatok());
                //dtm.addRow(new Object[]{row[0],row[1]+' '+row[2],row[3],row[4],row[5]});
                                    
            //dtm.addRow(new String[]{row[0]+" "+row[1],row[2],row[3],row[4]});
		}
tableMunkak.setModel(dtm);
TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableMunkak.getModel());
tableMunkak.setRowSorter(sorter);

List<RowSorter.SortKey> sortKeys = new ArrayList<>();
sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
sorter.setSortKeys(sortKeys);
//jlToplista.setRowSorter(sorter);

TableColumnModel tcm = tableMunkak.getColumnModel();
    System.out.println(tcm.getColumnCount());
tcm.removeColumn(tcm.getColumn(0));
tcm.removeColumn(tcm.getColumn(0));
tcm.removeColumn(tcm.getColumn(0));
    System.out.println(tableMunkak.getModel().getValueAt(1, 0));

}
    
private void newMunka(){
      newMunka myPanel = new newMunka(0);
      int result = JOptionPane.showConfirmDialog(null, myPanel, 
               "Új munka felvétele", JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION) {
          myPanel.AddNewMunka();
      }
      refreshTable();
}

private void modMunka(){
      modMunka myPanel = new modMunka(MunkaLista.get(tableMunkak.convertRowIndexToModel(tableMunkak.getSelectedRow())));
      int result = JOptionPane.showConfirmDialog(null, myPanel, 
               "Munka módosítása", JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION) {
          myPanel.modThisEmber();
      }
      refreshTable();
}

private void delMunka(){
      int result = JOptionPane.showConfirmDialog(null, "Biztosan törölni akarja?", 
               "Törlés", JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION) {
    //int selectedEmberIndex=EmberLista.get(tableEmberek.convertRowIndexToModel(tableEmberek.getSelectedRow())).getID();
     // System.out.println(selectedEmberIndex);
      //String sql="delete from emberek where id='"+selectedEmberIndex+"'";
      String sql="CALL delMunka("+tableMunkak.getModel().getValueAt(tableMunkak.convertRowIndexToModel(tableMunkak.getSelectedRow()), 0)+");";
      //String sql="delete from munkak where id='"+tableMunkak.getModel().getValueAt(tableMunkak.convertRowIndexToModel(tableMunkak.getSelectedRow()), 0)+"'";
          System.out.println(sql);
      String errormsg = DBConnector.DBUpdate(sql);
      System.out.println(errormsg);
      refreshTable();
      }
}

private void kioszt(){
      kiosztMunka myPanel = new kiosztMunka(MunkaLista.get(tableMunkak.convertRowIndexToModel(tableMunkak.getSelectedRow())));
      int result = JOptionPane.showConfirmDialog(null, myPanel, 
               "Csoport választása", JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION) {
          myPanel.munkaKioszt();
      }
      refreshTable();
}
    
    
    private void initComponents() {
        jpCommands = new JPanel();
        btDelete = new JButton();
        btKioszt = new JButton();
        btModMunka = new JButton();
        spMunkak = new JScrollPane();
        tableMunkak = new JTable();
        btNewMunka = new JButton();
        lbTitle = new JLabel();
        
        lbTitle.setFont(new Font("Tahoma", 1, 18)); // NOI18N
        lbTitle.setText("Munkák");
        
        spMunkak.setViewportView(tableMunkak);
        tableMunkak.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel selectionModel = tableMunkak.getSelectionModel();

selectionModel.addListSelectionListener(new ListSelectionListener() {
    public void valueChanged(ListSelectionEvent e) {
        if(tableMunkak.getSelectedRow()!=-1){
        btDelete.setEnabled(true);
        btModMunka.setEnabled(true);
        btKioszt.setEnabled(true);
        }else{
        btDelete.setEnabled(false);
        btModMunka.setEnabled(false);
        btKioszt.setEnabled(false);
        }
    }
});



        btDelete.setText("Törlés");
        btDelete.setEnabled(false);
        btDelete.addActionListener((ActionEvent evt) -> {
            delMunka();
        });   
        
         btModMunka.setText("Módosítás");
        btModMunka.setEnabled(false);
        btModMunka.addActionListener((ActionEvent evt) -> {
            modMunka();
        });   

        btNewMunka.setText("Új Munka");
        btNewMunka.addActionListener((ActionEvent e) -> {
            newMunka();
        });
        
         btKioszt.setText("Kiosztás");
        btKioszt.setEnabled(false);
        btKioszt.addActionListener((ActionEvent evt) -> {
            kioszt();
        });  
        
        GroupLayout jpCommandsLayout = new GroupLayout(jpCommands);
        jpCommands.setLayout(jpCommandsLayout);
        jpCommandsLayout.setHorizontalGroup(
            jpCommandsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jpCommandsLayout.createSequentialGroup()
                .addGroup(jpCommandsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(btNewMunka)
                    .addComponent(btDelete)
                    .addComponent(btModMunka)
                    .addComponent(btKioszt))
                .addGap(0, 20, Short.MAX_VALUE))
        );
        jpCommandsLayout.setVerticalGroup(
            jpCommandsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jpCommandsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btNewMunka)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btDelete)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btModMunka)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btKioszt)
                .addContainerGap(343, Short.MAX_VALUE))
        );
        
        GroupLayout thisLayout = new GroupLayout(this);
        this.setLayout(thisLayout);
        thisLayout.setHorizontalGroup(
            thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(thisLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spMunkak, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jpCommands, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        thisLayout.setVerticalGroup(
            thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, thisLayout.createSequentialGroup()
                .addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(spMunkak)
                    .addComponent(jpCommands, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    
    }    
}
