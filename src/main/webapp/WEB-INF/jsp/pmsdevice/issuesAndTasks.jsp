<%--
  Created by IntelliJ IDEA.
  User: gaozhou
  Date: 2016/12/28
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/taglib.jsp" %>

<style type="text/css">
    .top-nav ul {
        width: 100%;
        height:auto;
    }
    .top-nav {
        width:100%;
        position: relative;
        display: inline-block;
    }
    .top-nav li{
        float: left;
        list-style: none;
        height: 45px;
        line-height: 45px;
        text-align: center;
        background-color: #e9f3f1;
        color:#797665;
        width:11.11111111111111%;
        font-size: 14px;
        font-weight: 500;
        cursor: pointer;
    }
    .top-nav-active {
        background-color: #ffffff !important;
        color: #5e6b6a !important;
    }
    .issue-content {
        width: 100%;
        height: 50%;
        overflow-y: scroll;
    }
    .task-content {
        width:100%;
        height: 50%;
        overflow-y: scroll;
    }
</style>



<div class="top-nav issues-and-tasks">
    <ul>
        <li data-flow="feasible_check" class="on top-nav-active">可研初设审查</li>
        <li data-flow="factory_check" class="on">厂内验收</li>
        <li data-flow="arrive_check" class="on">到货验收</li>
        <li data-flow="hidden_check" class="on">隐蔽工程验收</li>
        <li data-flow="middle_check" class="on">中间验收</li>
        <li data-flow="complete_check" class="on">竣工预验收</li>
        <li data-flow="start_check" class="on">启动验收</li>
        <li data-flow="" class="off" data-toggle="alertmsg" data-options="{msg:'二期功能暂未开发！', type:'warn'}">
            运行阶段
        </li>
        <li data-flow="" class="off" data-toggle="alertmsg" data-options="{msg:'二期功能暂未开发！', type:'warn'}">
            设备退役
        </li>
    </ul>

</div>
<div class="issue-content">
</div>
<div class="task-content">
</div>


<script>
    $(function(){
        $('.issues-and-tasks.top-nav li.on').click(function(){
            $(this).addClass('top-nav-active').siblings().removeClass('top-nav-active');
            $(this).bjuiajax("doLoad", {
                target: $(".issue-content"),
                url: "${ctx}/${appid}/pmsdevice/issues?pms_deviceId=${pms_deviceId}&flow=" + $(this).data("flow")
            });
            $(this).bjuiajax("doLoad", {
                target: $(".task-content"),
                url: "${ctx}/${appid}/pmsdevice/tasks?pms_deviceId=${pms_deviceId}&flow=" + $(this).data("flow")
            });
        }).eq(0).click();
    });
</script>
