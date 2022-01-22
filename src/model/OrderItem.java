package model;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.DatabaseConn;

public class OrderItem {

  public static void save(Product product, Order order, double price, int quantity) {
    try {
      String query = "insert into order_item(productId, orderId, price, quantity) values ((?), (?), (?), (?))";
      PreparedStatement stmt = DatabaseConn.conn.prepareStatement(query);
      stmt.setString(1, product.getId());
      stmt.setString(2, order.getId());
      stmt.setDouble(3, price);
      stmt.setInt(4, quantity);
      stmt.executeUpdate();
      
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  
  public static Map<Product, Integer> getProductsFromOrder(Order order) {
    Map<Product, Integer> result = new HashMap<>();

    try {
      String query = "select * from order_item where orderId=(?)";
      PreparedStatement stmt = DatabaseConn.conn.prepareStatement(query);
      stmt.setString(1, order.getId());
      ResultSet rs = stmt.executeQuery();

      while(rs.next()) {
        String productId = rs.getString(2);
        Statement stmt1 = DatabaseConn.conn.createStatement();
        ResultSet rsProduct = stmt1.executeQuery("select * from product where id="+productId);
        if(rsProduct.next()) {
          Product product = new Product();
          product.setId(rsProduct.getString(1));
          product.setName(rsProduct.getString(2));
          product.setDescription(rsProduct.getString(3));
          product.setPrice(rsProduct.getDouble(4));
          product.setAvailableItemCount(rsProduct.getInt(5));
          product.setCategory(rsProduct.getString(6));
          try {
            product.setImage(rsProduct.getBinaryStream(7).readAllBytes());
          } catch (IOException e) {
            e.printStackTrace();
          }
          
          result.put(product, rs.getInt(5));
        }
      }
      
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return result;
  }
}
