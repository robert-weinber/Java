package AdatModell;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.Timer;


public class Modell {

    public int fordításokSzáma = 0, megoldva = 0, RÁCS = 4, egyes = 0, kettes = 0,
            döntetlen = 0, elteltIdő = 0;
    public static int VISSZAFORDIDŐ = 3000, IDŐLIMIT = 60, KARTYADB = 16, MAXPÁR, kártyaMagasság=100, kártyaSzélesség=100;
    public File toplistaFájl = new File("./src/toplista.dat"),hangFájlKiosztás  = new File("./src/cards.wav"),hangFájlFordítás  = new File("./src/flip.wav"), configFájl = new File("./src/config.csv")/*, képekFájl = new File("./src/kepek/proba/képek.dat")*/;
    public AudioInputStream audioStream;
    public Clip audioClip;
    public static ArrayList<Integer> toplista = new ArrayList<>();
    public Integer[] parossag;
    public String[] config;
    public boolean vanConfig;
    File[] képFájlok;
    public ImageIcon[] képTömb;
    public String sajátKépek;
    public String[] képHelyek=new String[4];
    public ImageIcon háttérKép;
    public Kártya[] kártyaTömb;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    public int sorrend = 1, játékosszám = 0;
    public Kártya k1, k2;
    public Játékos Egyes = new Játékos(), Kettes = new Játékos();

    public Timer visszafordít;
    Timer hangTimer = new Timer(1500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                audioClip.close();
                audioStream.close();
                System.out.println("audio vége");
            } catch (IOException ex) {
                Logger.getLogger(Modell.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    });

    public void audioBetöltés(File hang){
        try {
            audioStream = AudioSystem.getAudioInputStream(hang);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            audioClip = (Clip) AudioSystem.getLine(info);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(Modell.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void lejátszás() throws LineUnavailableException, IOException{
        audioClip.open(audioStream);
        audioClip.start();

    }

    public void képBeolvasás(String képhelye) throws IOException {
        File mappa = new File(képhelye);
        képFájlok = mappa.listFiles();
        //////////////////////////////////////////KEVERÉS
        Random rnd = ThreadLocalRandom.current();
        for (int i = képFájlok.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            File a = képFájlok[index];
            képFájlok[index] = képFájlok[i];
            képFájlok[i] = a;
        }
        System.out.println(képFájlok.length+" képet találtam");
        képTömb = new ImageIcon[KARTYADB/2];
for (int i = 0; i < KARTYADB/2; i++)
{
    if(i<képFájlok.length){
        képTömb[i] = new ImageIcon(képFájlok[i].getPath());
        Image img = képTömb[i].getImage() ;
        Image ujImg = img.getScaledInstance( kártyaSzélesség, kártyaMagasság,  java.awt.Image.SCALE_SMOOTH ) ;
        képTömb[i] = new ImageIcon( ujImg );
}
}

        háttérKép = new ImageIcon("./src/kepek/proba/hatter.png");
        Image img = háttérKép.getImage() ;
        Image ujImg = img.getScaledInstance( kártyaSzélesség, kártyaMagasság,  java.awt.Image.SCALE_SMOOTH ) ;
        háttérKép = new ImageIcon( ujImg );
    }

    public void visszafordít() {            //ELTÜNTETI A SZÁMOKAT 3 SEC UTÁN
        k1.setVisszafordít();
        k2.setVisszafordít();
        if (visszafordít.isRunning()) {
            visszafordít.stop();
        }
    }

    public void configBetölt() throws FileNotFoundException,
            IOException, ClassNotFoundException, ClassCastException {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(configFájl), "ISO-8859-1"))) {

            String adat;
            while ((adat = br.readLine()) != null) {
                config = adat.split(";");
                String kdb = config[0];
                String párszám = config[1];
                String időmax = config[2];
                String visszaidő = config[3];
                String kephely = config[4];
            }

        } catch (EOFException e) {

        System.out.println(e);
        }

    }

    public void configMent() throws IOException {
        String MentettLista;

        FileWriter fileWriter = new FileWriter("src/config.csv");
        try {
            fileWriter.write(config[0] + ";" + config[1] + ";" + config[2] + ";" + config[3] + ";" + config[4] + "\n");
            MentettLista = config[0] + ";" + config[1] + ";" + config[2] + ";" + config[3] + ";" + config[4] + "\n";

            System.out.println(MentettLista);
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
        }
    }

    public void Beállít(int KártyaSzámIndex, int MaxPárIndex, int IdőIndex, int VisszaIndex, int kephely) throws IOException {
        config = new String[5];
        RÁCS = (KártyaSzámIndex + 2) * 2;
        MAXPÁR = (MaxPárIndex + 1) * 2;
        IDŐLIMIT = (IdőIndex + 1) * 30;
        VISSZAFORDIDŐ=(VisszaIndex+1)*1500;
        config[0] = KártyaSzámIndex + "";
        config[1] = MaxPárIndex + "";
        config[2] = IdőIndex + "";
        config[3] = VisszaIndex+"";
        config[4] = kephely+"";
        KARTYADB = RÁCS * RÁCS;
        if (kephely!=4)
        System.out.println("Képek helye: "+képHelyek[kephely]);
        System.out.println("Időlimit: " + IDŐLIMIT);
        System.out.println("Max Párok száma: " + MAXPÁR);
        System.out.println("Rács: " + RÁCS);
        System.out.println("Kártyák: " + KARTYADB);
        System.out.println("Visszafordítási idő: "+VISSZAFORDIDŐ);
        configMent();

    }

    public void toplistaBetölt() {
        try {
            ois = new ObjectInputStream(
                    new FileInputStream(toplistaFájl));
            while (true) {
                toplista.add((Integer) ois.readObject());
            }
        } catch (Exception e) {
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
            }
        }
    }                  // BETÖLT, KEZEL(SZÁMOL ÉS BELEÍR), AZTÁN MENT, MAJD MEGJELENÍT)

    public void toplistaMent() {
        try {
            oos = new ObjectOutputStream(
                    new FileOutputStream(toplistaFájl));
            for (Integer e : toplista) {
                oos.writeObject(e);
            }
        } catch (Exception e) {
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public void toplistátTöröl() {
        toplista.clear();
    }

    public void toplistáhozAd(int eredmény) {

        toplista.add(eredmény);
        Collections.sort(toplista, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -o1.compareTo(o2);
            }
        });

    }

    public int getEredmény() {     // PONTSZÁMÍTÁS
        int e = (int) Math.round((float) (10 * KARTYADB / 2 - fordításokSzáma - elteltIdő));
        System.out.println(e);
        return (e > 0) ? e : 0;
    }

    public DefaultListModel toplistaKiír() {
        DefaultListModel dlm = new DefaultListModel();
        int i = 0, sorszám = 1;
        while (i < toplista.size()) {
            int aktEredmény = toplista.get(i);
            String s = sorszám + ". helyezés: " + aktEredmény + " pont (";
            int aktDb = 0;
            while (i < toplista.size() && aktEredmény == toplista.get(i)) {
                aktDb++;
                i++;
            }
            s += aktDb + " db)";
            dlm.addElement(s);
            sorszám++;
        }
        return dlm;
    }

    public void parKeveres() {                          //SZÁMOK GYÁRTÁSA, KEVERÉSE
        parossag = new Integer[KARTYADB];
        int diff = 0;
        for (int p = 0; p < MAXPÁR; p++) {

            for (int i = 0; i < (KARTYADB / MAXPÁR); i++) {               //1-8

                parossag[i + diff] = i + 1;

            }
            diff = diff + (KARTYADB / MAXPÁR);
        }
        //KEVERÉS
        Random rnd = ThreadLocalRandom.current();
        for (int i = parossag.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Sima cserealgoritmus
            int a = parossag[index];
            parossag[index] = parossag[i];
            parossag[i] = a;
        }
        System.out.println(Arrays.toString(parossag));
    }
}
