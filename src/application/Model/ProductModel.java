package application.Model;

import application.Main;

import java.sql.*;

public class ProductModel {
    Connection conn = Main.conn;
    private Statement statement = null;
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
        resultSet = statement.executeQuery("SELECT count(*) FROM customers");
        int invSize = resultSet.getInt("count");
        int[] inventoryList = new int[invSize];

        // fill list with ids
        resultSet = statement.executeQuery("SELECT id FROM customers");
        int i = 0;
        while (resultSet.next()) {
            inventoryList[i] = resultSet.getInt("id");
            i++;
        }
        return inventoryList;
    }

    // TODO: get list of categories

    // TODO: set product quantity

    // TODO: get product name by id

    // TODO: get product description by id

    // TODO: get product category

    // TODO: get product quantity in stock

    // TODO: get product sell by

    // TODO: get product image -- we should add a folder of product images with the file name "product-"+ id +".png"

}
