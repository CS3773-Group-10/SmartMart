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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static java.util.Objects.isNull;

public class ProductListController implements Initializable {

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
        }
    }

    public void start(AnchorPane mainPane) {
        this.mainPane = mainPane;



        for(int i=0; i < productList.length; i++) {
            Product product = productList[i];
            if (isNull(product))
                continue;
            Pane pane = product.getPane();
            AnchorPane.setTopAnchor(pane, (double) 100 + (140*(i / 2)));
            AnchorPane.setLeftAnchor(pane, (double) 40 + (160*(i % 2)));
            mainPane.getChildren().add(pane);
        }
    }


}
