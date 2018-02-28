package com.cnksi.app.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.dom4j.DocumentException;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException;

import com.cnksi.app.model.Card;
import com.cnksi.app.model.CardItem;
import com.cnksi.app.model.CardItemType;
import com.cnksi.app.model.CardStandard;
import com.cnksi.app.model.CountDevice;
import com.cnksi.app.model.CountTask;
import com.cnksi.app.model.DeviceType;
import com.cnksi.app.model.KBaseModel;
import com.cnksi.app.model.ProProject;
import com.cnksi.app.model.ResultIssue;
import com.cnksi.app.model.ResultIssueImportant;
import com.cnksi.app.model.ResultItem;
import com.cnksi.app.model.ResultItemCopy;
import com.cnksi.app.model.ResultSignname;
import com.cnksi.app.model.Task;
import com.cnksi.app.model.TaskCard;
import com.cnksi.kconf.controller.KController;
import com.cnksi.kcore.jfinal.model.KModel;
import com.cnksi.kcore.jfinal.model.KModelField;
import com.cnksi.kcore.web.KWebQueryVO;
import com.cnksi.util.WordUtil;
import com.cnksi.util.wordUtil.model.Pictrue;
import com.jfinal.aop.Before;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import freemarker.template.TemplateException;


/**
 *
 */
public class TaskController extends KController {
    private final String template_path = PathKit.getWebRootPath() + File.separator + "WEB-INF" + File.separator + "template_doc" + File.separator;
    private final String template = template_path + "taskCard.xml";
    private final String teplateRemain = template_path + "resultIssuesRemain.xml";
    private final String issueTemplatePath = template_path + "resultIssues" + File.separator;
    private final String resultIssueImportantTemplate = template_path + "resultIssueImportantTemplate.xml";
    private final String genDocPath = PathKit.getWebRootPath() + File.separator + "upload" + File.separator;
    private final String public_upload = PathKit.getWebRootPath() + File.separator + "upload" + File.separator;
    private final Map<String, ResultIssueDoc> resultIssuesMap = new HashMap<String, ResultIssueDoc>() {{
        put("feasible_check-keyan", new feasible_check());
        put("feasible_check-chushe", new feasible_check());
        put("factory_check-key", new factory_check());
        put("factory_check-leave", new factory_check());
        put("arrive_check-count", new arrive_check());
        put("arrive_check-open", new arrive_check());
        put("hidden_check", new hidden_check());
        put("middle_check", new middle_check());
        put("complete_check", new complete_check());
    }};


    public TaskController() {
        super(Task.class);
    }

    DocImageUtils processResultIssue(List<ResultIssue> resultIssues) throws DocumentException, TemplateException, IOException {
        DocImageUtils docImageUtils = new DocImageUtils();
        for (ResultIssue resultIssue : resultIssues) {
            List<String> pictureIds = new ArrayList<>();
            String[] imgs = ((String) resultIssue.get("imgs")).split(",");
            for (String img : imgs) {
                String imgUrl = public_upload + img;
                if (Files.isRegularFile(Paths.get(imgUrl))) {
                    pictureIds.add(docImageUtils.add(imgUrl));
                }
            }
            resultIssue.put("pictrueIds", pictureIds);
        }
        return docImageUtils;
    }

    public void index() {
        final Map<String, String> statusMap = new HashMap<String, String>() {{
            put("doing", "进行中");
            put("add", "新增");
            put("done", "完成");
        }};
        KWebQueryVO queryParam = super.doIndex();

        String projectId = getPara("projectId", getPara());
        String flow = getPara("flow", getPara());
        queryParam.addFilter("pid", "=", projectId);
        queryParam.addFilter("flow", "=", flow);
        queryParam.setOrderField("time");
        queryParam.setOrderDirection("desc");
        Page<Task> page = Task.me.paginate(queryParam);

        page.getList()
                .stream()
                .reduce(new HashMap<Integer, List<Task>>(), (acc, x) -> {
                    x.set("status", statusMap.get(x.get("status")));
                    if (!acc.containsKey(x.identity())) {
                        acc.put(x.identity(), new ArrayList());
                    }
                    ((List) acc.get(x.identity())).add(x);
                    return acc;
                }, place::ment)
                .values()
                .stream()
                .max(Comparator.comparingInt(List::size))
                .ifPresent(x -> {
                    if (x.size() > 1) {
                        x.stream()
                                .map(y -> y.put("_sameTask", true))
                                .collect(Collectors.toList())
                                .stream()
                                .findFirst()
                                .ifPresent(y -> setAttr("mergeTaskId", y.get("id")));
                    }
                });


        setAttr("sameCount", page.getList().stream().filter(x -> x.get("_sameTask") != null).count());
        setAttr("page", page);
        setAttr("projectId", projectId);
        render("list.jsp");
    }

    //task：其中的每一个属性以任何一个不为空的为准
    //task_card:其中的每一个属性以任何一个不为空的为准
    //result_signname:合并
    //result_item:合并，如果遇到都验收了同一个验收项，保留其中一个，优先保留不合格的
    //result_item_copy：合并，如果遇到同一个抄录，保留其中任何一个
    //result_issue：合并
    //result_issue_important:合并
    @Before(Tx.class)
    public void merge() {
        String mergeTaskId = getPara("mergeTaskId", getPara());
        if (mergeTaskId != null) {
            // 合并任务表
            List<Task> oldTasks = Task.me.findListByPropertitys(Task.identityStrings, Task.me.findById(mergeTaskId).getIdentityStrings(), Task.Logical.AND);

            String oldIds = oldTasks.stream().map(x -> {
                x.set("enabled", 1).set("remark", "被合并了" + new Random().nextInt(10)).update();
                return (String) x.getPkVal();
            }).collect(Collectors.joining(","));

            Task newestTask = oldTasks
                    .stream()
                    .max(Comparator.comparing((x) -> x.get("last_modify_time")))
                    .orElse(new Task());

            Task newTask = new Task();
            Stream.of(newestTask._getAttrNames()).forEach(x -> newTask.set(x, newestTask.get(x)));
            newTask.setPkVal(null).set("enabled", 0).set("oids", oldIds).set("remark", "合并之后");
            newTask.save();

            // 合并任务卡表
            Map<Task, TaskCard> taskMap = new HashMap<>();
            TaskCard newestTaskCard = oldTasks.stream()
                    .map(x -> {
                        TaskCard taskCard1 = TaskCard.me.findByPropertity("tid", x.getPkVal());
                        taskMap.put(x, taskCard1);
                        return taskCard1;
                    })
                    .map(x -> {
                        x.set("enabled", 1).set("remark", "被合并了" + new Random().nextInt(10)).update();
                        return x;
                    })
                    .max(Comparator.comparing((a) -> a.get("last_modify_time")))
                    .orElse(new TaskCard());
            TaskCard newTaskCard = new TaskCard();
            Stream.of(newestTaskCard._getAttrNames()).forEach(x -> newTaskCard.set(x, newestTaskCard.get(x)));
            newTaskCard.setPkVal(null).set("enabled", 0).set("tid", newTask.getPkVal()).set("cid", newTask.get("cards")).set("remark", "合并之后");
            newTaskCard.save();


            Consumer<KBaseModel> copyMerge = (x) -> {
                x.set("enabled", 1).set("remark", "被合并了" + new Random().nextInt(10)).update();
                x.set("id", null)
                        .set("tid", newTask.getPkVal())
                        .set("tcid", newTaskCard.getPkVal())
                        .set("remark", "合并之后")
                        .save();
            };
            // 合并其他可以用任务和任务卡检索的表
            taskMap.forEach((x, y) -> {
                ResultSignname.me.getSignByTaskAndTaskCardId(x.getPkVal(), y.getPkVal()).forEach(copyMerge);
                ResultIssue.me.getIssueByTaskAndTaskCardId(x.getPkVal(), y.getPkVal()).forEach(copyMerge);
                ResultIssueImportant.me.getImportantIssueByTaskAndTaskCardId(x.getPkVal(), y.getPkVal()).forEach(copyMerge);

                Map<String, String> old2NewMap = new HashMap<String, String>();
                ResultItem.me.getResultItemsByTaskAndTaskCardId(x.getPkVal(), y.getPkVal())
                        .stream()
                        .reduce(new Stack<ResultItem>(), (acc, z) -> {
                            z.put("_oldIds", Collections.singletonList(z.getPkVal()));
                            z.set("enabled", 1).set("remark", "被合并了" + new Random().nextInt(10)).update();

                            if (acc.size() == 0) {
                                acc.push(z);
                            } else if (acc.peek().get("ciid").equals(z.get(("ciid")))) {
                                if (z.getInt("result") == 1) {
                                    ((List<String>) (z.get("_oldIds"))).addAll((List<String>) (acc.pop().get("_oldIds")));
                                    acc.push(z);
                                } else {
                                    ((List<String>) (acc.peek().get("_oldIds"))).addAll((List<String>) (z.get("_oldIds")));
                                }
                            } else {
                                acc.push(z);
                            }
                            return acc;
                        }, place::ment)
                        .stream()
                        .map(z -> {
                            z.set("id", null)
                                    .set("tid", newTask.getPkVal())
                                    .set("tcid", newTaskCard.getPkVal())
                                    .set("remark", "合并之后")
                                    .save();
                            ((List<String>) (z.get("_oldIds"))).forEach(w -> {
                                old2NewMap.put(w, z.getPkVal());
                            });
                            return z;
                        })
                        .collect(Collectors.toList());

                ResultItemCopy.me.getResultItemsByTaskAndTaskCardId(x.getPkVal(), y.getPkVal())
                        .stream()
                        .reduce(new Stack<ResultItemCopy>(), (acc, z) -> {
                            z.set("enabled", 1).set("remark", "被合并了" + new Random().nextInt(10)).update();
                            if (acc.size() == 0 || !acc.peek().get("ccid").equals(z.get(("ccid")))) {
                                acc.push(z);
                            }
                            return acc;
                        }, place::ment)
                        .stream()
                        .map(z -> z.set("id", null)
                                    .set("tid", newTask.getPkVal())
                                    .set("tcid", newTaskCard.getPkVal())
                                    .set("riid", old2NewMap.get(z.<String>get("riid")))
                                    .set("remark", "合并之后")
                                    .save()
                        )
                        .collect(Collectors.toList());
            });

            bjuiAjax(200);
        } else {
            bjuiAjax(300);
        }
    }

    public void edit() {
        super.doEdit();

        String projectId = getPara("projectId", getPara());
        setAttr("projectId", projectId);
        String projectName = ProProject.me.findById(projectId).get("name");
        setAttr("projectName", projectName);

        String idValue = getPara("id", getPara());
        Task record = null;
        if (idValue != null) {
            record = Task.me.findById(idValue);
            List<TaskCard> taskCards = TaskCard.me.find("select * from " + TaskCard.me.getTableName() + " where tid = ? and enabled = 0", idValue);
            List<Card> cards = new ArrayList<Card>();
            for (TaskCard taskCard : taskCards) {
                String cardId = taskCard.get("cid");
                Card card = Card.me.findById(cardId);
                cards.add(card);
            }
            setAttr("taskCards", taskCards);
            setAttr("cards", cards);
        } else {
            record = new Task();

        }
        setAttr("record", record);


        KModel m = KModel.me.findByTableName("task_card");
        if (m != null) {
            List<KModelField> fields = m.getFormViewField();
            fields.forEach(f -> f.set("settings", f.getSettings()));
            setAttr("tc_fields", fields.stream().reduce(new HashMap<String, Object>(), (acc, x) -> {
                if (Objects.isNull(acc.get(x.getStr("field_name")))) {
                    acc.put(x.getStr("field_name"), x);
                }
                return acc;
            }, (x, y) -> x));
        }
        render("form.jsp");
    }

    private TaskCard modifyTaskCardByTask(Task record, TaskCard taskCard, String[] cardItemTypes) {
        if (cardItemTypes != null) {
            taskCard.set("citids", String.join(",", cardItemTypes));
        }

        String projectId = record.get("pid");
        ProProject project = ProProject.me.findById(projectId);
        String cardId = record.get("cards");
        String cardName = null;
        if (cardId != null) {
            cardName = Card.me.findById(cardId).get("name");
        }

        taskCard.set("tid", record.get("id"))
                .set("sbxh", record.get("deviceno"))
                .set("cid", record.get("cards"))
                .set("cname", cardName)
                .set("flow", record.get("flow"))
                .set("type", record.get("type"))
                .set("dtid", DeviceType.me.findByPropertity("name", record.get("devicetype")).get("id"))
                .set("gcmc", project.get("name"))
                .set("sjdw", project.get("sjdw"));

        return taskCard;
    }

    @Before(Tx.class)
    public void save() {
        Optional.ofNullable(getModel(Task.class, "record"))
                .filter(x -> Objects.nonNull(x.get("id")))
                .map(x -> {
                    x.update();
                    Optional.ofNullable(getModel(TaskCard.class, "taskCard")).ifPresent(TaskCard::update);
                    return bjuiAjax(200, "task-info", true);
                })
                .orElseGet(() -> bjuiAjax(300, false));
//        Task record = getModel(Task.class, "record");
//        if (record.get("id") != null) {
//            record.update();
//            Optional.ofNullable(getModel(TaskCard.class, "taskCard")).ifPresent(x -> x.update());
////            modifyTaskCardByTask(record, TaskCard.me.findByPropertity("tid", (String) record.get("id")), cardItemTypes).update();
//        } else {
//            String cardTypeKey = record.get("type");
//            CardType cardType = CardType.me.findByPropertity("card_type.key", cardTypeKey);
//            String flow = cardType.get("flow");
//            SysConfig config = SysConfig.me.findByPropertity("sys_config.key", flow);
//            String flowName = config.get("name");
//            record.set("flow", flow);
//            record.set("flowname", flowName);
//            record.save();
////            modifyTaskCardByTask(record, new TaskCard(), cardItemTypes).save();
//        }
    }

    public void delete() {
        Task record = Task.me.findById(getPara());
        if (record != null) {
            record.set("enabled", 1).update();
            bjuiAjax(200);
        } else {
            bjuiAjax(300);
        }

    }

    public void export() throws IOException {
        KWebQueryVO queryParam = super.doIndex();
        Page<Task> p = Task.me.paginate(queryParam);
        String xlsid = getPara("xlsid", "-1");
        super.export(xlsid, p.getList());
    }

    public void importxlsed() {
        String errorFile = "", msg = "";
        try {
            ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
            if (!result.isVerfiyFail()) {
                for (Map<String, Object> map : result.getList()) {
                    Task record = new Task();
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

    public void info() {
        String taskId = getPara("id");
        setAttr("tab", getParaToInt("tab"));
        Task task = Task.getTaskInfoById(taskId);
        if (task.get("type").equals("arrive_check-count")) {
            CountTask countTask = CountTask.me.findByPropertity("tid", taskId);
            if (countTask != null) {
                processCountDevice(taskId, countTask.getPkVal());
            }
            setAttr("countTask", countTask);

            String tid = task.getStr("id");
            List<ResultIssue> resultIssues = ResultIssue.me.find("select * from " + ResultIssue.me.getTableName() + " where tid = ? and enabled = 0", tid);
            setAttr("taskId", tid);
            setAttr("resultIssues", resultIssues);

            render("countInfo.jsp");
        } else {
            setAttr("task", task);
            setAttr("resultIssueImportantCount", ResultIssueImportant.me.find("select * from " + ResultIssueImportant.me.getTableName() + " where tid = ? and enabled = 0", taskId).size());
            render("info.jsp");
        }
    }

    public void countDevices() {
        String projectId = getPara("projectId");
        String deviceName = Optional.ofNullable(getPara("deviceName")).orElseGet(() -> DeviceType.me.findAllForSearch(false).get(0).getStr("name"));


        String sql = "SELECT cd.* FROM task t, count_task ct, count_device cd WHERE t.enabled = 0 AND ct.enabled = 0 AND cd.enabled = 0 AND t.id = ct.tid AND t.pid = ? AND t.flow = 'arrive_check' AND ct.tid = cd.tid AND ct.id = cd.ctid and cd.name = ?";
        List<Record> records = Db.find(sql, projectId, deviceName);
        int total = records.stream().reduce(0, (acc, x) -> acc += x.getInt("amount"), (x, y) -> x);
        setAttr("records", records);
        setAttr("total", total);
        setAttr("deviceName", deviceName);
        setAttr("projectId", projectId);
        render("countAll.jsp");
    }

    private void processCountDevice(String taskId, String countTaskId) {
        Map<String, Integer> staticsMap = new HashMap<String, Integer>() {{
            put("categoryCount", 0);
            put("allCount", 0);
        }};
        Map<String, List> categoryMap = new HashMap<>();
        CountDevice.me.findListByPropertitys(new String[]{"tid", "ctid"}, new String[]{taskId, countTaskId}, CountDevice.Logical.AND)
                .forEach(x -> {
                    String dtid = x.get("dtid");
                    String categoryName = DeviceType.me.findById(dtid).get("name");
                    if (!staticsMap.containsKey(categoryName)) {
                        staticsMap.put(categoryName, 0);
                    }
                    if (!categoryMap.containsKey(categoryName)) {
                        categoryMap.put(categoryName, new ArrayList<CountDevice>());
                    }
                    categoryMap.get(categoryName).add(x);
                    staticsMap.put(categoryName, staticsMap.get(categoryName) + x.getInt("amount"));

                    staticsMap.put("allCount", ((Integer) staticsMap.get("allCount")) + x.getInt("amount"));
                });
        staticsMap.put("categoryCount", categoryMap.size());
        setAttr("categoryMap", categoryMap);
        setAttr("staticsMap", staticsMap);
    }

    public void downloadItemIssueImportant() throws DocumentException, TemplateException, IOException {
        String taskId = getPara("taskId", getPara());
        Task task = Task.me.findById(taskId);
        String issueImportantId = getPara("issueImportantId", getPara());
        ResultIssueImportant resultIssueImportant = ResultIssueImportant.me.findById(issueImportantId);

        Calendar now = Calendar.getInstance();
        Map<String, Object> param = new HashMap<>();
        param.put("title", "重大问题反馈联系单");
        param.put("identifier", resultIssueImportant.get("no"));
        param.put("sendUnit", resultIssueImportant.get("senddepart"));
        param.put("sendUnitContact", resultIssueImportant.get("sendcontact"));
        param.put("acceptanceDepartment", resultIssueImportant.get("checkdepart"));
        param.put("acceptanceDepartmentContact", resultIssueImportant.get("checkcontact"));
        param.put("receivingUnit", resultIssueImportant.get("receivedepart"));
        param.put("receivingUnitContact", resultIssueImportant.get("receivecontact"));
        param.put("projectName", resultIssueImportant.get("project"));
        param.put("deviceType", resultIssueImportant.get("devicetype"));
        param.put("isbn", resultIssueImportant.get("installplace"));
        param.put("factoryAndType", resultIssueImportant.get("factory"));
        param.put("issueName", resultIssueImportant.get("name"));
        param.put("description", resultIssueImportant.get("description"));
        param.put("suggest", resultIssueImportant.get("suggest"));
        param.put("year", now.get(Calendar.YEAR) + "");
        param.put("month", (now.get(Calendar.MONTH) + 1) + "");
        param.put("day", now.get(Calendar.DAY_OF_MONTH) + "");

        List<Pictrue> pictures = new ArrayList<>();
        for (String img : ((String) resultIssueImportant.get("imgs")).split(",")) {
            String imgUrl = public_upload + img;
            if (Files.isRegularFile(Paths.get(imgUrl))) {
                pictures.add(new Pictrue(new File(imgUrl)));
            }
        }
        param.put("pictures", pictures);

        List<List<String>> pictureIdRows = new ArrayList<>();
        for (int i = 0; i < pictures.size(); i++) {
            Pictrue picture = pictures.get(i);
            int row = (i / 4) + 1;
            if (row > pictureIdRows.size()) {
                pictureIdRows.add(new ArrayList<String>());
            }
            pictureIdRows.get(row - 1).add(picture.getId());
        }
        param.put("pictureIdRows", pictureIdRows);

        String tempFileName = task.get("name") + "-重大问题反馈单";
        String genDoc = genDocPath + tempFileName + ".doc";
        WordUtil.generateWord(param, resultIssueImportantTemplate, genDoc);
        if (Files.isRegularFile(Paths.get(genDoc))) {
            render(new TempFileRender(tempFileName + ".doc", genDoc));
        }

    }

    public void downloadRemainResultIssue() throws DocumentException, TemplateException, IOException {
        String projectId = getPara("projectId");
        ProProject proProject = ProProject.me.findById(projectId);
        List<ResultIssue> resultIssues = ResultIssue.getRemainIssuesByProjectId(projectId);
        DocImageUtils docImageUtils = processResultIssue(resultIssues);

        Map<String, Object> param = new HashMap<>();
        param.put("projectName", proProject.get("name"));
        param.put("gldw", proProject.get("gldw"));
        param.put("yxdw", proProject.get("yxdw"));
        param.put("gldwContact", "");
        param.put("yxdwContact", "");
        param.put("date", "12-12-12");
        param.put("resultIssues", resultIssues);
        param.put("pictrues", docImageUtils.getPictures());

        String tempFileName = proProject.get("name") + "-遗留问题记录";
        String genDoc = genDocPath + tempFileName + ".doc";
        WordUtil.generateWord(param, teplateRemain, genDoc);
        if (Files.isRegularFile(Paths.get(genDoc))) {
            render(new TempFileRender(tempFileName + ".doc", genDoc));
        }
    }

    public void downloadResultIssue() throws Exception {
        String taskId = getPara("taskId", getPara());
        Task task = Task.me.findById(taskId);
        TaskCard taskCard = TaskCard.me.findFirst("select * from " + TaskCard.me.getTableName() + " where tid = ? and enabled = 0", taskId);
        task.put("_taskCard", taskCard);
        resultIssuesMap.get(task.getStr("type")).renderDocFile(task);
    }

    public void downloadTaskDoc() throws IOException, DocumentException, TemplateException {
        String taskId = getPara("taskId", getPara());
        String taskCardId = getPara("taskCardId", getPara());
        Task taskInfo = Task.getTaskInfoById(taskId);
        List<TaskCard> taskCards = taskInfo.get("_taskCards");
        TaskCard taskCard = null;
        for (TaskCard item : taskCards) {
            if (item.get("id").equals(taskCardId)) {
                taskCard = item;
            }
        }

        List<Pictrue> pictrues = new ArrayList<>();
        Map<String, Object> param = new HashMap<>();
        param.put("baseInfo", getBaseInfoByTaskCard(taskCard));
        DocImageUtils docImageUtils = new DocImageUtils();
        List<Object> items = getAcceptanceItemsByTaskCard(taskCard, docImageUtils);
        param.put("acceptanceItems", items);
        param.put("pics", docImageUtils.getPictures());

        String taskCardName = taskCard.get("cname");
        String date = taskCard.get("ysrq");
        String tempFileName = taskCardName + "(" + date + ")";
        String genDoc = genDocPath + tempFileName + ".doc";

        WordUtil.generateWord(param, template, genDoc);
        if (Files.isRegularFile(Paths.get(genDoc))) {
            render(new TempFileRender(tempFileName + ".doc", genDoc));
        }
    }

    private Map<String, Object> getBaseInfoByTaskCard(TaskCard taskCard) {
        Card card = taskCard.get("_card");
        String id = card.get("dtid");
        DeviceType deviceType = DeviceType.me.findById(id);

        List<AbstractMap.SimpleEntry<String,String>> entries = new ArrayList();
        if (card.getInt("showgcmc") == 1) {
            entries.add(new AbstractMap.SimpleEntry<>("工程名称", taskCard.getStr("gcmc")));
        }
        if (card.getInt("showsjdw") == 1) {
            entries.add(new AbstractMap.SimpleEntry<>("设计单位", taskCard.getStr("sjdw")));
        }
        if (card.getInt("showysdw") == 1) {
            entries.add(new AbstractMap.SimpleEntry<>("验收单位", taskCard.getStr("ysdw")));
        }
        if (card.getInt("showsccj") == 1) {
            entries.add(new AbstractMap.SimpleEntry<>("生产厂家", taskCard.getStr("sccj")));
        }
        if (card.getInt("showsbxh") == 1) {
            entries.add(new AbstractMap.SimpleEntry<>("设备型号", taskCard.getStr("sbxh")));
        }
        if (card.getInt("showscgh") == 1) {
            entries.add(new AbstractMap.SimpleEntry<>("生产工号", taskCard.getStr("scgh")));
        }
        if (card.getInt("showccbh") == 1) {
            entries.add(new AbstractMap.SimpleEntry<>("出厂编号", taskCard.getStr("ccbh")));
        }
        if (card.getInt("showbdsmc") == 1) {
            entries.add(new AbstractMap.SimpleEntry<>("变电所名称", taskCard.getStr("bdsmc")));
        }
        if (card.getInt("showsbmcbh") == 1) {
            entries.add(new AbstractMap.SimpleEntry<>("设备名称编号", taskCard.getStr("sbmcbh")));
        }
        if (card.getInt("showjldw") == 1) {
            entries.add(new AbstractMap.SimpleEntry<>("监理单位", taskCard.getStr("jldw")));
        }
        if (card.getInt("showsgdw") == 1) {
            entries.add(new AbstractMap.SimpleEntry<>("施工单位", taskCard.getStr("sgdw")));
        }
        if (card.getInt("showazdw") == 1) {
            entries.add(new AbstractMap.SimpleEntry<>("安装单位", taskCard.getStr("azdw")));
        }
        if (card.getInt("showysrq") == 1) {
            entries.add(new AbstractMap.SimpleEntry<>("验收日期", taskCard.getStr("ysrq")));
        }
        if (entries.size() % 2 == 1) {
            entries.add(new AbstractMap.SimpleEntry<>("", ""));
        }

        return new HashMap<String, Object>() {{
            put("name", card.get("name"));
            put("base", Optional.ofNullable(deviceType).map(x -> x.getStr("name")).orElseGet(() -> ""));
            put("entries", entries);
//            put("projectName", taskCard.get("gcmc"));
//            put("provideName", taskCard.get("sccj"));
//            put("deviceType", taskCard.get("sbxh"));
//            put("acceptanceDate", taskCard.get("ysrq"));
//            put("isbn", taskCard.get("ccbh") == null ? "" : taskCard.get("ccbh"));
//            put("acceptanceDepartment", taskCard.get("ysdw"));
        }};
    }

    private List<Object> getAcceptanceItemsByTaskCard(TaskCard taskCardInfo, DocImageUtils docImageUtils) throws DocumentException, TemplateException, IOException {
        List<ResultSignname> resultSignnames = ResultSignname.me.find("select * from " + ResultSignname.me.getTableName() + " where tcid = ? and enabled = 0", taskCardInfo.getStr("id"));
        List<ResultItem> resultItems = ResultItem.me.find("select * from " + ResultItem.me.getTableName() + " where tcid = ? and enabled = 0", taskCardInfo.getStr("id"));
        List<ResultItemCopy> resultItemCopys = ResultItemCopy.me.find("select * from " + ResultItemCopy.me.getTableName() + " where tcid = ? and enabled = 0", taskCardInfo.getStr("id"));


        List<Object> acceptanceItems = new ArrayList<>();
        List<CardItemType> cardItemTypes = taskCardInfo.get("_cardItemTypes");
        for (CardItemType cardItemType : cardItemTypes) {
            List<String> signIds = new ArrayList<>();
            for (ResultSignname resultSignname : resultSignnames) {
                if (cardItemType.getStr("id").equals(resultSignname.getStr("citid"))) {
                    cardItemType.put("_resultSignname", resultSignname);

                    String imgUrl = public_upload + resultSignname.get("signimg");
                    if (Files.isRegularFile(Paths.get(imgUrl))) {
                        signIds.add(docImageUtils.add(imgUrl));
                    }
                }
            }
            cardItemType.put("_signIds", signIds);

            List<Object> cardInfoItems = new ArrayList<>();
            List<CardItem> cardItems = cardItemType.get("_cardItems");
            for (CardItem cardItem : cardItems) {
                processCardItem(cardItem, resultItems, resultItemCopys);
                if (cardItem.get("_resultItem") == null) {
                    cardItem.put("_resultItem", new HashMap<String, Object>() {{
                        put("result", -1);
                    }});
                }

                if (cardItem.get("_resultItemCopys") == null) {
                    cardItem.put("_resultItemCopys", new ArrayList<>());
                }

                List<CardStandard> cardStandards = CardStandard.me.findListByPropertity("ciid", cardItem.get("id"));
                String tcid = taskCardInfo.get("id");
                String ciid = cardItem.get("id");
                List<ResultIssue> resultIssues = ResultIssue.me.find("select * from " + ResultIssue.me.getTableName() + " where tcid = ? and ciid = ? and enabled = 0", tcid, ciid);
                cardInfoItems.add(new HashMap<String, Object>() {{
                    put("cardItem", cardItem);
                    put("resultIssues", resultIssues);
                    put("cardStandards", cardStandards);
                }});
            }
            acceptanceItems.add(new HashMap<String, Object>() {{
                put("cardItemType", cardItemType);
                put("cardInfoItems", cardInfoItems);
            }});
        }
        return acceptanceItems;
    }

    public void cards() {
        super.doEdit();
        setAttr("task", Task.getTaskInfoById(getPara("id", getPara())));
        render("cards.jsp");
    }
    
    

    private void processCardItem(CardItem cardItem, List<ResultItem> resultItems, List<ResultItemCopy> resultItemCopys) {
        resultItems.stream()
                .filter(x -> cardItem.getStr("id").equals(x.getStr("ciid")))
                .forEach(x -> {
                    cardItem.put("_resultItem", x);

                    List<ResultItemCopy> subResultItemCopys = resultItemCopys.stream()
                            .filter(y -> x.getStr("id").equals(y.getStr("riid")))
                            .collect(Collectors.toList());

                    cardItem.put("_resultItemCopys", subResultItemCopys);
                });
    }

    public void cardStandard() {
        String ciid = getPara("ciid");
        List<CardStandard> cardStandards = CardStandard.me.find("select * from " + CardStandard.me.getTableName() + " where ciid = ? and enabled = 0", ciid);
        setAttr("cardStandards", cardStandards);
        render("cardStandard.jsp");
    }
    
   

    public void singleitemIssues() {
        String tcid = getPara("tcid");
        String ciid = getPara("ciid");
        List<ResultIssue> resultIssues = ResultIssue.me.find("select * from " + ResultIssue.me.getTableName() + " where tcid = ? and ciid = ? and enabled = 0", tcid, ciid);
        setAttr("resultIssues", resultIssues);
        render("singleitemIssues.jsp");
    }

    public void itemIssues() {
        String tid = getPara("tid");
        List<ResultIssue> resultIssues = ResultIssue.me.find("select * from " + ResultIssue.me.getTableName() + " where tid = ? and enabled = 0", tid);
        setAttr("taskId", tid);
        setAttr("resultIssues", resultIssues);
        render("itemIssues.jsp");
    }

    public void itemIssuesRemain() {
        String projectId = getPara("projectId");
        setAttr("projectId", projectId);
        setAttr("resultIssues", ResultIssue.getRemainIssuesByProjectId(projectId));
        render("itemIssuesRemain.jsp");
    }

    public void itemIssuesImportant() {
        String taskId = getPara("tid");
        List<ResultIssueImportant> resultIssueImportants = ResultIssueImportant.me.find("select * from " + ResultIssueImportant.me.getTableName() + " where tid = ? and enabled = 0", taskId);
        setAttr("taskId", taskId);
        setAttr("resultIssueImportants", resultIssueImportants);
        render("itemIssuesImportant.jsp");
    }

    public void itemIssueImportantDetail() {
        String id = getPara("id");
        ResultIssueImportant resultIssueImportant = ResultIssueImportant.me.findById(id);
        setAttr("resultIssueImportant", resultIssueImportant);
        render("itemIssueImportantDetail.jsp");
    }

    static class place<T> {
        static <T> T ment(T a, T b) {
            throw new RuntimeException();
        }
    }

    static class DocImageUtils {
        private List<com.cnksi.util.wordUtil.model.Pictrue> pictrues = new ArrayList<>();
        private Map<String, com.cnksi.util.wordUtil.model.Pictrue> imageMap = new HashMap<>();

        public String add(String absoluteUrl) throws DocumentException, TemplateException, IOException {
            if (!imageMap.containsKey(absoluteUrl)) {
                com.cnksi.util.wordUtil.model.Pictrue picture = new Pictrue(new File(absoluteUrl));
                imageMap.put(absoluteUrl, picture);
                pictrues.add(picture);
            }
            return imageMap.get(absoluteUrl).getId();
        }

        public List<com.cnksi.util.wordUtil.model.Pictrue> getPictures() {
            return pictrues;
        }

        static public class DocPictureNotFoundException extends RuntimeException {
        }
    }

    abstract class ResultIssueDoc {
        abstract public String fileName(Task task);
        abstract public Map<String, Object> getParam(Task task) throws DocumentException, TemplateException, IOException;
        public String getTemplate(Task task) {
            return issueTemplatePath + task.getStr("type") + ".xml";
        }
        public void renderDocFile(Task task) throws DocumentException, TemplateException, IOException {
            String tempFileName = fileName(task);
            String genDoc = genDocPath + tempFileName + ".doc";
            WordUtil.generateWord(getParam(task), getTemplate(task), genDoc);
            if (Files.isRegularFile(Paths.get(genDoc))) {
                render(new TempFileRender(tempFileName + ".doc", genDoc));
            }
        }
    }

    class feasible_check extends ResultIssueDoc {
        @Override
        public String fileName(Task task) {
            return task.get("name") + "-项目可研初设评审记录";
        }
        @Override
        public Map<String, Object> getParam(Task task) throws DocumentException, TemplateException, IOException {
            ProProject project = ProProject.me.findById((String) task.get("pid"));
            String id = task.get("id");
            List<ResultIssue> resultIssues = ResultIssue.me.find("select * from " + ResultIssue.me.getTableName() + " where tid = ? and enabled = 0", id);
            DocImageUtils docImageUtils = processResultIssue(resultIssues);

            return new HashMap<String, Object>() {{
                put("name", task.get("name"));
                put("gldw", project.get("gldw"));
                put("sgdw", project.get("sjdw"));
                put("resultIssues", resultIssues);
                put("pictrues", docImageUtils.getPictures());
            }};
        }
    }

    class arrive_check extends ResultIssueDoc {
        @Override
        public String fileName(Task task) {
            return task.get("name") + "-到货验收记录";
        }
        @Override
        public Map<String, Object> getParam(Task task) throws DocumentException, TemplateException, IOException {
            ProProject project = ProProject.me.findById((String) task.get("pid"));
            String id = task.get("id");
            List<ResultIssue> resultIssues = ResultIssue.me.find("select * from " + ResultIssue.me.getTableName() + " where tid = ? and enabled = 0", id);
            DocImageUtils docImageUtils = processResultIssue(resultIssues);

            return new HashMap<String, Object>() {{
                put("name", task.get("name"));
                put("gldw", project.get("gldw"));
                put("deviceno", project.get("deviceno"));
                put("productor", project.get("productor"));
                put("productor", project.get("scgh"));
                put("resultIssues", resultIssues);
                put("pictrues", docImageUtils.getPictures());
            }};
        }
    }

    class factory_check extends ResultIssueDoc {
        @Override
        public String fileName(Task task) {
            return task.get("name") + "-问题记录";
        }
        @Override
        public Map<String, Object> getParam(Task task) throws DocumentException, TemplateException, IOException {
            ProProject project = ProProject.me.findById((String) task.get("pid"));

            String id = task.get("id");
            List<ResultIssue> resultIssues = ResultIssue.me.find("select * from " + ResultIssue.me.getTableName() + " where tid = ? and enabled = 0", id);
            DocImageUtils docImageUtils = processResultIssue(resultIssues);

            TaskCard taskCard = task.<TaskCard>get("_taskCard");
            Map<String, Object> param = new HashMap<String, Object>(){{
                put("pname", task.get("pname"));
                put("gldw", project.get("gldw"));
                put("sbxh", taskCard.get("sbxh"));
                put("scgh", taskCard.get("scgh"));
                put("resultIssues", resultIssues);
                put("pictrues", docImageUtils.getPictures());
            }};
            return param;
        }
    }

    class middle_check extends ResultIssueDoc {
        @Override
        public String fileName(Task task) {
            return task.get("name") + "-中间验收记录";
        }
        @Override
        public Map<String, Object> getParam(Task task) throws DocumentException, TemplateException, IOException {
            ProProject project = ProProject.me.findById((String) task.get("pid"));
            String id = task.get("id");
            List<ResultIssue> resultIssues = ResultIssue.me.find("select * from " + ResultIssue.me.getTableName() + " where tid = ? and enabled = 0", id);
            DocImageUtils docImageUtils = processResultIssue(resultIssues);

            return new HashMap<String, Object>() {{
                put("name", task.get("name"));
                put("pname", task.get("pname"));
                put("gldw", project.get("gldw"));
                put("sgdw", project.get("sgdw"));
                put("resultIssues", resultIssues);
                put("pictrues", docImageUtils.getPictures());
            }};
        }
    }

    class hidden_check extends ResultIssueDoc {
        @Override
        public String fileName(Task task) {
            return task.get("name") + "-隐蔽工程验收记录";
        }
        @Override
        public Map<String, Object> getParam(Task task) throws DocumentException, TemplateException, IOException {
            ProProject project = ProProject.me.findById((String) task.get("pid"));
            String id = task.get("id");
            List<ResultIssue> resultIssues = ResultIssue.me.find("select * from " + ResultIssue.me.getTableName() + " where tid = ? and enabled = 0", id);
            DocImageUtils docImageUtils = processResultIssue(resultIssues);

            return new HashMap<String, Object>() {{
                put("name", task.get("name"));
                put("pname", task.get("pname"));
                put("gldw", project.get("gldw"));
                put("sgdw", project.get("sgdw"));
                put("resultIssues", resultIssues);
                put("pictrues", docImageUtils.getPictures());
            }};
        }
    }

    class complete_check extends ResultIssueDoc {
        @Override
        public String fileName(Task task) {
            return task.get("name") + "-竣工验收记录";
        }
        @Override
        public Map<String, Object> getParam(Task task) throws DocumentException, TemplateException, IOException {
//            ProProject project = ProProject.me.findById((String) task.get("pid"));
            String id = task.get("id");
            List<ResultIssue> resultIssues = ResultIssue.me.find("select * from " + ResultIssue.me.getTableName() + " where tid = ? and enabled = 0", id);
            DocImageUtils docImageUtils = processResultIssue(resultIssues);

            return new HashMap<String, Object>() {{
                put("resultIssues", resultIssues);
                put("pictrues", docImageUtils.getPictures());
            }};
        }
    }
    
    public void supervise() {
        super.doEdit();
        setAttr("task", Task.getTaskInfoById(getPara("id", getPara())));
        searchData(getPara("id"),getPara("id"));
        render("supervise.jsp");
    }
    
    public void superviseStandard() {
        String ciid = getPara("recordId");
        String sql = "SELECT problem FROM result_item_supervise where id = '"+ciid+"'";
        Record res = Db.findFirst(sql);
        setAttr("res", res);
        render("superviseStandard.jsp");
    }
    
    public void searchData(String taskId,String id){
//    	?id=123
    	if(taskId.equals("17041021273779810123")){
    		id = "1";
    	}else if(taskId.equals("17041021275638185387")){
    		id = "2";
    	}else if(taskId.equals("17041021281286332331")){
    		id = "3";
    	}else if(taskId.equals("17041021282734035637")){
    		id = "4";
    	}
		String sql = "SELECT supervise.*,item.*,item.problem as pro,type.name FROM task_supervise task LEFT JOIN supervise supervise ON task.flow = supervise.flow "
				+" AND task.dtid = supervise.did LEFT JOIN result_item_supervise item ON item.csid = supervise.id  AND item.tsid = task.id AND item.tid = task.tid "
				+" LEFT JOIN card_type type ON type.flow =  task.flow WHERE task.tid = '"+taskId+"' AND task.id ='"+id+"' ";
		List<Record> records= Db.find(sql);
		for(int i = 0 ;i < records.size();i++){
			String str = records.get(i).get("evaluation_criterion");
			String st = ";;";
			if(!"".equals(str) && str != null){
				records.get(i).set("evaluation_criterion", str.replaceAll(st, ""));
		
			}
		}
		setAttr("records",records);
	}
    
    public void exportFile() throws Exception{
    	String taskId = getPara("taskId");
    	String id = getPara("id");
    	
    	searchData(taskId,id);
    	List<Record> records = getAttr("records");
    	
    	String sql = "SELECT cname FROM task_supervise WHERE id ='"+id+"'";
    	Record name = Db.findFirst(sql);
    	
    	String srcPath = PathKit.getWebRootPath()+"/download/"+name.get("cname")+".xls";
    	
    	
    	FileOutputStream fileOut = new FileOutputStream(srcPath);
		HSSFWorkbook workbook = new HSSFWorkbook();
		workbook.createSheet();
		
		HSSFCellStyle setBorder = workbook.createCellStyle();  
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		setBorder.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
		setBorder.setWrapText(true);
		 HSSFFont font=workbook.createFont();
		 font.setFontHeightInPoints((short)12);
         font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 	
         setBorder.setFont(font);
		
		HSSFCellStyle setBorder1 = workbook.createCellStyle();  
		setBorder1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		setBorder1.setWrapText(true);
		setBorder1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
		
		HSSFSheet sheet = workbook.getSheetAt(0);
		sheet.createRow(records.size());
		String sqls = "SELECT sum(total) as total FROM result_item_supervise where total <0";
		Record total = Db.findFirst(sqls);
		String title[]={"序号","技术监督阶段","技术监督专业","监督项目","关键项权重","监督要点","监督依据","监督要求","监督结果","评价标准","存在问题","评价结果","条文解释","专业细分"};
		String tit[] = {"name","specialty_supervise","project_supervise","item_key","points_supervise","basis_supervise","require_supervise","result_supervise","evaluation_criterion","problem","total","explana","professional"};
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		double score = total.get("total");
		double  fen = 100;
		fen = fen+score;

 		cell.setCellValue(name.get("cname").toString()+"(总分"+fen+")");
		cell.setCellStyle(setBorder);
		row.setHeight((short) (500));
		
		 
		CellRangeAddress cellRange3 = new CellRangeAddress(0,0, 0, 13);
		sheet.addMergedRegion(cellRange3);
		for(int j = 0; j <= records.size() ;j++){
			row = sheet.createRow(1+j);
			int maxHei = 0;
			List<Object>  sss = new ArrayList<Object>();
			List<Object>  ssss = new ArrayList<Object>();
			for(int i = 0 ;i < title.length;i ++){
				if(j == 0){
					cell = row.createCell(i);
					cell.setCellValue(title[i]);
					if(i == 0 ){
						sheet.setColumnWidth(i, 10*256);
					}else if(i == 4){
						sheet.setColumnWidth(i, 15*256);
					}else{
						sheet.setColumnWidth(i, 25*256);
					}
					cell.setCellStyle(setBorder);
				}else{
					String str = "";
					cell = row.createCell(i);
					if(i == 0){
						cell.setCellValue(j);
						
					}else{
						if(records.get(j-1).get(tit[i-1]) != null){
							str = records.get(j-1).get(tit[i-1]).toString();
							cell.setCellValue(records.get(j-1).get(tit[i-1]).toString());
						}
					}
					int len = str.length() * 256; //和EXCEL宽度的转换
					int cellJLen = 6400;//获取列宽
					int s = 1;
					if(len == 0){
						s = 1;
					}else {
						s = len/cellJLen<2?2:len/cellJLen +1;
					}
					maxHei = Math.max(maxHei, s);
					cell.setCellStyle(setBorder1);
				}
			}
			if(j == 0){
				row.setHeight((short) (400));
			}else{
				row.setHeight((short) (maxHei*550));
			}
		}
		
		workbook.write(fileOut);
        fileOut.close();
        renderFile(new File(srcPath));
    }
	
}
