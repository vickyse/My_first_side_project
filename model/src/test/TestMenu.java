package test;

import model.menu;
import model.selectWorkTypeDetailsPage;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestMenu extends TestSelectWorkTypeDetailsPage{

    /**
     * 測試初始化文本內容。.
     */
    @Test
    public void testInitialiseSelectWorkTypeDetailsPage() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        menu test = new menu();
        String testTerminalString = """
                表單
                查看、修改數據
                離開
                """;
        Assert.assertEquals(testTerminalString, outputStream.toString());
        System.setOut(System.out);
    }

    /**
     * 測試moveUp()moveDown()方法執行後workTypesAndIsSelected是否正確運作。
     */
    @Test
    public void testUpdate() {
        menu test = new menu();
        test.moveUp(); // 可隨意更改
        test.update();

        Assert.assertTrue(test.workTypesAndIsSelected.get("離開"));
        Assert.assertFalse(test.workTypesAndIsSelected.get("表單"));
        Assert.assertFalse(test.workTypesAndIsSelected.get("查看、修改數據"));
        // 以上三列可以隨意根據測試情況更改。
    }
}
