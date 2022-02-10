package route;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginRoute {
  public LoginRoute() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
    Scene scene = new Scene(root);
    scene.getStylesheets().add("css/common.css");

    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }
}
