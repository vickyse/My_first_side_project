package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 定義class DBConnector，連接到專案數據庫，用於通篇專案中任何需要用到SQL查詢的部件。
 */
public class DBConnector {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // 固定用法。
    static final String DB_URL = "jdbc:mysql://sideproject.cdwguw44yf2n.ap-southeast-2.rds.amazonaws.com:3306/" +
            "side_project?user=admin";
    // 專案DB的URL。
    static final String USER = "admin";
    // 專案DB的使用者名稱。
    static final String PASSWORD = "f131078696";
    // 專案DB的密碼。

    /**
     * 方法，嘗試連接到數據庫。
     * @return 若成功連接到數據庫，返回一個Connection實體，用於往後的SQL操作。
     * @throws SQLException 若JDBC驅動發生任何問題拋出。
     */
    public static Connection connectToDB() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("連接至指定數據庫...");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            return connection;
        } catch (ClassNotFoundException e) {
            throw new SQLException("找不到JDBC驅動", e);
        }
    }
}
