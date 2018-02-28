package com.cnksi.app.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class ProMilestone extends KBaseModel<ProMilestone> {

    public static final ProMilestone me = new ProMilestone();


    @SuppressWarnings("rawtypes")
    @Override
    protected Class getCls() {
        return this.getClass();
    }

    public static boolean isAssocWithDevice(String flow) {
        return !(flow.contains("feasible_check") || flow.contains("arrive_check-count"));
    }

    public List<Record> findMilestonesBy(String projectId) {
        Map<String, String> flowchecks = SysConfig.getFlowChecks();

        String sql1 = String.format("select * from %s where enabled = 0 and pid = ? order by time", new Object[]{Task.me.getTableName()});
        final Map<String, Map<String, List<Task>>> taskGroups = Task.me.find(sql1, projectId)
                .stream()
                .collect(Collectors.groupingBy(x -> x.getStr("flow"), Collectors.groupingBy(x -> {
                    if (ProMilestone.isAssocWithDevice(x.getStr("type"))) {
                        return x.getStr("type") + x.getStr("devicetype");
                    } else {
                        return x.getStr("type");
                    }
                })));

        String sql2 = String.format("select * from %s where enabled = 0 and pid = ? order by plantime", new Object[]{me.tableName});
        Map<String, Map<String, List<ProMilestone>>> milstoneGroups = ProMilestone.me.find(sql2, projectId)
                .stream()
                .map(x -> x.put("_type", x.getStr("checktype").split("-")[0]))
                .collect(Collectors.groupingBy(x -> x.getStr("_type"), Collectors.groupingBy(x -> {
                    if (ProMilestone.isAssocWithDevice(x.getStr("checktype"))) {
                        return x.getStr("checktype") + x.getStr("devicetype");
                    } else {
                        return x.getStr("checktype");
                    }
                })));


        Map<String, String> typeMap = Db.find("select * from card_type where enabled = 0")
                .stream()
                .reduce(new HashMap<String, String>(), (acc, x) -> {
                    acc.put(x.getStr("key"), x.getStr("name"));
                    return acc;
                }, (x, y) -> x);

        for (Map.Entry<String, Map<String, List<Task>>> taskEntry : taskGroups.entrySet()) {
            String flow = taskEntry.getKey();
            if (!milstoneGroups.containsKey(flow)) {
                milstoneGroups.put(flow, new HashMap<String, List<ProMilestone>>());
            }
            Map<String, List<ProMilestone>> milestoneMap = milstoneGroups.get(flow);
            for (Map.Entry<String, List<Task>> entry : taskEntry.getValue().entrySet()) {
                if (!milestoneMap.containsKey(entry.getKey())) {
                    milestoneMap.put(entry.getKey(), new ArrayList<ProMilestone>());
                }
                String type = entry.getValue().get(0).getStr("type");
                String devicetype = entry.getValue().get(0).getStr("devicetype");
                List<ProMilestone> proMilestones = milestoneMap.get(entry.getKey());
                if (proMilestones.isEmpty()) {
                    if (ProMilestone.isAssocWithDevice(type)) {
                        proMilestones.add(new ProMilestone().set("name", typeMap.get(type)+ "-" + devicetype).set("checktype", type).set("devicetype", devicetype));
                    } else {
                        proMilestones.add(new ProMilestone().set("name", typeMap.get(type)).set("checktype", type).set("devicetype", devicetype));
                    }
                }
                proMilestones.get(0).put("_tasks", entry.getValue());
            }
        }
        final Map<String, Map<String, List<ProMilestone>>> milestones = milstoneGroups;


        return Db.find("select * from " + SysConfig.me.getTableName() + " where enabled = 0 and `type` = ? order by sort", "flow")
                .stream()
                .map(x -> {
                    return x.set("milestones", milestones.get(x.getStr("key")));
//                    String checks = '(' + flowchecks.get(x.getStr("key")) + ')';
//                    String sql = String.format("select * from %s where enabled = 0 and pid = ? and checktype in %s order by plantime", new Object[]{me.tableName, checks});
//                    List<ProMilestone> milestones = me.find(sql, new Object[]{projectId})
//                            .stream()
//                            .map(y -> {
//                                List<Task> tasks;
//                                if (y.getStr("checktype").contains("feasible_check")) {
//                                    String tasksql = String.format("select * from %s where enabled = 0 and pid = ? and type = ? order by time", new Object[]{Task.me.getTableName()});
//                                    tasks = Task.me.find(tasksql, new Object[]{projectId, y.getStr("checktype")});
//                                } else {
//                                    String tasksql = String.format("select * from %s where enabled = 0 and pid = ? and type = ? and devicetype = ? order by time", new Object[]{Task.me.getTableName()});
//                                    tasks = Task.me.find(tasksql, new Object[]{projectId, y.getStr("checktype"), y.getStr("devicetype")});
//                                }
//                                y.put("tasks", tasks);
//                                return y;
//                            }).collect(Collectors.toList());
//                    return x.set("milestones", milestones);
                }).collect(Collectors.toList());
    }
}
