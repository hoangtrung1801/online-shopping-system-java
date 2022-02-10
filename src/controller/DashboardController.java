package controller;

import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.security.auth.callback.LanguageCallback;

import constants.Category;
import constants.Role;
import database.Store;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Account;
import model.Product;
import model.ShoppingCart;
import route.AccountDetailRoute;
import route.AddCategoryRoute;
import route.AddProductRoute;
import route.BlockUserRoute;
import route.CheckoutRoute;
import route.ProductDetailRoute;

public class DashboardController implements Initializable {

  private ShoppingCart shoppingCart = null;
  private Account account;

  @FXML
  private Button btnAddProduct;

  @FXML
  private Button btnAddCategory;

  @FXML
  private Button btnBlockUser;

  @FXML
  private HBox cAdminFunction;

  @FXML
  private AnchorPane cCart;

  @FXML
  private FlowPane cProducts;

  @FXML
  private ScrollPane cWrapProducts;

  @FXML
  private Label lLogo;

  @FXML
  private Text tAmountProduct;

  @FXML
  private Text tUsername;

  @FXML
  private TextField txFind;

  @FXML
  private ComboBox<String> txCategory;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

    shoppingCart = (ShoppingCart) Store.get("shoppingCart");
    account = (Account) Store.get("account");

    // create new shopping cart if have no
    if (shoppingCart == null) {
      shoppingCart = new ShoppingCart();
      Store.set("shoppingCart", shoppingCart);
    }

    // set visible = false if being customer
    if(account.getRole().equals(Role.Customer)) {
      cAdminFunction.setVisible(false);
    }

    showProduct();
    showAmountCart();
    showUsername();
    showCategory();

    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        btnAddProduct.setOnAction(new actionAddProduct());
        btnAddCategory.setOnMouseClicked(new actionAddCategory());
        btnBlockUser.setOnMouseClicked(new actionBlockUser());
        
        cCart.setOnMouseClicked(new actionOpenCart());
        txFind.setOnKeyPressed(new actionFindKeyPress());
        txCategory.getSelectionModel().selectedItemProperty().addListener(new actionFindWithCategory());
        tUsername.setOnMouseClicked(new actionShowAccountDetail());
      }

    });
  }

  public void showCategory() {
    List<String> categories = Category.getCategories();
    txCategory.getItems().add("All");
    for(String category : categories) {
      txCategory.getItems().add(category);
    }
  }

  public void showProduct() {
    cProducts.getChildren().clear();
    txCategory.getSelectionModel().select("All");
    // show products
    try {
      List<Product> products = Product.getProducts();
      for (Product product : products) {
        cProducts.getChildren().add(createProductCard(product));
      }
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  private void showAmountCart() {
    // show amount cart
    tAmountProduct.setText(shoppingCart.getAmountItems() + "");
  }

  private void showUsername() {
    tUsername.setText(account.getUsername());
  }

  private AnchorPane createProductCard(Product product) {
    AnchorPane cProduct = new AnchorPane();
    cProduct.setStyle("-fx-background-color: #ecf0f1; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 1)");
    // cProduct.setStyle("-fx-background-radius: 12px");
    cProduct.setPrefWidth(250);
    cProduct.setPrefHeight(375);

    // image
    Image image = new Image(new ByteArrayInputStream(product.getImage()));
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(250);
    imageView.setFitHeight(250);

    AnchorPane.setTopAnchor(imageView, 0.0);
    AnchorPane.setLeftAnchor(imageView, 0.0);

    // name
    Label lName = new Label(product.getName());
    Font nameFont = Font.font("System", FontWeight.NORMAL, 18.0);
    // lName.setPrefWidth(240);
    lName.setFont(nameFont);
    lName.setWrapText(true);
    lName.setMaxWidth(240);
    lName.setMaxHeight(60);
    // lName.setTextFill(Paint.valueOf("#7f8c8d"));

    AnchorPane.setTopAnchor(lName, 260.0);
    AnchorPane.setLeftAnchor(lName, 10.0);

    // price
    Label lPrice = new Label(product.getPrice() + "$");
    Font priceFont = Font.font("System", FontWeight.BOLD, 16.0);
    lPrice.setFont(priceFont);

    AnchorPane.setTopAnchor(lPrice, 315.0);
    AnchorPane.setLeftAnchor(lPrice, 10.0);

    // button add cart
    Button addCart = new Button("Add cart");
    // addCart.setStyle("  -fx-cursor: \"open_hand\";\n  -fx-text-fill: #fff;\n  -fx-background-color:  #3498db;\n  -fx-background-radius: 4px;");
    addCart.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        shoppingCart.addItem(product);
        tAmountProduct.setText(shoppingCart.getAmountItems() + "");
      }
    });

    AnchorPane.setTopAnchor(addCart, 340.0);
    AnchorPane.setLeftAnchor(addCart, 10.0);

    cProduct.setOnMouseClicked(new EventHandler<Event>() {
      @Override
      public void handle(Event arg0) {
        try {
          new ProductDetailRoute(product);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

    cProduct.getChildren().addAll(imageView, lPrice, addCart, lName);
    return cProduct;
  }

  class actionAddProduct implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent arg0) {
      try {
        new AddProductRoute();
      } catch(IOException e) {
        e.printStackTrace();;
      }
    }
  }

  class actionOpenCart implements EventHandler<Event> {
    @Override
    public void handle(Event arg0) {
      try {
        ((Stage) tAmountProduct.getScene().getWindow()).close();
        new CheckoutRoute();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  class actionFindKeyPress implements EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent e) {
      if(e.getCode() != KeyCode.ENTER) {
        return;
      }
      String target = txFind.getText();
      try {
        List<Product> products = Product.getProducts();
        List<Product> productsFiltered = products.stream().filter(product -> {
          if(product.getName().toLowerCase().indexOf(target.toLowerCase(), 0) != -1) {
            return true;
          }
          return false;
        }).collect(Collectors.toList());

        cProducts.getChildren().clear();
        for(Product product : productsFiltered) {
          cProducts.getChildren().add(createProductCard(product));
        }

      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }

  }

  class actionAddCategory implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent arg0) {
      try {
        new AddCategoryRoute();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  class actionBlockUser implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent arg0) {
      try {
        new BlockUserRoute();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  class actionFindWithCategory implements javafx.beans.value.ChangeListener<String> {
    @Override
    public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
      String target = txCategory.getSelectionModel().getSelectedItem();
      try {
        List<Product> products = Product.getProducts();
        List<Product> productsFiltered = products.stream().filter(product -> {
          if(target.equals("All")) {
            return true;
          }
          if(product.getCategory().equals(target)) {
            return true;
          }
          return false;
        }).collect(Collectors.toList());

        cProducts.getChildren().clear();
        for(Product product : productsFiltered) {
          cProducts.getChildren().add(createProductCard(product));
        }

      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
  }

  class actionShowAccountDetail implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent arg0) {
      try {
        new AccountDetailRoute();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
