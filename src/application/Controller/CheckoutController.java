package application.Controller;

import application.Model.CartModel;
import application.Model.ProductModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

public class CheckoutController {
    private int orderId;
    private int userId;

    public void setId(int orderId, int userId) throws SQLException{
        this.userId = userId;
        this.orderId = orderId;
        setOrder();
    }

    private void setOrder() throws SQLException {

    }
}
