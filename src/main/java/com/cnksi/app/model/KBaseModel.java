package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by modun on 03/11/2016.
 */
public abstract class KBaseModel<M extends KBaseModel<M>> extends BaseModel<M> {
    @Override
    public boolean save() {
        if (this.get(getPkName()) == null) {
            this.set(getPkName(), getPrimarykey());
        }
        return super.save();
    }

    /**
     * 添加 key value便于流式操作
     * @return value
     */
    public Object putFetch(String key, Object value) {
        super.put(key, value);
        return value;
    }

    /**
     * 获取系统主键
     * @return 系统主键
     */
    public static String getPrimarykey() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmssSSS", Locale.CHINA);
        Random random = new Random();
        return formatter.format(new Date()) + String.valueOf(10000 + random.nextInt(89999));
    }

    public List<M> findAll() {
        String sql = String.format("select * from %s where enabled = 0 order by %s asc", new Object[]{this.tableName, this.pkName});
        return this.find(sql);
    }
    public List<M> findListByPropertity(String propertity, Object value) {
        String sql = String.format("select * from %s where enabled = 0 and %s = ? ", new Object[]{this.tableName, propertity});
        return this.find(sql, new Object[]{value});
    }
}
