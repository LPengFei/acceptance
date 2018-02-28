<%--
  Created by IntelliJ IDEA.
  User: gaozhou
  Date: 2017/1/18
  Time: 16:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>

<style>
    .ess-form > fieldset ul li label.unit {
        margin-top: 0px;
        display: inline-block;
        width: 50px;
        float: none;
        color: #61726f;
    }

    .props-input {
        display: inline-block;
        width: 45%;
    }
    .props-input > div {
        display: inline-block;
        margin: auto;
    }
    .props-input label {
        /*width: 100%;*/
        margin-right: 5px;
        line-height: 24px;
        font-weight: normal;
        font-size: 14px;
        color: #1ABC9C;
    }
    .props-input label:first-child {
        width: 100px;
        text-align: right;
        float: left;
    }

</style>

<form action="${ctx}/app/pmsdevice/saveProps" data-toggle="validate" data-alertmsg="false">
    <input type="hidden" name="deviceId" value="${deviceId}">
    <input type="hidden" name="pms_deviceId" value="${pms_deviceId}">
    <div class="ess-form">
        <fieldset>
            <legend>物理参数:</legend>
            <%--<ul>--%>
                <c:forEach items="${props.get('physics')}" var="v" varStatus="r">
                    <%--<li class="input_11">--%>
                        <%--<label>${v.name}</label>--%>
                        <%--<input type="text"--%>
                               <%--name="physics.${v.name}"--%>
                               <%--value="${v.value}"--%>
                               <%--placeholder="请输入${v.name}"/>--%>
                        <%--<label class="unit">${v.unit}</label>--%>
                    <%--</li>--%>
                    <div class="props-input">
                        <label>${v.name}</label>
                        <input type="text"
                               name="physics.${v.name}"
                               value="${v.value}"
                               placeholder="请输入"/>
                        <label class="unit">${v.unit}</label>
                    </div>
                </c:forEach>
            <%--</ul>--%>
            <div class="clearfix"></div>
        </fieldset>
        <fieldset>
            <legend>运行参数:</legend>
            <%--<ul>--%>
                <c:forEach items="${props.get('runtime')}" var="v" varStatus="r">
                    <%--<li class="input_11">--%>
                        <%--<label>${v.name}</label>--%>
                        <%--<input type="text"--%>
                               <%--name="runtime.${v.name}"--%>
                               <%--value="${v.value}"--%>
                               <%--placeholder="请输入${v.name}"/>--%>
                        <%--<label class="unit">${v.unit}</label>--%>
                    <%--</li>--%>
                    <div class="props-input">
                        <label>${v.name}</label>
                        <input type="text"
                               name="runtime.${v.name}"
                               value="${v.value}"
                               placeholder="请输入"/>
                        <label class="unit">${v.unit}</label>
                    </div>

                </c:forEach>
            <%--</ul>--%>
            <div class="clearfix"></div>
        </fieldset>
        <!-- 操作按钮 -->
        <div style="text-align:center; padding-top: 20px;">
            <button type="submit" class="btn" style="background: #14CAB4; color: white;">保存</button>
        </div>
    </div>
</form>