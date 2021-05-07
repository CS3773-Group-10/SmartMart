package application.Model;

import application.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Order {
    private static Connection conn = Main.conn;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    /**
     * addCustomer(userId, cardNum, status)
     * adds the customer to the customer table if email does not already exist.
     *
     * @param userId    the customer associated with this order
     * @param cardNum   the billing info of the customer
     * @param status    the status of the order
     * @return id of new order if successful, else return -1
     * @throws SQLException
     */
    public static int createOrder(int userId, int cardNum, String status) throws SQLException {

        preparedStatement = conn.prepareStatement
                ("INSERT INTO orders "
                        + "(custID, cardNum, status)"
                        + "VALUES(?, ?, ?)");
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, cardNum);
        preparedStatement.setString(3, status);
        int success = preparedStatement.executeUpdate();
        if(success > 0) { // new customer successfully added
            return getOrderId(userId);
        }
        return -1;
    }

    /**
     * addToOrder(orderId, productId, quantity)
     * adds an orderItem associated with orderId
     *
     * @param orderId    - the customer the  item is associated with
     * @param productId - the product being added to the cart
     * @param quantity  - the quantity being added to the cart
     * @throws SQLException
     */
    public static void addToOrder(int orderId, int productId, int quantity) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
                "INSERT INTO orderItems" +
                        "(orderID, productID, quantity)" +
                        "VALUES (?, ?, ?)");
        preparedStatement.setInt(1, orderId);
        preparedStatement.setInt(2, productId);
        preparedStatement.setInt(3, quantity);
        preparedStatement.executeUpdate();
    }

    /**
     * getOrderItems(orderId)
     * returns list of orderItem ids associated with orderID
     *
     * @param orderId    - the order the order items are associated with
     * @throws SQLException
     */
    public static ArrayList<Integer> getOrderItems(int orderId) throws SQLException {
        ArrayList<Integer> orderItemList = new ArrayList<>();

        // fill list with cart item ids
        PreparedStatement preparedStatement = conn.prepareStatement(
                "SELECT id FROM orderItems "+
                        "WHERE orderID = ?");
        preparedStatement.setInt(1, orderId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            orderItemList.add(resultSet.getInt("id"));
        }
        return orderItemList;
    }

    /**
     * getOrderId(userId)
     * returns the id of the order with the provided userId
     *
     * @param userId the id of the user to retrieve order id for
     * @return orderID of user if exists, else return -1
     * @throws SQLException
     */
    public static int getOrderId(int userId) throws SQLException {
        preparedStatement = conn.prepareStatement
                ("SELECT MAX(id) FROM orders WHERE custID=?");
        preparedStatement.setInt(1, userId);
        resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) { // user exists
            return resultSet.getInt(1);
        }
        return -1;
    }

    /**
     * getCardNum(orderId)
     * returns the cardNum of the user with the provided orderId
     *
     * @param orderId the orderId of the user to retrieve cardNum for
     * @return cardNum of order if order exists, else return -1
     * @throws SQLException
     */
    public int getCardNum(int orderId) throws SQLException {
        preparedStatement = conn.prepareStatement
                ("SELECT cardNum FROM orders WHERE id=?");
        preparedStatement.setInt(1, orderId);
        resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) { // order exists
            return resultSet.getInt("cardNum");
        }
        return -1;
    }

    /**
     * getStatus(orderId)
     * returns the status of the order with the provided orderId
     *
     * @param orderId the orderId of the user to retrieve cardNum for
     * @return cardNum of order if order exists, else return "N/A"
     * @throws SQLException
     */
    public String getStatus(int orderId) throws SQLException {
        preparedStatement = conn.prepareStatement
                ("SELECT status FROM orders WHERE id=?");
        preparedStatement.setInt(1, orderId);
        resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) { // order exists
            return resultSet.getString("status");
        }
        return "N/A";
    }
}
