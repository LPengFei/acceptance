<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>  
<%@ include file="../include/taglib.jsp" %>
	<div class="bjui-pageContent">
	<div class="ess-form">
		<form action="${ctx}/kconf/model/save"  data-toggle="validate" data-alertmsg="false">
			<input type="hidden" name="record.mid" value="${record.mid }">
			<ul>
				 <li>
					<label>模型名称</label>
				 	<input type="text" name="record.mname" id="j_mname" value="${record.mname }" data-rule="required" placeholder="请输入模型名称">
				 </li>
				 <li>
					<label>对应数据表</label>
				 	<input type="text" name="record.mtable" id="j_mtable" value="${record.mtable }" data-rule="required" placeholder="请输入数据表">
				 </li>
				 <li>
					<label>备注</label>
				 	<input type="text" name="record.remark" id="j_remark" value="${record.remark }" placeholder="请输入备注">
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
