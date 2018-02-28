<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--动态生成列表table head --%>
<c:forEach items="${fields }" var="f">
	<c:if test="${f.is_list_view == 1 }">
		<c:set var="listset" value="${f.settings.listview }"></c:set>
		<c:set var="orderEnable" value="${listset.is_list_sort eq '1' }"></c:set>
						
		<th title="${f.field_alias }" width="${listset.list_width }"
			<c:if test="${orderEnable }"> data-order-field="${f.field_name }" </c:if> >${f.field_alias }</th>
	</c:if>
</c:forEach>
