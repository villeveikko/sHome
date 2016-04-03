
package shomeserver;

import java.io.Serializable;

/**
 *
 */
public class Tv extends Thread implements Serializable {
    
    private boolean state;
    
    // Konstruktori
    public Tv() {
        this.state = false; // Tässä kohtaa katsottaisiin tila itse laitteesta, jos sellainen olisi olemassa.
    }
    
    /**
     * Getterit ja setterit
     */
    
    public boolean isState() {
        return state;
    }
    public void setState(boolean state) {
        this.state = state;
    }
    
    @Override
    public void run() {
        System.out.println("TV state switched!");
    }
    
}