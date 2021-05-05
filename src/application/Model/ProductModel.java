package application.Model;

import application.Main;
import javafx.scene.image.Image;

import java.io.File;
import java.sql.*;

public class ProductModel {
    Connection conn = Main.conn;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    /**
     * getInventory()
     * Gets the entire inventory of products as an array of Product objects
     *
     * @return  list of all products in the database
     */
    public Product[] getInventory() throws SQLException {
        statement = conn.createStatement();

        // create array size of inventory
        resultSet = statement.executeQuery("SELECT count(*) FROM products");
        int invSize = resultSet.getInt("count");
        Product[] inventoryList = new Product[invSize];

        // fill list with ids
        resultSet = statement.executeQuery("SELECT " +
                "(id, name, description, category, quantity, sellBy) FROM products");
        int i = 0;
        while (resultSet.next()) {
            Product product = new Product(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("category"),
                    resultSet.getInt("quantity"),
                    resultSet.getDate("sellBy"),
                    getImage(resultSet.getInt("id")
            ));
            inventoryList[i] = product;
            i++;
        }
        return inventoryList;
    }


    /**
     * getListByCategory(category)
     * returns list of ids for all products under specified category
     *
     * @param category
     * @return
     * @throws SQLException
     */
    public Product[] getListByCategory(String category) throws SQLException {
        // create array of appropriate size
        preparedStatement = conn.prepareStatement(
            "SELECT count(*) FROM products"+
                " WHERE category=?");
        preparedStatement.setString(1, category);
        resultSet = preparedStatement.executeQuery();
        int catSize = resultSet.getInt("count");
        Product[] categoryList = new Product[catSize];

        // fill list with ids
        preparedStatement = conn.prepareStatement(
            "SELECT (id, name, description, category, quantity, sellBy) FROM products " +
                    "WHERE category=?");
        preparedStatement.setString(1, category);
        resultSet = preparedStatement.executeQuery();
        int i = 0;
        while (resultSet.next()) {
            Product product = new Product(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("category"),
                    resultSet.getInt("quantity"),
                    resultSet.getDate("sellBy"),
                    getImage(resultSet.getInt("id")
            ));
            categoryList[i] = product;
        }
        return categoryList;
    }


    /**
     * setQuantity(id, qty)
     * sets the quantity of the product at given id to the given quantity
     *
     * @param id    id of the product to update
     * @param qty   quantity to update to
     * @throws SQLException
     */
    public void setQuantity(int id, int qty) throws SQLException {
        preparedStatement = conn.prepareStatement(
            "UPDATE products"+
                "SET quantity=?"+
                " WHERE id=?");
        preparedStatement.setInt(1, qty);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }


    /**
     * getName(id)
     * gets the name of the product at the provided id
     *
     * @param id    the id of the product to query
     * @return      the name of the product as a string,
     *              returns null if query fails
     * @throws SQLException
     */
    public String getName(int id) throws SQLException {
        preparedStatement = conn.prepareStatement(
            "SELECT name FROM products"+
                " WHERE id=?");
        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("name");
        }
        else return null;
    }


    /**
     * getDescription(id)
     * gets the desctiption of the product at the provided id
     *
     * @param id    the id of the product to query
     * @return      the description of the product as a string,
     *              returns null if query fails
     * @throws SQLException
     */
    public String getDescription(int id) throws SQLException {
        preparedStatement = conn.prepareStatement(
            "SELECT description FROM products"+
                " WHERE id=?");
        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("description");
        }
        else return null;
    }


    /**
     * getCategory(id)
     * gets the category of the product at the provided id
     *
     * @param id    the id of the product to query
     * @return      the category of the product as a string,
     *              returns null if query fails
     * @throws SQLException
     */
    public String getCategory(int id) throws SQLException {
        preparedStatement = conn.prepareStatement(
            "SELECT category FROM products"+
                " WHERE id=?");
        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("category");
        }
        else return null;
    }

    /**
     * getQuantity(id)
     * gets the quantity of the product at the provided id
     *
     * @param id    the id of the product to query
     * @return      the quantity of the product as an int,
     *              returns -1 if query fails
     * @throws SQLException
     */
    public int getQuantity(int id) throws SQLException {
        preparedStatement = conn.prepareStatement(
            "SELECT quantity FROM products"+
                " WHERE id=?");
        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("quantity");
        }
        else return -1;
    }

    /**
     * getSellBy(id)
     * gets the sell by date of the product at the provided id
     *
     * @param id    the id of the product to query
     * @return      the sell by date of the product as type Date,
     *              returns null if query fails
     * @throws SQLException
     */
    public Date getSellBy(int id) throws SQLException {
        preparedStatement = conn.prepareStatement(
            "SELECT sellBy FROM products"+
                " WHERE id=?");
        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDate("sellBy");
        }
        else return null;
    }

    /**
     * getImage(id)
     * gets the display image of the product at the provided id
     *
     * @param id    the id of the product to query
     * @return      the image icon of the product as a javafx Image object,
     *              returns null if query fails
     */
    public Image getImage(int id) throws SQLException {
        String imagePath = String.format("images/product-images/product-%d.png", id);
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        return(image);
    }

}
