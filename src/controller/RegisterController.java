package controller;

import java.net.URL;
import java.util.ResourceBundle;

import constants.AccountStatus;
import constants.Role;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Account;

public class RegisterController implements Initializable{

  @FXML
  private Button btnRegister;

  @FXML
  private TextField txAddress;

  @FXML
  private TextField txEmail;

  @FXML
  private TextField txName;

  @FXML
  private PasswordField txPassword;

  @FXML
  private TextField txPhone;

  @FXML
  private PasswordField txRetypePassword;

  @FXML
  private TextField txUsername;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        btnRegister.setOnAction(new actionRegister());
      }
    });
  }

  class actionRegister implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent arg0) {
      String username = txUsername.getText();
      String password = txPassword.getText();
      String retypePassword = txRetypePassword.getText();
      String name = txName.getText();
      String address = txAddress.getText();
      String email = txEmail.getText();
      String phone = txPhone.getText();

      if(!password.equals(retypePassword)) {
        System.out.println("Password is not the same");
        return;
      }

      Account account = new Account(username, password, AccountStatus.Active, name, address, email, phone, Role.Customer);
      account.save();

      ((Stage) btnRegister.getScene().getWindow()).close();
    }
  }
}

