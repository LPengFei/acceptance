package com.cnksi.app.model;

import com.jfinal.plugin.activerecord.Db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("serial")
public class RelDeviceIssue extends KBaseModel<RelDeviceIssue> {

    public static final RelDeviceIssue me = new RelDeviceIssue();


    @SuppressWarnings("rawtypes")
    @Override
    protected Class getCls() {
        return this.getClass();
    }

    public static void removeIssueNotAssocWithPmsDevices(String id, String[] pmsDevices) {
        String cond = Stream.of(pmsDevices).map(x -> "?").collect(Collectors.joining(","));
        String sql = "update rel_device_issue set enabled = 1, remark = '没有被关联' where iid = ? and did not in (" + cond + ")";
        ArrayList<String> paras = new ArrayList<>(Arrays.asList(pmsDevices));
        paras.add(0, id);
        Db.update(sql, paras.toArray());
    }

    public static void assocIssueWithPmsDevices(String id, String[] pmsDevices) {
        removeIssueNotAssocWithPmsDevices(id, pmsDevices);
        Stream.of(pmsDevices)
                .forEach(x ->
                        Optional.ofNullable(me.findByPropertity(new String[]{"did", "iid"}, new Object[]{x, id}, Logical.AND))
                                .map(y -> y.set("enabled", 0).set("remark", "重新关联").update())
                                .orElseGet(() -> new RelDeviceIssue().set("remark", "新的关联").set("did", x).set("iid", id).save())
                );
    }
}
