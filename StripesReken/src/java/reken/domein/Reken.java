/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reken.domein;

/**
 *
 * @author erwin
 */
public class Reken {
    private double getal1 = 0;
    private double getal2 = 0;
    
    public double getSom() {
        return (getal1 + getal2);
    }
    
    public double getVerschil() {
        return (getal1 - getal2);
    }
    
    public void setGetal1(double getal1){
        this.getal1 = getal1;
    }
    
    public double getGetal1(){
        return getal1;
    }
    
    public void setGetal2(double getal2){
        this.getal2 = getal2;
    }
    
    public double getGetal2(){
        return getal2;
    } 
}
