package com.cnksi.generator.tpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.generator.TableMeta;

public class KWebControllerGenerator {

	protected String packageTemplate = "package %s;%n%n";
	protected String importTemplate = "import %s.%s;%n%n";

	protected String indexTemplate = "\t %n";
	protected StringBuilder indexTemplateBuilder, editTemplateBuilder, saveTemplateBuilder, deleteTemplateBuilder, exportTemplateBuilder,importTemplateBuilder;

	protected String controllerPackageName;
	protected String basePackeName;// com.cnksi.jqwx
	protected String controllerOutputDir;

	public KWebControllerGenerator(String controllerPackageName, String controllerOutputDir) {
		if (StrKit.isBlank(controllerPackageName))
			throw new IllegalArgumentException("controllerPackageName can not be blank.");
		if (StrKit.isBlank(controllerOutputDir))
			throw new IllegalArgumentException("modelOutputDir can not be blank.");

		this.controllerPackageName = controllerPackageName;
		this.basePackeName = controllerPackageName.substring(0, controllerPackageName.indexOf(".controller"));
		this.controllerOutputDir = controllerOutputDir;
		/**
		 *
		 * <pre>
		 * public void index() {
		 * 
		 * 	KWebQueryVO queryParam = super.doIndex();
		 * 	setAttr("page", EmerBdz.me.paginate(queryParam));
		 * 
		 * 	render(listJsp);
		 * }
		 * </pre>
		 */
		indexTemplateBuilder = new StringBuilder("%n");
		indexTemplateBuilder.append("\t").append("public void index() {").append("%n").append("%n");
		indexTemplateBuilder.append("\t\t").append("KWebQueryVO queryParam = super.doIndex();").append("%n").append("%n");
		indexTemplateBuilder.append("\t\t").append("setAttr(\"page\", %s.me.paginate(queryParam));").append("%n").append("%n"); //
		indexTemplateBuilder.append("\t\t").append("render(listJsp);").append("%n");
		indexTemplateBuilder.append("\t").append("}").append("%n").append("%n").append("%n");

		/**
		 * <pre>
		 * public void edit() {
		 * 	super.doEdit();
		 * 
		 * 	String idValue = getPara("id", getPara());
		 * 	EmerBdz record = null;
		 * 	if (idValue != null) {
		 * 		record = EmerBdz.me.findById(idValue);
		 * 	} else {
		 * 		record = new EmerBdz();
		 * 	}
		 * 	setAttr("record", record);
		 * 
		 * 	render(formJsp);
		 * }
		 * </pre>
		 */
		editTemplateBuilder = new StringBuilder("%n");
		editTemplateBuilder.append("\t").append("public void edit() {").append("%n").append("%n");
		editTemplateBuilder.append("\t\t").append("super.doEdit();").append("%n").append("%n");
		editTemplateBuilder.append("\t\t").append("String idValue = getPara(\"id\", getPara());").append("%n");
		editTemplateBuilder.append("\t\t").append("%s record = null;").append("%n"); // %s=modelName
		editTemplateBuilder.append("\t\t").append("if (idValue != null) {").append("%n");
		editTemplateBuilder.append("\t\t\t").append("record = %s.me.findById(idValue);").append("%n");// %s=modelName
		editTemplateBuilder.append("\t\t").append("}else{").append("%n");
		editTemplateBuilder.append("\t\t\t").append("record = new %s();").append("%n").append("%n"); // %s=modelName
		editTemplateBuilder.append("\t\t").append("}").append("%n");
		editTemplateBuilder.append("\t\t").append("setAttr(\"record\", record);").append("%n").append("%n");
		editTemplateBuilder.append("\t\t").append("render(formJsp);").append("%n");
		editTemplateBuilder.append("\t").append("}").append("%n").append("%n").append("%n");

		/**
		 * <pre>
		 * public void save() {
		 * 	EmerBdz record = getModel(EmerBdz.class, "record");
		 * 	if (record.get("bdzid") != null) {
		 * 		record.update();
		 * 	} else {
		 * 		record.save();
		 * 	}
		 * 	bjuiAjax(200, true);
		 * }
		 * 
		 * </pre>
		 */
		saveTemplateBuilder = new StringBuilder();
		saveTemplateBuilder.append("\t").append("public void save() {").append("%n");
		saveTemplateBuilder.append("\t\t").append("%s record = getModel(%s.class, \"record\");").append("%n"); // %s=modelName , %s=modelName
		saveTemplateBuilder.append("\t\t").append("if (record.get(\"%s\") != null) {").append("%n"); // %s=pkName
		saveTemplateBuilder.append("\t\t\t").append("record.update();").append("%n");
		saveTemplateBuilder.append("\t\t").append("} else {").append("%n");
		saveTemplateBuilder.append("\t\t\t").append("record.save();").append("%n");
		saveTemplateBuilder.append("\t\t").append("} ").append("%n").append("%n");

		saveTemplateBuilder.append("\t\t").append("bjuiAjax(200, true);").append("%n");
		saveTemplateBuilder.append("\t").append("}").append("%n").append("%n").append("%n");

		/**
		 * <pre>
		 * public void delete() {
		 * 	EmerBdz record = EmerBdz.me.findById(getPara());
		 * 	if (record != null) {
		 * 		record.set("enabled", 1).update();
		 * 		bjuiAjax(200);
		 * 	} else {
		 * 		bjuiAjax(300);
		 * 	}
		 * }
		 * </pre>
		 */
		deleteTemplateBuilder = new StringBuilder("%n");
		deleteTemplateBuilder.append("\t").append("public void delete() {").append("%n");
		deleteTemplateBuilder.append("\t\t").append("%s record = %s.me.findById(getPara());").append("%n"); // %s=modelName %s=modelName
		deleteTemplateBuilder.append("\t\t").append("if (record != null) {").append("%n");
		deleteTemplateBuilder.append("\t\t\t").append("record.set(\"enabled\", 1).update();").append("%n");
		deleteTemplateBuilder.append("\t\t\t").append("bjuiAjax(200);").append("%n");
		deleteTemplateBuilder.append("\t\t").append("} else {").append("%n");
		deleteTemplateBuilder.append("\t\t\t").append("bjuiAjax(300);").append("%n");
		deleteTemplateBuilder.append("\t\t").append("} ").append("%n").append("%n");

		deleteTemplateBuilder.append("\t").append("}").append("%n").append("%n").append("%n");

		/**
		 * <pre>
		 * public void export() throws IOException {
		 * 	KWebQueryVO queryParam = super.doIndex();
		 * 	Page<EmerBdz> p = EmerBdz.me.paginate(queryParam);
		 * 	String xlsid = getPara("xlsid", "-1");
		 * 	super.export(xlsid, p.getList());
		 * }
		 * </pre>
		 */
		exportTemplateBuilder = new StringBuilder("%n");
		exportTemplateBuilder.append("\t").append("public void export()  throws IOException {").append("%n");
		exportTemplateBuilder.append("\t\t").append("KWebQueryVO queryParam = super.doIndex();").append("%n");
		exportTemplateBuilder.append("\t\t").append("Page<%s> p = %s.me.paginate(queryParam);").append("%n"); // %s=modelName,%s=modelName
		exportTemplateBuilder.append("\t\t").append("String xlsid = getPara(\"xlsid\", \"-1\");").append("%n");
		exportTemplateBuilder.append("\t\t").append("super.export(xlsid, p.getList());").append("%n");

		exportTemplateBuilder.append("\t").append("}").append("%n").append("%n").append("%n");

		/**
		 * <pre>
		 * public void importxlsed() {
		 * 	String errorFile = "",msg=""; 
		 * 	try {
		 * 		ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
		 * 		if (!result.isVerfiyFail()) {
		 * 			for (Map<String, Object> map : result.getList()) {
		 * 				EmerBdz bdz = new EmerBdz();
		 * 				bdz.put(map);
		 * 				bdz.set(bdz.getPkName(), UUID.randomUUID().toString());
		 * 				bdz.save();
		 * 			}
		 * 		} else {
		 * 			errorFile = result.getSaveFile().getName();
		 * 		}
		 * 	} catch (ExcelImportException e) {
		 * 		msg = "导入错误：" + e.getMessage();
		 * 	}
		 * 	bjuiAjax(StrKit.notBlank(msg) ? 300 : 200).put("errorFile", errorFile);
		 * 
		 * }
		 * 
		 * </pre>
		 */
		
		importTemplateBuilder = new StringBuilder("%n");
		importTemplateBuilder.append("\t").append("public void importxlsed()  {").append("%n");
		importTemplateBuilder.append("\t\t").append("String errorFile = \"\",msg=\"\"; ").append("%n");
		importTemplateBuilder.append("\t\t").append("try {").append("%n");  
		
		importTemplateBuilder.append("\t\t\t").append("ExcelImportResult<Map<String, Object>> result = super.importxls(getPara(\"xlsid\"), getFile());").append("%n");
		importTemplateBuilder.append("\t\t\t").append("if (!result.isVerfiyFail()) {").append("%n");
		importTemplateBuilder.append("\t\t\t\t").append("for (Map<String, Object> map : result.getList()) {").append("%n");
		importTemplateBuilder.append("\t\t\t\t\t").append("%s record = new %s();").append("%n");  //%s=modelName %s=modelName
		importTemplateBuilder.append("\t\t\t\t\t").append("record.put(map);").append("%n");
		importTemplateBuilder.append("\t\t\t\t\t").append("record.save();").append("%n");
		importTemplateBuilder.append("\t\t\t\t").append("}").append("%n");
		importTemplateBuilder.append("\t\t\t").append("} else {").append("%n");
		importTemplateBuilder.append("\t\t\t\t").append("errorFile = result.getSaveFile().getName();").append("%n");
		importTemplateBuilder.append("\t\t\t").append("}").append("%n");
		
		
		importTemplateBuilder.append("\t\t").append("} catch (ExcelImportException e) {").append("%n");
		importTemplateBuilder.append("\t\t\t").append("msg = \"导入错误：\" + e.getMessage();").append("%n");
		importTemplateBuilder.append("\t\t").append("}").append("%n");
		importTemplateBuilder.append("\t\t").append("bjuiAjax(StrKit.notBlank(msg) ? 300 : 200).put(\"errorFile\", errorFile);").append("%n").append("%n");
		importTemplateBuilder.append("\t").append("}").append("%n").append("%n").append("%n");
	}

	protected void genDeleteMethod(TableMeta tableMeta, StringBuilder ret) {
		ret.append(String.format(deleteTemplateBuilder.toString(), tableMeta.modelName, tableMeta.modelName));
	}

	protected void genSaveMethod(TableMeta tableMeta, StringBuilder ret) {
		ret.append(String.format(saveTemplateBuilder.toString(), tableMeta.modelName, tableMeta.modelName, tableMeta.primaryKey));
	}

	public void generate(List<TableMeta> tableMetas) {
		System.out.println("Generate controller ...");
		for (TableMeta tableMeta : tableMetas)
			genControllerContent(tableMeta);
		wirtToFile(tableMetas);

	}

	protected void genControllerContent(TableMeta tableMeta) {
		StringBuilder ret = new StringBuilder();
		genPackage(ret);
		genImport(tableMeta, ret);
		genClassDefine(tableMeta, ret);
		genIndexMethod(tableMeta, ret);
		genEditMethod(tableMeta, ret);
		genSaveMethod(tableMeta, ret);
		genDeleteMethod(tableMeta, ret);
		genExportMethod(tableMeta, ret);
		genImportMethod(tableMeta, ret);
		ret.append(String.format("}%n"));
		tableMeta.modelContent = ret.toString();
	}
	protected void genImportMethod(TableMeta tableMeta, StringBuilder ret) {
		ret.append(String.format(importTemplateBuilder.toString(), tableMeta.modelName, tableMeta.modelName));
	}
	protected void genExportMethod(TableMeta tableMeta, StringBuilder ret) {
		ret.append(String.format(exportTemplateBuilder.toString(), tableMeta.modelName, tableMeta.modelName));
	}

	protected void genPackage(StringBuilder ret) {
		ret.append(String.format(packageTemplate, controllerPackageName));
	}

	protected void genIndexMethod(TableMeta tableMeta, StringBuilder ret) {
		ret.append(String.format(indexTemplateBuilder.toString(), tableMeta.modelName));
	}

	protected void genEditMethod(TableMeta tableMeta, StringBuilder ret) {
		ret.append(String.format(editTemplateBuilder.toString(), tableMeta.modelName, tableMeta.modelName, tableMeta.modelName));
	}

	protected void genImport(TableMeta tableMeta, StringBuilder ret) {
		ret.append(String.format("import java.io.IOException; %n"));
		ret.append(String.format("import java.util.Map; %n"));
		ret.append(String.format("import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;%n"));
		ret.append(String.format("import org.jeecgframework.poi.exception.excel.ExcelImportException; %n"));
		ret.append(String.format("import com.jfinal.kit.StrKit;%n"));
		ret.append(String.format("import com.jfinal.plugin.activerecord.Page; %n"));
		ret.append(String.format("import com.cnksi.kcore.web.KWebQueryVO; %n"));
		ret.append(String.format("import com.cnksi.kconf.controller.KController; %n"));
		ret.append(String.format("import %s.model.%s; %n", basePackeName, tableMeta.modelName));
	}

	protected void genClassDefine(TableMeta tableMeta, StringBuilder ret) {
		String classDefineTemplate = "/**%n" + " * %n" + " */%n" + "public class %sController extends KController {%n";

		ret.append(String.format(classDefineTemplate, tableMeta.modelName));
		ret.append(String.format("%n \tpublic %sController(){", tableMeta.modelName));
		ret.append(String.format("%n\t\tsuper(%s.class);", tableMeta.modelName));
		ret.append(String.format("%n\t} %n"));
	}

	protected void wirtToFile(List<TableMeta> tableMetas) {
		try {
			for (TableMeta tableMeta : tableMetas) {
				if (!StrKit.isBlank(tableMeta.primaryKey)) {
					wirtToFile(tableMeta);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 若 model 文件存在，则不生成，以免覆盖用户手写的代码
	 */
	protected void wirtToFile(TableMeta tableMeta) throws IOException {
		File dir = new File(controllerOutputDir);
		if (!dir.exists())
			dir.mkdirs();

		String target = controllerOutputDir + File.separator + tableMeta.modelName + "Controller.java";

		File file = new File(target);
		if (file.exists()) {
			return; // 若 Model 存在，不覆盖
		}

		FileWriter fw = new FileWriter(file);
		try {
			fw.write(tableMeta.modelContent);
		} finally {
			fw.close();
		}
	}
}
