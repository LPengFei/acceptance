<%--
  Created by IntelliJ IDEA.
  User: gaozhou
  Date: 2016/12/28
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/taglib.jsp" %>


<%--<div class="bjui-pageContent">--%>
    <%--<form action="${ctx}/${appid}/pmsdevice/saveAssociate" class="pageForm" method="post">--%>
        <%--<input type="hidden" name="issueId" value="${issueId}">--%>
        <%--<input type="hidden" name="taskId" value="${taskId}">--%>
        <%--<ul id="associateTree" class="ztree"></ul>--%>


        <%--&lt;%&ndash;<div class="bjui-pageFooter">&ndash;%&gt;--%>
            <%--<ul>--%>
                <%--<li><button type="button" class="btn-close">关闭</button></li>--%>
                <%--<li><button type="submit" class="btn-default">保存</button></li>--%>
            <%--</ul>--%>
        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--</form>--%>
<%--</div>--%>


<script type="text/javascript">
    function associateCallback() {
        $(".issues-and-tasks li.top-nav-active").click();
    }
</script>

<div class="bjui-pageContent">
    <form id="associateTree-form" action="${ctx}/${appid}/pmsdevice/saveAssociate" class="pageForm" data-toggle="validate" data-onClose="associateCallback">
        <%--<input type="hidden" class="required" name="issueId" value="${issueId}">--%>
        <%--<input type="hidden" class="required" name="taskId" value="${taskId}">--%>
        <%--<ul id="associateTree" class="ztree"></ul>--%>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close">关闭</button></li>
        <li><button type="submit" class="btn-default">保存</button></li>
    </ul>
</div>




<script type="text/javascript" >
    var onAsyncSuccess = function(event, treeId, treeNode, msg) {
        if (treeId === "associateTree") {
            if (treeNode && treeNode.id !== 0) {

            } else {
                var zTree = $.fn.zTree.getZTreeObj("associateTree");
                zTree.expandNode(zTree.getNodeByTId("treeDemo_1"), true, false, false);
            }
        }
    }
    var onClick = function(e, treeId, treeNode) {
        if (treeNode.level > 0) {
            var zTree = $.fn.zTree.getZTreeObj("associateTree");
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
    var zTreeOnCheck = function(event, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("associateTree");
        $("#associateTree-form > input[type='hidden'].auto").remove();
        zTree.getCheckedNodes(true)
            .filter(function (v) {return v.hierarchy === 3;})
            .map(function (v) {
                var html = ["<input type='hidden' class='auto'", "name='pms_devices'", "value='", v.id, "'/>"].join("");
                $("#associateTree-form").append(html);
            })
    };
    var setting = {
        check: {
            enable: false
        },
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
            onClick: onClick,
            onCheck: zTreeOnCheck
        }
    };
    $(function(){
        $.fn.zTree.init($("#associateTree"), setting);
    });
</script>

