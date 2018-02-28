//初始化表单元素
initForm();

//设置第一项textarea换行显示
// $(".ess-form ul>li.input_textarea:first", $.CurrentNavtab).css("clear","both");

//工作部门字段名
currentNavtab("#j_input_work_dept_id").attr("name","record.work_dept");
//currentNavtab("textarea").css("height","100px");

//变电站线路切换
currentNavtab("#j_input_line_or_bdz_select").on("change",function(e){
	var lineOrBdz = e.target.value, deptid = currentNavtab("[name='record.dept_id']").val();
	//获取变电站、线路
	ajaxSelect(currentNavtab("[name='record.bdz_name']"), "/kess/combobox/ajaxBdzLine/"+deptid+"-"+lineOrBdz);
});

function dept_id_zTreeNodeCheck(e, treeId, treeNode){
	zTreeNodeCheck(e, treeId, treeNode);
	var fromObj = $('#'+ treeId).data('fromObj') || $("[data-tree='#"+treeId+"']");
	var deptid = fromObj.data("idval");
	
	//加载工作部门
	ajaxZTree("/kess/combobox/deptTree/"+deptid, 'j_input_work_dept_id_ztree', currentNavtab('#j_input_work_dept_id').data("val"));
	
	//获取变电站、线路
	var lineOrBdz = currentNavtab("[name='record.line_or_bdz']").val();
	ajaxSelect(currentNavtab("[name='record.bdz_name']"), "/kess/combobox/ajaxBdzLine/"+deptid+"-"+lineOrBdz);
}