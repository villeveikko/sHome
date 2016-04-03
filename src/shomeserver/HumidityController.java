/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shomeserver;

/**
 * 
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
