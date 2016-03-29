
package shomeserver;

import java.rmi.RemoteException;

/*
 * Etäolion toteutus
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
 
 public void lightSwitch(String lightName) throws RemoteException { 

     server.lightSwitch(lightName);

 }
 
 /*
 //Poistin catch-lohkon. n�in poikkeus nousee asiakkaalle asti
 public void start(String laitteenNimi, String asiakas) throws RemoteException {
 
     //Laitetaan testausta varten siiloihin vain 1000. Muuten kest�� kauhean kauan
	 server.start(laitteenNimi, asiakas, 1000);
   
 }
 
 //Poistin catch-lohkon. n�in poikkeus nousee asiakkaalle asti
 public void start(String laitteenNimi, String asiakas, int amount) throws RemoteException {

	     server.start(laitteenNimi, asiakas, amount);
	
 }
*/
 
 public ProcessState getState() throws RemoteException {
	 return server.getState();
 }
 
 public boolean login(String name) throws RemoteException{
	 return server.login(name);
 }

}