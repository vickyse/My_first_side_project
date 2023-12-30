package model;
import java.sql.*; // 導入JDBC。
import java.util.HashMap;
import java.util.Map;

/**
 * 定義植筋、鋼筋頁面。
 */
public class rebarDetailedPage {
    public HashMap<Integer, Float> rebarDetails; // 屬性，用於儲存從MySQL中獲取的資料。
    public ResultSet rs;
    public Statement stmt;
    public Connection conn;
    public String sql;

    /**
     * 初始化rebarDetailedPage頁面。
     * @throws SQLException SQL錯誤。
     */
    public rebarDetailedPage() throws SQLException {
        this.rebarDetails = new HashMap<>();
        this.rs = null;
        this.stmt = null;
        this.conn = null;
        this.sql = null;
        this.initialiseDetailedPage();
    } // 初始化。

    /**
     * 初始化鋼筋報價。會以"鋼筋編號: x 價格:y(元/cm)來表示。
     * 目前先以text形式建立，在做UI時必須改為GUI。
     * @throws SQLException 若在DBConnector.connectToDB()出錯或是此方法本身出錯，都會拋出此錯誤。
     */
    public void initialiseDetailedPage()
            throws SQLException {
        try {
            this.conn = DBConnector.connectToDB();
            System.out.println("實體化SQL語句...");
            this.stmt = this.conn.createStatement();
            this.sql = "SELECT * FROM rebar;";
            this.rs = this.stmt.executeQuery(sql);

            int basicRevenue = 0;

            while (this.rs.next()) {
                int rebarNumber = this.rs.getInt("rebar_number");
                float pricePerCm = this.rs.getFloat("price_per_cm");
                basicRevenue = this.rs.getInt("basic_revenue");

                this.rebarDetails.put(rebarNumber, pricePerCm);
            }
            for (Map.Entry<Integer, Float> keyValue : this.rebarDetails.entrySet()) {
                Integer key = keyValue.getKey();
                Float value = keyValue.getValue();

                System.out.println("鋼筋編號: #" + key + " " + "價格為: " + value + "(元/每公分深度)");
            }
            System.out.println("出車費為: " + basicRevenue + "元");
        } catch (SQLException se) {
            closeResourceInException();
            se.printStackTrace();
        }
    }

    /**
     * 方法，用於修改既有的鋼筋編號的價格。
     * @param rebarNumber 鋼筋編號。
     * @param price 欲修改的價格。
     */
    public void editRebarDetail(int rebarNumber, float price) {
        if (this.rebarDetails.containsKey(rebarNumber)) {
            try {
                this.sql = "UPDATE rebar SET price_per_cm = " + price
                        + "WHERE rebar_number = " + rebarNumber;
                System.out.println("正在修改...");
                this.stmt.executeUpdate(this.sql);
                System.out.println("完成！");
            } catch (SQLException e) {
                closeResourceInException();
                e.printStackTrace();
            }
        } else {
            System.out.println("未知的鋼筋編號");
        }
    }

    /**
     * 方法，用於關閉資源
     * @throws SQLException 查詢時發生的錯誤。
     */
    public void closeResource() throws SQLException {
        if (this.rs != null) {
            try {
                this.rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (this.stmt != null) {
            try {
                this.stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 方法，放置於每個使用到JDBC連接到MySQL的方法，放在Exception語句當中，用於確保在使用JDBC的try catch當中若catch到Exception時也能保證
     * 關閉所有資源。
     */
    public void closeResourceInException() {
        if (this.rs != null) {
            try {
                this.rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (this.stmt != null) {
            try {
                this.stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 重繪方法，用於除了初始化頁面方法後的所有details繪製。
     * @throws SQLException 出錯時拋出。
     */
    public void redraw() throws SQLException {
        try {
            this.rebarDetails.clear();
            this.sql = "SELECT * FROM rebar;";
            this.rs = this.stmt.executeQuery(sql);

            int basicRevenue = 0;

            while (this.rs.next()) {
                int rebarNumber = this.rs.getInt("rebar_number");
                float pricePerCm = this.rs.getFloat("price_per_cm");
                basicRevenue = this.rs.getInt("basic_revenue");

                this.rebarDetails.put(rebarNumber, pricePerCm);
            }
            for (Map.Entry<Integer, Float> keyValue : this.rebarDetails.entrySet()) {
                Integer key = keyValue.getKey();
                Float value = keyValue.getValue();

                System.out.println("鋼筋編號: #" + key + " " + "價格為: " + value + "(元/每公分深度)");
            }
            System.out.println("出車費為: " + basicRevenue + "元");
        } catch (SQLException se) {
            closeResourceInException();
            se.printStackTrace();
        }
    }
    public static void main(String[] args) throws SQLException {
        rebarDetailedPage a = new rebarDetailedPage();
        System.out.println();
        a.editRebarDetail(3, 1.0f);
        System.out.println();
        a.redraw();
        a.closeResource();
    }
}