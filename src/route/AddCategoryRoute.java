package route;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddCategoryRoute {
  public AddCategoryRoute() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AddCategoryView.fxml"));
    Parent root = fxmlLoader.load();

    Scene scene = new Scene(root);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.show();
  }
}
