<%--
  Created by IntelliJ IDEA.
  User: gaozhou
  Date: 2016/12/28
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/taglib.jsp" %>



<div class="bjui-pageContent">
    <form id="associateTree-form" action="${ctx}/${appid}/pmsdevice/saveAssociate" class="pageForm" data-toggle="validate">
        <input type="hidden" class="required" name="issueId" value="${issueId}">
        <input type="hidden" class="required" name="taskId" value="${taskId}">
        <ul id="associateTree" class="ztree"></ul>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close">关闭</button></li>
        <li><button type="submit" class="btn-default" style="display: none;">保存</button></li>
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
                if (${notLoad ? true : false}) {
                    zTree.checkNode(treeNode);
                    zTreeOnCheck();
                } else {
                    $(this).bjuiajax("doLoad", {
                        target: $("#issuesAndTasksContent"),
                        url: "${ctx}/${appid}/pmsdevice/issuesAndTasks?pms_deviceId=" + treeNode.id,
                    });
                }
            }
        }
    }
    var dblClickExpand = function(treeId, treeNode) {
        return treeNode.level > 0;
    }
    var zTreeOnCheck = function(event, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("associateTree");
        $("#associateTree-form > input[type='hidden'].auto").remove();
        var count = zTree.getCheckedNodes(true)
            .filter(function (v) {return v.hierarchy === 3;})
            .map(function (v) {
                var html = ["<input type='hidden' class='auto'", "name='pms_devices'", "value='", v.id, "'/>"].join("");
                $("#associateTree-form").append(html);
                return v;
            })
            .length;

        var $button = $("#associateTree-form").closest(".bjui-pageContent").next(".bjui-pageFooter").find("button[type='submit']");
        $button.toggle(count > 0);
    };
    var setting = {
        check: {
            enable: true
        },
        view: {
            dblClickExpand: dblClickExpand,
        },
        async: {
            enable: true,
            url:"${ctx}/${appid}/pmsdevice/pmsDeviceTree",
            autoParam:["id", "level=hierarchy"],
            otherParam:["issueId", ${issueId}, "taskId", ${taskId}],
            type: "get",
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

