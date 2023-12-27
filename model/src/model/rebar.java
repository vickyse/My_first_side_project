package model;

import java.util.HashMap;

/**
 * 定義植筋、鋼筋。
 */
public class rebar {

    public int rebarNumber; // 鋼筋編號。
    public int depth; // 此鋼筋被植入多深，可能改為double。
    public int amountPlanted; // 共植入幾隻。
    public double pricePerCm; // 植入此鋼筋每公分多少元。

    //初始化鋼筋。
    public rebar(int rebarNumber, int depth, int amountPlanted) {
        this.rebarNumber = rebarNumber;
        this.depth = depth;
        this.amountPlanted = amountPlanted;

        //TODO
        // this.pricePerCm(使用SQL)來查詢。
    }

    /**TODO
     * 方法，用於重新設置此鋼筋的編號。(使用SQL)
     * @param theNumber 欲修改的鋼筋編號
     */
    public void setRebarNumber(int theNumber) {}

    /**TODO
     * 方法，返回此鋼筋的編號。(使用SQL)
     * @return 鋼筋編號
     */
    public int getRebarNumber() {
        return this.rebarNumber;
    }

    /**
     * 方法，設置此鋼筋被植入多少公分，the_depth可能要修改為double。
     * @param theDepth 深度。
     */
    public void setDepth(int theDepth) {
        this.depth = theDepth;
    }

    /**
     * 方法，返回此鋼筋被植入多少公分。
     * @return 深度
     * 可能用不到。
     */
    public int getDepth() {
        return this.depth;
    }

    /**
     * 方法，透過參數鋼筋編號來設置此鋼筋一公分多少錢，用於初始化鋼筋時自動獲取價格。
     * @param rebarNumber 鋼筋編號，透過方法內的HashMap來獲取價格。
     */
    public double setPricePerCm(int rebarNumber) {
        return this.rebarNumberAndPrice.get(rebarNumber);
    }

    /**
     * 方法，返回此鋼筋植入一公分多少錢。
     * @return 一公分多少錢
     */
    public double getPricePerCm() {
        return this.pricePerCm;
    }

    /**
     * 方法，用於計算師傅在此種鋼筋之下賺取多少收入。
     * @return 收入
     */
    public double getRevenue() {
        return this.pricePerCm * this.depth * this.amountPlanted;
    }

    /**
     * 方法，用於修改已存在或是新添加鋼筋編號以及其價格。
     * @param rebarNumber 鋼筋編號，若已存在在HashMap中為修改，否則為新增。
     * @param thePrice 欲修改(新增)的價格。
     */
    public void changeOrAddRebar(int rebarNumber, double thePrice) {
        this.rebarNumberAndPrice.put(rebarNumber, thePrice);
        // HashMap.put()會自動檢測本身是否有給定參數的鍵，若有為修改，無則為新增。
    }

    /**
     * 方法，設置此鋼筋被植入了多少支。
     * @param amountPlanted 植入數量。
     */
    public void setAmountPlanted(int amountPlanted) {
        this.amountPlanted = amountPlanted;
    }

    /**
     * 方法，返回此鋼筋被植入多少支。
     * @return 數量。
     */
    public int getAmountPlanted() {
        return this.amountPlanted;
    }
}
