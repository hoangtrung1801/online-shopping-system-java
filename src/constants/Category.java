package constants;

import java.lang.Thread.State;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConn;

public class Category { 
  public static void addCategory(String category) {
    try {
      // check category if available
      PreparedStatement stmt = DatabaseConn.conn.prepareStatement("select * from category where name=(?)");
      stmt.setString(1, category);
      ResultSet rs = stmt.executeQuery();

      if(rs.next()){
        System.out.println("Category is already available");
        return;
      }

      // insert category
      PreparedStatement stmt1 = DatabaseConn.conn.prepareStatement("insert into category(name) values ((?))");
      stmt1.setString(1, category);
      stmt1.execute();
    } catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public static List<String> getCategories() {
    List<String> categories = new ArrayList<>();

    try {
      Statement stmt = DatabaseConn.conn.createStatement();
      ResultSet rs = stmt.executeQuery("select * from category");

      while(rs.next()) {
        categories.add(rs.getString(2));
      }

    } catch(SQLException e ){
      e.printStackTrace();
    }

    return categories;
  }
}
