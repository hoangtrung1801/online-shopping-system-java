package route;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RegisterRoute {
  public RegisterRoute() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/RegisterView.fxml"));
    Parent root = fxmlLoader.load();

    Scene scene = new Scene(root);
    scene.getStylesheets().add("css/common.css");
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.show();;
  }
}
