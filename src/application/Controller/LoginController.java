package application.Controller;


import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import javafx.scene.image.Image;
import java.io.File;
import java.util.ResourceBundle;
import java.net.URL;

public class LoginController implements Initializable {

    @FXML
    private Label loginMessageLabel;

    @FXML
    private ImageView logoImageView;

    @FXML
    private ImageView signinImageView;

    /*@FXML
    private TextField usernameTextField;

    @FXML
    private TextField enterPasswordField;*/



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File logoFile = new File( "images/logo.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);

        File loginFile = new File( "images/login.png");
        Image loginImage = new Image(loginFile.toURI().toString());
        signinImageView.setImage(loginImage);
    }

    public void loginButtonOnAction(ActionEvent event){

        //if(usernameTextField.getText().isBlank() == false && enterPasswordField.getText().isBlank() == false){
            loginMessageLabel.setText("You try to login");
        //}else{
         //   loginMessageLabel.setText("Please enter your Username and Password");
       // }
    }

}
