var utils = {
	//butotn切换效果
	buttonswitch: function(_this) {
		if (!$(_this).hasClass("active")) {
			$(_this).addClass("active").siblings().removeClass("active");
		}
	},
	//左侧主菜单展开和收缩效果
	MenuSilbing: function(_this) {
		if ($(_this).parent("li").hasClass("active")) {
			$(_this).parent("li").find("ul").hide();
			$(_this).parent("li").removeClass("active");
			$(_this).parent("li").css("height", "45px");
		} else {
			$(_this).parent("li").find("ul").slideDown();
			$(_this).parent("li").addClass("active").siblings().find("ul").hide();
			$(_this).parent("li").siblings().removeClass("active");
			$(_this).parent("li").css("height", "auto");
			$(_this).parent("li").siblings().css("height", "45px");
		}
	},
	//选择日期
	selectDate: function(_this) {
		$(_this).focus(function() {
			WdatePicker({
				dateFmt: 'yyyy-MM-dd'
			});
		});
	},
	selectTime: function(_this) {
		$(_this).focus(function() {
			WdatePicker({
				dateFmt: 'yyyy-MM-dd HH:mm:ss'
			});
		});
	},
	
	//背景填充
	backgroundFull: function() {
		if ($(".container-fluid").height() < $(window).height() - 80) {
			$(".container-fluid").height($(window).height() - 80);
		}
	},
	//自定义checkbox选中样式
	checkboxstyle: function(_this) {
		if ($(_this).is(":checked")) {
			if (!$(_this).prev().hasClass("active")) {
				$(_this).prev().addClass("active");
			}
		} else {
			if ($(_this).prev().hasClass("active")) {
				$(_this).prev().removeClass("active");
			}
		}
	}

}

$(function() {
	utils.backgroundFull();
	$(".menu>ul>li>a.sub").click(function() {
		utils.MenuSilbing($(this));
	});
	$(".menu>ul>li>ul>li").click(function() {
		utils.buttonswitch($(this));
	});

	//调用时间插件
	utils.selectDate($(".time"));
	utils.selectTime($(".datetime"));

	$(".push_box_line p input[type=checkbox]").click(function() {
		utils.checkboxstyle($(this));
	})
	
//	//可用区域高度（除去title，main_tab_nav）
//	window.clientHeight = $(window).height() - $(".top").outerHeight() - $(".main_header").outerHeight();
	
	resizeClickSwitchBtn();
	$(window).resize(function(){
		resizeClickSwitchBtn();
	});

});


/**
 * 弹出modal框
 */
function ajaxDialog(_this, opts) {
	var $modal = $('#ajaxModal'),
		$this = $(_this);
	opts = opts || {};

	$.extend(opts, {
		width: $this.attr("width") || 600,
	});

	if ($modal.length == 0) {
		$modal = $('<div class="modal fade" id="ajaxModal"></div>').appendTo('body');
	}

	//ajax加载modal界面
	$modal.load(_this.href, '', function() {
		initUI($modal);
		$modal.modal(opts);
	});

	return false;
}

function initUI($p) {
	//点击弹出模态框
	$('.dialog', $p).on('click', function() {
		return ajaxDialog(this);
	});
}

/**
 * 表单ajax提交
 */
function formAjaxSubmit(form, success) {
	var $form = $(form);

	//进行表单验证
	if ($form.is(".valid, .validate")) {
		if (!$form.valid()) {
			return false;
		}
	}

	$.ajax({
		type: "post",
		url: $form[0].action,
		data: $form.serializeArray(),
		dataType: "json",
		contentType: "application/x-www-form-urlencoded;charset=UTF-8",
		success: success || successOrError,
		error: function() {
			util.error("操作失败");
		}
	});
	return false;
}

/**
 * 提交搜索表单
 * @param obj
 */
function submitSearchForm(obj) {

	$("[name=_export]").val("");

	submitForm(obj);
}

/**
 * 提交obj所在表单
 * @param obj
 */
function submitForm(obj) {

	$(obj).parents().filter("form").first().submit();
}


/**
 * 判断用户屏幕的大小是否大于1366px
 */
function resizeClickSwitchBtn(){
	if($(window).width()>1367){
		$(".btn_expend").trigger('click');
		$("#tools_table_height").height(380);
	}else{
	    $(".btn_collapse").trigger('click');
	    $("#tools_table_height").height(200);
	}
}

/*****动态控制表格的高度*****/
$(".table").attr("height",$(window).height()-320);


/**
 * 使用ztree
 */
function usezTree(use_url,use_idKey,use_pIdKey,use_ztreeId,receive_id,receive_name){
	var setting = {
		check: {
			enable: true,
			chkStyle: "radio",
			radioType: "all"
		},
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pid",
				rootPId: ""
			}
		},
		callback: {
			onClick: onClick,
			onCheck: onCheck
		}
	};

	var zNodes;
	$(function(){
		$.ajax({
			type: "post",
			url: use_url,
			success: function(data){
				zNodes = data;
				zNodes[0].open = true; //展开第一项
				var t = $("#"+use_ztreeId+"");
				zTree = $.fn.zTree.init(t, setting, zNodes);
				
				var selected = false;
				//根据cookie中记录的id, 选中tree
				if($.cookie){
					var deptId = $.cookie("c_dept_tree_id2");
					if(deptId){
						var treeNode = zTree.getNodesByParam('id', deptId);
						if(treeNode && treeNode.length > 0){
							zTree.selectNode(treeNode[0]);
							$("#"+treeNode[0].tId+"_span").click();
							selected = true;
						}
					}
				}
			}
		});   
	});
	
	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj(use_ztreeId);
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}

	function onCheck(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj(use_ztreeId),
		nodes = zTree.getCheckedNodes(true),
		v = "";
		var tool_deptid = "";
		
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";
			tool_deptid += nodes[i].id + ",";
		}
		if (v.length > 0 ) v = v.substring(0, v.length-1);
		if (tool_deptid.length > 0 ) tool_deptid = tool_deptid.substring(0, tool_deptid.length-1);
		var cityObj = $("#"+receive_name+"");
		cityObj.attr("value", v);
		$(receive_id).attr("value", tool_deptid);
	}
}









