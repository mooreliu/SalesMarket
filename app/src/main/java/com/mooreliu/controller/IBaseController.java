package com.mooreliu.controller;

import android.content.Context;
import android.net.Uri;

import com.mooreliu.db.model.BaseModel;

import java.util.List;

/**
 * Created by mooreliu on 2015/9/15.
 */
public interface IBaseController {
    /**
     * 插入多条数据 @param context @param models @return
     */
    public int insert(Context context, List<? extends BaseModel> models);

    /**
     * 插入单条数据 @param context @param model @return
     */
    public Uri insert(Context context, BaseModel model);

    /**
     * @param context
     * @param id
     * @param tClass
     * @param <T>
     * @return
     */
    public <T extends BaseModel> T query(Context context, int id, Class<T> tClass);

    /**
     * 根据id删除数据
     *
     * @param context
     * @param id
     * @param tClass
     * @return
     */
    public boolean deleteById(Context context, int id, Class<? extends BaseModel> tClass);

    /**
     * 根据id更新数据
     *
     * @param context
     * @param id
     * @param model
     * @return
     */
    public boolean updateById(Context context, int id, BaseModel model);
}
