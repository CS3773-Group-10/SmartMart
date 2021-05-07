package application.Controller;

import application.Main;
import application.Model.Product;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class ProductController implements Initializable {

    private AnchorPane mainPane;
    private ImageView productImage;

    @FXML private ImageView searchImageView;
    @FXML private ImageView cartImageView;
    @FXML private ImageView orderImageView;
    @FXML private ImageView accountImageView;
    @FXML private ImageView logoImageView;

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

        File searchFile = new File( "images/searchIcon.png");
        Image searchImage = new Image(searchFile.toURI().toString());
        searchImageView.setImage(searchImage);

        File orderFile = new File( "images/ordersIcon.png");
        Image orderImage = new Image(orderFile.toURI().toString());
        orderImageView.setImage(orderImage);

        File cartFile = new File( "images/cartIcon.png");
        Image cartImage = new Image(cartFile.toURI().toString());
        cartImageView.setImage(cartImage);

        File accountFile = new File( "images/accountIcon.png");
        Image accountImage = new Image(accountFile.toURI().toString());
        accountImageView.setImage(accountImage);

        File logoFile = new File( "images/logoIcon.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);
    }

    private void backToProductList(ActionEvent event) {
        ProductListController controller = new ProductListController(product.getCategory(), userId);

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/productList.fxml"));
        loader.setController(controller);

        AnchorPane pane;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(pane, 360, 640);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setResizable(false);
        window.show();
        controller.start(pane);
    }

    @FXML
    private void goToCategoryList(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/application/View/categoryList.fxml"));
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

    @FXML
    public void goToAccount(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/application/View/account.fxml"));
        loader.load();

        //get the controller that the fxml is linked to and update the userId
        AccountController controller = loader.getController();
        try {
            controller.setUserId(userId);
            AnchorPane p = loader.getRoot();
            Scene scene = new Scene(p, 360, 640);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.setResizable(false);
            window.show();
        }
        catch (Exception e) {
            //exception
        }
    }


}
