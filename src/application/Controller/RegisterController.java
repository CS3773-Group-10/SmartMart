package application.Controller;

import application.Main;
import application.Model.CustomerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private ImageView registerImage;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField comfirmPasswordField;

    @FXML
    private Button registerButton;

    @FXML
    private Label registerMessageLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File registerFile = new File( "images/register.png");
        Image rImage = new Image(registerFile.toURI().toString());
        registerImage.setImage(rImage);

    }

    @FXML
    void register(ActionEvent event) throws SQLException {

        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordField.getText();
        String comfirmPassword = comfirmPasswordField.getText();

        // if passwords match, add customer using Customer Model
        CustomerModel cm = new CustomerModel();
        if(password.equals(comfirmPassword)){
            int userId = cm.addCustomer(firstName, lastName, email, password);
            
            try {
                goToCategoryList(event, userId);
            }
            catch (IOException e) {
                //something
            }
        } else{
            registerMessageLabel.setText("Password doesn't match. Please, try again.");
        }

    }

    @FXML
    private void goToCategoryList(ActionEvent event, int id) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        //content root is set to src, this may not work if content root set to something else
        loader.setLocation(getClass().getResource("/application/View/categoryList.fxml"));
        loader.load();

        //get the controller that the fxml is linked to and update the userId
        CategoryListController controller = loader.getController();
        controller.setUserId(id);

        AnchorPane p = (AnchorPane) loader.getRoot();
        Scene scene = new Scene(p, 360, 640);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

    public void goBack(ActionEvent event) throws IOException {
        Pane mainPane = FXMLLoader.load(Main.class.getResource("View/login.fxml"));
        Scene scene = new Scene(mainPane, 360, 640);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }
}
