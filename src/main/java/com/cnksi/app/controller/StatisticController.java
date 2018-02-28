package com.cnksi.app.controller;

import com.cnksi.app.model.*;
import com.cnksi.kconf.controller.KController;
import com.cnksi.kcore.web.KWebQueryVO;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class StatisticController extends KController {

    public StatisticController() {
        super(ProProject.class);
    }

    public void index() {
        setAttr("projectId", ProProject.me.findAll().get(0).getStr("id"));
        render("index.jsp");
    }


    public void projectList() {
        KWebQueryVO queryParam = super.doIndex();

        //查询项目名称、开始时间（以第一次可研时间为准，子查询得出）、结束时间（以最后一次结束时间为准，子查询得出）。
        String sql = "select *,(select `time` from task where pid = p.id and enabled = 0 order by `time` asc limit 1) as starttime,(select `time` from task where pid = p.id and enabled = 0 and flow = 'start_check' order by `time` desc limit 1) as endtime from pro_project p where p.enabled = 0";
        List<Record> records = Db.find(sql);

        setAttr("list", records);

        render("projectList.jsp");
    }

    public void total() {
        setAttr("flows", JsonKit.toJson(
                SysConfig.getAllFlows()
                        .stream()
                        .map(x -> new SysConfig().put("key", x.getStr("key")).put("name", x.getStr("name")))
                        .collect(Collectors.toList())
        ));
        setAttr("devices", JsonKit.toJson(
                DeviceType.getAllDevices()
                        .stream()
                        .map(x -> x.getStr("name"))
                        .collect(Collectors.toList())
        ));

        List<ResultIssue> issues = ResultIssue.getIssuesByProjectId(getPara("projectId"));
        setAttr("issuesMap", JsonKit.toJson(
                issues.stream()
                        .map(x -> new ResultIssue().put("flow", x.getStr("flow")).put("status", x.getStr("status")))
                        .collect(Collectors.groupingBy(x -> x.getStr("flow")))
        ));
        setAttr("devicesMap", JsonKit.toJson(
                issues.stream()
                        .filter(x -> x.getStr("_devicename") != null)
                        .map(x -> new ResultIssue()
                                .put("_devicename", x.getStr("_devicename"))
                                .put("status", x.getStr("status"))
                                .put("isimport", x.getInt("isimport") == 1))
                        .collect(Collectors.groupingBy(x -> x.getStr("_devicename")))
        ));
        setAttr("projectId", getPara("projectId"));

        render("totalStatistics.jsp");
    }

    public void chart() {
        render("chart.jsp");
    }

    public void projectStatistics() {
        setAttr("flows", JsonKit.toJson(
                SysConfig.getAllFlows()
                        .stream()
                        .map(x -> new SysConfig().put("key", x.getStr("key")).put("name", x.getStr("name")))
                        .collect(Collectors.toList())
        ));
        setAttr("devices", JsonKit.toJson(
                DeviceType.getAllDevices()
                        .stream()
                        .map(x -> x.getStr("name"))
                        .collect(Collectors.toList())
        ));

        List<ResultIssue> issues = ResultIssue.getIssuesByProjectId(getPara("projectId"));
        setAttr("issuesMap", JsonKit.toJson(
                issues.stream()
                        .map(x -> new ResultIssue().put("flow", x.getStr("flow")).put("status", x.getStr("status")))
                        .collect(Collectors.groupingBy(x -> x.getStr("flow")))
        ));
        setAttr("devicesMap", JsonKit.toJson(
                issues.stream()
                        .filter(x -> x.getStr("_devicename") != null)
                        .map(x -> new ResultIssue()
                                .put("_devicename", x.getStr("_devicename"))
                                .put("status", x.getStr("status"))
                                .put("isimport", x.getInt("isimport") == 1))
                        .collect(Collectors.groupingBy(x -> x.getStr("_devicename")))
        ));
//        setAttr("importantMap", JsonKit.toJson(
//                ResultIssueImportant.getAllByProjectId(getPara("projectId"))
//                .stream()
//                .map(x -> new ResultIssueImportant().put("name", x.getStr("devicetype")))
//                .collect(Collectors.groupingBy(x -> x.getStr("name"), Collectors.counting()))
//        ));
        setAttr("projectId", getPara("projectId"));
        render("projectStatistics.jsp");
    }
}
