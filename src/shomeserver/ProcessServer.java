package shomeserver;


import java.util.ArrayList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/*
 * Prosessin ydin
 */
public class ProcessServer   {
     
  public final static int SOCKET_PORT = 13267;  // you may change this
 //  public final static String FILE_TO_SEND = "fxml/testi.fxml";  // you may change this
    
    
 private User admin;
 private ArrayList<User> users;
 private ProcessState state;
 private Light light1;
 
 public ProcessServer() {
  super();
  admin = new User("admin", "password");
  admin.setView("Apartments.fxml");
  users = new ArrayList<User>();
  users.add(admin);
  
  light1 = new Light("Makuuhuone_Valo_1");
  
  this.state = new ProcessState(light1);

  System.out.println("Server started");
 }
 
 
 public ProcessState getState(){
  return state;
 }
 
 public boolean login(String name, String password) {
     for (int i = 0; i < users.size(); i++) {
        if (users.get(i).getUsername().equals(name) && users.get(i).getPassword().equals(password)) {
            System.out.println(name + " logged in.");
            return true;
        } 
     }
  
     System.out.println("ERROR: Wrong username or password!");
     return false;
  }
 
 public User getUser(String name, String password) {

     for (int i = 0; i < users.size(); i++) {
        if (users.get(i).getUsername().equals(name) && users.get(i).getPassword().equals(password)) {
            return users.get(i);
        }
     }
    return users.get(0);
 }

 
 /*
  * 
  */
 public boolean lightSwitch(String lightName) {
     switch(lightName) {
         case "light1":
             if (light1.isState()) {
                 light1.setState(false);
                 return false;
             }
             else {
                 light1.setState(true);
                 return true;
             }
     }
     return false;
 }
 public boolean getLightState(String light) {
     switch(light) {
         case "light1":
             return light1.isState();
     }
     return false;
 }
 
 public boolean writeFxml(String content) {
     
    try {
    
    File file = new File("C:\\Users\\Käyttäjä\\Desktop\\sHome\\src\\shome\\fxml\\testi.fxml");

    if (!file.exists()) {
	file.createNewFile();
    }

    FileWriter fw = new FileWriter(file.getAbsoluteFile());
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(content);
    bw.close();

    System.out.println("Done");
    
    return true;
    
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    return false;
     
 }
 
 public void startSendFile(String filename) throws IOException {
  Thread t = new Thread(){
   public void run(){
       try {
    sendFile(filename);
       } catch (IOException e) {
           
       }
   }
  };
    t.start();
 }
 
 public boolean sendFile(String filename) throws IOException {
     System.out.println("Saatiin pyyntö lähettää tiedosto " + filename);
     
     while (true) {
            ServerSocket welcomeSocket = null;
            Socket connectionSocket = null;
            BufferedOutputStream outToClient = null;

            try {
                welcomeSocket = new ServerSocket(3248);
                connectionSocket = welcomeSocket.accept();
                outToClient = new BufferedOutputStream(connectionSocket.getOutputStream());
            } catch (IOException ex) {
                // Do exception handling
            }

            if (outToClient != null) {
                File myFile = new File("src\\shomeserver\\fxml\\" + filename);
                byte[] mybytearray = new byte[(int) myFile.length()];

                FileInputStream fis = null;

                try {
                    fis = new FileInputStream(myFile);
                } catch (FileNotFoundException ex) {
                    System.out.println("Tiedostovirhe!");
                    ex.printStackTrace();
                    return false;
                }
                BufferedInputStream bis = new BufferedInputStream(fis);

                try {
                    bis.read(mybytearray, 0, mybytearray.length);
                    outToClient.write(mybytearray, 0, mybytearray.length);
                    
                    System.out.println("Bytes to send: " + Arrays.toString( mybytearray )); // Varmistus/vertailu
                    
                    outToClient.flush();
                    outToClient.close();
                    connectionSocket.close();

                    // File sent, exit the main method
                    System.out.println("Tiedoston lähetys onnistui!");
                    return true;
                } catch (IOException ex) {
                    // Do exception handling
                    return false;
                }
            }
        } 
 }
 
 /*
  * Saatetaan tarvita
  */
 /*
 public void start(String laitteenNimi, String asiakas, int mï¿½ï¿½rï¿½) {
  Thread t = new Thread(){
   public void run(){
    startDevice(laitteenNimi, asiakas, mï¿½ï¿½rï¿½);
   }
  };
  t.start();
 }
 */
 
 
 
}