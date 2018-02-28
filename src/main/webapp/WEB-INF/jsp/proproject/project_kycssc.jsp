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


<div class="pro_yslcC" data-projectId="${projectId}"
     style="padding-top: 0px;position: relative;background-color: #f8f8f8;">

    <div class="pro_yslc_lefttitle">
        <ul>
            <%--<c:forEach items="${checks}" var="check" varStatus="s">--%>
            <%--<li tabID="${check.key}" class="pro_yslc_lefttitle-active">${check.key}</li>--%>
            <%--</c:forEach>--%>
            <li tabID="pro_kycssc" class="pro_yslc_lefttitle-active">可研初设审查</li>
            <li tabID="pro_cnys">厂内验收</li>
            <li tabID="pro_dhys">到货验收</li>
            <li tabID="pro_ybgcys">隐蔽工程验收</li>
            <li tabID="pro_zjys">中间验收</li>
            <li tabID="pro_jgyys">竣工预验收</li>
            <li tabID="pro_qdys">启动验收</li>
        </ul>
    </div>


    <%--可研初设审查--%>
    <div class="pro_kycssc yslcTab">
        <div class="pro_wrap" style="padding-top: 10px">
            <div class="jgyys-btn" style="float: left;">
                <label>任务名称：</label>
                <input type="text" class="filter" value="" name="name" data-flow="feasible_check">
                <button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button>
            </div>
            <table class="table table-bordered project-scale">
                <thead>
                <tr class=" pro_yslc_title">
                    <th align="center">验收任务</th>
                    <th align="center">验收标准卡</th>
                    <th align="center">全过程技术监督卡</th>
                    <th align="center">验收记录</th>
                </tr>
                </thead>
                <tbody class="yslcTable">
                <c:forEach items="${checks.feasible_check}" var="v" varStatus="s">
                    <tr>
                        <td width="30%">${v.name}</td>
                        <td width="30%">
                            <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=0" data-toggle="navtab"
                               data-id="task-info"
                               data-title="详情任务">
                                    ${v._card.name}
                            </a>
                        </td>
                        <td width="30%">
                            <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=1" data-toggle="navtab"
                               data-id="task-info"
                               data-title="详情任务">
                                  	 组合电器全过程技术监督卡
                            </a>
                            
                        </td>
                        <td align="center">
                            <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=2" data-toggle="navtab"
                               data-id="task-info"
                               data-title="详情任务">
                                问题记录(${v._itemIssuesCount})
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <%--场内验收--%>
    <div class="pro_cnys yslcTab">
        <div class="pro_wrap" style="padding-top: 10px">
            <div class="jgyys-btn" style="float: left;">
                <label>任务名称：</label>
                <input type="text" class="filter" value="" name="name" data-flow="factory_check">
                <button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button>
            </div>
            <div class="jgyys-btn pull-right">
                <a href="${ctx}/${appid }/proprojectattachment/index?projectId=${projectId}&attachType=订货资料"
                   data-toggle="navtab" data-id="proprojectattachment-list" data-title="订货资料"><span>订货资料</span></a>
            </div>
            <table class="table table-bordered project-scale">
                <thead>
                <tr class=" pro_yslc_title">
                    <th align="center">验收任务</th>
                    <th align="center">验收设备型号</th>
                    <th align="center">验收标准卡</th>
                    <th align="center">全过程技术监督卡</th>
                    <th align="center">验收记录</th>
                    <th align="center">重大问题反馈联系单</th>
                </tr>
                </thead>
                <tbody class="yslcTable">
                <c:forEach items="${checks.factory_check}" var="v" varStatus="s">
                    <tr>
                        <td>${v.name}</td>
                        <td>${v.deviceno}</td>
                        <td>
                            <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=0" data-toggle="navtab"
                               data-id="task-info"
                               data-title="详情任务">
                                    ${v._card.name}
                            </a>
                        </td>
                        <td>
                             <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=1" data-toggle="navtab"
                               data-id="task-info"
                               data-title="详情任务">
                                  	 变压器全过程技术监督卡
                            </a>
                        </td>
                        <td>
                            <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=2" data-toggle="navtab"
                               data-id="task-info"
                               data-title="详情任务">
                                问题记录(${v._itemIssuesCount})
                            </a>
                        </td>
                        <td>
                            <c:if test="${v._importantIssuesCount > 0}">
                                <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=3" data-toggle="navtab"
                                   data-id="task-info"
                                   data-title="详情任务">
                                    重大问题反馈联系单
                                </a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <%--到货验收--%>
    <div class="pro_dhys yslcTab">
        <div class="pro_wrap" style="padding-top: 10px">
            <div class="jgyys-btn" style="float: left;">
                <label>任务名称：</label>
                <input type="text" class="filter" value="" name="name" data-flow="arrive_check">
                <button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button>
            </div>
            <div class="jgyys-btn pull-right">
                <a href="${ctx}/${appid }/task/countDevices?projectId=${projectId}" data-toggle="navtab"
                   data-id="count-list" data-title="到货统计"><span>到货统计</span></a>
            </div>
            <table class="table table-bordered project-scale">
                <thead>
                <tr class=" pro_yslc_title">
                    <th align="center">验收任务</th>
                    <th align="center">验收标准卡</th>
                    <th align="center">全过程技术监督卡</th>
                    <th align="center">到货清单</th>
                    <th align="center">验收记录</th>
                    <th align="center">重大问题反馈联系单</th>
                </tr>
                </thead>
                <tbody class="yslcTable">
                <c:forEach items="${checks.arrive_check}" var="v" varStatus="s">
                    <tr>
                        <td>${v.name}</td>
                        <td>
                            <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=0" data-toggle="navtab"
                               data-id="task-info"
                               data-title="详情任务">
                                    ${v._card.name}
                            </a>
                        </td>
                        <td>
                             <%-- <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=1" data-toggle="navtab"
                               data-id="task-info"
                               data-title="详情任务">
                                  	 变压器全过程技术监督卡
                            </a> --%>
                        </td>
                        <td>
                            <c:set var="images" value="${v._countTask.imgs.split(',')}"/>
                            <c:if test="${fn:length(images) > 0}">
                                <c:forEach var="image" items="${images}" varStatus="s1">
                                    <c:if test="${fn:length(image) > 0}">
                                        <a class="fancybox fancybox-buttons" href="${ctx}/upload/${image}"
                                           data-fancybox-group="${v.id}">
                                            <c:if test="${s1.count == 1}">
                                                查看
                                            </c:if>
                                        </a>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </td>
                        <td>
                            <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=2" data-toggle="navtab"
                               data-id="task-info"
                               data-title="详情任务">
                                问题记录(${v._itemIssuesCount})
                            </a>
                        </td>
                        <td>
                            <c:if test="${v._importantIssuesCount > 0}">
                                <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=3" data-toggle="navtab"
                                   data-id="task-info"
                                   data-title="详情任务">
                                    重大问题反馈联系单
                                </a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>


    <%--隐蔽工程验收--%>
    <div class="pro_ybgcys yslcTab">
        <div class="pro_wrap" style="padding-top: 10px">
            <div class="jgyys-btn" style="float: left;">
                <label>任务名称：</label>
                <input type="text" class="filter" value="" name="name" data-flow="hidden_check">
                <button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button>
            </div>
            <table class="table table-bordered project-scale">
                <thead>
                <tr class=" pro_yslc_title">
                    <th align="center">验收任务</th>
                    <th align="center">验收标准卡</th>
                    <th align="center">全过程技术监督卡</th>
                    <th align="center">验收记录</th>
                    <th align="center">重大问题反馈联系单</th>
                </tr>
                </thead>
                <tbody class="yslcTable">
                <c:forEach items="${checks.hidden_check}" var="v" varStatus="s">
                    <tr>
                        <td>${v.name}</td>
                        <td>
                            <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=0" data-toggle="navtab"
                               data-id="task-info"
                               data-title="详情任务">
                                    ${v._card.name}
                            </a>
                        </td>
                        <td>
                             <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=1" data-toggle="navtab"
                               data-id="task-info"
                               data-title="详情任务">
                                  	 变压器全过程技术监督卡
                            </a>
                        </td>
                        <td>
                            <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=2" data-toggle="navtab"
                               data-id="task-info"
                               data-title="详情任务">
                                问题记录(${v._itemIssuesCount})
                            </a>
                        </td>
                        <td>
                            <c:if test="${v._importantIssuesCount > 0}">
                                <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=3" data-toggle="navtab"
                                   data-id="task-info"
                                   data-title="详情任务">
                                    重大问题反馈联系单
                                </a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>


    <%--中间验收--%>
    <div class="pro_zjys yslcTab">
        <div class="pro_wrap" style="padding-top: 10px">
            <div class="jgyys-btn" style="float: left;">
                <label>任务名称：</label>
                <input type="text" class="filter" value="" name="name" data-flow="middle_check">
                <button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button>
            </div>
            <table class="table table-bordered project-scale">
                <thead>
                <tr class=" pro_yslc_title">
                    <th align="center">验收任务</th>
                    <th align="center">验收标准卡</th>
                    <th align="center">全过程技术监督卡</th>
                    <th align="center">验收记录</th>
                    <th align="center">重大问题反馈联系单</th>
                </tr>
                </thead>
                <tbody class="yslcTable">
                <c:forEach items="${checks.middle_check}" var="v" varStatus="s">
                    <tr>
                        <td>${v.name}</td>
                        <td>
                            <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=0" data-toggle="navtab"
                               data-id="task-info"
                               data-title="详情任务">
                                    ${v._card.name}
                            </a>
                        </td>
                        <td>
                             <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=1" data-toggle="navtab"
                               data-id="task-info"
                               data-title="详情任务">
                                  	 变压器全过程技术监督卡
                            </a>
                        </td>
                        <td>
                            <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=2" data-toggle="navtab"
                               data-id="task-info"
                               data-title="详情任务">
                                问题记录(${v._itemIssuesCount})
                            </a>
                        </td>
                        <td>
                            <c:if test="${v._importantIssuesCount > 0}">
                                <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=3" data-toggle="navtab"
                                   data-id="task-info"
                                   data-title="详情任务">
                                    重大问题反馈联系单
                                </a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <div class="pro_jgyys yslcTab">
        <div class="pro_wrap" style="padding-top: 10px">
            <div class="jgyys-btn" style="float: left;">
                <label>任务名称：</label>
                <input type="text" class="filter" value="" name="name" data-flow="complete_check">
                <button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button>
            </div>
            <div class="jgyys-btn pull-right">
                <a href="${ctx}/${appid }/proprojectattachment/index?projectId=${projectId}&attachType=验收计划"
                   data-toggle="navtab" data-id="proprojectattachment-list" data-title="验收计划"><span>验收计划</span></a>
                <a href="${ctx}/${appid }/proprojectattachment/index?projectId=${projectId}&attachType=验收方案"
                   data-toggle="navtab" data-id="proprojectattachment-list" data-title="验收方案"><span>验收方案</span></a>
                <a href="${ctx}/${appid }/proprojectattachment/index?projectId=${projectId}&attachType=监理报告"
                   data-toggle="navtab" data-id="proprojectattachment-list" data-title="监理报告"><span>监理报告</span></a>
            </div>
            <table class="table table-bordered project-scale">
                <thead>
                <tr class=" pro_yslc_title">
                    <th align="center">验收任务</th>
                    <th align="center">验收标准卡</th>
                    <th align="center">全过程技术监督卡</th>
                    <th align="center">验收记录</th>
                    <th align="center">重大问题反馈联系单</th>
                </tr>
                </thead>
                <tbody class="yslcTable">
                <c:forEach items="${checks.complete_check}" var="v" varStatus="s">
                    <tr>
                        <td>${v.name}</td>
                        <td>
                            <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=0" data-toggle="navtab"
                               data-id="task-info"
                               data-title="详情任务">
                                    ${v._card.name}
                            </a>
                        </td>
                        <td>
                             <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=1" data-toggle="navtab"
                               data-id="task-info"
                               data-title="详情任务">
                                  	 变压器全过程技术监督卡
                            </a>
                        </td>
                        <td align="center">
                            <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=2" data-toggle="navtab"
                               data-id="task-info"
                               data-title="详情任务">
                                问题记录(${v._itemIssuesCount})
                            </a>
                        </td>
                        <td>
                            <c:if test="${v._importantIssuesCount > 0}">
                                <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=3" data-toggle="navtab"
                                   data-id="task-info"
                                   data-title="详情任务">
                                    重大问题反馈联系单
                                </a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <%--启动验收--%>
    <div class="pro_qdys yslcTab">
        <div class="pro_wrap" style="padding-top: 10px">
            <div class="jgyys-btn" style="float: left;">
                <label>任务名称：</label>
                <input type="text" class="filter" value="" name="name" data-flow="start_check">
                <button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button>
            </div>
            <div class="jgyys-btn pull-right">
                <a href="${ctx}/${appid }/proprojectattachment/index?projectId=${projectId}&attachType=启动验收报告"
                   data-toggle="navtab" data-id="proprojectattachment-list" data-title="启动验收报告"><span>启动验收报告</span></a>
                <a href="${ctx}/${appid }/proprojectattachment/index?projectId=${projectId}&attachType=启动试运行方案"
                   data-toggle="navtab" data-id="proprojectattachment-list"
                   data-title="启动试运行方案"><span>启动试运行方案</span></a>
                <a href="${ctx}/${appid }/proproject/projectDocs?projectId=${projectId}" data-toggle="navtab"
                   data-id="projectdoc-list" data-title="工程移交"><span>工程移交</span></a>
            </div>
            <table class="table table-bordered project-scale">
                <thead>
                <tr class=" pro_yslc_title">
                    <th align="center">验收任务</th>
                    <th align="center">验收标准卡</th>
                    <th align="center">全过程技术监督卡</th>
                    <%--<th align="center">验收记录</th>--%>
                    <th align="center">重大问题反馈联系单</th>
                </tr>
                </thead>
                <tbody class="yslcTable">
                <c:forEach items="${checks.start_check}" var="v" varStatus="s">
                    <tr>
                        <td>${v.name}</td>
                        <td>
                            <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=0" data-toggle="navtab"
                               data-id="task-info"
                               data-title="详情任务">
                                    ${v._card.name}
                            </a>
                        </td>
                        <td>
                             <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=1" data-toggle="navtab"
                               data-id="task-info"
                               data-title="详情任务">
                                  	 变压器全过程技术监督卡
                            </a>
                        </td>
                        <%--<td>--%>
                            <%--<a href="${ctx}/${appid }/task/info?id=${v.id}&tab=1" data-toggle="navtab"--%>
                               <%--data-id="task-info"--%>
                               <%--data-title="详情任务">--%>
                                <%--问题记录(${v._itemIssuesCount})--%>
                            <%--</a>--%>
                        <%--</td>--%>
                        <td>
                            <c:if test="${v._importantIssuesCount > 0}">
                                <a href="${ctx}/${appid }/task/info?id=${v.id}&tab=2" data-toggle="navtab"
                                   data-id="task-info"
                                   data-title="详情任务">
                                    重大问题反馈联系单
                                </a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>


</div>


<script>
    $(function () {
        var flows = ['feasible_check', 'factory_check', 'arrive_check', 'hidden_check', 'middle_check', 'complete_check', 'start_check'];
        var flow_index = flows.indexOf('${flow}');
        flow_index = flow_index < 0 ? 0 : flow_index;
        $('[data-projectId="${projectId}"].pro_yslcC input.filter[data-flow="${flow}"]').val('${task_name}');

        $('[data-projectId="${projectId}"].pro_yslcC')
            .find('.yslcTab')
            .find('button[type="submit"]')
            .click(function () {
                var filter = $(this).closest('.yslcTab').find('input.filter');
                var flow = filter.data('flow');
                var task_name = filter.val();
                var url = "${ctx}/${appid }/proproject/checks?projectId=${projectId}&flow=" + flow + "&task_name=" + task_name;
                $(this).bjuiajax("doLoad", {
                    target: $(this).closest('.pro_yslcC'),
                    url: url
                });
            })
            .end()
            .hide()
            .end()
            .find('.pro_yslc_lefttitle li')
            .click(function () {
                $(this).parents('.pro_yslcC')
                    .find('.yslcTab.' + $(this).attr('tabID'))
                    .show()
                    .siblings('.yslcTab')
                    .hide();
                $(this).addClass('pro_yslc_lefttitle-active').siblings().removeClass('pro_yslc_lefttitle-active');
            })
            .eq(flow_index)
            .click();
    });
</script>

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