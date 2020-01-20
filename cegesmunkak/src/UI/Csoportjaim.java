package UI;

import Controller.DBConnector;
import Modell.Csoport;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Csoportjaim extends JPanel {

    private JPanel jpCommands;
    private JButton btMess;
    private JScrollPane spCsoportok;
    public static JTable tableCsoportok;
    private JLabel lbTitle;
    private JPanel csoportholder;

    private List<Csoport> CsoportLista;
    private Csoport selectedCsoport;
    private int myID;
    private int selectedCsop;

    public Csoportjaim(int myid) {
        this.myID = myid;
        initComponents();
        refreshTable();
    }

    private void refreshTable() {
        btMess.setEnabled(false);
        CsoportLista = null;
        CsoportLista = new ArrayList<>();

        String sql = "CALL selectMyCsop(" + myID + ");";
        List rs = DBConnector.DBQuery(sql);
        csoportholder = new JPanel();
        csoportholder.setLayout(new BoxLayout(csoportholder, BoxLayout.PAGE_AXIS));
        csoportholder.setLayout(new GridLayout(0, 1));
        boolean gray = false;
        int num = -1;
        JPanel title = new JPanel();
        title.setLayout(new GridLayout(1, 4));
        title.add(new JLabel("NÉV"));
        title.add(new JLabel("LEÍRÁS"));
        title.add(new JLabel("POZÍCIÓ"));
        title.add(new JLabel("ÜZENETEK"));
        csoportholder.add(title);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        System.out.println(rs.size());
        for (Object o : rs) {
            gray = !gray;
            num++;
            String[] row = (String[]) o;
            Csoport cs = new Csoport(Integer.parseInt(row[0]), row[1], row[2], row[3]);
            Date lastdate = null;
            Date checkdate = null;
            System.out.println(row[4]);
            System.out.println(row[5]);
            try {
                if (row[4] != null) {
                    checkdate = dateFormat.parse(row[4]);
                }
                if (row[5] != null) {
                    lastdate = dateFormat.parse(row[5]);
                }
            } catch (ParseException ex) {
                Logger.getLogger(Csoportjaim.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(row[4]);
            }
            if (checkdate == null || lastdate.after(checkdate)) {
                cs.setOlvasatlan();
                System.out.println(checkdate);
                System.out.println(lastdate);
                System.out.println("VAN ÚJABB");
            }
            cs.setPreferredSize(new Dimension(500, 100));
            CsoportLista.add(cs);
            if (gray) {
                cs.setBackground(Color.gray);
            }
            cs.setFocusable(true);
            cs.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (selectedCsoport != cs) {
                        if (selectedCsoport != null) {
                            selectedCsoport.setBorder(null);
                        }

                        selectedCsoport = cs;
                        selectedCsop = cs.getID();
                        System.out.println(cs.getNev());
                        cs.setBorder(BorderFactory.createLineBorder(Color.black, 3));
                        btMess.setEnabled(true);
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (cs != selectedCsoport) {
                        cs.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (cs != selectedCsoport) {
                        cs.setBorder(null);
                    }
                }
            });
            csoportholder.add(cs);

        }
        spCsoportok.setViewportView(csoportholder);
    }

    private void showMSG() {
        MessageWindow msgWin = new MessageWindow(myID, selectedCsop);
        msgWin.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Üzenetek bezárva");
                e.getWindow().dispose();
                refreshTable();
            }
        });
    }

    private void initComponents() {
        GridLayout glComm = new GridLayout(3, 1);
        jpCommands = new JPanel(glComm);
        spCsoportok = new JScrollPane();
        tableCsoportok = new JTable();
        btMess = new JButton();
        lbTitle = new JLabel();

        lbTitle.setFont(new Font("Tahoma", 1, 18));
        lbTitle.setText("Emberek");

        btMess.setText("Üzenőfal");
        btMess.addActionListener((ActionEvent e) -> {
            showMSG();
        });

        GroupLayout jpCommandsLayout = new GroupLayout(jpCommands);
        jpCommands.setLayout(jpCommandsLayout);
        jpCommandsLayout.setHorizontalGroup(
                jpCommandsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jpCommandsLayout.createSequentialGroup()
                                .addGroup(jpCommandsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(btMess))
                                .addGap(0, 20, Short.MAX_VALUE))
        );
        jpCommandsLayout.setVerticalGroup(
                jpCommandsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jpCommandsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btMess)
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
