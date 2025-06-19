package util;

import java.sql.*;

import repo.UserRepo;
import static util.InputUtil.*;

import static util.TableQueries.CREATE_TABLES;

public class DBConfig {
    private static final String DB_NAME = "bank_db";
    private static final String USER = "root";
    private static final String PASSWORD = "0000";
    private static final String BASE_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_URL = BASE_URL + DB_NAME + "?useSSL=false&allowPublicKeyRetrieval=true";

    private static boolean initialized = false;

    public static void initDatabase() {
        if (initialized)
            return;

        try (Connection conn = DriverManager.getConnection(BASE_URL, USER, PASSWORD);
                Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SHOW DATABASES LIKE '" + DB_NAME + "'");
            if (!rs.next()) {
                stmt.executeUpdate("CREATE DATABASE " + DB_NAME);
            }

        } catch (SQLException e) {
            print("Error creating database: " + e.getMessage());
        }

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {
            for (String query : CREATE_TABLES) {
                stmt.executeUpdate(query);
            }

            UserRepo.getInstance().insertAdmin();

        } catch (SQLException e) {
            print("Error creating tables: " + e.getMessage());
        }

        initialized = true;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}
