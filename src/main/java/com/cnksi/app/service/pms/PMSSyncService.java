package com.cnksi.app.service.pms;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * 处理PMS对接的逻辑
 */
@SuppressWarnings("unused")
public class PMSSyncService{
	public final static PMSSyncService service = new PMSSyncService();
	
	//获取部门/班组信息
	private String dept_class_url = PmsConstants.PMS_DATA_POST_URL + "/sgpms/com.sgcc.pms.ywjx.pwgg/rest/commsvc/getRyxx";
	//根据班组获取人员信息
	private String user_url = PmsConstants.PMS_DATA_POST_URL + "/sgpms/pmsframeworkisc/rest/iscuserlocext/?rnd=0.41469772229902446&params=%7B%22columns%22%3A%22iscId%2Crymc%2Cssbmid%22%2C%22filter%22%3A%22ssbmid%3D[dept_id]%22%2C%22pageIndex%22%3A1%2C%22pageSize%22%3A100%7D&_=1459926964421";
	//根据班组获取变电站信息
	public final static String bdz_url = PmsConstants.PMS_DATA_POST_URL + "/sgpms/dwzy/rest/grid/?rnd=0.24340261612087488&params=%7B%22columns%22%3A%22BDZMC%2CWHBZ%2CDYDJ%2CTYRQ%2CWHDJ%2CSFSNZ%2CBZFS%2CYXZT%2CDZLX%2Cobj_id%22%2C%22sorter%22%3A%22(SELECT+px+FROM+t_Dw_Bzzx_Ggdmb+where+bzflbm%3D'010401'+and+dm+%3D+dydj)+%2CNLSSORT(BDZMC%2C'NLS_SORT+%3D+SCHINESE_PINYIN_M')%22%2C%22filter%22%3A%22columns%3DBDZMC%2CWHBZ%2CDYDJ%2CTYRQ%2CWHDJ%2CSFSNZ%2CBZFS%2CYXZT%2CDZLX%2Cobj_id%26jmmmId%3D538dfc0e-4ae5-47ed-9ccf-01b82842425d-11452%26where%3D+WHBZ+%3D'[dept_id]'+and++1%3D1+and++yxzt+in+('10'%2C+'20'%2C+'31')+%26bgsqdId%3Dnull%22%2C%22pageIndex%22%3A1%2C%22pageSize%22%3A100%7D&_=1459927538884";
	//间隔获取地址
	public final static String spacing_url = PmsConstants.PMS_DATA_POST_URL + "/sgpms/dwzy/rest/grid/?rnd=0.8281458492856473&params=%7B%22columns%22%3A%22%22%2C%22sorter%22%3A%22+NLSSORT(JGDYMC%2C'NLS_SORT+%3D+SCHINESE_PINYIN_M')%22%2C%22filter%22%3A%22columns%3DJGDYBH%2CJGDYMC%2CSSZF%2CDYDJ%2CJGDYLX%2CJXFS%2CDDDW%2Cobj_id%26jmmmId%3D538dfc0e-4ae5-47ed-9ccf-01b86542425d-11452%26where%3D+(JGDYLX+%3C%3E'16'+OR+JGDYLX+IS+NULL)+and+sszf+%3D+'[bdz_id]'+and+ssjg+is+null+and++yxzt+in+('10'%2C+'20'%2C+'31')+%26bgsqdId%3Dnull%22%2C%22pageIndex%22%3A1%2C%22pageSize%22%3A1000%7D&_=1459497053255";
	//设备获取地址
	public final static String device_url = PmsConstants.PMS_DATA_POST_URL + "/sgpms/dwzy/rest/grid/?rnd=0.4118058041203767&params=%7B%22columns%22%3A%22%22%2C%22sorter%22%3A%22XS%2CNLSSORT(SBMC%2C'NLS_SORT+%3D+SCHINESE_PINYIN_M')%22%2C%22filter%22%3A%22columns%3DYXBH%2CSBMC%2CSBLX%2CJGDY%2CDYDJ%2Cyzz%2CYGLTX%2CDXMPYXKID%2CYXZT%2CXS%2Cobj_id%26jmmmId%3D538dfc0e-4ae5-47ed-9ccf-01b83342425d-11452%26where%3Djgdy%3D'[spacing_id]'+and++yxzt+in+('10'%2C+'20'%2C+'31')+%26bgsqdId%3Dnull%22%2C%22pageIndex%22%3A1%2C%22pageSize%22%3A1000%7D&_=1459496581388";
	//设备安装位置地址
	private String device_azwz_url = PmsConstants.PMS_DATA_POST_URL + "/sgpms/com.sgcc.pms.ywjx.yxzbgl/rest/tyjdwyjyxrzylcspz/?rnd=0.3575667254626751&params=%7B%22columns%22%3A%22objId%2Cbdzid%2Cbdzmc%2Cjclx%2Cjcsbid%2Cjcsbmc%2Cazwz%2Cbsz%2Cgjz%2Cjcybmc%2Csx%22%2C%22sorter%22%3A%22sx+ASC%22%2C%22filter%22%3A%22bdzid%3D[bdzid]%22%2C%22pageIndex%22%3A1%2C%22pageSize%22%3A2000%7D&_=1470643166868";
	//部件获取地址
	private String part_url = PmsConstants.PMS_DATA_POST_URL + "/sgpms/dwzy/rest/groupItemGrid/?rnd=0.9971760539337993&params=%7B%22columns%22%3A%22%22%2C%22sorter%22%3A%22SBLXMC+ASC+%22%2C%22filter%22%3A%22columns%3Dobj_id%2CSSSB%2CSBBM%2CSBMC%2CSBLXMC%2CSBLX%2CXH%2CSCCJ%2CZZGJ%2CCCBH%2CCCRQ%2CTYRQ%2CYXZT%26jmmmId%3D548dfc0e-4ae5-4jia-9thn-01b84632905d-93180%26where%3D+sssb%3D'[device_id]'%26bgsqdId%3Dnull%22%7D&_=1459496735733";
	//接地网
	private String jdw_url = PmsConstants.PMS_DATA_POST_URL + "/sgpms/dwzy/rest/grid/?rnd=0.4579594060778618&params=%7B%22columns%22%3A%22%22%2C%22sorter%22%3A%22NLSSORT(SBMC%2C'NLS_SORT+%3D+SCHINESE_PINYIN_M')%22%2C%22filter%22%3A%22columns%3DSBMC%2CSSZF%2CTYRQ%2CYXZT%2CZCXZ%2CDJSJ%2Cobj_id%26jmmmId%3DCC249095-C128-4AA3-99D1-4D0949A459D5-00001%26where%3DSSZF%3D'[bdz_id]'+AND++yxzt+in+('10'%2C+'20'%2C+'31')+%26bgsqdId%3Dnull%22%2C%22pageIndex%22%3A1%2C%22pageSize%22%3A20%7D&_=1460085469721";
	//避雷针
	private String blz_url = PmsConstants.PMS_DATA_POST_URL + "/sgpms/dwzy/rest/grid/?rnd=0.6276298365555704&params=%7B%22columns%22%3A%22%22%2C%22sorter%22%3A%22NLSSORT(SBMC%2C'NLS_SORT+%3D+SCHINESE_PINYIN_M')%22%2C%22filter%22%3A%22columns%3DSBMC%2CYXBH%2CSSZF%2CTYRQ%2CYXZT%2CZCXZ%2Cobj_id%26jmmmId%3DD1532BD0-00A2-46F8-A6C0-D63E74777A5C-00001%26where%3DSSZF%3D'[bdz_id]'+AND++yxzt+in+('10'%2C+'20'%2C+'31')+%26bgsqdId%3Dnull%22%2C%22pageIndex%22%3A1%2C%22pageSize%22%3A20%7D&_=1460085402135";
	//间隔(二次)获取地址
	private String second_spacing_url = PmsConstants.PMS_DATA_POST_URL + "/sgpms/dwzy/rest/grid/?rnd=0.050572884015739&params=%7B%22columns%22%3A%22%22%2C%22filter%22%3A%22columns%3DJGDYMC%2CJGDYBH%2CJGDYLX%2CSSZF%2CDDDW%2CJXFS%2Cobj_id%26jmmmId%3D2F1860FD-0480-45E6-861C-9D6BF7DEFEA7-00001%26where%3Dsszf+%3D'[bdz_id]'++and++yxzt+in+('10'%2C+'20'%2C+'31')+%26bgsqdId%3Dnull%22%2C%22pageIndex%22%3A1%2C%22pageSize%22%3A1000%7D&_=1459920038953";
	//设备(二次)获取地址
	private String second_device_url = PmsConstants.PMS_DATA_POST_URL + "/sgpms/dwzy/rest/grid/?rnd=0.9768483522348106&params=%7B%22columns%22%3A%22%22%2C%22filter%22%3A%22columns%3DSBMC%2CSBLX%2CYXZT%2CDYDJ%2CCCBH%2CCCRQ%2CTYRQ%2Cobj_id%26jmmmId%3DFEE340E0-24AD-4334-AF20-80C4B8BB98C1-00001%26where%3D+ssjg+%3D'[spacing_id]'++and++yxzt+in+('10'%2C+'20'%2C+'31')+%26bgsqdId%3Dnull%22%2C%22pageIndex%22%3A1%2C%22pageSize%22%3A300%7D&_=1459920448309";
	//二次屏
	private String second_ercp_url = PmsConstants.PMS_DATA_POST_URL + "/sgpms/dwzy/rest/grid/?rnd=0.8417029967531562&params=%7B%22columns%22%3A%22%22%2C%22filter%22%3A%22columns%3DPMC%2CYWDW%2CSSDS%2CSSZF%2CPGLX%2CYXZT%2CSCCJ%2Cobj_id%26jmmmId%3DA15F033D-FB55-4A7B-BA3D-B53DB2FC2B2F-00001%26where%3Dsszf+%3D'[bdz_id]'++and++yxzt+in+('10'%2C+'20'%2C+'31')+%26bgsqdId%3Dnull%22%2C%22pageIndex%22%3A1%2C%22pageSize%22%3A1000%7D&_=1459920903168";
	//继电器
	private String second_jidq_url = PmsConstants.PMS_DATA_POST_URL + "/sgpms/dwzy/rest/grid/?rnd=0.02253336668945849&params=%7B%22columns%22%3A%22%22%2C%22filter%22%3A%22columns%3DSBMC%2CSSDS%2CYWDW%2CSSZF%2CSBLX%2CYXZT%2CSBXH%2Cobj_id%26jmmmId%3D98F00307-C0C6-407F-B168-F3EBF2D8B642-00001%26where%3Dsszf+%3D'[bdz_id]'++and++yxzt+in+('10'%2C+'20'%2C+'31')++and+SSBH+IS+NULL%26bgsqdId%3Dnull%22%2C%22pageIndex%22%3A1%2C%22pageSize%22%3A1000%7D&_=1459920976479";
	//交直流电源及站用电系统
	private String second_jiaozl_url = PmsConstants.PMS_DATA_POST_URL + "/sgpms/dwzy/rest/grid/?rnd=0.7559566416312009&params=%7B%22columns%22%3A%22%22%2C%22filter%22%3A%22columns%3DXTMC%2CYWDW%2CSSZF%2CYXZT%2CCCRQ%2CTYRQ%2CZCXZ%2CZCDW%2CSBZJFS%2Cobj_id%26jmmmId%3D42826CBA-73EA-43ED-8E20-9CCC7FDF897A-00001%26where%3Dsszf+%3D+'[bdz_id]'+and++yxzt+in+('10'%2C+'20'%2C+'31')+%26bgsqdId%3Dnull%22%2C%22pageIndex%22%3A1%2C%22pageSize%22%3A1000%7D&_=1459921006747";
	//自动化设备
	private String second_autosb_url = PmsConstants.PMS_DATA_POST_URL + "/sgpms/dwzy/rest/grid/?rnd=0.6505358607973903&params=%7B%22columns%22%3A%22%22%2C%22filter%22%3A%22columns%3DXTMC%2CAZDD%2CXTLX%2CYYLX%2CSFGYPP%2CDQDW%2Cobj_id%26jmmmId%3DB51A9E9B-0C58-413D-ACBB-E378F85AAF10-00001%26where%3DXTLX+IN('0801007'%2C'0801008'%2C'0801010'%2C'0801011'%2C'0801012'%2C'0801013'%2C'0801015'%2C'0801018'%2C'0801019'%2C'0801021')+and+azdd%3D'[bdz_id]'+and+yxzt+in('10'%2C'20'%2C'31')%26bgsqdId%3Dnull%22%2C%22pageIndex%22%3A1%2C%22pageSize%22%3A1000%7D&_=1459921060056";

	private Logger logger = Logger.getLogger(this.getClass());
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getItemListData(String url, String account, String pwd){
		if (!loginPMS(account, pwd)){
			throw new RuntimeException("登录PMS失败!!!!!!!!!!!");
		}

		logger.info("请求地址："+ url);
		String returnJsonStr = HttpClientUtils.get(url, null, true);
//		logger.info("请求结果："+ returnJsonStr);

		Map<String, Object> map = HttpClientUtils.getItemsValue(returnJsonStr);
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("items");
		return list;
		
	}
	private Map<String, Object> getData(String url, String account, String pwd){
		if (!loginPMS(account, pwd)){
			throw new RuntimeException("登录PMS失败!!!!!!!!!!!");
		}
		logger.info("请求地址："+ url);
		String jsonStr = HttpClientUtils.get(url, null, true);
//		logger.info("请求结果："+ jsonStr);
		
		Map<String, Object> map = HttpClientUtils.turnJsonStrToMap(jsonStr);
		return map;
		
	}

	/**
	 * 登录PMS
	 */
	private boolean loginPMS(String account, String pwd) {
		if(PmsConstants.PMS_DEBUG) return true;		//调试模式直接登录成功
		
		return XunjianPMSService.service.pmsLoginAndAccessValidate(account, pwd);
	}
	
	
	/**
	 * 更新变电站列表数据
	 */
	public void upateBdzList(String account, String pwd,IListHandler handler){
		
		//获取部门信息
		String pmsDeptid = getPMSDeptid(account,pwd);
		
		//获取变电站信息
		String thisBdzUrl = bdz_url.replace("[dept_id]", pmsDeptid);
		List<Map<String, Object>> list = getItemListData(thisBdzUrl,account,pwd);
		
		//处理获取的变电站
		handler.handel(list);
	}

	private String getPMSDeptid(String account, String pwd) {
		Map<String, Object> map = getData(dept_class_url, account, pwd);
		// {"userID":"E3FA8F8801F12D91E040B00AD3034D6C","name":"胡亮","userName":"HuL6034","mobile":null,"ip":null,"mac":null,"requestIp":"10.176.3.150","sessionId":"w5TpXTCR4f3VHzzbvtXvtk6TbS3QdplzDTvp9cqm4S3b5LcxnrKW!-73371178!1460535889337","deptId":"16F37FCECCCF0ED1E0530100007F2F45","deptCode":"90000492","deptName":"变电运维一班","compId":"16F37FCECCC90ED1E0530100007F2F45","compCode":"00042367","compName":"乐山运维检修部(检修分公司)","subComp":null,"sswsid":"16F37FCEBB150ED1E0530100007F2F45","ssddjgid":null,"ssddjgmc":null}
		String deptid = (String) map.get("deptId");
		
		return deptid;
	}
	
	/**
	 * 更新间隔列表数据
	 */
	public void updateSpacingList(String bdzid,String account, String pwd,IListHandler handler){

		String url = spacing_url.replace("[bdz_id]", bdzid);
		List<Map<String, Object>> list = getItemListData(url, account, pwd);
		
		handler.handel(list);
	}
	
	/**
	 * 更新间隔（二次）列表数据
	 */
	public void updateSecondSpacingList(String bdzid,String account, String pwd,IListHandler handler){

		String url = second_spacing_url.replace("[bdz_id]", bdzid);
		List<Map<String, Object>> list = getItemListData(url, account, pwd);
		
		handler.handel(list);
	}
	
	/**
	 * 更新设备列表数据
	 */
	public void updateDeviceList(String spacingid,String account, String pwd,IListHandler handler){

		String url = device_url.replace("[spacing_id]", spacingid);
		List<Map<String, Object>> list = getItemListData(url, account, pwd);
		
		handler.handel(list);
	}
	
	/**
	 * 更新设备（二次）列表数据
	 */
	public void updateSecondDeviceList(String spacingid,String account, String pwd,IListHandler handler){

		String url = second_device_url.replace("[spacing_id]", spacingid);
		List<Map<String, Object>> list = getItemListData(url, account, pwd);
		
		handler.handel(list);
	}
}
