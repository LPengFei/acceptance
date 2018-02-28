<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>


<table class="table table-bordered">
    <tr>
        <th width="48">序号</th>
        <th>问题描述</th>
        <th width="200">整改建议</th>
    </tr>
    <c:forEach items="${resultIssues}" var="resultIssue" varStatus="index">
        <tr>
            <td>${index.count}</td>
            <td>${resultIssue.description}</td>
            <td>${resultIssue.suggest}</td>
        </tr>
    </c:forEach>
</table>
