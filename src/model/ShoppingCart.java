package model;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
  private final double shipmentFee = 5;

  private Map<Product, Integer> items;
  private int amountItems;

  public ShoppingCart() {
    items = new HashMap<Product, Integer>();
  }

  public Map<Product, Integer> getItems() {
    return items;
  }

  public void setItems(Map<Product, Integer> items) {
    this.items = items;
  }

  public boolean addItem(Product product) {
    amountItems++;
    if(items.containsKey(product)) {
      items.put(product, items.get(product).intValue() + 1);
    } else {
      items.put(product, 1);
    }
    return true;
  }

  public boolean removeItem(Product product) {
    if(items.get(product) > 0){
      items.put(product, items.get(product) - 1);
      amountItems--;
    }
    return true;
  }

  public int getAmountItems() {
    return amountItems;
  }

  public int getAmountItem(Product product) {
    if(items.containsKey(product)) {
      return items.get(product);
    } else {
      return 0;
    }
  }

  public double getPrice() {
    var wrapper = new Object() { double price = 0; };
    items.forEach((product, amount) -> {
      wrapper.price += amount * product.getPrice();
    });

    return wrapper.price;
  }

  public double getShipmentFee(){
    double price = getPrice();
    if(price > 0) return shipmentFee;
    return 0;
  }

  public double getTotal() {
    double price = getPrice();
    double shipmentFee = getShipmentFee();
    double total = price + shipmentFee;

    return total;
  }
}
