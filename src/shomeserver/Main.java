package shomeserver;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {

    public static void main(String[] args) {
         //if (System.getSecurityManager() == null)  System.setSecurityManager(new SecurityManager());
           try {
               Process process = new ProcessImplementation(new ProcessServer());
               Process stub =
                   (Process) UnicastRemoteObject.exportObject(process, 0);
               Registry registry = LocateRegistry.createRegistry(1099);
               registry.rebind("Process", stub);
               System.out.println("Process bound");
           } catch (Exception e) {
               System.err.println("Process exception:");
               e.printStackTrace();
           }
       }


}