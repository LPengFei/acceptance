package com.cnksi.app.service;

import java.util.List;
import java.util.Map;

import com.cnksi.app.model.PmsDevice;

public class PmsDeviceService extends PmsService {
	public final static PmsDeviceService service = new PmsDeviceService();
	

	public void sync(String bdzid, String spacingid, String account, String pwd) {
		
		pmsSyncService.updateDeviceList(spacingid, account, pwd, new ListHandler(bdzid,spacingid) {
			
			@Override
			public void handel(List<Map<String, Object>> list) {
				String bdzid = getConstructPara(0);
				String spacingid = getConstructPara(1);
				
				for (Map<String, Object> map : list) {
					
					//更新设备数据
					PmsDevice model = new PmsDevice();
					String id = (String) map.get(pmsID);
					model.set("id", spacingid+"_"+id);	//ID 默认与PmsID相同
					model.set("bid", bdzid);
					model.set("sid", spacingid);
					model.set("one", "yes");
					model.set("enabled", 0);
					setPmsField(map,model);
					
					saveOrUpdate(model);
					
				}
			}
		});
	}

}
