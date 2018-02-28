package com.cnksi.app.service;

import java.util.Map;
import java.util.Map.Entry;

import com.cnksi.app.service.pms.PMSSyncService;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.Model;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class PmsService {
	public final static String fieldPrefix =  "pms_";
	public final static String pmsID =  "obj_id";
	
	protected PMSSyncService pmsSyncService = PMSSyncService.service;

	protected <M extends Model<M>> void setPmsField(Map<String, Object> map, M model) {
		for (Entry<String, Object> entry : map.entrySet()) {
			
			String key = entry.getKey();
			String fieldName = fieldPrefix + key;
			model.set(fieldName, entry.getValue());
		}
	}
	


	protected <M extends Model<M>> void saveOrUpdate(M model) {
		try{
			model.save();
		}catch(ActiveRecordException e){
			
			
			if(e.getMessage().indexOf("Duplicate entry") > -1){
	
				model.update();
			}else{
				throw e;
			}
		}
	}

}
