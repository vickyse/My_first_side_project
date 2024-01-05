package test;

import model.cutDetailedPage;
import model.rebarDetailedPage;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;

public class TestCutDetailedPage {

    /**
     * 測試cutDetailedPage類別的初始化。
     */
    @Test
    public void testInitialiseDetailedPage() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // ByteArrayOutputStream是一個數組類別，用以承載原先打印到終端機上的輸出內容，並一個一個以字符的型態儲存。
        System.setOut(new PrintStream(outputStream));
        // System.setOut用來指定要將原本打印到終端機上的內容要轉為打印輸出到哪個PrintStream，
        // 在其參數指定outputStream就指定輸出到outputStream裡面。

        cutDetailedPage test = new cutDetailedPage();
        String testTerminalString = """
                連接至指定數據庫...
                實體化SQL語句...
                切割: 1cm價格為28元
                出車費為: 2500元
                """;
        // 必須加上\n的原因是因為被測試的方法中打印的方法是println，println本就帶有\n在最後面。
        Assert.assertEquals(testTerminalString, outputStream.toString());
        System.setOut(System.out);
        // 將輸出目的地改回終端機。
    }

    /**
     * 測試更改切割每公分價格。
     * @throws SQLException
     */
    @Test
    public void testEditCutPrice() throws SQLException {
        cutDetailedPage test = new cutDetailedPage();
        test.editCutPrice(10);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        test.redraw();
        String testTerminalString = """
                切割: 1cm價格為10元
                出車費為: 2500元
                """;
        Assert.assertEquals(testTerminalString, outputStream.toString());
        System.setOut(System.out);
    }

    /**
     * 測試修改切割的出車費。
     */
    @Test
    public void testEditBasicRevenue() {
        cutDetailedPage test = new cutDetailedPage();
        test.editBasicRevenue(10);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        test.redraw();
        String testTerminalString = """
                切割: 1cm價格為10元
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
        cutDetailedPage test = new cutDetailedPage();
        test.closeResource();

        Assert.assertNull(test.sql);
        Assert.assertNull(test.rs);
        Assert.assertNull(test.stmt);
        Assert.assertNull(test.conn);
    }
}

