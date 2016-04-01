package shome;

import shomeserver.User;
import shomeserver.ProcessState;
import shomeserver.Process;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/*
 * TÃ¤mÃ¤ luokka toimii RMI:n edustajaoliona
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
     } catch (RemoteException e) {
        e.printStackTrace();
     }
     
     return false;
 }
 
 public boolean login(String name, String password) {
  try {
   return process.login(name, password);
  } catch (RemoteException e) {
   e.printStackTrace();
  }
  return false;
 }
 
 public User getUser(String name, String password) {
     try{
     return process.getUser(name, password);
     } catch (RemoteException e) {
        e.printStackTrace();
     }
     return null;
 }
 
 public boolean writeFxml(String content) {
     try {
         return process.writeFxml(content);
     } catch (RemoteException e) {
         e.printStackTrace();
     }
     return false;
 }
 
}
