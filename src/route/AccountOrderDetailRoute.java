package route;

import java.io.IOException;

import controller.AccountOrderDetailController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Order;

public class AccountOrderDetailRoute {

  public AccountOrderDetailRoute(Order order) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AccountOrderDetailView.fxml"));
    Parent root = fxmlLoader.load();

    AccountOrderDetailController accountOrderDetailController = fxmlLoader.getController();
    accountOrderDetailController.setOrder(order);

    Scene scene = new Scene(root);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.show();
  }
  
}
