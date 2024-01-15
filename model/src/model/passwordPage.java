package model;

import java.util.Objects;
import java.util.Scanner;

/**
 * 定義輸入密碼的介面。
 */
public class passwordPage {
    public static final String PASSWORD = "f131078696";
    // 進入系統的密碼。
    public boolean accessPass;
    // 是否通過密碼測試。
    public Scanner passwordInputScanner;
    // 輸入密碼的scanner，用於model測試，GUI階段可能會改為視窗輸入。

    /**
     * 初始化。
     * 可能是用在VIEW。
     */
    public passwordPage() {
        this.accessPass = false;
        this.passwordInputScanner = new Scanner(System.in);
        // 用於model。
        this.passwordCheckInModel();
    }

    /**
     * 定義passwordCheck，用於GUI(有參數，因為從已從視窗中獲取用戶輸入的密碼)
     * @param passWordInput 視窗中用戶輸入的密碼。
     */
    public void passwordCheck(String passWordInput) {}

    /**
     * 定義passwordCheckInModel，用於model。
     */
    public void passwordCheckInModel() {
        while (!this.accessPass) {
            System.out.print("請輸入密碼: ");
            String passwordInput = this.passwordInputScanner.next();
            if (Objects.equals(passwordInput, PASSWORD)) {
                System.out.println("密碼正確");
                this.accessPass = true;
                this.passwordInputScanner.close();
            } else {
                System.out.println("錯誤的密碼");
            }
        }
    }
}
