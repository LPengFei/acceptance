<%--
  Created by IntelliJ IDEA.
  User: gaozhou
  Date: 2016/12/28
  Time: 13:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>

<table class="table table-bordered project-scale" style="background-color: #b9bfbf;">
    <thead>
    <tr>
        <th title="序号" width="50">序号</th>
        <th title="验收时间">验收时间</th>
        <th title="验收任务">验收任务</th>
        <th title="验收卡">验收卡</th>
        <th title="验收记录">验收记录</th>
        <th title="重大问题反馈联系单">重大问题反馈联系单</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${tasks}" var="v" varStatus="s">
        <tr>
            <td>${s.count}</td>
            <td>${v.time}</td>
            <td>${v.name}</td>
            <td>查看</td>
            <td>查看</td>
            <td>查看</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
