<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>


<script type="text/javascript" src="${ctx }/public/libraries/underlineTab/underlineTab.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/public/css/underlineTab.css"/>


<style type="text/css">
    .bdz_warp > div {
        height: 100%;
    }

    div.content_wrap > div {
        margin: 0px 2px;
    }

    div.content_wrap div.left {
        margin: 0px;
        width: 25%;
        display: inline-block;
        float: left;
        text-align: left;
        overflow-x: auto;
        border-right: 1px solid #d6d6d6;
    }

    div.content_wrap div.right {
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

    .ztree li span.button.switch.level0 {
        visibility: hidden;
        width: 1px;
    }

    .ztree li ul.level0 {
        padding: 0;
        background: none;
    }
</style>


<div class="bjui-pageContent tableContent white ess-pageContent">
    <div class="content_wrap bdz_warp">
        <div class="left">
            <div>
                <%--<ul id="bdzTree" class="ztree" data-toggle="ztree" data-expand-all="false" data-on-click="do_open_layout" data-edit-enable="true" data-add-hover-dom="edit" data-show-rename-btn="true" data-before-remove="M_BeforeRemove" data-remove-hover-dom="edit" data-on-remove="M_NodeRemove"--%>
                <%--data-max-add-level="6" data-options="{nodes:'ztree_returnjson'}" data-setting="{callback: {beforeRename: setRename}}">--%>
                <%--</ul>--%>
                <ul id="bdzTree" class="ztree"
                    data-toggle="ztree"
                    data-edit-enable="true"
                    data-max-add-level="3"
                    data-add-hover-dom="addHoverDom"
                    data-remove-hover-dom="removeHoverDom"
                    data-on-node-created="zTreeOnNodeCreated"
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
                                onClick: pms_list_click,
                                onRename: pms_list_update
                            },
                            edit: {
                                enable: true,
                                showRemoveBtn: false,
                                showRenameBtn: show_pms_rename_btn
                            },
                        }
                    }"></ul>
            </div>
        </div>
        <div id="pmsDeviceInfo" class="right">
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


<script type="text/javascript">
    var pms_list_update = function (e, treeId, treeNode) {
        var callback = function () {
            $.fn.zTree.getZTreeObj(treeId).reAsyncChildNodes(treeNode.getParentNode(), "refresh");
        };
        if (treeNode.getParentNode()) {
            if (treeNode.getParentNode().level === 0) {
                Q($.ajax({
                    type: "GET",
                    url: "${ctx}/${appid}/pmsbdz/saveBdz",
                    data: {
                        id: (treeNode.id ? treeNode.id : ""),
                        name: treeNode.name
                    }
                })).fin(callback);
            } else if (treeNode.getParentNode().level === 1) {
                Q($.ajax({
                    type: "GET",
                    url: "${ctx}/${appid}/pmsbdz/saveSpace",
                    data: {
                        name: treeNode.name,
                        pId: treeNode.getParentNode().id,
                        id: (treeNode.id ? treeNode.id : "")
                    }
                })).fin(callback);
            } else if (treeNode.getParentNode().level === 2) {
                Q($.ajax({
                    type: "GET",
                    url: "${ctx}/${appid}/pmsbdz/saveDevice",
                    data: {
                        name: treeNode.name,
                        pId: treeNode.getParentNode().id,
                        bId: treeNode.getParentNode().bid,
                        id: (treeNode.id ? treeNode.id : "")
                    }
                })).fin(callback);
            }
        }
    };
    var pms_list_before_remove = function (treeId, treeNode) {
        if (treeNode.hierarchy == 2) {
            if (!treeNode.children) {
                return false;
            } else if (treeNode.children.length > 0) {
                $(treeNode).alertmsg('error', '不能删除非空间隔', {});
                return false;
            } else {
                return true
            }
        } else {
            return true;
        }
    };
    var pms_list_remove = function (e, treeId, treeNode) {
        var callback = function () {
            $.fn.zTree.getZTreeObj(treeId).reAsyncChildNodes(treeNode.getParentNode(), "refresh");
        };
        if (treeNode.getParentNode()) {
            if (treeNode.getParentNode().level === 1) {
                Q($.ajax({
                    type: "GET",
                    url: "${ctx}/${appid}/pmsbdz/deleteSpace",
                    data: {
                        pId: treeNode.getParentNode().id,
                        id: (treeNode.id ? treeNode.id : "")
                    }
                })).fin(callback);
            } else if (treeNode.getParentNode().level === 2) {
                Q($.ajax({
                    type: "GET",
                    url: "${ctx}/${appid}/pmsbdz/deleteDevice",
                    data: {
                        pId: treeNode.getParentNode().id,
                        bId: treeNode.getParentNode().bid,
                        id: (treeNode.id ? treeNode.id : "")
                    }
                })).fin(callback);
            }
        }
    };
    var pms_list_click = function (e, treeId, treeNode) {
        if (treeNode.level > 0 && !$(e.target).hasClass("tree_add")) {
            var zTree = $.fn.zTree.getZTreeObj("bdzTree");
            zTree.expandNode(treeNode);
            if (!treeNode.isParent) {
                $(this).bjuiajax("doLoad", {
                    target: $("#pmsDeviceInfo"),
                    url: "${ctx}/${appid}/pmsbdz/pmsDeviceInfo?pms_deviceId=" + treeNode.id,
                });
            } else if (treeNode.hierarchy === 1) {
                $(this).bjuiajax("doLoad", {
                    target: $("#pmsDeviceInfo"),
                    url: "${ctx}/${appid}/docmanage/doc1?projectId=" + treeNode.projectId,
                });
            }
        }
    };
    var show_pms_rename_btn = function (treeId, treeNode) {
        return treeNode.getParentNode();
    };
    var onAsyncSuccess = function (event, treeId, treeNode, msg) {
        if (treeId === "bdzTree") {
            if (treeNode && treeNode.id !== 0) {

            } else {
                var zTree = $.fn.zTree.getZTreeObj("bdzTree");
                zTree.expandNode(zTree.getNodeByTId("bdzTree_1"), true, false, false);
            }
        }
    };
    var zTreeOnNodeCreated = function (event, treeId, treeNode) {
        if (!treeNode.id && treeNode.parentTId) {
            pms_list_update(event, treeId, treeNode);
        }
    };
    var addHoverDom = function (treeId, treeNode) {
        var level2str = ["新增变电站", "新增间隔", "新增设备"];
        var zTree = $.fn.zTree.getZTreeObj(treeId);
        var level = treeNode.level;
        var $obj = $('#' + treeNode.tId + "_a");
        var $add = $('#diyBtn_add_' + treeNode.id);
        var $del = $('#diyBtn_del_' + treeNode.id);
        var showAdd = level > 0 && level < 3;
        var showDel = level > 1 && level <= 3;
        if (showAdd && !$add.length) {
            $add = $('<span class="tree_add" id="diyBtn_add_' + treeNode.id + '" title="添加"></span>');
            $add.appendTo($obj);
            $add.on('click', function () {
                zTree.addNodes(treeNode, {name: level2str[level]});
            })
        }
        if (showDel && !$del.length) {
            var $del = $('<span class="tree_del" id="diyBtn_del_' + treeNode.id + '" title="删除"></span>')
            $del.appendTo($obj)
                .on('click', function (event) {
                        var delFn = function () {
                            $del.alertmsg('confirm', '确认要删除 ' + treeNode.name + ' 吗？', {
                                okCall: function () {
                                    zTree.removeNode(treeNode)
                                    if (pms_list_remove) {
                                        var fn = pms_list_remove.toFunc()

                                        if (fn) fn.call(this, event, treeId, treeNode)
                                    }
                                },
                                cancelCall: function () {
                                    return
                                }
                            })
                        }

                        if (pms_list_before_remove) {
                            var fn = pms_list_before_remove.toFunc()

                            if (fn) {
                                var isdel = fn.call(fn, treeId, treeNode)

                                if (isdel && isdel == true) delFn()
                            }
                        } else {
                            delFn()
                        }
                    }
                )
        }
    };
    var removeHoverDom = function (treeId, treeNode) {
        var $add = $('#diyBtn_add_' + treeNode.id);
        var $del = $('#diyBtn_del_' + treeNode.id);

        if ($add && $add.length) {
            $add.off('click').remove()
        }

        if ($del && $del.length) {
            $del.off('click').remove()
        }
    };
</script>
