package model;

import org.apache.commons.lang3.tuple.Pair;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 定義洗孔頁面。
 */
public class holeWashDetailedPage extends rebarDetailedPage{
    public static LinkedHashMap<Pair<Float, Integer>, Integer> holeWashDetails;
    static {
        holeWashDetails = new LinkedHashMap<>();
    }
    // 在holeWashDetailedPage初始化的時候，繼承父類的super必須第一個宣告，這會造成
    // holeWashDetailedPage的HashMap不是使用正確的數據結構(父類rebarDetailedPage的HashMap結構為
    // 簡單鍵值對，而holeWashDetailedPage的HashMap則為(Pair<>, value)結構)，因此我們將holeWashDetails
    // 這個鍵值對改為static，表示當你呼叫holeWashDetailedPage的當下他的HashMap就會被初始化(在super之前)。
    // 因此就可以正確迭代。

    /**
     * 初始化holeWashDetailedPage。
     */
    public holeWashDetailedPage() {
        super();
    }

    /**
     * 初始化holeWashDetailedPage。
     */
    //TODO : 之後記得添加若是參數資料型態不符規定時的處理
    @Override
    public void initialiseDetailedPage() {
        try {
            this.conn = DBConnector.connectToDB();
            System.out.println("實體化SQL語句...");
            this.stmt = this.conn.createStatement();
            this.sql = "SELECT * FROM hole_wash ORDER BY size, depth;";
            this.rs = this.stmt.executeQuery(sql);

            int basicRevenue = 0;

            while (this.rs.next()) {
                float holeSize = this.rs.getFloat("size");
                int depth = this.rs.getInt("depth");
                int price = this.rs.getInt("price");
                basicRevenue = this.rs.getInt("basic_revenue");

                Pair<Float, Integer> pairKey = Pair.of(holeSize, depth);
                holeWashDetails.put(pairKey, price);
            }
            for (Map.Entry<Pair<Float, Integer>, Integer> keyValue : holeWashDetails.entrySet()) {
                float keySize = keyValue.getKey().getLeft();
                int keyDepth = keyValue.getKey().getRight();
                int value = keyValue.getValue();

                System.out.println("孔的口徑: " + keySize + "吋 " + "孔的深度: " + keyDepth + "cm " + "價格為:" + value + "元");
            }
            System.out.println("出車費為: " + basicRevenue + "元");
        } catch (SQLException se) {
            closeResource();
            se.printStackTrace();
        }
    }

    /**
     * 方法，添加一個新的孔報價。
     * @param holeSize 孔的口徑。
     * @param depth 孔的深度。
     * @param price 這個規格的價格。
     */
    //TODO : 之後記得添加若是參數資料型態不符規定時的處理
    public void addNewHoleDetails(float holeSize, int depth, int price) {
        if (!holeWashDetails.containsKey(Pair.of(holeSize, depth))) {
            // 不加上this的原因是因為他是靜態屬性。
            try {
                this.sql = "INSERT INTO hole_wash(size, depth, price, basic_revenue) VALUES" + "(" +
                        holeSize + ", " + depth + ", " + price + ", 1000);";
                this.stmt.executeUpdate(this.sql);
            } catch (SQLException e) {
                closeResource();
                e.printStackTrace();
            }
        } else {
            System.out.println("已經有此孔的報價！");
        }
    }

    /**
     * 方法，刪除指定的孔。
     * @param holeSize 孔的口徑。
     * @param depth 孔的深度。
     */
    //TODO : 之後記得添加若是參數資料型態不符規定時的處理
    public void deleteHoleDetails(float holeSize, int depth) {
        if (holeWashDetails.containsKey(Pair.of(holeSize, depth))) {
            try {
                this.sql =
                        "DELETE FROM hole_wash WHERE (size, depth) = (" + holeSize + ", " + depth + ");";
                this.stmt.executeUpdate(this.sql);
            } catch (SQLException se) {
                closeResource();
                se.printStackTrace();
            }
        } else {
            System.out.println("不存在的孔。");
        }
    }

    /**
     * 方法，用於查詢只給定孔的口徑，查詢所有該孔口徑報價。
     * @param holeSize 孔的口徑。
     */
    public void getHoleDetails(float holeSize) {
        try {
            holeWashDetails.clear();
            this.sql = "SELECT * FROM  hole_wash WHERE size = " + holeSize;
            this.stmt.executeQuery(this.sql);
            this.rs = this.stmt.executeQuery(sql);

            if (this.rs.next()) {
            // 判斷邏輯為，若SQL端點有查詢結果(rs.next為True)，代表有此口徑的孔，可以開始迭代取出結果，若無結果，
            // 則表示SQL查無此孔的口徑，就打印出不存在的訊息。(超聰明)
                do {
                    float holeSizeInResult = this.rs.getFloat("size");
                    int depth = this.rs.getInt("depth");
                    int price = this.rs.getInt("price");

                    Pair<Float, Integer> pairKey = Pair.of(holeSizeInResult, depth);
                    holeWashDetails.put(pairKey, price);
                } while (this.rs.next());
                // 這邊很重要，若不使用do-while循環，我們將會遺失第一筆口徑為holeSize的資料，因為rs.next()方法
                // 會將光標移動至rs中的下一個目標。也就是說if (this.rs.next())執行後會將光標移至rs中第二筆資料
                // 開始迭代。因此改成do-while循環就會變成我在執行while循環之前先將if (this.rs.next())執行後當
                // 下的那一筆也就是第一筆資料先推進去holeWashDetails之後，在開始迭代rs中的結果。
                for (Map.Entry<Pair<Float, Integer>, Integer> keyValue : holeWashDetails.entrySet()) {
                    float keySize = keyValue.getKey().getLeft();
                    int keyDepth = keyValue.getKey().getRight();
                    int value = keyValue.getValue();

                    System.out.println("孔的口徑: " + keySize + "吋 " + "孔的深度: " + keyDepth + "cm " + "價格為:" + value + "元");
                }
            } else {
                System.out.println("不存在的口徑");
            }
        } catch (SQLException e) {
            closeResource();
            e.printStackTrace();
        }
    }

    /**
     * 方法，用於查詢給定口徑以及深度的報價。
     * @param holeSize
     * @param depth
     */
    public void getHoleDetails(float holeSize, int depth) {
        try {
            holeWashDetails.clear();
            this.sql = "SELECT * FROM  hole_wash WHERE (size, depth) = " +
                    "(" + holeSize + ", " + depth + ")";
            this.stmt.executeQuery(this.sql);
            this.rs = this.stmt.executeQuery(sql);

            if (this.rs.next()) {
                float holeSizeInResult = this.rs.getFloat("size");
                int holeDepth = this.rs.getInt("depth");
                int price = this.rs.getInt("price");

                Pair<Float, Integer> pairKey = Pair.of(holeSizeInResult, depth);
                holeWashDetails.put(pairKey, price);

                for (Map.Entry<Pair<Float, Integer>, Integer> keyValue : holeWashDetails.entrySet()) {
                    float keySize = keyValue.getKey().getLeft();
                    int keyDepth = keyValue.getKey().getRight();
                    int value = keyValue.getValue();

                    System.out.println("孔的口徑: " + keySize + "吋 " + "孔的深度: " + keyDepth + "cm " + "價格為:" + value + "元");
                }
            } else {
                System.out.println("不存在的口徑");
            }
        } catch (SQLException e) {
            closeResource();
            e.printStackTrace();
        }
    }

    /**
     * 方法，修改指定口徑及深度的孔的報價。
     */
    public void editHolePrice(float holeSize, int depth, int price) {
        if (holeWashDetails.containsKey(Pair.of(holeSize, depth))) {
            try {
                this.sql = "UPDATE hole_wash SET price = " + price +
                        " WHERE (size, depth) = (" + holeSize + ", "  + depth + ");";
                this.stmt.executeUpdate(this.sql);
            } catch (SQLException se) {
                closeResource();
                se.printStackTrace();
            }
        } else {
            System.out.println("不存在的孔。");
        }
    }

    /**
     * 方法，重寫父類的同名方法。
     */
    @Override
    public void redraw() {
        try {
            holeWashDetails.clear();
            this.sql = "SELECT * FROM hole_wash ORDER BY size, depth;";
            this.rs = this.stmt.executeQuery(sql);

            int basicRevenue = 0;

            while (this.rs.next()) {
                float holeSize = this.rs.getFloat("size");
                int depth = this.rs.getInt("depth");
                int price = this.rs.getInt("price");
                basicRevenue = this.rs.getInt("basic_revenue");

                Pair<Float, Integer> pairKey = Pair.of(holeSize, depth);
                holeWashDetails.put(pairKey, price);
            }
            for (Map.Entry<Pair<Float, Integer>, Integer> keyValue : holeWashDetails.entrySet()) {
                float keySize = keyValue.getKey().getLeft();
                int keyDepth = keyValue.getKey().getRight();
                int value = keyValue.getValue();

                System.out.println("孔的口徑: " + keySize + "吋 " + "孔的深度: " + keyDepth + "cm " + "價格為:" + value + "元");
            }
            System.out.println("出車費為: " + basicRevenue + "元");
        } catch (SQLException se) {
            closeResource();
            se.printStackTrace();
        }
    }

    public static void main(String[] args) {
        holeWashDetailedPage test = new holeWashDetailedPage();
        test.editHolePrice(1, 15, 10);
    }
}
