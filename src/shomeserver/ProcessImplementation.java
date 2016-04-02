
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

 public User getUser(String name, String password) throws RemoteException {
     return server.getUser(name, password);
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
 public boolean writeFxml(String content) throws RemoteException {
     return server.writeFxml(content);
 }
 
 public boolean sendFile(String filename) throws RemoteException, IOException {
     return server.sendFile(filename);
 }
 
 public void startSendFile(String filename) throws RemoteException, IOException {
     server.startSendFile(filename);
 }
}