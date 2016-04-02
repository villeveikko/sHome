package shomeserver;

import java.io.Serializable;

/*
 * Kuvaa prosessin tilaa. 
 */

public class ProcessState implements Serializable {

 
 private static final long serialVersionUID = 1L;
 //lista laitteista tÃ¤nne!
        private Light light1;
        private Door door1;
 
 public ProcessState(Light light1, Door door1) {
 
  //luodaan laitteet
                this.light1 = light1;
                this.door1 = door1;
  
 
 }
 
 
  // kyselyt laitteista tÃ¤nne
 
        public boolean getLight1State() {
            return light1.isState();
        }
        public boolean getDoor1State() {
            return door1.isState();
        }

  
}
