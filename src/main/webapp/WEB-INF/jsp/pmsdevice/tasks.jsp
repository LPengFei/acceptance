<%--
  Created by IntelliJ IDEA.
  User: gaozhou
  Date: 2016/12/28
  Time: 13:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>


<script type="text/javascript">
    function associateCallback() {
        $(".issues-and-tasks li.top-nav-active").click();
    }
</script>

<table class="table table-bordered project-scale">
    <thead>
    <tr>
        <th title="序号" width="50">序号</th>
        <th title="验收时间">验收时间</th>
        <th title="验收任务">验收任务</th>
        <th title="验收卡">验收卡</th>
        <th title="验收记录">验收记录</th>
        <th title="重大问题反馈联系单">重大问题反馈联系单</th>
        <th title="操作">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${tasks}" var="v" varStatus="s">
        <tr>
            <td>${s.count}</td>
            <td>${v.time}</td>
            <td>${v.name}</td>
            <td>
                <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=0" data-toggle="navtab"
                   data-id="task-info"
                   data-title="详情任务">查看</a>
            </td>
            <td>
                <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=1" data-toggle="navtab"
                   data-id="task-info"
                   data-title="详情任务">查看</a>
            </td>
            <td>
                <c:if test="${v.flow ne 'feasible_check'}">
                    <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=2" data-toggle="navtab"
                       data-id="task-info"
                       data-title="详情任务">查看</a>
                </c:if>
            </td>
            <td>
                <a href="${ctx}/${appid}/pmsdevice/associateTree?taskId=${v.id}&notLoad=true" class="btn btn-default" data-toggle="dialog" data-width="400" data-height="800" data-id="associate-box" data-on-close="associateCallback">重新关联</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
