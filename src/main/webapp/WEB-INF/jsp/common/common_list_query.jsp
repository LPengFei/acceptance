<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--动态生成查询条件  --%>
<c:set var="queryCount" value="0"></c:set>

<c:forEach items="${fields }" var="f">
	<c:set var="listset" value="${f.settings.listview }"></c:set>
	<c:set var="fset" value="${f.settings.formview }"></c:set>
	
	<c:if test="${!empty(listset.list_search_op)}">
		<c:set var="queryCount" value="${queryCount + 1 }"></c:set>
		
		<div><label>${f.field_alias }：</label>
			<c:choose>
				<c:when test="${fn:contains(f.data_source, '?')}">
					<input type="text" value="${requestScope[f.field_name] }" name="${f.field_name }"
						   data-ds="${ctx}${f.data_source }&isFilter=true"
						   data-toggle="${fset.view_type }"
						   data-chk-style="${fset.chk_style }"
						   data-live-search='${fset.select_search }'
					>
				</c:when>
				<c:otherwise>
					<input type="text" value="${requestScope[f.field_name] }" name="${f.field_name }"
						   data-ds="${ctx}${f.data_source }?isFilter=true"
						   data-toggle="${fset.view_type }"
						   data-chk-style="${fset.chk_style }"
						   data-live-search='${fset.select_search }'
					>
				</c:otherwise>
			</c:choose>
			&nbsp;
		</div>
	</c:if>
</c:forEach>

<c:if test="${queryCount gt 0 }">
	<button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button>
</c:if>
