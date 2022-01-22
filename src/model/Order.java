package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import constants.OrderStatus;
import database.DatabaseConn;

public class Order {
  private String id;
  private OrderStatus status;
  private Date orderDate;
  private Account account;
  private Payment payment;

  public Account getAccount() {
    return this.account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public Payment getPayment() {
    return this.payment;
  }

  public void setPayment(Payment payment) {
    this.payment = payment;
  }

  public OrderStatus getStatus() {
    return this.status;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public Date getOrderDate() {
    return this.orderDate;
  }

  public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
  }

  public Order() {

  }

  public Order(String id, OrderStatus status, Date orderDate) {
    this.id = id;
    this.status = status;
    this.orderDate = orderDate;
  }

  public void save() {
    try {
      String query = "insert into orders(accountId, paymentId, status, orderDate) values ((?), (?), (?), (?))";
      PreparedStatement stmt = DatabaseConn.conn.prepareStatement(query);

      stmt.setString(1, account.getId());
      stmt.setString(2, payment.getId());
      stmt.setString(3, status.name());
      stmt.setDate(4, orderDate);
      stmt.executeUpdate();

    } catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public void update() {
    try {
      String query = "update orders set accountId=(?), paymentId=(?), status=(?), orderDate=(?) where id=(?)";
      PreparedStatement stmt = DatabaseConn.conn.prepareStatement(query);
      stmt.setString(1, account.getId());
      stmt.setString(2, payment.getId());
      stmt.setString(3, status.name());
      stmt.setDate(4, orderDate);
      stmt.setString(5, id);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static List<Order> getOrdersOfAccount(Account account) {
    List<Order> result = new ArrayList<>();

    try {
      String query = "select * from orders where accountId=(?)";
      PreparedStatement stmt = DatabaseConn.conn.prepareStatement(query);
      stmt.setString(1, account.getId());
      ResultSet rs = stmt.executeQuery();

      while(rs.next()) {
        Order order = new Order(rs.getString(1), OrderStatus.valueOf(rs.getString(4)), rs.getDate(5));
        order.setAccount(account);
        order.setPayment(Payment.getPaymentFromOrderId(rs.getString(1)));

        result.add(order);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return result;
  }

}
