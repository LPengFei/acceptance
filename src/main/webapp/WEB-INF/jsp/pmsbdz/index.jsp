<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <div class="bjui-searchBar ess-searchBar">
        <button type="button" data-url="${ctx}/${appid }/pmsbdz/syncBdzlist?account=account&pwd=pwd" data-toggle="doajax"
                data-id="${modelName }-edit" style="background: #14CAB4; color: white; float: right;">
            从PMS同步变电站列表
        </button>
    </div></div>
<div class="bjui-pageContent tableContent white ess-pageContent">
    <table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
        <thead>
        <tr>
            <th title="序号" width="50">序号</th>
            <%@include file="../common/common_list_thead.jsp" %>
            <th title="设备台账管理">设备台账管理</th>
            <th title="PMS台账同步">PMS台账同步</th>
            <%--<th width="70">操作</th>--%>
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
                    <a href="${ctx}/${appid }/pmsbdz/list?bdzid=${r.id}" data-toggle="navtab" data-id="device-list" data-title="设备台账">浏览</a>
                </td>
                <td>
                    <%--<a href="${ctx}/${appid }/pmsbdz/syncDevice?account=account&pwd=pwd&pmsid=${r.id}" data-toggle="doajax">从PMS同步至后台</a>--%>
                    <a href="javascript:;">从PMS同步至后台</a>
                    &nbsp;
                    &nbsp;
                    <%--<a href="javascript:void(confirm('此操作会将PMS的台账覆盖，确认要同步到PMS吗？'));" >从后台同步到PMS</a>--%>
                    <a href="javascript:;" >从后台同步到PMS</a>
                </td>
                <%--<td>--%>
                    <%--<a style="color:red;" data-toggle="doajax"  data-confirm-msg="确定要删除该行信息吗？" href="${ctx }/${appid }/${modelName }/delete/${r[pkName]}">删除</a>  &nbsp;--%>
                    <%--<a href="${ctx}/${appid }/${modelName }/edit/${r[pkName]}"  style="color:green;"  data-toggle="navtab" data-id="${modelName }-edit" data-title="编辑${modelTitle }" >修改</a>--%>
                <%--</td>--%>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
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
    <div class="pagination-box" data-toggle="pagination" data-total="${page.totalRow }" data-page-size="${page.pageSize }" data-page-current="${page.pageNumber }" data-page-num="15"></div>
</div>
