package application.Controller;
import application.Model.CartModel;
import application.Model.ProductModel;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

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
    void checkout(ActionEvent event) {
        // TODO: proceed to checkout
    }


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
        CartModel cm = new CartModel();
        ProductModel pm = new ProductModel();
        ArrayList<Integer> cart = cm.getCart(userId);

        for (Integer cartId : cart) { // for each item in the cart, create an hbox
            int productId = cm.getProduct(cartId);
            HBox hbox = new HBox(10);

            // add photo to hbox
            File productImgFile= new File("images/product-images/product-"+productId+".png");
            Image productImg = new Image(productImgFile.toURI().toString());
            ImageView productImgView = new ImageView(productImg);

            // add product name
            String name = pm.getName(productId);
            Label nameLabel = new Label(name);

            // add quantity to hbox
            int quantity = cm.getQuantity(cartId);
            Label quantitylabel = new Label("x" + quantity);

            // add price to hbox
            double price = cm.getItemsPrice(cartId);
            Label priceLabel = new Label(String.format("$%.2f", price));

            // add delete button to hbox (red x)
            Button del = new Button("delete");
            del.setTextFill(Color.color(1, 0, 0));

            HBox.setHgrow(quantitylabel, Priority.ALWAYS);
            quantitylabel.setMaxWidth(Double.MAX_VALUE);
            hbox.getChildren().addAll(productImgView, nameLabel, quantitylabel, priceLabel, del);
            hbox.setPrefWidth(310);
            cartVbox.getChildren().add(hbox);
        }
        double total = cm.getCartTotal(userId);
        totalLbl.setText(String.format("Total: $%.2f", total));
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

        //content root is set to src, this may not work if content root set to something else
        loader.setLocation(getClass().getResource("/application/View/categoryList.fxml"));
        loader.load();

        //get the controller that the fxml is linked to and update the userId
        CategoryListController controller = loader.getController();
        controller.setUserId(userId);

        AnchorPane p = (AnchorPane) loader.getRoot();
        Scene scene = new Scene(p, 360, 640);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

    public void goToAccount(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        //content root is set to src, this may not work if content root set to something else
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
            //exception
        }
    }

}
