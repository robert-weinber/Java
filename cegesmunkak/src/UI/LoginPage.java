package UI;

import Controller.DBConnector;
import Modell.Ember;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginPage extends JPanel {

    private JButton btAdmin;
    private JButton btUzlet;
    private JButton btFejleszto;
    private ContentWindow holder;

    private JButton btLogin;
    private JTextField jelszo = new JTextField(15);
    private JComboBox nev;

    public LoginPage(ContentWindow holder) {
        this.holder = holder;
        btLogin = new JButton();
        btLogin.setPreferredSize(new Dimension(200, 100));
        nev = new JComboBox();
        btAdmin = new JButton();
        btAdmin.setPreferredSize(new Dimension(200, 100));
        btUzlet = new JButton();
        btUzlet.setPreferredSize(new Dimension(200, 100));
        btFejleszto = new JButton();
        btFejleszto.setPreferredSize(new Dimension(200, 100));

        /*this.add(btAdmin);
        this.add(btFejleszto);
        this.add(btUzlet);*/
        this.add(nev);
        this.add(jelszo);
        this.add(btLogin);
        jelszo.setToolTipText("1234");
        jelszo.setText("1234");
        btAdmin.setText("Admin");
        btAdmin.addActionListener((ActionEvent evt) -> {

            AdminPage mf = new AdminPage(holder, 15);
            holder.relpaceContent(mf);
            holder.setTitle("Admin");
        });

        btFejleszto.setText("Fejlesztő");
        btFejleszto.addActionListener((ActionEvent evt) -> {
            DeveloperPage mf = new DeveloperPage(holder, 15);
            holder.relpaceContent(mf);
            holder.setTitle("Fejlesztő");
        });

        btUzlet.setText("Üzletkötő");
        btUzlet.addActionListener((ActionEvent e) -> {
            SalesPage mf = new SalesPage(holder, 15);
            holder.relpaceContent(mf);
            holder.setTitle("Üzletkötő");
        });

        btLogin.setText("Bejelentkezés");
        btLogin.addActionListener((ActionEvent e) -> {
            Login();
        });

        String sql = "CALL selectEmber();";
        List rs = DBConnector.DBQuery(sql);

        for (Object o : rs) {
            String[] row = (String[]) o;
            Ember e = new Ember(Integer.parseInt(row[0]), row[1], row[2], row[3], row[4], Integer.parseInt(row[5]));
            nev.addItem(e);
        }

    }

    private void Login() {
        Ember emb = (Ember) nev.getSelectedItem();
        int myid = emb.getID();
        String mypos = emb.getPozicio();
        System.out.println(myid);
        System.out.println(mypos);

        String sql = "CALL pwcheck(" + myid + ",'" + jelszo.getText() + "');";
        List rs = DBConnector.DBQuery(sql);
        for (Object o : rs) {
            String[] row = (String[]) o;
            sql = row[0];
        }
        System.out.println(sql);
        System.out.println(sql);
        if (sql.equals("1")) {
            if (mypos.equals("Fejlesztő")) {
                System.out.println("Fejlesztő");
                DeveloperPage mf = new DeveloperPage(holder, myid);
                holder.relpaceContent(mf);
                holder.setTitle("Fejlesztő");
            } else if (mypos.equals("Kereskedő")) {
                System.out.println("Kereskedő");
                SalesPage mf = new SalesPage(holder, myid);
                holder.relpaceContent(mf);
                holder.setTitle("Kereskedő");
            } else if (mypos.equals("Admin")) {
                System.out.println("Admin");
                AdminPage mf = new AdminPage(holder, myid);
                holder.relpaceContent(mf);
                holder.setTitle("Admin");
            }
        }

    }

    public String get_SHA_512_SecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

}
