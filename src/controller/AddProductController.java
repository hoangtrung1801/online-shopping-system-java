package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import constants.Category;
import database.Store;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Admin;
import model.Product;

public class AddProductController implements Initializable{

    private Admin adminAccount;

    @FXML
    private Button btnAdd;

    @FXML
    private TextField txAvailableItemCount;

    @FXML
    private TextField txDescription;

    @FXML
    private TextField txName;

    @FXML
    private TextField txPrice;

    @FXML 
    private Button btnChooseImage;

    @FXML
    private ComboBox<String> txCategory;

    private String name, description, category;
    private double price;
    private int availableItemCount;
    private InputStream image;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        adminAccount = (Admin) Store.get("account");

        List<String> categories = Category.getCategories();
        for(String category : categories) {
            txCategory.getItems().add(category);
        }
    }

    public void chooseImage() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        btnChooseImage.setText("File: " + file.getPath());
        image = new FileInputStream(file);
    }

    public void addProduct() {
        adminAccount.addProduct(txName.getText(), txDescription.getText(), Double.valueOf(txPrice.getText()), Integer.valueOf(txAvailableItemCount.getText()), txCategory.getValue(), image);

        ((Stage) btnAdd.getScene().getWindow()).close();
        ((DashboardController) Store.get("dashboardController")).showProduct();
    }

}

