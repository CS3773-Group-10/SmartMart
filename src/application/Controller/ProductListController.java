package application.Controller;

import application.Main;
import application.Model.Product;
import application.Model.ProductModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProductListController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    private String category;
    private Product[] productList;

    public ProductListController(String category) {
        this.category = category;
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        try {
            productList = ProductModel.getListByCategory(category);
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
            return;
        }


        int numProducts = productList.length;
        for(int i=0; i < numProducts; i++) {
            Product product = productList[i];
            System.out.println(category + "\n");
        }
    }


}
