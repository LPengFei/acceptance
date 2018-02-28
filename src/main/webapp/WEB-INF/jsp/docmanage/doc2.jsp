<%--
  Created by IntelliJ IDEA.
  User: gaozhou
  Date: 2017/2/7
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
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

<style type="text/css">
    .table th, .table td {
        border: 1px solid #DDDDDD;
    }

    .ess-form > span > a:only-of-type {
        float: right;
    }

    .table th, .table td {
        border: 1px solid #DDDDDD;
    }

    /*.icon0 img {*/
        /*margin-top: 5px;*/
    /*}*/

    /*.icon0 {*/
        /*width: 50px;*/
        /*height: 30px;*/
        /*!*padding-left: 43px;*!*/
        /*float: right;*/
        /*line-height: 34px;*/
        /*position: relative;*/
        /*cursor: pointer;*/
    /*}*/

    /*.icon1 {*/
        /*position: absolute;*/
        /*left: 0px;*/
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
        /*right: -10px;*/
        /*top: -4px;*/
        /*border-radius: 8px;*/
    /*}*/
</style>


<div class="pro_lcbC" style="padding-top: 0px;background-color: #f8f8f8;width: 100%">
    <div class="pro_wrap" style="width: 100%">
        <c:forEach var="r" items="${flows}" varStatus="s">
            <table class="table table-bordered project-milestone">
                <thead>
                <tr class="pro_lcb_title">
                    <th colspan="2">
                        <span cool="open" style="margin: 2px 10px 0px 10px;float: left"></span>
                        <span style="float: left;margin-top: 5px">${r.name}</span>
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${checks.get(r.key)}" var="r" varStatus="s">
                    <tr>
                        <td>${r.name}</td>
                        <td width="100px" class="center">
                            <c:choose>
                                <c:when test="${empty r._images}">

                                </c:when>
                                <c:otherwise>
                                    <div class="icon0">
                                        <c:forEach var="image" items="${r._images}" varStatus="s">
                                            <a class="fancybox fancybox-buttons" href="${ctx}/upload/${image}"
                                               rel="details_${r.id}"
                                               style="visibility: ${s.count == 1 ? 'visible' : 'hidden'}">
                                                    <span class="icon1">
                                                    <c:if test="${s.count == 1}">
                                                        <img src="${ctx}/upload/${image}" style="height:100%; width:100%;">
                                                    </c:if>
                                                    </span>
                                                <span class="icon2">${fn:length(r._images)}</span>
                                            </a>
                                        </c:forEach>
                                    </div>
                                </c:otherwise>
                            </c:choose>
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

<script type="text/javascript">
    $(document).ready(function () {
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