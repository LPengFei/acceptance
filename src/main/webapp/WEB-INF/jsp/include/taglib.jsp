<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/kvalue" prefix="kval" %>
<%@ taglib uri="/kprop" prefix="kprop" %>
<c:set var="appid" value="app"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctx_kess" value="${pageContext.request.contextPath}/${appid }"/>

<%--  js页面的ctx常量  --%>
<script type="text/javascript">
    var SERVER_CTX = "${ctx}";
    var SERVER_CTX_KESS = "${ctx_kess}";
    var APP_ID = "${appid}";
</script>