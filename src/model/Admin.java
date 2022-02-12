package model;

import java.io.InputStream;

import constants.AccountStatus;
import constants.Category;

public class Admin extends Account{
  public Admin(Account account) {
    super(account.getUsername(), account.getPassword(), account.getStatus(), account.getName(), account.getShippingAddress(), account.getEmail(), account.getPhone(), account.getRole(), account.getId());
  }

  public boolean addProduct(String name, String description, double price, int availableItemCount, String category, InputStream image) {
    Product.save(name, description, price, availableItemCount, category, image);
    return true;
  }

  public boolean addCategory(String category) {
    Category.addCategory(category);
    return true;
  }

  public boolean blockUser(Customer customer) {
    customer.setStatus(AccountStatus.Blocked);
    return true;
  }

  public boolean unblockUser(Customer customer){
    customer.setStatus(AccountStatus.Active);
    return true;
  }

}
