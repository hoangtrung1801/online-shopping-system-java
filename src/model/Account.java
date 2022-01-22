package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import constants.AccountStatus;
import constants.Role;
import database.DatabaseConn;

public class Account {
  private String id;
  private String username;
  private String password;
  private AccountStatus status;
  private String name;
  private String shippingAddress;
  private String email;
  private String phone;
  private Role role;

  public String getUsername() {
    return this.username;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public AccountStatus getStatus() {
    return this.status;
  }

  public void setStatus(AccountStatus status) {
    if(this.status == status) return;
    this.status = status;
    updateStatus();
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getShippingAddress() {
    return this.shippingAddress;
  }

  public void setShippingAddress(String shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return this.phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Account(String username, String password, AccountStatus status, String name, String shippingAddress, String email, String phone, Role role) {
    this.username = username;
    this.password = password;
    this.status = status;
    this.name = name;
    this.shippingAddress = shippingAddress;
    this.email = email;
    this.phone = phone;
    this.role = role;
  }

  public Account() {}

  public void save() {
    try {
      String query = "insert Account(username, password, status, name, shippingAddress, email, phone, role) values (?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement stmt = DatabaseConn.conn.prepareStatement(query);
      stmt.setString(1, username);
      stmt.setString(2, password);
      stmt.setString(3, status.name());
      stmt.setString(4, name);
      stmt.setString(5, shippingAddress);
      stmt.setString(6, email);
      stmt.setString(7, phone);
      stmt.setString(8, role.name());

      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void updateStatus() {
    try {
      PreparedStatement stmt = DatabaseConn.conn.prepareStatement("update account set status=(?) where username=(?)");
      stmt.setString(1, status.name());
      stmt.setString(2, username);
      stmt.executeUpdate();
    } catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public static Account checkAccount(String username, String password) {
    try {
      String query = "select top 1 * from Account where username=(?)";
      PreparedStatement stmt = DatabaseConn.conn.prepareStatement(query);
      stmt.setString(1, username);
      ResultSet rs = stmt.executeQuery();
      if(!rs.isBeforeFirst()) {
        System.out.println("Username is not correct");
        return null;
      }
      rs.next();
      if(!rs.getString(2).equals(password)) {
        System.out.println("Password is not correct");
        return null;
      }

      Account account = new Account();
      account.setUsername(rs.getString(1));
      account.setPassword(rs.getString(2));
      account.setStatus(AccountStatus.valueOf(rs.getString(3)));
      account.setName(rs.getString(4));
      account.setShippingAddress(rs.getString(5));
      account.setEmail(rs.getString(6));
      account.setPhone(rs.getString(7));
      account.setRole(Role.valueOf(rs.getString(8)));
      account.setId(rs.getString(9));

      return account;
    } catch(SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static List<Account> getAccounts() {
    List<Account> accounts = new ArrayList<>();
    try {
      String query = "select * from account";
      PreparedStatement stmt = DatabaseConn.conn.prepareStatement(query);
      ResultSet rs = stmt.executeQuery();

      while(rs.next()) {
        accounts.add(new Account(rs.getString(1), rs.getString(2), AccountStatus.valueOf(rs.getString(3)), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), Role.valueOf(rs.getString(8))));
      }

    } catch(SQLException e) {
      e.printStackTrace();
    }
    return accounts;
  }
}
