package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SQLRunner {

    private static final String DELIMITER = ";";

    public void runScript(Connection connection, File file)
            throws FileNotFoundException, SQLException {
        Scanner scanner = new Scanner(file).useDelimiter(DELIMITER);
        Statement statement = connection.createStatement();

        while (scanner.hasNext()) {
            String nextStatement = scanner.next();
            //System.out.println(nextStatement);
            if (!nextStatement.trim().isEmpty()) {
                statement.execute(nextStatement);
            }
        }
        statement.close();
        scanner.close();
    }

}
