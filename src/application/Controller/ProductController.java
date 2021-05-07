package application.Controller;

import application.Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ProductController implements Initializable {

    private AnchorPane mainPane;
    private ImageView productImage;
    private Button backToProductListButton;

    private Product product;

    private int userId;

    public ProductController(Product product) {
        this.product = product;
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        String path = String.format("images/product-images/product-%d.png", product.getId());
    }

    public void start(AnchorPane mainPane, int userId) {
        this.mainPane = mainPane;
        this.userId = userId;

        backToProductListButton = (Button) mainPane.getChildren().get(0);
        backToProductListButton.setOnAction(this::backToProductList);

    }

    private void backToProductList(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/application/View/productList.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CategoryListController controller = loader.getController();
        controller.setUserId(userId);

        AnchorPane p = loader.getRoot();
        Scene scene = new Scene(p, 360, 640);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

}
