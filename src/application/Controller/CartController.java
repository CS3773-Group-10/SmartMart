package application.Controller;
import application.Model.CartModel;
import application.Model.Order;
import application.Model.Product;
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

public class CartController implements Initializable {

    @FXML private ImageView searchImageView;
    @FXML private ImageView cartImageView;
    @FXML private ImageView orderImageView;
    @FXML private ImageView accountImageView;
    @FXML private ImageView logoImageView;
    @FXML private VBox cartVbox;
    @FXML private Label totalLbl;

//    @FXML
//    private Label addbutton;
//    @FXML
//    private Button buttonAddToCart;


    private int userId;

    /**
     * BUTTON ACTION FUNCTIONS
     */


    @FXML
    void deleteCartItem(ActionEvent event) {
        // backburner task, not priority
    }

    /**
     * INITALIZATION FUNCTIONS
     */

    public void setUserId(int id) {
        this.userId = id;
        try {
            populateCart(id); // populate cart using users id
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void populateCart(int userId) throws SQLException {
        // get cart for the user (list)
        ArrayList<Integer> cart = CartModel.getCart(userId);
        CartModel cm = new CartModel();

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

            // add delete button to hbox (red x)
            Button del = new Button("delete");
            del.setTextFill(Color.color(1, 0, 0));

            EventHandler<ActionEvent> delEvent = e -> {
                try {
                    // add quantity in cart back to inventory stock quantity
                    int cartQuantity = cm.getQuantity(cartId);
                    int oldStockQuantity = ProductModel.getQuantity(productId);
                    int newStockQty = oldStockQuantity + cartQuantity;
                    ProductModel.setQuantity(productId, newStockQty);

                    // delete the cart item from the cart
                    cm.removeFromCart(cartId);

                    // reload cart view
                    reload(e);

                } catch (SQLException | IOException e1) {
                    e1.printStackTrace();
                }
            };

            // assign delEvent action to del button
            del.setOnAction(delEvent);

            HBox.setHgrow(quantityLabel, Priority.ALWAYS);
            quantityLabel.setMaxWidth(Double.MAX_VALUE);
            hbox.getChildren().addAll(productImgView, nameLabel, quantityLabel, priceLabel, del);
            hbox.setPrefWidth(310);
            cartVbox.getChildren().add(hbox);
        }
        String total = CartModel.getCartTotalAsString(userId);
        totalLbl.setText(total);
    }

    /**
     * Clears the cart
     *
     * @param event
     */
    @FXML
    void clearCart(ActionEvent event) throws SQLException, IOException {
        // get cart for the user (list)
        ArrayList<Integer> cart = CartModel.getCart(userId);
        CartModel cm = new CartModel();

        for (Integer cartId : cart) { // for each item in the cart, remove
            try {
                int productId = CartModel.getProduct(cartId);

                // add quantity in cart back to inventory stock quantity
                int cartQuantity = cm.getQuantity(cartId);
                int oldStockQuantity = ProductModel.getQuantity(productId);
                int newStockQty = oldStockQuantity + cartQuantity;
                ProductModel.setQuantity(productId, newStockQty);

                // delete the cart item from the cart
                cm.removeFromCart(cartId);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // reload cart view
        reload(event);
    }

    /**
     * reload(event)
     * reloads the cart view
     *
     * @param event
     * @throws IOException
     */
    public void reload(ActionEvent event) throws IOException {
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

    public void checkout(javafx.event.ActionEvent actionEvent) throws SQLException{
       // int isEmpty = CartModel.isEmpty(userId);
        int orderId = Order.createOrder(userId, 1122233, "Confirmed");
        CartModel.clearCart(userId);
    }
}
