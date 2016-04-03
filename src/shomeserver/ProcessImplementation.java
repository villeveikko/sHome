
package shomeserver;

import java.rmi.RemoteException;
import java.io.IOException;
import java.util.ArrayList;

/*
 * EtÃ¤olion toteutus
 */
public class ProcessImplementation implements Process {

 /**
  * Versionumero
  */
 private static final long serialVersionUID = 1L;
 private ProcessServer server;

 public ProcessImplementation(ProcessServer server) {
  super();
  this.server = server;
 }
 

 
 
 
 public boolean login(String name, String password) throws RemoteException{
  return server.login(name, password);
 }
 
 public boolean doesUserExist(String username) throws RemoteException {
     return server.doesUserExist(username);
 }

 public User getUser(String name) throws RemoteException {
     return server.getUser(name);
 }
 public ArrayList<String> getUsers() throws RemoteException {
     return server.getUsers();
 }
 
 public void createUser(String username, String password, String content) throws RemoteException {
     server.createUser(username, password, content);
 } 
 public void deleteUser(User user) throws RemoteException, IOException {
     server.deleteUser(user);
 }
  public void changeUserPassword(User user, String password) throws RemoteException, IOException {
      server.changeUserPassword(user, password);
  }

 
 /**
  * LAITEMETODEJA
  */
  public boolean lightSwitch(String lightName) throws RemoteException { 
     return server.lightSwitch(lightName);
 }
 public boolean getLightState(String light) throws RemoteException {
     return server.getLightState(light);
 }
 
  public boolean doorLockSwitch(String door) throws RemoteException {
      return server.doorLockSwitch(door);
  }
 public boolean getDoorState(String door) throws RemoteException {
     return server.getDoorState(door);
 }
 
  public boolean tvSwitch(String tv) throws RemoteException {
      return server.tvSwitch(tv);
  }
  public boolean getTvState(String tv) throws RemoteException {
      return server.getTvState(tv);
  }
 
  public boolean stereoSwitch(String stereo) throws RemoteException {
      return server.stereoSwitch(stereo);
  }
  public boolean getStereoState(String stereo) throws RemoteException {
      return server.getStereoState(stereo);
  }
  
  public void setTemperatureValue(String apartment, double amount) throws RemoteException {
      server.setTemperatureValue(apartment, amount);
  }
  public double getTemperatureValue(String apartment) throws RemoteException {
      return server.getTemperatureValue(apartment);
  }
  
  public void setHumidityValue(String apartment, double amount) throws RemoteException {
      server.setHumidityValue(apartment, amount);
  }
  public double getHumidityValue(String apartment) throws RemoteException {
      return server.getHumidityValue(apartment);
  }
  
 /**
  * TIEDOSTOMETODEJA
  */
 public boolean writeFxml(String content, String username) throws RemoteException {
     return server.writeFxml(content, username);
 }
 
 public boolean sendFile(String filename) throws RemoteException, IOException {
     return server.sendFile(filename);
 }
 
 public void startSendFile(String filename) throws RemoteException, IOException {
     server.startSendFile(filename);
 }
}