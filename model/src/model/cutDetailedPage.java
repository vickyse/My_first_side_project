package model;

import java.sql.*;

public class cutDetailedPage extends rebarDetailedPage{
    private int cut_price_per_cm;

    /**
     * 初始化cutDetailedPage頁面。
     * 可能是用在VIEW。
     */
    public cutDetailedPage() {
        super();
    } // 初始化。

    /**
     * 同名方法，用於初始化頁面。
     * 可能用於VIEW。
     */
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

    /**
     * 方法，修改切割每公分價格。
     * @param price 欲修改價格。
     */
    //TODO : 之後記得添加若是參數資料型態不符規定時的處理
    public void editCutPrice(int price) {
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

    /**
     * 方法，修改切割工程的出車費。
     * @param newBasicRevenue 欲修改得出車費。
     */
    //TODO : 之後記得添加若是參數資料型態不符規定時的處理
    public void editBasicRevenue(int newBasicRevenue) {
        try {
            this.sql = "UPDATE cut SET basic_revenue =" + newBasicRevenue + " WHERE price_per_cm != -1;";
            System.out.println("正在修改...");
            this.stmt.executeUpdate(this.sql);
            System.out.println("完成！");
        } catch (SQLException se) {
            closeResource();
            se.printStackTrace();
        }
    }

    /**
     * 重繪切割的detailed page，用於使用其他增刪查改方法之後。
     * 可能是用在VIEW。
     */
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
}
