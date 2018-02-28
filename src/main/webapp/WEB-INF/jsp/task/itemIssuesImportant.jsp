<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>

<style type="text/css">
    .table th, .table td {
        border: 1px solid #DDDDDD;
    }
</style>

<div class="table" style="margin-top: 10px;">
    <table border="1" class="table">
        <tr class="title_tr">
            <th width="5%" align="center">序号</th>
            <th width="20%" align="center">编号</th>
            <th width="15%" align="center">问题名称</th>
            <th width="50%" align="center">问题描述</th>
            <th width="10%" align="center">操作</th>
        </tr>
        <c:forEach var="resultIssueImportant" items="${resultIssueImportants}" varStatus="index">
            <tr>
                <td>${index.count}</td>
                <td>${resultIssueImportant.no}</td>
                <td>${resultIssueImportant.name}</td>
                <td>${resultIssueImportant.description}</td>
                <td>
                    <a href="${ctx}/${appid}/task/itemIssueImportantDetail?id=${resultIssueImportant.id}"
                       style="color: #85CC34;"
                       data-toggle="navtab" data-id="task-itemIssueImportantDetail" data-title="重大问题反馈单查看"
                    >
                        查看
                    </a>
                    &nbsp;&nbsp;
                    <a href="${ctx}/${appid}/task/downloadItemIssueImportant?issueImportantId=${resultIssueImportant.id}&taskId=${taskId}" target="_blank" style="color: #1BBC9C;">导出</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>