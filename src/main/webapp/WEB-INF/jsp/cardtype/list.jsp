<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="../include/taglib.jsp" %>
<c:choose>
	<c:when test="${not empty jsFile}">
		<script src="${ctx }${jsFile}"></script>
	</c:when>
	<c:otherwise>
<script type="text/javascript">
initForm();
</script>
	</c:otherwise>
</c:choose>

<div class="bjui-pageHeader">
	<c:set var="modelTitle" value="${model.mname }"></c:set>

	<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/${appid }/${modelName}" method="post">
		<input type="hidden" name="pageNumber" value="${query.pageNumber }" /> 
		<input type="hidden" name="pageSize" value="${query.pageSize }" /> 
		<input type="hidden" name="orderField" value="${query.orderField }" />
		<input type="hidden" name="orderDirection" value="${query.orderDirection}">
		
		<div class="bjui-searchBar ess-searchBar">
			<%--动态生成查询条件 start --%>
			<%@include file="../common/common_list_thead.jsp" %>
			<%--动态生成查询条件 end --%>
			<button type="button"  data-url="${ctx}/${appid }/${modelName}/edit" data-icon="plus" data-toggle="navtab" data-id="${modelName }-edit" data-title="新增${modelTitle }" style="background: #14CAB4; color: white; float: right;"  >新增</button>
			
			<%--<div class="pull-right">--%>
 				<%--<c:if test="${!empty(exports) or !empty(imports) }">--%>
	 			<%--<div class="btn-group">--%>
	                <%--<button type="button" class="btn-default dropdown-toggle" data-toggle="dropdown" data-icon="copy">导出-批量操作<span class="caret"></span></button>--%>
	                <%--<ul class="dropdown-menu right" role="menu">--%>
	                	<%--<c:forEach items="${exports }" var="e">--%>
	                    	<%--<li><a href="${ctx}/${appid }/${modelName}/export?xlsid=${e.ieid}" data-toggle="doexport" data-confirm-msg="确定要${e.iename }吗？" class="blue">${e.iename }-导出</a></li>--%>
	                    <%--</c:forEach>--%>
	                    <%--<c:if test="${!empty(imports) }">--%>
	                    <%--<li class="divider"></li>--%>
	                   	<%--<c:forEach items="${imports }" var="e">--%>
	                    	<%--<li><a href="${ctx}/${appid }/${modelName}/importxls?xlsid=${e.ieid}"  data-toggle="dialog" data-width="500" data-height="200" data-id="dialog-normal" class="green">${e.iename }-导入</a></li>--%>
	                    <%--</c:forEach>--%>
	                    <%--</c:if>--%>
	                    <%--<!-- --%>
	                    <%--<li><a href="book1.xlsx" data-toggle="doexportchecked" data-confirm-msg="确定要导出选中项吗？" data-idname="expids" data-group="ids">导出<span style="color: red;">选中</span></a></li>--%>
	                    <%--<li class="divider"></li>--%>
	                    <%--<li><a href="ajaxDone2.html" data-toggle="doajaxchecked" data-confirm-msg="确定要删除选中项吗？" data-idname="delids" data-group="ids">删除选中</a></li> --%>
	                    <%---->--%>
	                <%--</ul>--%>
	            <%--</div>--%>
	            <%--</c:if>--%>
           <%--</div> --%>
           
		</div>
	</form>
</div>
<div class="bjui-pageContent tableContent white ess-pageContent">
	<table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
		<thead>
			<tr>
				<th title="序号" width="50">序号</th>
				<%@include file="../common/common_list_thead.jsp" %>
				<th width="70">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="r" varStatus="s">
				<tr>
					<td>
							${(page.pageNumber-1) * page.pageSize + s.count}
					</td>
					<c:forEach items="${fields }" var="f">  
						<c:if test="${f.is_list_view ==1 }">
							<kval:val model="${r }" field="${f }"></kval:val>
						</c:if>
					</c:forEach>  
					<td>
						<a style="color:red;" data-toggle="doajax"  data-confirm-msg="确定要删除该行信息吗？" href="${ctx }/${appid }/${modelName }/delete/${r[pkName]}">删除</a>  &nbsp;
 						<a href="${ctx}/${appid }/${modelName }/edit/${r[pkName]}"  style="color:green;"  data-toggle="navtab" data-id="${modelName }-edit" data-title="编辑${modelTitle }" >修改</a>
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
