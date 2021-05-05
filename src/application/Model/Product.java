package application.Model;

import application.Controller.ProductListController;
import javafx.scene.image.Image;
import java.io.File;
import java.sql.Date;
import java.sql.SQLException;

//import static application.Controller.ProductListController.productModel;

public class Product {

    private int id;
    private String name;
    private String description;
    private String category;
    private int quantity;
    private Date date;
    private Image image;

    public Product(int id) {
        this.id = id;
        try {
            name = ProductModel.getName(id);
            description = ProductModel.getDescription(id);
            category = ProductModel.getCategory(id);
            quantity = ProductModel.getQuantity(id);
            date = ProductModel.getSellBy(id);
            image = ProductModel.getImage(id);
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Product(int id, String name, String description, String category, int quantity, Date date, Image image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.date = date;
        this.image = image;
    }

    public void update() {
        try {
            name = ProductModel.getName(id);
            description = ProductModel.getDescription(id);
            category = ProductModel.getCategory(id);
            quantity = ProductModel.getQuantity(id);
            date = ProductModel.getSellBy(id);
            image = ProductModel.getImage(id);
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
