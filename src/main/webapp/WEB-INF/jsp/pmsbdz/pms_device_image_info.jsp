<%--
  Created by IntelliJ IDEA.
  User: gaozhou
  Date: 2017/1/17
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>

<style type="text/css">
    .top-nav ul {
        width: 100%;
        height: auto;
    }

    .top-nav {
        width: 100%;
        position: relative;
        display: inline-block;
    }

    .top-nav li {
        float: left;
        list-style: none;
        height: 45px;
        line-height: 45px;
        text-align: center;
        background-color: #e9f3f1;
        color: #797665;
        width: 33.333333%;
        font-size: 14px;
        font-weight: 500;
        cursor: pointer;
    }

    .top-nav-active {
        background-color: #ffffff !important;
        color: #5e6b6a !important;
    }

    .image-galley-box {
        background-color: black;
        /*border: 2px solid red;*/
        /*width: 100%;*/
        width: 100%;
        height: 70%;
        margin: auto auto;
        overflow: hidden;
    }

    #main-info {
        /*position: relative;*/
        /*bottom: 0px;*/
        width: 100%;
        overflow: auto;
        height: 55%;

        /*height: 100%;*/
        /*border: 2px solid red;*/
        /*margin-top: ;*/
    }

    .ess-form > fieldset ul li label {
        color: #1ABC9C;
        width: 100%;
        float: left;
        margin-top: 15px;
    }

    .ess-form > ul > li > label {
        margin-top: 0px;
    }

    form > .ess-form > ul {
        margin-top: 0px;
    }
</style>


<div class="top-nav pms-images">
    <ul>
        <li data-target="image1" class="on top-nav-active">设备照片</li>
        <li data-target="image2" class="on">铭牌照片</li>
        <li data-target="image3" class="on">附件照片</li>
        <%--<li data-target="image4" class="on">验收报告</li>--%>
    </ul>
</div>
<c:set var="imagesId" value="11"/>
<c:set var="gallery_images" value="${imageMap.get('device_pic')}"/>
<div class="image-galley-box image1">
    <%@include file="gallery_images.jsp" %>
</div>
<c:set var="imagesId" value="22"/>
<c:set var="gallery_images" value="${imageMap.get('nameplate_pic')}"/>
<div class="image-galley-box image2">
    <%@include file="gallery_images.jsp" %>
</div>
<c:set var="imagesId" value="33"/>
<c:set var="gallery_images" value="${imageMap.get('attachment_pic')}"/>
<div class="image-galley-box image3">
    <%@include file="gallery_images.jsp" %>
</div>
<%--<c:set var="imagesId" value="44"/>--%>
<%--<c:set var="gallery_images" value="${imageMap.get('acceptance_report')}"/>--%>
<%--<div class="image-galley-box image4">--%>
    <%--<%@include file="gallery_images.jsp" %>--%>
<%--</div>--%>
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


<script>
    $(function () {
        $('.pms-images.top-nav li.on').click(function () {
            $(this).addClass('top-nav-active').siblings().removeClass('top-nav-active');
            $(".image-galley-box").hide();
            $("." + $(this).data("target")).show();
        }).eq(0).click();
    });
</script>


