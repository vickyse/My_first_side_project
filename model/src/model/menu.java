package model;

import java.util.LinkedHashMap;
import java.util.Set;

public class menu extends selectWorkTypeDetailsPage{

    /**
     * 覆寫父類同名方法。
     * 可能是用在VIEW。
     */
    @Override
    public void initialiseSelectWorkTypeDetailsPage() {
        this.workTypesAndIsSelected.put("表單", false);
        this.workTypesAndIsSelected.put("查看、修改數據", false);
        this.workTypesAndIsSelected.put("離開", false);
        this.update();
        Set<String> workTypes = this.workTypesAndIsSelected.keySet();
        for (String workType : workTypes) {
            System.out.println(workType);
        }
    }

    /**
     * 覆寫父類同名方法。
     */
    @Override
    public void update() {
        if ((this.countForSelect % 3) == 0) {
            this.workTypesAndIsSelected.put("表單", true);
            this.workTypesAndIsSelected.put("查看、修改數據", false);
            this.workTypesAndIsSelected.put("離開", false);
        } else if ((this.countForSelect % 3) == 1 || (this.countForSelect % 3) == -2) {
            this.workTypesAndIsSelected.put("表單", false);
            this.workTypesAndIsSelected.put("查看、修改數據", true);
            this.workTypesAndIsSelected.put("離開", false);
        } else if ((this.countForSelect % 3) == 2 || (this.countForSelect % 3) == -1) {
            this.workTypesAndIsSelected.put("表單", false);
            this.workTypesAndIsSelected.put("查看、修改數據", false);
            this.workTypesAndIsSelected.put("離開", true);
        }
    }

    //TODO: redraw()。
}
