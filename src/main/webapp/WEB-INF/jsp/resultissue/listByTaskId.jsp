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



<div class="bjui-pageHeader">
    <c:set var="modelTitle" value="${model.mname }"></c:set>

    <form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/${appid }/${modelName}/viewByTaskId" method="post">
        <input type="hidden" name="pageNumber" value="${query.pageNumber }"/>
        <input type="hidden" name="pageSize" value="${query.pageSize }"/>
        <input type="hidden" name="orderField" value="${query.orderField }"/>
        <input type="hidden" name="orderDirection" value="${query.orderDirection}">
        <input type="hidden" name="taskId" value="${taskId}">

        <div class="bjui-searchBar ess-searchBar">
            <button type="button" data-url="${ctx}/${appid }/${modelName}/edit" data-icon="plus" data-toggle="navtab"
                    data-id="${modelName }-edit" data-title="新增${modelTitle }"
                    style="background: #14CAB4; color: white; float: right;">新增
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
                            <%--<!----%>
                            <%--<li><a href="book1.xlsx" data-toggle="doexportchecked" data-confirm-msg="确定要导出选中项吗？" data-idname="expids" data-group="ids">导出<span style="color: red;">选中</span></a></li>--%>
                            <%--<li class="divider"></li>--%>
                            <%--<li><a href="ajaxDone2.html" data-toggle="doajaxchecked" data-confirm-msg="确定要删除选中项吗？" data-idname="delids" data-group="ids">删除选中</a></li>--%>
                            <%---->--%>
                        <%--</ul>--%>
                    <%--</div>--%>
                <%--</c:if>--%>
            <%--</div>--%>
        </div>
    </form>
</div>


<table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
    <thead>
    <tr>
        <th title="序号" width="50">序号</th>
        <%@include file="../common/common_list_thead.jsp" %>
        <th width="170">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list }" var="r" varStatus="s">
        <tr>
            <td>
                    ${(page.pageNumber-1) * page.pageSize + s.count}
            </td>
            <c:forEach items="${fields }" var="f">
                <c:if test="${f.is_list_view ==1 }">
                    <kval:val model="${r }" field="${f }"></kval:val>
                </c:if>
            </c:forEach>
            <td>
                <a href="${ctx}/${appid }/${modelName }/edit/${r[pkName]}" style="color:green;" data-toggle="navtab"
                   data-id="${modelName }-edit" data-title="编辑${modelTitle }">编辑</a>
                &nbsp;
                <a style="color:red;" data-toggle="doajax" data-confirm-msg="确定要删除该行信息吗？"
                   href="${ctx }/${appid }/${modelName }/delete/${r[pkName]}">删除</a>
                &nbsp;
                <a href="${ctx}/${appid }/task/projectTask/${r.id}" data-toggle="navtab" data-id="task-list">
                    <button class="light-green-background"><span>验收任务管理</span></button>
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>


<div class="bjui-pageFooter">
    <div class="pages">
        <span>每页&nbsp;</span>
        <div class="selectPagesize">
            <select data-toggle="selectpicker" data-toggle-change="changepagesize">
                <option value="30">30</option>
                <option value="60">60</option>
                <option value="120">120</option>
                <option value="150">150</option>
            </select>
        </div>
        <span>&nbsp;条，共 ${page.totalRow } 条</span>
    </div>
    <div class="pagination-box" data-toggle="pagination" data-total="${page.totalRow }"
         data-page-size="${page.pageSize }" data-page-current="${page.pageNumber }" data-page-num="15"></div>
</div>






<%--<a href="${ctx}/${appid }/task" data-toggle="dialog" data-id="mypaging" data-title="我的分页演示" data-width="620" data-height="400">打开分页演示</a>--%>



<%--<div class="bjui-pageContent">--%>
    <%--<form action="ajaxDone1.html" id="j_form_form" class="pageForm" data-toggle="validate">--%>
        <%--<div style="margin:15px auto 0; width:800px;">--%>
            <%--<fieldset>--%>
                <%--<legend>选项卡</legend>--%>
                <%--<!-- Tabs -->--%>
                <%--<ul class="nav nav-tabs" role="tablist">--%>
                    <%--<li class="active"><a href="#home" role="tab" data-toggle="tab">Home</a></li>--%>
                    <%--<li><a href="${ctx}/${appid }/task" role="tab" data-toggle="ajaxtab" data-target="#profile" data-reload="false">ajax加载</a></li>--%>
                    <%--<li><a href="#messages" role="tab" data-toggle="tab">固定表头表格</a></li>--%>
                    <%--<li><a href="#settings" role="tab" data-toggle="tab">Settings</a></li>--%>
                <%--</ul>--%>
                <%--<!-- Tab panes -->--%>
                <%--<div class="tab-content">--%>
                    <%--<div class="tab-pane fade active in" id="home"><p>选项卡的a标签上添加[data-toggle="ajaxtab"]属性可以实现ajax加载内容。</p><p>[data-reload]属性可以定义点击该选项卡时是否每次都需要重新加载。</p></div>--%>
                    <%--<div class="tab-pane fade" id="profile"></div>--%>
                    <%--<div class="tab-pane fade" id="messages">--%>
                        <%--<table class="table table-bordered table-hover table-striped table-condensed" data-toggle="tablefixed" data-height="150">--%>
                            <%--<thead>--%>
                            <%--<tr>--%>
                                <%--<th rowspan="2" data-order-field="operation">ID</th>--%>
                                <%--<th rowspan="2" data-order-field="name">姓名</th>--%>
                                <%--<th rowspan="2" data-order-field="sex">性别</th>--%>
                                <%--<th colspan="2" align="center">出生信息</th>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<th data-order-field="birthday">出生日期</th>--%>
                                <%--<th data-order-field="birthplace">出生地</th>--%>
                            <%--</tr>--%>
                            <%--</thead>--%>
                            <%--<tbody>--%>
                            <%--<tr>--%>
                                <%--<td>1</td>--%>
                                <%--<td>一一</td>--%>
                                <%--<td>♂</td>--%>
                                <%--<td>2000-01-01</td>--%>
                                <%--<td>CN</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<td>2</td>--%>
                                <%--<td>二二</td>--%>
                                <%--<td>♀</td>--%>
                                <%--<td>1999-01-01</td>--%>
                                <%--<td>JP</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<td>3</td>--%>
                                <%--<td>三三</td>--%>
                                <%--<td>♂</td>--%>
                                <%--<td>2000-01-01</td>--%>
                                <%--<td>CN</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<td>4</td>--%>
                                <%--<td>四四</td>--%>
                                <%--<td>♀</td>--%>
                                <%--<td>2005-01-01</td>--%>
                                <%--<td>US</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<td>5</td>--%>
                                <%--<td>五五</td>--%>
                                <%--<td>♀</td>--%>
                                <%--<td>2000-01-01</td>--%>
                                <%--<td>KR</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<td>6</td>--%>
                                <%--<td>六六</td>--%>
                                <%--<td>♂</td>--%>
                                <%--<td>2000-01-01</td>--%>
                                <%--<td>CN</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<td>7</td>--%>
                                <%--<td>七七</td>--%>
                                <%--<td>♀</td>--%>
                                <%--<td>1999-01-01</td>--%>
                                <%--<td>JP</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<td>8</td>--%>
                                <%--<td>八八</td>--%>
                                <%--<td>♂</td>--%>
                                <%--<td>2000-01-01</td>--%>
                                <%--<td>CN</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<td>9</td>--%>
                                <%--<td>九九</td>--%>
                                <%--<td>♀</td>--%>
                                <%--<td>2005-01-01</td>--%>
                                <%--<td>US</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<td>10</td>--%>
                                <%--<td>十十</td>--%>
                                <%--<td>♀</td>--%>
                                <%--<td>2000-01-01</td>--%>
                                <%--<td>KR</td>--%>
                            <%--</tr>--%>
                            <%--</tbody>--%>
                        <%--</table>--%>
                    <%--</div>--%>
                    <%--<div class="tab-pane fade" id="settings">No4. Settings</div>--%>
                <%--</div>--%>
            <%--</fieldset>--%>
        <%--</div>--%>
    <%--</form>--%>
<%--</div>--%>
<%--<div class="bjui-pageFooter">--%>
    <%--<ul>--%>
        <%--<li><button type="button" class="btn-close" data-icon="close">关闭</button></li>--%>
    <%--</ul>--%>
<%--</div>--%>