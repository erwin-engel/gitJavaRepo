package model.domein;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import model.data.UserIO;

/**
 * Klasse voor lijst gebruikers met methoden
 * om een gebruiker toe te voegen en te verwijderen.
 * Elke gebruiker (voornaam + achternaam) staat maar een
 * keer in de lijst.
 * @author Medewerker OUNL
 *
 */
public class UserList implements Iterable<User> {
  private ArrayList<User> userList = null;
  private String filename = null;
  
  /**
   * Constructor leest gebruikers uit tekstbestand
   * @param filename  de naam van het tekstbestand
   * @throws IOException  als het lezen mislukt
   */
  public UserList(String filename) throws IOException {
    this.filename = filename;
    userList = UserIO.readUsers(filename);
  }
  
  /**
   * @return de iterator van de ArrayList met gebruikers
   */
  public Iterator<User> iterator() {
    return userList.iterator();
  }
  
  /**
   * Voegt gebruiker toe. Als er al een gebruiker was met
   * dezelfde naam, dan wordt die eerst verwijderd.
   * @param firstName  voornaam van de toe te voegen gebruiker
   * @param lastName  achternaam van de toe te voegen gebruiker
   * @param emailAddress  email-adres van de toe te voegen gebruiker
   */
  public synchronized void addUser(
    String firstName, String lastName, String emailAddress) {
    removeUser(firstName, lastName);
    User user = new User(firstName, lastName, emailAddress);
    userList.add(user);
  }
  
  /**
   * Verwijdert gebruiker uit de lijst.
   * @param firstName  voornaam van de te verwijderen gebruiker
   * @param lastName  achternaam van de te verwijderen gebruiker
   * @return  true als de gebruiker gevonden en verwijderd is, anders false
   */
  public synchronized boolean removeUser(String firstName, String lastName) {
    User user = new User(firstName, lastName, "");
    for (int index = 0; index < userList.size(); index++) {
      User u = userList.get(index);
      if (u.equals(user)) {
        userList.remove(user);
        return true;
      }
    }
    return false;
  }
  
  /**
   * @return  het aantal gebruikers in de lijst
   */
  public int size() {
    return userList.size();
  }
  
  /**
   * Sluit de lijst af door alle namen weg te schrijven.
   * @throws IOException  als het schrijven mislukt
   */
  public void close() throws IOException {
    UserIO.writeUsers(userList, filename);
    userList = null;
  }
  
  /**
   * Voor testdoeleinden
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    String filename = "userList.txt";
    UserList lijst = new UserList(filename);
    lijst.removeUser("Mark", "Jansen");
    lijst.addUser("Marleen", "Sint", "marleen.sint@ou.nl");
    lijst.close();
  }
}
