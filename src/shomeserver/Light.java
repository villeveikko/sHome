package shomeserver;

import java.io.Serializable;

/**
 *
 */
public class Light extends Thread implements Serializable {
    
    private String identification;
    private boolean state;
    
    // Konstruktori
    public Light(String id) {
        this.identification = id;
        this.state = false;
    }
    
    /**
     * Getterit ja setterit
     */
    
    public String getIdentification() {
        return identification;
    }
    public boolean isState() {
        return state;
    }
    public void setState(boolean state) {
        this.state = state;
    }
    
    @Override
    public void run() {
        System.out.println("Light switched!");
    }
    
    
    
    
    
    
}
