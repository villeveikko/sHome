/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shomeserver;

/**
 *
 * @author Käyttäjä
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
