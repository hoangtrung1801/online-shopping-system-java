package constants;

import java.util.List;

import model.Product;

public interface Search {
  public List<Product> searchProductsByName(String name);
  public List<Product> searchProductsByCategory(String category);
}
