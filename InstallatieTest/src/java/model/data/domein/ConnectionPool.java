package model.data;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Singletonklasse die verbinding maakt met de database en
 * verbindingen uit de 
 * @author Medewerker OUNL
 *
 */
public class ConnectionPool {
  // constante voor de JNDI databasenaam
  private static final String DBNAAM = "java:/comp/env/jdbc/ITest";
  
  // de enige instantie van deze klasse
  private static ConnectionPool pool = null;
  
  // de datasource
  private DataSource dataSource = null; 

  /**
   * De (private) constructor maakt verbinding met de database.
   */
  private ConnectionPool() {
    try {
      Context initCtx = new InitialContext() ;
      dataSource = (DataSource) initCtx.lookup(DBNAAM);
    }
    catch (NamingException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Geeft de enige instantie terug
   * @return  de enige instantie van ConnectionPool
   */
  public static synchronized ConnectionPool getInstance() {
    if (pool == null) {
      pool = new ConnectionPool();
    }
    return pool;
  }
  
  /**
   * Vraagt om een beschikbare connectie uit de pool die wordt
   * bijgehouden door de datasource
   * @return  een connectie met de datasource
   */
  public Connection getConnection() {
    try {
      return dataSource.getConnection();
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
  
  /**
   * Geeft een connectie terug aan de pool
   * @param c  de connectie die vrijgegeven kan worden
   */
  public void freeConnection(Connection c) {
    try {
      c.close();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }
    
}
