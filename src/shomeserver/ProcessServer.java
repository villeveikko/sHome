package shomeserver;

import java.util.ArrayList;

/*
 * Prosessin ydin
 */
public class ProcessServer {

 private ArrayList<String> users;
 private ProcessState state;
 
 private Lights light1;
 
 public ProcessServer() {
  super();
  users = new ArrayList<String>();
  
  light1 = new Lights("Makuuhuone_Valo_1");
  
  this.state = new ProcessState(light1);

  System.out.println("Server started");
 }
 
 
 public ProcessState getState(){
  return state;
 }
 
 public boolean login(String name) {
  users.add(name); //Kaiken nimiset k�ytt�j�t tervetulleita
  System.out.println(name + " logged in");
  return true;
  }

 
 /*
  * 
  */
 public void lightSwitch(String lightName) {
     switch(lightName) {
         case "light1":
             if (light1.isState()) {
                 light1.setState(false);
             }
             else {
                 light1.setState(true);
             }
     }
 }
 
 
 /*
  * Saatetaan tarvita
  */
 /*
 public void start(String laitteenNimi, String asiakas, int m��r�) {
  Thread t = new Thread(){
   public void run(){
    startDevice(laitteenNimi, asiakas, m��r�);
   }
  };
  t.start();
 }
 */
 
 
 
}