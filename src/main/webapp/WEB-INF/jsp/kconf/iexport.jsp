<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="../include/taglib.jsp" %>
<div class="bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/kconf/iexport" method="post">
		<input type="hidden" name="pageNumber" value="${query.pageNumber }" /> 
		<input type="hidden" name="pageSize" value="${query.pageSize }" /> 
		<input type="hidden" name="orderField" value="${query.orderField }" />
		<input type="hidden" name="orderDirection" value="${query.orderDirection}">
		<div class="bjui-searchBar ess-searchBar">
			<label>配置名称：</label><input type="text" value="${query.iename }" name="iename" size="10">&nbsp; 
			<label>导出类型：</label><input type="text" value="${query.ietype }" name="ietype" size="10">&nbsp; 
			<button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button> &nbsp;
 			<a href="${ctx}/kconf/iexport/edit" data-toggle="navtab" data-id="confiexport-create" data-title="新增Excel配置" class="btn btn-green" data-icon="plus" style="float:right;">添加</a>&nbsp;
		</div>
	</form>
</div><div class="bjui-pageContent tableContent  white ess-pageContent">
	<table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
		<thead>
			<tr>
				<th>配置ID</th>
				<th>配置名称</th>
				<th>类型</th>
				<th>导出表</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="r" varStatus="s">
				<tr>
					<td>${r.ieid }</td>
					<td>${r.iename }</td>
					<td>${r.ietype }</td>
					<td>${r.ietable}</td>
					<td>
						<a style="color:red;"  data-toggle="doajax"  data-confirm-msg="确定要删除该行信息吗？" href="${ctx }/kconf/iexport/delete?id=${r.ieid}">删除</a>  &nbsp;&nbsp;
 						<a href="${ctx}/kconf/iexport/edit?id=${r.ieid}"  style="color:green;"  data-toggle="navtab" data-id="iexport-edit" data-title="编辑" >修改</a>  &nbsp;&nbsp;
						<a href="${ctx}/kconf/iexport/fields?ieid=${r.ieid}" data-toggle="navtab" data-id="fields-list" data-title="导入\导出字段配置"  >字段配置</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div class="bjui-pageFooter">
	<div class="pages">
		<span>每页&nbsp;</span>
		<div class="selectPagesize">
			<select data-toggle="selectpicker" data-toggle-change="changepagesize">
				<option value="30">30</option>
				<option value="60">60</option>
				<option value="120">120</option>
				<option value="150">150</option>
			</select>
		</div>
		<span>&nbsp;条，共 ${page.totalRow } 条</span>
	</div>
	<div class="pagination-box" data-toggle="pagination" data-total="${page.totalRow }" data-page-size="${page.pageSize }" data-page-current="${page.pageNumber }" data-page-num="15"></div>
</div>
