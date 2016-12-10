package model.data;

import java.io.*;
import java.util.*;
import model.domein.User;
import model.domein.User;

/**
 * Deze klasse heeft methoden die alle gebruikers inleest 
 * uit een tekstbestand respectievelijk wegschrijft naar een
 * tekstbestand.
 * @author Open Universiteit Nederland
 *
 */
public class UserIO {

  /**
   * Leest gebruikers (voornaam, achternaam, email-adres) uit een 
   * tekstbestand en plaatst ze in een lijst. Als het tekstbestand 
   * niet bestaat,wordt een lege lijst teruggegeven.
   * @param filename  de naam van het tekstbestand
   * @return een lijst met gebruikers 
   * @throws IOException als het lezen mislukt.
   */
  public static ArrayList<User> readUsers(String filename) throws IOException {
    ArrayList<User> users = new ArrayList<User>();
    try {
      BufferedReader in = new BufferedReader(
          new FileReader(filename));
      String line = in.readLine();
      while (line != null) {
        StringTokenizer t = new StringTokenizer(line, "|");
        String emailAddress = t.nextToken();
        String firstName = t.nextToken();
        String lastName = t.nextToken();
        User user = new User(firstName, lastName, emailAddress);
        users.add(user);
        line = in.readLine();
      }
      in.close();
      return users;
    } catch (FileNotFoundException e) {
      return new ArrayList<User>();
    }
  }

  /**
   * Schrijft alle gebruikers in de gegeven lijst naar een
   * tekstbestand.
   * @param userList  een lijst gebruikers
   * @param filename  de naam van het tekstbestand
   * @throws IOException als het schrijven mislukt.
   */
  public static void writeUsers(ArrayList<User> userList, 
                                  String filename) throws IOException {
    File file = new File(filename);
    PrintWriter out = new PrintWriter(
        new FileWriter(file, false));
    for (User user: userList) {
      out.println(user);        
    }
    out.close();
  }
}
