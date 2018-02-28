<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="../include/taglib.jsp" %>
<div class="bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/kconf/role" method="post">
		<input type="hidden" name="pageNumber" value="${query.pageNumber }" /> 
		<input type="hidden" name="pageSize" value="${query.pageSize }" /> 
		<input type="hidden" name="orderField" value="${query.orderField }" />
		<input type="hidden" name="orderDirection" value="${query.orderDirection}">
		<div class="bjui-searchBar ess-searchBar">
			<label>角色名称：</label><input type="text" value="${query.rname }" name="rname" size="10">&nbsp; 
			<button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button>
			<button type="button" data-url="${ctx}/kconf/role/confMenu" data-icon="plus" data-toggle="navtab" style="background: #14CAB4; color: white; float: right;" data-id="role-create" data-title="新增模型" class="btn">新增</button>
		</div>
	</form>
</div><div class="bjui-pageContent tableContent  white ess-pageContent">
	<table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
		<thead>
			<tr>
				<th>序号</th>
				<th>角色名称</th>
				<th>角色备注</th>
		        <th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="record" varStatus="s">
			    <c:set var="id" value="${record[model.pkName]}"></c:set>
			    <tr>
			        <td style="width: 35px;">${s.index+1 }</td>
			        <td>${record.rname }</td>
			        <td>${record.remark }</td>
			        <td style="width: 60px;padding-left:10px;">
				        <a style="color:red;"  data-toggle="doajax"  data-confirm-msg="确定要删除该行信息吗？" href="${ctx }/kconf/role/delete?id=${record.rid}">删除</a>  &nbsp;&nbsp;  
				        <a href="${ctx}/kconf/role/confMenu?id=${record.rid}"  style="color:green;"  data-toggle="navtab" data-id="role-menu" data-title="配置菜单" >菜单配置</a>&nbsp;&nbsp;
						<a href="${ctx}/kconf/role/confAuthority?id=${record.rid}"  style="color:green;"  data-toggle="navtab" data-id="role-authority" data-title="配置权限" >权限配置</a>
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
