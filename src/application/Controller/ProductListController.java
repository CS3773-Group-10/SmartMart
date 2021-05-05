package application.Controller;

import application.Main;
import application.Model.Product;
import application.Model.ProductModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static java.util.Objects.isNull;

public class ProductListController implements Initializable {

    private AnchorPane mainPane;
    private ImageView header;

    private String category;
    private Product[] productList;
    private int userId;

    @FXML private ImageView searchImageView;
    @FXML private ImageView cartImageView;
    @FXML private ImageView orderImageView;
    @FXML private ImageView accountImageView;
    @FXML private ImageView logoImageView;

    public ProductListController(String category, int id) {
        this.userId = id;
        this.category = category;
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        String path = String.format("images/%sTextLabel.png", category.toLowerCase());
        File headerFile = new File(path);
        Image headerImage = new Image(headerFile.toURI().toString());
        header = new ImageView(headerImage);

        try {
            productList = ProductModel.getListByCategory(category);
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
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

    public void start(AnchorPane mainPane) {
        this.mainPane = mainPane;

        AnchorPane.setTopAnchor(header, (double)15);
        AnchorPane.setLeftAnchor(header, (double) 40);
        mainPane.getChildren().add(header);

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


}
