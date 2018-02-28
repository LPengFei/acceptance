//初始化表单控件

function initForm($form){
	$form = $form || $("form", $.CurrentNavtab||$.CurrentDialog);
	$("input:text", $form).each(function(){
		
		var $this = $(this), viewType = $this.data("toggle");
		if(viewType == 'textarea'){
			$this.replaceWith("<textarea name='"+this.name+"'>"+this.value+"</textarea>");
			
		}else if(viewType == 'password'){
			$this.prop("type", 'password');
			
		}else if(viewType == 'selectpicker'){//下拉选择
			var id = this.id || "", value = this.value;
			if(id) id = id+"_select";
			
			var liveSearch = $(this).data('live-search') || false;
			var width = $(this).data("width") || "210"
			
			var select = $("<select data-toggle='selectpicker' data-width='"+width+"' name='"+this.name+"' id='"+id+"' data-val='"+value+"' data-rule='"+$(this).data("rule")+"' data-live-search='"+liveSearch+"' ></select>");
			if($(this).attr("multiple")) select.attr("multiple","multiple");
			
			$this.replaceWith(select);
			ajaxSelect(select, $this.attr("data-ds"), value, $(this).data("select-empty-text"));
			
		}else if(viewType == 'selectztree'){//树形下拉选择
			
			var ztreeId = this.id+'_ztree', chkStyle = $this.data("chk-style")||"";
			$this.attr("data-tree", "#"+ztreeId).attr("readonly",true).attr("data-val",$this.val());
			$this.before("<input type='hidden' name='"+this.name+"' value='"+this.value+"' />");
			
			//修改input name
			var inputName = this.name, value = $this.val();
			if(inputName.indexOf(".") != -1){
				inputName = inputName.substring(inputName.indexOf(".") + 1);
			}
			$this.attr("name", inputName+"_text");
			
			var clickFn = inputName+'_zTreeNodeClick', checkFn = inputName+'_zTreeNodeCheck';
// 			console.debug(inputName+" click: "+clickFn+", check: "+checkFn);
			
			//设置默认的事件处理函数
			try {
				if(typeof(eval(clickFn))!="function") clickFn = 'zTreeNodeClick';
			} catch (e) {
				clickFn = 'zTreeNodeClick';
			}
			try {
				if(typeof(eval(checkFn))!="function") checkFn = 'zTreeNodeCheck';
			} catch (e) {
				checkFn = 'zTreeNodeCheck';
			}
// 			console.debug(inputName+" click: "+clickFn+", check: "+checkFn);
			
			var $tree = $('<ul id="'+ztreeId+'" class="ztree hide" data-toggle="ztree" data-expand-all="true" data-check-enable="true"' 
				+'data-setting=\'{check:{chkboxType:{"Y":"","N":""}}}\''
				+'data-on-check="'+checkFn+'" data-on-click="'+clickFn+'"></ul>');
			if(chkStyle == 'radio') {
				$tree.attr("data-radio-type","all").attr("data-chk-style", "radio");
			}
			$this.after($tree);
			
			//ajax获取树
			var data_source = $this.attr("data-ds");
			$this.val("");
			ajaxZTree(data_source, ztreeId, value);			
		}
		
	});
}

function ajaxSelect($select,url, defaulVal, emptyText){
	if (!$select || !url) {
		$select.change();
		return false;
	}
	//url = SERVER_CTX_KESS+"/"+url;

	$.get(url, function (json) {
		$select.empty();
		if (!json) return false;

		if (emptyText) $select.append("<option value=''>" + emptyText + "</option>");

		var count = 0
		$.each(json, function (i, obj) {
			count++;
			$select.append("<option value='" + obj.id + "'>" + (obj.text || obj.name || "") + "</option>")
		});
		$select.selectpicker("refresh");

		if (defaulVal) $select.selectpicker('val', defaulVal);
		$select.change();
	});
}

/**
 * ajax加载树
 */
function ajaxZTree(url,treeId, defaultVal){
	if(!treeId || !url) return false;
	//url = SERVER_CTX_KESS+"/"+url;
	
	$.get(url, function(json){
		if(!json) return false;
		
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		if(!treeObj) return false;
		
		//添加node
		var rootNode = treeObj.getNodes()[0];
		if(rootNode){
			treeObj.removeNode(rootNode);
		}
		treeObj.addNodes(null, json);
		
		//展开根目录
		expandRootNode(treeObj);
		
		//选中默认值
// 		console.debug(treeId + ","+defaultVal);
		setZtreeChecked(treeObj, defaultVal);
		
	});
	
}

/**
 * 设置ztree选中
 */
function setZtreeChecked(treeObj, val){
	if(!val || !treeObj) return false;
	
	var vals = val.toString().split(",");
	$.each(vals, function(index, val){
		var node = treeObj.getNodeByParam("id", val, null);
		if(node){
			treeObj.checkNode(node, true, true, true);
		}
	});
}

/**
 * 展开根目录
 */
function expandRootNode(tree){
	var treeObj = typeof(tree) == 'object' ? tree : $.fn.zTree.getZTreeObj(tree);
	var nodes = treeObj.getNodes();
	if(nodes && nodes[0]){
		treeObj.expandNode(nodes[0], true, false, true);
	}
}

/**
 * ztree 选中node回调函数
 * @param e
 * @param treeId
 * @param treeNode
 */
function zTreeNodeCheck(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId),
        nodes = zTree.getCheckedNodes(true);
    var ids = '', names = '';
    
    for (var i = 0; i < nodes.length; i++) {
        ids   += ','+ nodes[i].id;
        names += ','+ nodes[i].name;
    }
    
    if (ids.length > 0) {
        ids = ids.substr(1), names = names.substr(1);
    } 
    
    var $from = $('#'+ treeId).data('fromObj') || $("[data-tree='#"+treeId+"']");
    if ($from && $from.length) 
    { 
    	$from.val(names).data("idval", ids);
    	$from.prev().val(ids);
    	$from.attr("title", names);
    }
    
}

/**
 * ztree单击事件回调函数
 */
function zTreeNodeClick(event, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId);
    zTree.checkNode(treeNode, !treeNode.checked, true, true);
    event.preventDefault();
}

function getZtreeObj(treeId){
	return  $.fn.zTree.getZTreeObj(treeId);
}

function currentNavtab(element){
	return $(element, $.CurrentNavtab);
}