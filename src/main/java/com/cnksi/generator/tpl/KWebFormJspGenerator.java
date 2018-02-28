package com.cnksi.generator.tpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.generator.TableMeta;

public class KWebFormJspGenerator {

	protected StringBuilder _formTemplateBuilder = new StringBuilder(1024);

	protected String formJspOutputDir = "";

	public KWebFormJspGenerator(String formJspOutputDir) {
		this.formJspOutputDir = formJspOutputDir;
	}

	public void generate(List<TableMeta> tableMetas) {
		System.out.println("Generate form jsp ...");
		_formTemplateBuilder.append("<%%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\"  pageEncoding=\"UTF-8\"%%>  %n");
		_formTemplateBuilder.append("<%%@ include file=\"../taglib.jsp\" %%> %n");
		_formTemplateBuilder.append("\t<div class=\"bjui-pageContent\">%n");
		_formTemplateBuilder.append("\t<div class=\"ess-form\">%n");
		_formTemplateBuilder.append("\t\t<form action=\"${ctx}/app/%s/save\"  data-toggle=\"validate\" data-alertmsg=\"false\">%n");
		_formTemplateBuilder.append("\t\t\t<input type=\"hidden\" name=\"record.%s\" value=\"${record.%s }\">%n"); //pk 2个
		_formTemplateBuilder.append("\t\t\t\t<ul>%n");
		_formTemplateBuilder.append("\t\t\t\t<c:forEach items=\"${fields }\" var=\"f\" >%n");
		_formTemplateBuilder.append("\t\t\t\t\t<c:if test=\"${f.is_form_view == 1 }\"> %n");
		_formTemplateBuilder.append("\t\t\t\t\t\t<li>%n");
		_formTemplateBuilder.append("\t\t\t\t\t\t\t<label>${f.field_alias }</label>%n");
		_formTemplateBuilder.append("\t\t\t\t\t\t\t<input type=\"text\" name=\"record.${f.field_name }\" id=\"j_${f.field_name }\" value=\"${record[f.field_name]}\"  placeholder=\"请输入${f.field_alias }\">%n");
		_formTemplateBuilder.append("\t\t\t\t\t\t</li> %n");
		_formTemplateBuilder.append("\t\t\t\t\t</c:if>%n");
		_formTemplateBuilder.append("\t\t\t\t</c:forEach> %n");
		_formTemplateBuilder.append("\t\t\t\t</ul>%n");
		_formTemplateBuilder.append("\t\t</form>%n");
		_formTemplateBuilder.append("\t\t<div class=\"clearfix\"></div>%n");
		_formTemplateBuilder.append("\t</div>%n");
		_formTemplateBuilder.append("</div>%n");
		_formTemplateBuilder.append("<div class=\"bjui-pageFooter\">%n");
		_formTemplateBuilder.append("\t<ul>%n");
		_formTemplateBuilder.append("\t\t<li><button type=\"button\" class=\"btn-close\" data-icon=\"close\">取消</button></li>%n");
		_formTemplateBuilder.append("\t\t<li><button type=\"submit\" class=\"btn-default\" data-icon=\"save\">保存</button></li>%n");
		_formTemplateBuilder.append("\t</ul>%n");
		_formTemplateBuilder.append("</div>%n");

		for (TableMeta tableMeta : tableMetas) {
			if (!StrKit.isBlank(tableMeta.primaryKey)) {
				genJspContent(tableMeta);
			}
		}
		wirtToFile(tableMetas);
	}

	protected void genJspContent(TableMeta tableMeta) {
		StringBuilder ret = new StringBuilder();
		ret.append(String.format(_formTemplateBuilder.toString(), tableMeta.modelName.toLowerCase(),tableMeta.primaryKey,tableMeta.primaryKey));
		tableMeta.modelContent = ret.toString();
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
		File dir = new File(formJspOutputDir + File.separator + tableMeta.modelName.toLowerCase());
		if (!dir.exists())
			dir.mkdirs();

		String target = formJspOutputDir + File.separator + tableMeta.modelName.toLowerCase() + File.separator + tableMeta.modelName.toLowerCase() + "_form.jsp";

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
