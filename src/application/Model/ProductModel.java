package application.Model;

import application.Main;
import javafx.scene.image.Image;

import java.io.File;
import java.sql.*;

public class ProductModel {
    private static Connection conn = Main.conn;

    /**
     * getInventory()
     * Gets the entire inventory of products as an array of Product objects
     *
     * @return  list of all products in the database
     */
    public static Product[] getInventory() throws SQLException {
        Statement statement = conn.createStatement();

        // create array size of inventory
        ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM products");
        int invSize = resultSet.getInt("count");
        Product[] inventoryList = new Product[invSize];

        // fill list with ids
        resultSet = statement.executeQuery(
                "SELECT id, name, description, category, price, quantity, sellBy FROM products");
        int i = 0;
        while (resultSet.next()) {
            Product product = new Product(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("category"),
                    resultSet.getInt("price"),
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
    public static Product[] getListByCategory(String category) throws SQLException {
        // create array of appropriate size
        PreparedStatement preparedStatement = conn.prepareStatement(
            "SELECT count(*) AS count FROM products"+
                " WHERE category=?");
        preparedStatement.setString(1, category);
        ResultSet resultSet = preparedStatement.executeQuery();
        int catSize = 0;
        if (resultSet.next())
            catSize = resultSet.getInt(1);
        Product[] categoryList = new Product[catSize];

        // fill list with ids
        preparedStatement = conn.prepareStatement(
            "SELECT id, name, description, category, price, quantity, sellBy FROM products " +
                    "WHERE category=?");
        preparedStatement.setString(1, category);
        resultSet = preparedStatement.executeQuery();
        int i = 0;
        while (resultSet.next()) {
            Product product = new Product(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("category"),
                    resultSet.getInt("price"),
                    resultSet.getInt("quantity"),
                    resultSet.getDate("sellBy"),
                    getImage(resultSet.getInt("id")
            ));
            categoryList[i] = product;
            i++;
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
    public static void setQuantity(int id, int qty) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
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
    public static String getName(int id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
            "SELECT name FROM products"+
                " WHERE id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
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
    public static String getDescription(int id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
            "SELECT description FROM products"+
                " WHERE id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
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
    public static String getCategory(int id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
            "SELECT category FROM products"+
                " WHERE id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
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
    public static int getQuantity(int id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
            "SELECT quantity FROM products"+
                " WHERE id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
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
    public static Date getSellBy(int id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
            "SELECT sellBy FROM products"+
                " WHERE id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDate("sellBy");
        }
        else return null;
    }


    public static int getPrice(int id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
            "SELECT price FROM products"+
                " WHERE id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return (resultSet.getInt("price"));
        }
        else return -1;
    }


    /**
     * getImage(id)
     * gets the display image of the product at the provided id
     *
     * @param id    the id of the product to query
     * @return      the image icon of the product as a javafx Image object,
     *              returns null if query fails
     */
    public static Image getImage(int id) throws SQLException {
        String imagePath = String.format("images/product-images/product-%d.png", id);
        File file = new File(imagePath);
        return (new Image(file.toURI().toString()));
    }

}
