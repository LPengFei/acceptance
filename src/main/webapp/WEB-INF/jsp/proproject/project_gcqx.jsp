<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>

<style>
    .pro_yslc_title th, .pro_yslc_title .fixedtableCol {
        color: #5f6d6a;
        background-color: #dbf1ed !important;
    }

    .pro_yslcC td {
        padding-left: 15px;
    }

    .jgyys-btn span {
        display: inline-block;
        padding: 8px 15px;
        color: white;
        margin-left: 30px;
        border-radius: 3px;
        cursor: pointer
    }

    .jgyys-btn span:nth-child(1) {
        background-color: #71d398;
        margin-left: 10px
    }

    .jgyys-btn span:nth-child(2) {
        background-color: #ffcb4f
    }

    .jgyys-btn span:nth-child(3) {
        background-color: #f87773
    }

    .yslcTab {
        background-color: #f8f8f8;
        float: left;
        width: 87%
    }

</style>

<style type="text/css">
    .pro_yslc_title th, .pro_yslc_title .fixedtableCol {
        color: #5f6d6a;
        background-color: #dbf1ed !important;
    }

    .pro_gcqx img {
        width: 20px;
        cursor: pointer;
        position: absolute;
        right: 50%;
        top: 20%;
        margin-right: -10px;
    }

    .jgyys-btn span {
        display: inline-block;
        padding: 8PX 15PX;
        color: white;
        margin-left: 30px;
        border-radius: 3px;
        cursor: pointer;
    }

    .jgyys-btn span:nth-child(1) {
        background-color: #71d398;
        margin-left: 10px
    }

    .jgyys-btn span:nth-child(2) {
        background-color: #ffcb4f
    }

    .jgyys-btn span:nth-child(3) {
        background-color: #f87773
    }

    .yslcTab {
        background-color: #f8f8f8;
        float: left;
        width: 87%
    }

    .pro_gcqx td {
        text-align: center;
        position: relative
    }

    .gcqx_no {
        position: absolute;
        width: 15px;
        height: 15px;
        border-radius: 50%;
        background-color: #e7604a;
        right: 50%;
        top: 10%;
        line-height: 15px;
        color: white;
        margin-right: -19px;
        display: inline-block;
        text-align: center
    }

    .td_center {
        text-align: center;
    }

    .td_left {
        text-align: left
    }

    .table > tbody > tr > td.td_center {
        line-height: 30px;
    }

    a {
        cursor: pointer
    }
</style>


<style type="text/css">
    .table th, .table td {
        border: 1px solid #DDDDDD;
    }

    /*.icon0 {*/
        /*width: 50px;*/
        /*height: 30px;*/
        /*padding-left: 10px;*/
        /*float: right;*/
        /*line-height: 34px;*/
        /*position: relative;*/
        /*cursor: pointer;*/
    /*}*/

    /*.icon1 {*/
        /*position: absolute;*/
        /*left: 10px;*/
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
        /*right: 5px;*/
        /*top: -4px;*/
        /*border-radius: 8px;*/
    /*}*/
</style>

<c:choose>
    <c:when test="${devicename ne null}">
        <style type="text/css">
            .yslcTab {
                background-color: #f8f8f8;
                float: left;
                width: 100%
            }
        </style>
        <div class="middle_check yslcTab">
            <div class="pro_wrap" style="padding-top: 10px">
                <table class="table table-bordered project-scale">
                    <thead>
                    <%@include file="issuesHeader.jsp" %>
                    </thead>
                    <tbody class="yslcTable">
                    <c:set var="issueItems" value="${issues}"/>
                    <c:set var="flowKey" value="all"/>
                    <%@include file="issuesRow.jsp" %>
                    </tbody>
                </table>
            </div>
        </div>
    </c:when>
    <c:when test="${flow ne null}">
        <style type="text/css">
            .yslcTab {
                background-color: #f8f8f8;
                float: left;
                width: 100%
            }
        </style>
        <div class="middle_check yslcTab">
            <div class="pro_wrap" style="padding-top: 10px">
                <table class="table table-bordered project-scale">
                    <thead>
                    <%@include file="issuesHeader.jsp" %>
                    </thead>
                    <tbody class="yslcTable">
                    <c:set var="issueItems" value="${issuesMap.get(flow)}"/>
                    <c:set var="flowKey" value="${flow}"/>
                    <%@include file="issuesRow.jsp" %>
                    </tbody>
                </table>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="pro_gcqx" data-projectId="${projectId}"
             style="padding-top: 0px;position: relative;background-color: #f8f8f8;">

            <div class="pro_yslc_lefttitle">
                <ul>
                    <li tabID="all" class="pro_yslc_lefttitle-active">全部缺陷</li>
                    <li tabID="feasible_check">可研初设审查</li>
                    <li tabID="factory_check">厂内验收</li>
                    <li tabID="arrive_check">到货验收</li>
                    <li tabID="hidden_check">隐蔽工程验收</li>
                    <li tabID="middle_check">中间验收</li>
                    <li tabID="complete_check">竣工预验收</li>
                    <li tabID="start_check">启动验收</li>
                </ul>
            </div>


                <%--全部缺陷--%>
            <div class="all yslcTab">
                <div class="pro_wrap" style="padding-top: 10px">
                    <div class="jgyys-btn pull-right">
                        <a href="${ctx}/${appid }/task/itemIssuesRemain?projectId=${projectId}" data-toggle="navtab"
                           data-id="remain-list" data-title="遗留问题"><span>遗留问题</span></a>
                    </div>
                    <table class="table table-bordered project-scale">
                        <thead>
                        <%@include file="issuesHeader.jsp" %>
                        </thead>
                        <tbody class="yslcTable">
                        <c:set var="issueItems" value="${issues}"/>
                        <c:set var="flowKey" value="all"/>
                        <%@include file="issuesRow.jsp" %>
                        </tbody>
                    </table>
                </div>
            </div>

                <%--可研初设审查--%>
            <div class="feasible_check yslcTab">
                <div class="pro_wrap" style="padding-top: 10px">
                    <table class="table table-bordered project-scale">
                        <thead>
                        <%@include file="issuesHeader.jsp" %>
                        </thead>
                        <tbody class="yslcTable">
                        <c:set var="issueItems" value="${feasible_check}"/>
                        <c:set var="flowKey" value="feasible_check"/>
                        <%@include file="issuesRow.jsp" %>
                        </tbody>
                    </table>
                </div>
            </div>


                <%--厂内验收--%>
            <div class="factory_check yslcTab">
                <div class="pro_wrap" style="padding-top: 10px">
                    <table class="table table-bordered project-scale">
                        <thead>
                        <%@include file="issuesHeader.jsp" %>
                        </thead>
                        <tbody class="yslcTable">
                        <c:set var="issueItems" value="${factory_check}"/>
                        <c:set var="flowKey" value="factory_check"/>
                        <%@include file="issuesRow.jsp" %>
                        </tbody>
                    </table>
                </div>
            </div>


                <%--到货验收--%>
            <div class="arrive_check yslcTab">
                <div class="pro_wrap" style="padding-top: 10px">
                    <table class="table table-bordered project-scale">
                        <thead>
                        <%@include file="issuesHeader.jsp" %>
                        </thead>
                        <tbody class="yslcTable">
                        <c:set var="issueItems" value="${arrive_check}"/>
                        <c:set var="flowKey" value="arrive_check"/>
                        <%@include file="issuesRow.jsp" %>
                        </tbody>
                    </table>
                </div>
            </div>

                <%--隐蔽工程验收--%>
            <div class="hidden_check yslcTab">
                <div class="pro_wrap" style="padding-top: 10px">
                    <table class="table table-bordered project-scale">
                        <thead>
                        <%@include file="issuesHeader.jsp" %>
                        </thead>
                        <tbody class="yslcTable">
                        <c:set var="issueItems" value="${hidden_check}"/>
                        <c:set var="flowKey" value="hidden_check"/>
                        <%@include file="issuesRow.jsp" %>
                        </tbody>
                    </table>
                </div>
            </div>


                <%--中间验收--%>
            <div class="middle_check yslcTab">
                <div class="pro_wrap" style="padding-top: 10px">
                    <table class="table table-bordered project-scale">
                        <thead>
                        <%@include file="issuesHeader.jsp" %>
                        </thead>
                        <tbody class="yslcTable">
                        <c:set var="issueItems" value="${middle_check}"/>
                        <c:set var="flowKey" value="middle_check"/>
                        <%@include file="issuesRow.jsp" %>
                        </tbody>
                    </table>
                </div>
            </div>


                <%--竣工预验收--%>
            <div class="complete_check yslcTab">
                <div class="pro_wrap" style="padding-top: 10px">
                    <table class="table table-bordered project-scale">
                        <thead>
                        <%@include file="issuesHeader.jsp" %>
                        </thead>
                        <tbody class="yslcTable">
                        <c:set var="issueItems" value="${complete_check}"/>
                        <c:set var="flowKey" value="complete_check"/>
                        <%@include file="issuesRow.jsp" %>
                        </tbody>
                    </table>
                </div>
            </div>


                <%--启动验收--%>
            <div class="start_check yslcTab">
                <div class="pro_wrap" style="padding-top: 10px">
                    <table class="table table-bordered project-scale">
                        <thead>
                        <%@include file="issuesHeader.jsp" %>
                        </thead>
                        <tbody class="yslcTable">
                        <c:set var="issueItems" value="${start_check}"/>
                        <c:set var="flowKey" value="start_check"/>
                        <%@include file="issuesRow.jsp" %>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>


        <script>
            $(function () {
                $('[data-projectID="${projectId}"].pro_gcqx')
                    .find('.yslcTab')
                    .hide()
                    .end()
                    .find('.pro_yslc_lefttitle li')
                    .click(function () {
                        $(this).parents('.pro_gcqx')
                            .find('.yslcTab.' + $(this).attr('tabID'))
                            .show()
                            .siblings('.yslcTab')
                            .hide();
                        $(this).addClass('pro_yslc_lefttitle-active').siblings().removeClass('pro_yslc_lefttitle-active');
                    }).eq(0).click();
            });
        </script>
    </c:otherwise>
</c:choose>

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