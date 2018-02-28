<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="../include/taglib.jsp" %>
<div class="bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/kconf/datasource" method="post">
		<input type="hidden" name="pageNumber" value="${query.pageNumber }" /> 
		<input type="hidden" name="pageSize" value="${query.pageSize }" /> 
		<input type="hidden" name="orderField" value="${query.orderField }" />
		<input type="hidden" name="orderDirection" value="${query.orderDirection}">
		<div class="bjui-searchBar ess-searchBar">
			<label>名称：</label><input type="text" value="${query.dsname }" name="dsname" size="10">&nbsp; 
			<button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button> &nbsp; 
			<button type="button"  data-url="${ctx}/kconf/datasource/edit" data-icon="plus" data-toggle="navtab" style="background: #14CAB4; color: white; float: right;" data-id="datasource-create" data-title="新增datasource">新增</button>
		</div>
	</form>
</div><div class="bjui-pageContent tableContent  white ess-pageContent">
	<table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
		<thead>
			<tr>
				<th>数据源名称</th>
				<th>数据源地址</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="r" varStatus="s">
				<tr>
					<td>${r.dsname }</td>
					<td>${r.dataurl }</td>
					<td>
						<a style="color:red;"  data-toggle="doajax"  data-confirm-msg="确定要删除该行信息吗？" href="${ctx }/kconf/datasource/delete?id=${r.dsid}">删除</a>  &nbsp;&nbsp;
 						<a href="${ctx}/kconf/datasource/edit?id=${r.dsid}"  style="color:green;"  data-toggle="navtab" data-id="datasource-edit" data-title="编辑数据源" >修改</a>
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
