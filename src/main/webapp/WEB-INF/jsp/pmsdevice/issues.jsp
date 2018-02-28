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
        <th title="问题描述">问题描述</th>
        <th title="发现时间">发现时间</th>
        <th title="重大问题">重大问题</th>
        <th title="是否已整改">是否已整改</th>
        <th title="整改时间">整改时间</th>
        <th title="验收卡">验收卡</th>
        <th title="验收记录">验收记录</th>
        <th title="重大问题反馈联系单">重大问题反馈联系单</th>
        <th title="操作">操作</th>
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
            <td>
                <a href="${ctx}/${appid }/task/info?id=${v.tid}&tab=0" data-toggle="navtab"
                   data-id="task-info"
                   data-title="详情任务">查看</a>
            </td>
            <td>
                <a href="${ctx}/${appid }/task/info?id=${v.tid}&tab=1" data-toggle="navtab"
                   data-id="task-info"
                   data-title="详情任务">查看</a>
            </td>
            <td>
                <c:if test="${v.flow ne 'feasible_check'}">
                    <a href="${ctx}/${appid }/task/info?id=${v.tid}&tab=2" data-toggle="navtab"
                       data-id="task-info"
                       data-title="详情任务">查看</a>
                </c:if>
            </td>
            <td>
                <a href="${ctx}/${appid}/pmsdevice/associateTree?issueId=${v.id}&notLoad=true" class="btn btn-default" data-toggle="dialog" data-width="400" data-height="800" data-id="associate-box" data-on-close="associateCallback">重新关联</a>
                <%--<c:if test="${v.flow ne 'feasible_check'}">--%>
                    <%--<a href="${ctx}/${appid }/task/info?id=${v.tid}&tab=2" data-toggle="navtab"--%>
                       <%--data-id="task-info"--%>
                       <%--data-title="详情任务">查看</a>--%>
                <%--</c:if>--%>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>