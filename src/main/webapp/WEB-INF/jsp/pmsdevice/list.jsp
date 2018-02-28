<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

<script type="text/javascript" src="${ctx }/public/libraries/underlineTab/underlineTab.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/public/css/underlineTab.css"/>


<style type="text/css">
    .pms_device_wrap > div {
        height: 100%;
    }
    div.content_wrap > div {
        margin: 0px 2px;
    }
    div.content_wrap div.left{
        margin: 0px;
        width: 25%;
        display: inline-block;
        float: left;
        text-align:left;
        overflow-x: auto;
        border-right: 1px solid #d6d6d6;
    }
    div.content_wrap div.right{
        width: 75%;
        display: inline-block;
        float: right;
        margin: 0px;
    }
    div.content_wrap > div > div {
        display: inline-block;
    }

    .ztree li span.button.switch.level0 {visibility:hidden; width:1px;}
    .ztree li ul.level0 {padding:0; background:none;}
</style>


<div class="bjui-pageContent tableContent white ess-pageContent">
    <div class="content_wrap pms_device_wrap">
        <div class="left">
            <div>
                <ul id="treeDemo" class="ztree"></ul>
            </div>
        </div>
        <div id="issuesAndTasksContent" class="right">
        </div>
    </div>
</div>


<script type="text/javascript" >
    var onAsyncSuccess = function(event, treeId, treeNode, msg) {
        if (treeId === "treeDemo") {
            if (treeNode && treeNode.id !== 0) {

            } else {
                var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                zTree.expandNode(zTree.getNodeByTId("treeDemo_1"), true, false, false);
            }
        }
    }
    var onClick = function(e, treeId, treeNode) {
        if (treeNode.level > 0) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.expandNode(treeNode);
            if (!treeNode.isParent) {
                $(this).bjuiajax("doLoad", {
                    target: $("#issuesAndTasksContent"),
                    url: "${ctx}/${appid}/pmsdevice/issuesAndTasks?pms_deviceId=" + treeNode.id,
                });
            }
        }
    }
    var dblClickExpand = function(treeId, treeNode) {
        return treeNode.level > 0;
    }
    var setting = {
        view: {
            dblClickExpand: dblClickExpand,
        },
        async: {
            enable: true,
            url:"${ctx}/${appid}/pmsdevice/pmsDeviceTree",
            autoParam:["id", "level=hierarchy"],
            type: "get"
        },
        callback: {
            onAsyncSuccess: onAsyncSuccess,
            onClick: onClick
        }
    };
    $(function(){
        $.fn.zTree.init($("#treeDemo"), setting);
    });
</script>
