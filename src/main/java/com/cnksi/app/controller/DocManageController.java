package com.cnksi.app.controller;

import com.cnksi.app.model.*;
import com.cnksi.kconf.controller.KController;
import com.cnksi.util.WordUtil;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import freemarker.template.TemplateException;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by gaozhou on 2017/2/7.
 */
public class DocManageController extends KController {
    public DocManageController() {
        super(ProProject.class);
    }

    public void index() {
        setAttr("projectId", getPara("projectId"));
        setAttr("projects", ProProject.me.findAll().stream().filter(x -> x.getInt("enabled") == 0).collect(Collectors.toList()));
        render("index.jsp");
    }

    public void doc1() {
        List<ProProjectAttachment> proProjectAttachments = ProProjectAttachment.me.findListByPropertitys(new String[]{"enabled", "type", "pid"}, new Object[]{0, "档案", getPara("projectId")}, ProProjectAttachment.Logical.AND)
                .stream()
                .filter(x -> x.getStr("url").startsWith("img:"))
                .map(x -> {
                    List<ProProjectAttachmentImage> proProjectAttachmentImages = ProProjectAttachmentImage.me.findListByPropertity("paid", x.getStr("id"));
                    x.put("_images", proProjectAttachmentImages);
                    return x;
                })
                .collect(Collectors.toList());
        setAttr("docs", proProjectAttachments);
        render("doc1.jsp");
    }

    public void doc2() {
        String projectId = getPara("projectId");
        List<Record> flows = Db.find("select * from " + SysConfig.me.getTableName() + " where enabled = 0 and `type` = ? order by sort", "flow");
        Map<String, List<Task>> checks = flows.stream()
                .reduce(new HashMap<String, List<Task>>(), (acc, x) -> {
                    String flowKey = x.getStr("key");
                    if (!acc.containsKey(flowKey)) {
                        String sql = String.format("select * from %s where enabled = 0 and pid = ? and flow = ? order by time", Task.me.getTableName());
                        List<Task> tasks = Task.me.find(sql, projectId, x.getStr("key"))
                                .stream()
                                .map(y -> Task.getTaskInfoById(y.getStr("id")))
                                .map(y -> {
                                    List<String> images = y.<List<TaskCard>>get("_taskCards")
                                            .parallelStream()
                                            .flatMap(z -> z.<List<CardItemType>>get("_cardItemTypes").stream())
                                            .flatMap(z -> z.<List<CardItem>>get("_cardItems").stream())
                                            .map(z -> z.<ResultItem>get("_resultItem"))
                                            .filter(Objects::nonNull)
                                            .filter(z -> !z.getStr("images").isEmpty())
                                            .flatMap(z -> Stream.of(z.getStr("images").split(",")))
                                            .collect(Collectors.toList());
                                    y.put("_images", images);

                                    return y;
                                })
                                .filter(y -> !y.<List<String>>get("_images").isEmpty())
                                .collect(Collectors.toList());
                        acc.put(x.getStr("key"), tasks);
                    }
                    return acc;
                }, (x, y) -> x);
        setAttr("flows", flows);
        setAttr("checks", checks);
        render("doc2.jsp");
    }

    public void doc3() {
        setAttr("bdzid", getPara("bdzid"));
        render("doc3.jsp");
    }

    public void docFile() throws DocumentException, TemplateException, IOException {
        final String genDocPath = PathKit.getWebRootPath() + File.separator + "upload" + File.separator;
        final String templatePath = PathKit.getWebRootPath() + File.separator + "WEB-INF" + File.separator + "template_doc" + File.separator + "bdz.xml";

        ProProject project = ProProject.me.findById(getPara("projectId"));
        List<ResultIssue> resultIssueImportants = ResultIssue.getIssuesImportantByProjectId(project.getStr("id"));

        String sql = String.format("select * from task where enabled = 0 and pid = ? order by time limit 1", Task.me.getTableName());

        String tempFileName = project.getStr("name") + "-报告";
        String genDoc = genDocPath + tempFileName + ".doc";
        WordUtil.generateWord(new HashMap<String, Object>() {{
            put("pname", project.getStr("name"));
            put("ysdw", "国网吴忠供电公司");
            Optional.ofNullable(Task.me.findFirst(sql, project.getStr("id")))
                    .ifPresent(x ->
                            put("date", LocalDate.parse(x.getStr("time"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                                    .format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")))
                    );
            put("date2", "____年__月__日");
            put("jsdw", project.getStr("gldw"));
            put("sgdw", project.getStr("sgdw"));
            put("bdz_name", project.getStr("bdz"));
            put("fr", project.getStr("fr"));
            put("sjdw", project.getStr("sjdw"));
            put("jldw", project.getStr("jldw"));
            put("yxdw", project.getStr("yxdw"));
            put("scount", resultIssueImportants.size());
            put("issues", resultIssueImportants.isEmpty() ? "无影响设备投运的缺陷。" : "有如下重大问题:");
            put("issues_desc", resultIssueImportants.stream().map(x -> x.getStr("description")).collect(Collectors.toList()));
        }}, templatePath, genDoc);
        if (Files.isRegularFile(Paths.get(genDoc))) {
            render(new TempFileRender(tempFileName + ".doc", genDoc));
        }
    }
}
