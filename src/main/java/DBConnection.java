import java.sql.*;

public class DBConnection {

    private static Connection getConnection()
            throws SQLException, ClassNotFoundException {
        String dbDriver = "com.mysql.cj.jdbc.Driver";
        String dbURL = "jdbc:mysql://localhost:3306/";
        // Database name to access
        String dbName = "birthdays";
        String dbUsername = "root";
        String dbPassword = "root";

        Class.forName(dbDriver);
        return DriverManager.getConnection(dbURL + dbName + "?useUnicode=yes&characterEncoding=UTF-8&serverTimezone=UTC",
                dbUsername,
                dbPassword);
    }

    protected static void add(String name, java.util.Date birthday) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into birthdays (name, birthday) values(?,DATE_ADD(?, interval 1 day));");
        preparedStatement.setString(1, name);

        Date date1 = new Date(birthday.getTime());
        preparedStatement.setDate(2, date1);
        preparedStatement.executeUpdate();
        connection.close();
    }
    protected static void remove(String name) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("delete from birthdays where name = ?");
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();
        connection.close();
    }

    protected static HashTable<String, java.util.Date> getAllBirthdays() throws SQLException, ClassNotFoundException {
        HashTable<String, java.util.Date> map = new HashTable<>();
        Connection connection = getConnection();
        ResultSet resultSet = connection.prepareStatement("select * from birthdays").executeQuery();
        while (resultSet.next()) {
            map.add(resultSet.getString(1), resultSet.getDate(2));
        }
        connection.close();
        return map;
    }
}
