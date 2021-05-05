package application.Model;

import javafx.scene.image.Image;
import java.io.File;
import java.sql.Date;
import java.sql.SQLException;

public class Product {

    private static ProductModel productModel = new ProductModel();

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
            name = productModel.getName(id);
            description = productModel.getDescription(id);
            category = productModel.getCategory(id);
            quantity = productModel.getQuantity(id);
            date = productModel.getSellBy(id);
            image = productModel.getImage(id);
        }
        catch (SQLException e) {
            System.err.println((e.getMessage()));
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
            name = productModel.getName(id);
            description = productModel.getDescription(id);
            category = productModel.getCategory(id);
            quantity = productModel.getQuantity(id);
            date = productModel.getSellBy(id);
            image = productModel.getImage(id);
        }
        catch (SQLException e) {
            System.err.println((e.getMessage()));
        }
    }
}
