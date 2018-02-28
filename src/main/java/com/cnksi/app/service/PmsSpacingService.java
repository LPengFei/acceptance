package com.cnksi.app.service;

import java.util.List;
import java.util.Map;

import com.cnksi.app.model.PmsSpacing;

public class PmsSpacingService extends PmsService {
	public final static PmsSpacingService service = new PmsSpacingService();
	
	

	/**
	 * 同步设备信息
	 */
	public void sync(String bdzid, String account, String pwd) {
		pmsSyncService.updateSpacingList(bdzid, account, pwd, new ListHandler(bdzid) {
			@Override
			public void handel(List<Map<String, Object>> list) {
				String bdzid = getConstructPara(0);
				
				for (Map<String, Object> map : list) {

					//更新间隔数据
					PmsSpacing model = new PmsSpacing();
					String id = (String) map.get(pmsID);
					model.set("id", id);	//ID 默认与PmsID相同
					model.set("bid", bdzid);
					model.set("one", "yes");
					model.set("enabled", 0);
					setPmsField(map, model);
					
					saveOrUpdate(model);
					
					
					//同步设备数据
					PmsDeviceService.service.sync(bdzid,id,account,pwd);
				}
				
				
			}
		});
		
	}



}
