package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import constants.AccountStatus;
import database.Store;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Account;
import route.DashboardRoute;
import route.RegisterRoute;

public class LoginController implements Initializable{
  @FXML
  private Button btnLogin;

  @FXML
  private Hyperlink btnRegister;

  @FXML
  private PasswordField txPassword;

  @FXML
  private TextField txUsername;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        btnLogin.setOnAction(new actionLogin());
        btnRegister.setOnMouseClicked(new actionRegister());
        txPassword.setOnKeyPressed(new actionLoginKeyPress());
      }
    });
  }

  private void login() {
    String username = txUsername.getText();
    String password = txPassword.getText();
    Account acc = Account.checkAccount(username, password);
    Store.set("account", acc);
    if(acc == null) {
        return;
    }
    if(acc.getStatus() == AccountStatus.Blocked) {
      System.out.println("You are blocked");
      return;
    }

    ((Stage) btnLogin.getScene().getWindow()).close();
    try {
      new DashboardRoute();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  class actionLogin implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent arg0) {
      login();
    }
  }

  class actionLoginKeyPress implements EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent e) {
      if(e.getCode() == KeyCode.ENTER) {
        login();
      }
    }
  }

  class actionRegister implements EventHandler<Event> {
    @Override
    public void handle(Event arg0) {
      try {
        new RegisterRoute();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}

