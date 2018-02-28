<%--
  Created by IntelliJ IDEA.
  User: gaozhou
  Date: 2016/12/28
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>

<div class="bjui-pageContent associate-confirm">
    <div class="text-center">
        <h3>系统检测到还有缺陷以及验收任务未关联，是否进行关联？</h3>
        <div class="col-md-6 col-md-offset-3">
            <button type="button" class="btn-red pull-left btn-close">无视</button>
            <button type="button" class="btn-green pull-right btn-close" onclick="associate(this);">关联</button>
            <input id="associate-confirm-value" type="hidden">
        </div>
    </div>
</div>
<script type="text/javascript">
    function associate(e) {
        $(e).next("input").data("associate", true);
        return true;
    }
</script>