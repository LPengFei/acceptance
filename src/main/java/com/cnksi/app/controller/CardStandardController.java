package com.cnksi.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.cnksi.app.model.CardItem;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.cnksi.kcore.web.KWebQueryVO;
import com.cnksi.kconf.controller.KController;
import com.cnksi.app.model.CardStandard;
import com.cnksi.app.model.ResultIssue;

import java.util.List;

/**
 *
 */
public class CardStandardController extends KController {

    public CardStandardController() {
        super(CardStandard.class);
    }

    public void index() {
        KWebQueryVO queryParam = super.doIndex();
        String ciid = getPara("ciid");
        queryParam.addFilter("ciid", "=", ciid);
        setAttr("page", CardStandard.me.paginate(queryParam));
        setAttr("ciid", ciid);
        render("list.jsp");
    }


    public void edit() {

        super.doEdit();

        String idValue = getPara("id", getPara());
        CardStandard record;
        String ciid;
        if (idValue != null) {
            record = CardStandard.me.findById(idValue);
            ciid = record.get("ciid");
        } else {
            record = new CardStandard();
            ciid = getPara("ciid");
        }
        setAttr("record", record);
        setAttr("cardItem", CardItem.me.findById(ciid));

        render("form.jsp");
    }


    public void save() {
        CardStandard record = getModel(CardStandard.class, "record");
        if (record.get("id") != null) {
            record.update();
        } else {
            record.save();
        }

        bjuiAjax(200, true);
    }


    public void delete() {
        CardStandard record = CardStandard.me.findById(getPara());
        if (record != null) {
            record.set("enabled", 1).update();
            bjuiAjax(200);
        } else {
            bjuiAjax(300);
        }

    }


    public void export() throws IOException {
        KWebQueryVO queryParam = super.doIndex();
        Page<CardStandard> p = CardStandard.me.paginate(queryParam);
        String xlsid = getPara("xlsid", "-1");
        super.export(xlsid, p.getList());
    }


    public void importxlsed() {
        String errorFile = "", msg = "";
        try {
            ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
            if (!result.isVerfiyFail()) {
                for (Map<String, Object> map : result.getList()) {
                    CardStandard record = new CardStandard();
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


    public void viewCardStandard() {
        String cardItemId = getPara();
        List<CardStandard> cardStandards = CardStandard.me.getCardStandardsByCardItemId(cardItemId);
        renderJson(cardStandards);
    }

    public void viewProblems() {
        String cardItemId = getPara();
        List<ResultIssue> resultIssues = ResultIssue.me.getProblemsByCardItemId(cardItemId);
        renderJson(resultIssues);
    }


}
