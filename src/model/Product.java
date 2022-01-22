package model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConn;

public class Product {
  private String id;
  private String name;
  private String description;
  private double price;
  private int availableItemCount;
  private String category;
  private byte[] image;

  public String getName() {
    return this.name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getPrice() {
    return this.price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getAvailableItemCount() {
    return this.availableItemCount;
  }

  public void setAvailableItemCount(int availableItemCount) {
    this.availableItemCount = availableItemCount;
  }

  public String getCategory() {
    return this.category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Product() {

  }

  public Product(String name, String description, double price, int availableItemCount, String category, byte[] image) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.availableItemCount = availableItemCount;
    this.category = category;
    this.image = image;
  }


  public static void save(String name, String description, double price, int availableItemCount, String category,
      InputStream image) {
    try {
      String query = "insert Product(name, description, price, availableItemCount, category, image) values (?, ?, ?, ?, ?, ?)";
      PreparedStatement stmt = DatabaseConn.conn.prepareStatement(query);
      stmt.setString(1, name);
      stmt.setString(2, description);
      stmt.setDouble(3, price);
      stmt.setInt(4, availableItemCount);
      stmt.setString(5, category);
      stmt.setBinaryStream(6, image);

      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void save() {
    try {
      String query = "insert Product(name, description, price, availableItemCount, category, image) values (?, ?, ?, ?, ?, ?)";
      PreparedStatement stmt = DatabaseConn.conn.prepareStatement(query);
      stmt.setString(1, name);
      stmt.setString(2, description);
      stmt.setDouble(3, price);
      stmt.setInt(4, availableItemCount);
      stmt.setString(5, category);
      stmt.setBinaryStream(6, new ByteArrayInputStream(image));

      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void update() {
    try {
      String query = "update Product set name=(?), description=(?), price=(?), availableItemCount=(?), category=(?), image=(?) where id=(?)";
      PreparedStatement stmt = DatabaseConn.conn.prepareStatement(query);
      stmt.setString(1, name);
      stmt.setString(2, description);
      stmt.setDouble(3, price);
      stmt.setInt(4, availableItemCount);
      stmt.setString(5, category);
      stmt.setBinaryStream(6, new ByteArrayInputStream(image));
      stmt.setString(7, id);
      stmt.executeUpdate();
    } catch(SQLException e ) {
      e.printStackTrace();
    }
  }

  public void delete() {
    try {
      String query = "delete from Product where id = " + id;
      Statement stmt = DatabaseConn.conn.createStatement();
      stmt.executeUpdate(query);
    } catch(SQLException e ){
      e.printStackTrace();
    }
  }

  public static List<Product> getProducts() throws IOException {
    List<Product> products = new ArrayList<>();

    try {
      String query = "select * from Product";
      Statement stmt = DatabaseConn.conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        Product product = new Product(rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5), rs.getString(6), rs.getBinaryStream(7).readAllBytes()); 
        product.setId(rs.getString(1));

        products.add(product);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return products;
  }
}
