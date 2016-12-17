package model.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.domein.Rij;

public class DBIntermediair {
  // enige instantie van DBIntermediair
  private static DBIntermediair instance = null;

  // geparametriseerde sql-queries
  private static final String SQL_GET_DATA = "SELECT * FROM testtabel";

  /**
   * Haalt de enige instantie op
   * @return  de enige instantie van DBIntermediair
   */
  public static DBIntermediair getInstance() {
    if (instance == null) {
      instance = new DBIntermediair();
    }
    return instance;
  }

  /**
   * Private constructor
   */
  private DBIntermediair() {}

  /**
   * Haalt de testdata op.
   * @return alle rijen in DBTest als beans
   */
  public ArrayList<Rij> getData() {
    ConnectionPool pool = ConnectionPool.getInstance();
    Connection con = pool.getConnection();
    PreparedStatement prepGetData = null; 
    try {
      prepGetData = con.prepareStatement(SQL_GET_DATA);
      ResultSet res = prepGetData.executeQuery();
      ArrayList<Rij> data = new ArrayList<Rij>();
      while (res.next()) {
        Rij rij = new Rij(res.getString(1), res.getString(2));
        data.add(rij);
      }
      return data;
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    finally {
      closePreparedStatement(prepGetData);
      pool.freeConnection(con);
    }
  }

  /**
   * Sluit een prepared statement af.
   * @param ps
   */
  private void closePreparedStatement(PreparedStatement ps) {
    try {
      if (ps != null) {
        ps.close();
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

}

