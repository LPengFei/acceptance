<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>


<style type="text/css">
    .project-base::before {
        content: "|";
        display: inline-block;
        width: 5px;
        height: 20px;
        background-color: darkorange;
    }

    .project-base {
        width: 100%;
        margin: 5px 0px;
        color: darkorange;
        line-height: 20px;
        font-size: 1.5em;
    }

    .project-details {
        padding: 10px;
    }

    .project-details ul li {
        display: inline-block;
        width: 200px;
        margin-bottom: 0px;
    }

    .project-details ul li label:first-child {
        display: block;
        color: #1ABC9C;
    }

    .project-details hr {
        border: 1px dashed #DDDDDD;
        margin: 10px 5px;
    }

    .project-scale {
        margin: 0px;
        border-left-width: 0px;
        border-right-width: 0px;
        border-bottom-width: 0px;
    }

    .table-bordered.project-scale > thead > tr > th:first-child,
    .table-bordered.project-scale > tbody > tr > th:first-child,
    .table-bordered.project-scale > thead > tr > td:first-child,
    .table-bordered.project-scale > tbody > tr > td:first-child {
        border-left-width: 0px;
    }

    .table-bordered.project-scale > thead > tr > th:last-child,
    .table-bordered.project-scale > tbody > tr > th:last-child,
    .table-bordered.project-scale > thead > tr > td:last-child,
    .table-bordered.project-scale > tbody > tr > td:last-child {
        border-right-width: 0px;
    }

    .project-scale tr:nth-child(odd) {
        background-color: #f3f8f8;
    }

    .project-scale tr:hover {
        background-color: rgba(137, 183, 221, 0.30);
    }

</style>

<div>
    <div class="project-base">
        &nbsp;基本信息
        <div class="jgyys-btn pull-right">
            <a href="${ctx}/${appid }/docmanage?projectId=${record.id}" data-toggle="navtab"
               data-id="docmanage-list" data-title="公共档案"><span>公共档案</span></a>
        </div>
        <div class="jgyys-btn pull-right">
            <a href="${ctx}/${appid }/docmanage/docFile?projectId=${record.id}" target="_blank"><span>生成报告</span></a>
        </div>
        <c:if test="${isadmin}">
            <div class="jgyys-btn pull-right">
                <a href="${ctx}/${appid }/proproject/delete/${record.id}" data-toggle="doajax" data-confirm-msg="确定要删除该工程吗？">
                    <span style="background-color:red;" >删除工程</span>
                </a>
            </div>
            <div class="jgyys-btn pull-right">
                <a href="${ctx}/${appid }/proproject/edit?id=${record.id}" data-toggle="navtab"
                   data-id="docmanage-edit" data-title="修改工程">
                    <span style="background-color: orange;" >修改工程</span>
                </a>
            </div>
        </c:if>
    </div>
    <div class="project-details">
        <%--<ul>--%>
            <%--<li><label>项目名称</label><label>${record.name}</label></li>--%>
        <%--</ul>--%>
        <%--<div class="clearfix"></div>--%>
        <%--<hr>--%>
        <ul>
            <li><label>项目法人</label><label>${record.fr}</label></li>
            <li><label>建设管理单位</label><label>${record.gldw}</label></li>
            <li><label>设计单位</label><label>${record.sjdw}</label></li>
        </ul>
        <div class="clearfix"></div>
        <hr>
        <ul>
            <li><label>监理单位</label><label>${record.jldw}</label></li>
            <li><label>施工单位</label><label>${record.sgdw}</label></li>
            <li><label>运行单位</label><label>${record.yxdw}</label></li>
        </ul>
        <div class="clearfix"></div>
    </div>
    <table class="table table-bordered project-scale">
        <thead>
        <tr>
            <th title="验收项目" width="30%">验收项目</th>
            <th title="终期规模">终期规模</th>
            <th title="终期规模">终期规模</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${scals}" var="scal" varStatus="index">
            <tr>
                <td width="30%">${scal.name}</td>
                <td>${scal.finalscal}</td>
                <td>${scal.initscal}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
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
