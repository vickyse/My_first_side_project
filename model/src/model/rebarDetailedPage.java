package model;
import java.sql.*; // 導入JDBC。
import java.util.LinkedHashMap; // 不使用HashMap的原因為HashMap的存儲是沒有依照SQL查詢順序的。
import java.util.Map;

/**
 * 定義植筋、鋼筋頁面。
 */
public class rebarDetailedPage {
    LinkedHashMap<Integer, Float> rebarDetails; // 屬性，用於儲存從MySQL中獲取的資料。
    public ResultSet rs;
    public Statement stmt;
    public Connection conn;
    public String sql;

    /**
     * 初始化rebarDetailedPage頁面。
     */
    public rebarDetailedPage() {
        this.rebarDetails = new LinkedHashMap<>();
        this.rs = null;
        this.stmt = null;
        this.conn = null;
        this.sql = null;
        this.initialiseDetailedPage();
    } // 初始化。

    /**
     * 初始化鋼筋報價。會以"鋼筋編號: x 價格:y(元/cm)來表示。
     * 目前先以text形式建立，在做UI時必須改為GUI。
     */
    public void initialiseDetailedPage() {
        try {
            this.conn = DBConnector.connectToDB();
            System.out.println("實體化SQL語句...");
            this.stmt = this.conn.createStatement();
            this.sql = "SELECT * FROM rebar ORDER BY rebar_number;";
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
            closeResource();
            se.printStackTrace();
        }
    }

    /**
     * 方法，用於新增新種類編號的鋼筋以及其價格。
     * @param rebarNumber 鋼筋編號。
     * @param price 其價格。
     */
    //TODO : 之後記得添加若是參數資料型態不符規定時的處理
    public void addNewRebar(int rebarNumber, float price) {
        if (!this.rebarDetails.containsKey(rebarNumber)) {
            try {
                this.sql = "INSERT INTO rebar (rebar_number, price_per_cm, basic_revenue) VALUES" + "(" + rebarNumber
                        + ", " + price + ", 1000)";
                this.stmt.executeUpdate(this.sql);
            } catch (SQLException e) {
                closeResource();
                e.printStackTrace();
            }
        } else {
            System.out.println("此種鋼筋已存在。");
        }
    }

    /**
     * 方法，用於修改既有的鋼筋編號的價格。
     * @param rebarNumber 鋼筋編號。
     * @param price 欲修改的價格。
     */
    //TODO : 之後記得添加若是參數資料型態不符規定時的處理
    public void editRebarDetail(int rebarNumber, float price) {
        if (this.rebarDetails.containsKey(rebarNumber)) {
            try {
                this.sql = "UPDATE rebar SET price_per_cm = " + price
                        + "WHERE rebar_number = " + rebarNumber;
                System.out.println("正在修改...");
                this.stmt.executeUpdate(this.sql);
                System.out.println("完成！");
            } catch (SQLException e) {
                closeResource();
                e.printStackTrace();
            }
        } else {
            System.out.println("未知的鋼筋編號");
        }
    }

    /**
     * 方法，刪除指定鋼筋。
     * @param rebarNumber 鋼筋編號。
     */
    //TODO : 之後記得添加若是參數資料型態不符規定時的處理
    public void deleteRebar(int rebarNumber) {
        if (this.rebarDetails.containsKey(rebarNumber)) {
            try {
                this.sql = "DELETE FROM rebar WHERE rebar_number = " + rebarNumber;
                System.out.println("正在刪除...");
                this.stmt.executeUpdate(this.sql);
                System.out.println("完成！");
            } catch (SQLException e) {
                closeResource();
                e.printStackTrace();
            }
        } else {
            System.out.println("此鋼筋不存在。");
        }
    }

    /**
     * 修改植筋的出車費。
     * @param newBasicRevenue 欲修改的出車費。
     */
    //TODO : 之後記得添加若是參數資料型態不符規定時的處理
    public void editBasicRevenue(int newBasicRevenue) {
        try {
            this.sql = "UPDATE rebar SET basic_revenue =" + newBasicRevenue + " WHERE rebar_number != 100;";
            System.out.println("正在修改...");
            this.stmt.executeUpdate(this.sql);
            System.out.println("完成！");
        } catch (SQLException se) {
            closeResource();
            se.printStackTrace();
        }
    }

    /**
     * 重繪方法，用於除了初始化頁面方法後的所有details繪製。
     */
    public void redraw() {
        try {
            this.rebarDetails.clear();
            this.sql = "SELECT * FROM rebar ORDER BY rebar_number;";
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
            closeResource();
            se.printStackTrace();
        }
    }

    /**
     * 方法，用於關閉資源
     */
    public void closeResource(){
        if (this.sql!= null) {
            this.sql = null;
        }
        if (this.rs != null) {
            try {
                this.rs.close();
                this.rs = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (this.stmt != null) {
            try {
                this.stmt.close();
                this.stmt = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (this.conn != null) {
            try {
                this.conn.close();
                this.conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}