package route;

import java.io.IOException;

import controller.ProductDetailController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Product;

public class ProductDetailRoute {
  public ProductDetailRoute(Product product) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ProductDetailView.fxml"));
    Parent root = fxmlLoader.load();

    ProductDetailController productDetailController = fxmlLoader.getController();
    productDetailController.setProduct(product);

    Scene scene = new Scene(root);
    Stage stage = new Stage();
    stage.setTitle(product.getName());
    stage.setScene(scene);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setResizable(false);
    stage.show();
  }
}
