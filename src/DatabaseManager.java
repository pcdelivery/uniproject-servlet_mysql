import java.security.DrbgParameters;
import java.sql.*;
import java.util.ArrayList;

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

    public static int COUNTRY_MODE = 1;
    public static int TOWN_MODE = 2;
    public static int PLACES_MODE = 3;
    public static int ACCOUNT_MODE = 4;
    public static int QUIZ_MODE = 5;
    public static int SINGLE_PLACE_MODE = 6;
    public static int QUESTIONS_MODE = 7;
    public static int ANSWERS_MODE = 8;

    /**
     * Getting array of countries presented MySQL database
     * @since 23-05-2020
     * @return Array {"countries": [...]} of countries or null
     */
    public static String getAllCountriesInSQL() {
        lastQuery = "SELECT country_name FROM countries;";
        return fetch(lastQuery, COUNTRY_MODE);
    }

    /**
     * Getting array of filtered towns presented MySQL database
     * @since 23-05-2020
     * @return Array {"towns": [...]} of towns or null
     */
    public static String getAllTownsInSQL(String country) {
        lastQuery = "SELECT town_name FROM towns WHERE country='" + country + "';";
        return fetch(lastQuery, TOWN_MODE);
    }

    /**
     * Getting array of filtered places presented MySQL database
     * @since 23-05-2020
     * @return Array {"places": [{...},{...},...]} of places or null
     */
    public static String getAllPlacesInSQL(String country, String town) {
        lastQuery = "SELECT * FROM places WHERE town='" + town + "' AND country='" + country + "';";
        return fetch(lastQuery, PLACES_MODE);
    }

    /**
     * Getting account info presented MySQL database
     * @since 23-05-2020
     * @return Arcantown account {"account": {...}} or null
     */
    public static String getAccountInfoInSQL(String email) {
        lastQuery = "SELECT * FROM accounts WHERE email='" + email + "';";
        return fetch(lastQuery, ACCOUNT_MODE);
    }

    /**
     * Getting quiz data presented in MySQL database
     * @since 24-05-2020
     * @return Quiz data {"place": {...}, "questions": [{...},{...},...]} or null
     */
    public static String getQuizInSQL(String placeID) {
        // TODO image
        StringBuilder sb = new StringBuilder();

        lastQuery = "SELECT * FROM places WHERE id=" + placeID + ";";
        sb.append("{").append(fetch(lastQuery, SINGLE_PLACE_MODE)).append(",");

        lastQuery = "SELECT * FROM questions WHERE place_id=" + placeID + ";";
        sb.append(fetch(lastQuery, QUESTIONS_MODE)).append("}");

        System.out.println(DATABASE_MANAGER_TAG + "Quiz request result: " + sb.toString());
        return sb.toString();
    }

    /**
     * Creates new account in MySQL database
     * @since 23-05-2020
     * @return Number of changed (placed) rows in MySQL database (1 is good)
     */
    public static int createAccountInSQL(String authType, String email, String login) {
        lastQuery = String.format(
                "INSERT INTO `arcantowndb`.`accounts` (`auth_type`, `email`, `login`, `completed`) " +
                        "VALUES ('%s', '%s', '%s', '0');",
                authType, email, login);

        System.out.println(DATABASE_MANAGER_TAG + "Input query to execute: " + lastQuery);
        return send(lastQuery);
    }

    /**
     * Change existing account in MySQL database
     * @since 23-05-2020
     * @return Number of changed rows (accounts) in MySQL database (1 is good)
     */
    public static int changeAccountProperty(String id, String column, String newValue) {
        lastQuery = String.format(
                "UPDATE `arcantowndb`.`accounts` SET `%s`='%s' WHERE `id`='%s';",
                column, newValue, id);

        System.out.println(DATABASE_MANAGER_TAG + "Input query to execute: " + lastQuery);
        return send(lastQuery);
    }

    /**
     * Change existing account in MySQL database after test completion
     * @since 23-05-2020
     * @return Number of changed rows (accounts) in MySQL database (1 is good)
     */
    public static int appendCompletedQuiz(String userID, String placeID, String points) {
        lastQuery = String.format(
                "UPDATE `arcantowndb`.`accounts` SET `completed`=concat(`completed`,%s), `points`=(`points`+%s) " +
                        "WHERE `id`='%s';",
                placeID, points, userID);

        System.out.println(DATABASE_MANAGER_TAG + "Updating completed quiz text to execute: " + lastQuery);
        return send(lastQuery);
    }

    /**
     * Connects to MySQL database to send or update data
     * @since 23-05-2020
     * @return Number of changed rows in MySQL database (1 is good)
     */
    private static int send(String SQLQuery) {
        Connection connection;
        int result = 0;

        try {
            connection = DriverManager.getConnection(databaseSimplifiedURL, "root", "qwerty123");

            if (!connection.isClosed()) {
                System.out.println(DATABASE_MANAGER_TAG + "Send/Update: Connection established");
                System.out.println(DATABASE_MANAGER_TAG + "Send/Update: Query to execute: " + SQLQuery);

                Statement statement = connection.createStatement();
                result = statement.executeUpdate(SQLQuery);

                connection.close();

                System.out.println(DATABASE_MANAGER_TAG + "Send/Update: Connection was closed as planned");
            } else
                System.out.println(DATABASE_MANAGER_TAG + "Send/Update: Error: Connection was not established");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println(DATABASE_MANAGER_TAG + "Send/Update: Error while updating");
        }

        return result;
    }

    /**
     * Fetching items from MySQL database via static databaseURL
     * @since 19-05-2020
     * @param SQLQuery: Query to execute
     * @param fetchingMode: see private static values
     * @return JSON or null
     */
    private static String fetch(String SQLQuery, int fetchingMode) {
        Connection connection;
        String result = null;

        try {
            connection = DriverManager.getConnection(databaseSimplifiedURL, "root", "qwerty123");

            if (!connection.isClosed()) {
                // @todo Log
                System.out.println(DATABASE_MANAGER_TAG + "Fetch: Connection established");
                System.out.println(DATABASE_MANAGER_TAG + "Fetch: Query to execute: " + SQLQuery);

                Statement statement = connection.createStatement();
                ResultSet data = statement.executeQuery(SQLQuery);

                if (fetchingMode == COUNTRY_MODE)
                    result = _get_one_in_json(data, "countries");
                else if (fetchingMode == TOWN_MODE)
                    result = _get_one_in_json(data, "towns");
                else if (fetchingMode == PLACES_MODE)
                    result = _get_places_in_json(data);
                else if (fetchingMode == ACCOUNT_MODE)
                    result = _get_account_in_json(data);
                else if (fetchingMode == SINGLE_PLACE_MODE)
                    result = _get_one_place_in_json_BALD(data);
                else if (fetchingMode == QUESTIONS_MODE)
                    result = _get_questions_in_json_BALD(data);
                else if (fetchingMode == ANSWERS_MODE)
                    result = _get_answers_json_BALD(data);

                connection.close();

                System.out.println(DATABASE_MANAGER_TAG + "Fetch: Connection was closed as planned");
            } else
                System.out.println(DATABASE_MANAGER_TAG + "Fetch: Error: Connection was not established");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println(DATABASE_MANAGER_TAG + "Fetch: Error while fetching");
        }

        return result;
    }

    public static String _get_one_in_json(ResultSet set, String name) throws SQLException {
        StringBuilder result = new StringBuilder();

        result.append("{").append("\""+ name + "\" : [");

        for (int i = 0; set.next(); i++) {
            if (i == 0)
                result.append("\"").append(set.getString(1)).append("\"");
            else
                result.append(",\"").append(set.getString(1)).append("\"");
        }

        result.append("]}");

        return result.toString();
    }

    public static String _get_places_in_json(ResultSet set) throws SQLException {
        StringBuilder result = new StringBuilder();

        result.append("{").append("\"places\" : [");

        for (int i = 0; set.next(); i++) {
            if (i == 0)
                result.append("{");
            else
                result.append(",{");

            result.append("\"id\": ").append(set.getInt(1)).append(",");
            result.append("\"title\": \"").append(set.getString(2)).append("\",");
            result.append("\"picture_uri\": \"").append(set.getString(3)).append("\",");
            result.append("\"loc_lat\": ").append(set.getDouble(4)).append(",");
            result.append("\"loc_long\": ").append(set.getDouble(5)).append(",");
            result.append("\"open_time\": ").append(set.getInt(6)).append(",");
            result.append("\"close_time\": ").append(set.getInt(7)).append(",");
            result.append("\"rating\": ").append(set.getDouble(8)).append(",");
            result.append("\"country\": \"").append(set.getString(9)).append("\",");
            result.append("\"town\": \"").append(set.getString(10)).append("\"");

            result.append("}");
        }

        result.append("]}");

        return result.toString();
    }

    public static String _get_account_in_json(ResultSet set) throws SQLException {
        StringBuilder result = new StringBuilder();

        result.append("{").append("\"account\" : {");

        if (!set.next())
            return null;

        result.append("\"id\": ").append(set.getInt(1)).append(",");
        result.append("\"auth_type\": \"").append(set.getString(2)).append("\",");
        result.append("\"email\": \"").append(set.getString(3)).append("\",");
        result.append("\"login\": \"").append(set.getString(4)).append("\",");
        result.append("\"name\": \"").append(set.getString(5)).append("\",");
        result.append("\"points\": ").append(set.getInt(6)).append(",");
        result.append("\"country\": \"").append(set.getString(7)).append("\",");
        result.append("\"town\": \"").append(set.getString(8)).append("\",");
        result.append("\"completed\": \"").append(set.getString(9)).append("\"");

        result.append("}}");

        System.out.println(DATABASE_MANAGER_TAG + "Account formed: " + result);

        return result.toString();
    }

    public static String _get_one_place_in_json_BALD(ResultSet set) throws SQLException {
        StringBuilder result = new StringBuilder();

        if (!set.next())
            return null;

//        result.append("{").append("\"place\" : {");
        result.append("\"place\" : {");

        result.append("\"id\": ").append(set.getInt(1)).append(",");
        result.append("\"title\": \"").append(set.getString(2)).append("\",");
        result.append("\"picture_uri\": \"").append(set.getString(3)).append("\",");
        result.append("\"loc_lat\": ").append(set.getDouble(4)).append(",");
        result.append("\"loc_long\": ").append(set.getDouble(5)).append(",");
        result.append("\"open_time\": ").append(set.getInt(6)).append(",");
        result.append("\"close_time\": ").append(set.getInt(7)).append(",");
        result.append("\"rating\": ").append(set.getDouble(8)).append(",");
        result.append("\"country\": \"").append(set.getString(9)).append("\",");
        result.append("\"town\": \"").append(set.getString(10)).append("\"");

//        result.append("}}");
        result.append("}");

        return result.toString();
    }

    public static String _get_questions_in_json_BALD(ResultSet set) throws SQLException {
        StringBuilder result = new StringBuilder();

//        result.append("{").append("\"questions\" : [");
        result.append("\"questions\" : [");

        for (int i = 0; set.next(); i++) {
            if (i == 0)
                result.append("{");
            else
                result.append(",{");

            lastQuery = "SELECT * FROM answers WHERE question_id=" + set.getInt(1) + ";";
            String answerArray = fetch(lastQuery, ANSWERS_MODE);

            result.append("\"id\": ").append(set.getInt(1)).append(",");
            result.append("\"place_id\": ").append(set.getInt(2)).append(",");
            result.append("\"question_type\": \"").append(set.getString(3)).append("\",");
            result.append("\"question_unique_image\": \"").append(set.getString(4)).append("\",");
            result.append("\"question\": \"").append(set.getString(5)).append("\",");
            result.append("\"question_themes\": \"").append(set.getString(6)).append("\",");
            result.append(answerArray);
            result.append("}");
        }

//        result.append("]}");
        result.append("]");

        return result.toString();
    }

    public static String _get_answers_json_BALD(ResultSet set) throws SQLException {
        StringBuilder result = new StringBuilder();

//        result.append("{").append("\"answers\" : [");
        result.append("\"answers\" : [");

        for (int i = 0; set.next(); i++) {
            if (i == 0)
                result.append("{");
            else
                result.append(",{");

            result.append("\"id\": ").append(set.getInt(1)).append(",");
            result.append("\"question_id\": ").append(set.getInt(2)).append(",");
            result.append("\"answer\": \"").append(set.getString(3)).append("\",");
            result.append("\"is_true\": ").append(set.getInt(4)).append(",");
            result.append("\"image_uri\": \"").append(set.getString(5)).append("\"");
            result.append("}");
        }

//        result.append("]}");
        result.append("]");

        return result.toString();
    }
}