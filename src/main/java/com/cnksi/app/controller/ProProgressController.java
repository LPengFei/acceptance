package com.cnksi.app.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.jfinal.plugin.activerecord.Record;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException; 
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page; 
import com.cnksi.kcore.web.KWebQueryVO; 
import com.cnksi.kconf.controller.KController; 
import com.cnksi.app.model.ProProgress; 
/**
 * 
 */
public class ProProgressController extends KController {

 	public ProProgressController(){
		super(ProProgress.class);
	} 

	public void index() {

		KWebQueryVO queryParam = super.doIndex();

		setAttr("page", ProProgress.me.paginate(queryParam));

		render(listJsp);
	}



	public void edit() {

		super.doEdit();

		String idValue = getPara("id", getPara());
		ProProgress record = null;
		if (idValue != null) {
			record = ProProgress.me.findById(idValue);
		}else{
			record = new ProProgress();

		}
		setAttr("record", record);

		render(formJsp);
	}


	public void save() {
		ProProgress record = getModel(ProProgress.class, "record");
		if (record.get("id") != null) {
			record.update();
		} else {
			record.save();
		} 

		bjuiAjax(200, true);
	}



	public void delete() {
		ProProgress record = ProProgress.me.findById(getPara());
		if (record != null) {
			record.set("enabled", 1).update();
			bjuiAjax(200);
		} else {
			bjuiAjax(300);
		} 

	}



	public void export()  throws IOException {
		KWebQueryVO queryParam = super.doIndex();
		Page<ProProgress> p = ProProgress.me.paginate(queryParam);
		String xlsid = getPara("xlsid", "-1");
		super.export(xlsid, p.getList());
	}



	public void importxlsed()  {
		String errorFile = "",msg=""; 
		try {
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
			if (!result.isVerfiyFail()) {
				for (Map<String, Object> map : result.getList()) {
					ProProgress record = new ProProgress();
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


	public void gantt() {
		String projectId = getPara("projectId");
		if ( isParaExists("editing") && getParaToBoolean("editing") ) {
			Arrays.asList(getParaValues("ids"))
					.forEach(x -> {
						String start_date = LocalDateTime.parse(getPara(x + "_start_date", "21-12-1987 00:00"), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
									.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
						if ( "inserted".equals(getPara(x + "_!nativeeditor_status")) ) {
							new ProProgress()
									.set("id", getPara(x + "_id"))
									.set("pid", projectId)
									.set("start_date", start_date)
									.set("duration", getPara(x + "_duration"))
									.set("text", getPara(x + "_text")).save();
						} else if ( "deleted".equals(getPara(x + "_!nativeeditor_status")) ) {
							ProProgress record = ProProgress.me.findById(x);
							if (record != null) {
								record.set("enabled", 1).update();
							}
						} else if ( "updated".equals(getPara(x + "_!nativeeditor_status")) ) {
							ProProgress proProgress = ProProgress.me.findById(getPara(x + "_id"));
							proProgress.set("start_date", start_date)
									.set("duration", getPara(x + "_duration"))
									.set("text", getPara(x + "_text")).update();
						}
					});
			bjuiAjax(200);
		} else {
			String sql = String.format("select * from %s where enabled = 0 and pid = ? order by start_date asc", new Object[]{this.tableName});
			List<ProProgress> proProgresss = ProProgress.me.find(sql, new Object[]{projectId}).stream().map(x -> {
				String start_date = x.<Timestamp>get("start_date")
						.toLocalDateTime()
						.toLocalDate()
						.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				x.set("start_date", start_date);
				return x;
			}).collect(Collectors.toList());

			renderJson(new HashMap<String, Object>(){{
				put("data", proProgresss);
			}});
		}
	}
}
