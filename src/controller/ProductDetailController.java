package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import constants.Role;
import database.Store;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Account;
import model.Product;
import route.ModifyProductRoute;

public class ProductDetailController implements Initializable {

  private Product product;
  private Account account;

  @FXML
  private ImageView cImage;

  @FXML
  private Label tDescription;

  @FXML
  private Label tName;

  @FXML
  private Text tPrice;

  @FXML
  private BorderPane cWrapImage;

  @FXML
  private Button btnModify;

  @FXML
  private Button btnDelete;

  public void setProduct(Product product) {
    this.product = product;
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        tName.setText(product.getName());
        tDescription.setText(product.getDescription());
        tPrice.setText(product.getPrice() + " $");

        Image image = new Image(new ByteArrayInputStream(product.getImage()));
        cImage.setImage(image);
      }
    });

    account = (Account) Store.get("account");
    if(account.getRole() == Role.Admin) {
      btnModify.setOnMouseClicked(new actionModify());
      btnDelete.setOnMouseClicked(new actionDelete());
    } else {
      btnModify.setVisible(false);
      btnDelete.setVisible(false);
    }
  }

  class actionModify implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent arg0) {
      try {
        new ModifyProductRoute(product);
      } catch(IOException e) {
        e.printStackTrace();
      }
    }
  }

  class actionDelete implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent arg0) {
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Delete product");
      alert.setHeaderText("Are you sure ?");

      Optional<ButtonType> option = alert.showAndWait();
      
      if(option.get() == ButtonType.OK) {
        product.delete();     
        ((DashboardController) Store.get("dashboardController")).showProduct();
        ((Stage) btnDelete.getScene().getWindow()).close();
      }

    }
  }
}
