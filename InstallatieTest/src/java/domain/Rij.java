package model.domein;

import java.io.Serializable;

public class Rij implements Serializable  {
  private String sleutel = null;
  private String waarde = null;
  
  public Rij(){}
  
  public Rij(String sleutel, String waarde){
    this.sleutel = sleutel;
    this.waarde = waarde;
  }
  
  public String getSleutel() {
    return sleutel;
  }
  
  public String getWaarde() {
    return waarde;
  }
  
  public void setWaarde(String waarde) {
    this.waarde = waarde;
  }
  
  // liever geen setSleutel!!
}
