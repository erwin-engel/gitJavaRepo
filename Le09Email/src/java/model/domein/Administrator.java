package model.domein;

/**
 * Deze klasse respresenteert een persoon met beheersrechten
 * op de email lijst. Wachtwoorden moeten gecodeerd worden om
 * deze klass veilig te maken.
 */
public class Administrator {
  private String userName = null;
  private String password = null;
  
  public Administrator(String userName, String password){
    this.userName = userName;
    this.password = password;
  }
  
  public String getUserName() {
    return userName;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  /**
   * 
   * @param userName  de ingevoerde gebuikersnaam
   * @param password  het ingevoerde wachtwoord
   * @return  true als gebruikersnaam en wachtwoord
   *          correct zijn, anders false
   */
  public boolean controleerLogin(String userName, String password) {
    return userName.equals(this.userName) && password.equals(this.password);
  }
}
