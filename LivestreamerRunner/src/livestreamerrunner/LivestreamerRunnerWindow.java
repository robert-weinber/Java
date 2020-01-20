package livestreamerrunner;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolfram
 */
public class LivestreamerRunnerWindow extends javax.swing.JFrame {
    public TreeMap<Integer, Kedvenc> lista = new TreeMap<>();
    private static TreeMap<String,Integer> indexek=new TreeMap<>();
    private File fájl = new File("./kedvencek.csv");;

    public LivestreamerRunnerWindow() throws IOException, FileNotFoundException, ClassNotFoundException {
        initComponents();
        Name.setText("smitegame");
        lbMentés.setVisible(false);
        Betölt();
        listaFeltöltés();
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Name = new javax.swing.JTextField();
        Play = new javax.swing.JButton();
        Quality = new javax.swing.JComboBox();
        lbName = new javax.swing.JLabel();
        lbQuality = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlKedvencek = new javax.swing.JList();
        lbKedvencek = new javax.swing.JLabel();
        btMentés = new javax.swing.JButton();
        txtLeírás = new javax.swing.JTextField();
        lbLeírás = new javax.swing.JLabel();
        lbMentés = new javax.swing.JLabel();
        btDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LivestreamerRunner");
        setResizable(false);

        Name.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Name.setText("jTextField1");

        Play.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Play.setText("Play");
        Play.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayActionPerformed(evt);
            }
        });

        Quality.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Quality.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "source", "high", "medium", "low", "mobile" }));

        lbName.setText("Channel Name:");

        lbQuality.setText("Quality:");

        jlKedvencek.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jlKedvencek.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jlKedvencek.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlKedvencekMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jlKedvencek);

        lbKedvencek.setText("Kedvencek:");

        btMentés.setText("Mentés Kedvencnek");
        btMentés.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btMentésActionPerformed(evt);
            }
        });

        txtLeírás.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLeírásActionPerformed(evt);
            }
        });

        lbLeírás.setText("Leírás:");

        lbMentés.setText("Mentve");

        btDelete.setText("Töröl");
        btDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtLeírás)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbQuality)
                        .addGap(128, 128, 128))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Quality, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbKedvencek)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btDelete)
                        .addGap(18, 18, 18)
                        .addComponent(lbMentés)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btMentés, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbLeírás)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(Play, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbName)
                    .addComponent(lbQuality))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Quality, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btMentés)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbLeírás))
                    .addComponent(Play, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLeírás, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbKedvencek)
                    .addComponent(lbMentés)
                    .addComponent(btDelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayActionPerformed

        try {

            String streamname = Name.getText();
            String streamquality = Quality.getSelectedItem().toString();
            Process p = Runtime.getRuntime().exec("cmd /c livestreamer.exe twitch.tv/" + streamname + " " + streamquality);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }

        } catch (IOException e1) {
        } catch (InterruptedException e2) {
        }

        System.out.println("Done");
    }//GEN-LAST:event_PlayActionPerformed

    private void btMentésActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btMentésActionPerformed
        try {
            KedvencekhezAd();
            Ment();
            listaFeltöltés();
            
        } catch (IOException ex) {
            Logger.getLogger(LivestreamerRunnerWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        lbMentés.setVisible(true);
        
    }//GEN-LAST:event_btMentésActionPerformed

    private void txtLeírásActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLeírásActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtLeírásActionPerformed

    private void jlKedvencekMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlKedvencekMouseClicked
        // TODO add your handling code here:
          Name.setText(lista.get(jlKedvencek.getSelectedIndex()).getStreamName());
//          Name.setText(""+jlKedvencek.getSelectedIndex());
    }//GEN-LAST:event_jlKedvencekMouseClicked

    private void btDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeleteActionPerformed
        // TODO add your handling code here:
        lista.remove(jlKedvencek.getSelectedIndex());
        try {
            Ment();
            listaFeltöltés();
        } catch (IOException ex) {
            Logger.getLogger(LivestreamerRunnerWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btDeleteActionPerformed

      private void KedvencekMousePressed(java.awt.event.MouseEvent evt) {                                    
        // TODO add your handling code here:
//        Name.setText(lista.get(jlKedvencek.getSelectedIndex()).getStreamName()); 
//          System.out.println(lista.get(jlKedvencek.getSelectedIndex()).getStreamName());
    } 
    public static void main(String args[]) {

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LivestreamerRunnerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LivestreamerRunnerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LivestreamerRunnerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LivestreamerRunnerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new LivestreamerRunnerWindow().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(LivestreamerRunnerWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(LivestreamerRunnerWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Name;
    private javax.swing.JButton Play;
    private javax.swing.JComboBox Quality;
    private javax.swing.JButton btDelete;
    private javax.swing.JButton btMentés;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList jlKedvencek;
    private javax.swing.JLabel lbKedvencek;
    private javax.swing.JLabel lbLeírás;
    private javax.swing.JLabel lbMentés;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbQuality;
    private javax.swing.JTextField txtLeírás;
    // End of variables declaration//GEN-END:variables

    private void listaFeltöltés() throws IOException {
        List<String> rajLista = new ArrayList<>(); 
        for (int i = 0; i < lista.size(); i++) {
            rajLista.add(lista.get(i).getStreamName()+", "+lista.get(i).getStreamLeírás());
        }
            jlKedvencek.setListData(rajLista.toArray());
    }

        
    public void Ment() throws IOException{
       String MentettLista =""; 
      
      FileWriter fileWriter = new FileWriter("kedvencek.csv");
       try{
        
          for (int i = 0; i < lista.size(); i++) {
              fileWriter.write(lista.get(i).getStreamName()+";"+lista.get(i).getStreamLeírás()+"\n");
              MentettLista += lista.get(i).getStreamName()+";"+lista.get(i).getStreamLeírás()+"\n";
          }
       System.out.println(lista.size());
       System.out.println(MentettLista);
   }
   catch (Exception e) {
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
    
    
    
    
    static <K,V extends Comparable<? super V>>
SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
    SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
        new Comparator<Map.Entry<K,V>>() {
            @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                int res = e1.getValue().compareTo(e2.getValue());
                return res != 0 ? res : 1;
            }
        }
    );
    sortedEntries.addAll(map.entrySet());
    return sortedEntries;
}
    
    public TreeMap<Integer, Kedvenc> Betölt() throws FileNotFoundException,
            IOException, ClassNotFoundException, ClassCastException {
        lista.clear();
        int x = 0;
    BufferedReader br = null;
      try{
          
      br = new BufferedReader(new InputStreamReader(new FileInputStream(fájl), "ISO-8859-1"));
      String sor = "";
      while ((sor = br.readLine()) != null) {
        String[] átmenetiTömb = sor.split(";");
        String StreamName = átmenetiTömb[0];
        String StreamLeírás = átmenetiTömb[1];
        
          lista.put(x,new Kedvenc(StreamName, StreamLeírás));
          x++;
      }
    
    
      }
    catch (EOFException e) {
            return lista;
        } finally {
            br.close();
        }
    return lista;
        
}

    private void KedvencekhezAd() {
        lista.put(lista.size(), new Kedvenc(Name.getText(), txtLeírás.getText()));
    }
}
