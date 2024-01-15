package model;

import java.util.*;

/**
 * 定義選擇工程種類的頁面。
 */
public class selectWorkTypeDetailsPage {

    public LinkedHashMap<String, Boolean> workTypesAndIsSelected;
    public int countForSelect;

    /**
     * 呼叫此class的初始化。
     * 可能是用在VIEW。
     */
    public selectWorkTypeDetailsPage() {
        this.workTypesAndIsSelected = new LinkedHashMap<>();
        this.countForSelect = 0;
        this.initialiseSelectWorkTypeDetailsPage();
    }

    /**
     * 內部初始化。
     * 可能是用在VIEW。
     */
    public void initialiseSelectWorkTypeDetailsPage() {
        this.workTypesAndIsSelected.put("植筋", false);
        this.workTypesAndIsSelected.put("切割", false);
        this.workTypesAndIsSelected.put("洗孔", false);
        this.update();
        Set<String> workTypes = this.workTypesAndIsSelected.keySet();
        for (String workType : workTypes) {
            System.out.println(workType);
        }
    }

    /**
     * 定義moveUp用於選擇工程(具體上是利用countForSelect來決定哪一種工程被選取。)。
     */
    public void moveUp() {
        this.countForSelect  = this.countForSelect - 1;
    }

    /**
     * 定義moveDown用於選擇工程(具體上是利用countForSelect來決定哪一種工程被選取。)。
     */
    public void moveDown() {
        this.countForSelect = this.countForSelect + 1;
    }

    /**
     * 在moveUp或是moveDown之後必須執行這個以更新workTypesAndIsSelected。
     */
    public void update() {
        if ((this.countForSelect % 3) == 0) {
            this.workTypesAndIsSelected.put("植筋", true);
            this.workTypesAndIsSelected.put("切割", false);
            this.workTypesAndIsSelected.put("洗孔", false);
        } else if ((this.countForSelect % 3) == 1 || (this.countForSelect % 3) == -2) {
            this.workTypesAndIsSelected.put("植筋", false);
            this.workTypesAndIsSelected.put("切割", true);
            this.workTypesAndIsSelected.put("洗孔", false);
        } else if ((this.countForSelect % 3) == 2 || (this.countForSelect % 3) == -1) {
            this.workTypesAndIsSelected.put("植筋", false);
            this.workTypesAndIsSelected.put("切割", false);
            this.workTypesAndIsSelected.put("洗孔", true);
        }
    }

    // TODO: redraw()。
}