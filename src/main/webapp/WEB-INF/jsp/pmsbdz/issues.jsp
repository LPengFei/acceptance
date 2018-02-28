<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>

<table class="table table-bordered project-scale" style="background-color: #b9bfbf;">
    <thead>
    <tr>
        <th title="序号" width="50">序号</th>
        <th title="问题描述">问题描述</th>
        <th title="发现时间">发现时间</th>
        <th title="重大问题">重大问题</th>
        <th title="是否已整改">是否已整改</th>
        <th title="整改时间">整改时间</th>
        <th title="验收卡">验收卡</th>
        <th title="验收记录">验收记录</th>
        <th title="重大问题反馈联系单">重大问题反馈联系单</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${issues}" var="v" varStatus="s">
        <tr>
            <td>${s.count}</td>
            <td>${v.description}</td>
            <td><fmt:formatDate value="${v.createtime}" pattern="yyyy-MM-dd"/></td>
            <td>是</td>
            <td>${v.status eq "cleared" ? '是': '否'}</td>
            <td><fmt:formatDate value="${v.cleartime}" pattern="yyyy-MM-dd"/></td>
            <td>查看</td>
            <td>查看</td>
            <td>查看</td>
        </tr>
    </c:forEach>
    </tbody>
</table>