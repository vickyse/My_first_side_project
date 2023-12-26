import java.sql.*; // 導入JDBC。


/**
 * 定義try_JDBC。第一次嘗試JDBC，於2023/12/26
 */
public class try_JDBC {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    // 使用JDBC的時候都必須要有這一行。
    static final String DB_URL =
            "jdbc:mysql://127.0.0.1:3306/side_project?user=root";
    // 使用JDBC必須要指定連接的伺服器，也就是user=root這行，root即為MySQL中的伺服器名稱。
    // side_project則為此伺服器中的DB名稱。

    static final String USER = "root";
    static final String PASSWORD = "f131078696";
    //以上兩行為使用此數據庫的帳號以及密碼。
    //以上全部為使用JDBC將JAVA連接到mySQL的固定用法


    public static void main(String[] args) {

        Connection conn = null;
        // Connection為一個接口，表示JAVA與MySQL的"連接"這個實體，初始值為null，代表尚未連接。
        Statement stmt = null;
        // Statement為一個接口，表示用於MySQL查詢中的"語句"這個實體，初始值為nul，代表沒有要執行的語句。

        try {
            //註冊JDBC驅動
            Class.forName(JDBC_DRIVER);
            // Class是JAVA所有類的父類，forName()方法用於返回參數內的東西是屬於哪一個具體的"類"。
            // 這邊不需要再次宣告某個值來賦值返回值的原因在於，返回值本身不重要，
            // 重要的是在執行這個程式碼之後，JAVA已經撥出記憶體用來記憶JDBC_DRIVER所屬類別的所有
            // 屬性、方法等等以供後續使用，因此才有註冊的說法。

            //嘗試連接
            System.out.println("連接中...");
            conn = DriverManager.getConnection(DB_URL,USER, PASSWORD);
            // DriverManager是某一個類，他是管理一組數據庫的一個驅動程式類。
            // getConnection()方法接受這三個參數，用來連接參數指定的數據庫。

            //查詢
            System.out.println("實例化 SQL Statement...");
            stmt = conn.createStatement();
            // conn是Connection類別，方法createStatement()返回一個Statement類，用於查詢。
            String sql;
            sql = "SELECT * FROM rebar;";
            ResultSet rs = stmt.executeQuery(sql);
            // ResultSet類為查詢過後輸出的結果，可以把結果視為一字排開的可迭代結果
            // 必須依靠你對數據庫本身的了解，去正確迭代他，如以下。

            //處理sql結果
            while (rs.next()) {
                //通過字段檢索
                int rebar_number = rs.getInt("rebar_number");
                float price_per_cm = rs.getFloat("price_per_cm");
                // rebar表格中有int類別的鋼筋編號、以及float類別的一公分價格。
                // 因此你可以知道在這一字排開的結果中getInt()就會是編號，getFloat()就是
                // 一公分多少錢。

                //輸出數據
                System.out.print("鋼筋編號: " + rebar_number + " ");
                System.out.print("每公分價格為: " + price_per_cm);
                System.out.print("\n");
                // 正確迭代之後，你就能用文字來表述他們分別是什麼東西的數據。
            }
            //完成後關閉資源
            rs.close();
            stmt.close();
            conn.close();
            // 記得要反過來關閉，先開後關。
        }catch(SQLException se ) {
            //處理JDBC錯誤
            se.printStackTrace();
            // printStackTrace()是一個很重要的方法，用於打印出為什麼會一連串的發生此異常
            // 包括異常類型、資料、發生位置、以及異常的調用stack
            // 幾乎所有的exception都有這種方法。
        }catch(Exception e) {
            // 處理Class.forName錯誤
            e.printStackTrace();
        }finally {
            // 確保所有資源關閉。
            try{
                if (stmt != null) {
                    stmt.close();
                    // 若前面stmt沒正確關閉，這邊再關一次。
                }
            }catch (SQLException se2) {
                se2.printStackTrace();
            }
            try{
                if (conn != null) {
                    conn.close();
                    // 若前面conn沒正確關閉，這邊再關一次。
                }
            }catch (SQLException se3) {
                se3.printStackTrace();
            }
        }
    }
}