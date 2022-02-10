package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import constants.PaymentStatus;
import database.DatabaseConn;

public class Payment {
  private String id;
  // private Order order;
  private double amount;
  private PaymentStatus status;

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  // public Order getOrder() {
  //   return this.order;
  // }

  // public void setOrder(Order order) {
  //   this.order = order;
  // }

  public double getAmount() {
    return this.amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public PaymentStatus getStatus() {
    return this.status;
  }

  public void setStatus(PaymentStatus status) {
    this.status = status;
  }

  public void save() {
    try {
      String query = "insert into payment(status, amount) values ((?), (?))";
      PreparedStatement stmt = DatabaseConn.conn.prepareStatement(query);
      stmt.setString(1, status.name());
      stmt.setDouble(2, amount);
      stmt.executeUpdate();
      
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void update() {
    try {
      String query = "update payment set status=(?), amount=(?) where id=(?)";
      PreparedStatement stmt = DatabaseConn.conn.prepareStatement(query);
      stmt.setString(1, status.name());
      stmt.setDouble(2, amount);
      stmt.setString(3, id);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static Payment getPaymentFromOrderId(String paymentId) {
    Payment result = new Payment();

    try {
      String query = "select * from payment where id = (?)";
      PreparedStatement stmt = DatabaseConn.conn.prepareStatement(query);
      stmt.setString(1, paymentId);
      ResultSet rs = stmt.executeQuery();
      if(!rs.next()) {
        return null;
      }
      result.setId(paymentId);
      result.setStatus(PaymentStatus.valueOf(rs.getString(2)));
      result.setAmount(rs.getDouble(3));
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return result;
  }

}
