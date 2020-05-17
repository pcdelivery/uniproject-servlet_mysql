import java.sql.*;

class DatabaseManager {
    private static final String DATABASE_MANAGER_TAG = "DataBaseManager: ";

    //@todo: ? OR % OR $ OR ~
    private static final String DIVIDER = "?";

    private static final String databaseSimplifiedURL = "jdbc:mysql://localhost:3306/arcantowndb" +
            "?serverTimezone=UTC";
    private static final String databaseURL = "jdbc:mysql://localhost:3306/arcantowndb"+
            "?useUnicode=true"+
            "&useSSL=true"+
            "&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false"+
            "&serverTimezone=UTC";

    private Connection connection;
    private String _query;
    public String _result_string;

    DatabaseManager() {
        _query = null;
    }

    public void setQuery(String query) {
        this._query = query;
    }

    public String databaseGetCountries() {
        _query = "SELECT country_name FROM countries;";

        return execute__countries();
    }

    public String databaseGetPlaces(String town) {
        //@todo: town doubles
        if (town == null)
            return "";

        _query = "SELECT place_name, latitude, longitude FROM places WHERE town='" + town + "';";
        return execute__places();
    }

    public String databaseGetTownsByCountry(String country) {
        if (country == null)
            return "";

        _query = "SELECT town_name FROM towns WHERE country='" + country + "';";
        return execute__towns();
    }

    public String databaseCompareAccount(String login, String email, String password) {
        if (login != null && email != null && password != null) {
            StringBuilder sb = new StringBuilder();

            String[] filter = {"login=" + '\'' + login + '\'',
                    "email=" + '\'' + email + '\'',
                    "password=" + '\'' + password + '\''};

            _query = "SELECT * FROM accounts WHERE " + filter[0] + " AND " + filter[1] + " AND " + filter[2] + ';';
            return execute__auth();
        }

        return "false";
    }

    private String execute__countries() {
        try {
            connection = DriverManager.getConnection(databaseSimplifiedURL, "root", "qwerty123");

            if (!connection.isClosed()) {
                System.out.println(DATABASE_MANAGER_TAG + "Connection established");
                System.out.println(DATABASE_MANAGER_TAG + "Query to execute: " + _query);

                Statement statement = connection.createStatement();
                ResultSet data = statement.executeQuery(_query);

                StringBuilder resultSet = new StringBuilder();
                while(data.next()) {
                    resultSet.append(data.getString(1)).append('|');
                }

                connection.close();
                _result_string = resultSet.toString();

                System.out.println("DatabaseManager: " + "Connection was closed as planned");
                System.out.println("DatabaseManager: " + "Data set: " + _result_string);

                return _result_string;
            } else {
                System.out.println("DatabaseManager: " + "Connection is not established");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "";
    }

    private String execute__towns() {
        try {
            connection = DriverManager.getConnection(databaseSimplifiedURL, "root", "qwerty123");

            if (!connection.isClosed()) {
                System.out.println(DATABASE_MANAGER_TAG + "Connection established");
                System.out.println(DATABASE_MANAGER_TAG + "Query to execute: " + _query);

                Statement statement = connection.createStatement();
                ResultSet data = statement.executeQuery(_query);

                StringBuilder resultSet = new StringBuilder();
                while(data.next()) {
                    resultSet.append(data.getString(1)).append('|');
                }

                connection.close();
                _result_string = resultSet.toString();

                System.out.println("DatabaseManager: " + "Connection was closed as planned");
                System.out.println("DatabaseManager: " + "Data set: " + _result_string);

                return _result_string;
            } else {
                System.out.println("DatabaseManager: " + "Connection is not established");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "";
    }

    private String execute__places() {
        try {
            connection = DriverManager.getConnection(databaseSimplifiedURL, "root", "qwerty123");

            if (!connection.isClosed()) {
                System.out.println(DATABASE_MANAGER_TAG + "Connection established");
                System.out.println(DATABASE_MANAGER_TAG + "Query to execute: " + _query);

                Statement statement = connection.createStatement();
                ResultSet data = statement.executeQuery(_query);

                StringBuilder resultSet = new StringBuilder();
                while(data.next()) {
                    resultSet.append(data.getString(1)).append('|');
                    resultSet.append(data.getDouble(2)).append('|');
                    resultSet.append(data.getDouble(3)).append('|');
                }

                connection.close();
                _result_string = resultSet.toString();

                System.out.println("DatabaseManager: " + "Connection was closed as planned");
                System.out.println("DatabaseManager: " + "Data set: " + _result_string);

                return _result_string;
            } else {
                System.out.println("DatabaseManager: " + "Connection is not established");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "";
    }

    private String execute__auth() {
        try {
            connection = DriverManager.getConnection(databaseSimplifiedURL, "root", "qwerty123");

            if (!connection.isClosed()) {
                System.out.println(DATABASE_MANAGER_TAG + "Connection established");
                System.out.println(DATABASE_MANAGER_TAG + "Query to execute: " + _query);

                Statement statement = connection.createStatement();
                ResultSet data = statement.executeQuery(_query);

                if (!data.next())
                    return "false";

                connection.close();

                System.out.println("DatabaseManager: " + "Connection was closed as planned");
                System.out.println("DatabaseManager: " + "Result: true");

                return "true";
            } else {
                System.out.println("DatabaseManager: " + "Connection is not established");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // @todo: error vs false
        return "connection_error";
    }
}