package shomeserver;


import java.util.ArrayList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Prosessin ydin. Sisältää tiedot kaikista käyttäjistä ja älykodin laitteista.
 * @author Villeveikko
 */
public class ProcessServer   {
     
  public final static int SOCKET_PORT = 13267;  

 private User admin;
 private ArrayList<User> users;
  
 private Light light1;
 private Light light2;
 private Light light3;
 private Light light4;
 private Light light5;
 private Light light6;
 private Light light7;
 private Light light8;
 private Light light9;
 private Door door1;
 private Door door2;
 private Door door3;
 private Tv tv1;
 private Tv tv2;
 private Tv tv3;
 private Stereo stereo1;
 private Stereo stereo2;
 private Stereo stereo3;
 private TemperatureController temp1;
 private TemperatureController temp2;
 private HumidityController humi1;
 private HumidityController humi2;
 
  final static String FILE_NAME = "shomeserver\\UserList.txt";
  final static Charset ENCODING = StandardCharsets.UTF_8;
 
  /**
   * Konstruktori alustaa serverin kaikkine laitteineen.
   */
 public ProcessServer() {
  super();

  users = new ArrayList<User>();
  interpretUserList();
  
  light1 = new Light();
  light2 = new Light();
  light3 = new Light();
  light4 = new Light();
  light5 = new Light();
  light6 = new Light();
  light7 = new Light();
  light8 = new Light();
  light9 = new Light();
  door1 = new Door();
  door2 = new Door();
  door3 = new Door();
  tv1 = new Tv();
  tv2 = new Tv();
  tv3 = new Tv();
  stereo1 = new Stereo();
  stereo2 = new Stereo();
  stereo3 = new Stereo();
  temp1 = new TemperatureController();
  temp2 = new TemperatureController();
  humi1 = new HumidityController();
  humi2 = new HumidityController();
  
  System.out.println("Server started");
 }
 
 /**
  * Tarkistaa users-ArrayList:stä, onko annettua käyttäjänimi-salasanayhdistelmää olemassa.
  * @param name saatu käyttäjänimi
  * @param password saatu salasana
  * @return true = käyttäjä olemassa ; false = käyttäjä ei ole olemassa
  */
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
 /**
  * Tarkistaa users-ArrayList:stä, onko annettua käyttäjänimeä olemassa.
  * @param username saatu käyttäjänimi
  * @return true = käyttäjänimi olemassa ; false = käyttäjänimi ei ole olemassa
  */
  public boolean doesUserExist(String username) {
     for (int i = 0; i < users.size(); i++) {
        if (users.get(i).getUsername().equals(username)) {
            System.out.println(username + " does exist on the server!");
            return true;
        } 
     }
  
     System.out.println(username + " doesn't exist on the server!");
     return false;
  }
 
  /**
   * Hakee User-luokan instanssin users-ArrayList:stä annetun käyttäjänimen perusteella.
   * @param name saatu käyttäjänimi
   * @return löydetty User-luokan instanssi
   */
 public User getUser(String name) {

     for (int i = 0; i < users.size(); i++) {
        if (users.get(i).getUsername().equals(name)) {
            return users.get(i);
        }
     }
    return users.get(0);
 }
 /**
  * Tekee uuden String-pohjaisen ArrayListin kaikkien palvelimella olevien käyttäjien käyttäjänimistä.
  * @return luotu ArrayList
  */
 public ArrayList<String> getUsers() {
     ArrayList<String> lista = new ArrayList();
     for (int i = 0; i < users.size(); i++) {
         lista.add(users.get(i).getUsername());
     }
     return lista;
 }
 
 /**
  * Luo palvelimelle uuden käyttäjän. Tiedot uudesta käyttäjästä kirjoitetaan UserList.txt-tiedostoon.
  * Käyttäjälle luodaan myös oma FXML-näkymä.
  * @param username uudelle käyttäjälle asetettava käyttäjänimi
  * @param password uudelle käyttäjälle asetettava salasana
  * @param content uudelle käyttäjälle asetettava näkymä String-muodossa
  */
 public void createUser(String username, String password, String content)  {
    
     try{
User asd = new User(username, password);
     asd.setView(username + ".fxml");
     writeFxml(content, username);
     users.add(asd);
     updateUserList(asd);
 } catch (IOException e) {}
 }
 /**
  * Poistaa annetun käyttäjän palvelimelta.
  * @param user saatu User-instanssin käyttäjä
  */
 public void deleteUser(User user) {
     removeUserList(user);
     users.remove(user);
     interpretUserList();
 }
 /**
  * Vaihtaa olemassaolevan käyttäjän salasanan.
  * Metodi oikeastaan luo käyttäjän uudelleen päivitetyillä tiedoilla, ja sitten poistaa vanhentuneen version.
  * @param user saatu User-instanssin käyttäjä, jonka salasanaa muutetaan
  * @param password uusi salasana
  */
 public void changeUserPassword(User user, String password) {
     try {
         String username = user.getUsername();
         String view = user.getView();
         
         interpretUserList();
         
         removeUserList(user);
         User u = new User(username, password, view);
         users.add(u);
         updateUserList(u);
         interpretUserList();
     } catch (IOException e) {}
     
 }
 
 
 /**
  * Pistää valon päälle tai pois päältä!
  * @param lightName valon nimi
  * @return true = valo on nyt päällä ; false = valo on nyt pois päältä
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
             case "light2":
             if (light2.isState()) {
                 light2.setState(false);
                 return false;
             }
             else {
                 light2.setState(true);
                 return true;
             }
             case "light3":
             if (light3.isState()) {
                 light3.setState(false);
                 return false;
             }
             else {
                 light3.setState(true);
                 return true;
             }
             case "light4":
             if (light4.isState()) {
                 light4.setState(false);
                 return false;
             }
             else {
                 light4.setState(true);
                 return true;
             }
             case "light5":
             if (light5.isState()) {
                 light5.setState(false);
                 return false;
             }
             else {
                 light5.setState(true);
                 return true;
             }
             case "light6":
             if (light6.isState()) {
                 light6.setState(false);
                 return false;
             }
             else {
                 light6.setState(true);
                 return true;
             }
             case "light7":
             if (light7.isState()) {
                 light7.setState(false);
                 return false;
             }
             else {
                 light7.setState(true);
                 return true;
             }
             case "light8":
             if (light8.isState()) {
                 light8.setState(false);
                 return false;
             }
             else {
                 light8.setState(true);
                 return true;
             }
             case "light9":
             if (light9.isState()) {
                 light9.setState(false);
                 return false;
             }
             else {
                 light9.setState(true);
                 return true;
             }
     }
     return false;
 }
 /**
  * Palauttaa tiedon, onko valo päällä vai pois päältä.
  * @param light valon nimi
  * @return true = valo on päällä ; false = valo on pois päältä
  */
 public boolean getLightState(String light) {
     switch(light) {
         case "light1": return light1.isState();
         case "light2": return light2.isState();
         case "light3": return light3.isState();
         case "light4": return light4.isState();
         case "light5": return light5.isState();
         case "light6": return light6.isState();
         case "light7": return light7.isState();
         case "light8": return light8.isState();
         case "light9": return light9.isState(); 
     }
     return false;
 }
 
 /**
  * Pistää oven lukkoon tai avaa oven lukosta!
  * @param door oven nimi
  * @return true = ovi on nyt lukossa ; false = ovi on nyt auki
  */
 public boolean doorLockSwitch(String door) {
     switch(door) {
         case "door1":
             if (door1.isState()) {
                 door1.setState(false);
                 return false;
             }
             else {
                 door1.setState(true);
                 return true;
             }
             case "door2":
             if (door2.isState()) {
                 door2.setState(false);
                 return false;
             }
             else {
                 door2.setState(true);
                 return true;
             }
             case "door3":
             if (door3.isState()) {
                 door3.setState(false);
                 return false;
             }
             else {
                 door3.setState(true);
                 return true;
             }
     }
     return false;
 }
 /**
  * Palauttaa tiedon, onko ovi lukossa vai auki.
  * @param door oven nimi
  * @return true = ovi on lukossa ; false = ovi on auki
  */
 public boolean getDoorState(String door) {
     switch(door) {
         case "door1": return door1.isState();
         case "door2": return door2.isState();
         case "door3": return door3.isState();
     }
     return false;
 }
 
 /**
  * Pistää TV:n joko auki tai kiinni!
  * @param tv TV:n nimi
  * @return true = TV on nyt auki ; false = TV on nyt kiinni
  */
 public boolean tvSwitch(String tv) {
     switch(tv) {
         case "tv1":
             if (tv1.isState()) {
                 tv1.setState(false);
                 return false;
             }
             else {
                 tv1.setState(true);
                 return true;
             }
             case "tv2":
             if (tv2.isState()) {
                 tv2.setState(false);
                 return false;
             }
             else {
                 tv2.setState(true);
                 return true;
             }
             case "tv3":
             if (tv3.isState()) {
                 tv3.setState(false);
                 return false;
             }
             else {
                 tv3.setState(true);
                 return true;
             }
     }
     return false;
 }
 /**
  * Palauttaa tiedon, onko TV auki vai kiinni.
  * @param tv TV:n nimi
  * @return true = TV on auki ; false = TV on kiinni
  */
 public boolean getTvState(String tv) {
     switch(tv) {
         case "tv1": return tv1.isState();
         case "tv2": return tv2.isState();
         case "tv3": return tv3.isState();
     }
     return false;
 }
 
 /**
  * Pistää stereon joko auki tai kiinni!
  * @param stereo TV:n nimi
  * @return true = stereo on nyt auki ; false = stereo on nyt kiinni
  */
 public boolean stereoSwitch(String stereo) {
     switch(stereo) {
         case "stereo1":
             if (stereo1.isState()) {
                 stereo1.setState(false);
                 return false;
             }
             else {
                 stereo1.setState(true);
                 return true;
             }
             case "stereo2":
             if (stereo2.isState()) {
                 stereo2.setState(false);
                 return false;
             }
             else {
                 stereo2.setState(true);
                 return true;
             }
             case "stereo3":
             if (stereo3.isState()) {
                 stereo3.setState(false);
                 return false;
             }
             else {
                 stereo3.setState(true);
                 return true;
             }
     }
     return false;
 }
 /**
  * Palauttaa tiedon, onko stereo auki vai kiinni.
  * @param stereo stereon nimi
  * @return true = stereo on auki ; false = stereo on kiinni
  */
 public boolean getStereoState(String stereo) {
     switch(stereo) {
         case "stereo1": return stereo1.isState();
         case "stereo2": return stereo2.isState();
         case "stereo3": return stereo3.isState();
     }
     return false;
 }
 
 public void setTemperatureValue(String apartment, double amount) {
     switch (apartment) {
         case "home": temp1.setTemperature(amount);
         case "cottage": temp2.setTemperature(amount);
     }
 }
 public double getTemperatureValue(String apartment) {
     switch (apartment) {
         case "home": return temp1.getTemperature();
         case "cottage": return temp2.getTemperature();
     }
     return 18.0;
 }
 
 public void setHumidityValue(String apartment, double amount) {
     switch (apartment) {
         case "home": humi1.setHumidity(amount);
         case "cottage": humi2.setHumidity(amount);
     }
 }
 public double getHumidityValue(String apartment) {
     switch (apartment) {
         case "home": return humi1.getHumidity();
         case "cottage": return humi2.getHumidity();
     }
     return 50.0;
 }
 
 
 
 /**
  * Kirjoittaa FXML-tyyppisen tiedoston palvelimelle, ja nimeää sen käyttäjänimen perusteella. 
  * @param content sisältö, joka kirjoitetaan tiedostoon
  * @param username käyttäjänimi, jonka mukaan tiedosto nimetään
  * @return true = kirjoitus onnistui ; false = kirjoitus epäonnistui
  */
 public boolean writeFxml(String content, String username) {
     
    try {
    
    File file = new File("shomeserver\\fxml\\" + username + ".fxml");

    if (!file.exists()) {
	file.createNewFile();
    }

    FileWriter fw = new FileWriter(file.getAbsoluteFile());
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(content);
    bw.close();

    System.out.println("New view for the user " + username + " done.");
    
    } catch (IOException e) {}
    
    try {
    
    File file = new File("shome\fxml\\" + username + ".fxml");

    if (!file.exists()) {
	file.createNewFile();
    }

    FileWriter fw = new FileWriter(file.getAbsoluteFile());
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(content);
    bw.close();

    System.out.println("New view for the user " + username + " done.");
    
    return true;
    
    } catch (IOException e) {}
    
    return false;
     
 }
 
 /**
  * Lukee UserList.txt-tiedoston ja palauttaa sen jokaisen rivin List-oliossa.
  * @return
  * @throws IOException 
  */
 public List<String> readUserList() throws IOException {
     Path path = Paths.get(FILE_NAME);
     return Files.readAllLines(path, ENCODING);
 }
 
 /**
  * Päivittää UserList.txt-tiedoston lisämällä siihen uuden käyttäjän. 
  * Käyttäjät kirjoitetaan <käyttäjänimi salasana fxmlTiedostoNimi> -muodossa
  * @param user tiedostoon lisättävä käyttäjä
  * @throws IOException 
  */
 public void updateUserList(User user) throws IOException {
     List<String> aLines = readUserList();
     aLines.add("<" + user.getUsername() + " " + user.getPassword() + " " + user.getView() + ">");
     
     Path path = Paths.get(FILE_NAME);
     Files.write(path, aLines, ENCODING);
 }
 
 /**
  * Lukee UserList.txt-tiedoston, ja lisää sieltä löytyvät käyttäjät users-ArrayListiin.
  */
 public void interpretUserList()  {
     users.clear();
     try { 
     for (int i = 0; i < readUserList().size(); i++) {
         String temp = readUserList().get(i);
         System.out.println(temp);
         
         String username = temp.substring(1, temp.indexOf(" "));
         temp = temp.substring(temp.indexOf(" ") + 1);
         
         String password = temp.substring(0, temp.indexOf(" "));
         temp = temp.substring(temp.indexOf(" ") + 1);
         
         String view = temp.substring(0, temp.indexOf(">"));
         
         User user = new User(username, password, view);
         users.add(user);
         
     }
     } catch (IOException e) {
       System.out.println("Jokin väärin tiedostopolussa!"); 
     }
 }
 /**
  * Poistaa annetun käyttäjän UserList.txt-tiedostosta.
  * @param user poistettava käyttäjä
  */
 public void removeUserList(User user) {
     try {
     List<String> aLines = readUserList(); 
     int index = 1;
     for (int i = 0; i < readUserList().size(); i++) {
         String temp = readUserList().get(i);
         if (temp.contains(user.getUsername()) && temp.contains(user.getPassword())) {
             index = i;
         }
     }
     aLines.remove(index);
     Path path = Paths.get(FILE_NAME);
     Files.write(path, aLines, ENCODING);
     }
      catch (IOException e) {
       System.out.println("Jokin väärin tiedostopolussa!"); 
     }
 }
 
 /**
  * Käynnistää uuden säikeen sendFile-metodille.
  * @param filename lähetettävän tiedoston nimi
  * @throws IOException 
  */
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
 
 /**
  * Yrittää lähettää tiedoston asiakassovellukselle.
  * @param filename pyydetyn tiedoston nimi
  * @return true = lähetys onnistui ; false = lähetys epäonnistui
  * @throws IOException 
  */
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
                File myFile = new File("shomeserver\\fxml\\" + filename);
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
 
}