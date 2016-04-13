
package shomeserver;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Etäolion rajapinta
 * @author Villeveikko
 */
public interface Process extends Remote{
 
 
 public boolean login(String name, String password) throws RemoteException;
 public boolean doesUserExist(String username) throws RemoteException;
 public User getUser(String name) throws RemoteException;
 public ArrayList<String> getUsers() throws RemoteException;
 public void createUser(String username, String password, String content) throws RemoteException;
 public void deleteUser(User user) throws RemoteException, IOException;
 public void changeUserPassword(User user, String password) throws RemoteException, IOException;
 public boolean writeFxml(String content, String username) throws RemoteException;
 public void startSendFile(String filename) throws RemoteException, IOException;
 public boolean lightSwitch(String lightName) throws RemoteException;  
public boolean getLightState(String light) throws RemoteException;
 public boolean doorLockSwitch(String door) throws RemoteException;
 public boolean getDoorState(String door) throws RemoteException;
 public boolean tvSwitch(String tv) throws RemoteException;
 public boolean getTvState(String tv) throws RemoteException;
 public boolean stereoSwitch(String stereo) throws RemoteException;
 public boolean getStereoState(String stereo) throws RemoteException;
 public void setTemperatureValue(String apartment, double amount) throws RemoteException;
 public double getTemperatureValue(String apartment) throws RemoteException;
 public void setHumidityValue(String apartment, double amount) throws RemoteException;
 public double getHumidityValue(String apartment) throws RemoteException;
}