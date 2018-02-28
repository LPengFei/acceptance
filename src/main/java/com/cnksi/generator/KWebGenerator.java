package com.cnksi.generator;

import com.cnksi.app.AppConfig;
import com.cnksi.generator.tpl.KWebControllerGenerator;
import com.cnksi.generator.tpl.KWebModelGenerator;
import com.cnksi.generator.tpl.RouteKitGenerator;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;
import com.jfinal.plugin.druid.DruidPlugin;

import javax.sql.DataSource;

/**
 * GeneratorDemo
 */
public class KWebGenerator {

    public static String[] genTables = new String[]{
            "card",
            "card_copy",
            "card_item",
            "card_item_type",
            "card_standard",
            "card_type",
            "count_device",
            "count_task",
            "device_field",
            "device_type",
            "doc_field",
            "doc_field_value",
            "doc_file",
            "doc_group_value",
            "pro_milestone",
            "pro_project",
            "pro_progress",
            "pro_project_attachment",
            "pro_project_attachment_image",
            "pro_project_scal",
            "result_issue",
            "result_issue_important",
            "result_issue_log",
            "result_item",
            "result_item_copy",
            "result_signname",
            "task",
            "card",
            "task_card",
            "sys_config",
            "pms_bdz",
            "pms_spacing",
            "pms_device",
            "device_field_value",
            "device_img",
            "pms_dict",
            "rel_device_issue",
            "rel_device_task",
            "supervise",
            "task_supervise"
    };
    static String[] removedTableNamePrefixes = new String[]{"app_", "k_"};
    static String prePackage = "com.cnksi." + AppConfig.appid;

    static ActiveRecordPlugin arp = null;

    public static DataSource getDataSource() {
        Prop p = PropKit.use("application.conf");
        DruidPlugin c3p0Plugin;
        if (PropKit.getBoolean("devMode", false)) {
            c3p0Plugin = new DruidPlugin(p.get("db.dev_jdbcUrl"), p.get("db.dev_user"), p.get("db.dev_user"));
        } else {
            c3p0Plugin = new DruidPlugin(p.get("db.prod_jdbcUrl"), p.get("db.prod_user"), p.get("db.prod_user"));
        }
        c3p0Plugin.start();

        // _MappingKit.mapping(arp);

        return c3p0Plugin.getDataSource();
    }

    public static void main(String[] args) {

        MetaBuilder metaBuilder = new MetaBuilder(getDataSource());
        metaBuilder.addincludedTable(genTables);
        metaBuilder.setRemovedTableNamePrefixes(removedTableNamePrefixes);

        // base model 所使用的包名
        String baseModelPackageName = prePackage + ".model";
        String baseModelOutputDir = PathKit.getWebRootPath() + "/src/main/java/" + baseModelPackageName.replaceAll("[.]", "/") + "/base";// com/cnksi/kweb/model/base";

        // model 所使用的包名 (MappingKit 默认使用的包名)
        String modelPackageName = prePackage + ".model";
        // model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = baseModelOutputDir.replace("/base", "");

        // 创建生成器
        // Generator gernerator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
        Generator gernerator = new Generator(getDataSource(), null, new KWebModelGenerator(modelPackageName, baseModelPackageName, modelOutputDir));

        gernerator.setMetaBuilder(metaBuilder);

        // 设置数据库方言
        gernerator.setDialect(new MysqlDialect());
        // 添加不需要生成的表名
        gernerator.addExcludedTable("adv");
        // 设置是否在 Model 中生成 dao 对象
        gernerator.setGenerateDaoInModel(true);
        // 设置是否生成字典文件
        gernerator.setGenerateDataDictionary(true);
        // 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
        gernerator.setRemovedTableNamePrefixes(removedTableNamePrefixes);
        // 生成
        gernerator.generate();

        // 生成查询QueryVO
        // String queryvoPackageName = prePackage + ".controller.vo";
        // String queryvoOutputDir = PathKit.getWebRootPath() + "/src/main/java/" + queryvoPackageName.replaceAll("[.]", "//");
        // KWebQueryVOGenerator queryvoGen = new KWebQueryVOGenerator(queryvoPackageName, queryvoOutputDir);
        // queryvoGen.generate(metaBuilder.build());

        // 生成Controller
        String controllerPackageName = prePackage + ".controller";
        String controllerOutputDir = PathKit.getWebRootPath() + "/src/main/java/" + controllerPackageName.replaceAll("[.]", "//");

        KWebControllerGenerator controllerGen = new KWebControllerGenerator(controllerPackageName, controllerOutputDir);
        controllerGen.generate(metaBuilder.build());

        // 生成Route配置文件
        RouteKitGenerator routeKitGenerator = new RouteKitGenerator(controllerPackageName, controllerOutputDir);
        routeKitGenerator.generate(metaBuilder.build());

        // 生成JSP页面
        // String jspOutputDir = PathKit.getWebRootPath() + "/src/main/webapp/WEB-INF/jsp";
        // KWebListJspGenerator listGenerator = new KWebListJspGenerator(jspOutputDir);
        // listGenerator.generate(metaBuilder.build());
        //
        // KWebFormJspGenerator formGenerator = new KWebFormJspGenerator(jspOutputDir);
        // formGenerator.generate(metaBuilder.build());
    }

}
