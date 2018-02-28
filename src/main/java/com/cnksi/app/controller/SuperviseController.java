package com.cnksi.app.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException;

import com.cnksi.app.model.Card;
import com.cnksi.app.model.Supervise;
import com.cnksi.app.model.Task;
import com.cnksi.kconf.controller.KController;
import com.cnksi.kcore.web.KWebQueryVO;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile; 
/**
 * 
 */
public class SuperviseController extends KController {

 	public SuperviseController(){
		super(Supervise.class);
	} 

	public void index() {

		KWebQueryVO queryParam = super.doIndex();

		setAttr("page", Supervise.me.paginate(queryParam));

		render(listJsp);
	}



	public void edit() {

		super.doEdit();

		String idValue = getPara("id", getPara());
		Supervise record = null;
		if (idValue != null) {
			record = Supervise.me.findById(idValue);
		}else{
			record = new Supervise();

		}
		setAttr("record", record);

		render(formJsp);
	}


	public void save() {
		Supervise record = getModel(Supervise.class, "record");
		if (record.get("id") != null) {
			record.update();
		} else {
			record.save();
		} 

		bjuiAjax(200, true);
	}



	public void delete() {
		Supervise record = Supervise.me.findById(getPara());
		if (record != null) {
			record.set("enabled", 1).update();
			bjuiAjax(200);
		} else {
			bjuiAjax(300);
		} 

	}



	public void export()  throws IOException {
		KWebQueryVO queryParam = super.doIndex();
		Page<Supervise> p = Supervise.me.paginate(queryParam);
		String xlsid = getPara("xlsid", "-1");
		super.export(xlsid, p.getList());
	}



	public void importxlsed()  {
		String errorFile = "",msg=""; 
		try {
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
			if (!result.isVerfiyFail()) {
				for (Map<String, Object> map : result.getList()) {
					Supervise record = new Supervise();
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
	
	public void importFile() throws Exception{
		UploadFile file = getFile();
		String srcFilePath = file.getUploadPath()+"/"+file.getFileName();
	/*//	String srcFilePath = PathKit.getWebRootPath()+"1.xls";
		String srcFilePath = "C:/1.xls";*/
		//String fileName = file.getFileName();//文件名称
		String title[] ={"监督技术项目","监督项目","关键项权重","监督要点","监督依据","监督要求","监督结果","评价标准","存在问题","评价结果（总分100分）","条文解释","专业细分"}; 
		String tit[] = {"specialty_supervise","project_supervise","item_key","points_supervise","basis_supervise","require_supervise","result_supervise","evaluation_criterion","problem","result","explana","professional"};
		
		String titlse[] = {"监督要点","监督依据","条文解释","专业细分"};
		String tits[] = {"points_supervise","basis_supervise","explana","professional"};
		InputStream input = new FileInputStream(srcFilePath);
		boolean flag = true;
		if(file.getFileName().indexOf(".xlsx") < 0 ){
			HSSFWorkbook workbook = new HSSFWorkbook(input);
			HSSFSheet sheet = workbook.getSheetAt(0);
			String typeName = sheet.getSheetName();
			String ty[] = typeName.split("-");
			typeName = ty[0];
			String typeNa = "";
			if(ty.length > 1){
				typeNa = ty[1];
			}
			
			int coloumNum=sheet.getRow(0).getPhysicalNumberOfCells();//获得总列数
	        int rowNum=sheet.getLastRowNum();//获得总行数
	        String devType = sheet.getRow(0).getCell(0).getStringCellValue();
	        
	        String idSql = "SELECT id FROM device_type WHERE name = '"+devType+"'";
	        Record id = Db.findFirst(idSql);
	        String typeSql = "SELECT card.flow,card.key from card_type card where card.name='"+typeName+"'";
	        Record type = Db.findFirst(typeSql);
	        for(int i = 3; i<=rowNum ; i++){
	        	flag = true;
	        	HSSFRow row = sheet.getRow(i);
	        	Supervise supervise = new Supervise();
	        	if(coloumNum == 5){
	        		for(int j = 1; j < coloumNum; j++){
	        			HSSFCell cells = row.getCell(j);
	            		String str = cells.getStringCellValue();
	            		if(j == 2){
	            			str += "《国网四川省电力公司"+devType+"全过程技术监督精益化管理实施评价细则》";
	            		}
	            		supervise.set(tits[j-1], str);
	        		}
	        	}else{
	        		for(int j = 2 ;j <= 13;j++){
	            		HSSFCell cells = row.getCell(j);
	            		if(cells == null){
	            			flag = false;
	            			break;
	            		}
	            		String str = cells.getStringCellValue();
	            		if(j==2 && "".equals(str)){
	            			flag = false;
	            			break;
	            		}
	            		if(j == 6){
	            			str += "《国网四川省电力公司"+devType+"全过程技术监督精益化管理实施评价细则》";
	            		}
	            		if(j == 9){
	            			String score = "\\n";
	            			String [] dataStr = str.split(score); 
	            			String re = "";
	            			String rs = "";
	        		        Pattern pattern = Pattern.compile("扣\\d+分");
	        		        Matcher matcher = pattern.matcher(str);
	        		        while(matcher.find()){
	        		        	rs += matcher.group();
	        		        }
	        		        Pattern patterns = Pattern.compile("\\d+");
	        		        Matcher matchers = patterns.matcher(rs);
	        		        rs = "";
	        		        while(matchers.find()){
	        		        	rs += matchers.group()+";;";
	        		        }
	        		        supervise.set("score", rs);    
	            		    for(String s:dataStr){ 
	            		       re += s +";;";
	            		    } 
	            		    str = re;
	            		}
	            		supervise.set(tit[j-2], str);
	            	}
	        	}
	        	if(flag){
	        		supervise.set("did",id.get("id"));
	        		supervise.set("flow", type.get("flow"));
	        		supervise.set("type", type.get("key"));
	        		supervise.set("type_name", typeNa);
	        		supervise.save();
	        	}
	        }
		}else{
			XSSFWorkbook workbooks = new XSSFWorkbook(input);
			XSSFSheet sheets = workbooks.getSheetAt(0);
			String typeNames = sheets.getSheetName();
			String type[] = typeNames.split("-");
			typeNames = type[0];
			String typeNa = "";
			if(type.length > 1){
				typeNa = type[1];
			}
			int coloumNums=sheets.getRow(0).getPhysicalNumberOfCells();//获得总列数
	        int rowNums=sheets.getLastRowNum();//获得总行数
	        String devTypes = sheets.getRow(0).getCell(0).getStringCellValue();
	        
	        String idSqls = "SELECT id FROM device_type WHERE name = '"+devTypes+"'";
	        Record ids = Db.findFirst(idSqls);
	        String typeSqls = "SELECT card.flow,card.key from card_type card where card.name='"+typeNames+"'";
	        Record types = Db.findFirst(typeSqls);
	        for(int i = 3; i<=rowNums ; i++){
	        	flag = true;
	        	XSSFRow rows = sheets.getRow(i);
	        	Supervise supervise = new Supervise();
	        	if(coloumNums == 5){
	        		for(int j = 1; j < coloumNums; j++){
	        			XSSFCell cells = rows.getCell(j);
	            		String str = cells.getStringCellValue();
	            		if(j == 2){
	            			str += "《国网四川省电力公司"+devTypes+"全过程技术监督精益化管理实施评价细则》";
	            		}
	            		supervise.set(tits[j-1], str);
	        		}
	        	}else{
	        		for(int j = 2 ;j <= 13;j++){
	            		XSSFCell cells = rows.getCell(j);
	            		if(cells == null){
	            			flag = false;
	            			break;
	            		}
	            		String str = cells.getStringCellValue();
	            		if(j==2 && "".equals(str)){
	            			flag = false;
	            			break;
	            		}
	            		if(j == 6){
	            			str += "《国网四川省电力公司"+devTypes+"全过程技术监督精益化管理实施评价细则》";
	            		}
	            		if(j == 9){
	            			String score = "\\n";
	            			String [] dataStr = str.split(score); 
	            			String re = "";
	            			String rs = "";
	        		        Pattern pattern = Pattern.compile("扣\\d+分");
	        		        Matcher matcher = pattern.matcher(str);
	        		        while(matcher.find()){
	        		        	rs += matcher.group();
	        		        }
	        		        Pattern patterns = Pattern.compile("\\d+");
	        		        Matcher matchers = patterns.matcher(rs);
	        		        rs = "";
	        		        while(matchers.find()){
	        		        	rs += matchers.group()+";;";
	        		        }
	        		        supervise.set("score", rs);    
	            		    for(String s:dataStr){ 
	            		       re += s +";;";
	            		    } 
	            		    str = re;
	            		}
	            		supervise.set(tit[j-2], str);
	            	}
	        	}
	        	if(flag){
	        		supervise.set("did",ids.get("id"));
		        	supervise.set("flow", types.get("flow"));
		        	supervise.set("type", types.get("key"));
		        	supervise.set("type_name", typeNa);
		        	supervise.save();
	        	}
	        }
		}
	}
	
	
	
	
	
}
