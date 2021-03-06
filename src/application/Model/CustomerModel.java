package application.Model;

import application.Main;

import java.sql.*;

public class CustomerModel {
    Connection conn = Main.conn;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    /**
     * verifyCustomer(email, password)
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
     * getUserId(email)
     * returns the id of the user with the provided email
     *
     * @param email the email of the user to retrieve id for
     * @return id of user with email if exists, else return -1
     * @throws SQLException
     */
    public int getUserId(String email) throws SQLException {
        preparedStatement = conn.prepareStatement
            ("SELECT id FROM customers WHERE email=?");
        preparedStatement.setString(1, email.toLowerCase());
        resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) { // user exists
            return resultSet.getInt("id");
        }
        return -1;
    }

    /**
     * getFirstName(id)
     * returns the firstName of the user with the provided id
     *
     * @param id the id of the user to firstName for
     * @return firstName of user with id if exists, else return N/A
     * @throws SQLException
     */
    public String getFirstName(int id) throws SQLException {
        preparedStatement = conn.prepareStatement
                ("SELECT firstName FROM customers WHERE id=?");
        preparedStatement.setString(1, String.valueOf(id));
        resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) { // user exists
            return resultSet.getString("firstName");
        }
        return "N/A";
    }

    /**
     * addCustomer(firstName, lastName, email, password)
     * adds the customer to the customer table if email does not already exist.
     *
     * @param firstName the new customers first name
     * @param lastName  the new customers last name
     * @param email     the new customers email
     * @param password  the new customers password
     * @return id of new user if successful, else return -1
     * @throws SQLException
     */
    public int addCustomer(String firstName, String lastName, String email, String password) throws SQLException {
        email = email.toLowerCase();
        if (getUserId(email) != -1) { // user already exists
            return -1;
        }
        preparedStatement = conn.prepareStatement
            ("INSERT INTO customers "
                + "(firstName, lastName, email, password)"
                + "VALUES(?, ?, ?, ?)");
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, password);
        int success = preparedStatement.executeUpdate();
        if(success > 0) { // new customer successfully added
            return getUserId(email);
        }
        return -1;
    }

    // TODO: set firstName @id

    // TODO: set LastName @id

    // TODO: set email @id

    // TODO: set password @id


    /**
     * setAddress(address)
     * saves a preferred shipping for the customer into the database
     */
    public void setAddress(String address, int id) throws SQLException {
        preparedStatement = conn.prepareStatement
            ("UPDATE customers "
                + "SET address=?"
                + "WHERE id=?");
        preparedStatement.setString(1, address);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }
    /**
     * getLastName(id)
     * returns the lastName of the user with the provided id
     *
     * @param id the id of the user to lastName for
     * @return lastName of user with id if exists, else return N/A
     * @throws SQLException
     */
    public String getLastName(int id) throws SQLException{
        preparedStatement = conn.prepareStatement
                ("SELECT lastName FROM customers WHERE id=?");
        preparedStatement.setString(1, String.valueOf(id));
        resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) { // user exists
            return resultSet.getString("lastName");
        }
        return "N/A";
    }

    /**
     * getEmail(id)
     * returns the email of the user with the provided id
     *
     * @param id the id of the user to email for
     * @return email of user with id if exists, else return N/A
     * @throws SQLException
     */
    public String getEmail(int id) throws SQLException{
        preparedStatement = conn.prepareStatement
                ("SELECT email FROM customers WHERE id=?");
        preparedStatement.setString(1, String.valueOf(id));
        resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) { // user exists
            return resultSet.getString("email");
        }
        return "N/A";
    }

    /**
     * getAddress(id)
     * returns the email of the user with the provided id
     *
     * @param id the id of the user to address for
     * @return address of user with id if exists, else return N/A
     * @throws SQLException
     */
    public String getAddress(int id) throws SQLException{
        preparedStatement = conn.prepareStatement
                ("SELECT address FROM customers WHERE id=?");
        preparedStatement.setString(1, String.valueOf(id));
        resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) { // user exists
            return resultSet.getString("address");
        }
        return "N/A";
    }
}
