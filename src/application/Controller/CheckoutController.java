package application.Controller;

import application.Model.CartModel;
import application.Model.CustomerModel;
import application.Model.Order;
import application.Model.ProductModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CheckoutController implements Initializable {

    @FXML private ImageView searchImageView;
    @FXML private ImageView cartImageView;
    @FXML private ImageView orderImageView;
    @FXML private ImageView accountImageView;
    @FXML private ImageView logoImageView;
    @FXML private VBox cartVbox;
    @FXML private Label totalLbl;
    @FXML private Label errorMsg;
    @FXML private TextField addressLine;
    @FXML private TextField cardNumLine;

    private int orderId;
    private int userId;

    public void setId(int userId) throws SQLException{
        this.userId = userId;
        try {
            populateCart(userId); // populate cart using users id
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initalizeFields(userId);
    }

    private void initalizeFields(int userId) throws SQLException {
        CustomerModel cm = new CustomerModel();
        String custAddr = cm.getAddress(userId);
        if (!custAddr.equals("N/A")) { // if address exist
            addressLine.setText(custAddr);
        }
    }

    @FXML
    void placeOrder(ActionEvent event) throws SQLException, IOException {
        if (addressLine.getText().isBlank() || cardNumLine.getText().isBlank()) {
            errorMsg.setText("Fields left blank. Please enter both an address and card number.");
        } else {
            // place the order
            int cardNum = Integer.valueOf(cardNumLine.getText());
            int orderId = Order.createOrder(this.userId, cardNum, "Pending Order");

            // add carts as order items and clear
            ArrayList<Integer> cart = CartModel.getCart(userId);
            for (Integer cartId : cart) { // for each item in the cart
                int productId = CartModel.getProduct(cartId);
                int qty = CartModel.getQuantity(cartId);
                Order.addToOrder(orderId, productId, qty); // add to orderItems
            }
            // clear cart
            CartModel.clearCart(userId);

            // open orders screen
            redirectToOrders(event);
        }
    }

    public void populateCart(int userId) throws SQLException {
        // get cart for the user (list)
        ArrayList<Integer> cart = CartModel.getCart(userId);

        for (Integer cartId : cart) { // for each item in the cart, create an hbox
            int productId = CartModel.getProduct(cartId);
            HBox hbox = new HBox(10);

            // add photo to hbox
            File productImgFile= new File("images/product-images/product-"+productId+".png");
            Image productImg = new Image(productImgFile.toURI().toString());
            ImageView productImgView = new ImageView(productImg);
            productImgView.setX(20);
            productImgView.setY(20);  // add product name
            productImgView.setFitWidth(40);
            productImgView.setPreserveRatio(true);
            String name = ProductModel.getName(productId);
            Label nameLabel = new Label(name);

            // add quantity to hbox
            int quantity = CartModel.getQuantity(cartId);
            Label quantityLabel = new Label("x" + quantity);

            // add price to hbox
            String price = CartModel.getItemsPriceAsString(cartId);
            Label priceLabel = new Label(price);


            HBox.setHgrow(quantityLabel, Priority.ALWAYS);
            quantityLabel.setMaxWidth(Double.MAX_VALUE);
            hbox.getChildren().addAll(productImgView, nameLabel, quantityLabel, priceLabel);
            hbox.setPrefWidth(310);
            cartVbox.getChildren().add(hbox);
        }
        String total = CartModel.getCartTotalAsString(userId);
        totalLbl.setText(total);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initialize images
        File searchFile = new File("images/searchIcon.png");
        Image searchImage = new Image(searchFile.toURI().toString());
        searchImageView.setImage(searchImage);

        File orderFile = new File("images/ordersIcon.png");
        Image orderImage = new Image(orderFile.toURI().toString());
        orderImageView.setImage(orderImage);

        File cartFile = new File("images/cartIcon.png");
        Image cartImage = new Image(cartFile.toURI().toString());
        cartImageView.setImage(cartImage);

        File accountFile = new File("images/accountIcon.png");
        Image accountImage = new Image(accountFile.toURI().toString());
        accountImageView.setImage(accountImage);

        File logoFile = new File( "images/logoIcon.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);
    }

    /**
     * MENU FUNCTIONS
     */


    private void redirectToOrders(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/application/View/orders.fxml"));
        loader.load();

        OrdersController controller = loader.getController();
        try {
            controller.setUserId(userId);
            Pane p = loader.getRoot();
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
    private void goToOrders(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/application/View/orders.fxml"));
        loader.load();

        OrdersController controller = loader.getController();
        try {
            controller.setUserId(userId);
            Pane p = loader.getRoot();
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
    private void goToCategoryList(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/application/View/categoryList.fxml"));
        loader.load();

        //get the controller that the fxml is linked to and update the userId
        CategoryListController controller = loader.getController();
        controller.setUserId(userId);

        AnchorPane p = loader.getRoot();
        Scene scene = new Scene(p, 360, 640);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToCart(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/application/View/cart.fxml"));
        loader.load();

        //get the controller that the fxml is linked to and update the userId
        CartController controller = loader.getController();
        try {
            controller.setUserId(userId);
            Pane p = loader.getRoot();
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

}
