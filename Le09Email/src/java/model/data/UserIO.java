package model.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import model.domein.Administrator;
import model.domein.User;

/**
 * Singletonklasse die verbinding maakt met de database.
 * Er zijn methoden om
 * <ul>
 * <li>een gebruiker te zoeken</li>
 * <li>een gebruiker toe te voegen</li>
 * <li>een gebruiker te verwijderen</li>
 * <li>alle gebruikers op te halen</li>
 * <li>een administrator op te zoeken</li>
 * </ul>
 * @author Medewerker OUNL
 *
 */
public class UserIO {
  
  // Enige instantie. 
  private static UserIO instance = null;

  // constanten voor de gebruikte SQL-opdrachten
  private static final String sqlGetUser = 
    "SELECT * FROM user WHERE first_name = ? AND last_name = ?";
  private static final String sqlAddUser = 
    "INSERT INTO user (first_name, last_name, email_address) values (?,?,?)";
  private static final String sqlRemoveUser = 
    "DELETE FROM user WHERE first_name = ? AND last_name = ?";
  private static final String sqlUpdateEmail = 
    "UPDATE user SET email_address = ? WHERE first_name = ? AND last_name = ?";
  private static final String sqlGetAllUsers = 
    "SELECT * FROM user";
  private static final String sqlGetAdmin = 
    "SELECT * FROM admin WHERE username = ?";
  
  /**
   * Haalt de enige instantie op
   * @return  de enige instantie van UserIO
   */
  public static synchronized UserIO getInstance() {
    if (instance == null) {
      instance = new UserIO();
    }
    return instance;
  }

  /**
   * Private constructor
   */
  private UserIO() {}
  
  /**
   * Haalt de user met gegeven voor- en achternaam uit de
   * database
   * @param firstName  voornaam gezochte gebruiker
   * @param lastName  achternaam gezochte gebruiker
   * @return  gebruiker met deze voor- en achternaam, of null als
   *          die er niet is.
   * @throws EmailException
   */
  public User getUser(String firstName, String lastName) throws EmailException {
    ConnectionPool pool = ConnectionPool.getInstance();
    Connection con = pool.getConnection();
    PreparedStatement prepGetUser = null; 
    try {
      prepGetUser = con.prepareStatement(sqlGetUser);
      prepGetUser.setString(1, firstName);
      prepGetUser.setString(2, lastName);      
      ResultSet res = prepGetUser.executeQuery();
      if (res.next()) {
        User user = new User(res.getString(1), res.getString(2), res.getString(3));
        return user;
      }
      else {
        return null;
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      throw new EmailException(
          "Door een systeemfout bij het ophalen van een gebruiker" +
          "kan niet voldaan worden aan dit verzoek");
    }
    finally {
      closePreparedStatement(prepGetUser);
      pool.freeConnection(con);
    }
  }
  
  /**
   * Als de database nog geen gebruiker bevat met deze voor- en
   * achternaam, wordt die toegevoegd, anders wordt het e-mail-adres
   * gewijzigd.
   * @param user  de toe te voegen of te wizjigen gebruiker
   * @throws EmailException
   */
  public void addUser(User user) throws EmailException {    
    ConnectionPool pool = ConnectionPool.getInstance();
    Connection con = pool.getConnection();
    PreparedStatement prepAddUser = null; 
    PreparedStatement prepUpdateEmail = null;
    String firstName = user.getFirstName();
    String lastName = user.getLastName();
    String emailAddress = user.getEmailAddress();
    try {      
      User storedUser = getUser(firstName, lastName);
      if (storedUser == null) {
        prepAddUser = con.prepareStatement(sqlAddUser);
        prepAddUser.setString(1, firstName);
        prepAddUser.setString(2, lastName);
        prepAddUser.setString(3, emailAddress);
        prepAddUser.executeUpdate();
      } else {
        prepUpdateEmail = con.prepareStatement(sqlUpdateEmail);
        prepUpdateEmail.setString(1, emailAddress);
        prepUpdateEmail.setString(2, firstName);
        prepUpdateEmail.setString(3, lastName);
        prepUpdateEmail.executeUpdate();
      }      
    }
    catch (SQLException e) {
      e.printStackTrace();
      throw new EmailException(
          "Door een systeemfout bij het toevoegen van een gebruiker " +
          "kan niet voldaan worden aan dit verzoek");
    }
    finally {
      closePreparedStatement(prepAddUser);
      closePreparedStatement(prepUpdateEmail);
      pool.freeConnection(con);
    }
   }
  
  /**
   * Verwijdert een gebruiker me de gegeven namen uit de database
   * @param firstName voornaam van de te verwijderen gebruiker
   * @param lastName achternaam van de te verwijderen gebruiker
   * @return  true als er een gebruiker verwijderd is; 
   *          false als die er niet was.
   * @throws EmailException
   */
  public boolean removeUser(String firstName, String lastName) throws EmailException {
    ConnectionPool pool = ConnectionPool.getInstance();
    Connection con = pool.getConnection();
    PreparedStatement prepRemoveUser = null; 
    try {
      prepRemoveUser = con.prepareStatement(sqlRemoveUser);
      prepRemoveUser.setString(1, firstName);
      prepRemoveUser.setString(2, lastName);
      int aantalGewijzigd = prepRemoveUser.executeUpdate();    
      return (aantalGewijzigd == 1);
    }
    catch (SQLException e) {
      e.printStackTrace();
      throw new EmailException(
          "Door een systeemfout bij het verwijderen van een gebruiker " +
          "kan niet voldaan worden aan dit verzoek");
    }
    finally {
      closePreparedStatement(prepRemoveUser);
      pool.freeConnection(con);
    }    
  }
  
  /**
   * Haalt alle gebruiker op.
   * @return een lijst van alle gebruikers
   * @throws EmailException
   */
  public ArrayList<User> getUsers() throws EmailException {
    ConnectionPool pool = ConnectionPool.getInstance();
    Connection con = pool.getConnection();
    PreparedStatement prepGetAllUsers = null; 
    try {
      prepGetAllUsers = con.prepareStatement(sqlGetAllUsers);
      ResultSet res = prepGetAllUsers.executeQuery();
      ArrayList<User> userList = new ArrayList<User>();
      while (res.next()) {
        User user = new User(res.getString(1), res.getString(2), res.getString(3));
        userList.add(user);
      }
      return userList;
    }
    catch (SQLException e) {
      e.printStackTrace();
      throw new EmailException(
          "Door een systeemfout bij het ophalen van de gebruikers " +
          "kan niet voldaan worden aan dit verzoek");
    }
    finally {
      closePreparedStatement(prepGetAllUsers);
      pool.freeConnection(con);
    }
  }
  
  /**
   * Haalt de administrator op met de gegeven gebruikersnaam
   * @param userName  gebruikersnaam van gezochte gebruiker
   * @return  de Administrator instantie met de gegeven 
   *          gebruikersnaam of null als die er niet is
   * @throws EmailException
   */
  public Administrator getAdmin(String userName) throws EmailException {
    ConnectionPool pool = ConnectionPool.getInstance();
    Connection con = pool.getConnection();
    PreparedStatement prepGetAdmin = null; 
    try {
      prepGetAdmin = con.prepareStatement(sqlGetAdmin);
      prepGetAdmin.setString(1, userName);
      ResultSet res = prepGetAdmin.executeQuery();
      if (res.next()) {
        return new Administrator(userName, res.getString("password"));     
      }
      else {
        return null;
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      throw new EmailException(
          "Door een systeemfout bij het opzoeken van de beheersgegevens " +
          "kan niet voldaan worden aan dit verzoek");
    }
    finally {
      closePreparedStatement(prepGetAdmin);
      pool.freeConnection(con);
    }
  }
  
  /**
   * Sluit een prepared statement af.
   * @param ps
   * @throws EmailException
   */
  private void closePreparedStatement(PreparedStatement ps) throws EmailException {
    try {
      if (ps != null) {
        ps.close();
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      throw new EmailException(
          "Door een systeemfout bij het sluiten van een databaseopdracht " +
          "kan niet voldaan worden aan dit verzoek");
    }
  }
  
}
