package com.mooreliu.controller;

/**
 * Created by mooreliu on 2015/9/16.
 */
public interface IShoppingChartController {
    /**
     * @param user_id 用户ID @param merchandise_id 商品ID @param merchandise_number 购买商品数量
     */
    public boolean insertToShoppingChartDb(int user_id, int merchandise_id, int merchandise_number);

    /**
     * @param user_id            用户ID
     * @param merchandise_id     商品ID
     * @param merchandise_number 购买商品数量
     */
    public void newShoppingChartItem(int user_id, int merchandise_id, int merchandise_number);

}