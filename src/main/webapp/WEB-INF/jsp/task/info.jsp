<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>

<style type="text/css">
    #taskDetailTab > ul {
        border-bottom: 1px solid #ddd;
    }

    #taskDetailTab > ul > li {
        width: auto;
        float: left;
        margin-bottom: -1px;
        position: relative;
        display: block;
    }

    #taskDetailTab > ul > li > a {
        width: 220px;
        margin-right: 2px;
        line-height: 1.42857143;
        background-color: transparent;
        position: relative;
        display: block;
        padding: 10px 15px;
    }

    #taskDetailTab > ul > li > a:hover {
        border-color: #eee #eee #ddd;
        background-color: #eee;
    }

    #taskDetailTab > ul > li.active > a, #taskDetailTab > ul > li.active > a:hover {
        border-color: #c3ced5;
        border-bottom-color: white;
        background-color: transparent;
    }

    #taskDetailTab > ul > li > a > .menu_div {
        width: 100%;
        height: 35px;
        border-radius: 5px;
        line-height: 35px;
        font-size: 16px;
        text-align: center;
        background: #AAD3CB;
        color: #FFFFFF;
        font-weight: bold;
    }

    #taskDetailTab > ul > li.active > a > .menu_div {
        background: #FFFFFF;
        color: #1ABC9C;
    }
</style>

<div class="bjui-pageContent">
    <div style="padding: 20px;background: white;border: 1px solid #E0E2E5;">
        <div class="panel-heading">
            <h3 class="panel-title">
                <button class="list" disabled="disabled">
                    <span class="glyphicon glyphicon-th-large"></span>
                </button>
                任务信息
            </h3>
        </div>
        <div class="ess-form">
            <%--<div style="float:left; width:200px;">--%>
                <%--<ul id="layout-tree" class="ztree" data-toggle="ztree" data-expand-all="true" data-on-click="do_open_layout">--%>
                    <%--<li data-id="1" data-pid="0">第一业务</li>--%>
                    <%--<li data-id="10" data-pid="1" data-url="layout/layout-01.html" data-divid="#layout-01">业务1</li>--%>
                    <%--<li data-id="11" data-pid="1" data-url="layout/layout-02.html" data-divid="#layout-02">业务2</li>--%>
                    <%--<li data-id="2" data-pid="0">第二业务</li>--%>
                    <%--<li data-id="20" data-pid="2" data-url="layout/layout-03.html" data-divid="#layout-01">业务3</li>--%>
                    <%--<li data-id="21" data-pid="2" data-url="${ctx}/${appid}/resultissuelog?tid=${task.id}" data-divid="#layout-02">业务4</li>--%>

                <%--</ul>--%>
            <%--</div>--%>

            <ul>
                <li>
                    <label>任务名称</label>
                    <div>
                        <label>${task.name}</label>
                    </div>
                </li>
                <li>
                    <label>设备型号</label>
                    <div>
                        <label>${task.deviceno}</label>
                    </div>
                </li>
                <li>
                    <c:if test="${task.status ne 'add'}">
                        <label>问题记录</label>
                        <div>
                            <label>发现${task._totalResultIssueCount}个问题</label>
                        </div>
                    </c:if>
                </li>
            </ul>
            <a class="btn btn-green"
               href="${ctx}/${appid }/task/edit/${task.id}?projectId=${task.pid}" class="green-color" data-toggle="navtab"
               data-id="task-edit" data-title="修改任务信息">修改</a>
            <div class="clearfix"></div>
        </div>

        <div style="margin-top: 20px;" id="taskDetailTab">
            <ul class="nav nav-tabs" id="taskDetailTabUL">
                <li>
                    <a data-toggle="tab" data-target="#taskDetail"
                       href="${ctx}/${appid}/task/cards?id=${task.id}">
                        <div class="menu_div">验收标准卡</div>
                    </a>
                </li>
                <li>
                    <a data-toggle="tab" data-target="#taskDetail"
                       href="${ctx}/${appid}/task/supervise?id=${task.id}">
                        <div class="menu_div">全过程技术监督</div>
                    </a>
                </li>
                <li>
                    <c:if test="${task.flow ne 'start_check'}">
                        <a data-toggle="tab" data-target="#taskDetail"
                           href="${ctx}/${appid}/task/itemIssues?tid=${task.id}">
                            <div class="menu_div">
                                问题记录(${task._totalResultIssueCount gt 0 ? task._totalResultIssueCount : "无"})
                            </div>
                        </a>
                    </c:if>
                </li>
                <c:if test="${task.flow ne 'feasible_check'}">
                    <li>
                        <a data-toggle="tab" data-target="#taskDetail"
                           href="${ctx}/${appid}/task/itemIssuesImportant?tid=${task.id}">
                            <div class="menu_div">
                                重大问题反馈联系单(${resultIssueImportantCount > 0 ? resultIssueImportantCount : "无"})
                            </div>
                        </a>
                    </li>
                </c:if>
                <li>
                    <a data-toggle="tab" data-target="#taskDetail" data-adjust="true"
                       href="${ctx}/${appid}/resultissuelog?tid=${task.id}">
                        <div class="menu_div">验收操作记录</div>
                    </a>
                </li>
            </ul>
            <div class="tab-content">
                <div id="taskDetail"></div>
            </div>
        </div>

        <!-- 操作按钮 -->
        <div style="text-align:center; padding-top: 20px;">
            <button type="button" class="btn-close" style="background: red; color: white;">关闭</button>
        </div>
    </div>
</div>

<script type="text/javascript">
    $("#taskDetailTabUL li a").each(function () {
        var adjust = $(this).data('adjust');
        $(this).click(function () {
            if(adjust) {
                $('#taskDetail').height("100%").css("overflow", "hidden");
            } else {
                $('#taskDetail').height("auto").css("overflow", "auto");
            }
            $(this).bjuiajax("doLoad", {
                target: $(this).attr("data-target"),
                url: $(this).attr("href"),
            })
        })
    });
    $("#taskDetailTabUL li a")[${tab == null ? 0 : tab}].click();
</script>
