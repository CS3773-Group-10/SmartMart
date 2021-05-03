package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;

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

            // if database is empty, initialize with default data
            Statement statement = conn.createStatement();

            ResultSet rs = statement.executeQuery("select count(*) from sqlite_master");

            if (rs.getInt("count(*)") == 0) { // empty
                // run each script in sql_script folder to initialize the data
                File folder = new File(".\\src\\application\\sql_scripts");
                File[] listOfFiles = folder.listFiles();
                SQLRunner runner = new SQLRunner();
                for (File file : listOfFiles) {
                    if (file.isFile()) {
                        try {
                            runner.runScript(conn, file);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }


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
