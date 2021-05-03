package application.Model;

import application.Main;

import java.sql.*;

public class ProductModel {
    Connection conn = Main.conn;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    /**
     * getInventory()
     * Gets the entire inventory of products as an array of all product IDs
     *
     * @return  list of product IDs for all products in the database
     */
    public int[] getInventory() throws SQLException {
        // TODO: get product list
        statement = conn.createStatement();

        // create array size of inventory
        resultSet = statement.executeQuery("SELECT count(*) FROM products");
        int invSize = resultSet.getInt("count");
        int[] inventoryList = new int[invSize];

        // fill list with ids
        resultSet = statement.executeQuery("SELECT id FROM products");
        int i = 0;
        while (resultSet.next()) {
            inventoryList[i] = resultSet.getInt("id");
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
    public int[] getListByCategory(String category) throws SQLException {
        // TODO: get category list
        // create array of approperiate size
        preparedStatement = conn.prepareStatement(
            "SELECT count(*) FROM products"+
                " WHERE category=?");
        preparedStatement.setString(1, category);
        resultSet = preparedStatement.executeQuery();
        int catSize = resultSet.getInt("count");
        int[] categoryList = new int[catSize];

        // fill list with ids
        preparedStatement = conn.prepareStatement(
            "SELECT id FROM products"+
                " WHERE category=?");
        preparedStatement.setString(1, category);
        resultSet = preparedStatement.executeQuery();
        int i = 0;
        while (resultSet.next()) {
            categoryList[i] = resultSet.getInt("id");
        }
        return categoryList;
    }


    public void setQuantity(int id, int qty) throws SQLException {
        // TODO: set product quantity
        preparedStatement = conn.prepareStatement(
            "UPDATE products"+
                "SET quantity=?"+
                " WHERE id=?");
        preparedStatement.setInt(1, qty);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }


    public String getName(int id) throws SQLException {
        // TODO: get product name by id
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

    // TODO: get product description by id

    // TODO: get product category

    // TODO: get product quantity in stock

    // TODO: get product sell by

    // TODO: get product image -- we should add a folder of product images with the file name "product-"+ id +".png"

}
