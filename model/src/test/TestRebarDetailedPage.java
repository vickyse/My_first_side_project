package test;
import model.rebarDetailedPage;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;


/**
 * 測試rebarDetailedPage的所有方法以及scenario。
 * 再做任何測試之前，請保持rebar表格為原始數據也就是rebar_number只有3, 4, 5, 6, 7, 8, 10。
 */
public class TestRebarDetailedPage {

    /**
     * 測試rebarDetailedPage類別的初始化。
     */
    @Test
    public void testInitialiseDetailedPage() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // ByteArrayOutputStream是一個數組類別，用以承載原先打印到終端機上的輸出內容，並一個一個以字符的型態儲存。
        System.setOut(new PrintStream(outputStream));
        // System.setOut用來指定要將原本打印到終端機上的內容要轉為打印輸出到哪個PrintStream，
        // 在其參數指定outputStream就指定輸出到outputStream裡面。

        rebarDetailedPage test = new rebarDetailedPage();
        String testTerminalString = """
                連接至指定數據庫...
                實體化SQL語句...
                鋼筋編號: #3 價格為: 1.0(元/每公分深度)
                鋼筋編號: #4 價格為: 1.0(元/每公分深度)
                鋼筋編號: #5 價格為: 1.36(元/每公分深度)
                鋼筋編號: #6 價格為: 2.235(元/每公分深度)
                鋼筋編號: #7 價格為: 3.578(元/每公分深度)
                鋼筋編號: #8 價格為: 4.095(元/每公分深度)
                鋼筋編號: #10 價格為: 3.833(元/每公分深度)
                出車費為: 1000元
                """;
        // 必須加上\n的原因是因為被測試的方法中打印的方法是println，println本就帶有\n在最後面。
        Assert.assertEquals(testTerminalString, outputStream.toString());
        System.setOut(System.out);
        // 將輸出目的地改回終端機。
    }

    /**
     * 測試添加新鋼筋到資料庫內。
     * @throws SQLException SQL出錯時拋出。
     */
    @Test
    public void testAddNewRebar() throws SQLException {
        rebarDetailedPage test = new rebarDetailedPage();
        test.addNewRebar(15, 100.0f);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        test.redraw();
        String testTerminalString = """
                鋼筋編號: #3 價格為: 1.0(元/每公分深度)
                鋼筋編號: #4 價格為: 1.0(元/每公分深度)
                鋼筋編號: #5 價格為: 1.36(元/每公分深度)
                鋼筋編號: #6 價格為: 2.235(元/每公分深度)
                鋼筋編號: #7 價格為: 3.578(元/每公分深度)
                鋼筋編號: #8 價格為: 4.095(元/每公分深度)
                鋼筋編號: #10 價格為: 3.833(元/每公分深度)
                鋼筋編號: #15 價格為: 100.0(元/每公分深度)
                出車費為: 1000元
                """;
        Assert.assertEquals(testTerminalString, outputStream.toString());
        System.setOut(System.out);
    }

    /**
     * 測試新添加一個已經存在的鋼筋。
     * @throws SQLException SQL出錯時拋出。
     */
    @Test
    public void testAddNewRebarButAlreadyHad() throws SQLException {
        rebarDetailedPage test = new rebarDetailedPage();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        test.addNewRebar(3, 100.0f);
        //添加已存在的鋼筋。
        String testTerminalString = "此種鋼筋已存在。\n";
        Assert.assertEquals(testTerminalString, outputStream.toString());
        System.setOut(System.out);
    }


    /**
     * 測試更改既有鋼筋的資料。
     * @throws SQLException SQL出錯時拋出。
     */
    @Test
    public void testEditRebarDetails() throws SQLException {
        rebarDetailedPage test = new rebarDetailedPage();
        test.editRebarDetail(3, 2.0f);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        test.redraw();
        String testTerminalString = """
                鋼筋編號: #3 價格為: 2.0(元/每公分深度)
                鋼筋編號: #4 價格為: 1.0(元/每公分深度)
                鋼筋編號: #5 價格為: 1.36(元/每公分深度)
                鋼筋編號: #6 價格為: 2.235(元/每公分深度)
                鋼筋編號: #7 價格為: 3.578(元/每公分深度)
                鋼筋編號: #8 價格為: 4.095(元/每公分深度)
                鋼筋編號: #10 價格為: 3.833(元/每公分深度)
                出車費為: 1000元
                """;
        Assert.assertEquals(testTerminalString, outputStream.toString());
        System.setOut(System.out);
    }

    /**
     * 方法，測試更改不存在的鋼筋數據。
     * @throws SQLException SQL出錯時拋出。
     */
    @Test
    public void testEditRebarDetailsButNotFound() throws SQLException {
        rebarDetailedPage test = new rebarDetailedPage();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        test.editRebarDetail(1, 2.0f);
        String testTerminalString = "未知的鋼筋編號\n";
        Assert.assertEquals(testTerminalString, outputStream.toString());
        System.setOut(System.out);
    }

    /**
     * 測試刪除某個鋼筋的操作是否正確執行。
     */
    @Test
    public void testDeleteRebar() {
        rebarDetailedPage test = new rebarDetailedPage();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        test.deleteRebar(3);
        test.redraw();
        String testTerminalString = """
                正在刪除...
                完成！
                鋼筋編號: #4 價格為: 1.0(元/每公分深度)
                鋼筋編號: #5 價格為: 1.36(元/每公分深度)
                鋼筋編號: #6 價格為: 2.235(元/每公分深度)
                鋼筋編號: #7 價格為: 3.578(元/每公分深度)
                鋼筋編號: #8 價格為: 4.095(元/每公分深度)
                鋼筋編號: #10 價格為: 3.833(元/每公分深度)
                出車費為: 1000元
                """;
        Assert.assertEquals(testTerminalString, outputStream.toString());
        System.setOut(System.out);
    }

    /**
     * 測試刪除某個不存在的鋼筋。
     */
    @Test
    public void testDeleteRebarButNotFound() {
        rebarDetailedPage test = new rebarDetailedPage();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        test.deleteRebar(100);
        String testTerminalString = "此鋼筋不存在。\n";
        Assert.assertEquals(testTerminalString, outputStream.toString());
        System.setOut(System.out);
    }
    // redraw()不需要測試因為在前列任何有修改資料庫的方法測試中都已經有測試到了。

    /**
     * 測試是否可以修改出車費。
     */
    @Test
    public void testEditBasicRevenue() {
        rebarDetailedPage test = new rebarDetailedPage();
        test.editBasicRevenue(10);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        test.redraw();
        String testTerminalString = """
                鋼筋編號: #3 價格為: 1.0(元/每公分深度)
                鋼筋編號: #4 價格為: 1.0(元/每公分深度)
                鋼筋編號: #5 價格為: 1.36(元/每公分深度)
                鋼筋編號: #6 價格為: 2.235(元/每公分深度)
                鋼筋編號: #7 價格為: 3.578(元/每公分深度)
                鋼筋編號: #8 價格為: 4.095(元/每公分深度)
                鋼筋編號: #10 價格為: 3.833(元/每公分深度)
                出車費為: 10元
                """;
        Assert.assertEquals(testTerminalString, outputStream.toString());
        System.setOut(System.out);
    }

    /**
     * 測試正確執行SQL操作後是否關閉資源。
     */
    @Test
    public void testCloseResource() {
        rebarDetailedPage test = new rebarDetailedPage();
        test.closeResource();

        Assert.assertNull(test.sql);
        Assert.assertNull(test.rs);
        Assert.assertNull(test.stmt);
        Assert.assertNull(test.conn);
    }
}