package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import constants.Search;

public class Catalog implements Search{
  private String name;
  private Map<String, List<Product>> productsByName = new HashMap<>();
  private Map<String, List<Product>> productsByCategory = new HashMap<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public List<Product> searchProductsByName(String name) {
    if(productsByName.containsKey(name)) {
      return productsByName.get(name);
    };

    try {
      List<Product> products = Product.getProducts();
      List<Product> productsFiltered = products.stream().filter(product -> {
        if (product.getName().toLowerCase().indexOf(name.toLowerCase(), 0) != -1) {
          return true;
        }
        return false;
      }).collect(Collectors.toList());
      productsByName.put(name, productsFiltered);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return productsByName.get(name);
  }

  @Override
  public List<Product> searchProductsByCategory(String category) {
    if(productsByCategory.containsKey(category)) {
      return productsByCategory.get(category);
    }

    try {
      List<Product> products = Product.getProducts();
      List<Product> productsFiltered = products.stream().filter(product -> {
        if (category.equals("All")) {
          return true;
        }
        if (product.getCategory().equals(category)) {
          return true;
        }
        return false;
      }).collect(Collectors.toList());
      productsByCategory.put(category, productsFiltered);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return productsByCategory.get(category);
  }
}
