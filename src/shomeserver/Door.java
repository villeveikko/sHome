
package shomeserver;

import java.io.Serializable;

/**
 *
 * @author Käyttäjä
 */
public class Door {
     private String identification;
    private boolean state;
    
    // Konstruktori
    public Door(String id) {
        this.identification = id;
        this.state = false;
    }
    public Door() {
        this.state = false; // Tässä kohtaa katsottaisiin tila itse laitteesta, jos sellainen olisi olemassa.
    }
    
    /**
     * Getterit ja setterit
     */
    
   /* public String getIdentification() {
        return identification;
    }*/
    public boolean isState() {
        return state;
    }
    public void setState(boolean state) {
        this.state = state;
    }
    
    //@Override
    public void run() {
        System.out.println("Door lock position switched!!");
    }
    
}
