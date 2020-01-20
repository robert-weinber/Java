package UI;

import Controller.DBConnector;
import Modell.Ember;
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

public class Emberek extends JPanel {

    private JPanel jpCommands;
    private JButton btDelete;
    private JScrollPane spEmberek;
    public static JTable tableEmberek;
    private JButton btNewEmber;
    private JButton btModEmber;
    private JLabel lbTitle;

    DefaultTableModel dtm;
    Object[] oszlopnevek;
    List<Ember> EmberLista;

    public Emberek() {

        initComponents();
        oszlopnevek = new Object[]{"ID", "Név", "Pozíció", "Kezdés", "Órabér"};
        refreshTable();

    }

    private void refreshTable() {
        dtm = null;
        EmberLista = null;
        EmberLista = new ArrayList<>();
        dtm = new DefaultTableModel(oszlopnevek, 0);
        String sql = "CALL selectEmber();";
        List rs = DBConnector.DBQuery(sql);
        for (Object o : rs) {
            String[] row = (String[]) o;
            Ember e = new Ember(Integer.parseInt(row[0]), row[1], row[2], row[3], row[4], Integer.parseInt(row[5]));
            EmberLista.add(e);
            dtm.addRow(e.getAdatok());
        }
        tableEmberek.setModel(dtm);
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableEmberek.getModel());
        tableEmberek.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);

        TableColumnModel tcm = tableEmberek.getColumnModel();
        tcm.removeColumn(tcm.getColumn(0));
        System.out.println(tableEmberek.getModel().getValueAt(1, 0));

    }

    private void newEmber() {
        newEmber myPanel = new newEmber();
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Új ember felvétele", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            myPanel.AddNewEmber();
        }
        refreshTable();
    }

    private void modEmber() {
        modEmber myPanel = new modEmber(EmberLista.get(tableEmberek.convertRowIndexToModel(tableEmberek.getSelectedRow())));
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Ember módosítása", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            myPanel.modThisEmber();
        }
        refreshTable();
    }

    private void delEmber() {
        int result = JOptionPane.showConfirmDialog(null, "Biztosan törölni akarja?",
                "Törlés", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String sql = "delete from emberek where id='" + tableEmberek.getModel().getValueAt(tableEmberek.convertRowIndexToModel(tableEmberek.getSelectedRow()), 0) + "'";
            System.out.println(sql);
            String errormsg = DBConnector.DBUpdate(sql);
            System.out.println(errormsg);
            refreshTable();
        }
    }

    private void initComponents() {
        jpCommands = new JPanel();
        btDelete = new JButton();
        btModEmber = new JButton();
        spEmberek = new JScrollPane();
        tableEmberek = new JTable();
        btNewEmber = new JButton();
        lbTitle = new JLabel();

        lbTitle.setFont(new Font("Tahoma", 1, 18));
        lbTitle.setText("Emberek");

        spEmberek.setViewportView(tableEmberek);
        tableEmberek.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel selectionModel = tableEmberek.getSelectionModel();

        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (tableEmberek.getSelectedRow() != -1) {
                    btDelete.setEnabled(true);
                    btModEmber.setEnabled(true);
                } else {
                    btDelete.setEnabled(false);
                    btModEmber.setEnabled(false);
                }
            }
        });

        btDelete.setText("Törlés");
        btDelete.setEnabled(false);
        btDelete.addActionListener((ActionEvent evt) -> {
            delEmber();
        });

        btModEmber.setText("Módosítás");
        btModEmber.setEnabled(false);
        btModEmber.addActionListener((ActionEvent evt) -> {
            modEmber();
        });

        btNewEmber.setText("Új Ember");
        btNewEmber.addActionListener((ActionEvent e) -> {
            newEmber();
        });

        GroupLayout jpCommandsLayout = new GroupLayout(jpCommands);
        jpCommands.setLayout(jpCommandsLayout);
        jpCommandsLayout.setHorizontalGroup(
                jpCommandsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jpCommandsLayout.createSequentialGroup()
                                .addGroup(jpCommandsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(btNewEmber)
                                        .addComponent(btDelete)
                                        .addComponent(btModEmber))
                                .addGap(0, 20, Short.MAX_VALUE))
        );
        jpCommandsLayout.setVerticalGroup(
                jpCommandsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jpCommandsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btNewEmber)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btDelete)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btModEmber)
                                .addContainerGap(343, Short.MAX_VALUE))
        );

        GroupLayout thisLayout = new GroupLayout(this);
        this.setLayout(thisLayout);
        thisLayout.setHorizontalGroup(
                thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(thisLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(spEmberek, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jpCommands, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        thisLayout.setVerticalGroup(
                thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, thisLayout.createSequentialGroup()
                                .addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(spEmberek)
                                        .addComponent(jpCommands, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );

    }
}
