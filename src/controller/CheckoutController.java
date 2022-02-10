package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import constants.OrderStatus;
import constants.PaymentStatus;
import database.DatabaseConn;
import database.Store;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Account;
import model.Order;
import model.OrderItem;
import model.Payment;
import model.Product;
import model.ShoppingCart;
import route.DashboardRoute;

public class CheckoutController implements Initializable {

  private ShoppingCart shoppingCart;
  private Account account;

  double amount;

  @FXML
  private VBox cProducts;

  @FXML
  private Text tPrice;

  @FXML
  private Text tShipmentFee;

  @FXML
  private Text tTotal;

  @FXML
  private Button btnCheckout;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    shoppingCart = (ShoppingCart) Store.get("shoppingCart");
    account = (Account) Store.get("account");

    updateShoppingCart();
    updateCheckout();;

    btnCheckout.setOnMouseClicked(new actionCheckout());


    Platform.runLater(() -> {
      // close window
      ((Stage)cProducts.getScene().getWindow()).setOnCloseRequest(new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent arg0) {
          try {
            new DashboardRoute();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      });
    });
  }

  private void updateShoppingCart() {
    // update shopping cart
    shoppingCart.getItems().forEach((product, amount) -> {
      cProducts.getChildren().add(createProductCard(product, amount));
    });
  }

  private AnchorPane createProductCard(Product product, int amount) {
    AnchorPane cProduct = new AnchorPane();
    cProduct.setStyle("-fx-background-color: #ecf0f1; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 1)");
    cProduct.setPadding(new Insets(0, 0, 10, 0));

    // image
    Image image = new Image(new ByteArrayInputStream(product.getImage()));
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(100);
    imageView.setFitHeight(100);
    AnchorPane.setLeftAnchor(imageView, 0.0);
    AnchorPane.setTopAnchor(imageView, 0.0);

    // name
    Label tName = new Label(product.getName());
    Font fontName = Font.font("System", FontWeight.NORMAL, 18.0);
    tName.setFont(fontName);
    tName.setMaxWidth(240.0);
    tName.setMaxHeight(60.0);
    tName.setWrapText(true);
    AnchorPane.setTopAnchor(tName, 10.0);
    AnchorPane.setLeftAnchor(tName, 120.0);

    // price
    Text tPrice = new Text(product.getPrice() + " $");
    Font fontPrice = Font.font("System", FontWeight.BOLD, 16.0);
    tPrice.setFont(fontPrice);
    AnchorPane.setTopAnchor(tPrice, 70.0);
    AnchorPane.setLeftAnchor(tPrice, 120.0);

    // amount
    HBox cAmount = new HBox();

    Button bDesc = new Button("-");
    bDesc.setPrefWidth(30.0);
    bDesc.setPrefHeight(30.0);

    Button bInc = new Button("+");
    bInc.setPrefWidth(30.0);
    bInc.setPrefHeight(30.0);
    bDesc.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        shoppingCart.removeItem(product);
      }
    });

    Label lAmount = new Label(amount + "");
    lAmount.setPrefWidth(30.0);
    lAmount.setPrefHeight(30.0);
    lAmount.setAlignment(Pos.CENTER);

    cAmount.getChildren().addAll(bDesc, lAmount, bInc);

    AnchorPane.setTopAnchor(cAmount, 30.0);
    AnchorPane.setLeftAnchor(cAmount, 400.0);

    bDesc.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        shoppingCart.removeItem(product);
        lAmount.setText(shoppingCart.getAmountItem(product) + "");
        updateCheckout();
      }
    });

    bInc.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        shoppingCart.addItem(product);
        lAmount.setText(shoppingCart.getAmountItem(product) + "");
        updateCheckout();
      }
    });

    cProduct.getChildren().addAll(imageView, tName, tPrice, cAmount);
    return cProduct;
  }

  public void setShoppingCart(ShoppingCart shoppingCart) {
    this.shoppingCart = shoppingCart;
  }

  public void updateCheckout() {
    double price = shoppingCart.getPrice();
    double shipmentFee = 5;
    double total = price + shipmentFee;

    tPrice.setText(price + " $");
    tShipmentFee.setText(shipmentFee + " $");
    tTotal.setText(total + " $");
  }

  class actionCheckout implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent arg0) {
      Order order = new Order();
      Payment payment = new Payment();

      payment.setId(DatabaseConn.getIdNextInTable("payment") + "");
      // payment.setOrder(order);
      payment.setStatus(PaymentStatus.Completed);

      // caculate amount all products and save order item
      shoppingCart.getItems().forEach((product, quantity) -> {
        amount += product.getPrice() * quantity;
      });
      // add shipping fee
      amount += 5;
      payment.setAmount(amount);
      payment.save();

      order.setId(DatabaseConn.getIdNextInTable("orders") + "");
      order.setAccount(account);
      order.setOrderDate(new Date(System.currentTimeMillis()));
      order.setPayment(payment);
      order.setStatus(OrderStatus.Pending);
      order.save();

      shoppingCart.getItems().forEach((product, quantity) -> {
        OrderItem.save(product, order, product.getPrice(), quantity);
      });


      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Notification");
      alert.setHeaderText("Checkout success");

      Optional<ButtonType> option = alert.showAndWait();
      if(option.get() == ButtonType.OK) {
        ((Stage) btnCheckout.getScene().getWindow()).close();
      }
    }
  }
}
