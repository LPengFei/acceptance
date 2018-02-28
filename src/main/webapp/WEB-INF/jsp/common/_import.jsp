<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>  
<%@ include file="../include/taglib.jsp" %>

<script type="text/javascript">

function callback(json){
	 
	if("300"==json.statusCode){
		$("#error").attr("href",json.errorFile);
		$("#error").show(); 
		$("#errorInfo").show();
		$("#error").click(); 
	}else{
		$(this).bjuiajax('ajaxDone', json).navtab("refresh").dialog('closeCurrent',true); // 为指定的tabid设置刷新标记
	}
}
</script>


<div class="bjui-pageContent">
	<br/>
	<br/>
	<form action="${ctx}/${appid }/${modelName }/importxlsed?xlsid=${xlsid}"  data-toggle="validate" data-alertmsg="false" enctype="multipart/form-data" data-callback="callback">
			<label class="control-label x140">请选择数据文件：</label>
		  	<input type="file" name="_xlsfile"  /> 
	</form>
	<div id="errorInfo" style="display:none;color:red;padding:20px 38px 0px 38px;">导入错误：错误信息请查看导入结果文件</div>
	<div style="padding:20px 38px;">
	<a  href="${ctx}/${appid }/${modelName }/exportpl?xlsid=${xlsid}"  style="color:green;"  data-toggle="doexport"  >下载模板</a>
	 
	<a  id="error" href="#"  style="display:none; color:red;margin-left:30px;"  data-toggle="doexport"  >下载错误信息</a>
	</div>
	
	<div class="clearfix"></div>
</div>

<div class="bjui-pageFooter">
	<ul>
		<li><button type="submit" class="btn-default" data-icon="save">上传</button></li>
	</ul>
</div>
 