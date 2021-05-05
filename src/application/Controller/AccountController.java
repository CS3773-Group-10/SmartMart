package application.Controller;

import application.Model.CustomerModel;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AccountController implements Initializable{

    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label emailLabel;
    @FXML private Label addressLabel;
    @FXML private ImageView accountLabel;
    @FXML private ImageView searchImageView;
    @FXML private ImageView cartImageView;
    @FXML private ImageView orderImageView;
    @FXML private ImageView accountImageView;
    @FXML private ImageView logoImageView;

    private int userId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File account2File = new File("images/accountLabel.png");
        Image account2Image = new Image(account2File.toURI().toString());
        accountLabel.setImage(account2Image);

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

    //display the user Id on screen
    public void setUserId(int id) throws SQLException {
        this.userId = id;
        CustomerModel cm = new CustomerModel();

        String firstName = cm.getFirstName(userId);
        firstNameLabel.setText(firstName);

        String lastName = cm.getLastName(userId);
        lastNameLabel.setText(lastName);

        String email = cm.getEmail(userId);
        emailLabel.setText(email);

        String address = cm.getAddress(userId);
        if (address == null) {
            address = "N/A";
        }
        addressLabel.setText(address);
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
