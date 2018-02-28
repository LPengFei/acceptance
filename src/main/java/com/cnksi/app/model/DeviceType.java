package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

import java.util.List;

@SuppressWarnings("serial")
public class DeviceType extends KBaseModel<DeviceType> {

    public static final DeviceType me = new DeviceType();


    @SuppressWarnings("rawtypes")
    @Override
    protected Class getCls() {
        return this.getClass();
    }

    public List<DeviceType> findAllForDropDown() {
        String sql = "select id, name from " + tableName + " where enabled=0 order by type,sort";
        return find(sql);
    }
    public List<DeviceType> findAllForSearch(boolean isfilter) {
        String sql = "select name as id, name from " + tableName + " where enabled=0 order by type,sort";
        List<DeviceType> deviceTypes = DeviceType.me.find(sql);
        if ( isfilter ) {
            deviceTypes.add(0, new DeviceType().set("name", "无条件").set("id", ""));
        }
        return deviceTypes;
    }

    public static List<DeviceType> getAllDevices() {
        return me.find("select * from device_type where enabled = 0 order by sort");
    }
}
