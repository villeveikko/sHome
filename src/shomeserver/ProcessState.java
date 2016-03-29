package shomeserver;

import java.io.Serializable;

/*
 * Kuvaa prosessin tilaa. 
 */

public class ProcessState implements Serializable {

	
	private static final long serialVersionUID = 1L;
	//lista laitteista tänne!
        private Lights light1;
	
	public ProcessState(Lights light1) {
	
		//luodaan laitteet
                this.light1 = light1;
		
	
	}
	
	
	 // kyselyt laitteista tänne
	
        public boolean getLight1State() {
            return light1.isState();
        }

		
}
