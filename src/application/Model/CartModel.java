package application.Model;

import application.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    public void addToCart(int custId, int productId, int quantity) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
                "INSERT INTO cartItems"+
                        "(custID, productID, quantity)"+
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
     * @param cartId
     * @throws SQLException
     */
    public void removeFromCart(int cartId) throws SQLException {
        // TODO: removeFromCart(cartId) - removes the cart item from the database
        // use executeUpdate to perform a DELETE
    }


    /**
     * clearCart(custId)
     * clears the customers cart.
     *
     * @param custId
     * @throws SQLException
     */
    public void clearCart(int custId) throws SQLException {
        // TODO: clearCart(custId) - clears the customers cart. removes all cart items associated with the custId. (This will be utilized when a customer converts their cart into an order)
    }

    // TODO: getCart(custId) - returns list of cart ids associated with the custID
    // use executeQuery to SELECT data

    // TODO: getProduct(cartId) - returns product id associated with the cartId

    //TODO: getQuantity(cartId) - returns quantity added to cart at cartId

    // TODO: setQuantity(cartId) - sets the quantity added to cart for cart item at cartId

}