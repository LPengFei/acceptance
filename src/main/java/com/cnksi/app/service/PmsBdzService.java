package com.cnksi.app.service;

import java.util.List;
import java.util.Map;

import com.cnksi.app.model.PmsBdz;
import com.cnksi.app.model.PmsSpacing;

public class PmsBdzService extends PmsService{
	public final static PmsBdzService service = new PmsBdzService();
	
	public void sync(String account, String pwd) {
		pmsSyncService.upateBdzList(account, pwd, new ListHandler(){

			@Override
			public void handel(List<Map<String, Object>> list) {
				for (Map<String, Object> map : list) {
					
					PmsBdz model = new PmsBdz();
					String id = (String) map.get(pmsID);
					model.set("id", id);	//ID 默认与PmsID相同
					model.set("enabled", 0);
					setPmsField(map, model);
					
					saveOrUpdate(model);
				}
			}
		});
	}
}
