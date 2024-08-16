package test;

import model.passwordPage;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * 測試passwordPage的所有方法以及scenario。
 */
public class TestPasswordPage {

    /**
     * 測試初始化屬性都是正確的。
     */
    @Test
    public void testInitialisePasswordPage() {
        passwordPage test = new passwordPage();

        Assert.assertFalse(test.access);
        Assert.assertEquals("f131078696", passwordPage.PASSWORD);
    }
}
