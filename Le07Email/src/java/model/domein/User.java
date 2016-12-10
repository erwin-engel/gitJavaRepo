package model.domein;

/**
 * Klasse voor lid van email-lijst
 * @author Medewerker OUNL
 *
 */
public class User {
  private String firstName = "";
  private String lastName = "";
  private String emailAddress = "";

  public User() {
  }

  public User(String first, String last, String email) {
    firstName = first;
    lastName = last;
    emailAddress = email;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getFirstName() { 
    return firstName; 
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getLastName() { 
    return lastName; 
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getEmailAddress() { 
    return emailAddress; 
  }
  
  /**
   * Vergelijkt een object met deze gebruiker.
   * Twee gebruikers zijn gelijk als ze dezelfde voor-
   * en achternaam hebben
   * @param o  het object waarme deze gebruiker vergeleken wordt
   * @return true als o een instantie van User is die gelijk
   *         is aan deze gebruiker en anders false
   */
  public boolean equals(Object o) {
    if (o instanceof User) {
      User u = (User) o;
      return u.firstName.equals(firstName) && u.lastName.equals(lastName);
    } else {
      return false;
    }
  }
  
  /**
   * @return een String-representatie van deze gebruiker
   */
  public String toString() {
    return emailAddress+ "|"
    + firstName + "|"
    + lastName;
  }
}
