package com.cnksi.taglib;

import java.util.Objects;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.cnksi.kcore.jfinal.model.KModelField;
import com.cnksi.kcore.jfinal.model.KModelField.Setting;
import com.cnksi.kcore.utils.KStrKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("rawtypes")
public class KValueTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(KValueTag.class);

	// private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	// 数据库表名
	private Model model;

	private KModelField field;

	@Override
	public int doAfterBody() throws JspException {
		return super.doAfterBody();
	}

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {

		JspWriter out = this.pageContext.getOut();

		try {

			Objects.requireNonNull(model);
			Objects.requireNonNull(field);

			Object val = model.get(field.getStr("field_name"), ""); // method(datas.get(i), f);
			Objects.requireNonNull(val);

			String valStr = KStrKit.toStr(val);

			String outString = "<td data-val='" + valStr + "'>" + valStr + "</td>";
			// 读取字段数据源配置
			Setting setting = field.getSettings();
			if (setting != null && !setting.getListview().isEmpty()) {
				JSONObject listview = setting.getListview();

				// 如果列表配置里有sql配置，则直接根据sql获取字段值(如获取部门id对应的部门名称等)
				String listViewSql = listview.getString("list_sql");
				if (StrKit.notBlank(listViewSql)) {
					String[] valStrs = valStr.split(",");
					if (valStrs.length > 1) {
						StringBuilder sb = new StringBuilder();
						for (String query : valStrs) {
							sb.append(KStrKit.toStr(Db.queryColumn(listViewSql, query)));
							sb.append(",");
					}
						sb.deleteCharAt(sb.length() - 1);
						valStr = sb.toString();
					} else {
						valStr = KStrKit.toStr(Db.queryColumn(listViewSql, valStr));
					}
				} else {
					// //根据数据源配置获取id对应值
					// String dataSourceName = setting.getDataconfig().getString("dataSource");
					//
					// //读取数据源 查询条件所需参数
					// if (StrKit.notBlank(dataSourceName)) {
					// Record data = DataSourceManager.me.getOneData(dataSourceName, valStr);
					// if(data != null){
					// valStr = data.get("name");
					// }
					// }
				}

				// 读取样式配置
				String cssClass = KStrKit.trimEmpty(listview.getString("list_class"));
				String style = KStrKit.trimEmpty(listview.getString("list_style"));
				if (StrKit.notBlank(style) && !style.endsWith(";"))
					style += ";";

				String width = KStrKit.trimEmpty(listview.getString("list_width"));
				if (StrKit.notBlank(width))
					style += "width: " + width.trim() + "px;";

				String align = KStrKit.trimEmpty(listview.getString("list_align"));
				if (StrKit.notBlank(align))
					style += "text-align: " + align + ";";

				outString = String.format("<td style='%s' class='%s' title='%s' data-val='%s' >%s</td>", style, cssClass, valStr, valStr, valStr);

			}

			out.print(outString);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return super.doStartTag();
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public void setField(KModelField field) {
		this.field = field;
	}

	public KModelField getField() {
		return field;
	}

}
