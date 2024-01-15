package test;

import model.selectWorkTypeDetailsPage;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * 測試selectWorkTypeDetailsPage的所有方法以及scenario。
 */
public class TestSelectWorkTypeDetailsPage {

    /**
     * 測試初始化文本內容。
     */
    @Test
    public void testInitialiseSelectWorkTypeDetailsPage() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        selectWorkTypeDetailsPage test = new selectWorkTypeDetailsPage();
        String testTerminalString = """
                植筋
                切割
                洗孔
                """;
        Assert.assertEquals(testTerminalString, outputStream.toString());
        System.setOut(System.out);
    }

    /**
     * 測試MoveUp()方法是否正確。
     */
    @Test
    public void testMoveUp() {
        selectWorkTypeDetailsPage test = new selectWorkTypeDetailsPage();
        test.moveUp();
        Assert.assertEquals(-1, (test.countForSelect % 3));
    }

    /**
     * 測試MoveDown()方法是否正確。
     */
    @Test
    public void testMoveDown() {
        selectWorkTypeDetailsPage test = new selectWorkTypeDetailsPage();
        test.moveDown();
        Assert.assertEquals(1, (test.countForSelect % 3));
    }

    /**
     * 測試moveUp()moveDown()方法執行後workTypesAndIsSelected是否正確運作。
     */
    @Test
    public void testUpdate() {
        selectWorkTypeDetailsPage test = new selectWorkTypeDetailsPage();
        test.moveUp(); // 可隨意更改
        test.update();

        Assert.assertTrue(test.workTypesAndIsSelected.get("洗孔"));
        Assert.assertFalse(test.workTypesAndIsSelected.get("植筋"));
        Assert.assertFalse(test.workTypesAndIsSelected.get("切割"));
        // 以上三列可以隨意根據測試情況更改。
    }
}
