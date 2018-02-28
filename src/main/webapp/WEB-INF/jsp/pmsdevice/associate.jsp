<%--
  Created by IntelliJ IDEA.
  User: gaozhou
  Date: 2016/12/28
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>

<style type="text/css">
    .issue-content {
        width: 100%;
        height: 50%;
        overflow-y: scroll;
    }

    .task-content {
        width: 100%;
        height: 50%;
        overflow-y: scroll;
        /*margin-top:20px;*/
    }
</style>


<div class="bjui-pageContent tableContent white ess-pageContent">
    <div class="issue-content">
        <table class="table table-bordered project-scale">
            <thead>
            <tr>
                <th title="序号" width="50">序号</th>
                <th title="问题描述">缺陷（问题）描述</th>
                <th title="工程名称">工程名称</th>
                <th title="所属间隔">所属间隔</th>
                <th title="所属设备">所属设备</th>
                <th title="操作">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${issues}" var="v" varStatus="s">
                <tr>
                    <td>${s.count}</td>
                    <td>${v.description}</td>
                    <td>${v.pname}</td>
                    <td>${v.space}</td>
                    <td>${v.devicename}</td>
                    <td>
                        <a href="${ctx}/${appid}/pmsdevice/associateTree?issueId=${v.id}&notLoad=true" class="btn btn-default" data-toggle="dialog" data-width="400" data-height="800" data-id="associate-box">关联</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="task-content">

        <table class="table table-bordered project-scale">
            <thead>
            <tr>
                <th title="序号" width="50">序号</th>
                <th title="验收任务">验收任务</th>
                <th title="工程名称">工程名称</th>
                <th title="设备类型">设备类型</th>
                <th title="设备型号">设备型号</th>
                <th title="设备数量">设备数量</th>
                <th title="操作">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${tasks}" var="v" varStatus="s">
                <tr>
                    <td>${s.count}</td>
                    <td>${v.name}</td>
                    <td>${v.pname}</td>
                    <td>${v.devicetype}</td>
                    <td>${v.deviceno}</td>
                    <td>0</td>
                    <td>
                        <a href="${ctx}/${appid}/pmsdevice/associateTree?taskId=${v.id}&notLoad=true" class="btn btn-default" data-toggle="dialog" data-width="400" data-height="800" data-id="associate-box">关联</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>
</div>
