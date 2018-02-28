<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>

<style>
    .project-milestone:first-child {
        margin-top: 0px
    }

    .project-milestone td {
        padding-left: 15px;
    }

    .pro_lcbC .pro_wrap {
        padding-top: 10px;
    }
</style>


<div class="pro_lcbC" style="padding-top: 0px;background-color: #f8f8f8;width: 100%">
    <div class="pro_wrap" style="width: 100%">
        <c:forEach items="${checkStages}" var="check" varStatus="s">
            <table class="table table-bordered project-milestone">
                <thead>
                <tr class="pro_lcb_title">
                    <th width="30" align="left" colspan="3">
                            <%--class="glyphicon glyphicon-minus-sign cursorP"--%>
                        <span cool="open" style="margin: 2px 10px 0px 10px;float: left"></span>
                        <span style="float: left;margin-top: 5px">${check.name}</span>
                    </th>
                    <th width="70" align="right">
                        <%--<a class="data_load">数据导入</a>--%>
                        <a class="pro_add" href="${ctx}/${appid }/${modelName}/edit?projectId=${projectId}&flow=${check.key}"
                           data-toggle="navtab" data-id="${modelName}-edit">添加</a>
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${check.milestones}" var="milestone" varStatus="s1">
                    <tr>
                        <td width="12%">
                            ${milestone.value[0].name}
                        </td>
                        <td width="12%">
                            计划开始时间：<fmt:formatDate value="${milestone.value[0].plantime}" pattern="yyyy-MM-dd"/>
                        </td>
                        <td width="12%">
                            计划结束时间：<fmt:formatDate value="${milestone.value[0].finishTime}" pattern="yyyy-MM-dd"/>
                        </td>
                        <td>
                            实际完成时间：
                            <c:forEach items="${milestone.value[0]._tasks}" var="task" varStatus="s2">
                                <a href="${ctx}/${appid }/task/info?id=${task.id}" data-toggle="navtab"
                                   data-id="task-info"
                                   data-title="详情任务">
                                        ${task.time}
                                </a>
                                <c:if test="${s2.count != fn:length(milestone.value[0]._tasks)}">,</c:if>
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:forEach>
    </div>
</div>


<c:choose>
    <c:when test="${not empty jsFile}">
        <script src="${ctx }${jsFile}"></script>
    </c:when>
    <c:otherwise>
        <script type="text/javascript">
            initForm();
        </script>
    </c:otherwise>
</c:choose>