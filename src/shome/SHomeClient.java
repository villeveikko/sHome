package shome;

import shomeserver.ProcessState;
import shomeserver.Process;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/*
 * Tämä luokka toimii RMI:n edustajaoliona
 */
public class SHomeClient extends UnicastRemoteObject {

	private static final long serialVersionUID = 1L;
	private Process process;
	private String name;

	/**
	 * Konstruktori, joka luo edustajaolion, SecurityManagerin ja hakee viittauksen et�olioon RMIregistryst�.
	 * @throws RemoteException
	 */
	protected SHomeClient() throws RemoteException {
		try {
			//if (System.getSecurityManager() == null) System.setSecurityManager(new RMISecurityManager());
			
			Registry registry = LocateRegistry.getRegistry(null); //t�ss� null = localhost
			process = (Process) registry.lookup("Process");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Palauttaa edustajaolioon liittyvän asiakkaan nimen
	 * @return asiakkaan nimi
	 */
	public String getName() {
		return name;
	}

	/**
	 * Asettaa asiakkaalle nimen kirjautumisen yhteydess�
	 * @param name asiakkaan nimi
	 */
	public void setName(String name) {
		this.name = name;
    	System.out.println("Logged in as " + name);
	}
	
	/**
	 * Lähettää etäkutsun palvelimelle valon sytyttämiseksi tai sammuttamiseksi.
	 * @param o laitteen tyyppi
	 * @param s laitteen nimi
	 */
	public void lightSwitch(String s){
		try {
			process.lightSwitch(s);
		} catch (Exception e) {
			System.out.println("Error with RMI-call");
			e.printStackTrace();
		}
	}
	
	/**
	 * Lähettää palvelimelle kyselyn laitteen tilasta
	 * @return prosessin tila
	 * @throws RemoteException
	 */
        
        
	public ProcessState getState() {
		try {
                        return process.getState();
		} catch (RemoteException e) {
                        System.out.println("Error with RMI-call");
                        e.printStackTrace();
		}
		return null;
	}
	
	public boolean login(String name) {
		try {
			return process.login(name);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
