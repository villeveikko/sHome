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

/*
 * Tämä luokka toimii RMI:n edustajaoliona
 */
public class SHomeClient extends UnicastRemoteObject {

 private static final long serialVersionUID = 1L;
 private Process process;
 private String name;
private String password;

 /**
  * Konstruktori, joka luo edustajaolion, SecurityManagerin ja hakee viittauksen etï¿½olioon RMIregistrystï¿½.
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
 
  public boolean doorLockSwitch(String door) throws RemoteException {
      try {
          return process.doorLockSwitch(door);
      } catch (RemoteException e) {
        return false;     
      }
  }
   public boolean getDoorState(String door) throws RemoteException {
        try{
            return process.getDoorState(door);
        } catch (RemoteException e) {}
     
     return false;
   }

   public boolean tvSwitch(String tv) throws RemoteException {
       try {
           return process.tvSwitch(tv);
       } catch (RemoteException e) {}
       return false;
   }
   public boolean getTvState(String tv) throws RemoteException {
       try {
           return process.getTvState(tv);
       } catch (RemoteException e) {}
       return false;
   }
  
   public boolean stereoSwitch(String stereo) throws RemoteException {
       try {
           return process.stereoSwitch(stereo);
       } catch (RemoteException e) {}
       return false;
   }
   public boolean getStereoState(String stereo) throws RemoteException {
       try {
           return process.getStereoState(stereo);
       } catch (RemoteException e) {}
       return false;
   }
   
   public void setTemperatureValue(String apartment, double amount) {
       try {
           process.setTemperatureValue(apartment, amount);
       } catch (RemoteException e) {}
   }
   public double getTemperatureValue(String apartment) {
       try {
           return process.getTemperatureValue(apartment);
       } catch (RemoteException e) {}
       return 18.0;
   }
   
   public void setHumidityValue(String apartment, double amount) {
       try {
           process.setHumidityValue(apartment, amount);
       } catch (RemoteException e) {}
   }
   public double getHumidityValue(String apartment) {
       try {
           return process.getHumidityValue(apartment);
       } catch (RemoteException e) {}
       return 50.0;
   }

  
 public boolean login(String name, String password) {
  try {
   return process.login(name, password);
  } catch (RemoteException e) {
   e.printStackTrace();
  }
  return false;
 }
  public boolean doesUserExist(String username) {
    try {
      return process.doesUserExist(username);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
    return false;
 }
 
 public User getUser(String name) {
     try{
     return process.getUser(name);
     } catch (RemoteException e) {
        e.printStackTrace();
     }
     return null;
 }
 public ArrayList<String> getUsers() {
     try {
         return process.getUsers();
     } catch (RemoteException e) {return null;}
 }
 
 public void createUser(String username, String password, String content) {
     try{
        process.createUser(username, password, content);
     } catch (RemoteException e) {
        e.printStackTrace();
     }
 }
 public void deleteUser(User user) throws IOException {
     try{
        process.deleteUser(user);
     } catch (RemoteException e) {
        e.printStackTrace();
     }
 }
 public void changeUserPassword(User user, String password) throws IOException {
     try {
         process.changeUserPassword(user, password);
     } catch (RemoteException e) {
        e.printStackTrace();
     }
 }
 
 
 
 
 public boolean writeFxml(String content, String username) {
     try {
         return process.writeFxml(content, username);
     } catch (RemoteException e) {
         e.printStackTrace();
     }
     return false;
 }
 
 public boolean sendFile(String filename) throws IOException {
    try{
       return process.sendFile(filename);  
    } catch (RemoteException e) {
     e.printStackTrace();
    } 
 return false;
 }
 
 public void startSendFile(String filename) throws IOException {
     try {
         process.startSendFile(filename);
     } catch (RemoteException e) {
         e.printStackTrace();
     }
 }
 
}
