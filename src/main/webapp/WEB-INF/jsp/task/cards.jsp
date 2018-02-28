<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>

<script type="text/javascript">
    function card_info_filedownload(a) {
        $.fileDownload($(a).attr('href'), {
            failCallback: function (responseHtml, url) {
                if (responseHtml.trim().startsWith('{')) responseHtml = responseHtml.toObj()
                $(a).bjuiajax('ajaxDone', responseHtml)
            }
        })
    }
</script>

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
        /*margin-top:5px;*/
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

<div class="ess-form" style="border: none;">
    <c:forEach var="taskCard" items="${task._taskCards }">
        <span style="font-size: large; display: block; margin-bottom: 10px;">
            ${taskCard._card.name}
            <c:choose>
                <c:when test="${task.status eq 'add'}">
                    (新增)
                </c:when>
                <c:when test="${task.status eq 'doing'}">
                    (进行中)
                </c:when>
                <c:otherwise>
                    (完成)
                </c:otherwise>
            </c:choose>
            <a class="btn btn-green"
               href="${ctx}/${appid}/task/downloadTaskDoc?taskId=${taskCard.tid}&taskCardId=${taskCard.id}"
               target="_blank">导出</a>
        </span>
        <div class="ess-form">
            <ul>
                <c:if test="${taskCard._card.showgcmc eq 1}">
                    <li>
                        <label>工程名称</label>
                        <div>
                            <label>${taskCard.gcmc}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showsjdw eq 1}">
                    <li>
                        <label>设计单位</label>
                        <div>
                            <label>${taskCard.sjdw}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showysdw eq 1}">
                    <li>
                        <label>验收单位</label>
                        <div>
                            <label>${taskCard.ysdw}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showysrq eq 1}">
                    <li>
                        <label>验收日期</label>
                        <div>
                            <label>${taskCard.ysrq}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showsccj eq 1}">
                    <li>
                        <label>生产厂家</label>
                        <div>
                            <label>${taskCard.sccj}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showsbxh eq 1}">
                    <li>
                        <label>设备型号</label>
                        <div>
                            <label>${taskCard.sbxh}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showscgh eq 1}">
                    <li>
                        <label>生产工号</label>
                        <div>
                            <label>${taskCard.sjdw}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showccbh eq 1}">
                    <li>
                        <label>出厂编号</label>
                        <div>
                            <label>${taskCard.ccbh}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showbdsmc eq 1}">
                    <li>
                        <label>变电所名称</label>
                        <div>
                            <label>${taskCard.bdsmc}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showsbmcbh eq 1}">
                    <li>
                        <label>设备名称编号</label>
                        <div>
                            <label>${taskCard.sbmcbh}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showjldw eq 1}">
                    <li>
                        <label>监理单位</label>
                        <div>
                            <label>${taskCard.jldw}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showsgdw eq 1}">
                    <li>
                        <label>施工单位</label>
                        <div>
                            <label>${taskCard.sgdw}</label>
                        </div>
                    </li>
                </c:if>
            </ul>
            <div class="clearfix"></div>
        </div>
        <div class="table" style="margin-top: 10px;">
            <table border="1" class="table">
                <tr class="title_tr">
                    <th width="5%" align="center">序号</th>
                    <th width="20%" align="center">验收项目</th>
                    <th width="20%" align="center">数据抄录</th>
                    <th width="5%" align="center">资料图片</th>
                    <th width="5%" align="center">验收结论</th>
                    <th width="10%" align="center">验收问题</th>
                </tr>
                <c:forEach var="cardItemType" items="${taskCard._cardItemTypes}">
                    <tr height="40px" class="tr_sign">
                        <th colspan="6" style="position: relative;">
                                ${cardItemType.name}
                            <img src="${ctx}/upload/${cardItemType._resultSignname.signimg}"
                                 style="height:40px; width:1px;visibility: hidden;">
                            <div style="float: right;">
                                负责人签字 :
                                <img src="" style="height: 40px;width: 1px;visibility: hidden;">
                                <c:forEach var="sign" items="${cardItemType._resultSignnames}" varStatus="s">
                                    <c:if test="${sign._exists eq true}">
                                        <img src="${ctx}/upload/${sign.signimg}" style="height:40px; width:100px;">
                                    </c:if>
                                </c:forEach>
                            </div>
                        </th>
                    </tr>
                    <c:forEach var="cardItem" items="${cardItemType._cardItems}" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>
                                <label>
                                    <a href="${ctx }/${appid }/task/cardStandard?ciid=${cardItem.id}"
                                       data-toggle="dialog" data-width="400" data-height="500" data-id="dialog-mask"
                                       data-mask="true" data-title="验收标准-<${cardItem.name}>">
                                            ${cardItem.name }
                                    </a>
                                </label>
                            </td>
                            <td>
                                <c:forEach var="resultItemCopy" items="${cardItem._resultItemCopys}">
                                    ${resultItemCopy.name }:&nbsp;${resultItemCopy.value}${resultItemCopy.unit}
                                    <br/>
                                </c:forEach>
                            </td>
                            <td>
                                <div class="center">
                                    <c:choose>
                                        <c:when test="${empty cardItem._resultItem.images}">

                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="images" value="${cardItem._resultItem.images.split(',')}"/>
                                            <div class="icon0">
                                                <c:forEach var="image" items="${images}" varStatus="s">
                                                    <a class="fancybox fancybox-buttons" href="${ctx}/upload/${image}"
                                                       rel="${cardItem._resultItem.id}"
                                                       style="visibility: ${s.count == 1 ? 'visible' : 'hidden'}">
                                                        <span class="icon1">
                                                        <c:if test="${s.count == 1}">
                                                            <img src="${ctx}/upload/${image}" style="height:100%; width:100%;">
                                                        </c:if>
                                                        </span>
                                                        <span class="icon2">${fn:length(images)}</span>
                                                    </a>
                                                </c:forEach>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </td>
                            <td>
                                <c:if test="${cardItem._resultItem.result eq 1 }">
                                    <label style="color:red">不合格</label>
                                </c:if>
                                <c:if test="${cardItem._resultItem.result eq 0 }">
                                    合格
                                </c:if>
                            </td>
                            <td>
                                    <%--<c:if test="${cardItem._resultIssueCount eq 0}">--%>
                                    <%--没有问题--%>
                                    <%--</c:if>--%>
                                <c:if test="${cardItem._resultIssueCount > 0}">
                                    <label>
                                        <a style="color:red;"
                                           href="${ctx }/${appid }/task/singleitemIssues?tcid=${taskCard.id}&ciid=${cardItem.id}"
                                           data-toggle="dialog" data-width="800" data-height="500" data-id="dialog-mask"
                                           data-mask="true">
                                            存在${cardItem._resultIssueCount}个问题
                                        </a>
                                    </label>
                                </c:if>

                            </td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </table>
        </div>
    </c:forEach>
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