package application.Controller;

import application.Main;
import application.Model.CartModel;
import application.Model.Product;
import application.Model.ProductModel;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class ProductController implements Initializable {

    @FXML private AnchorPane mainPane;
    @FXML private ImageView searchImageView;
    @FXML private ImageView cartImageView;
    @FXML private ImageView orderImageView;
    @FXML private ImageView accountImageView;
    @FXML private ImageView logoImageView;
    @FXML private ImageView productImageView;
    @FXML private Label productNameLabel;
    @FXML private Label productPriceLabel;
    @FXML private Label productDescriptionLabel;
    @FXML private Label quantityRemainingLabel;
    @FXML private Label quantitySelectedLabel;
    @FXML private Button minusButton;
    @FXML private Button plusButton;
    @FXML private Button addToCartButton;

    private Product product;

    private int userId;
    private int quantitySelected;

    public ProductController(Product product) {
        this.product = product;
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {

    }

    public void start(AnchorPane mainPane, int userId) {
        this.mainPane = mainPane;
        this.userId = userId;
        quantitySelected = 0;

        productNameLabel.setText(product.getName());
        productPriceLabel.setText(product.getPriceAsString());
        productDescriptionLabel.setText(product.getDescription());
        quantityRemainingLabel.setText(String.format("%d left in stock", product.getQuantity()));
        quantitySelectedLabel.setText("0");

        File productImageFile = new File(String.format("images/product-images/product-%d.png", product.getId()));
        Image productImage = new Image(productImageFile.toURI().toString());
        productImageView.setImage(productImage);

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
    private void goToAccount(MouseEvent event) throws IOException {
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
            e.printStackTrace();
        }
    }

    @FXML
    private void subtractItem(ActionEvent event) {
        if (quantitySelected > 0)
            quantitySelected--;
        quantitySelectedLabel.setText(String.valueOf(quantitySelected));
    }

    @FXML
    private void addItem(ActionEvent event) {
        if (quantitySelected < product.getQuantity())
            quantitySelected++;
        quantitySelectedLabel.setText(String.valueOf(quantitySelected));
    }

    @FXML
    private void addToCart(ActionEvent event) {
        try {
            CartModel.addToCart(userId, product.getId(), quantitySelected);
            // update stock quantity in database
            int newStockQty = product.getQuantity() - quantitySelected;
            ProductModel.setQuantity(product.getId(), newStockQty);

            // update product object
            product = new Product(product.getId());

            // reload view
            reload(event);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void reload(ActionEvent event) {
        ProductController controller = new ProductController(product);

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/product.fxml"));
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
        controller.start(pane, userId);
    }

}
