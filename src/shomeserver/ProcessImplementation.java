
package shomeserver;

import java.rmi.RemoteException;
import java.io.IOException;

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
 
 public ProcessState getState() throws RemoteException {
  return server.getState();
 }
 
 
 
 public boolean login(String name, String password) throws RemoteException{
  return server.login(name, password);
 }
 
 public boolean doesUserExist(String username) throws RemoteException {
     return server.doesUserExist(username);
 }

 public User getUser(String name, String password) throws RemoteException {
     return server.getUser(name, password);
 }
 
 public void createUser(String username, String password, String content) throws RemoteException {
     server.createUser(username, password, content);
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