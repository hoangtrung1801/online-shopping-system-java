package route;

import java.io.IOException;

import controller.ModifyProductController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Product;

public class ModifyProductRoute {
  public ModifyProductRoute(Product product) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ModifyProductView.fxml"));
    Parent root = fxmlLoader.load();

    ModifyProductController modifyProductController = fxmlLoader.getController();
    modifyProductController.setProduct(product);

    Scene scene = new Scene(root);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.show();
  }
}
