<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>

<style type="text/css">
    .table th, .table td {
        border: 1px solid #DDDDDD;
    }
</style>

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


<div class="bjui-pageContent">
    <div class="ess-form">
        <div class="panel-heading">
            <h3 class="panel-title">
                <button class="list" disabled="disabled">
                    <span class="glyphicon glyphicon-th-large"></span>
                </button>
                到货清单
            </h3>
        </div>
        <div class="ess-form">
            <c:forEach var="image" items="${countTask.imgs.split(',')}" varStatus="s">
                <a class="fancybox fancybox-buttons" href="${ctx}/upload/${image}" data-fancybox-group="gallery">
                    <img src="${ctx}/upload/${image}" style="width: 10%">
                </a>
            </c:forEach>
        </div>


        <div class="panel-heading">
            <h3 class="panel-title">
                <button class="list" disabled="disabled">
                    <span class="glyphicon glyphicon-th-large"></span>
                </button>
                到货情况:(本次已到货${staticsMap.categoryCount == null ? 0 : staticsMap.categoryCount}种, 数量：${staticsMap.allCount == null ? 0 : staticsMap.allCount})
            </h3>
        </div>
        <div class="ess-form">
            <div class="table" style="margin-top: 10px;">
                <table border="1" class="table">
                    <tr class="title_tr">
                        <th width="10%" align="center">设备名称</th>
                        <th width="10%" align="center">设备型号</th>
                        <th width="10%" align="center">数量</th>
                        <th width="10%" align="center">到货时间</th>
                        <th width="10%" align="center">存放地点</th>
                        <th width="10%" align="center">清点人员</th>
                    </tr>
                    <c:forEach var="item" items="${categoryMap}" varStatus="s1">
                        <tr height="40px" class="tr_sign">
                            <th colspan="6">${item.key}(数量：${staticsMap[item.key]})</th>
                        </tr>
                        <c:forEach var="countDevice" items="${item.value}" varStatus="s2">
                            <tr>
                                <td>${countDevice.name}</td>
                                <td>${countDevice.deviceno}</td>
                                <td>${countDevice.amount}</td>
                                <td>${countDevice.time}</td>
                                <td>${countDevice.place}</td>
                                <td>${countDevice.counter}</td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </table>
            </div>
        </div>
        <div class="panel-heading">
            <h3 class="panel-title">
                <button class="list" disabled="disabled">
                    <span class="glyphicon glyphicon-th-large"></span>
                </button>
                问题列表
            </h3>
        </div>
        <div class="ess-form">
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
                        <td>
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
        </div>

    </div>
</div>


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