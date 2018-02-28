package com.cnksi.app.model;

import com.jfinal.plugin.activerecord.Db;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("serial")
public class RelDeviceTask extends KBaseModel<RelDeviceTask> {

    public static final RelDeviceTask me = new RelDeviceTask();


    @SuppressWarnings("rawtypes")
    @Override
    protected Class getCls() {
        return this.getClass();
    }

    public static void removeTaskNotAssocWithPmsDevices(String id, String[] pmsDevices) {
        // 找出任务下面关联的所有设备
        String sql = "select rdi.* from rel_device_issue rdi, result_issue ri where rdi.enabled = 0 and ri.enabled = 0 and ri.id = rdi.iid and ri.tid = ?";
        List<String> dids = Db.find(sql, id)
                .stream()
                .map(x -> x.getStr("did"))
                .collect(Collectors.toList());
        // 和当前添加的设备
        dids.addAll(Stream.of(pmsDevices).collect(Collectors.toList()));
        // 去除重复后就是这个任务需要关联的设备
        Object[] devices = dids.stream().distinct().collect(Collectors.toList()).toArray();
        String cond = Stream.of(devices).map(x -> "?").collect(Collectors.joining(","));
        String sql1 = "update rel_device_task set enabled = 1, remark = '没有被关联' where tid = ? and did not in (" + cond + ")";
        ArrayList<Object> paras = new ArrayList<>(Arrays.asList(devices));
        paras.add(0, id);
        Db.update(sql1, paras.toArray());
    }

    public static void assocTaskWithPmsDevices(String id, String[] pmsDevices) {
        removeTaskNotAssocWithPmsDevices(id, pmsDevices);
        Stream.of(pmsDevices)
                .forEach(x ->
                        Optional.ofNullable(me.findByPropertity(new String[]{"did", "tid"}, new Object[]{x, id}, Logical.AND))
                                .map(y -> y.set("enabled", 0).set("remark", "重新关联").update())
                                .orElseGet(() -> new RelDeviceTask().set("remark", "新的关联").set("did", x).set("tid", id).save())
                );
    }
}
