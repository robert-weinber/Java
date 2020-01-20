package UI;

import Controller.DBConnector;
import Modell.Csoport;
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

public class Csoportok extends JPanel {

    private JPanel jpCommands;
    private JButton btDelete;
    private JScrollPane spCsoportok;
    public static JTable tableCsoportok;
    private JButton btNewCsoport;
    private JButton btModTagok;
    private JButton btModCsoport;
    private JLabel lbTitle;

    DefaultTableModel dtm;
    Object[] oszlopnevek;
    List<Csoport> CsoportLista;

    public Csoportok() {

        initComponents();
        oszlopnevek = new Object[]{"ID", "Név", "Leírás"};

        refreshTable();

    }

    private void refreshTable() {
        dtm = null;
        CsoportLista = null;
        CsoportLista = new ArrayList<>();
        dtm = new DefaultTableModel(oszlopnevek, 0);

        String sql = "CALL selectCsoport();";
        List rs = DBConnector.DBQuery(sql);
        for (Object o : rs) {
            String[] row = (String[]) o;
            Csoport e = new Csoport(Integer.parseInt(row[0]), row[1], row[2]);
            CsoportLista.add(e);
            dtm.addRow(e.getAdatok());
        }
        tableCsoportok.setModel(dtm);
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableCsoportok.getModel());
        tableCsoportok.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);

        TableColumnModel tcm = tableCsoportok.getColumnModel();
        tcm.removeColumn(tcm.getColumn(0));
        System.out.println(tableCsoportok.getModel().getValueAt(1, 0));

    }

    private void newCsoport() {
        newCsoport myPanel = new newCsoport();
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Új csoport felvétele", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            myPanel.AddNewCsoport();
        }
        refreshTable();
    }

    private void modCsoport() {
        modCsoport myPanel = new modCsoport(CsoportLista.get(tableCsoportok.convertRowIndexToModel(tableCsoportok.getSelectedRow())));
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Csoport módosítása", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            myPanel.modThisCsoport();
        }
        refreshTable();
    }

    private void modTagok() {
        modTagok myPanel = new modTagok(CsoportLista.get(tableCsoportok.convertRowIndexToModel(tableCsoportok.getSelectedRow())));
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Tagok", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
        }
        refreshTable();
    }

    private void delCsoport() {
        int result = JOptionPane.showConfirmDialog(null, "Biztosan törölni akarja?",
                "Törlés", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String sql = "delete from csoportok where id='" + tableCsoportok.getModel().getValueAt(tableCsoportok.convertRowIndexToModel(tableCsoportok.getSelectedRow()), 0) + "'";
            System.out.println(sql);
            String errormsg = DBConnector.DBUpdate(sql);
            System.out.println(errormsg);
            refreshTable();
        }
    }

    private void initComponents() {
        jpCommands = new JPanel();
        btDelete = new JButton();
        btModTagok = new JButton();
        btModCsoport = new JButton();
        spCsoportok = new JScrollPane();
        tableCsoportok = new JTable();
        btNewCsoport = new JButton();
        lbTitle = new JLabel();

        lbTitle.setFont(new Font("Tahoma", 1, 18));
        lbTitle.setText("Csoportok");

        spCsoportok.setViewportView(tableCsoportok);
        tableCsoportok.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel selectionModel = tableCsoportok.getSelectionModel();

        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (tableCsoportok.getSelectedRow() != -1) {
                    btDelete.setEnabled(true);
                    btModTagok.setEnabled(true);
                    btModCsoport.setEnabled(true);
                } else {
                    btDelete.setEnabled(false);
                    btModTagok.setEnabled(false);
                    btModCsoport.setEnabled(false);
                }
            }
        });

        btDelete.setText("Törlés");
        btDelete.setEnabled(false);
        btDelete.addActionListener((ActionEvent evt) -> {
            delCsoport();
        });

        btModCsoport.setText("Módosítás");
        btModCsoport.setEnabled(false);
        btModCsoport.addActionListener((ActionEvent evt) -> {
            modCsoport();
        });

        btModTagok.setText("Tagok");
        btModTagok.setEnabled(false);
        btModTagok.addActionListener((ActionEvent evt) -> {
            modTagok();
        });

        btNewCsoport.setText("Új Csoport");
        btNewCsoport.addActionListener((ActionEvent e) -> {
            newCsoport();
        });

        GroupLayout jpCommandsLayout = new GroupLayout(jpCommands);
        jpCommands.setLayout(jpCommandsLayout);
        jpCommandsLayout.setHorizontalGroup(
                jpCommandsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jpCommandsLayout.createSequentialGroup()
                                .addGroup(jpCommandsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(btNewCsoport)
                                        .addComponent(btDelete)
                                        .addComponent(btModCsoport)
                                        .addComponent(btModTagok))
                                .addGap(0, 20, Short.MAX_VALUE))
        );
        jpCommandsLayout.setVerticalGroup(
                jpCommandsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jpCommandsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btNewCsoport)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btDelete)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btModCsoport)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btModTagok)
                                .addContainerGap(343, Short.MAX_VALUE))
        );

        GroupLayout thisLayout = new GroupLayout(this);
        this.setLayout(thisLayout);
        thisLayout.setHorizontalGroup(
                thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(thisLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(spCsoportok, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jpCommands, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        thisLayout.setVerticalGroup(
                thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, thisLayout.createSequentialGroup()
                                .addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(spCsoportok)
                                        .addComponent(jpCommands, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );

    }
}
