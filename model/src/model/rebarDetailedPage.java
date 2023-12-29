package model;
import java.sql.*; // 導入JDBC。
import java.util.HashMap;
import java.util.Map;

/**
 * 定義植筋、鋼筋頁面。
 */
public class rebarDetailedPage {
    public HashMap<Integer, Float> rebarDetails; // 屬性，用於儲存從MySQL中獲取的資料。

    public rebarDetailedPage() throws SQLException {
        this.rebarDetails = new HashMap<>();
        this.initialiseDetailedPage();
    } // 初始化。

    /**
     * 初始化鋼筋報價。會以"鋼筋編號: x 價格:y(元/cm)來表示。
     * 目前先以text形式建立，在做UI時必須改為GUI。
     * @throws SQLException 若在DBConnector.connectToDB()出錯或是此方法本身出錯，都會拋出此錯誤。
     */
    public void initialiseDetailedPage() throws SQLException {
        ResultSet rs = null;
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = DBConnector.connectToDB();
            System.out.println("實體化SQL語句...");
            stmt = conn.createStatement();
            String sql = null;
            sql = "SELECT * FROM rebar;";
            rs = stmt.executeQuery(sql);

            int basicRevenue = 0;

            while (rs.next()) {
                int rebarNumber = rs.getInt("rebar_number");
                float pricePerCm = rs.getFloat("price_per_cm");
                basicRevenue = rs.getInt("basic_revenue");

                HashMap<Integer, Float> rebarDetails = this.rebarDetails;
                rebarDetails.put(rebarNumber, pricePerCm);
            }
            for (Map.Entry<Integer, Float> keyValue : rebarDetails.entrySet()) {
                Integer key = keyValue.getKey();
                Float value = keyValue.getValue();

                System.out.println("鋼筋編號: #" + key + " " + "價格為: " + value + "(元/每公分深度)");
                System.out.println("出車費為: " + basicRevenue + "元");
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            closeResourceInFinally(rs, stmt, conn);
        }
    }

    /**
     * 方法，放置於每個使用到JDBC連接到MySQL的方法，放在finally語句當中，用於確保在使用JDBC的try catch當中若catch到Exception時也能保證
     * 關閉所有資源。
     * @param rs JDBC連接到MySQL的方法中所產生的result set。
     * @param stmt JDBC連接到MySQL的方法中所產生的SQL statement。
     * @param conn JDBC連接到MySQL的方法中所產生的Connection。
     */
    public static void closeResourceInFinally(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        rebarDetailedPage a = new rebarDetailedPage();
    }

}