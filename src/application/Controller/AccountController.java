package application.Controller;

import application.Model.CustomerModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

public class AccountController implements Initializable{

    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label emailLabel;
    @FXML private Label addressLabel;
    @FXML private ImageView accountImageView;

    private int userId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File accountFile = new File("images/accountLabel.png");
        Image accountImage = new Image(accountFile.toURI().toString());
        accountImageView.setImage(accountImage);
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
}
