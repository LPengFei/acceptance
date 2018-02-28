package com.cnksi.app.controller;

import com.cnksi.app.model.*;
import com.cnksi.kconf.KConfig;
import com.cnksi.kconf.controller.KController;
import com.cnksi.kconf.model.Menu;
import com.cnksi.kconf.model.User;
import com.cnksi.kcore.jfinal.model.BaseModel;
import com.cnksi.kcore.web.KWebQueryVO;
import com.jfinal.aop.Before;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.upload.UploadFile;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
public class ProProjectController extends KController {

    String uploadPath = PathKit.getWebRootPath() + File.separator + JFinal.me().getConstants().getBaseDownloadPath() + File.separator;

    public ProProjectController() {
        super(ProProject.class);
    }

    public void index() {
        KWebQueryVO queryParam = super.doIndex();
//
//        Page page = ProProject.me.paginate(queryParam);
//
//        setAttr("page", page);
//        render("list.jsp");


        final List<Object> params = queryParam.getFilter()
                .values()
                .stream()
                .reduce(new ArrayList<Object>(), (acc, x) -> {
                    acc.add(x);
                    return acc;
                }, (x, y) -> x);

        StringBuilder whereBuffer = queryParam.getFilter()
                .keySet()
                .stream()
                .reduce(params.isEmpty() ? new StringBuilder(256) : new StringBuilder().append("and "),
                        (acc, x) -> acc.append(x).append(" ").append("?").append(" "), (x, y) -> x);


        List<Record> records = Db.find("select * from " + ProProject.me.getTableName() + " where enabled = 0 " + whereBuffer.toString() + " order by createtime asc", params.toArray());
        setAttr("projects", records.stream().map(x -> {
            List<Record> scals = Db.find("select * from " + ProProjectScal.me.getTableName() + " where pid = ? and `key` != -1 and enabled = 0 order by sort", x.getStr("id"));
            if (scals.size() == 0) {
                scals = Db.find("select * from " + SysConfig.me.getTableName() + " where `type` = ? order by sort", "project_scal");
            }
            List<Record> scalLines = Db.find("select * from " + ProProjectScal.me.getTableName() + " where pid = ? and `key` = -1 and enabled = 0 order by sort", x.getStr("id"));
            x.set("scals", scals);
            x.set("scalLines", scalLines);
            return x;
        }).collect(Collectors.toList()));
        render("list.jsp");
    }


    public void edit() {

        super.doEdit();

        String idValue = getPara("id", getPara());
        ProProject record;
        List<Record> scals;
        List<Record> scalLines = new ArrayList<>();
        if (idValue != null) {
            record = ProProject.me.findById(idValue);
            scals = Db.find("select * from " + ProProjectScal.me.getTableName() + " where pid = ? and `key` != -1 and enabled = 0 order by sort", record.getStr("id"));
            if (scals.size() == 0) {
                scals = Db.find("select * from " + SysConfig.me.getTableName() + " where `type` = ? order by sort", "project_scal");
            }
            scalLines = Db.find("select * from " + ProProjectScal.me.getTableName() + " where pid = ? and `key` = -1 and enabled = 0 order by sort", record.getStr("id"));
        } else {
            record = new ProProject();
            scals = Db.find("select * from " + SysConfig.me.getTableName() + " where `type` = ? order by sort", "project_scal");
        }
        setAttr("record", record);
        setAttr("scals", scals);
        setAttr("scalLines", scalLines);

        render("form.jsp");
    }

    public void download() {
        String projectId = getPara("projectId", getPara());
        String type = getPara("type", getPara());
        if (projectId != null) {
            ProProject record = ProProject.me.findById(projectId);
            String fileUrl = record.get(type);
            String fileLocation = PathKit.getWebRootPath() + File.separator + JFinal.me().getConstants().getBaseDownloadPath() + File.separator + fileUrl;
            Path p = Paths.get(fileLocation);
            if (Files.exists(p)) {
                renderFile(fileUrl);
            }
        } else {
            bjuiAjax(300);
        }
    }

    @Before(Tx.class)
    public void save() throws IOException {
        String projectId = getPara("projectId", getPara());
        List<UploadFile> uploadFiles = this.getFiles("attachment/");
        ProProject record = getModel(ProProject.class, "record");

        Optional.ofNullable(PmsBdz.me.findById(record.getStr("bdzid")))
                .orElseGet(() -> {
                    PmsBdz bdz = new PmsBdz().set("pms_BDZMC", record.getStr("bdz"));
                    bdz.save();
                    record.set("bdzid", bdz.getStr("id"));
                    return null;
                });


        for (UploadFile uploadFile : uploadFiles) {
            record.set(uploadFile.getParameterName().split("_")[0], "attachment/" + uploadFile.getFileName());
        }

        List<ProProjectScal> proProjectScals = getModels(ProProjectScal.class, "scals");
        List<ProProjectScal> proProjectScalLines = getModels(ProProjectScal.class, "scalLines");
        if (record.get("id") != null) {
            record.update();
        } else {
            record.save();
        }


        //预先删除前端废弃的进出线
        List<Record> scalLines = Db.find("select id from " + ProProjectScal.me.getTableName() + " where pid = ? and `key` = -1 and enabled = 0 order by sort", record.getStr("id"));
        for (Record scalLine : scalLines) {
            Boolean hasFound = false;
            for (ProProjectScal scal : proProjectScalLines) {
                if (scalLine.getStr("id").equals(scal.getStr("id"))) {
                    hasFound = true;
                }
            }
            if (!hasFound) {
                ProProjectScal.me.deleteById(scalLine.getStr("id"));
            }
        }

        proProjectScals.addAll(proProjectScalLines);
        for (ProProjectScal scal : proProjectScals) {
            scal.set("pid", record.getStr("id"));
            if (scal.getStr("id") != null) {
                scal.update();
            } else {
                scal.save();
            }
        }

//        bjuiAjax(200, true);
//        HashMap<String, Object> resultMap = new HashMap<String, Object>();
//        resultMap.put("statusCode", 200);
//        resultMap.put("message", "操作成功");
//        resultMap.put("closeCurrent", true);
//        resultMap.put("projectId", record.getStr("id"));
//        resultMap.put("tabid", "main");
//        renderJson(resultMap);

        bjuiAjax(200, "main", true);
    }

    @Before(Tx.class)
    public void delete() {
        ProProject record = ProProject.me.findById(getPara());
        if (record != null) {
            record.set("enabled", 1).update();
            Optional.ofNullable(PmsBdz.me.findById(record.getStr("bdzid"))).ifPresent(x -> x.set("enabled", 1).update());

            bjuiAjax(200, "main", false);
        } else {
            bjuiAjax(300);
        }
    }


    public void export() throws IOException {
        KWebQueryVO queryParam = super.doIndex();
        Page<ProProject> p = ProProject.me.paginate(queryParam);
        String xlsid = getPara("xlsid", "-1");
        super.export(xlsid, p.getList());
    }


    public void importxlsed() {
        String errorFile = "", msg = "";
        try {
            ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
            if (!result.isVerfiyFail()) {
                for (Map<String, Object> map : result.getList()) {
                    ProProject record = new ProProject();
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

    public void progress() {
        String projectId = getPara("projectId");
        setAttr("projectId", projectId);
        render("progress.jsp");
    }

    public void ganttJsp() {
        String projectId = getPara("projectId");
        setAttr("projectId", projectId);
        render("gantt.jsp");
    }

    public void gantt() {
//        String projectId = getPara("projectId");
        Record record = new Record().set("id", 1)
                .set("start_date", new Date())
                .set("duration", 5)
                .set("text", "project #1")
                .set("progress", 0)
                .set("parent", 0)
                .set("open", true)
                .set("sortorder", 1);
        renderJson(new HashMap<String, Object>() {{
            put("data", Arrays.asList(record));
        }});
    }

    public void info() {
        String idValue = getPara("id", getPara());
        ProProject proProject = ProProject.me.findById(idValue);
        if (proProject != null) {
            List<Record> scals = Db.find("select * from " + ProProjectScal.me.getTableName() + " where pid = ? and `key` != -1 and enabled = 0 order by sort", proProject.getStr("id"));
            if (scals.size() == 0) {
                scals = Db.find("select * from " + SysConfig.me.getTableName() + " where `type` = ? order by sort", "project_scal");
            }
            List<Record> scalLines = Db.find("select * from " + ProProjectScal.me.getTableName() + " where pid = ? and `key` = -1 and enabled = 0 order by sort", proProject.getStr("id"));
            setAttr("record", proProject);
            setAttr("scals", scals);
            setAttr("scalLines", scalLines);
            User user = getSessionAttr(KConfig.SESSION_USER_KEY);
            setAttr("isadmin", "1".equals(user.getUserRoleId()));
            render("info.jsp");
        }
    }

    /*里程碑界面*/
    public void project_lcb() {
    }

    /*竣工预验收*/
    public void project_jgyys() {
    }

    /*工程缺陷*/
    public void project_gcqx() {
    }

    public void checks() {
        String flow = getPara("flow");
        String task_name = getPara("task_name");
        
        final Map<String, CardType> keymap = CardType.me.findAll()
                .stream()
                .reduce(new HashMap<String, CardType>(), (acc, x) -> {
                    acc.put(x.getStr("key"), x);
                    return acc;
                }, (x, y) -> x);

        String projectId = getPara("projectId");
        Map<String, List<Task>> checks = Db.find("select * from " + SysConfig.me.getTableName() + " where enabled = 0 and `type` = ? order by sort", "flow")
                .stream()
                .reduce(new HashMap<String, List<Task>>(), (acc, x) -> {
                    String flowKey = x.getStr("key");
                    if (!acc.containsKey(flowKey)) {
                        String sql = String.format("select * from %s where enabled = 0 and pid = ? and flow = ? order by time", Task.me.getTableName());
                        List<Task> tasks = Task.me.find(sql, projectId, x.getStr("key"))
                                .stream()
                                .filter(y -> {
                                    if (flow == null) {
                                        return true;
                                    } else if (flowKey.equals(flow)) {
                                        return task_name == null || y.getStr("name").contains(task_name);
                                    }
                                    return true;
                                })
                                .map(y -> {
                                    y.put("_itemIssuesCount", ResultIssue.me.find("select * from " + ResultIssue.me.getTableName() + " where tid = ? and enabled = 0", y.getStr("id")).size());
                                    y.put("_importantIssuesCount", ResultIssueImportant.me.find("select * from " + ResultIssueImportant.me.getTableName() + " where tid = ? and enabled = 0", y.getStr("id")).size());

                                    if ("arrive_check-count".equals(y.getStr("type"))) {
                                        CountTask countTask = CountTask.me.findByPropertity("tid", y.getStr("id"));
                                        y.put("_countTask", countTask);
                                    }
                                    Optional.ofNullable(Card.me.findById(y.getStr("cards")))
                                            .map(z -> y.putFetch("_CardType", keymap.get(z.getStr("type"))))
                                            .map(z -> y.putFetch("_card", z));
                                    return y;
                                })
                                .collect(Collectors.toList());
                        String sqls = "SELECT task.id,task.cname FROM task_supervise task LEFT JOIN task tas ON task.tid = tas.id "
                        		+" LEFT JOIN pro_project pro ON pro.id = tas.pid WHERE pro.id = '"+projectId+"' LIMIT 1";
                        List<Record> records = Db.find(sqls);
                        for(Task ta:tasks){
                        	ta.put("records",records);
                        }
                        acc.put(x.getStr("key"), tasks);
                    }
                    return acc;
                }, (x, y) -> x);
        setAttr("projectId", projectId);
       
        setAttr("checks", checks);
        setAttr("flow", flow);
        setAttr("task_name", task_name);
        render("project_kycssc.jsp");
    }


    public void issues() {
        setAttr("flow", getPara("flow"));
        String projectId = getPara("projectId");
        setAttr("projectId", projectId);
        String devicename = Optional.ofNullable(getPara("devicename")).orElse(null);
        setAttr("devicename", devicename);
        Boolean cleared = Optional.ofNullable(getParaToBoolean("cleared")).orElse(false);
        Boolean isimport = Optional.ofNullable(getParaToBoolean("isimport")).orElse(false);
        List<ResultIssue> issues = ResultIssue.getIssuesByProjectId(projectId)
                .stream()
                .filter(x -> !cleared || "cleared".equals(x.getStr("status")))
                .filter(x -> devicename == null || devicename.equals(x.getStr("_devicename")))
                .filter(x -> !isimport || x.getInt("isimport") == 1)
                .map(x -> {
                    x.put("_signature", ResultSignname.me.findListByPropertitys(new String[]{"enabled", "riid"}, new Object[]{0, x.getStr("id")}, ResultSignname.Logical.AND));
                    return x;
                })
                .collect(Collectors.toList());
        setAttr("issues", issues);
        Map<String, List<ResultIssue>> issuesMap = issues.stream().collect(Collectors.groupingBy(x -> x.getStr("flow")));

        SysConfig.getAllFlows().stream().map(x -> x.getStr("key"))
                .forEach(x -> setAttr(x, Optional.ofNullable(issuesMap.get(x)).orElseGet(ArrayList::new)));

        setAttr("issuesMap", issuesMap);
        render("project_gcqx.jsp");
    }

    public void projectDocs() {
        setAttr("projectId", getPara("projectId"));
        render("projectDocs.jsp");
    }

    public void docFile() {
        String projectId = getPara("projectId");
        setAttr("projectId", projectId);
        String key = getPara("key");
        String sql = String.format("select * from %s where enabled = 0 and key_ = ?", DocFile.me.getTableName());
        DocFile docFile = DocFile.me.findFirst(sql, key);
        setAttr("docFile", docFile);

        String sql0 = "select * from doc_field where enabled = 0 and isgroup = 1 and docid = ? order by sort";
        setAttr("groupFields", DocField.me.find(sql0, docFile.getStr("id")));

        String sql1 = "select * from doc_field where enabled = 0 and isgroup = 0 and pid is not null and docid = ? order by sort";
        setAttr("groupValues", DocField.me.find(sql1, docFile.getStr("id")));

        if (docFile.getStr("type").equals("doc")) {
            if (docFile.getStr("key_").equals("gcgmjzyjsjjzb")) {
                docFile.put("keyVlaueFields", getKeyValueFields(projectId, docFile));
                setAttr("list", getGroupValues(projectId, docFile));
                render("doc000.jsp");
            } else {
                docFile.put("keyVlaueFields", getKeyValueFields(projectId, docFile));
                render("doc.jsp");
            }
        } else {
            setAttr("list", getGroupValues(projectId, docFile));
            render("table_" + key + ".jsp");
        }
    }

    private List<DocField> getKeyValueFields(String projectId, DocFile docFile) {
        String sql1 = String.format("select * from %s where enabled = 0 and isgroup = 0 and pid is null and docid = ? order by sort", DocField.me.getTableName());
        return DocField.me.find(sql1, docFile.getStr("id"))
                .stream()
                .map(y -> {
                    String sql2 = String.format("select * from %s where enabled = 0 and docid = ? and dfid = ? and pid = ?", DocFieldValue.me.getTableName());
                    y.put("_docFieldValue", DocFieldValue.me.find(sql2, docFile.getStr("id"), y.getStr("id"), projectId).stream().findFirst().orElseGet(DocFieldValue::new));
                    return y;
                })
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> getGroupValues(String projectId, DocFile docFile) {
        String sql2 = "select * from doc_group_value where pid = ? and docid = ? and enabled = 0 order by sort";
        return DocGroupValue.me.find(sql2, projectId, docFile.getStr("id"))
                .stream()
                .map(x -> {
                    Map<String, Object> v = new HashMap<>();
                    v.put("group", x);
                    return v;
                })
                .map(x -> {
                    String groupid = ((DocGroupValue) (x.get("group"))).get("id");
                    String sql3 = "select * from doc_field_value where groupid = ? and enabled = 0 order by sort";
                    DocFieldValue.me.find(sql3, groupid).forEach(y -> x.put(y.getStr("key_"), y.getStr("value")));
                    return x;
                })
                .collect(Collectors.toList());
    }

    @Before(Tx.class)
    public void saveDoc() {
        String projectId = getPara("projectId");
        String docId = getPara("docId");
        Map<String, String> record = getParamMap("map");

        String sql = "select * from doc_field where enabled = 0 and docid = ? and isgroup = 0 and pid is null";
        DocField.me.find(sql, docId)
                .forEach(x -> {
                    String sql1 = "select * from doc_field_value where enabled = 0 and docid = ? and pid = ? and key_ = ? and groupid is null";
                    Optional.ofNullable(DocFieldValue.me.findFirst(sql1, docId, projectId, x.getStr("key_")))
                            .map(y -> y.set("value", record.get(x.getStr("key_"))).set("remark", "刚刚更新的").update())
                            .orElseGet(() -> new DocFieldValue()
                                    .set("docid", docId)
                                    .set("dfid", x.getStr("id"))
                                    .set("pid", projectId)
                                    .set("name", x.getStr("name"))
                                    .set("unit", x.getStr("unit"))
                                    .set("key_", x.getStr("key_"))
                                    .set("type", x.getStr("type"))
                                    .set("sort", x.getInt("sort"))
                                    .set("value", record.get(x.getStr("key_")))
                                    .set("remark", "刚刚添加的")
                                    .save());
                });

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("statusCode", "200");
        resultMap.put("message", "更新成功");
        resultMap.put("closeCurrent", false);
        renderJson(resultMap);
    }

    @Before(Tx.class)
    public void saveGroup() {
        String projectId = getPara("projectId");
        String docId = getPara("id");
        boolean closeCurrent = getParaToBoolean("closeCurrent");
        DocGroupValue model = getModel(DocGroupValue.class, "group");
        Map<String, String> record = getParamMap("record");

        Optional.ofNullable(DocGroupValue.me.findById(model.getStr("id")))
                .map(v -> {
                    DocField docField = DocField.me.findByPropertity(new String[]{"enabled", "docid", "key_"}, new Object[]{0, docId, model.getStr("key_")}, DocField.Logical.AND);
                    v.set("docid", docId)
                            .set("dfid", docField.getStr("id"))
                            .set("pid", projectId)
                            .set("key_", docField.getStr("key_"))
                            .set("value", docField.getStr("name"))
                            .set("sort", docField.getInt("sort"))
                            .set("remark", "刚更新的")
                            .update();


                    String sql = "select * from doc_field_value where enabled = 0 and docid = ? and groupid = ?";
                    DocFieldValue.me.find(sql, docId, v.getStr("id"))
                            .stream()
                            .filter(x -> Objects.nonNull(record.get(x.getStr("key_"))))
                            .forEach(x -> x.set("value", record.get(x.getStr("key_"))).set("remark", "刚刚更新的").update());
                    return v;
                })
                .orElseGet(() -> {
                    DocField docField = DocField.me.findByPropertity(new String[]{"enabled", "docid", "key_"}, new Object[]{0, docId, model.getStr("key_")}, DocField.Logical.AND);
                    DocGroupValue docGroupValue = new DocGroupValue()
                            .set("docid", docId)
                            .set("dfid", docField.getStr("id"))
                            .set("pid", projectId)
                            .set("key_", docField.getStr("key_"))
                            .set("value", docField.getStr("name"))
                            .set("sort", docField.getInt("sort"))
                            .set("remark", "刚插入的");
                    docGroupValue.save();

                    String sql = "select * from doc_field where enabled = 0 and docid = ? and isgroup = 0 and pid like ?";
                    DocField.me.find(sql, docId, "%" + docField.getStr("id") + "%")
                            .forEach(x -> {
                                new DocFieldValue()
                                        .set("docid", x.getStr("docid"))
                                        .set("dfid", x.getStr("id"))
                                        .set("pid", projectId)
                                        .set("groupid", docGroupValue.getStr("id"))
                                        .set("name", x.getStr("name"))
                                        .set("key_", x.getStr("key_"))
                                        .set("type", x.getStr("type"))
                                        .set("value", record.get(x.getStr("key_")))
                                        .set("remark", "刚刚添加的")
                                        .save();
                            });
                    return null;
                });
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("statusCode", "200");
        resultMap.put("message", "更新成功");
        resultMap.put("closeCurrent", closeCurrent);
        resultMap.put("tabid", "doc-list");
        renderJson(resultMap);
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

    public void deleteGroup() {
        Optional.ofNullable(DocGroupValue.me.findById(getPara("groupId")))
                .ifPresent(x -> x.set("enabled", 1).update());

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("statusCode", "200");
        resultMap.put("message", "更新成功");
        resultMap.put("closeCurrent", true);
        resultMap.put("tabid", "doc-list");
        renderJson(resultMap);
    }

    public void issuesRemain() {
        String projectId = getPara("projectId");
        setAttr("projectId", projectId);
        setAttr("resultIssues", ResultIssue.getRemainIssuesByProjectId(projectId));
        render("table_gcylwtclqd.jsp");
    }

    public void saveIssueRemain() {
        ResultIssue model = getModel(ResultIssue.class, "record");
        Optional.ofNullable(ResultIssue.me.findById(model.getStr("id")))
                .ifPresent(x ->
                        x.set("responsibility", model.getStr("responsibility"))
                                .set("limittime", model.getDate("limittime"))
                                .update());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("statusCode", "200");
        resultMap.put("message", "更新成功");
        resultMap.put("closeCurrent", true);
        resultMap.put("tabid", "doc-list");
        renderJson(resultMap);
    }

    public void issuesRemainPreview() {
        String projectId = getPara("projectId");
        setAttr("projectId", projectId);
        setAttr("resultIssues", ResultIssue.getRemainIssuesByProjectId(projectId));
        render("issuesRemainPreview.jsp");
    }

    public void docPreview() {
        String projectId = getPara("projectId");
        String id = getPara("id");
        String basePath = getRequest().getContextPath() + "/upload/";

        DocFile docFile = DocFile.me.findById(id);
        String tpl = docFile.get("tpl_path");


        Map<String, Object> map = new HashMap<String, Object>();

        //查询普通的键值对
        String sql = "select * from doc_field_value where enabled = 0 and pid = ? and docid = ? and groupid is null";
        List<DocFieldValue> values = DocFieldValue.me.find(sql, projectId, id);
        List<Object> signs = new ArrayList<>();
        for (DocFieldValue docFieldValue : values) {

            String key = docFieldValue.getStr("key_");
            String value = docFieldValue.getStr("value");
            String type = docFieldValue.getStr("type");

            if (type.equals("sign")) {
                //处理签名路径的问题
                if (StrKit.notBlank(value)) value = basePath + value;

                docFieldValue.put(key, value);
                signs.add(docFieldValue);
            } else {
                map.put(key, value);
            }
        }
        map.put("list", signs);

        //签名列表和group列表的key都是list，但是不会同时存在在一个文档中
        if (!(signs.size() > 0)) {
            //查询行数据，先查询group在查询
            sql = "select * from doc_group_value where pid = ? and docid = ? and enabled = 0 order by sort";
            List<DocGroupValue> groups = DocGroupValue.me.find(sql, projectId, id);

            List<Map<String, Object>> list = new ArrayList<>();
            for (DocGroupValue group : groups) {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("group", group);

                String groupid = group.get("id");
                sql = "select * from doc_field_value where groupid = ? and enabled = 0 order by sort";
                List<DocFieldValue> docFieldValues = DocFieldValue.me.find(sql, groupid);
                for (DocFieldValue docFieldValue : docFieldValues) {

                    String key = docFieldValue.getStr("key_");
                    String value = docFieldValue.getStr("value");
                    String type = docFieldValue.getStr("type");

                    if (type.equals("sign")) {
                        //处理签名路径的问题
                        if (StrKit.notBlank(value)) value = basePath + value;
                    }

                    item.put(key, value);
                }

                list.add(item);
            }
            map.put("list", list);
        }

        String result = freemarkerProccess(tpl, map);
        setAttr("content", result);

        render("docPreview.jsp");
    }

    private String freemarkerProccess(String tpl, Map<String, Object> map) {
        try {
            StringWriter writer = new StringWriter();

            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("utf-8");
            configuration.setDirectoryForTemplateLoading(new File(PathKit.getWebRootPath() + "/upload"));
            Template t = configuration.getTemplate(tpl);
            t.process(map, writer);

            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
