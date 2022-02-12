package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import constants.AccountStatus;
import constants.OrderStatus;
import constants.PaymentStatus;
import constants.Role;
import database.DatabaseConn;

public class Customer extends Account {
  public Customer(Account account) {
    super(account.getUsername(), account.getPassword(), account.getStatus(), account.getName(), account.getShippingAddress(), account.getEmail(), account.getPhone(), account.getRole(), account.getId());
  }

  public Customer() {

  }

  public boolean placeOrder(ShoppingCart shoppingCart) {
    var wrapper = new Object(){double amount = 0;};
    // caculate amount all products and save order item
    shoppingCart.getItems().forEach((product, quantity) -> {
      wrapper.amount += product.getPrice() * quantity;
    });

    wrapper.amount += 5;

    Order order = new Order();
    Payment payment = new Payment();

    payment.setId(DatabaseConn.getIdNextInTable("payment") + "");
    payment.setStatus(PaymentStatus.Completed);

    payment.setAmount(wrapper.amount);
    payment.save();

    order.setId(DatabaseConn.getIdNextInTable("orders") + "");
    order.setAccount((Account) this);
    order.setOrderDate(new Date(System.currentTimeMillis()));
    order.setPayment(payment);
    order.setStatus(OrderStatus.Pending);
    order.save();

    shoppingCart.getItems().forEach((product, quantity) -> {
      OrderItem.save(product, order, product.getPrice(), quantity);
    });

    return true;
  }


  public Customer(String username, String password, AccountStatus status, String name, String shippingAddress,
    String email, String phone, Role role) {
      super(username, password, status, name, shippingAddress, email, phone, role);
  }

  public static List<Customer> getCustomers() {
    List<Customer> members = new ArrayList<>();
    try {
      String query = "select * from account where role=(?)";
      PreparedStatement stmt = DatabaseConn.conn.prepareStatement(query);
      stmt.setString(1, Role.Customer.name());
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        members.add(new Customer(rs.getString(1), rs.getString(2), AccountStatus.valueOf(rs.getString(3)),
            rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), Role.valueOf(rs.getString(8))));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return members;
  }
}
