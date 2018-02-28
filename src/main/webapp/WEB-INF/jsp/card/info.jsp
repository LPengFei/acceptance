<%--
  Created by IntelliJ IDEA.
  User: gaozhou
  Date: 2016/11/14
  Time: 上午11:36
  To change this template use File | Settings | File Templates.
--%>
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


<script type="text/javascript">
    function card_info_filedownload(a) {
        $.fileDownload($(a).attr('href'), {
            failCallback: function(responseHtml, url) {
                if (responseHtml.trim().startsWith('{')) responseHtml = responseHtml.toObj()
                $(a).bjuiajax('ajaxDone', responseHtml)
            }
        })
    }
</script>


<style type="text/css">
    #card-info {
        /*margin: 30px;*/
        width: 100%;
        /*border-style: solid;*/
        /*border-width: 1px;*/
    }
    /*#card-info > thead > tr > th:nth-of-type(4) {*/
        /*background-color: red;*/
    /*}*/
</style>

<div class="bjui-pageContent">
    <div class="ess-form">
        <div class="panel-heading">
            <h3 class="panel-title">
                <button class="list" disabled="disabled">
                    <span class="glyphicon glyphicon-th-large"></span>
                </button>
                <%--变压器关键点见证标准卡--%>
                ${card.name}
            </h3>
        </div>
        <div class="ess-form">
            <div class="panel-body">
                <table border="1" class="table_head" id="card-info">
                    <thead>
                        <tr >
                            <th rowspan="6">变压器基础信息</th>
                        </tr>
                        <tr>
                            <th><c:if test="${showgcmc == '1' }"><input type="hidden" value="${card.showgcmc}" class="soh">工程名称</c:if></td></th>
                            <th>${baseInfo.projectName}</th>
                            <th><c:if test="${card.showsccj == '1'}">生产厂家</c:if></th>
                            <th colspan="2">自定义生产厂家</th>
                        </tr>
                        <tr>
                            <th><c:if test="${card.showsjdw == '1' }">设计单位</c:if></th>
                            <th>${baseInfo.projectName}</th>
                            <th><c:if test="${card.showysdw == '1'}">验收单位</c:if></th>
                            <th colspan="2">自定义验收单位</th>
                        </tr>
                        <tr>
                            <th><c:if test="${card.showscgh == '1'}">生产工号</c:if></th>
                            <th>自定义生产工号</th>
                            <th><c:if test="${card.showccbh == '1'}">出厂编号</c:if></th>
                            <th colspan="2">自定义出厂编号</th>
                        </tr>
                        <tr>
                            <th><c:if test="${card.showysrq == '1' }">验收日期</c:if></th>
                            <th>自定义验收日期</th>
                            <th><c:if test="${card.showsbxh == '1' }">设备型号</c:if></th>
                            <th colspan="2">自定义设备型号</th>
                        </tr>
                        <tr>
                            <th><c:if test="${card.showysrq == '1' }">变电所名称</c:if></th>
                            <th>自定义变电所名称</th>
                            <th><c:if test="${card.showsbxh == '1' }">设备名称编号</c:if></th>
                            <th colspan="2">自定义设备名称编号</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <th>序号</th>
                            <th>验收项目</th>
                            <th>验收标准</th>
                            <th>检查方式</th>
                            <th>验收结论（是否合格）</th>
                            <th>验收问题说明</th>
                        </tr>
                        <c:forEach var="acceptanceItem" items="${acceptanceItems}">
                            <tr>
                                <th colspan="3" style="border-right: none;">${acceptanceItem.cardItemType.name}</th>
                                <th colspan="3" style="border-left: none;">验收人签字：</th>
                                <c:forEach var="cardInfoItem" items="${acceptanceItem.cardInfoItems}" varStatus="status">
                                    <tr>
                                        <td class="number">${status.count}</td>
                                        <td class="test">${cardInfoItem.cardItem.name}${cardInfoItem.cardItem.id}</td>
                                        <td>
                                            <c:forEach var="cardStandard" items="${cardInfoItem.cardStandards}" varStatus="status">
                                                (${status.count})${cardStandard.description}<br>
                                            </c:forEach>
                                        </td>

                                        <td class="way">${cardInfoItem.cardItem.checktype}</td>

                                        <td>
                                            ${cardInfoItem.resultItem.result == "0" ? "合格" : "不合格"}
                                            <%--<c:forEach var="cardCopy" items="${cardInfoItem.cardCopies }">--%>
                                                <%--${cardCopy.name }11<br>--%>
                                            <%--</c:forEach>--%>
                                        </td>

                                        <td>1</td>
                                    </tr>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="clearfix"></div>
        </div>
        <!-- 操作按钮 -->
        <div class=""
             style="text-align:center; padding-top: 20px;">
            <a class="btn btn-green" href="${ctx}/${appid}/card/download?cardId=${card.id}" onclick="card_info_filedownload(this); return false;">导出</a>
            <button type="button" class="btn-red btn-close">返回</button>
        </div>
    </div>
</div>