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


<style type="text/css">
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
    #pmsDeviceInfo {
        overflow: hidden;
    }

    .ztree li span.button.switch.level0 {visibility:hidden; width:1px;}
    .ztree li ul.level0 {padding:0; background:none;}
</style>


<div class="tableContent white ">
    <div class="content_wrap">
        <div class="left">
            <div>
                <ul id="bdzdocTree"
                    class="ztree"
                    data-toggle="ztree"
                    data-edit-enable="true"
                    data-max-add-level="3"
                    data-options="{
                        setting: {
                            view: {
                                selectedMulti: false
                            },
                            async: {
                                enable: true,
                                url:'${ctx}/${appid}/pmsbdz/PmsBdzTree?bdzid=${bdzid}',
                                autoParam:['id', 'level=hierarchy'],
                                type: 'get'
                            },
                            callback: {
                                onAsyncSuccess: onAsyncSuccess,
                                onClick: onClick,
                            },
                        }
                    }"></ul>
            </div>
        </div>
        <div id="pmsDeviceImageInfo" class="right">
        </div>
    </div>
</div>

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


<script type="text/javascript" >
    var onClick = function(e, treeId, treeNode) {
        if (treeNode.level > 0 && !$(e.target).hasClass("tree_add")) {
            var zTree = $.fn.zTree.getZTreeObj("bdzdocTree");
            zTree.expandNode(treeNode);
            if (!treeNode.isParent) {
                $(this).bjuiajax("doLoad", {
                    target: $("#pmsDeviceImageInfo"),
                    url: "${ctx}/${appid}/pmsbdz/pmsDeviceImageInfo",
                    data: {
                        pms_deviceId: treeNode.id,
                    }
                });
            }
        }
    };
    var onAsyncSuccess = function(event, treeId, treeNode, msg) {
        if (treeId === "bdzdocTree") {
            if (treeNode && treeNode.id !== 0) {

            } else {
                var zTree = $.fn.zTree.getZTreeObj("bdzdocTree");
                zTree.expandNode(zTree.getNodeByTId("bdzdocTree_1"), true, false, false);
            }
        }
    };
</script>
