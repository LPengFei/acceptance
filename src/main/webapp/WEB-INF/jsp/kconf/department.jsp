<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="../include/taglib.jsp" %>
<div class="bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/kconf/department" method="post">
		<input type="hidden" name="pageNumber" value="${query.pageNumber }" /> 
		<input type="hidden" name="pageSize" value="${query.pageSize }" /> 
		<input type="hidden" name="orderField" value="${query.orderField }" />
		<input type="hidden" name="orderDirection" value="${query.orderDirection}">
		<div class="bjui-searchBar ess-searchBar">
 		<a href="${ctx}/kconf/department/edit" data-toggle="navtab" data-id="department-create" data-title="新增" class="btn btn-green" data-icon="plus">添加</a>&nbsp;
		</div>
	</form>
</div><div class="bjui-pageContent tableContent  white ess-pageContent">
	<table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
		<thead>
			<tr>
				<c:forEach items="${fields }" var="f"> 
					<c:if test="${f.is_list_view ==1 }">
						<th data-order-field="${f.field_name }">${f.field_alias }</th>
					</c:if>
				</c:forEach>  
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="r" varStatus="s">
				<tr>
					<c:forEach items="${fields }" var="f">  
						<c:if test="${f.is_list_view ==1 }">
							<kval:val model="${r }" field="${f }"></kval:val>
						</c:if>
					</c:forEach>  
					<td>
						<a style="color:red;"  data-toggle="doajax"  data-confirm-msg="确定要删除该行信息吗？" href="${ctx }/kconf/department/delete?id=${r.did}">删除</a>  &nbsp;&nbsp;
 						<a href="${ctx}/kconf/department/edit?id=${r.did}"  style="color:green;"  data-toggle="navtab" data-id="department-edit" data-title="编辑用户" >修改</a>
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
