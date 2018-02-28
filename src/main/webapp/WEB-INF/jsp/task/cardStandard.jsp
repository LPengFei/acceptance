<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>


<c:forEach items="${cardStandards}" var="cardStandard" varStatus="index">
    <h5>${index.count}.&nbsp;&nbsp;${cardStandard.description}</h5>
</c:forEach>