<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>  
<%@ include file="../include/taglib.jsp" %>
	<div class="bjui-pageContent">
	<div class="ess-form">
		<form action="${ctx}/kconf/datasource/save"  data-toggle="validate" data-alertmsg="false">
			<input type="hidden" name="record.dsid" value="${record.dsid }">
			<ul>
				 <li>
					<label>数据源名称</label>
				 	<input type="text" name="record.dsname" id="j_dsname" value="${record.dsname }" data-rule="required" placeholder="请输入名称">
				 </li>
				 <li style="width:450px;">
					<label>数据源地址</label>
				 	<input type="text" name="record.dataurl" id="j_dataurl" value="${record.dataurl }" data-rule="required" placeholder="数据源地址" style="width:400px">
				 </li>
			</ul>
			<div class="clearfix"></div>
			<!-- 操作按钮 -->
			<div style="text-align:right; padding-top: 20px;">
				<button type="submit" class="btn" style="background: #14CAB4; color: white;">保存</button>
				<button type="button" class="btn-close" style="background: red; color: white;">取消</button>
			</div>
			
		</form>
		
	</div>
</div>
