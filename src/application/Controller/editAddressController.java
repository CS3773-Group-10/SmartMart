package application.Controller;
import application.Model.CustomerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class editAddressController implements Initializable{

    private int userId;
    @FXML private ImageView editAddressImageView;
    @FXML private Label currentAddress;
    @FXML private Label messageLabel;
    @FXML private TextField newAddress;
    @FXML private PasswordField password;
    @FXML private ImageView searchImageView;
    @FXML private ImageView cartImageView;
    @FXML private ImageView orderImageView;
    @FXML private ImageView accountImageView;
    @FXML private ImageView logoImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //something
        File addressFile = new File( "images/editAddressLabel.png");
        Image addressImage = new Image(addressFile.toURI().toString());
        editAddressImageView.setImage(addressImage);

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

    //record the userId
    public void setUserId(int id) throws SQLException{
        this.userId = id;
        CustomerModel cm = new CustomerModel();

        String address = cm.getAddress(userId);
        if (address == null) {
            address = "N/A";
        }

        currentAddress.setText(address);
    }

    @FXML
    public void updateAddress(ActionEvent event) throws SQLException {
        String addressInput = newAddress.getText();
        String passInput = password.getText();
        CustomerModel cm = new CustomerModel();
        String email = cm.getEmail(userId);

        if(addressInput.isBlank() || passInput.isBlank()){
            messageLabel.setText("Please enter both fields");
        } else {
            // verify
            if(cm.verifyCustomer(email, passInput)) {
                int id = cm.getUserId(email);
                cm.setAddress(addressInput, userId);
                currentAddress.setText(addressInput);
                messageLabel.setText("Successfully updated!");
            }
           else messageLabel.setText("Login attempt failed.");
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
    @FXML
    public void goToAccount(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        //content root is set to src, this may not work if content root set to something else
        loader.setLocation(getClass().getResource("/application/View/account.fxml"));
        loader.load();

        //get the controller that the fxml is linked to and update the userId
        AccountController controller = loader.getController();
        try {
            controller.setUserId(userId);
            AnchorPane p = (AnchorPane) loader.getRoot();
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
