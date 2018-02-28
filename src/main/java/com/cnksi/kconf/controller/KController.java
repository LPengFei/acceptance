package com.cnksi.kconf.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.jeecgframework.poi.excel.entity.params.ExcelImportEntity;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.excel.export.styler.ExcelExportStylerDefaultImpl;
import org.jeecgframework.poi.util.PoiPublicUtil;

import com.cnksi.kconf.KConfig;
import com.cnksi.kconf.excel.KExcelDataHandler;
import com.cnksi.kconf.excel.KVerifyHanlder;
import com.cnksi.kconf.model.Iefield;
import com.cnksi.kconf.model.Iexport;
import com.cnksi.kconf.model.User;
import com.cnksi.kcore.jfinal.model.BaseModel.Logical;
import com.cnksi.kcore.jfinal.model.KModel;
import com.cnksi.kcore.jfinal.model.KModelField;
import com.cnksi.kcore.jfinal.model.KModelField.Setting;
import com.cnksi.kcore.web.KWebQueryVO;
import com.jfinal.core.Controller;
import com.jfinal.core.Injector;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;
import com.jfinal.upload.UploadFile;

public class KController extends Controller {
    protected static final String formJsp = "/WEB-INF/jsp/common/common_form.jsp";
    protected static final String listJsp = "/WEB-INF/jsp/common/common_list.jsp";
    protected static final String importJsp = "/WEB-INF/jsp/common/_import.jsp";
    protected String modelName = "", tableName = "", tabid = "";
    protected Class<? extends Model<?>> modelClass = null;
    protected Table table = null;

    public KController(Class<? extends Model<?>> modelClass) {
        table = TableMapping.me().getTable(modelClass);
        tableName = table.getName();
        modelName = modelClass.getSimpleName().toLowerCase();
        tabid = modelName + "-list";
    }

    /**
     * 列表查询前的处理
     *
     * @return KWebQueryVO 返回类型
     */
    protected KWebQueryVO doIndex() {
        return doIndex(KModel.me.findByTableName(tableName), null);
    }

    protected KWebQueryVO doIndex(Class<?> cls) {
        return doIndex(KModel.me.findByTableName(tableName), cls);
    }

    /**
     * <pre>
     * 1、处理Excel导入导出
     * 2、获取数据库配置的动态参数
     * </pre>
     *
     * @return
     */
    protected KWebQueryVO doIndex(KModel m, Class<?> cls) {
        // 导出Excel配置
        List<Iexport> exports = Iexport.me.findListByPropertitys(new String[]{"ietable", "enabled", "ietype"}, new String[]{tableName, "0", "导出"}, Logical.AND);
        // 导入Excel配置
        List<Iexport> imports = Iexport.me.findListByPropertitys(new String[]{"ietable", "enabled", "ietype"}, new String[]{tableName, "0", "导入"}, Logical.AND);

        KWebQueryVO queryParam = null;
        if (cls == null) {
            queryParam = new KWebQueryVO();
            queryParam.setTableName(tableName);
            queryParam.setPageSize(getParaToInt("pageSize", queryParam.getPageSize()));
            queryParam.setPageNumber(getParaToInt("pageNumber", queryParam.getPageNumber()));
            queryParam.setOrderField(getPara("orderField", queryParam.getOrderField()));
            queryParam.setOrderDirection(getPara("orderDirection", queryParam.getOrderDirection()));
        } else {
            queryParam = (KWebQueryVO) Injector.injectBean(cls, null, getRequest(), false);
        }
        List<KModelField> fields = null;
        if (m != null) {
            fields = m.getListViewField();

            for (KModelField field : fields) {
                String fname = field.getStr("field_name");
                // 将setting字符串转化为json对象方便操作
                Setting setting = field.getSettings();
                field.set("settings", setting);

                // 获取查询条件，并将之设置到queryVO
                String searchOp = setting.getListview().getString("list_search_op");
                String requstVal = getPara(fname);
                // System.err.println(String.join(",", fname, searchOp, requstVal));
                if (StrKit.notBlank(searchOp, requstVal)) {
                    if ("like".equals(searchOp.trim())) {
                        requstVal = "%" + requstVal + "%";
                    }
                    queryParam.addFilter(fname, " " + searchOp + " ", requstVal);
                }
            }
        }

        setAttr("model", m);
        setAttr("fields", fields);
        setAttr("imports", imports);
        setAttr("exports", exports);
        setAttr("query", queryParam);
        setAttr("modelName", modelName);
        setAttr("pkName", table.getPrimaryKey()[0]);

        keepPara();

        return queryParam;
    }

    /**
     * 编辑页面，查询数据库配置的字段信息
     */
    protected void doEdit(KModel m) {
        if (m != null) {
            List<KModelField> fields = m.getFormViewField();
            fields.forEach(f -> f.set("settings", f.getSettings()));
            m.put("fields", fields);
        }
        setAttr("model", m);
        setAttr("fields", m != null ? m.get("fields") : null);
        setAttr("pkName", table.getPrimaryKey()[0]);
        setAttr("modelName", modelName);
    }

    protected void doEdit() {
        doEdit(KModel.me.findByTableName(tableName));
    }

    /**
     * 下载导入错误的excel文件
     */
    public void downerrfile() {
        String fileName = getPara("fname");
        String path = PoiPublicUtil.getWebRootPath("upload");
        File file = new File(path + "/" + fileName);
        if (file.exists()) {
            renderFile(file);
        } else {
            renderNull();
        }
    }

    /**
     * 导出Excel模板
     *
     * @throws IOException
     */
    public void exportpl() throws IOException {
        String xlsid = getPara("xlsid", "1");
        export(xlsid, null);
    }

    /**
     * 导航到数据导入页面
     */
    public void importxls() {
        keepPara();
        setAttr("modelName", modelName);
        setAttr("appid", KConfig.appid);
        render(importJsp);
    }

    /**
     * 根据配置的模型导出数据
     *
     * @param xlsid 模型id
     * @param data
     * @throws IOException
     * @throws IOException
     */
    protected void export(Object xlsid, List<?> data) throws IOException {
        Iexport m = Iexport.me.findById(xlsid);
        if (m != null) {
            List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
            // List<Iefield> fileds = Iefield.me.findListByPropertity("ieid", m.get("ieid"));
            List<Iefield> fileds = Iefield.me.findListByPropertitys(new String[]{"ieid", "enabled"}, new Object[]{m.get("ieid"), "0"}, Logical.AND);
            for (Iefield f : fileds) {
                ExcelExportEntity eee = new ExcelExportEntity(f.getStr("field_name"), f.getStr("field_alias"), f.getInt("width") == null ? 15 : f.getInt("width"));
                eee.setWrap(true);
                if (StrKit.notBlank(f.getStr("format"))) {
                    eee.setFormat(f.getStr("format"));
                }

                if (f.getInt("text_align") != null && 1 == f.getInt("text_align")) {
                    eee.setTextAlign(CellStyle.ALIGN_LEFT);
                } else {
                    eee.setTextAlign(CellStyle.ALIGN_CENTER);
                }

                eee.setType(f.getInt("type"));
                if (f.getInt("sort") != null)
                    eee.setOrderNum(f.getInt("sort"));
                entity.add(eee);

            }

            File xlsFile = new File(m.getStr("iename") + ".xls");
            FileOutputStream fout = new FileOutputStream(xlsFile);

            ExportParams ep = new ExportParams(m.getStr("iename"), "sheet1");
            KExcelDataHandler handler = new KExcelDataHandler(fileds);
            ep.setDataHanlder(handler);

            ep.setStyle(ExcelExportStylerDefaultImpl.class);
            Workbook workbook = ExcelExportUtil.exportExcel(ep, entity, data);
            workbook.write(fout);
            renderFile(xlsFile);
        } else {
            renderError(404);
        }
    }

    /**
     * 根据模板ID，解析上传的Excel文件
     *
     * @param xlsid
     * @param xlsFile
     */
    protected ExcelImportResult<Map<String, Object>> importxls(String xlsid, UploadFile xlsFile) {

        ImportParams params = new ImportParams();

        Iexport m = Iexport.me.findById(xlsid);
        if (m != null) {
            params.setTitleRows(1);
            params.setHeadRows(1);
            // 必填包含的列头
            List<String> requiredCol = new ArrayList<String>();
            List<Iefield> fileds = Iefield.me.findListByPropertity("ieid", m.get("ieid"));
            for (Iefield f : fileds) {
                if (1 == f.getInt("required")) {
                    requiredCol.add(f.getStr("field_alias"));
                }

                ExcelImportEntity eie = new ExcelImportEntity();
                if (StrKit.notBlank(f.getStr("format"))) {
                    eie.setFormat(f.getStr("format"));
                }
                if (1 == f.getInt("allow_blank")) {
                    eie.setAllowBlank(false);
                }
                eie.setName(f.getStr("field_alias"));
                eie.setKey(f.getStr("field_name"));
                eie.setType(f.getInt("type")); // 设定导入列的类型
                params.addExcelImportEntity(eie);
            }

            // params.setSaveUrl(""); // 服务器端临时文件保存的位置
            params.setNeedSave(true);
            params.setSaveUrl("upload");
            /**
             * 导入时校验数据模板,是不是正确的Excel
             */
            String[] importFields = new String[requiredCol.size()];
            requiredCol.toArray(importFields);
            params.setImportFields(importFields);

            KExcelDataHandler dataHandler = new KExcelDataHandler(fileds);
            params.setDataHanlder(dataHandler);

            // 数据校验
            params.setVerifyHanlder(new KVerifyHanlder(fileds));
        }

        return ExcelImportUtil.importExcelVerify(xlsFile.getFile(), Map.class, params);
    }

    /**
     * 不关闭
     */
    protected Map<String, Object> bjuiAjax(int statusCode) {
        return bjuiAjax(statusCode == 200 ? "操作成功" : "操作失败", statusCode);
    }

    protected Map<String, Object> bjuiAjax(int statusCode, String listId, boolean closeCurrent) {
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("statusCode", statusCode);
        resultMap.put("message", statusCode == 200 ? "操作成功" : "操作失败");
        resultMap.put("closeCurrent", closeCurrent);
        resultMap.put("tabid", listId);
        renderJson(resultMap);
        return resultMap;
    }


    /**
     * 关闭
     */
    protected Map<String, Object> bjuiAjax(int statusCode, boolean closeCurrent) {
        return bjuiAjax(statusCode == 200 ? "操作成功" : "操作失败", statusCode, closeCurrent);
    }

    /**
     * @param msg
     */
    protected Map<String, Object> bjuiAjax(String msg) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("statusCode", "200");
        resultMap.put("message", msg);
        resultMap.put("closeCurrent", false);
        resultMap.put("tabid", modelName + "-list");
        renderJson(resultMap);
        return resultMap;
    }

    protected Map<String, Object> bjuiAjax(String msg, int statusCode) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("statusCode", statusCode);
        resultMap.put("message", msg);
        resultMap.put("closeCurrent", false);
        resultMap.put("tabid", modelName + "-list");
        renderJson(resultMap);
        return resultMap;
    }

    protected Map<String, Object> bjuiAjax(String msg, int statusCode, boolean closeCurrent) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("statusCode", statusCode);
        resultMap.put("message", msg);
        resultMap.put("closeCurrent", closeCurrent);
        resultMap.put("tabid", modelName + "-list");
        renderJson(resultMap);
        return resultMap;
    }

    /**
     * 当前登录用户
     *
     * @return
     */
    protected User getLoginUser() {

        User user = getSessionAttr(KConfig.SESSION_USER_KEY);

        if (user == null) {
            String uid = getPara("uid");
            if (StringUtils.isNotBlank(uid)) {
                user = User.me.findById(uid);
            }
        }

        return user;
    }

    protected <T> List<T> getModels(Class<T> modelClass, String modelName) {
        List<String> names = new LinkedList<>();
        for (Enumeration<String> e = getParaNames(); e.hasMoreElements(); ) {
            String name = e.nextElement();
            if (name.startsWith(modelName)) {
                String realName = name.split("\\.")[0];
                if (!names.contains(realName)) {
                    names.add(realName);
                }
            }
        }
        List<T> records = new ArrayList<>();
        for (String name : names) {
            records.add(getModel(modelClass, name));
        }

        return records;
    }
}
