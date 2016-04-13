
package shomeserver;

/**
 * Luokka laitteelle, joka säätelee ja seuraa huoneen/asunnon kosteusprosenttia.
 * @author Villeveikko
 */
public class HumidityController {
    
    private double humidity;
    
    public HumidityController() {
        this.humidity = 50.0;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
    
}
