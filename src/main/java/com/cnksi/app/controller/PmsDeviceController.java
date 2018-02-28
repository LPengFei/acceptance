package com.cnksi.app.controller;

import com.cnksi.app.model.*;
import com.cnksi.kconf.controller.KController;
import com.cnksi.kcore.web.KWebQueryVO;
import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class PmsDeviceController extends KController {

    public PmsDeviceController() {
        super(PmsDevice.class);
    }

    public void index() {

        KWebQueryVO queryParam = super.doIndex();

        setAttr("page", PmsDevice.me.paginate(queryParam));

        render("index.jsp");
    }

    public void list() {

        KWebQueryVO queryParam = super.doIndex();

        setAttr("page", PmsDevice.me.paginate(queryParam));

        render("list.jsp");

    }


    public void edit() {

        super.doEdit();

        String idValue = getPara("id", getPara());
        PmsDevice record = null;
        if (idValue != null) {
            record = PmsDevice.me.findById(idValue);
        } else {
            record = new PmsDevice();

        }
        setAttr("record", record);

        render(formJsp);
    }


    public void save() {
        PmsDevice record = getModel(PmsDevice.class, "record");
        if (record.get("id") != null) {
            record.update();
        } else {
            record.save();
        }

        bjuiAjax(200, true);
    }


    public void delete() {
        PmsDevice record = PmsDevice.me.findById(getPara());
        if (record != null) {
            record.set("enabled", 1).update();
            bjuiAjax(200);
        } else {
            bjuiAjax(300);
        }

    }


    public void export() throws IOException {
        KWebQueryVO queryParam = super.doIndex();
        Page<PmsDevice> p = PmsDevice.me.paginate(queryParam);
        String xlsid = getPara("xlsid", "-1");
        super.export(xlsid, p.getList());
    }


    public void importxlsed() {
        String errorFile = "", msg = "";
        try {
            ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
            if (!result.isVerfiyFail()) {
                for (Map<String, Object> map : result.getList()) {
                    PmsDevice record = new PmsDevice();
                    record.put(map);
                    record.save();
                }
            } else {
                errorFile = result.getSaveFile().getName();
            }
        } catch (ExcelImportException e) {
            msg = "导入错误：" + e.getMessage();
        }
        bjuiAjax(StrKit.notBlank(msg) ? 300 : 200).put("errorFile", errorFile);

    }

    public void pmsDeviceTree() {
        String id = getPara("id");
        String hierarchy = getPara("hierarchy");
        renderJson(PmsBdz.getPmsTreeJson(null, id, hierarchy));
    }

    public void issuesAndTasks() {
        String id = getPara("pms_deviceId");
        setAttr("pms_deviceId", id);
        render("issuesAndTasks.jsp");
    }

    public void issues() {
        String id = getPara("pms_deviceId");
        String flow = getPara("flow");

        String sql = String.format("select ri.* from rel_device_issue as rdi, result_issue as ri where rdi.enabled = 0 and ri.enabled = 0 and rdi.iid = ri.id and rdi.did = ? and ri.flow = ?");
        List<Record> records = Db.find(sql, id, flow);
        setAttr("issues", records);
        render("issues.jsp");
    }

    public void tasks() {
        String id = getPara("pms_deviceId");
        String flow = getPara("flow");

        String sql = String.format("select t.* from rel_device_task as rdt, task as t where rdt.enabled = 0 and t.enabled = 0 and rdt.tid = t.id and rdt.did = ? and t.flow = ?");
        List<Record> records = Db.find(sql, id, flow);
        setAttr("tasks", records);
        render("tasks.jsp");
    }

    public void needAssociate() {
        String issueSql = "select * from result_issue as ri where enabled = 0 and not exists(select * from rel_device_issue as rdi where rdi.enabled = 0 and ri.id = rdi.id)";
        String taskSql = "select * from task as t where enabled = 0 and not exists(select * from rel_device_task as rdt where rdt.enabled = 0 and t.id = rdt.id)";
        Stream.of(Db.find(issueSql).stream(), Db.find(taskSql).stream())
                .flatMap(x -> x)
                .findAny()
                .map(x -> {
                    bjuiAjax(200, false);
                    return x;
                })
                .orElseGet(() -> {
                    bjuiAjax(300, false);
                    return new Record();
                });
    }

    public void associateDialog() {
        render("associateDialog.jsp");
    }

    public void associate() {
        String issueSql = "select ri.*, min(rdi.enabled) as _e from result_issue ri left join rel_device_issue rdi on ri.id = rdi.iid where ri.enabled = 0 group by ri.id having _e = 1 or _e is null";
        String taskSql = "select t.*, min(rdt.enabled) as _e from task t left join rel_device_task rdt on t.id = rdt.tid where t.enabled = 0 group by t.id having _e = 1 or _e is null";

        setAttr("issues", Db.find(issueSql));
        setAttr("tasks", Db.find(taskSql));

        render("associate.jsp");
    }

    public void associateTree() {
        String issueId = getPara("issueId");
        String taskId = getPara("taskId");
        setAttr("issueId", issueId);
        setAttr("taskId", taskId);
        setAttr("notLoad", getParaToBoolean("notLoad"));
        render("associateTree.jsp");
    }

    @Before(Tx.class)
    public void saveAssociate() {
        Optional.ofNullable(getParaValues("pms_devices"))
                .ifPresent(x -> {
                    Optional.ofNullable(getPara("issueId"))
                            .filter(y -> y.length() > 0)
                            .ifPresent(y -> {
                                RelDeviceIssue.assocIssueWithPmsDevices(y, x);
                                RelDeviceTask.assocTaskWithPmsDevices(ResultIssue.me.findById(y).getStr("tid"), x);
                            });

                    Optional.ofNullable(getPara("taskId"))
                            .filter(y -> y.length() > 0)
                            .ifPresent(y -> RelDeviceTask.assocTaskWithPmsDevices(y, x));
                });
        refreshAssociateList();
    }

    private void refreshAssociateList() {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("statusCode", 200);
        resultMap.put("message", "操作成功");
        resultMap.put("closeCurrent", true);
        resultMap.put("tabid", "pmsdevice-associate-list");
        renderJson(resultMap);
    }

    public void pmsDeviceProps() {
        String deviceId = getPara("deviceId");
        String pms_deviceId = getPara("pms_deviceId");
        setAttr("deviceId", deviceId);
        setAttr("pms_deviceId", pms_deviceId);

        String sql = "SELECT df.*, (select value from device_field_value dfv where dfv.dtid = df.dtid AND dfv.did = ? AND dfv.enabled = 0 AND df.name = dfv.name AND df.type = dfv.type) as value FROM device_field df WHERE df.enabled = 0 AND df.dtid = ?";
        setAttr("props", DeviceField.me.find(sql, pms_deviceId, deviceId)
                .stream()
                .collect(Collectors.groupingBy(x -> x.getStr("type"))));

        render("pms_device_props.jsp");
    }

    @Before(Tx.class)
    public void saveProps() {
        String deviceId = getPara("deviceId");
        String pms_deviceId = getPara("pms_deviceId");
        Map<String, String> runtime = getParamMap("runtime");
        Map<String, String> physics = getParamMap("physics");
        Map<String, Map<String, String>> type_map = new HashMap<>();
        type_map.put("runtime", runtime);
        type_map.put("physics", physics);



        Optional.ofNullable(PmsDevice.me.findById(pms_deviceId))
                .ifPresent(x ->
                        Optional.ofNullable(DeviceType.me.findById(deviceId)).ifPresent(y ->
                                x.set("dtid", deviceId)
                                        .set("one", "one".equals(y.getStr("type")) ? "yes" : "no")
                                        .set("second", "second".equals(y.getStr("type")) ? "yes" : "no")
                                        .update())
                );

        DeviceFieldValue.me.find("select * from device_field_value where enabled = 0 and did = ? and dtid = ?", pms_deviceId, deviceId)
                .forEach(x -> Optional.ofNullable(type_map.get(x.getStr("type")).get(x.getStr("name")))
                        .ifPresent(y -> {
                            x.set("value", y).update();
                            type_map.get(x.getStr("type")).remove(x.getStr("name"));
                        }));

        Optional.ofNullable(deviceId).ifPresent(x ->
                Optional.ofNullable(pms_deviceId).ifPresent(y ->
                        type_map.entrySet().forEach(z ->
                                z.getValue().entrySet().forEach(w ->
                                        new DeviceFieldValue()
                                                .set("did", y)
                                                .set("dtid", x)
                                                .set("type", z.getKey())
                                                .set("name", w.getKey())
                                                .set("value", w.getValue())
                                                .save()))
                ));

        bjuiAjax(200, false);
    }

    private Map<String, String> getParamMap(String param) {
        return getParaMap()
                .entrySet()
                .stream()
                .filter(x -> x.getKey().startsWith(param + "."))
                .map(x -> new String[]{x.getKey().split("\\.")[1], x.getValue()[0]})
                .reduce(new HashMap<String, String>(), (acc, x) -> {
                    acc.put(x[0], x[1]);
                    return acc;
                }, (x, y) -> x);
    }
}
