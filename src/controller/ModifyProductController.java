package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import constants.Category;
import database.Store;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Product;

public class ModifyProductController implements Initializable{

  private Product product;

  @FXML
  private Button btnAdd;

  @FXML
  private TextField txAvailableItemCount;

  @FXML
  private ComboBox<String> txCategory;
  // private TextField txCategory;

  @FXML
  private TextField txDescription;

  @FXML
  private TextField txName;

  @FXML
  private TextField txPrice;

  @FXML
  private Button btnChooseImage;

  @FXML
  private Button btnDone;

  private InputStream image;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {


    List<String> categories = Category.getCategories();
    for(String category : categories) {
      txCategory.getItems().add(category);
    }
    btnDone.setOnMouseClicked(new actionDone());

    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        txName.setText(product.getName());
        txDescription.setText(product.getDescription());
        txCategory.getSelectionModel().select(product.getCategory());
        txPrice.setText(product.getPrice()+"");
        txAvailableItemCount.setText(product.getAvailableItemCount()+"");
      }
    });

  }

  // public void addProduct() {
  // name = txName.getText();
  // description = txDescription.getText();
  // price = Double.valueOf(txPrice.getText());
  // availableItemCount = Integer.valueOf(txAvailableItemCount.getText());
  // category = txCategory.getText();

  // Product.save(name, description, price, availableItemCount, category, image);
  // ((Stage) btnAdd.getScene().getWindow()).close();
  // ((DashboardController) Store.get("dashboardController")).showProduct();
  // }

  public void chooseImage() throws FileNotFoundException {
    FileChooser fileChooser = new FileChooser();
    File file = fileChooser.showOpenDialog(new Stage());
    btnChooseImage.setText("File: " + file.getPath());
    image = new FileInputStream(file);
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  class actionDone implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent arg0) {
      product.setName(txName.getText());
      product.setAvailableItemCount(Integer.valueOf(txAvailableItemCount.getText()));
      product.setCategory(txCategory.getSelectionModel().getSelectedItem());
      product.setDescription(txDescription.getText());
      product.setPrice(Double.valueOf(txPrice.getText()));
      if(image != null) {
        try {
          product.setImage(image.readAllBytes());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      product.update();

      ((Stage) txName.getScene().getWindow()).close();
      ((DashboardController) Store.get("dashboardController")).showProduct();
    }
  }
}

