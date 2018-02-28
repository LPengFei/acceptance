<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%> 
<%@ page isELIgnored="false" %>
<%@ include file="../include/taglib.jsp" %>

<style>

#kfield-list-table td.yes button{
	color: green
}
#kfield-list-table td.no button{
	color: red
}

/* 表格输入框，selectpicker居中显示  */
#kfield-list-table td.center input, #kfield-list-table td span{
	text-align: center;
}


</style>

<div class="bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx }/kconf/field" method="post">
		<input type="hidden" name="pageNumber" value="${pageNumber}">
		<input type="hidden" name="pageSize" value="${pageSize}">
		<input type="hidden" name="orderField" value="${orderField}">
		<input type="hidden" name="orderDirection" value="${orderDirection}">
		<input type="hidden" name="mid" value="${mid}">
		<div class="bjui-searchBar ess-searchBar">
			<div><label>字段名称：</label><input type="text" value="${fieldname }" name="fieldname" size="10">&nbsp;</div>
			<div><label>字段别名：</label><input type="text" value="${fieldalias }" name="fieldalias" size="10">&nbsp; </div>
			<button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button>
			<button type="button"  data-url="${ctx }/kconf/field/edit?mid=${mid}" data-icon="plus" data-toggle="navtab" style="background: #14CAB4; color: white; float: right;" data-id="user-add" data-title="新增模型">新增</button>
		</div>
	</form>
</div>
 
 <div class="bjui-pageContent tableContent white ess-pageContent" id="fields_div">
 	<form data-toggle="validate" method="post">
		<table data-toggle="tabledit" id="kfield-list-table" class="table table-bordered table-hover table-striped table-top"
		  data-initnum="0" data-single-noindex="true" data-action="${ctx }/kconf/field/save?closeCurrent=false&nosetting=true" >
			  <thead>
	                <tr data-idname="f.mfid">
	                    
	                    <th>序号</th>
						<th title='名称'><input type='text' name='f.field_name' ></th>
						<th title='别名'><input type='text' name='f.field_alias' ></th>
						<th title='类型'><input type='text' name='f.type' ></th>
						<th title='必填' data-order-field="is_required">
							<select name='f.is_required' data-width="100%" data-toggle='selectpicker'>
								<option value="1" style="color:green">&radic;</option>
								<option value="0" style="color:red">&chi;</option>
							</select>
						</th>
						<th title='列表显示' data-order-field="is_list_view">
							<select name='f.is_list_view' data-width="100%" data-toggle='selectpicker'>
								<option value="1" style="color:green">&radic;</option>
								<option value="0" style="color:red">&chi;</option>
							</select></th>
						<th title='显示顺序' data-order-field="list_sort"><input type='text' name='f.list_sort'  data-toggle="spinner" data-step="5" ></th>
						<th title='表单输入' data-order-field="is_form_view">
							<select name='f.is_form_view' data-width="100%" data-toggle='selectpicker'>
								<option value="1" style="color:green">&radic;</option>
								<option value="0" style="color:red">&chi;</option>
							</select>
						</th>
						<th title='表单排序' data-order-field="form_sort"><input type='text' name='f.form_sort' data-toggle="spinner" data-step="5" ></th>
						<th>配置</th>
	                </tr>
	            </thead>
	            <tbody>
	            	<c:forEach items="${page.list }" var="r" varStatus="s">
	                <tr data-id="${r.mfid }" align="center">
	                    <td>${s.index + 1}</td>
	                    <td>${r.field_name }</td>
	                    <td>${r.field_alias }</td>
	                    <td>${r.type }</td>
	                    <td data-val="${r.is_required }" >
	                    	${r.is_required }
	                    </td>
	                    <td data-val="${r.is_list_view }">${r.is_list_view }</td>
	                    <td class="center">${r.list_sort }</td>
	                    <td data-val="${r.is_form_view }">${r.is_form_view }</td>
	                    <td class="center">${r.form_sort }</td>
						<td  style="padding:0px 5px;">
							<a href="javascript:;" class="green" class="editSaveBtn" data-toggle="doedit" style="display: none;">完成</a>
							<a href="${ctx }/kconf/field/delete?mfid=${r.mfid}" class="row-del" style="color:red;" data-confirm-msg="确定要删除该行信息吗？">删除</a>
							<a href="${ctx }/kconf/field/edit?mfid=${r.mfid}" style="color:green;" class="edit" data-toggle="navtab" data-id="app-edit" data-title="${r.field_name }编辑">编辑</a>
					    </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
</div>
<div class="bjui-pageFooter white ess-pageContent ">
    <div class="pages">
        <span>每页&nbsp;</span>
        <div class="selectPagesize">
            <select data-toggle="selectpicker" data-toggle-change="changepagesize">
                <option value="20">20</option>
                <option value="30">30</option>
                <option value="60">60</option>
                <option value="120">120</option>
                <option value="150">150</option>
            </select>
        </div>
        <span>&nbsp;条，共 ${page.totalRow } 条</span>
    </div>
    <div class="pagination-box" data-toggle="pagination" data-total="${page.totalRow }" data-page-size="${page.pageSize }" data-page-current="${page.pageNumber }" data-page-num="15">
    </div>
</div> 

<script type="text/javascript">
	function list_sort_update(that,id){
		$.ajax({
			type: "post",
			url: "${ctx }/kconf/field/list_sort_update?name="+$(that).attr("name")+"&value="+$(that).val()+"&mfid="+id,
			success: function(data){
				$(this).navtab({id:'fields-list', url:'${ctx }/kconf/field?mid='+data.mid,title:data.mname+'配置字段' , fresh:true});
			}
		});
	    return true
	}
	
	
	$.CurrentNavtab.on(BJUI.eventType.afterInitUI, function(e){
		//页面初始化之后，重新绑定 编辑按钮 点击事件
		$(e.target).find(".edit").click(function(){
			$(this).attr("data-toggle", "navtab");
		});
	});
	
	//双击编辑行，隐藏原编辑按钮
	$('#kfield-list-table tbody tr', $.CurrentNavtab).dblclick(function(){
		$(this).find('td:last [data-toggle="doedit"]').show().nextAll().hide();
	});
	
	//单击完成后，提交修改，显示原来的编辑按钮
	$("[data-toggle=doedit]", $.CurrentNavtab).on("click",function(){
		$(this).hide().nextAll().show();
		//刷新页面
		setTimeout(function() { currentNavtab("#pagerForm").submit() }, 100);
	});
	
	currentNavtab("table#kfield-list-table td[data-val]").each(function(){
		if($(this).data("val") == '1'){
			$(this).addClass("yes");
		}else{
			$(this).addClass("no");
		}
	});
	
</script>