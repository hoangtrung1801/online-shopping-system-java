package controller;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Order;
import model.OrderItem;
import model.Product;

public class AccountOrderDetailController implements Initializable {

  private Order order;

  @FXML
  private VBox cYourOrder;

  @FXML
  private Text tOrderId;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Map<Product, Integer> products = OrderItem.getProductsFromOrder(order);
        products.forEach((product, amount) -> {
          cYourOrder.getChildren().add(createProductCard(product, amount));
        });

        tOrderId.setText("#" + order.getId());
      }
    });
  }

  private AnchorPane createProductCard(Product product, int amount) {
    AnchorPane cProduct = new AnchorPane();
    cProduct.setStyle("-fx-background-color: #ecf0f1; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 1)");
    cProduct.setPadding(new Insets(5));

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
    tName.setWrapText(true);
    tName.setPrefWidth(270);
    tName.setMaxHeight(55);
    AnchorPane.setTopAnchor(tName, 5.0);
    AnchorPane.setLeftAnchor(tName, 120.0);

    // price
    Text tPrice = new Text(product.getPrice() + " $");
    Font fontPrice = Font.font("System", FontWeight.BOLD, 18.0);
    tPrice.setFont(fontPrice);
    AnchorPane.setTopAnchor(tPrice, 60.0);
    AnchorPane.setLeftAnchor(tPrice, 120.0);

    // amount
    HBox cAmount = new HBox();

    Label lAmount = new Label("Amount: " + amount);
    lAmount.setAlignment(Pos.CENTER);

    cAmount.getChildren().addAll(lAmount);

    AnchorPane.setTopAnchor(cAmount, 82.0);
    AnchorPane.setLeftAnchor(cAmount, 120.0);

    cProduct.getChildren().addAll(imageView, tName, tPrice, cAmount);
    return cProduct;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

}
