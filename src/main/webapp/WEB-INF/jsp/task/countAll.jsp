<%@ page import="java.util.Map" %>
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
</style>



<div class="bjui-pageHeader">
    <form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/${appid }/task/countDevices?projectId=${projectId}" method="post">
        <div class="bjui-searchBar ess-searchBar">
            <div><label>设备名称：</label>
            <input type="text"
                   id="device_name_filter"
                   value="${deviceName}"
                   name="deviceName"
                   data-ds="${ctx}/${appid}/devicetype/deviceTypeName"
                   data-toggle="selectpicker"
                   data-chk-style="radio"
                   data-live-search="true"
            >
            </div>
        </div>
    </form>
</div>

<div class="bjui-pageContent">
    <div class="ess-form">
        <div class="panel-heading">

            <h3 class="panel-title">
                <button class="list" disabled="disabled">
                    <span class="glyphicon glyphicon-th-large"></span>
                </button>
                ${deviceName}到货情况:(数量：${total})
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
                    <c:forEach var="countDevice" items="${records}" varStatus="s">
                        <tr>
                            <td>${countDevice.name}</td>
                            <td>${countDevice.deviceno}</td>
                            <td>${countDevice.amount}</td>
                            <td>${countDevice.time}</td>
                            <td>${countDevice.place}</td>
                            <td>${countDevice.counter}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
    $(function () {
        var changed = false;
        $("#device_name_filter_select").change(function (e) {
            if (changed) {
                $("#device_name_filter_select").closest("form").submit();
            }
            changed = true;
        });
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


