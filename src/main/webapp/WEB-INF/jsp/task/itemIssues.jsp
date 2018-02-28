<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>


<style type="text/css">
    .table th, .table td {
        border: 1px solid #DDDDDD;
    }

    /*.icon0 {*/
        /*width: 96px;*/
        /*height: 30px;*/
        /*padding-left: 43px;*/
        /*float: right;*/
        /*line-height: 34px;*/
        /*position: relative;*/
        /*cursor: pointer;*/
    /*}*/

    /*.icon1 {*/
        /*position: absolute;*/
        /*left: 37px;*/
        /*width: 30px;*/
        /*height: 20px;*/
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
        /*right: 20px;*/
        /*top: -4px;*/
        /*border-radius: 8px;*/
    /*}*/
    a.issue-download {
        float: right;
        margin: 5px;
    }
</style>
<c:if test="${not empty resultIssues}">
    <a class="btn btn-green issue-download" href="${ctx}/${appid}/task/downloadResultIssue?taskId=${taskId}" target="_blank">导出</a>
</c:if>
<div class="table" style="margin-top: 10px;">
    <table border="1" class="table">
        <tr class="title_tr">
            <th width="5%" align="center">序号</th>
            <th width="50%" align="center">问题描述</th>
            <th width="10%" align="center">问题图片</th>
            <th width="25%" align="center">整改建议</th>
            <th width="10%" align="center">是否已经整改</th>
        </tr>
        <c:forEach var="resultIssue" items="${resultIssues}" varStatus="index">
            <tr>
                <td>${index.count}</td>
                <td>${resultIssue.description}</td>
                <td class="center">
                    <c:choose>
                        <c:when test="${empty resultIssue.imgs}">
                            无
                        </c:when>
                        <c:otherwise>
                            <c:set var="images" value="${resultIssue.imgs.split(',')}"/>
                            <div class="icon0">
                                <c:forEach var="image" items="${images}" varStatus="s">
                                    <a class="fancybox fancybox-buttons" href="${ctx}/upload/${image}" rel="${resultIssue.id}" style="visibility: ${s.count == 1 ? 'visible' : 'hidden'}">
                                        <span class="icon1">
                                        <c:if test="${s.count == 1}">
                                            <img src="${ctx}/upload/${image}"  style="height:100%; width:100%;">
                                        </c:if>
                                        </span>
                                        <span class="icon2">${fn:length(images)}</span>
                                    </a>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${resultIssue.suggest}</td>
                <td>
                    <c:choose>
                        <c:when test="${resultIssue.status eq 'normal'}">
                            否
                        </c:when>
                        <c:otherwise>
                            是
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>


<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    问题描述图片
                </h4>
            </div>
            <div class="modal-body">
                <div id="my_carousel" class="carousel slide">
                    <!-- 放置图片 -->
                    <div class="carousel-inner" id="mycarousel">
                    </div>
                    <a href="#my_carousel" data-slide="prev" id="prev" class="carousel-control left"><span
                            class="glyphicon glyphicon-chevron-left"></span></a> <a
                        href="#my_carousel" data-slide="next" id="next" class="carousel-control right"><span
                        class="glyphicon glyphicon-chevron-right"></span></a>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">关闭
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<script type="text/javascript">
    $(document).ready(function() {
        $('.fancybox').fancybox({
            padding : 0,
            openEffect  : 'elastic'
        });
        $('.fancybox-buttons').fancybox({
            openEffect  : 'none',
            closeEffect : 'none',

            prevEffect : 'none',
            nextEffect : 'none',

            afterLoad : function() {
                this.title = ['第 ', (this.index + 1), '/', this.group.length, ' 张'].join("");
            }
        });
    });
</script>