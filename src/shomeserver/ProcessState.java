package shomeserver;

import java.io.Serializable;

/*
 * Kuvaa prosessin tilaa. 
 */

public class ProcessState implements Serializable {

 
 private static final long serialVersionUID = 1L;
 //lista laitteista tÃ¤nne!
        private Light light1;
 
 public ProcessState(Light light1) {
 
  //luodaan laitteet
                this.light1 = light1;
  
 
 }
 
 
  // kyselyt laitteista tÃ¤nne
 
        public boolean getLight1State() {
            return light1.isState();
        }

  
}
