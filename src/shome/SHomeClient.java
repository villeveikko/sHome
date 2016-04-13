package shome;

import shomeserver.User;
import shomeserver.Process;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Tämä luokka toimii RMI:n edustajaoliona
 * @author Villeveikko
 */
public class SHomeClient extends UnicastRemoteObject {

 private static final long serialVersionUID = 1L;
 private Process process;
 private String name;
private String password;

 /**
  * Konstruktori, joka luo edustajaolion, SecurityManagerin ja hakee viittauksen etäolioon RMIregistrystä.
  * Tehtävän toteutuksen helpottamiseksi jätämme SecurityManagerin kuitenkin toteuttamatta.
  * @throws RemoteException
  */
 protected SHomeClient() throws RemoteException {
  try {
   //if (System.getSecurityManager() == null) System.setSecurityManager(new RMISecurityManager());
   
   Registry registry = LocateRegistry.getRegistry(null); //tässä null = localhost
   process = (Process) registry.lookup("Process");
  } catch (Exception e) {
   e.printStackTrace();
  }
 }

 /**
  * Palauttaa edustajaolioon liittyvÃ¤n asiakkaan kÃ¤yttÃ¤jÃ¤nimen
  * @return asiakkaan kÃ¤yttÃ¤jÃ¤nimi
  */
 public String getName() {
  return name;
 }

 /**
  * Asettaa asiakkaalle kÃ¤yttÃ¤jÃ¤nimen kirjautumisen yhteydessÃ¤
  * @param name asiakkaan kÃ¤yttÃ¤jÃ¤nimi
  */
 public void setName(String name) {
  this.name = name;
     System.out.println("Logged in as " + name);
 }
 
        /**
  * Palauttaa edustajaolioon liittyvÃ¤n asiakkaan salasanan
  * @return asiakkaan salasana
  */
 public String getPassword() {
  return password;
 }

 /**
  * Asettaa asiakkaalle salasanan kirjautumisen yhteydessï¿½
  * @param password asiakkaan salasana
  */
 public void setPassword(String password) {
  this.password = password;
     System.out.println("Password set to " + password);
 }
        
 /**
  * Lähettää etäkutsun palvelimelle valon sytyttämiseksi tai sammuttamiseksi.
  * @param light valon nimi
  */
 public boolean lightSwitch(String light){
  try {
   if (process.lightSwitch(light)) {
       return true;
   }
   else {
       return false;
   }
  } catch (Exception e) {
   System.out.println("Error with RMI-call");
   e.printStackTrace();
   return false;
  }
 }
 /**
  * Kysyy palvelimelta, onko valo päällä vai pois päältä.
  * @param light valon nimi
  * @return true = valo päällä ; false = valo pois päältä
  * Yhteyden epäonnistuessa palautetaan automaattisesti false.
  */
  public boolean getLightState(String light) {
     try{
     return process.getLightState(light);
     } catch (RemoteException e) {}
     return false;
 }
 
  /**
  * Lähettää etäkutsun palvelimelle oven avaamiseksi tai lukitsemiseksi.
  * @param door oven nimi
  */
  public boolean doorLockSwitch(String door) throws RemoteException {
      try {
          return process.doorLockSwitch(door);
      } catch (RemoteException e) {
        return false;     
      }
  }
  /**
  * Kysyy palvelimelta, onko ovi auki vai lukossa.
  * @param door oven nimi
  * @return true = ovi lukossa ; false = ovi auki
  * Yhteyden epäonnistuessa palautetaan automaattisesti false.
  */
   public boolean getDoorState(String door) throws RemoteException {
        try{
            return process.getDoorState(door);
        } catch (RemoteException e) {}
     
     return false;
   }

   /**
  * Lähettää etäkutsun palvelimelle TV:n avaamiseksi tai sulkemiseksi.
  * @param tv TV:n nimi
  */
   public boolean tvSwitch(String tv) throws RemoteException {
       try {
           return process.tvSwitch(tv);
       } catch (RemoteException e) {}
       return false;
   }
   /**
  * Kysyy palvelimelta, onko TV auki vai kiinni.
  * @param tv TV:n nimi
  * @return true = TV auki ; false = TV kiinni
  * Yhteyden epäonnistuessa palautetaan automaattisesti false.
  */
   public boolean getTvState(String tv) throws RemoteException {
       try {
           return process.getTvState(tv);
       } catch (RemoteException e) {}
       return false;
   }
  
   /**
  * Lähettää etäkutsun palvelimelle stereon avaamiseksi tai sulkemiseksi.
  * @param stereo stereon nimi
  */
   public boolean stereoSwitch(String stereo) throws RemoteException {
       try {
           return process.stereoSwitch(stereo);
       } catch (RemoteException e) {}
       return false;
   }
   /**
  * Kysyy palvelimelta, onko stereo auki vai kiinni.
  * @param stereo stereon nimi
  * @return true = stereo auki ; false = stereo kiinni
  * Yhteyden epäonnistuessa palautetaan automaattisesti false.
  */
   public boolean getStereoState(String stereo) throws RemoteException {
       try {
           return process.getStereoState(stereo);
       } catch (RemoteException e) {}
       return false;
   }
   
   /**
    * Määrittää palvelimelle uuden arvon (tyyppiä double) asunnon lämpötilaksi (celsius-asteina)
    * @param apartment asunnon nimi
    * @param amount uusi arvo (celsius-asteina)
    */
   public void setTemperatureValue(String apartment, double amount) {
       try {
           process.setTemperatureValue(apartment, amount);
       } catch (RemoteException e) {}
   }
   /**
    * Kysyy palvelimelta asunnon senhetkisen lämpötilan (celsius-asteina)
    * @param apartment asunnon nimi
    * @return asunnon lämpötila (double)
    * Yhteyden epäonnistuessa palautetaan automaattisesti lämpötilaksi 18.0 astetta.
    */
   public double getTemperatureValue(String apartment) {
       try {
           return process.getTemperatureValue(apartment);
       } catch (RemoteException e) {}
       return 18.0;
   }
   
   /**
    * Määrittää palvelimelle uuden arvon (tyyppiä double) asunnon kosteusprosentiksi
    * @param apartment asunnon nimi
    * @param amount uusi arvo
    */
   public void setHumidityValue(String apartment, double amount) {
       try {
           process.setHumidityValue(apartment, amount);
       } catch (RemoteException e) {}
   }
   /**
    * Kysyy palvelimelta asunnon senhetkisen kosteusprosentin 
    * @param apartment asunnon nimi
    * @return asunnon kosteusprosentti (double)
    * Yhteyden epäonnistuessa palautetaan automaattisesti kosteusprosentiksi 50.0.
    */
   public double getHumidityValue(String apartment) {
       try {
           return process.getHumidityValue(apartment);
       } catch (RemoteException e) {}
       return 50.0;
   }

 /**
  * Lähettää palvelimelle kirjautumispyynnön. Palvelin selvittää onko kirjautuvaa käyttäjää olemassa.
  * @param name syötetty käyttäjänimi
  * @param password syötetty salasana
  * @return true = käyttäjätunnukset löytyivät ; false = käyttäjätunnuksia ei löytynyt
  * Yhteyden epäonnistuessa palautetaan automaattisesti false.
  */ 
 public boolean login(String name, String password) {
  try {
   return process.login(name, password);
  } catch (RemoteException e) {  }
  return false;
 }
 /**
  * Kysyy palvelimelta, onko annetun nimistä käyttäjää olemassa.
  * @param username annettu käyttäjänimi
  * @return true = käyttäjäon olemassa ; false = käyttäjä ei ole olemassa
  * Yhteyden epäonnistuessa palautetaan automaattisesti false.
  */
  public boolean doesUserExist(String username) {
    try {
      return process.doesUserExist(username);
    } catch (RemoteException e) {    }
    return false;
 }
 
  /**
   * Pyydetään palvelimelta käyttäjä-luokan instanssi
   * @param name käyttäjänimi, jonka perusteella instanssi haetaan
   * @return instanssi tyyppiä User, jolta löytyy syötetty käyttäjänimi
   * HUOM: Käytä metodia AINOASTAAN silloin, kun olet varma että kyseinen käyttäjä on olemassa!
   */
 public User getUser(String name) {
     try{
       return process.getUser(name);
     } catch (RemoteException e) {
       return null;
     }
     
 }
 /**
  * Pyydetään palvelimelta ArrayList kaikista palvelimella olevista käyttäjänimistä
  * @return String-tyyppinen ArrayList
  */
 public ArrayList<String> getUsers() {
     try {
         return process.getUsers();
     } catch (RemoteException e) {return null;}
 }
 
 /**
  * Pyydetään palvelinta luomaan uusi käyttäjä.
  * @param username käyttäjänimi uudelle käyttäjälle 
  * @param password salasana uudelle käyttäjälle 
  * @param content näkymä FXML-tyylisesti kirjoitettuna uudelle käyttäjälle 
  */
 public void createUser(String username, String password, String content) {
     try{
        process.createUser(username, password, content);
     } catch (RemoteException e) {
        e.printStackTrace();
     }
 }
 /**
  * Pyytää palvelinta poistamaan olemassaolevan käyttäjän.
  * @param user poistettava käyttäjä User-luokan instanssina
  * @throws IOException 
  */
 public void deleteUser(User user) throws IOException {
     try{
        process.deleteUser(user);
     } catch (RemoteException e) {}
 }
 /**
  * Pyytää palvelinta vaihtamaan käyttäjän salasanan.
  * @param user käyttäjä User-luokan instanssina, jonka salasanaa ollaan vaihtamassa
  * @param password uusi salasana
  * @throws IOException 
  */
 public void changeUserPassword(User user, String password) throws IOException {
     try {
         process.changeUserPassword(user, password);
     } catch (RemoteException e) {}
 }
 
 /**
  * Lähettää palvelimelle pyynnön kirjoittaa FXML-tiedosto tietylle käyttäjälle.
  * @param content tulevan FXML-tiedoston sisältö
  * @param username käyttäjän käyttäjänimi, jolle tiedosto kirjoitetaan.
  * @return 
  */
 public boolean writeFxml(String content, String username) {
     try {
         return process.writeFxml(content, username);
     } catch (RemoteException e) {}
     return false;
 }
 
 /**
  * Lähettää palvelimelle pyynnön aloittaa tiedoston lähetyksen.
  * @param filename pyydettävän tiedoston nimi
  * @throws IOException 
  */
 public void startSendFile(String filename) throws IOException {
     try {
         process.startSendFile(filename);
     } catch (RemoteException e) {
         e.printStackTrace();
     }
 }
 
}
