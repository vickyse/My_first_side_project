package model;
import java.sql.*; // 導入JDBC。
import java.util.HashMap;

/**
 * 定義植筋、鋼筋。
 */
public class rebar_detailed_page {
    public HashMap<Integer, Float> rebar_details; // 屬性，用於儲存從MySQL中獲取的資料。

    public rebar_detailed_page() throws SQLException {
        HashMap<Integer, Float> rebar_details = new HashMap<>();
        rebar_detailed_page.initialiseDetailedPage();
    } // 初始化。

    /**
     * 初始化鋼筋報價。會以"鋼筋編號: x 價格:y(元/cm)來表示。
     * @throws SQLException 若在DBConnector.connectToDB()出錯或是此方法本身出錯，都會拋出此錯誤。
     */
    public static void initialiseDetailedPage() throws SQLException {
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

            int basic_revenue = 0;

            while (rs.next()) {
                int rebar_number = rs.getInt("rebar_number");
                float price_per_cm = rs.getFloat("price_per_cm");
                basic_revenue = rs.getInt("basic_revenue");

                System.out.println("鋼筋編號: " + rebar_number + ", " + "價格: " + price_per_cm);
            }
            System.out.println("出車費: " + basic_revenue);
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
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
    }
}