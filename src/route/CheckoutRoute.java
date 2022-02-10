package route;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CheckoutRoute {
  public CheckoutRoute() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/CheckoutView.fxml"));
    Parent root = (Parent)fxmlLoader.load();

    Scene scene = new Scene(root);
    scene.getStylesheets().add("css/Checkout.css");

    Stage stage = new Stage();
    stage.setResizable(false);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(scene);
    stage.show();
  }
}
