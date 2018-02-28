<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="../include/taglib.jsp" %>
<div class="bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/kconf/lookup/types" method="post">
		<input type="hidden" name="pageNumber" value="${query.pageNumber }" /> 
		<input type="hidden" name="pageSize" value="${query.pageSize }" /> 
		<input type="hidden" name="orderField" value="${query.orderField }" />
		<input type="hidden" name="orderDirection" value="${query.orderDirection}">
		<div class="bjui-searchBar ess-searchBar">
			<label>类型名称：</label><input type="text" value="${query.tname }" name="tname" size="10">&nbsp; &nbsp;
			<button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button> &nbsp;&nbsp;
			<button type="button"  data-url="${ctx}/kconf/lookup/typeEdit" data-icon="plus" data-toggle="navtab" style="background: #14CAB4; color: white; float: right;" data-id="lookuptype-create" data-title="新增常量">新增</button>
		</div>
	</form>
</div><div class="bjui-pageContent tableContent  white ess-pageContent">
	<table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
		<thead>
			<tr>
				<th>类型ID</th>
				<th>类型名称</th>
				<th>备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="r" varStatus="s">
				<tr>
					<td>${r.ltid }</td>
					<td>${r.tname }</td>
					<td>${r.remark }</td>
					<td style="padding-left:10px;">
						<a class="red"  data-toggle="doajax"  data-confirm-msg="确定要删除该行信息吗？" href="${ctx }/kconf/lookup/typeDelete?id=${r.ltid}">删除</a>  &nbsp;&nbsp;
 						<a href="${ctx}/kconf/lookup/typeEdit?id=${r.ltid}"  style="color:green;"  data-toggle="navtab" data-id="lookuptype-edit" data-title="编辑用户" >修改</a>  &nbsp;&nbsp;
 						<a href="${ctx}/kconf/lookup?type=${r.ltid}"  style="color:green;"  data-toggle="navtab" data-id="lookup-list" data-title="查看Lookup" >查看Lookup</a> 
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
