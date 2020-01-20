package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import AdatModell.Kártya;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.Timer;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class Ablak extends javax.swing.JFrame {

    AdatModell.Modell modell;
    Container ablak;
    public Timer időzítő = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            modell.elteltIdő++;
            int idő = modell.IDŐLIMIT - modell.elteltIdő;
            int perc = idő / 60, másodperc = idő % 60;
            String s = perc + ":" + String.format("%02d", másodperc);
            lbKijelzőKozep.setText(s);
            if (idő == 0) {
                időzítő.stop();
                vége(0);
            }
        }
    });

    public Ablak() {
        this.modell = new AdatModell.Modell();
        ablak = this.getContentPane();
        this.setTitle("Memóra Játék");
        
    }

    private void egyFőre() { // 1P mód
        modell.játékosszám = 1;
        modell.elteltIdő = 0;
        lbKijelzőBal.setText("Egyjátékos mód");
        lbKijelzőJobb.setText("0 próbálkozás");
        lbKijelzőKozep.setText("");
        System.out.println("Időlimit: " + modell.IDŐLIMIT);
        System.out.println("Max Párok száma: " + modell.MAXPÁR);
        System.out.println("Rács: " + modell.RÁCS);
        System.out.println("Kártyák: " + modell.KARTYADB);
        modell.parKeveres();
        kiosztas();
        időzítő.start();
    }

    public void kétFőre() { // 2P mód
        modell.játékosszám = 2;
        modell.Egyes.setPontszám(0);
        modell.Egyes.setAktív(true);
        modell.Kettes.setPontszám(0);
        lbKijelzőBal.setFont(new Font("Tahoma", Font.BOLD, 10));
        lbKijelzőJobb.setFont(new Font("Tahoma", Font.PLAIN, 10));
        lbKijelzőBal.setText("Első Játékos pontja: ");
        lbKijelzőJobb.setText("Második Játékos pontja: ");
        lbKijelzőKozep.setText("Első Játékos Próbálkozik");
        System.out.println("Időlimit: " + modell.IDŐLIMIT);
        System.out.println("Max Párok száma: " + modell.MAXPÁR);
        System.out.println("Rács: " + modell.RÁCS);
        System.out.println("Kártyák: " + modell.KARTYADB);
        modell.parKeveres();
        kiosztas();
    }

    public void kiosztas() { // Új leosztás
        modell.kártyaTömb = new Kártya[modell.KARTYADB];
        modell.megoldva = 0;
        pnKirakós.setLayout(new GridLayout(modell.RÁCS, 0));
        
        for (int i = 0; i < modell.KARTYADB; i++) {
            Kártya k = new Kártya();
            k.setId(i);
            k.setSize(new Dimension(100,100));
            k.setPárSzáma(modell.parossag[i]);
            k.setMargin(new Insets(0, 0, 0, 0));
            k.setBorder(null);
            k.setHáttér(modell.háttérKép);
            k.setSzimbólum(modell.képTömb[k.getPárSzáma()-1]);
            modell.kártyaTömb[i] = k;
            pnKirakós.add(k);
                k.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    értékel(k);
                }
            });
        }
        modell.visszafordít = new Timer(modell.VISSZAFORDIDŐ, new ActionListener() {                                 //EZ FORDÍTJA VISSZA A KÁRTYÁKAT 3 SEC MULVA(3000 millisec)
        @Override
        public void actionPerformed(ActionEvent ae) {
            modell.visszafordít();
        }
    });
        
        try {
            modell.lejátszás();
        } catch (LineUnavailableException | IOException ex) {
            Logger.getLogger(Ablak.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void értékel(Kártya k) { // Lépés kiértékelése
        k.setFelfordít();
        try {
            modell.audioBetöltés(modell.hangFájlFordítás);
            modell.lejátszás();
        } catch (LineUnavailableException | IOException ex) {
            Logger.getLogger(Ablak.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (1 == modell.sorrend) {
            if (modell.k1 != null && modell.k1.getFelfordítva() && modell.k1.isEnabled()) {
                modell.k1.setVisszafordít();
            }
            if (modell.visszafordít.isRunning()) {
                modell.visszafordít.stop();
            }
            if (modell.k2 != null && modell.k2.getFelfordítva() && modell.k2.isEnabled()) {
                modell.k2.setVisszafordít();
            }
            if (modell.visszafordít.isRunning()) {
                modell.visszafordít.stop();
            }
            modell.k1 = k;
            modell.sorrend = 2;
        }
        if (2 == modell.sorrend) {
            if (k.getId() != modell.k1.getId()) {

                modell.k2 = k;
                modell.sorrend = 1;
                modell.fordításokSzáma++;
                if (modell.játékosszám == 1) {
                    lbKijelzőJobb.setText(modell.fordításokSzáma + " próbálkozás");
                }
                if (modell.k1.getPárSzáma() == modell.k2.getPárSzáma() && modell.k1.getId() != modell.k2.getId()) {
                    modell.k1.setPárosítva(true);
                    modell.k2.setPárosítva(true);
                    modell.megoldva++;

                    if (modell.Egyes.getAktív()) {
                        modell.Egyes.setPontszám(modell.Egyes.getPontszám() + 1);
                        lbKijelzőBal.setText("Első Játékos Pontjai: " + modell.Egyes.getPontszám());
                    }
                    if (modell.Kettes.getAktív()) {
                        modell.Kettes.setPontszám(modell.Kettes.getPontszám() + 1);
                        lbKijelzőJobb.setText("Kettes Játékos Pontjai: " + modell.Kettes.getPontszám());
                    }

                    System.out.println(modell.megoldva);
                    System.out.println("Találat");
                } else {
                    if (modell.játékosszám == 2) {
                        if (modell.Egyes.getAktív()) {
                            modell.Egyes.setAktív(false);
                            modell.Kettes.setAktív(true);
                            lbKijelzőKozep.setText("Második Játékos Próbálkozik");
                            lbKijelzőJobb.setFont(new Font("Tahoma", Font.BOLD, 10));
                            lbKijelzőBal.setFont(new Font("Tahoma", Font.PLAIN, 10));
                        } else {
                            modell.Egyes.setAktív(true);
                            modell.Kettes.setAktív(false);
                            lbKijelzőBal.setFont(new Font("Tahoma", Font.BOLD, 10));
                            lbKijelzőJobb.setFont(new Font("Tahoma", Font.PLAIN, 10));
                            lbKijelzőKozep.setText("Első Játékos Próbálkozik");
                        }
                    }
                    System.out.println("Nincs találat");
                    modell.visszafordít.start();
                }
            }
        }
        if (modell.megoldva == modell.KARTYADB / 2) {
            vége(1);
        }
    }

    public void vége(int idő) { // Játék végi kiértékelés
        if (modell.játékosszám == 1) {
            időzítő.stop();
            int eredmény = modell.getEredmény();
            if (idő == 1) {
                Toplista(eredmény);
                lbPont.setText("Utolsó eredmény: " + eredmény);
            }
            if (idő == 0||idő==2) {
                Toplista(0);
                if(idő==0)
                lbPont.setText("Lejárt az idő!(0 pont)");
                if (idő==2)
                lbPont.setText("Feladtad!(0 pont)");
            }
            modell.fordításokSzáma = 0;
        } else {
            if (modell.Egyes.getPontszám() > modell.Kettes.getPontszám()) {
                lbGyőztes.setText("AZ EGYES JÁTÉKOS NYERT");
                modell.egyes++;
            } else if (modell.Egyes.getPontszám() < modell.Kettes.getPontszám()) {
                lbGyőztes.setText("A KETTES JÁTÉKOS NYERT");
                modell.kettes++;
            } else {
                lbGyőztes.setText("DÖNTETLEN");
                modell.döntetlen++;
            }
            modell.Egyes.setPontszám(0);
            modell.Kettes.setPontszám(0);
            lbElső.setText(modell.egyes + "");
            lbMásodik.setText(modell.kettes + "");
            lbDöntetlen.setText(modell.döntetlen + "");
            CardLayout cl = (CardLayout) (AblakKezelo.getLayout());
            cl.show(AblakKezelo, "eredmeny");
            this.pack();
        }
        for (Kártya kártya : modell.kártyaTömb) {
            pnKirakós.remove(kártya);
        }
        try {
            modell.audioStream.close(); // Audio lejátszása
            System.out.println("audio vége");
        } catch (IOException ex) {
            Logger.getLogger(Ablak.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }

    private void Toplista(int e) { // Ugrás a toplistára
        modell.toplistáhozAd(e);
        modell.toplistaMent();
        jlToplista.setModel(modell.toplistaKiír());
        CardLayout cl = (CardLayout) (AblakKezelo.getLayout());
        cl.show(AblakKezelo, "toplista");
    }

    private void Elment() { // Saját kép
        int KártyaSzámIndex = cbKártyaszám.getSelectedIndex();
        int MaxPárIndex = cbMaxPárok.getSelectedIndex();
        int IdőIndex = cbIdő.getSelectedIndex();
        int VisszaIndex = cbVisszaIdő.getSelectedIndex();
        int KépHelyeIndex = cbKépek.getSelectedIndex();
        try {
            modell.Beállít(KártyaSzámIndex, MaxPárIndex, IdőIndex, VisszaIndex, KépHelyeIndex);
            if(KépHelyeIndex!=4)
            modell.képBeolvasás(modell.képHelyek[KépHelyeIndex]);
            else
            modell.képBeolvasás(modell.sajátKépek);
        } catch (IOException ex) {
        }
        CardLayout cl = (CardLayout) (AblakKezelo.getLayout());
        cl.show(AblakKezelo, "fomenu");
    }
    
    
    
    private void ablakNyitás(){ // Alap képek betöltése
                modell.képHelyek[0]="./src/kepek/valami";
                modell.képHelyek[1]="./src/kepek/ezmegaz";
                modell.képHelyek[2]="./src/kepek/ilyesmi";
                modell.képHelyek[3]="./src/kepek/olyasmi";
                try {
                    
                    modell.configBetölt();
                    cbKártyaszám.setSelectedIndex(Integer.parseInt(modell.config[0]));
                    cbMaxPárok.setSelectedIndex(Integer.parseInt(modell.config[1]));
                    cbIdő.setSelectedIndex(Integer.parseInt(modell.config[2]));
                    cbVisszaIdő.setSelectedIndex(Integer.parseInt(modell.config[3]));
                    cbKépek.setSelectedIndex(Integer.parseInt(modell.config[4]));
                    modell.Beállít(Integer.parseInt(modell.config[0]), Integer.parseInt(modell.config[1]), Integer.parseInt(modell.config[2]), Integer.parseInt(modell.config[3]), Integer.parseInt(modell.config[4]));
                } catch (IOException | ClassNotFoundException | ClassCastException ex) {
                }                     
                
                String képhelye="./src/kepek/valami";
                if(cbKépek.getSelectedIndex()!=4)
                képhelye=modell.képHelyek[cbKépek.getSelectedIndex()];
                try {
                    modell.képBeolvasás(képhelye);
                } catch (IOException ex) {
                    Logger.getLogger(Ablak.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(modell.képTömb.length+" db kép");
                modell.toplistaBetölt();
                modell.audioBetöltés(modell.hangFájlKiosztás);
    }

    public void initComponents(Container Felület) { // UI Felépítése
        AblakKezelo = new JPanel(new CardLayout());
        ActionListener Főmenübe = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (AblakKezelo.getLayout());
                cl.show(AblakKezelo, "fomenu");
            }
        };
        Font Címsor = new Font("Tahoma", 1, 18);
        //FŐMENÜ
        jpFomenu = new JPanel();
        btUjJatek = new JButton();
        btToplista = new JButton();
        btLeiras = new JButton();
        lbFomenu = new JLabel();
        btBeallitas = new JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                modell.toplistaMent();
            }

            @Override
            public void windowOpened(java.awt.event.WindowEvent evt) {
                ablakNyitás();
            }
        });

        btUjJatek.setText("Új Játék");
        btUjJatek.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (AblakKezelo.getLayout());
                cl.show(AblakKezelo, "ujjatek");

            }
        });

        btToplista.setText("Toplista");
        btToplista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jlToplista.setModel(modell.toplistaKiír());
                CardLayout cl = (CardLayout) (AblakKezelo.getLayout());
                cl.show(AblakKezelo, "toplista");
            }
        });

        btBeallitas.setText("Beállítások");
        btBeallitas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (AblakKezelo.getLayout());
                cl.show(AblakKezelo, "beallitasok");
            }
        });

        btLeiras.setText("Leírás");
        btLeiras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (AblakKezelo.getLayout());
                cl.show(AblakKezelo, "leiras");
            }
        });

        lbFomenu.setFont(Címsor);
        lbFomenu.setHorizontalAlignment(SwingConstants.CENTER);
        lbFomenu.setVerticalAlignment(SwingConstants.CENTER);
        lbFomenu.setText("Főmenü");

        GroupLayout jpFomenuLayout = new GroupLayout(jpFomenu);
        jpFomenu.setLayout(jpFomenuLayout);
        jpFomenuLayout.setHorizontalGroup(
                jpFomenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lbFomenu, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btLeiras, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btBeallitas, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btToplista, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btUjJatek, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpFomenuLayout.setVerticalGroup(jpFomenuLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(jpFomenuLayout.createSequentialGroup()
                        .addGroup(jpFomenuLayout.createSequentialGroup()
                                .addComponent(lbFomenu, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btUjJatek, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btToplista, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btBeallitas, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btLeiras, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        ))
        );

        //ÚJ JÁTÉK
        jpUjJatek = new JPanel();
        btEgyjatekos = new JButton();
        btKetjatekos = new JButton();
        btVissza1 = new JButton();
        lbUjJatek = new JLabel();

        btEgyjatekos.setText("1 Játékos");
        btEgyjatekos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (AblakKezelo.getLayout());
                cl.show(AblakKezelo, "jatekmezo");
                egyFőre();
            }
        });

        btKetjatekos.setText("2 Játékos");
        btKetjatekos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (AblakKezelo.getLayout());
                cl.show(AblakKezelo, "jatekmezo");
                kétFőre();

            }
        });

        lbUjJatek.setFont(Címsor);
        lbUjJatek.setHorizontalAlignment(SwingConstants.CENTER);
        lbUjJatek.setVerticalAlignment(SwingConstants.CENTER);
        lbUjJatek.setText("Új Játék");

        btVissza1.setText("Vissza");
        btVissza1.addActionListener(Főmenübe);

        javax.swing.GroupLayout jpUjJatekLayout = new GroupLayout(jpUjJatek);
        jpUjJatek.setLayout(jpUjJatekLayout);
        jpUjJatekLayout.setHorizontalGroup(jpUjJatekLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jpUjJatekLayout.createParallelGroup()
                        .addComponent(btVissza1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbUjJatek, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btKetjatekos, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btEgyjatekos, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpUjJatekLayout.setVerticalGroup(jpUjJatekLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jpUjJatekLayout.createSequentialGroup()
                        .addComponent(lbUjJatek, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btEgyjatekos, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btKetjatekos, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btVissza1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        //BEÁLLÍTÁSOK
        jpBeallitas = new JPanel();
        btBeállítástMent = new JButton();
        btVissza2 = new JButton();
        cbKártyaszám = new JComboBox();
        cbIdő = new JComboBox();
        lbVisszaIdő = new JLabel();
        cbVisszaIdő = new JComboBox();
        cbKépek = new JComboBox();
        lbBeallitasok = new JLabel();
        cbMaxPárok = new JComboBox();
        lbMaxPárok = new JLabel();
        lbMaxIdo = new JLabel();
        lbKartyakSzama = new JLabel();
        lbKépek = new JLabel();
        pnConfigMező = new JPanel();

        cbKártyaszám.setModel(new DefaultComboBoxModel(new String[]{"4x4", "6x6", "8x8"}));

        cbMaxPárok.setModel(new DefaultComboBoxModel(new String[]{"2", "4"}));

        cbIdő.setModel(new DefaultComboBoxModel(new String[]{"0,5 perc", "1 perc", "1,5 perc", "2 perc", "2,5 perc", "3 perc"}));
        
        cbVisszaIdő.setModel(new DefaultComboBoxModel(new String[]{"Gyors(1,5s)", "Közepes(3s)", "Lassú(4,5s)"}));
        
        cbKépek.setModel(new DefaultComboBoxModel(new String[]{"1", "2", "3", "4", "Saját képek..."}));
        cbKépek.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        if (cbKépek.getSelectedIndex()==4) {
                            String sajátmappa="";
                            int result;
        
    JFileChooser chooser = new JFileChooser(); 
    chooser.setCurrentDirectory(new java.io.File("."));
    chooser.setDialogTitle("Saját képek kiválasztása...");
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    //
    // "All files" kikapcsolása.
    //
    chooser.setAcceptAllFileFilterUsed(false);
    //    
    if (chooser.showOpenDialog(ablak) == JFileChooser.APPROVE_OPTION) { 
      System.out.println("getCurrentDirectory(): " +  chooser.getCurrentDirectory());
      System.out.println("getSelectedFile() : "  +  chooser.getSelectedFile());
      modell.sajátKépek=chooser.getSelectedFile().getPath();
      }
    else {
        cbKépek.setSelectedIndex(0);
      System.out.println("No Selection ");
      }
     }     
     }
                }            
        );

        lbBeallitasok.setFont(Címsor);
        lbBeallitasok.setHorizontalAlignment(SwingConstants.CENTER);
        lbBeallitasok.setVerticalAlignment(SwingConstants.CENTER);
        lbBeallitasok.setText("Beállítások");

        lbKartyakSzama.setHorizontalAlignment(SwingConstants.CENTER);
        lbKartyakSzama.setVerticalAlignment(SwingConstants.CENTER);
        lbKartyakSzama.setText("Kártyák száma:");

        lbMaxPárok.setHorizontalAlignment(SwingConstants.CENTER);
        lbMaxPárok.setVerticalAlignment(SwingConstants.CENTER);
        lbMaxPárok.setText("Azonos lapok száma:");

        lbMaxIdo.setHorizontalAlignment(SwingConstants.CENTER);
        lbMaxIdo.setVerticalAlignment(SwingConstants.CENTER);
        lbMaxIdo.setText("Max idő:");
        
        lbVisszaIdő.setHorizontalAlignment(SwingConstants.CENTER);
        lbVisszaIdő.setVerticalAlignment(SwingConstants.CENTER);
        lbVisszaIdő.setText("Visszafordítási idő:");
        
        lbKépek.setHorizontalAlignment(SwingConstants.CENTER);
        lbKépek.setVerticalAlignment(SwingConstants.CENTER);
        lbKépek.setText("Képek témája:");

        btBeállítástMent.setText("Mentés");
        btBeállítástMent.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Elment();
            }

        });

        btVissza2.setText("Vissza");
        btVissza2.addActionListener(Főmenübe);

        pnConfigMező.setLayout(new GridLayout(0, 2));
        pnConfigMező.add(lbKartyakSzama);
        pnConfigMező.add(cbKártyaszám);
        pnConfigMező.add(lbMaxPárok);
        pnConfigMező.add(cbMaxPárok);
        pnConfigMező.add(lbKépek);
        pnConfigMező.add(cbKépek);
        pnConfigMező.add(lbMaxIdo);
        pnConfigMező.add(cbIdő);
        pnConfigMező.add(lbVisszaIdő);
        pnConfigMező.add(cbVisszaIdő);
        pnConfigMező.add(btBeállítástMent);
        pnConfigMező.add(btVissza2);

        javax.swing.GroupLayout jpBeallitasLayout = new GroupLayout(jpBeallitas);
        jpBeallitas.setLayout(jpBeallitasLayout);
        jpBeallitasLayout.setHorizontalGroup(jpBeallitasLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(pnConfigMező, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbBeallitasok, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpBeallitasLayout.setVerticalGroup(jpBeallitasLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jpBeallitasLayout.createSequentialGroup()
                        .addComponent(lbBeallitasok)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnConfigMező, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        //LEÍRÁS
        jpLeiras = new JPanel();
        btVissza3 = new JButton();
        lbLeiras = new JLabel();
        lbMagyarazat = new JLabel();

        lbLeiras.setFont(Címsor);
        lbLeiras.setText("Játékleírás");
        lbLeiras.setHorizontalAlignment(SwingConstants.CENTER);
        lbLeiras.setVerticalAlignment(SwingConstants.CENTER);

        lbMagyarazat.setFont(new Font("Tahoma", 1, 10));
        lbMagyarazat.setText("Ide jön a magyarázószöveg.");

        btVissza3.setText("Vissza");
        btVissza3.addActionListener(Főmenübe);

        GroupLayout jpLeirasLayout = new GroupLayout(jpLeiras);
        jpLeiras.setLayout(jpLeirasLayout);
        jpLeirasLayout.setHorizontalGroup(jpLeirasLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jpLeirasLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lbLeiras, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbMagyarazat, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btVissza3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpLeirasLayout.setVerticalGroup(jpLeirasLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jpLeirasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbLeiras)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbMagyarazat, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btVissza3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
        );

        //TOPLISTA
        jpToplista = new JPanel();
        btToplistaÜrítése = new JButton();
        spToplista = new JScrollPane();
        jlToplista = new JList();
        btVissza4 = new JButton();
        lbToplista = new JLabel();
        lbPont = new JLabel();

        lbToplista.setFont(Címsor);
        lbToplista.setHorizontalAlignment(SwingConstants.CENTER);
        lbToplista.setVerticalAlignment(SwingConstants.CENTER);
        lbToplista.setText("Toplista");

        spToplista.setViewportView(jlToplista);

        btToplistaÜrítése.setText("Törlés");
        btToplistaÜrítése.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                modell.toplistátTöröl();
            }
        });

        btVissza4.setText("Vissza");
        btVissza4.addActionListener(Főmenübe);

        javax.swing.GroupLayout jpToplistaLayout = new GroupLayout(jpToplista);
        jpToplista.setLayout(jpToplistaLayout);
        jpToplistaLayout.setHorizontalGroup(jpToplistaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jpToplistaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpToplistaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(jpToplistaLayout.createSequentialGroup()
                                        .addComponent(lbToplista)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(spToplista, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
                                .addGroup(jpToplistaLayout.createSequentialGroup()
                                        .addComponent(btToplistaÜrítése)
                                ))
                        .addGap(18, 18, 18)
                        .addGroup(jpToplistaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(btVissza4)
                                .addComponent(lbPont))
                        .addContainerGap())
        );
        jpToplistaLayout.setVerticalGroup(jpToplistaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jpToplistaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpToplistaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(spToplista, GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                                .addGroup(jpToplistaLayout.createSequentialGroup()
                                        .addGroup(jpToplistaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(lbToplista)
                                                .addComponent(lbPont))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpToplistaLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btVissza4)
                                .addComponent(btToplistaÜrítése)))
        );

        //JÁTÉKMEZŐ
        jpJatekmezo = new JPanel();
        pnKirakós = new JPanel();
        pnKijelző = new JPanel();
        btFeladás = new JButton();
        lbKijelzőBal = new JLabel();
        lbKijelzőJobb = new JLabel();
        lbKijelzőKozep = new JLabel();
        
        pnKirakós.setPreferredSize(new Dimension(400,400));
                
        pnKijelző.setPreferredSize(new Dimension(400, 50));
        pnKijelző.setLayout(new GridLayout(1, 3));
        pnKijelző.add(lbKijelzőBal);
        pnKijelző.add(lbKijelzőKozep);
        pnKijelző.add(lbKijelzőJobb);

        lbKijelzőBal.setHorizontalAlignment(SwingConstants.CENTER);
        lbKijelzőBal.setVerticalAlignment(SwingConstants.CENTER);

        lbKijelzőKozep.setHorizontalAlignment(SwingConstants.CENTER);
        lbKijelzőKozep.setVerticalAlignment(SwingConstants.CENTER);

        lbKijelzőJobb.setHorizontalAlignment(SwingConstants.CENTER);
        lbKijelzőJobb.setVerticalAlignment(SwingConstants.CENTER);
        
        btFeladás.setText("Feladás");
        btFeladás.setPreferredSize(new Dimension(400, 100));
        btFeladás.setBounds(WIDTH, WIDTH, WIDTH, 100);
        btFeladás.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                CardLayout cl = (CardLayout) (AblakKezelo.getLayout());
                vége(2);
                cl.show(AblakKezelo, "toplista");
                
            }
        });
        
        
        javax.swing.GroupLayout jpJatekmezoLayout = new GroupLayout(jpJatekmezo);
        jpJatekmezo.setLayout(jpJatekmezoLayout);
        jpJatekmezoLayout.setHorizontalGroup(jpJatekmezoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(pnKirakós)
                .addComponent(btFeladás, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnKijelző)
        );
        jpJatekmezoLayout.setVerticalGroup(jpJatekmezoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jpJatekmezoLayout.createSequentialGroup()
                        .addComponent(true, pnKijelző)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnKirakós)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(true, btFeladás))
        );

        //EREDMÉNY
        jpEredmény = new JPanel();
        btÚjra = new JButton();
        btVissza6 = new JButton();
        lbElsoFelirat = new JLabel();
        lbMasodikFelirat = new JLabel();
        lbDontetlenFelirat = new JLabel();
        lbElső = new JLabel();
        lbMásodik = new JLabel();
        lbDöntetlen = new JLabel();
        lbGyőztes = new JLabel();
        lbEddigi = new JLabel();

        btÚjra.setText("Újra");
        btÚjra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                CardLayout cl = (CardLayout) (AblakKezelo.getLayout());
                cl.show(AblakKezelo, "jatekmezo");
                kétFőre();
            }
        });

        lbGyőztes.setHorizontalAlignment(SwingConstants.CENTER);
        lbGyőztes.setVerticalAlignment(SwingConstants.CENTER);

        lbEddigi.setText("Eddigi eredmények:");
        lbEddigi.setHorizontalAlignment(SwingConstants.CENTER);
        lbEddigi.setVerticalAlignment(SwingConstants.CENTER);

        lbElső.setHorizontalAlignment(SwingConstants.CENTER);
        lbElső.setVerticalAlignment(SwingConstants.CENTER);

        lbElsoFelirat.setText("Első:");
        lbElsoFelirat.setHorizontalAlignment(SwingConstants.CENTER);
        lbElsoFelirat.setVerticalAlignment(SwingConstants.CENTER);

        lbMásodik.setHorizontalAlignment(SwingConstants.CENTER);
        lbMásodik.setVerticalAlignment(SwingConstants.CENTER);

        lbMasodikFelirat.setText("Második:");
        lbMasodikFelirat.setHorizontalAlignment(SwingConstants.CENTER);
        lbMasodikFelirat.setVerticalAlignment(SwingConstants.CENTER);

        lbDöntetlen.setHorizontalAlignment(SwingConstants.CENTER);
        lbDöntetlen.setVerticalAlignment(SwingConstants.CENTER);

        lbDontetlenFelirat.setText("Döntetlen:");
        lbDontetlenFelirat.setHorizontalAlignment(SwingConstants.CENTER);
        lbDontetlenFelirat.setVerticalAlignment(SwingConstants.CENTER);

        btVissza6.setText("Vissza");
        btVissza6.addActionListener(Főmenübe);

        GroupLayout jpEredményLayout = new GroupLayout(jpEredmény);
        jpEredmény.setLayout(jpEredményLayout);
        jpEredményLayout.setHorizontalGroup(
                jpEredményLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jpEredményLayout.createSequentialGroup()
                        .addComponent(btÚjra, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btVissza6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jpEredményLayout.createSequentialGroup()
                        .addGroup(jpEredményLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lbElsoFelirat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbMasodikFelirat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbDontetlenFelirat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpEredményLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lbElső, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbMásodik, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbDöntetlen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(lbGyőztes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbEddigi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jpEredményLayout.setVerticalGroup(
                jpEredményLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, jpEredményLayout.createSequentialGroup()
                        .addComponent(lbGyőztes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbEddigi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jpEredményLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lbElsoFelirat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbElső, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpEredményLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lbMasodikFelirat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbMásodik, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpEredményLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lbDontetlenFelirat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbDöntetlen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                        .addGroup(jpEredményLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btÚjra, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btVissza6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                )
        );

        AblakKezelo = new JPanel(new CardLayout());
        AblakKezelo.add(jpFomenu, "fomenu");
        AblakKezelo.add(jpBeallitas, "beallitasok");
        AblakKezelo.add(jpToplista, "toplista");
        AblakKezelo.add(jpUjJatek, "ujjatek");
        AblakKezelo.add(jpJatekmezo, "jatekmezo");
        AblakKezelo.add(jpEredmény, "eredmeny");
        AblakKezelo.add(jpLeiras, "leiras");
        Felület.add(AblakKezelo, BorderLayout.CENTER);
    }

    private JPanel AblakKezelo;
    //FŐMENÜ
    private JPanel jpFomenu;
    private JButton btBeallitas;
    private JButton btLeiras;
    private JButton btToplista;
    private JButton btUjJatek;
    private JLabel lbFomenu;
    //ÚJ JÁTÉK
    private JPanel jpUjJatek;
    private JButton btEgyjatekos;
    private JButton btKetjatekos;
    private JButton btVissza1;
    private JLabel lbUjJatek;
    //BEÁLLÍTÁSOK
    private JPanel jpBeallitas;
    private JButton btBeállítástMent;
    private JButton btVissza2;
    public static JComboBox cbIdő;
    public static JComboBox cbKártyaszám;
    private JLabel lbBeallitasok;
    public static JComboBox cbVisszaIdő;
    private JLabel lbVisszaIdő;
    public static JComboBox cbKépek;
    private JLabel lbKépek;
    public static JComboBox cbMaxPárok;
    private JLabel lbMaxPárok;
    private JLabel lbMaxIdo;
    private JLabel lbKartyakSzama;
    public static JPanel pnConfigMező;
    //LEÍRÁS
    private JPanel jpLeiras;
    private JButton btVissza3;
    private JLabel lbMagyarazat;
    private JLabel lbLeiras;
    //TOPLISTA
    private JPanel jpToplista;
    private JButton btToplistaÜrítése;
    private JScrollPane spToplista;
    public static JList jlToplista;
    private JButton btVissza4;
    private JLabel lbToplista;
    public static JLabel lbPont;
    //JÁTÉKMEZŐ
    public JPanel jpJatekmezo;
    private JButton btFeladás;
    public static JPanel pnKirakós;
    public static JPanel pnKijelző;
    public static JLabel lbKijelzőBal;
    public static JLabel lbKijelzőJobb;
    public static JLabel lbKijelzőKozep;
    //EREDMÉNY
    private JPanel jpEredmény;
    private JButton btÚjra;
    private JButton btVissza6;
    private JLabel lbElsoFelirat;
    private JLabel lbMasodikFelirat;
    private JLabel lbDontetlenFelirat;
    public static JLabel lbElső;
    public static JLabel lbMásodik;
    public static JLabel lbDöntetlen;
    public static JLabel lbGyőztes;
    public JLabel lbEddigi;
}
