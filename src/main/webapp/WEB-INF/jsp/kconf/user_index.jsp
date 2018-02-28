<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%> 
<%@ page isELIgnored="false" %>
<%@ include file="../include/taglib.jsp" %>
<script type="text/javascript">
	function do_open_layout(event, treeId, treeNode) {
		var id = treeNode.id;
		if("undefined" == typeof id){
			id = "";
		}
		$(event.target).bjuiajax('doLoad', {url:"${ctx }/kconf/department/edit?dept_id=" + id + "&parent_id=" + treeNode.pId, target:"#layout-01"}) 
	    $(event.target).bjuiajax('doLoad', {url:"${ctx }/kconf/user/user?deptid=" + id, target:"#layout-02"}) 
	    
	    event.preventDefault();
		
	}
	
	function ztree_returnjson() {
     	return ${ztreedata};
    }
	
	//删除部门
	function M_BeforeRemove(treeId, treeNode) {
		$.ajax({
			type: "post",
			url: "${ctx }/kconf/department/delete?dept_id="+treeNode.id,
			success: function(data){
				var treeObj = $.fn.zTree.getZTreeObj(treeId);
				if (treeNode.id != null) {
					treeNode.id = data.pid;
					treeObj.updateNode(treeNode);
				}
				do_open_layout(event, treeId, treeNode);
			}
		});
	    return true
	}
	
	//修改部门名称 
	function setRename(treeId, treeNode, newName, isCancel) {
		var id = treeNode.id;
		var pid = treeNode.pId;
		if("undefined" == typeof id){
			id = "";
		}
		$.ajax({
			type: "post",
			url: "${ctx }/kconf/department/save?record.did="+id+"&record.dname="+newName+"&record.pid="+pid, 
			success: function(data){
				var treeObj = $.fn.zTree.getZTreeObj(treeId);
				if (treeNode.id == null || treeNode.id == '') {
					treeNode.id = data.did;
					treeObj.updateNode(treeNode);
				}
				do_open_layout(event, treeId, treeNode);
			}
		});
	    return true
	}
	
</script>
<style>
	.ztree li a.curSelectedNode {
	    background-color: #d9e7f2;
	    color: black;
	    border: 0px;
	    opacity: 1;
	}
</style>
<div class="bjui-pageContent white" >
    <div style="float:left; width:300px;height:100%;  overflow:auto;" class="fixedtableScroller">
<!--      border:1px solid #c3ced5; border-radius:5px; height:99%; margin-top:7px; -->
        <fieldset style="width:300px; height:98%; ">
        	<legend style="display: block;">部门树</legend>
	        <ul id="tree" class="ztree" data-toggle="ztree" data-expand-all="false" data-on-click="do_open_layout" data-edit-enable="true" data-add-hover-dom="edit" data-show-rename-btn="true" data-before-remove="M_BeforeRemove" data-remove-hover-dom="edit" data-on-remove="M_NodeRemove"
	        data-max-add-level="6" data-options="{nodes:'ztree_returnjson'}" data-setting="{callback: {beforeRename: setRename}}">
	        </ul>
        </fieldset>
    </div>
    <div style="margin-left:210px;margin-right:10px; height:99.9%; overflow:hidden; padding-left: 10px; ">
        <div style="height:20%; overflow:hidden;">
            <fieldset style="height:100%;">
                <legend style="display: block;">部门信息</legend>
                <div id="layout-01" style="height:94%; overflow:hidden;">
                </div>
            </fieldset>
        </div>
        <div style="height:80%; overflow:hidden; padding-top: 22px;">
            <fieldset style="height:97.6%;">
                <legend style="display: block;">人员信息</legend>
                <div id="layout-02" style="height:98%; overflow:hidden; ">
                </div>
            </fieldset>
        </div>
    </div>
</div>
