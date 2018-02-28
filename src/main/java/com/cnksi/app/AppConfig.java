package com.cnksi.app;

import java.util.UUID;

import com.cnksi.app.controller.DocManageController;
import com.cnksi.app.controller.StatisticController;
import org.apache.log4j.Logger;

import com.cnksi.app.controller.PmsController;
import com.cnksi.app.controller._RouteKit;
import com.cnksi.app.model._MappingKit;
import com.cnksi.generator.KWebGenerator;
import com.cnksi.interceptor.LoginInterceptor;
import com.cnksi.kconf.KConfig;
import com.cnksi.kconf.model.Authority;
import com.cnksi.kconf.model.Menu;
import com.cnksi.kcore.jfinal.model.BaseModel.Logical;
import com.jfinal.config.Interceptors;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.config.Constants;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;

/**
 * API引导式配置
 */
public class AppConfig extends KConfig {

    Logger logger = Logger.getLogger(AppConfig.class);

    /**
     * 配置常量
     */
     public void configConstant(Constants me) {
         super.configConstant(me);
         me.setBaseDownloadPath("upload");
         me.setBaseUploadPath("upload");
     }

    /**
     * 配置路由
     */
    public void configRoute(Routes me) {
        super.configRoute(me);
        _RouteKit.mapping(me);

        me.add("/app/pms", PmsController.class);
        me.add("/app/statistic", StatisticController.class,"/statistic");
        me.add("/app/docmanage", DocManageController.class,"/docmanage");
    }

    /**
     * 配置插件
     */
    public void configPlugin(Plugins me) {
        super.configPlugin(me);
        _MappingKit.mapping(arp);
    }

    /**
     * 配置全局拦截器
     */
    public void configInterceptor(Interceptors me) {
        super.configInterceptor(me);
        me.addGlobalActionInterceptor(new LoginInterceptor());
    }

    // /**
    // * 配置处理器
    // */
    // public void configHandler(Handlers me) {
    //
    // }
    //
    @Override
    public void afterJFinalStart() {
        super.afterJFinalStart();

        if (PropKit.getBoolean("devMode")) {
            // 向ConfModel中插入数据
            MetaBuilder metaBuilder = new MetaBuilder(DbKit.getConfig().getDataSource());
            metaBuilder.addincludedTable(KWebGenerator.genTables);
            insertToModel(metaBuilder.build(true));

            // 插入数据到IExport
            insertToIExport(metaBuilder.build(true));

            // 只在开发模式下更新只菜单
            for (String key : JFinal.me().getAllActionKeys()) {

                if (key.contains("kconf") || key.contains("sec") || key.equals("/app") || key.equals("/app/index_main")) {
                    continue;
                }

                // 添加权限
                Authority _res = Authority.me.findByPropertity("aurl", key);
                if (_res == null) {
                    Authority res = new Authority();
                    res.set("aid", UUID.randomUUID().toString());
                    res.set("aurl", key);
                    res.set("aname", "");

                    // 设定父级资源
                    Authority r = res.findByPropertity("aurl", key.substring(0, key.lastIndexOf("/")));
                    if (r != null) {
                        res.set("paid", r.get("aid"));
                    }
                    res.save();
                }

                // 添加菜单
                if (key.split("/").length == 3 && key.startsWith("/app")) {
                    Menu menu = Menu.me.findByPropertity(new String[]{"murl"}, new String[]{key}, Logical.AND);
                    if (menu == null) {
                        menu = new Menu();
                        menu.set("menuid", key.replace("/app/", ""));
                        menu.set("murl", key);
                        menu.set("mname", "");
                        menu.set("pmenuid", "funcs");
                        menu.save();
                    }
                }

            }
        }
    }
    //
    // @Override
    // public void beforeJFinalStop() {
    // }

    /**
     * 添加kmode配置数据 @throws
     */
    void inserKModel() {
        MetaBuilder metaBuilder = new MetaBuilder(DbKit.getConfig().getDataSource());
        metaBuilder.addincludedTable("k_lookup", "k_iexport", "k_role", "k_user", "k_department");
        insertToModel(metaBuilder.build(true));
    }

    /**
     * 建议使用 JFinal 手册推荐的方式启动项目 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
     */
    public static void main(String[] args) {
        // JFinal.start("WebRoot", 81, "/", 5);
    }
}
