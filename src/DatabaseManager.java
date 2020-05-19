import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

class DatabaseManager {
    private static final String DATABASE_MANAGER_TAG = "DataBaseManager: ";

    //@todo: ? OR % OR $ OR ~
    public static final String ROW_DIVIDER = "?";

    private static final String databaseSimplifiedURL = "jdbc:mysql://localhost:3306/arcantowndb" +
            "?serverTimezone=UTC";
    private static final String databaseURL = "jdbc:mysql://localhost:3306/arcantowndb"+
            "?useUnicode=true"+
            "&useSSL=true"+
            "&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false"+
            "&serverTimezone=UTC";

    public static String lastQuery;
    public static String lastResult;

    public static ArrayList<String> getAllCountriesInSQL() {
        lastQuery = "SELECT country_name FROM countries;";
        ResultSet set = fetch(lastQuery);

        if (set == null)
            // @todo?
            return null;

        // todo: set.getArray
        ArrayList<String> res = new ArrayList<String>();

        try {
            while(set.next())
                res.add(set.getString(1));
        } catch (SQLException x) {
            System.out.println(DATABASE_MANAGER_TAG + "Fetch: Error: " + x.getErrorCode());
            res = null;
        }

        lastResult = res.toString();
        return res;
    }

    public static ArrayList<String> getAllTownsInSQL(String country) {
        lastQuery = "SELECT town_name FROM towns WHERE country='" + country + "';";
        ResultSet set = fetch(lastQuery);

        if (set == null)
            // @todo?
            return null;

        // todo: set.getArray
        ArrayList<String> res = new ArrayList<String>();

        try {
            while(set.next()) {
                res.add(set.getString(1));
            }
        } catch (SQLException x) {
            System.out.println(DATABASE_MANAGER_TAG + "Fetch: Error: " + x.getErrorCode());
            res = null;
        }

        lastResult = res.toString();
        return res;
    }

    //@todo: town doubles
    public static ArrayList<String> getAllPlacesInSQL(String town) {
        lastQuery = "SELECT place_name, latitude, longitude FROM places WHERE town='" + town + "';";
        ResultSet set = fetch(lastQuery);

        if (set == null)
            // @todo?
            return null;

        // todo: set.getArray
        ArrayList<String> res = new ArrayList<String>();

        try {
            while(set.next()) {
                res.add(set.getString(1) + ROW_DIVIDER);
                res.add(set.getDouble(2) + ROW_DIVIDER);
                res.add(set.getDouble(3) + ROW_DIVIDER);
            }
        } catch (SQLException x) {
            System.out.println(DATABASE_MANAGER_TAG + "Fetch: Error: " + x.getErrorCode());
            res = null;
        }

        lastResult = res.toString();
        return res;
    }

    /*
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


        if (!data.next())
            return "false";

        // @todo: error vs false
    }
     */

    /**
     * Fetching items from MySQL database via static databaseURL
     * @since 19-05-2020
     * @param SQLQuery
     * @return set of received items or Null
     */
    private static ResultSet fetch(String SQLQuery) {
        Connection connection;
        ResultSet data = null;

        try {
            connection = DriverManager.getConnection(databaseSimplifiedURL, "root", "qwerty123");

            if (!connection.isClosed()) {
                // @todo Log
                System.out.println(DATABASE_MANAGER_TAG + "Fetch: Connection established");
                System.out.println(DATABASE_MANAGER_TAG + "Fetch: Query to execute: " + SQLQuery);

                Statement statement = connection.createStatement();
                data = statement.executeQuery(SQLQuery);

                connection.close();

                System.out.println(DATABASE_MANAGER_TAG + "Fetch: Connection was closed as planned");
            } else
                System.out.println(DATABASE_MANAGER_TAG + "Fetch: Error: Connection was not established");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println(DATABASE_MANAGER_TAG + "Fetch: Error while fetching");
        }

        return data;
    }

    public String _databaseGetTry() {
        return "{\n" +
                "        \"placeTitle\" : \"Kremlin\",\n" +
                "            \"placeImage\" : \"/kremlin.png\",\n" +
                "            \"questions\" : [" +
                "{\n" +
                "        \"question-type\" : \"text\",\n" +
                "            \"question-unique-image\" : \"null\",\n" +
                "            \"question\" : \"Why are you gay?\",\n" +
                "            \"answers\" : [\"You gAy\", \"Like really gay\", \"Ur mom gay\"],\n" +
                "        \"correctAnswerIndex\" : 3\n" +
                "}," +
                "{\n" +
                "        \"question-type\" : \"text\",\n" +
                "            \"question-unique-image\" : \"null\",\n" +
                "            \"question\" : \"Why kremlin gay?\",\n" +
                "            \"answers\" : [\"no you gay\", \"rusophobic gay\", \"actually ur mom gay\", \"Mah Empire\", \"dunno just another answer\", \"dunno just another answer2\", \"dunno just another answer3\", \"dunno just another answer4\"],\n" +
                "        \"correctAnswerIndex\" : 3\n" +
                "}]\n" +
                "    }";
    }
}