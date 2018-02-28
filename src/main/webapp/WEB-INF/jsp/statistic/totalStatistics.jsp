<%--
  Created by IntelliJ IDEA.
  User: gaozhou
  Date: 2016/12/29
  Time: 18:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>


<style type="text/css">
    .project-container {
        overflow: hidden;
        width: 100%;
        display: block;
        background: white;
        border: 1px solid #E0E2E5;
        border-top-width: 0px;
    }
</style>



<div class="bjui-pageContent" style="overflow-x: hidden">
    <div class="project-container">
        <div class="col-md-12 statistics-chart">
            <%@ include file="projectStatistics.jsp" %>
        </div>
    </div>
</div>

