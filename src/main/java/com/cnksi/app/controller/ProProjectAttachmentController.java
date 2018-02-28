package com.cnksi.app.controller;

import com.cnksi.app.model.ProProject;
import com.cnksi.app.model.ProProjectAttachment;
import com.cnksi.app.model.ProProjectAttachmentImage;
import com.cnksi.kconf.controller.KController;
import com.cnksi.kcore.web.KWebQueryVO;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 *
 */
public class ProProjectAttachmentController extends KController {

    public ProProjectAttachmentController() {
        super(ProProjectAttachment.class);
    }

    public void index() {

        KWebQueryVO queryParam = super.doIndex();

        Optional.ofNullable(getPara("projectId"))
                .filter(x -> Objects.nonNull(x))
                .filter(x -> !x.isEmpty())
                .map(x -> {
                    setAttr("projectId", x);
                    return x;
                })
                .ifPresent(x -> queryParam.addFilter("pid", "=", x));

        Optional.ofNullable(getPara("attachType"))
                .map(x -> {
                    setAttr("attachType", x);
                    return x;
                })
                .ifPresent(x -> queryParam.addFilter("type", "=", x));

        Page<ProProjectAttachment> page = ProProjectAttachment.me.paginate(queryParam);
        page.getList()
                .stream()
                .filter(x -> x.getStr("url").startsWith("img:"))
                .forEach(x -> {
                    List<ProProjectAttachmentImage> proProjectAttachmentImages = ProProjectAttachmentImage.me.findListByPropertity("paid", x.getStr("id"));
                    x.put("_images", proProjectAttachmentImages);
                });

        setAttr("page", page);

        render("list.jsp");
    }


    public void edit() {

        super.doEdit();

        String idValue = getPara("id", getPara());
        ProProjectAttachment record = null;
        if (idValue != null) {
            record = ProProjectAttachment.me.findById(idValue);
        } else {
            record = new ProProjectAttachment();

        }
        setAttr("record", record);
        String projectId = getPara("projectId");
        String attachType = getPara("attachType");
        setAttr("projectId", projectId);
        setAttr("attachType", attachType);

        render("form.jsp");
    }

    public void download() {
        String attachmentId = getPara("attachmentId", getPara());
        ProProjectAttachment record = null;
        if (attachmentId != null) {
            record = ProProjectAttachment.me.findById(attachmentId);
            String fileUrl = record.get("url");
            String fileLocation = PathKit.getWebRootPath() + File.separator + JFinal.me().getConstants().getBaseDownloadPath() + File.separator + fileUrl;
            Path p = Paths.get(fileLocation);
            if (Files.exists(p)) {
                renderFile(fileUrl);
            }
        } else {
            bjuiAjax(300);
        }
    }

    public void save() {
        String projectId = getPara("projectId", getPara());
        if (projectId != null) {
            List<UploadFile> uploadFiles = this.getFiles("attachment/");
            if (uploadFiles.size() > 0) {
                UploadFile uploadFile = uploadFiles.get(0);
                ProProjectAttachment record = getModel(ProProjectAttachment.class, "record");
                record.set("url", "attachment/" + uploadFile.getFileName());
                record.save();
                bjuiAjax(200, true);
            }
        } else {
            List<UploadFile> uploadFiles = this.getFiles("attachment/");
            if (uploadFiles.size() > 0) {
                UploadFile uploadFile = uploadFiles.get(0);
                ProProjectAttachment record = getModel(ProProjectAttachment.class, "record");
                if ("规章制度".equals(record.getStr("type"))) {
                    record.set("url", "attachment/" + uploadFile.getFileName());
                    record.save();
                    HashMap<String, Object> resultMap = new HashMap<String, Object>();
                    resultMap.put("statusCode", "200");
                    resultMap.put("message", "操作成功");
                    resultMap.put("closeCurrent", true);
                    resultMap.put("tabid", "guizhangzhidu-list");
                    renderJson(resultMap);
                } else {
                    bjuiAjax(300);
                }
            } else {
                bjuiAjax(300);
            }
        }
    }


    public void delete() {
        ProProjectAttachment record = ProProjectAttachment.me.findById(getPara());
        if (record != null) {
            record.set("enabled", 1).update();
            bjuiAjax(200);
        } else {
            bjuiAjax(300);
        }

    }


    public void export() throws IOException {
        KWebQueryVO queryParam = super.doIndex();
        Page<ProProjectAttachment> p = ProProjectAttachment.me.paginate(queryParam);
        String xlsid = getPara("xlsid", "-1");
        super.export(xlsid, p.getList());
    }


    public void importxlsed() {
        String errorFile = "", msg = "";
        try {
            ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
            if (!result.isVerfiyFail()) {
                for (Map<String, Object> map : result.getList()) {
                    ProProjectAttachment record = new ProProjectAttachment();
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

}
