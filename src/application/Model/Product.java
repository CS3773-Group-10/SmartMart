package application.Model;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.sql.Date;
import java.sql.SQLException;

public class Product {

    private final int id;
    private String name;
    private String description;
    private String category;
    private int price;
    private int quantity;
    private Date date;
    private Image image;

    public Product(int id) {
        this.id = id;
        try {
            name = ProductModel.getName(id);
            description = ProductModel.getDescription(id);
            category = ProductModel.getCategory(id);
            price = ProductModel.getPrice(id);
            quantity = ProductModel.getQuantity(id);
            date = ProductModel.getSellBy(id);
            image = ProductModel.getImage(id);
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Product(int id, String name, String description, String category, int price,
                   int quantity, Date date, Image image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
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

    public BorderPane getPane() {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(120);
        imageView.setFitWidth(120);
        Label nameLabel = new Label(name);

        BorderPane pane = new BorderPane();
        pane.setCenter(imageView);
        pane.setBottom(nameLabel);
        return pane;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getDate() {
        return date;
    }

    public Image getImage() {
        return image;
    }

    public String getPriceAsString() {
        return String.format("$%d.%02d", (price/100), (price%100));
    }

    public int getPriceAsInt() {
        return price;
    }
}
