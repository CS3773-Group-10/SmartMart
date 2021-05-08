package application.Controller;

import application.Main;
import application.Model.CartModel;
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

public class OrdersController implements Initializable {

    @FXML private ImageView searchImageView;
    @FXML private ImageView cartImageView;
    @FXML private ImageView orderImageView;
    @FXML private ImageView accountImageView;
    @FXML private ImageView logoImageView;
    @FXML private VBox vbox;

    private int userId;

    /**
     * INITALIZATION FUNCTIONS
     */

    public void setUserId(int id) {
        this.userId = id;
        try {
            listOrders(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listOrders(int userId) throws SQLException {
        // get cart for the user (list)
        ArrayList<Integer> orders = Order.getOrders(userId);
        Order o = new Order();

        for (Integer orderId : orders) { // for each order associated with
            HBox hbox = new HBox(10);

            // add number
            Label orderNumLabel = new Label("Order ID: " + orderId);

            // add status
            Label statusLbl = new Label("Status: " + o.getStatus(orderId));


            HBox.setHgrow(orderNumLabel, Priority.ALWAYS);
            orderNumLabel.setMaxWidth(Double.MAX_VALUE);
            hbox.getChildren().addAll(orderNumLabel, statusLbl);
            hbox.setPrefWidth(310);
            vbox.getChildren().add(hbox);
        }
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

    @FXML
    public void goToCart(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/application/View/cart.fxml"));
        loader.load();

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
}
