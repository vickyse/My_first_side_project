package model;
import java.sql.*;

public class cutDetailedPage extends rebarDetailedPage{
    public int cut_price_per_cm;

    /**
     * 初始化cutDetailedPage頁面。
     */
    public cutDetailedPage(){
        super();
    } // 初始化。

    @Override
    public void initialiseDetailedPage() {
        try {
            this.conn = DBConnector.connectToDB();
            System.out.println("實體化SQL語句...");
            this.stmt = this.conn.createStatement();
            this.sql = "SELECT * FROM cut ORDER BY price_per_cm;";
            this.rs = this.stmt.executeQuery(sql);

            int basicRevenue = 0;
            while (this.rs.next()) {
                cut_price_per_cm = this.rs.getInt("price_per_cm");
                basicRevenue = this.rs.getInt("basic_revenue");
            }

            System.out.println("切割: 1cm價格為" + cut_price_per_cm + "元");
            System.out.println("出車費為: " + basicRevenue + "元");
        } catch (SQLException se) {
            closeResource();
            se.printStackTrace();
        }
    }
    public void editCutPrice(int price) throws SQLException {
        try{
            this.sql = "UPDATE cut SET price_per_cm = " + price;
            System.out.println("正在修改...");
            this.stmt.executeUpdate(this.sql);
            System.out.println("完成！");
        } catch (SQLException e) {
            closeResource();
            e.printStackTrace();
        }
    }

    @Override
    public void redraw() {
        try {
            this.sql = "SELECT * FROM cut ORDER BY price_per_cm;";
            this.rs = this.stmt.executeQuery(sql);

            int basicRevenue = 0;
            while (this.rs.next()) {
                cut_price_per_cm = this.rs.getInt("price_per_cm");
                basicRevenue = this.rs.getInt("basic_revenue");
            }

            System.out.println("切割: 1cm價格為" + cut_price_per_cm + "元");
            System.out.println("出車費為: " + basicRevenue + "元");
        } catch (SQLException se) {
            closeResource();
            se.printStackTrace();
        }
    }
    //TODO 修改出車費。
}
