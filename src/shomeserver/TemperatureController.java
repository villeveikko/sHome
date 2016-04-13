
package shomeserver;

/**
 * Luokka laitteelle, joka säätelee ja seuraa huoneen/asunnon lämpötilaa.
 * @author Villeveikko
 */
public class TemperatureController {
    
    private double temperature;
    
    public TemperatureController() {
        this.temperature = 18.0;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    
}
