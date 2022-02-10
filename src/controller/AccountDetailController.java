package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import database.Store;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.Account;
import model.Order;
import route.AccountOrderDetailRoute;

public class AccountDetailController implements Initializable{

  Account account;
  Order orderSelected;


  @FXML
  private TableView<Order> cYourOrder;

  @FXML
  private TableColumn<Order, String> createdDateCol;

  @FXML
  private TableColumn<Order, String> idCol;

  @FXML
  private TableColumn<Order, String> shipmentCol;

  @FXML
  private TableColumn<Order, String> statusCol;
  
  @FXML
  private TableColumn<Order, String> amountCol;

  @FXML
  private Text tAddress;

  @FXML
  private Text tEmail;

  @FXML
  private Text tName;

  @FXML
  private Text tPhone;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    account = (Account) Store.get("account");

    tName.setText(account.getName());
    tAddress.setText(account.getShippingAddress());
    tEmail.setText(account.getEmail());
    tPhone.setText(account.getPhone());

    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    createdDateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
    shipmentCol.setCellValueFactory(new PropertyValueFactory<>("status"));

    amountCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Order ,String>,ObservableValue<String>>() {
      @Override
      public ObservableValue<String> call(CellDataFeatures<Order, String> order) {
        ObservableStringValue result = new SimpleStringProperty(order.getValue().getPayment().getAmount() + " $");
        return result;
      }
    });
    statusCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Order, String>, ObservableValue<String>>() {
      @Override
      public ObservableValue<String> call(CellDataFeatures<Order, String> order) {
        ObservableStringValue result = new SimpleStringProperty(order.getValue().getPayment().getStatus().name());
        return result;
      }
    });

    showOrders();

    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        cYourOrder.setOnMouseClicked(new actionShowOrderDetail());
      }
    });
  }

  public void showOrders() {
    List<Order> orders = Order.getOrdersOfAccount(account);
    cYourOrder.setItems(FXCollections.observableList(orders));
  }

  class actionShowOrderDetail implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent e) {
      orderSelected = cYourOrder.getSelectionModel().getSelectedItem();
      if(e.getButton().equals(MouseButton.PRIMARY)) {
        if(e.getClickCount() == 2 ) {
          Order order = cYourOrder.getSelectionModel().getSelectedItem();
          try {
            new AccountOrderDetailRoute(order);
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        }
      }
    }
  }

}

