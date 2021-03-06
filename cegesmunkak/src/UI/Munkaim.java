package UI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Controller.DBConnector;
import Modell.Munka;
import java.awt.BorderLayout;
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
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Wolfram
 */
public class Munkaim extends JPanel{
    
    private JPanel jpCommands;
   // private JButton btDelete;
    private JScrollPane spMunkak;
    public static JTable tableMunkak;
    private JButton btLogWork;
   // private JButton btModEmber;
    private JLabel lbTitle;
    private JPanel munkaholder;
    
    private List<Munka> MunkaLista;
    DefaultTableModel dtm;
    Object[] oszlopnevek;
    private int devid;
    
    
    public Munkaim(int devid) {
        this.devid=devid;
       oszlopnevek =new Object[]{"ID","FelID","ÜzlID","Megrendelő","Megnevezés","Típus","Státusz","Összköltség","Csoport","Felelős","Üzletkötő","Dátum"};

initComponents();
refreshTable();
    }
    
private void refreshTable(){
dtm=null;
   MunkaLista=null;
   MunkaLista=new ArrayList<>();
    dtm = new DefaultTableModel(oszlopnevek,0);

            /*String sql = "select `munk`.`id` AS `ID`,"
                    + "`csop`.`nev` AS `csoport`,"
                    + "`munk`.`felvetelIdeje` AS `datum`,"
                    + "`munk`.`megrendelo` AS `megrendelo`,"
                    + "`munk`.`megnevezes` AS `megnevezes`,"
                    + "concat(`vez`.`veznev`,' ',`vez`.`kernev`) AS `felelos`,"
                    + "concat(`uzl`.`veznev`,' ',`uzl`.`kernev`) AS `uzletkoto`,"
                    + "`mt`.`nev` AS `tipus`,"
                    + "`ms`.`nev` AS `statusz`,"
                    + "`munk`.`osszkoltseg` AS `osszkoltseg`, "
                    + "`munk`.`felelos` AS `felid`, "
                    + "`munk`.`uzletkoto` AS `uzlid` "
                    + "from `munkak` `munk` join `csoportok` `csop` on`munk`.`csoport` = `csop`.`id` "
                    + "join `emberek` `vez` on `munk`.`felelos` = `vez`.`id` "
                    + "join `emberek` `uzl` on `munk`.`uzletkoto` = `uzl`.`id` "
                    + "join `munkatipus` `mt` on `munk`.`tipus` = `mt`.`id`"
                    + "join `munkastatus` `ms` on `munk`.`status` = `ms`.`id`"
                    + "where `munk`.`id` in (select distinct munk.id from  `munkak` `munk` left join `csoportok` `csop` on csop.id=munk.csoport left join csoporttagsag cst on csop.id=cst.id where `munk`.`felelos`="+devid+" or `munk`.`uzletkoto`="+devid+" or `cst`.`tag`="+devid+");";*/
            String sql="CALL selectMyMunka("+devid+");";
            List rs = DBConnector.DBQuery(sql);
                munkaholder = new JPanel(new BorderLayout()/*new GridLayout(0,1)*/);
                boolean gray=true;
            for (Object o : rs) {
                gray=!gray;
			String[] row = (String[]) o;
                        /*for (int i = 0; i < row.length; i++) {
                            System.out.println(row[i]);
                }*/
                Munka e = new Munka(Integer.parseInt(row[0]),row[1],row[2],row[3],row[4],row[5],row[6],row[7],row[8],Integer.parseInt(row[9]),Integer.parseInt(row[10]),Integer.parseInt(row[11]));
             MunkaLista.add(e);
                dtm.addRow(e.getAdatok());
               /* if(gray)
            m.setBackground(Color.gray);
                m.setFocusable(true);
                m.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(selectedMunka!=null)
                        selectedMunka.setBorder(null);
                        System.out.println(m.getOsszkoltseg());
                        m.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                        selectedMunka=m;
                        btModEmber.setEnabled(true);
                        btDelete.setEnabled(true);
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
//                        Font fs=m.getComponent(1).getFont();
//                        Font f = new Font(fs.getFontName(), fs.getStyle(), fs.getSize()+2);
//                        m.getComponent(1).setFont(f);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
//                        Font fs=m.getComponent(1).getFont();
//                        Font f = new Font(fs.getFontName(), fs.getStyle(), fs.getSize()-2);
//                        m.getComponent(1).setFont(f);
                    }
                });*/
               // munkaholder.add(m, BorderLayout.NORTH);
		}
               // spMunkak.setViewportView(munkaholder);
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
    
private void logWork(){
      newLog myPanel = new newLog();
      int result = JOptionPane.showConfirmDialog(null, myPanel, 
               "Munka loggolása", JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION) {
          myPanel.AddNewLog(MunkaLista.get(tableMunkak.convertRowIndexToModel(tableMunkak.getSelectedRow())).getID(),devid);
      }
      refreshTable();
}

//private void modEmber(){
      /*modEmber myPanel = new modEmber(MunkaLista.get(tableEmberek.convertRowIndexToModel(tableEmberek.getSelectedRow())));
      int result = JOptionPane.showConfirmDialog(null, myPanel, 
               "Ember módosítása", JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION) {
          myPanel.modThisEmber();
      }
      refreshTable();*/
//}

/*private void delEmber(){
      int result = JOptionPane.showConfirmDialog(null, "Biztosan törölni akarja?", 
               "Törlés", JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION) {
      String sql="delete from munkak where id='"+MunkaLista.get(tableMunkak.convertRowIndexToModel(tableMunkak.getSelectedRow())).getID()+"'";
      String errormsg = DBConnector.DBUpdate(sql);
      System.out.println(errormsg);
      refreshTable();
      }
}*/
    
    
    private void initComponents() {
        jpCommands = new JPanel();
        btLogWork = new JButton();
       // btModEmber = new JButton();
        spMunkak = new JScrollPane();
        tableMunkak = new JTable();
       // btNewEmber = new JButton();
        lbTitle = new JLabel();
        
        lbTitle.setFont(new Font("Tahoma", 1, 18)); // NOI18N
        lbTitle.setText("Munkaim");
        
        spMunkak.setViewportView(tableMunkak);
        tableMunkak.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      //  ListSelectionModel selectionModel = tableMunkak.getSelectionModel();
/*
selectionModel.addListSelectionListener(new ListSelectionListener() {
    public void valueChanged(ListSelectionEvent e) {
        if(tableMunkak.getSelectedRow()!=-1){
        btDelete.setEnabled(true);
        btModEmber.setEnabled(true);
        }else{
        btDelete.setEnabled(false);
        btModEmber.setEnabled(false);
        }
    }
});*/


/*
        btDelete.setText("Törlés");
        btDelete.setEnabled(false);
        btDelete.addActionListener((ActionEvent evt) -> {
            delEmber();
        });   
        
         btModEmber.setText("Módosítás");
        btModEmber.setEnabled(false);
        btModEmber.addActionListener((ActionEvent evt) -> {
            modEmber();
        });  */ 

        btLogWork.setText("Munka loggolása");
        btLogWork.addActionListener((ActionEvent e) -> {
            logWork();
        });
        
        GroupLayout jpCommandsLayout = new GroupLayout(jpCommands);
        jpCommands.setLayout(jpCommandsLayout);
        jpCommandsLayout.setHorizontalGroup(
            jpCommandsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jpCommandsLayout.createSequentialGroup()
                .addGroup(jpCommandsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(btLogWork)
                   /* .addComponent(btDelete)
                    .addComponent(btModEmber)*/)
                .addGap(0, 20, Short.MAX_VALUE))
        );
        jpCommandsLayout.setVerticalGroup(
            jpCommandsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jpCommandsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btLogWork)
              /*  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btDelete)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btModEmber)*/
                .addContainerGap(343, Short.MAX_VALUE))
        );
        
        GroupLayout thisLayout = new GroupLayout(this);
        this.setLayout(thisLayout);
        thisLayout.setHorizontalGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(thisLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spMunkak, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jpCommands, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        thisLayout.setVerticalGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, thisLayout.createSequentialGroup()
                .addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(spMunkak)
                    .addComponent(jpCommands, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    
    }    
}
