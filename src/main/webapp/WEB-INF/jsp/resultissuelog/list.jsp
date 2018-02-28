<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>


<style type="text/css">
    .table th, .table td {
        border: 1px solid #DDDDDD;
    }
</style>

<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/${appid }/${modelName}?riid=${riid}&tid=${tid}" method="post">
</form>

<div class="bjui-pageContent tableContent white ess-pageContent">
    <table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
        <thead>
        <tr>
            <th title="序号" width="50">序号</th>
            <c:if test="${isTask}">
                <th>验收大项</th>
                <th>验收项目</th>
            </c:if>
            <%@include file="../common/common_list_thead.jsp" %>
            <c:if test="${not isTask}">
                <th width="50">操作图片</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.list }" var="r" varStatus="s">
            <tr>
                <td>${(page.pageNumber-1) * page.pageSize + s.count}</td>
                <c:if test="${isTask}">
                    <td data-val='${r.citname}'>${r.citname}</td>
                    <td data-val='${r.ciname}'>${r.ciname}</td>
                </c:if>
                <c:forEach items="${fields }" var="f">
                    <c:if test="${f.is_list_view ==1 }">
                        <kval:val model="${r }" field="${f }"></kval:val>
                    </c:if>
                </c:forEach>
                <c:if test="${not isTask}">
                    <td>
                    <c:if test="${not empty r.op_img}">
                        <c:set var="images" value="${r.op_img.split(',')}"/>
                        <div class="icon0">
                            <c:forEach var="image" items="${images}" varStatus="s1">
                                <a class="fancybox fancybox-buttons" href="${ctx}/upload/${image}"
                                   data-fancybox-group="issue_log${r.id}">
                                    <c:if test="${s1.count == 1}">
                                        <span class="icon1" data-imgs="${r.op_img}">
                                            <img src="${ctx}/upload/${image}" style="height:100%; width:100%;">
                                        </span>
                                        <span class="icon2">${fn:length(images)}</span>
                                    </c:if>
                                </a>
                            </c:forEach>
                        </div>
                        </td>
                </c:if>
            </c:if>
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
    <div class="pagination-box" data-toggle="pagination" data-total="${page.totalRow }"
         data-page-size="${page.pageSize }" data-page-current="${page.pageNumber }" data-page-num="15"></div>
</div>


<script type="text/javascript">
    $(function () {
        $('.fancybox').fancybox({
            padding: 0,
            openEffect: 'elastic'
        });
        $('.fancybox-buttons').fancybox({
            openEffect: 'none',
            closeEffect: 'none',

            prevEffect: 'none',
            nextEffect: 'none',

            afterLoad: function () {
                this.title = ['第 ', (this.index + 1), '/', this.group.length, ' 张'].join("");
            }
        });
    });
</script>


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