package application.Controller;


import application.Main;
import application.Model.CustomerModel;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.net.URL;

public class LoginController implements Initializable {

    @FXML
    private Label loginMessageLabel;

    @FXML
    private ImageView logoImageView;

    @FXML
    private ImageView signinImageView;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField enterPasswordField;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File logoFile = new File( "images/logo.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);

        File loginFile = new File( "images/login.png");
        Image loginImage = new Image(loginFile.toURI().toString());
        signinImageView.setImage(loginImage);
    }

    public void loginButtonOnAction(ActionEvent event) throws SQLException {
        String emailInput = usernameTextField.getText();
        String passInput = enterPasswordField.getText();

        if(emailInput.isBlank() || passInput.isBlank()){
            loginMessageLabel.setText("Please enter your Username and Password.");
        } else {
            // verify
            CustomerModel cm = new CustomerModel();
            if(cm.verifyCustomer(emailInput, passInput)) {
                int id = cm.getUserId(emailInput);
                goToCategoryList(event, id);
            }
            else loginMessageLabel.setText("Login attempt failed.");
        }
    }

    @FXML
    private void goToCategoryList(ActionEvent event, int id) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/categoryList.fxml"));

        //content root is set to src, this may not work if content root set to something else
        try {
            loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
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

    public void registerButtonOnAction(ActionEvent event) throws IOException {
        AnchorPane mainPane = FXMLLoader.load(Main.class.getResource("View/register.fxml"));
        Scene scene = new Scene(mainPane, 360, 640);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }
}
