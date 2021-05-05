package application.Controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CategoryListController implements Initializable {

    @FXML
    private ImageView browseImageView;

    @FXML
    private ImageView fruitImageView;

    @FXML
    private ImageView veggiesImageView;

    @FXML
    private ImageView meatsImageView;

    @FXML
    private ImageView grainsImageView;

    @FXML
    private ImageView snacksImageView;

    @FXML
    private ImageView dairyImageView;

    @FXML
    private Label userLabel;

    private int userId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File browseFile = new File( "images/browseTextLabel.png");
        Image browseImage = new Image(browseFile.toURI().toString());
        browseImageView.setImage(browseImage);

        File fruitsFile = new File( "images/fruitsIcon.png");
        Image fruitImage = new Image(fruitsFile.toURI().toString());
        fruitImageView.setImage(fruitImage);

        File veggiesFile = new File( "images/veggiesIcon.png");
        Image veggiesImage = new Image(veggiesFile.toURI().toString());
        veggiesImageView.setImage(veggiesImage);

        File dairyFile = new File( "images/dairyIcon.png");
        Image dairyImage = new Image(dairyFile.toURI().toString());
        dairyImageView.setImage(dairyImage);

        File grainsFile = new File( "images/grainsIcon.png");
        Image grainsImage = new Image(grainsFile.toURI().toString());
        grainsImageView.setImage(grainsImage);

        File meatsFile = new File( "images/meatsIcon.png");
        Image meatsImage = new Image(meatsFile.toURI().toString());
        meatsImageView.setImage(meatsImage);

        File snacksFile = new File( "images/snacksIcon.png");
        Image snacksImage = new Image(snacksFile.toURI().toString());
        snacksImageView.setImage(snacksImage);
    }

    //display the user Id on screen
    public void setUserId(int id) {
        this.userId = id;
        userLabel.setText("User id is: " + userId);
    }

    @FXML
    private void goToProductList(MouseEvent event) throws IOException {

        Map<EventTarget, String> categories = new HashMap<>();
        categories.put(fruitImageView, "Fruits");
        categories.put(veggiesImageView, "Vegetables");
        categories.put(dairyImageView, "Dairy");
        categories.put(grainsImageView, "Grains");
        categories.put(meatsImageView, "Meat");
        categories.put(snacksImageView, "Snacks");

        EventTarget target = event.getTarget();
        String category = categories.get(event.getTarget());

        ProductListController controller = new ProductListController(category);

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/productList.fxml"));
        loader.setController(controller);

        AnchorPane mainPane = loader.load();
        Scene scene = new Scene(mainPane, 360, 640);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

}
