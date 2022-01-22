package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import constants.AccountStatus;
import constants.Role;
import database.DatabaseConn;

public class Customer extends Account {
  public boolean placeOrder() {
    return false;
  }

  public Customer() {
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
