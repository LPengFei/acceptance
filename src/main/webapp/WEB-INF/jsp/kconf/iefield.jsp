<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="../include/taglib.jsp" %>
<div class="bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/kconf/iexport/fields" method="post">
		<input type="hidden" name="pageNumber" value="${query.pageNumber }" /> 
		<input type="hidden" name="pageSize" value="${query.pageSize }" /> 
		<input type="hidden" name="orderField" value="${query.orderField }" />
		<input type="hidden" name="orderDirection" value="${query.orderDirection}">
		<div class="bjui-searchBar ess-searchBar">
			<label>字段名称：</label><input type="text" value="${query.name }" name="name" size="10">&nbsp; 
			<label>字段别名：</label><input type="text" value="${query.alias }" name="alias" size="10">&nbsp; 
			<button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button> &nbsp;
 			<a href="${ctx}/kconf/iexport/fieldEdit?ieid=${query.ieid}" data-toggle="navtab" data-id="confiefield-create" data-title="新增" class="btn btn-green" data-icon="plus">添加</a>&nbsp;
		</div>
	</form>
</div>

<c:if test="${iexport.ietype eq '导出' }">
<div class="bjui-pageContent tableContent  white ess-pageContent">
	<table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
		<thead><tr class="resize-head"><th style="width: 158px;"></th><th style="width: 182px;"></th><th style="width: 158px;"></th><th style="width: 158px;"></th><th style="width: 170px;"></th><th style="width: 182px;"></th><th style="width: 182px;"></th><th style="width: 182px;"></th><th style="width: 158px;"></th><th style="width: 158px;"></th></tr>
			<tr>
				<th >编号</th>
				<th >字段名称</th>
				<th >别名</th>
				<th >宽度</th>
				<th >类型</th>
				<th >格式化</th>
				<th >顺序</th>
				<th style="text-align: center;">是否导出</th>
				<th >备注</th>
				<th >操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="r" varStatus="s">
				<tr>
					<td>${r.iefid }</td>
					<td>${r.field_name }</td>
					<td>${r.field_alias }</td>
					<td>${r.width }</td>
					<td>${r.type=='1'?'字符串':'数字' }</td>
					<td>${r.format }</td>
					<td>${r.sort }</td>
					<td style="text-align: center;">
						 <c:if test="${r.enabled=='0' }">
							<a data-toggle="doajax"   href="${ctx }/kconf/iexport/fieldDelete?id=${r.iefid}"><span style="color:green">&radic;</span></a>
						</c:if>
						<c:if test="${r.enabled=='1' }">
						 	<a data-toggle="doajax"   href="${ctx }/kconf/iexport/fieldDelete?id=${r.iefid}"><span style="color:red">&chi;</span></a>
						</c:if>
					</td>
					<td>${r.remark }</td>
					<td>
						<a style="color:red;"  data-toggle="doajax"  data-confirm-msg="确定要删除该行信息吗？" href="${ctx }/kconf/iexport/fieldDelete?id=${r.iefid}">删除</a>  &nbsp;&nbsp;
 						<a href="${ctx}/kconf/iexport/fieldEdit?id=${r.iefid}"  style="color:green;"  data-toggle="navtab" data-id="iefield-edit" data-title="编辑用户" >修改</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</c:if>


<c:if test="${iexport.ietype eq '导入' }">
<div class="bjui-pageContent tableContent  white ess-pageContent">
	<table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
		<thead><tr class="resize-head"><th style="width: 158px;"></th><th style="width: 182px;"></th><th style="width: 158px;"></th><th style="width: 158px;"></th><th style="width: 170px;"></th><th style="width: 182px;"></th><th style="width: 182px;"></th><th style="width: 182px;"></th><th style="width: 158px;"></th><th style="width: 158px;"></th></tr>
			<tr>
				<th >编号</th>
				<th >字段名称</th>
				<th >别名</th>
				<th >是否必填</th>
				<th >是否允许为空</th>
				<th style="text-align: center;">是否导入</th>
				<th >备注</th>
				<th >操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="r" varStatus="s">
				<tr>
					<td>${r.iefid }</td>
					<td>${r.field_name }</td>
					<td>${r.field_alias }</td>
					<td>${r.required =='1'?'必填':'非必填'  }</td>
					<td>${r.allow_blank=='1'?'允许空':'非空'  }</td>
					<td style="text-align: center;">${r.enabled=='0'?'<span style="color:green">&radic;</span>':'<span style="color:red">&chi;</span>'  }</td>
					<td>${r.remark }</td>
					<td>
						<a style="color:red;"  data-toggle="doajax"  data-confirm-msg="确定要删除该行信息吗？" href="${ctx }/kconf/iexport/fieldDelete?id=${r.iefid}">删除</a>  &nbsp;&nbsp;
 						<a href="${ctx}/kconf/iexport/fieldEdit?id=${r.iefid}"  style="color:green;"  data-toggle="navtab" data-id="iefield-edit" data-title="编辑用户" >修改</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</c:if>

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
