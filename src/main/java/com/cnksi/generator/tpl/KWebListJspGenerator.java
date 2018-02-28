package com.cnksi.generator.tpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.generator.TableMeta;

public class KWebListJspGenerator {

	protected StringBuilder _listTemplateBuilder = new StringBuilder(1024);

	protected String listJspOutputDir = "";

	public KWebListJspGenerator(String listJspOutputDir) {
		this.listJspOutputDir = listJspOutputDir;
	}

	public void generate(List<TableMeta> tableMetas) {
		System.out.println("Generate list jsp ...");
		_listTemplateBuilder.append("<%%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%%>  %n");
		_listTemplateBuilder.append("<%%@ include file=\"../taglib.jsp\" %%>%n");
		_listTemplateBuilder.append("<div class=\"bjui-pageHeader\">%n");
		_listTemplateBuilder.append("\t<form id=\"pagerForm\" data-toggle=\"ajaxsearch\" action=\"${ctx}/app/%s\" method=\"post\">%n"); //modelName 1
		_listTemplateBuilder.append("\t\t<input type=\"hidden\" name=\"pageNumber\" value=\"${query.pageNumber }\" /> %n");
		_listTemplateBuilder.append("\t\t<input type=\"hidden\" name=\"pageSize\" value=\"${query.pageSize }\" /> %n");
		_listTemplateBuilder.append("\t\t<input type=\"hidden\" name=\"orderField\" value=\"${query.orderField }\" />%n");
		_listTemplateBuilder.append("\t\t<input type=\"hidden\" name=\"orderDirection\" value=\"${query.orderDirection}\">%n");
		_listTemplateBuilder.append("\t\t<div class=\"bjui-searchBar ess-searchBar\">%n ");
		_listTemplateBuilder.append("\t\t<a href=\"${ctx}/app/%s/edit\" data-toggle=\"navtab\" data-id=\"%s-create\" data-title=\"新增\" class=\"btn btn-green\" data-icon=\"plus\">添加</a>&nbsp;%n");//modelName 2,3
		_listTemplateBuilder.append("\t\t</div>%n");
		_listTemplateBuilder.append("\t</form>%n");
		_listTemplateBuilder.append("</div>");
		_listTemplateBuilder.append("<div class=\"bjui-pageContent tableContent  white ess-pageContent\">%n");
		_listTemplateBuilder.append("\t<table data-toggle=\"tablefixed\" data-width=\"100%%\" data-nowrap=\"true\">%n");
		_listTemplateBuilder.append("\t\t<thead>%n");
		_listTemplateBuilder.append("\t\t\t<tr>%n");
		_listTemplateBuilder.append("\t\t\t\t<c:forEach items=\"${fields }\" var=\"f\"> %n");
		_listTemplateBuilder.append("\t\t\t\t\t<c:if test=\"${f.is_list_view ==1 }\">%n");
		_listTemplateBuilder.append("\t\t\t\t\t\t<th data-order-field=\"${f.field_name }\">${f.field_alias }</th>%n");
		_listTemplateBuilder.append("\t\t\t\t\t</c:if>%n");
		_listTemplateBuilder.append("\t\t\t\t</c:forEach>  %n");
		_listTemplateBuilder.append("\t\t\t\t<th>操作</th>%n");
		_listTemplateBuilder.append("\t\t\t</tr>%n");
		_listTemplateBuilder.append("\t\t</thead>%n");
		_listTemplateBuilder.append("\t\t<tbody>%n");
		_listTemplateBuilder.append("\t\t\t<c:forEach items=\"${page.list }\" var=\"r\" varStatus=\"s\">%n");
		_listTemplateBuilder.append("\t\t\t\t<tr>%n");
		_listTemplateBuilder.append("\t\t\t\t\t<c:forEach items=\"${fields }\" var=\"f\">  %n");
		_listTemplateBuilder.append("\t\t\t\t\t\t<c:if test=\"${f.is_list_view ==1 }\">%n");
		_listTemplateBuilder.append("\t\t\t\t\t\t\t<kval:val model=\"${r }\" field=\"${f }\"></kval:val>%n");
		_listTemplateBuilder.append("\t\t\t\t\t\t</c:if>%n");
		_listTemplateBuilder.append("\t\t\t\t\t</c:forEach>  %n");
		_listTemplateBuilder.append("\t\t\t\t\t<td>%n");
		_listTemplateBuilder.append("\t\t\t\t\t\t<a style=\"color:red;\"  data-toggle=\"doajax\"  data-confirm-msg=\"确定要删除该行信息吗？\" href=\"${ctx }/app/%s/delete?id=${r.%s}\">删除</a>  &nbsp;&nbsp;%n "); //modelName=4  pk =5
		_listTemplateBuilder.append("\t\t\t\t\t\t<a href=\"${ctx}/app/%s/edit?id=${r.%s}\"  style=\"color:green;\"  data-toggle=\"navtab\" data-id=\"%s-edit\" data-title=\"编辑用户\" >修改</a>%n"); //modelName=6  pk =7  modelName=8
		_listTemplateBuilder.append("\t\t\t\t\t</td>%n");
		_listTemplateBuilder.append("\t\t\t\t</tr>%n");
		_listTemplateBuilder.append("\t\t\t</c:forEach>%n");
		_listTemplateBuilder.append("\t\t</tbody>%n");
		_listTemplateBuilder.append("\t</table>%n");
		_listTemplateBuilder.append("</div>%n");
		_listTemplateBuilder.append("<div class=\"bjui-pageFooter\">%n");
		_listTemplateBuilder.append("\t<div class=\"pages\">%n");
		_listTemplateBuilder.append("\t\t<span>每页&nbsp;</span>%n");
		_listTemplateBuilder.append("\t\t<div class=\"selectPagesize\">%n");
		_listTemplateBuilder.append("\t\t\t<select data-toggle=\"selectpicker\" data-toggle-change=\"changepagesize\">%n");
		_listTemplateBuilder.append("\t\t\t\t<option value=\"30\">30</option>%n");
		_listTemplateBuilder.append("\t\t\t\t<option value=\"60\">60</option>%n");
		_listTemplateBuilder.append("\t\t\t\t<option value=\"120\">120</option>%n");
		_listTemplateBuilder.append("\t\t\t\t<option value=\"150\">150</option>%n");
		_listTemplateBuilder.append("\t\t\t</select>%n");
		_listTemplateBuilder.append("\t\t</div>%n");
		_listTemplateBuilder.append("\t\t<span>&nbsp;条，共 ${page.totalRow } 条</span>%n");
		_listTemplateBuilder.append("\t</div>%n");
		_listTemplateBuilder.append("\t<div class=\"pagination-box\" data-toggle=\"pagination\" data-total=\"${page.totalRow }\" data-page-size=\"${page.pageSize }\" data-page-current=\"${page.pageNumber }\" data-page-num=\"15\"></div>%n");
		_listTemplateBuilder.append("</div>%n");

		for (TableMeta tableMeta : tableMetas) {
			if (!StrKit.isBlank(tableMeta.primaryKey)) {
				genJspContent(tableMeta);
			}
		}
		wirtToFile(tableMetas);
	}

	protected void genJspContent(TableMeta tableMeta) {
		StringBuilder ret = new StringBuilder();
		ret.append(String.format(_listTemplateBuilder.toString(), tableMeta.modelName.toLowerCase(), tableMeta.modelName.toLowerCase(), tableMeta.modelName.toLowerCase(), tableMeta.modelName.toLowerCase(), tableMeta.primaryKey, tableMeta.modelName.toLowerCase(), tableMeta.primaryKey, tableMeta.modelName.toLowerCase()));
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
		File dir = new File(listJspOutputDir + File.separator + tableMeta.modelName.toLowerCase());
		if (!dir.exists())
			dir.mkdirs();

		String target = listJspOutputDir + File.separator + tableMeta.modelName.toLowerCase() + File.separator + tableMeta.modelName.toLowerCase() + ".jsp";

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
