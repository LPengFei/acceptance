package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;
import com.jfinal.plugin.activerecord.Db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class SysConfig extends BaseModel<SysConfig> {

    public static final SysConfig me = new SysConfig();

    static public Map<String, String> getFlowChecks() {
        return Db.find("select * from card_type where enabled = 0")
                .stream()
                .reduce(new HashMap<String, String>(), (acc, x) -> {
                    String flow = x.getStr("flow");
                    String key = "'" + x.getStr("key") + "'";
                    if (!acc.containsKey(flow)) {
                        acc.put(flow, key);
                    } else {
                        acc.put(flow, acc.get(flow) + "," + key);
                    }
                    return acc;
                }, (x, y) -> x);
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected Class getCls() {
        return this.getClass();
    }

    public List<SysConfig> findChecksForDropDown() {
        String sql = "select name, `key` as id from " + tableName + " where enabled = 0 and type = 'flow' order by sort";
        return find(sql);
    }

    public static List<SysConfig> getAllFlows() {
        String sql = "select * from " + me.tableName + " where enabled = 0 and type = 'flow' order by sort";
        return me.find(sql);
    }
}
