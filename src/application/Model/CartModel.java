package application.Model;
import application.Controller.CartController;

import application.Main;

import javax.xml.transform.Result;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CartModel {
    private static Connection conn = Main.conn;

    /**
     * addToCart(custId, productId, quantity)
     * adds a cartItem associated with custId
     *
     * @param custId    - the customer the cart item is associated with
     * @param productId - the product being added to the cart
     * @param quantity  - the quantity being added to the cart
     * @throws SQLException
     */
    public static void addToCart(int custId, int productId, int quantity) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
                "INSERT INTO cartItems" +
                        "(custID, productID, quantity)" +
                        "VALUES (?, ?, ?)");
        preparedStatement.setInt(1, custId);
        preparedStatement.setInt(2, productId);
        preparedStatement.setInt(3, quantity);
        preparedStatement.executeUpdate();
    }


    /**
     * removeFromCart(cartId)
     * removes the cart item from the database
     *
     * @param cartId    - the cart item to be deleted
     * @throws SQLException
     */
    public static void removeFromCart(int cartId) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
                "DELETE FROM cartItems "+
                        "WHERE id = ? ");
        preparedStatement.setInt(1, cartId);
        preparedStatement.executeUpdate();

    }


    /**
     * clearCart(custId)
     * clears the customers cart.
     * removes all cart items associated with the custId
     * (This will be utilized when a customer converts their cart into an order)
     *
     * @param custId    - the customer the cart item is associated with
     * @throws SQLException
     */
    public void clearCart(int custId) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
                "DELETE FROM cartItems "+
                        "WHERE custId = ?");
        preparedStatement.setInt(1, custId);
        preparedStatement.executeUpdate();
    }


    /**
     * getCart(custId)
     * returns list of cart ids associated with custID
     *
     * @param custId    - the customer the cart items are associated with
     * @throws SQLException
     */
    public static ArrayList<Integer> getCart(int custId) throws SQLException {
        ArrayList<Integer> cartList = new ArrayList<>();

        // fill list with cart item ids
        PreparedStatement preparedStatement = conn.prepareStatement(
            "SELECT id FROM cartItems "+
                "WHERE custId = ?");
        preparedStatement.setInt(1, custId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            cartList.add(resultSet.getInt("id"));
        }
        return cartList;
    }


    /**
     * getProduct(cartId)
     * returns product id associated with the cartId
     *
     * @param cartId    - the cart item to query
     * @return          - the product id associated with the cartId, -1 if error
     * @throws SQLException
     */
    public static int getProduct(int cartId) throws SQLException {
    PreparedStatement preparedStatement = conn.prepareStatement(
        "SELECT productId FROM cartItems "+
            "WHERE id = ?");
        preparedStatement.setInt(1, cartId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("productId");
        }
        else return -1;
    }


    /**
     * getQuantity(cartId)
     *
     * gets the quantity added to cart at cartId
     *
     * @param cartId    the id of the product to query
     * @return      the quantity of the product as an int,
     *              returns -1 if query fails
     * @throws SQLException
     */
    public static int getQuantity(int cartId) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
                "SELECT quantity FROM cartItems "+
                        " WHERE id=?");
        preparedStatement.setInt(1, cartId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("quantity");
        }
        else return -1;
    }




    /**
     * setQuantity(cartId, quantity )
     * sets the quantity added to cart for cart item at cartId
     *
     * @param cartId    id of the product to update
     * @param quantity   quantity to update to
     * @throws SQLException
     */
    public static void setQuantity(int cartId, int quantity) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
                "UPDATE products "+
                        "SET quantity=? "+
                        "WHERE id=?");
        preparedStatement.setInt(1, quantity);
        preparedStatement.setInt(2, cartId);
        preparedStatement.executeUpdate();
    }


    /**
     * getItemsPrice
     * gets the total price for a particular cart item
     *
     * @param cartId
     * @return
     * @throws SQLException
     */
    public static int getItemsPrice(int cartId) throws SQLException {
        int pid = getProduct(cartId);
        int singularPrice = ProductModel.getPrice(pid);
        int quantity = getQuantity(cartId);
        return (singularPrice * quantity);
    }

    /**
     * getItemsPriceAsString
     * gets the total price for a particular cart item as a String
     *
     * @param cartId
     * @return
     * @throws SQLException
     */
    public static String getItemsPriceAsString(int cartId) throws SQLException {
        int pid = getProduct(cartId);
        int singularPrice = ProductModel.getPrice(pid);
        int total = getQuantity(cartId) * singularPrice;
        return (String.format("$%d.%02d", (total / 100), (total % 100)));
    }


    public static int getCartTotal(int custId) throws SQLException {
        int total = 0;
        PreparedStatement preparedStatement = conn.prepareStatement(
            "SELECT id FROM cartItems "+
                "WHERE custId = ?");
        preparedStatement.setInt(1, custId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int cid = resultSet.getInt("id");
            total += getItemsPrice(cid);
        }
        return total;
    }

    public static String getCartTotalAsString(int custId) throws SQLException {
        int total = 0;
        PreparedStatement preparedStatement = conn.prepareStatement(
                "SELECT cartId FROM cartItems "+
                        "WHERE custId = ?");
        preparedStatement.setInt(1, custId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int cid = resultSet.getInt("cartId");
            total += getItemsPrice(cid);
        }
        return (String.format("$%d.%02d", (total / 100), (total % 100)));
    }

}
