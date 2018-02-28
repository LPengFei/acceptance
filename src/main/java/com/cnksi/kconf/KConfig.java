package com.cnksi.kconf;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.cnksi.app.handler.XssHandler;
import com.cnksi.kconf.controller.DatasourceController;
import com.cnksi.kconf.controller.DepartmentController;
import com.cnksi.kconf.controller.IexportController;
import com.cnksi.kconf.controller.IndexController;
import com.cnksi.kconf.controller.SecureController;
import com.cnksi.kconf.controller.LookupController;
import com.cnksi.kconf.controller.ModelController;
import com.cnksi.kconf.controller.ModelFieldController;
import com.cnksi.kconf.controller.RoleController;
import com.cnksi.kconf.controller.UserController;
import com.cnksi.kconf.model.Authority;
import com.cnksi.kconf.model.Datasource;
import com.cnksi.kconf.model.Department;
import com.cnksi.kconf.model.Iefield;
import com.cnksi.kconf.model.Iexport;
import com.cnksi.kconf.model.Lookup;
import com.cnksi.kconf.model.LookupType;
import com.cnksi.kconf.model.Menu;
import com.cnksi.kconf.model.Role;
import com.cnksi.kconf.model.RoleAuthority;
import com.cnksi.kconf.model.RoleMenu;
import com.cnksi.kconf.model.User;
import com.cnksi.kconf.model.UserRole;
import com.cnksi.kcore.jfinal.model.BaseModel.Logical;
import com.cnksi.kcore.jfinal.model.KModel;
import com.cnksi.kcore.jfinal.model.KModelField;
import com.cnksi.kcore.utils.SystemInfo;
import com.cnksi.util.ScriptRunner;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.ModelRecordElResolver;
import com.jfinal.plugin.activerecord.generator.ColumnMeta;
import com.jfinal.plugin.activerecord.generator.TableMeta;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;

/**
 * BaseConfig
 *
 * @author joe
 */
public class KConfig extends JFinalConfig {
    public final static String SESSION_USER_KEY = "_login_user_key";
    public final static String SESSION_DEPT_KEY = "_login_dept_key";
    public final static String appid = "app";

    protected ActiveRecordPlugin arp = null;

    @Override
    public void configConstant(Constants me) {
        // 加载少量必要配置，随后可用PropKit.get(...)获取值
        PropKit.use("application.conf");
        me.setDevMode(PropKit.getBoolean("devMode", false));
        me.setViewType(ViewType.JSP);
        me.setBaseViewPath("/WEB-INF/jsp");
//        me.setError500View("/WEB-INF/jsp/error/500.jsp");
//        me.setError401View("/WEB-INF/jsp/error/401.jsp");
//        me.setError404View("/WEB-INF/jsp/error/404.jsp");
        me.setUrlParaSeparator("--");
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/secure", SecureController.class, "/secure");
        me.add("/" + appid, IndexController.class, "/index");
        me.add("/kconf/model", ModelController.class, "/kconf");
        me.add("/kconf/field", ModelFieldController.class, "/kconf");
        me.add("/kconf/iexport", IexportController.class, "/kconf");// 导入导出配置
        me.add("/kconf/department", DepartmentController.class, "/kconf");
        me.add("/kconf/lookup", LookupController.class, "/kconf");
        me.add("/kconf/role", RoleController.class, "/kconf");
        me.add("/kconf/user", UserController.class, "/kconf");
        me.add("/kconf/datasource", DatasourceController.class, "/kconf");
    }

    @Override
    public void configPlugin(Plugins me) {
        DruidPlugin druidPlugin;
        if (PropKit.getBoolean("devMode", false)) {
            druidPlugin = new DruidPlugin(PropKit.get("db.dev_jdbcUrl"), PropKit.get("db.dev_user", "root"), PropKit.get("db.dev_password", "root").trim());
        } else {
            druidPlugin = new DruidPlugin(PropKit.get("db.prod_jdbcUrl"), PropKit.get("db.prod_user", "root"), PropKit.get("db.prod_password", "root").trim());
        }
        druidPlugin.setInitialSize(PropKit.getInt("db.initialSize", 2));
        druidPlugin.setMaxActive(PropKit.getInt("db.maxActive", 3));
        druidPlugin.setMinIdle(PropKit.getInt("db.minIdle", 1));
        me.add(druidPlugin);

        // 配置ActiveRecord插件
        arp = new ActiveRecordPlugin(druidPlugin);
        arp.setShowSql(PropKit.getBoolean("db.showSql", true));

        // 数据模型配置，动态模型
        arp.addMapping("k_model", "mid", KModel.class);
        arp.addMapping("k_model_field", "mfid", KModelField.class);
        arp.addMapping("k_iefield", "iefid", Iefield.class);
        arp.addMapping("k_iexport", "ieid", Iexport.class);

        arp.addMapping("k_authority", "aid", Authority.class);
        arp.addMapping("k_department", "did", Department.class);
        arp.addMapping("k_lookup", "lkid", Lookup.class);
        arp.addMapping("k_lookup_type", "ltid", LookupType.class);
        arp.addMapping("k_menu", "menuid", Menu.class);
        arp.addMapping("k_role", "rid", Role.class);
        arp.addMapping("k_role_authority", "raid", RoleAuthority.class);
        arp.addMapping("k_role_menu", "rmid", RoleMenu.class);
        arp.addMapping("k_user", "uid", User.class);
        arp.addMapping("k_user_role", "urid", UserRole.class);
        arp.addMapping("k_datasource", "dsid", Datasource.class);

        me.add(arp);
    }

    @Override
    public void configInterceptor(Interceptors me) {

    }

    @Override
    public void configHandler(Handlers me) {
        /**
         * 添加XSS攻击字符串过滤器(全局)
         */
        me.add(new XssHandler(""));
    }

    @Override
    public void afterJFinalStart() {
        super.afterJFinalStart();

        ModelRecordElResolver.setResolveBeanAsModel(true);

//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//
//            @Override
//            public void run() {
//				SystemInfo.getInstance().init();
//				SystemInfo.getInstance().PrintInfo();
//            }
//        }, 1000, 60000);

        // // 只在开发模式下更新只菜单
        // if (PropKit.getBoolean("devMode")) {
        // for (String key : JFinal.me().getAllActionKeys()) {
        // // 判断当前action是否存在
        // Resource _res = Resource.me.findByPropertity("value", key);
        // if (_res == null) {
        // Resource res = new Resource();
        // res.set("value", key);
        //
        // // 设定父级资源
        // Resource r = res.findByPropertity("value", key.substring(0, key.lastIndexOf("/")));
        // if (r != null) {
        // res.set("pid", r.get("menuid"));
        // }
        // res.save();
        // }
        // }
        // }

    }

    // 向ConfModel中插入数据
    protected void insertToModel(List<TableMeta> build) {

        for (TableMeta tableMeta : build) {
            if (!StrKit.isBlank(tableMeta.primaryKey)) {

                KModel model = KModel.me.findByPropertity("mtable", tableMeta.name);
                if (model == null) {
                    model = new KModel();
                    model.set("mtable", tableMeta.name);
                    model.set("mname", tableMeta.remarks);
                    model.save();
                }

                int i = 0;
                for (ColumnMeta colMeta : tableMeta.columnMetas) {
                    KModelField field = KModelField.me.findByPropertity(new String[]{"mid", "field_name"}, new Object[]{model.getInt("mid"), colMeta.name}, Logical.AND);
                    if (field == null) {
                        field = new KModelField();
                        field.set("mid", model.getInt("mid"));
                        field.set("field_alias", colMeta.remarks);
                        field.set("field_name", colMeta.name);

                        field.set("type", colMeta.type);

                        field.set("is_system", "1");
                        field.set("is_required", colMeta.isNullable == "NO" ? "1" : "0");

                        if ("enabled,last_modify_time,create_time".contains(colMeta.name)) {
                            field.set("is_list_view", "0");
                            field.set("list_sort", i);
                            field.set("is_form_view", "0");
                            field.set("form_sort", i);
                        } else {
                            field.set("is_list_view", "1");
                            field.set("list_sort", i);
                            field.set("is_form_view", "1");
                            field.set("form_sort", i);
                        }

                        field.save();
                        i = i + 5;
                    }
                }
            }
        }
    }

    protected void insertToIExport(List<TableMeta> build) {
        for (TableMeta tableMeta : build) {
            Iexport iemport = Iexport.me.findByPropertity(new String[]{"ietable", "ietype"}, new String[]{tableMeta.name, "导入"}, Logical.AND);
            if (iemport == null) {
                iemport = new Iexport();
                iemport.set("ietable", tableMeta.name);
                iemport.set("ietype", "导入");
                iemport.save();
            }

            Iexport exmport = Iexport.me.findByPropertity(new String[]{"ietable", "ietype"}, new String[]{tableMeta.name, "导出"}, Logical.AND);
            if (exmport == null) {
                exmport = new Iexport();
                exmport.set("ietable", tableMeta.name);
                exmport.set("ietype", "导出");
                exmport.save();
            }

            int i = 0;
            for (ColumnMeta colMeta : tableMeta.columnMetas) {
                if ("enabled,last_modify_time,create_time".contains(colMeta.name)) {
                    continue;
                }

                Iefield importField = Iefield.me.findByPropertity(new String[]{"ieid", "field_name"}, new Object[]{iemport.getInt("ieid"), colMeta.name}, Logical.AND);
                if (importField == null) {
                    importField = new Iefield();
                    importField.set("ieid", iemport.getInt("ieid"));
                    importField.set("field_alias", colMeta.remarks);
                    importField.set("field_name", colMeta.name);
                    importField.set("width", "20");
                    importField.set("sort", i);
                    importField.set("type", 1);
                    importField.set("allow_blank", 0);
                    importField.save();
                    i = i + 5;
                }

                Iefield exportField = Iefield.me.findByPropertity(new String[]{"ieid", "field_name"}, new Object[]{exmport.getInt("ieid"), colMeta.name}, Logical.AND);
                if (exportField == null) {
                    exportField = new Iefield();
                    exportField.set("ieid", exmport.getInt("ieid"));
                    exportField.set("field_alias", colMeta.remarks);
                    exportField.set("field_name", colMeta.name);
                    exportField.set("width", "20");
                    exportField.set("sort", i);
                    exportField.set("type", 1);
                    exportField.set("allow_blank", 0);
                    exportField.save();
                    i = i + 5;
                }
            }
        }
    }

    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }
}
