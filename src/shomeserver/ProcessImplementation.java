
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
 
 public boolean lightSwitch(String lightName) throws RemoteException { 

     if (server.lightSwitch(lightName)) {
         return true;
     }
     return false;

 }
 
 /*
 //Poistin catch-lohkon. nï¿½in poikkeus nousee asiakkaalle asti
 public void start(String laitteenNimi, String asiakas) throws RemoteException {
 
     //Laitetaan testausta varten siiloihin vain 1000. Muuten kestï¿½ï¿½ kauhean kauan
  server.start(laitteenNimi, asiakas, 1000);
   
 }
 
 //Poistin catch-lohkon. nï¿½in poikkeus nousee asiakkaalle asti
 public void start(String laitteenNimi, String asiakas, int amount) throws RemoteException {

      server.start(laitteenNimi, asiakas, amount);
 
 }
*/
 
 public ProcessState getState() throws RemoteException {
  return server.getState();
 }
 
 public boolean login(String name, String password) throws RemoteException{
  return server.login(name, password);
 }

 public User getUser(String name, String password) throws RemoteException {
     return server.getUser(name, password);
 }
 
 public boolean getLightState(String light) throws RemoteException {
     return server.getLightState(light);
 }
 
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