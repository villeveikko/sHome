package shomeserver;

import java.io.Serializable;

/**
 * Luokka valo-laitteille älykodissa.
 * @author Villeveikko
 */
public class Light extends Thread implements Serializable {
    
    private boolean state;
    
    // Konstruktori
     public Light() {
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
    
    /**
     * Mahdollinen run-metodi laitteen käynnistämiseksi, jos sellainen olisi olemassa.
     */
    @Override
    public void run() {
        System.out.println("Light switched!");
    }
    
    
    
    
    
    
}
