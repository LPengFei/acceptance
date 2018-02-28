package com.cnksi.app.controller;

import java.io.IOException;
import java.util.Map;

import com.cnksi.app.model.Card;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.cnksi.kcore.web.KWebQueryVO;
import com.cnksi.kconf.controller.KController;
import com.cnksi.app.model.CardItemType;

/**
 *
 */
public class CardItemTypeController extends KController {

    public CardItemTypeController() {
        super(CardItemType.class);
    }

    public void index() {
        KWebQueryVO queryParam = super.doIndex();
        String cid = getPara("cid");
        if (cid != null) {
            queryParam.addFilter("cid", "=", cid);
        }
        setAttr("page", CardItemType.me.paginate(queryParam));
        setAttr("cid", cid);
        render("list.jsp");
    }


    public void edit() {

        super.doEdit();

        String idValue = getPara("id", getPara());
        CardItemType record;
        String cid;
        if (idValue != null) {
            record = CardItemType.me.findById(idValue);
            cid = record.get("cid");
        } else {
            record = new CardItemType();
            cid = getPara("cid");
        }
        setAttr("record", record);
        setAttr("card", Card.me.findById(cid));

        render("form.jsp");
    }


    public void save() {
        CardItemType record = getModel(CardItemType.class, "record");
        if (record.get("id") != null) {
            record.update();
        } else {
            record.save();
        }

        bjuiAjax(200, true);
    }


    public void delete() {
        CardItemType record = CardItemType.me.findById(getPara());
        if (record != null) {
            record.set("enabled", 1).update();
            bjuiAjax(200);
        } else {
            bjuiAjax(300);
        }

    }


    public void export() throws IOException {
        KWebQueryVO queryParam = super.doIndex();
        Page<CardItemType> p = CardItemType.me.paginate(queryParam);
        String xlsid = getPara("xlsid", "-1");
        super.export(xlsid, p.getList());
    }


    public void importxlsed() {
        String errorFile = "", msg = "";
        try {
            ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
            if (!result.isVerfiyFail()) {
                for (Map<String, Object> map : result.getList()) {
                    CardItemType record = new CardItemType();
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
