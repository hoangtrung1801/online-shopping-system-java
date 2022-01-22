package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConn {
  private static DatabaseConn databaseConn = null;

  private static final String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=onlineShopping";
  private static final String username = "hoangtrung1801";
  private static final String password = "842756";

  public static Connection conn;
  private static Statement stmt;

  private DatabaseConn() {
    createConnection();
  }

  public static DatabaseConn connect() {
    if(databaseConn == null) {
        databaseConn = new DatabaseConn();
    }
    System.out.println("connected database");
    return databaseConn;
  }

  public static int getIdNextInTable(String table) {
    int res = 0;
    try {
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select IDENT_CURRENT('" + table + "')");
      if(!rs.next()) {
        return 1;
      }
      res = rs.getInt(1);
    } catch(SQLException e) {
      e.printStackTrace();
    }
    return res + 1;
  }

  void createConnection() {
    try {
      conn = DriverManager.getConnection(url, username, password);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

}
