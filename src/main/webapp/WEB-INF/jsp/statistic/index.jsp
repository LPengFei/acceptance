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
        width:14.2857143%;
        font-size: 14px;
        font-weight: 500;
        cursor: pointer;
    }
    .top-nav-active {
        background-color: #ffffff !important;
        color: #5e6b6a !important;
    }
    .statistic_contenter {
        width: 100%;
        height: 100%;
    }
</style>



<div class="top-nav statistic">
    <ul>
        <li data-target="projectList" class="on top-nav-active" data-url="${ctx}/${appid}/statistic/projectList">工程项目验收情况分析</li>
        <li data-target="total" class="on" data-url="${ctx}/${appid}/statistic/total?projectId=${projectId}">工程项目汇总分析</li>
        <li data-target="total" class="off" data-toggle="alertmsg" data-options="{msg:'二期功能暂未开发！', type:'warn'}">设备运行情况分析</li>
    </ul>
</div>
<div class="statistic_contenter projectList">
</div>
<div class="statistic_contenter total">
</div>


<script>
    $(function(){
        $('.statistic.top-nav li.on').click(function(){
            $(this).addClass('top-nav-active').siblings().removeClass('top-nav-active');

            $(".statistic_contenter").hide();
            var divclass = "."+  $(this).data("target")
            $(divclass).show();

            $(this).bjuiajax("doLoad", {
                target: $(divclass),
                url: $(this).data("url")
            });
        }).eq(0).click();
    });
</script>
