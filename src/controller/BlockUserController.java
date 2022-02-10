package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import constants.AccountStatus;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Customer;

public class BlockUserController implements Initializable {

  Customer accountSelected;

  @FXML
  private TableView<Customer> cAccount;

  @FXML
  private TableColumn<Customer, String> usernameCol;

  @FXML
  private TableColumn<Customer, String> emailCol;

  @FXML
  private TableColumn<Customer, String> nameCol;

  @FXML
  private TableColumn<Customer, String> phoneCol;

  @FXML
  private TableColumn<Customer, String> shippingAddressCol;

  @FXML
  private TableColumn<Customer, String> statusCol;

  @FXML
  private Button btnBlock;

  @FXML
  private Button btnUnblock;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    List<Customer> customer = Customer.getCustomers();
    usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
    shippingAddressCol.setCellValueFactory(new PropertyValueFactory<>("shippingAddress"));
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
    statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

    cAccount.setItems(FXCollections.observableList(customer));

    cAccount.setOnMouseClicked(new actionSelectRow());
    btnBlock.setOnMouseClicked(new actionBlock());
    btnUnblock.setOnMouseClicked(new actionUnblock());
  }

  public void reloadTable() {
    List<Customer> customer = Customer.getCustomers();
    cAccount.setItems(FXCollections.observableList(customer));
  }

  class actionSelectRow implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent arg0) {
      accountSelected = cAccount.getSelectionModel().getSelectedItem();
    }
  }

  class actionBlock implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent arg0) {
      accountSelected.setStatus(AccountStatus.Blocked);
      reloadTable();
    }
  }

  class actionUnblock implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent arg0) {
      accountSelected.setStatus(AccountStatus.Active);
      reloadTable();
    }
  }
}
