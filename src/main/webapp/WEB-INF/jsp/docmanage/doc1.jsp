<%--
  Created by IntelliJ IDEA.
  User: gaozhou
  Date: 2017/2/7
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>



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

<table class="table table-bordered">
    <thead>
    <tr>
        <th>名称</th>
        <th width="60">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${docs}" var="r" varStatus="s">
        <tr>
            <td>${r.name}</td>
            <td class="center">
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
