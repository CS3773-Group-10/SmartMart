package application.Model;

import application.Main;

import java.sql.*;

public class CustomerModel {
    Connection conn = Main.conn;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    /**
     * verifyCustomer(String email, String password)
     * Verifies if the customer with the provide email exists with the provided password
     *
     * @param email     the email to be verified
     * @param password  the password to be verified
     * @return true if successfully verified, else false
     * @throws SQLException
     */
    public boolean verifyCustomer(String email, String password) throws SQLException {
        preparedStatement = conn.prepareStatement
            ("SELECT password FROM customers WHERE email=?");
        preparedStatement.setString(1, email);
        resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) { // user exists
            String key = resultSet.getString("password");
            if (password.equals(key)) return true;
        }
        return false;
    }


    /**
     * getUserId(String email)
     *
     * @param email the email of the user to retrieve id for
     * @return id of user with email if exists, else return -1
     * @throws SQLException
     */
    public int getUserId(String email) throws SQLException {
        preparedStatement = conn.prepareStatement
            ("SELECT id FROM customers WHERE email=?");
        preparedStatement.setString(1, email);
        resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) { // user exists
            return resultSet.getInt("id");
        }
        return -1;
    }

    // TODO: add customer

    // TODO: set firstName

    // TODO: set LastName

    // TODO: set email

    // TODO: set password

    // TODO: set shipping address

}
