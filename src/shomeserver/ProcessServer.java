package shomeserver;


import java.util.ArrayList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Prosessin ydin
 */
public class ProcessServer   {
     
 private User admin;
 private ArrayList<User> users;
 private ProcessState state;
 
 private Light light1;
 
 public ProcessServer() {
  super();
  admin = new User("admin", "password");
  admin.setView("fxml/Apartments.fxml");
  users = new ArrayList<User>();
  users.add(admin);
  
  light1 = new Light("Makuuhuone_Valo_1");
  
  this.state = new ProcessState(light1);

  System.out.println("Server started");
 }
 
 
 public ProcessState getState(){
  return state;
 }
 
 public boolean login(String name, String password) {
     for (int i = 0; i < users.size(); i++) {
        if (users.get(i).getUsername().equals(name) && users.get(i).getPassword().equals(password)) {
            System.out.println(name + " logged in.");
            return true;
        } 
     }
  
     System.out.println("ERROR: Wrong username or password!");
     return false;
  }
 
 public User getUser(String name, String password) {

     for (int i = 0; i < users.size(); i++) {
        if (users.get(i).getUsername().equals(name) && users.get(i).getPassword().equals(password)) {
            return users.get(i);
        }
     }
    return users.get(0);
 }

 
 /*
  * 
  */
 public boolean lightSwitch(String lightName) {
     switch(lightName) {
         case "light1":
             if (light1.isState()) {
                 light1.setState(false);
                 return false;
             }
             else {
                 light1.setState(true);
                 return true;
             }
     }
     return false;
 }
 public boolean getLightState(String light) {
     switch(light) {
         case "light1":
             return light1.isState();
     }
     return false;
 }
 
 public boolean writeFxml(String content) {
     
    try {
    
    File file = new File("C:\\Users\\Käyttäjä\\Desktop\\sHome\\src\\shome\\fxml\\testi.fxml");

    if (!file.exists()) {
	file.createNewFile();
    }

    FileWriter fw = new FileWriter(file.getAbsoluteFile());
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(content);
    bw.close();

    System.out.println("Done");
    
    return true;
    
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    return false;
     
 }
 
 /*
  * Saatetaan tarvita
  */
 /*
 public void start(String laitteenNimi, String asiakas, int mï¿½ï¿½rï¿½) {
  Thread t = new Thread(){
   public void run(){
    startDevice(laitteenNimi, asiakas, mï¿½ï¿½rï¿½);
   }
  };
  t.start();
 }
 */
 
 
 
}