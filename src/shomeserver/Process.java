
package shomeserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * Etäolion rajapinta
 */
public interface Process extends Remote{
	
	
 public void lightSwitch(String lightName) throws RemoteException;  
 /*public void start(String laitteenNimi, String asiakas) throws RemoteException;
 public void start(String laitteenNimi, String asiakas, int amount) throws RemoteException;*/
 public ProcessState getState() throws RemoteException;
 public boolean login(String name) throws RemoteException;
}