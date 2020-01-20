package UI;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ContentWindow extends JFrame {

    private JPanel content;
    private LoginPage login;

    public ContentWindow() {
        content = new JPanel();
        content.setLayout(new GridLayout(0, 1));
        login = new LoginPage(this);

        content.add(login);
        this.add(content);
        this.setTitle("Bejelentkezés");
        this.setPreferredSize(new Dimension(1000, 700));
        this.setLocation(100, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
    }

    public void logout() {
        content.removeAll();
        login = new LoginPage(this);
        relpaceContent(login);
        this.setTitle("Bejelentkezés");
        this.pack();
    }

    public void relpaceContent(JPanel newPanel) {
        content.removeAll();
        content.add(newPanel);
        this.pack();
    }
}
