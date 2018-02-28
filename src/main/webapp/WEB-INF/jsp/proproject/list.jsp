<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>
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

<script type="text/javascript" src="${ctx }/public/libraries/underlineTab/underlineTab.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/public/css/underlineTab.css"/>

<style type="text/css">
    header.title-color {
        height: 35px;
        line-height: 35px;

        background-color: #1abc9c;
        margin-top: 15px;
        color: white;
        border: 1px solid #ddd;
        border-bottom: 0px solid transparent;
        padding: 0px;
        border-top-left-radius: 3px;
        border-top-right-radius: 3px;
    }

    .project-name {
        padding-left: 10px;
        height: 100%;
        line-height: inherit;
        display: inline;
    }

    .glyphicon {;
        cursor: pointer
    }

    .underline-nav-cursor {
        color: white
    }

    .project-container {
        /*position: relative;*/
        /*top: -1px;*/
        overflow: hidden;
        width: 100%;
        display: block;
        background: white;
        border: 1px solid #E0E2E5;
        border-top-width: 0px;
    }

    .project-containers {
        padding: 0px 20px 20px 20px;
    }
</style>


<div class="bjui-pageHeader">
    <c:set var="modelTitle" value="${model.mname }"></c:set>

    <form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/${appid }/${modelName}" method="post">
        <input type="hidden" name="pageNumber" value="${query.pageNumber }"/>
        <input type="hidden" name="pageSize" value="${query.pageSize }"/>
        <input type="hidden" name="orderField" value="${query.orderField }"/>
        <input type="hidden" name="orderDirection" value="${query.orderDirection}">

        <div class="bjui-searchBar ess-searchBar">
            <%--动态生成查询条件 start --%>
            <%@include file="../common/common_list_query.jsp" %>
            <%--动态生成查询条件 end --%>
            <button type="button" data-url="${ctx}/${appid }/${modelName}/edit" data-icon="plus" data-toggle="navtab"
                    data-id="${modelName }-edit" data-title="新增${modelTitle }"
                    style="background: #14CAB4; color: white; float: right;">
                新建工程项目
            </button>

            <%--<div class="pull-right">--%>
            <%--<c:if test="${!empty(exports) or !empty(imports) }">--%>
            <%--<div class="btn-group">--%>
            <%--<button type="button" class="btn-default dropdown-toggle" data-toggle="dropdown"--%>
            <%--data-icon="copy">导出-批量操作<span class="caret"></span></button>--%>
            <%--<ul class="dropdown-menu right" role="menu">--%>
            <%--<c:forEach items="${exports }" var="e">--%>
            <%--<li><a href="${ctx}/${appid }/${modelName}/export?xlsid=${e.ieid}"--%>
            <%--data-toggle="doexport" data-confirm-msg="确定要${e.iename }吗？"--%>
            <%--class="blue">${e.iename }-导出</a></li>--%>
            <%--</c:forEach>--%>
            <%--<c:if test="${!empty(imports) }">--%>
            <%--<li class="divider"></li>--%>
            <%--<c:forEach items="${imports }" var="e">--%>
            <%--<li><a href="${ctx}/${appid }/${modelName}/importxls?xlsid=${e.ieid}"--%>
            <%--data-toggle="dialog" data-width="500" data-height="200"--%>
            <%--data-id="dialog-normal" class="green">${e.iename }-导入</a></li>--%>
            <%--</c:forEach>--%>
            <%--</c:if>--%>
            <%--<li><a href="book1.xlsx" data-toggle="doexportchecked" data-confirm-msg="确定要导出选中项吗？" data-idname="expids" data-group="ids">导出<span style="color: red;">选中</span></a></li>--%>
            <%--<li class="divider"></li>--%>
            <%--<li><a href="ajaxDone2.html" data-toggle="doajaxchecked" data-confirm-msg="确定要删除选中项吗？" data-idname="delids" data-group="ids">删除选中</a></li>--%>
            <%--</ul>--%>
            <%--</div>--%>
            <%--</c:if>--%>
            <%--</div>--%>

        </div>
    </form>
</div>

<div class="bjui-pageContent" style="overflow-x: hidden">
    <div class="project-containers">
        <c:forEach items="${projects}" var="record" varStatus="s">
            <div class="project">
                <header class="panel-heading title-color">
                    <h3 class="panel-title project-name">${record.name}</h3>
                        <%--<span class="project-arrow glyphicon glyphicon-th-large"></span>--%>
                    <div class="underline-nav" data-id="${record.id}-${record.name}" data-pid="${record.id}">
                        <ul class="underline-tab">
                            <li><a href="#" data-nav="info"
                                   data-url="${ctx}/${appid}/${modelName}/info?id=${record.id}">项目概况</a></li>
                            <li><a href="#" data-nav="milestone"
                                   data-url="${ctx}/${appid}/promilestone/index?projectId=${record.id}">里程碑</a></li>
                            <li><a href="#" data-nav="gantt"
                                   data-url="${ctx}/${appid}/${modelName}/progress?projectId=${record.id}">施工进度</a></li>
                            <li><a href="#" data-nav="yslc"
                                   data-url="${ctx}/${appid}/proproject/checks?projectId=${record.id}">验收流程</a></li>
                            <li><a href="#" data-nav="gcqx"
                                   data-url="${ctx}/${appid}/proproject/issues?projectId=${record.id}">工程缺陷</a></li>
                            <li><span class="glyphicon underline-nav-cursor glyphicon-chevron-down"></span></li>
                        </ul>
                        <div class="under-line"></div>
                    </div>
                </header>
                <div class="project-container" style="display: none;">
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<%--<div class="bjui-pageHeader">--%>
<%--<c:set var="modelTitle" value="${model.mname }"></c:set>--%>

<%--<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/${appid }/${modelName}" method="post">--%>
<%--<input type="hidden" name="pageNumber" value="${query.pageNumber }"/>--%>
<%--<input type="hidden" name="pageSize" value="${query.pageSize }"/>--%>
<%--<input type="hidden" name="orderField" value="${query.orderField }"/>--%>
<%--<input type="hidden" name="orderDirection" value="${query.orderDirection}">--%>

<%--<div class="bjui-searchBar ess-searchBar">--%>
<%--&lt;%&ndash;动态生成查询条件 start &ndash;%&gt;--%>
<%--<%@include file="../common/common_list_query.jsp" %>--%>
<%--&lt;%&ndash;动态生成查询条件 end &ndash;%&gt;--%>
<%--<button type="button" data-url="${ctx}/${appid }/${modelName}/edit" data-icon="plus" data-toggle="navtab"--%>
<%--data-id="${modelName }-edit" data-title="新增${modelTitle }"--%>
<%--style="background: #14CAB4; color: white; float: right;">新增--%>
<%--</button>--%>

<%--<div class="pull-right">--%>
<%--<c:if test="${!empty(exports) or !empty(imports) }">--%>
<%--<div class="btn-group">--%>
<%--<button type="button" class="btn-default dropdown-toggle" data-toggle="dropdown"--%>
<%--data-icon="copy">导出-批量操作<span class="caret"></span></button>--%>
<%--<ul class="dropdown-menu right" role="menu">--%>
<%--<c:forEach items="${exports }" var="e">--%>
<%--<li><a href="${ctx}/${appid }/${modelName}/export?xlsid=${e.ieid}"--%>
<%--data-toggle="doexport" data-confirm-msg="确定要${e.iename }吗？"--%>
<%--class="blue">${e.iename }-导出</a></li>--%>
<%--</c:forEach>--%>
<%--<c:if test="${!empty(imports) }">--%>
<%--<li class="divider"></li>--%>
<%--<c:forEach items="${imports }" var="e">--%>
<%--<li><a href="${ctx}/${appid }/${modelName}/importxls?xlsid=${e.ieid}"--%>
<%--data-toggle="dialog" data-width="500" data-height="200"--%>
<%--data-id="dialog-normal" class="green">${e.iename }-导入</a></li>--%>
<%--</c:forEach>--%>
<%--</c:if>--%>
<%--<!----%>
<%--<li><a href="book1.xlsx" data-toggle="doexportchecked" data-confirm-msg="确定要导出选中项吗？" data-idname="expids" data-group="ids">导出<span style="color: red;">选中</span></a></li>--%>
<%--<li class="divider"></li>--%>
<%--<li><a href="ajaxDone2.html" data-toggle="doajaxchecked" data-confirm-msg="确定要删除选中项吗？" data-idname="delids" data-group="ids">删除选中</a></li>--%>
<%---->--%>
<%--</ul>--%>
<%--</div>--%>
<%--</c:if>--%>
<%--</div>--%>

<%--</div>--%>
<%--</form>--%>
<%--</div>--%>
<%--<div class="bjui-pageContent tableContent white ess-pageContent">--%>

<%--<table data-toggle="tablefixed" data-width="100%" data-nowrap="true">--%>
<%--<thead>--%>
<%--<tr>--%>
<%--<th title="序号" width="50">序号</th>--%>
<%--<%@include file="../common/common_list_thead.jsp" %>--%>
<%--<th width="170">操作</th>--%>
<%--</tr>--%>
<%--</thead>--%>
<%--<tbody>--%>
<%--<c:forEach items="${page.list }" var="r" varStatus="s">--%>
<%--<tr>--%>
<%--<td>--%>
<%--${(page.pageNumber-1) * page.pageSize + s.count}--%>
<%--</td>--%>
<%--<c:forEach items="${fields }" var="f">--%>
<%--<c:if test="${f.is_list_view ==1 }">--%>
<%--<kval:val model="${r }" field="${f }"></kval:val>--%>
<%--</c:if>--%>
<%--</c:forEach>--%>
<%--<td>--%>
<%--<a href="${ctx}/${appid }/${modelName }/progress?projectId=${r.id}" data-toggle="navtab" data-id="gantt-list">--%>
<%--<button class="light-green-background"><span>施工进度信息</span></button>--%>
<%--</a>--%>
<%--&nbsp;--%>
<%--<a href="${ctx}/${appid }/promilestone/index?projectId=${r[pkName]}"  style="color:green;"  data-toggle="navtab" data-id="promilestone-list" data-title="编辑里程碑" >编辑</a>--%>
<%--&nbsp;--%>
<%--<a href="${ctx}/${appid }/${modelName }/info/${r[pkName]}"  style="color:green;"  data-toggle="navtab" data-id="${modelName }-edit" data-title="编辑${modelTitle }" >编辑</a>--%>
<%--&nbsp;--%>
<%--<a style="color:red;" data-toggle="doajax"  data-confirm-msg="确定要删除该行信息吗？" href="${ctx }/${appid }/${modelName }/delete/${r[pkName]}">删除</a>  &nbsp;--%>
<%--&nbsp;--%>
<%--<a href="${ctx}/${appid }/task/index?projectId=${r.id}" data-toggle="navtab" data-id="task-list">--%>
<%--<button class="light-green-background"><span>验收任务管理</span></button>--%>
<%--</a>--%>
<%--&nbsp;--%>
<%--<a href="${ctx}/${appid }/proprojectattachment/index?projectId=${r.id}" data-toggle="navtab" data-id="proprojectattachment-list" data-title="&lt;${r.name}&gt;附件管理">--%>
<%--<button class="light-green-background"><span>附件管理</span></button>--%>
<%--</a>--%>
<%--&nbsp;--%>
<%--<a href="${ctx}/${appid }/task/itemIssuesRemain?projectId=${r.id}" data-toggle="navtab" data-id="remain-list" data-title="&lt;${r.name}&gt;遗留问题">--%>
<%--<button class="light-green-background"><span>遗留问题</span></button>--%>
<%--</a>--%>
<%--&nbsp;--%>
<%--<a href="${ctx}/${appid }/task/countDevices?projectId=${r.id}" data-toggle="navtab" data-id="count-list" data-title="&lt;${r.name}&gt;到货情况">--%>
<%--<button class="light-green-background"><span>到货情况</span></button>--%>
<%--</a>--%>
<%--&nbsp;--%>
<%--</td>--%>
<%--</tr>--%>
<%--</c:forEach>--%>
<%--</tbody>--%>
<%--</table>--%>
<%--</div>--%>
<%--<div class="bjui-pageFooter">--%>
<%--<div class="pages">--%>
<%--<span>每页&nbsp;</span>--%>
<%--<div class="selectPagesize">--%>
<%--<select data-toggle="selectpicker" data-toggle-change="changepagesize">--%>
<%--<option value="30">30</option>--%>
<%--<option value="60">60</option>--%>
<%--<option value="120">120</option>--%>
<%--<option value="150">150</option>--%>
<%--</select>--%>
<%--</div>--%>
<%--<span>&nbsp;条，共 ${page.totalRow } 条</span>--%>
<%--</div>--%>
<%--<div class="pagination-box" data-toggle="pagination" data-total="${page.totalRow }"--%>
<%--data-page-size="${page.pageSize }" data-page-current="${page.pageNumber }" data-page-num="15"></div>--%>
<%--</div>--%>

<script>
    $(function () {
        var $tab = new $.fn.navtab.Constructor().tools.getTab("main");
        var options = $tab.data("options");
        var projectId = options.projectId;
        delete options.projectId;
        $tab.data("options", options);

        $('.underline-nav').each(function (i, x) {
            var $project = $(x).closest('.project');
            var $target = $project.find('.project-container');
            $(x).underlineTab({
                target: $target,
                onShowPanel: function () {
                    var $cursor = $(this).find('.underline-nav-cursor');
                    if (!$cursor.hasClass('glyphicon-chevron-up')) {
                        $cursor.removeClass('glyphicon-chevron-up glyphicon-chevron-down').addClass('glyphicon-chevron-up');
                        $target.slideDown('fast', function () {
                            var scrollHeight = $project.closest('.bjui-pageContent')[0].scrollHeight;
                            var height = $project.closest('.bjui-pageContent').height();
                            var $projects = $('.project');
                            var totalHeight = $projects.get().reduce(function (acc, v) {
                                return acc += 16 + $(v).height();
                            }, 0);
                            if (totalHeight > height) {
                                var h = $project.prevAll().get().reduce(function (acc, v) {
                                    return acc += 16 + $(v).height();
                                }, 0);
                                $project.closest('.bjui-pageContent').animate({
                                    scrollTop: h / scrollHeight * scrollHeight + 10
                                }, Math.max(300, h / scrollHeight * 500));
                            }
                        });
                    }
                },
                onHidePanel: function () {
                    var $cursor = $(this).find('.underline-nav-cursor');
                    if (!$cursor.hasClass('glyphicon-chevron-down')) {
                        $cursor.removeClass('glyphicon-chevron-up glyphicon-chevron-down').addClass('glyphicon-chevron-down');
                        $target.slideUp();
                    }
                }
            });
        }).each(function (i, x) {
            if (projectId) {
                if ($(x).data('id').indexOf(projectId) > -1) {
                    $(x).showPanel(0);
                    return false;
                }
            } else {
                if (i === 0) {
                    $(x).showPanel(0);
                }
                return false;
            }
        });
    });
</script>