package controller;

import java.net.URL;
import java.util.ResourceBundle;

import constants.Category;
import database.Store;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Admin;

public class AddCategoryController implements Initializable{
  private Admin adminAccount;

  @FXML
  private TextField txName;

  @FXML
  private Button btnAdd;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    adminAccount = (Admin) Store.get("account");

    btnAdd.setOnMouseClicked(new actionAdd());
  }

  class actionAdd implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent arg0) {
      adminAccount.addCategory(txName.getText());
      ((Stage) btnAdd.getScene().getWindow()).close();
    }
  }
}
