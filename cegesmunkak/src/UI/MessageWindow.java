package UI;

import Controller.DBConnector;
import Modell.Message;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class MessageWindow extends JFrame {

    private JScrollPane content;
    private JPanel msgHolder;
    private JPanel msgCreator;
    private JTextField newMessage;
    private JButton btSend;
    private int myID;
    private int csopID;
    private String lastMsgTime = "";

    public MessageWindow(int me, int csop) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer");
                String namesql = "CALL selectCsoportTime(" + csop + ");";
                List rs = DBConnector.DBQuery(namesql);
                String latest = "";
                for (Object o : rs) {
                    String[] row = (String[]) o;
                    latest = row[0];
                }
                if (!lastMsgTime.equals(latest)) {
                    lastMsgTime = latest;
                    refreshMsg();
                }
            }
        }, 5000, 5000);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                timer.cancel();
            }
        });
        this.myID = me;
        this.csopID = csop;
        content = new JScrollPane();
        msgHolder = new JPanel();
        msgCreator = new JPanel();
        newMessage = new JTextField();
        btSend = new JButton();
        content.setViewportView(msgHolder);
        this.setLayout(new BorderLayout());
        msgHolder.setLayout(new GridLayout(0, 1));
        btSend.setPreferredSize(new Dimension(1000, 100));
        newMessage.setPreferredSize(new Dimension(1000, 100));
        this.add(content);
        msgCreator.setLayout(new BorderLayout());
        msgCreator.add(newMessage, BorderLayout.PAGE_START);
        msgCreator.add(btSend, BorderLayout.PAGE_END);
        this.add(msgCreator, BorderLayout.PAGE_END);
        btSend.setText("Küldés");
        btSend.addActionListener((ActionEvent e) -> {
            sendMsg();
        });
        String namesql = "CALL selectCsoportNevek(" + csop + ");";
        List rs = DBConnector.DBQuery(namesql);
        String name = "";
        for (Object o : rs) {
            String[] row = (String[]) o;
            name = row[0];
        }

        this.setTitle("Üzenőfal: " + name);
        this.setPreferredSize(new Dimension(1000, 700));
        this.setLocation(100, 100);
        this.setVisible(true);
        this.setResizable(false);
        this.pack();
        String sql = "CALL updateTimecheck(" + csopID + "," + myID + ");";
        String error = DBConnector.DBUpdate(sql);
        refreshMsg();
    }

    public void refreshMsg() {

        msgHolder.removeAll();
        String sql = "CALL selectMsgs(" + csopID + ");";
        List rs = DBConnector.DBQuery(sql);

        for (Object o : rs) {
            String[] row = (String[]) o;
            Message msg = null;
            if (Integer.parseInt(row[1]) == myID) {
                msg = new Message("Én", row[2], Color.green);
            } else {
                msg = new Message(row[0], row[2], Color.GRAY);
            }
            msg.setPreferredSize(new Dimension(500, 100));
            msgHolder.add(msg);
            msg.setFocusable(false);
        }
        reposition();
    }

    public void reposition() {
        JScrollBar vertical = content.getVerticalScrollBar();
        System.out.println(vertical.getMaximum());
        System.out.println(vertical.getValue());
        content.validate();
        vertical.setValue(vertical.getMaximum());
    }

    public void sendMsg() {
        if (!newMessage.getText().trim().equals("")) {
            String newmsg = newMessage.getText().trim();
            String sql = "CALL insertMsg(" + csopID + ", " + myID + ", '" + newmsg + "');";
            String error = DBConnector.DBUpdate(sql);
            System.out.println(error);
            refreshMsg();
            newMessage.setText("");
        }
    }
}
