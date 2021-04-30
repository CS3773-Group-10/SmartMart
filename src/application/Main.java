package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {

    public static Connection conn; // connection to database

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/login.fxml"));
        primaryStage.setTitle("SmartMart :)");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        try {
            // connect to database
            conn = DriverManager.getConnection("jdbc:sqlite:smartmart.db");

            // start javafx application
            launch(args);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        finally { // close db connection
            try {
                if (conn != null) { // close conn if it exists
                    conn.close();
                }
            }
            catch (SQLException e) {
                System.err.println((e.getMessage()));
            }
        }
    }
}
