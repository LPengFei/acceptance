package com.cnksi.app.controller;

import com.cnksi.app.model.*;
import com.cnksi.app.service.PmsBdzService;
import com.cnksi.app.service.PmsSpacingService;
import com.cnksi.exception.MsgException;
import com.cnksi.kconf.controller.KController;
import com.cnksi.kcore.jfinal.model.BaseModel;
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
public class PmsBdzController extends KController {

    public PmsBdzController() {
        super(PmsBdz.class);
    }

    public void index() {

        KWebQueryVO queryParam = super.doIndex();

        setAttr("page", PmsBdz.me.paginate(queryParam));

        render("index.jsp");
    }


    public void edit() {

        super.doEdit();

        String idValue = getPara("id", getPara());
        PmsBdz record = null;
        if (idValue != null) {
            record = PmsBdz.me.findById(idValue);
        } else {
            record = new PmsBdz();

        }
        setAttr("record", record);

        render(formJsp);
    }


    public void save() {
        PmsBdz record = getModel(PmsBdz.class, "record");
        if (record.get("id") != null) {
            record.update();
        } else {
            record.save();
        }

        bjuiAjax(200, true);
    }


    public void delete() {
        PmsBdz record = PmsBdz.me.findById(getPara());
        if (record != null) {
            record.set("enabled", 1).update();
            bjuiAjax(200);
        } else {
            bjuiAjax(300);
        }

    }

    /**
     * 同步变电站列表
     */


    public void list() {
        setAttr("bdzid", getPara("bdzid"));
        KWebQueryVO queryParam_v1 = super.doIndex();

        setAttr("page", PmsBdz.me.paginate(queryParam_v1));

        render("list.jsp");

    }


    public void syncBdzlist() {
        try {

            String account = getPara("account");
            if (StrKit.isBlank(account)) throw new MsgException("PMS用户名不能为空！");
            String pwd = getPara("pwd");
            if (StrKit.isBlank(pwd)) throw new MsgException("PMS密码不能为空！");

            PmsBdzService.service.sync(account, pwd);

            bjuiAjax(200);
        } catch (MsgException e) {

            System.out.println(e.getMessage());
            bjuiAjax(300);
        }

    }

    /**
     * 同步变电站下的台账
     */
    public void syncDevice() {
        try {

            String account = getPara("account");
            if (StrKit.isBlank(account)) throw new MsgException("PMS用户名不能为空！");
            String pwd = getPara("pwd");
            if (StrKit.isBlank(pwd)) throw new MsgException("PMS密码不能为空！");
            String pmsid = getPara("pmsid");
            if (StrKit.isBlank(pmsid)) throw new MsgException("必须选择变电站！");

            //同步间隔列表
            PmsSpacingService.service.sync(pmsid, account, pwd);

            bjuiAjax(200);
        } catch (MsgException e) {

            System.out.println(e.getMessage());
            bjuiAjax(300);
        }
    }


    public void export() throws IOException {
        KWebQueryVO queryParam = super.doIndex();
        Page<PmsBdz> p = PmsBdz.me.paginate(queryParam);
        String xlsid = getPara("xlsid", "-1");
        super.export(xlsid, p.getList());
    }


    public void importxlsed() {
        String errorFile = "", msg = "";
        try {
            ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
            if (!result.isVerfiyFail()) {
                for (Map<String, Object> map : result.getList()) {
                    PmsBdz record = new PmsBdz();
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

    public void saveBdz() {
        String id = getPara("id");
        String name = getPara("name");
        Optional.ofNullable(PmsBdz.me.findById(id))
                .ifPresent(x -> x.set("pms_BDZMC", name).update());
        bjuiAjax(200);
    }
    public void saveDevice() {
        String id = getPara("id");
        String pId = getPara("pId");
        String bId = getPara("bId");
        String name = getPara("name");
        Optional.ofNullable(PmsDevice.me.findById(id))
                .map(x -> x.set("pms_SBMC", name).update())
                .orElseGet(() -> new PmsDevice().set("pms_SBMC", name).set("bid", bId).set("sid", pId).save());
        bjuiAjax(200);
    }
    public void saveSpace() {
        String id = getPara("id");
        String pId = getPara("pId");
        String name = getPara("name");
        Optional.ofNullable(PmsSpacing.me.findById(id))
                .map(x -> x.set("pms_JGDYMC", name).update())
                .orElseGet(() -> new PmsSpacing().set("pms_JGDYMC", name).set("bid", pId).save());
        bjuiAjax(200);
    }

    public void PmsBdzTree() {
        String bdzid = getPara("bdzid");
        String id = getPara("id");
        String hierarchy = getPara("hierarchy");
        renderJson(PmsBdz.getPmsTreeJson(bdzid, id, hierarchy));
    }

    public void pmsDeviceInfo() {
        setAttr("pms_deviceId", getPara("pms_deviceId"));
        Optional.ofNullable(PmsDevice.me.findById(getPara("pms_deviceId")))
                .ifPresent(x -> setAttr("deviceId", x.getStr("dtid")));
        setAttr("imageMap",
                DeviceImg.me.findListByPropertitys(new String[]{"enabled", "did"}, new Object[]{0, getPara("pms_deviceId")}, DeviceImg.Logical.AND)
                .stream()
                .collect(Collectors.groupingBy(x -> x.getStr("type"))));
        render("pms_device_info.jsp");
    }
    public void pmsDeviceImageInfo() {
        setAttr("pms_deviceId", getPara("pms_deviceId"));
        setAttr("imageMap",
                DeviceImg.me.findListByPropertitys(new String[]{"enabled", "did"}, new Object[]{0, getPara("pms_deviceId")}, DeviceImg.Logical.AND)
                        .stream()
                        .collect(Collectors.groupingBy(x -> x.getStr("type"))));
        render("pms_device_image_info.jsp");
    }

    public void deleteSpace() {
        String id = getPara("id");
        if (PmsDevice.me.findListByPropertity("sid", id).size() != 0) {
            bjuiAjax(300);
        } else {
            PmsSpacing.me.findById(id).set("enabled", 1).update();
            bjuiAjax(200);
        }
    }
    public void deleteDevice() {
        String id = getPara("id");
        PmsDevice.me.findById(id).set("enabled", 1).update();
        bjuiAjax(200);
    }



}
