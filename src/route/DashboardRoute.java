package route;

import java.io.IOException;

import controller.DashboardController;
import database.Store;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashboardRoute {
  public DashboardRoute() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DashboardView.fxml"));
    Parent root = fxmlLoader.load();
    Scene scene = new Scene(root);

    scene.getStylesheets().add("css/Dashboard.css");

    DashboardController dashboardController = fxmlLoader.<DashboardController>getController();
    Store.set("dashboardController", dashboardController);

    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }
}
