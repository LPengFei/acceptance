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


<style type="text/css">
    .table th, .table td {
        border: 1px solid #DDDDDD;
    }

    /*.icon0 {*/
        /*display: inline-block;*/
        /*width: 60px;*/
        /*height: 30px;*/
        /*padding-left: 10px;*/
        /*float: right;*/
        /*line-height: 34px;*/
        /*position: relative;*/
        /*cursor: pointer;*/
        /*margin-top: 4px;*/
    /*}*/

    /*.icon1 {*/
        /*position: absolute;*/
        /*!*left: 37px;*!*/
        /*width: 30px;*/
        /*height: 20px;*/
        /*top: 5px;*/
    /*}*/

    /*.icon2 {*/
        /*position: absolute;*/
        /*width: 16px;*/
        /*height: 16px;*/
        /*background-color: #C81623;*/
        /*font-size: 12px;*/
        /*line-height: 14px;*/
        /*text-align: center;*/
        /*color: #fff;*/
        /*right: 15px;*/
        /*top: -4px;*/
        /*border-radius: 8px;*/
    /*}*/
</style>


<script type="text/javascript">
    function doc_filedownload1(a) {
        $.fileDownload($(a).attr('href'), {
            failCallback: function (responseHtml, url) {
                if (responseHtml.trim().startsWith('{')) responseHtml = responseHtml.toObj()
                $(a).bjuiajax('ajaxDone', responseHtml)
            }
        })
    }
</script>

<div class="bjui-pageHeader">
    <c:set var="modelTitle" value="${model.mname }"></c:set>

    <form id="pagerForm" data-toggle="ajaxsearch"
          action="${ctx}/${appid }/${modelName}/index?projectId=${projectId}&attachType=${attachType}" method="post">
        <input type="hidden" name="pageNumber" value="${query.pageNumber }"/>
        <input type="hidden" name="pageSize" value="${query.pageSize }"/>
        <input type="hidden" name="orderField" value="${query.orderField }"/>
        <input type="hidden" name="orderDirection" value="${query.orderDirection}">

        <div class="bjui-searchBar ess-searchBar">
            <%--动态生成查询条件 start --%>
            <%@include file="../common/common_list_query.jsp" %>
            <%--动态生成查询条件 end --%>
            <button type="button"
                    data-url="${ctx}/${appid }/${modelName}/edit?projectId=${projectId}&attachType=${attachType}"
                    data-icon="plus" data-toggle="navtab" data-id="${modelName }-edit" data-title="添加${attachType}"
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
<div class="bjui-pageContent tableContent white ess-pageContent">
    <table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
        <thead>
        <tr>
            <%@include file="../common/common_list_thead.jsp" %>
            <th width="60">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.list }" var="r" varStatus="s">
            <tr>
                <c:forEach items="${fields }" var="f">
                    <c:if test="${f.is_list_view ==1 }">
                        <kval:val model="${r }" field="${f }"></kval:val>
                    </c:if>
                </c:forEach>
                <td>
                    <a style="color:red;" data-toggle="doajax" data-confirm-msg="确定要删除该行信息吗？"
                       href="${ctx }/${appid }/${modelName }/delete/${r[pkName]}">删除</a>
                    &nbsp;
                    <c:choose>
                        <c:when test="${r._images eq null}">
                            <a href="${ctx}/${appid }/${modelName }/download?attachmentId=${r[pkName]}" target="_blank"
                               style="color:green;">下载</a>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${fn:length(r._images) > 0}">
                                <c:forEach var="image" items="${r._images}" varStatus="s">
                                    <a class="fancybox fancybox-buttons" href="${ctx}/upload/${image.img_path}"
                                       data-fancybox-group="${r.id}">
                                        <c:if test="${s.count == 1}">
                                            查看
                                        </c:if>
                                    </a>
                                </c:forEach>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </td>
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