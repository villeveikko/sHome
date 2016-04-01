
package shomeserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * EtÃ¤olion rajapinta
 */
public interface Process extends Remote{
 
 
 public boolean lightSwitch(String lightName) throws RemoteException;  
 /*public void start(String laitteenNimi, String asiakas) throws RemoteException;
 public void start(String laitteenNimi, String asiakas, int amount) throws RemoteException;*/
 public ProcessState getState() throws RemoteException;
 public boolean login(String name, String password) throws RemoteException;
 public User getUser(String name, String password) throws RemoteException;
 public boolean getLightState(String light) throws RemoteException;
 public boolean writeFxml(String content) throws RemoteException;
}