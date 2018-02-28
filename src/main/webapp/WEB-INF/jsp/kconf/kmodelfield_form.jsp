<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%> 
<%@ page isELIgnored="false" %>
<%@ include file="../include/taglib.jsp" %>

<form action="${ctx }/kconf/field/save" data-toggle="validate" data-alertmsg="false">
	<div class="bjui-pageContent">
		<div class="ess-form">
			<input type="hidden" name="f.mid" value="${record.mid }">
			<input type="hidden" name="f.mfid" value="${record.mfid }">
			<input type="hidden" name="f.enabled" value="0">
			<fieldset>
				<legend style="display: block;">基础配置</legend> 
				<ul>
					<li>
						<label>字段名称</label>
						<input type="text" name="f.field_name" id="j_field_name" value="${record.field_name }" data-rule="required" placeholder="请输入字段名称">
					</li>
					<li>
						<label>字段别名</label>
						<input type="text" name="f.field_alias" id="j_field_alias" value="${record.field_alias }" data-rule="required" placeholder="请输入字段别名">
					</li>
					<li>
						<label>字段类型</label>
						<input type="text" name="f.type" id="j_type" value="${record.type }" data-rule="required" placeholder="请输入字段类型">
					</li>
				 	 
				 	<li>
						<label>字段备注</label>
					 	<input type="text" name="f.remark" id="j_remark" value="${record.remark }" placeholder="请输入备注">
				 	</li>
				</ul>
			</fieldset>
			
			
			 <fieldset>
			 	<legend style="display: block;">列表配置</legend>
			 	<ul>
					<%-- --%>
					<li>
						<label>列表显示</label>
						<select data-toggle="selectpicker" data-width="200" name="f.is_list_view"  data-size="10" data-val="${record.is_list_view }">
	                    	<option value="1" selected="selected">显示</option>
							<option value="0" <c:if test="${record.is_list_view == 0 }"> selected="selected" </c:if> >不显示</option>
	                    </select>
					</li>
					
					 
					<%-- 
					<li>
						<label>显示顺序</label>
						<input type="text" name="f.list_sort" id="j_custom_listsort" value="${record.list_sort}" data-toggle="spinner" data-min="0" data-max="100" data-step="1" data-rule="integer">
					</li>
					 --%>
					
					<li>
						<label>列表排序</label>
		                <select data-toggle="selectpicker" data-width="200" name="list.is_list_sort"  data-size="10">
	                    	<option value="1" selected="selected">允许排序</option>
							<option value="0"   <c:if test="${record.is_list_sort == 0 }"> selected="selected" </c:if>>不允许排序</option>
	                    </select>
					</li>
					
					
					
					<li>
						<label>对齐方式</label>
		                <select data-toggle="selectpicker" data-width="200" name="list.list_align"  data-size="10">
	                    	<option value="left" selected="selected" >居左</option>
	                    	<option value="center" <c:if test="${record.list_align =='center' }"> selected="selected" </c:if>>居中</option>
	                    	<option value="right" <c:if test="${record.list_align == 'right' }"> selected="selected" </c:if>>居右</option>
	                    </select>
					</li>
					
					<li>
						<label>列表宽度(px)</label>
						<input type="text" name="list.list_width" placeholder="如：100">
					</li>
					
					<li>
						<label>列表样式</label>
						<input type="text" name="list.list_style" placeholder="如：padding-left:10px;" >
					</li>
					<li>
						<label>列表检索条件</label>
						<input type="text" name="list.list_search_op" placeholder="检索条件: =,>,<,like,!=,>=,<=" >
					</li>
					<li style="width: 608px;">
						<label>关联SQL</label>
						<input type="text" name="list.list_sql" style="width:100%;" placeholder="如: select dname from dept where deptid=?" >
					</li>
				</ul>
			</fieldset>
			
			
			<fieldset>
				<legend style="display: block;">表单配置</legend> 
				<ul>
					 <li>
						<label>输入方式</label>
						<select data-toggle="selectpicker" data-width="200" name="form.view_type"  data-size="10">
							<option value="text">普通文本框-text</option>
							<option value="password">密码输入框-password</option>
							<option value="textarea">多行文本域-textarea</option>
							<option value="datepicker">日期选择器-datepicker</option>
							<option value="selectpicker">下拉选择框-selectpicker</option>
							<option value="selectztree">树形下拉框-selectztree</option>
							<option value="lookup">查找带回-lookup</option>
						</select>
					 </li>
					 <li>
						<label>表单显示</label>
	                    <select data-toggle="selectpicker" data-width="200" name="f.is_form_view"  data-size="10" data-val="${record.is_form_view }">
	                    	<option value="1"  <c:if test="${record.is_form_view == 1 }"> selected="selected" </c:if>>显示</option>
							<option value="0" selected="selected">不显示</option>
	                    </select>
					 </li>
					 
					 <li>
						<label>是否只读</label>
	                     <select data-toggle="selectpicker" data-width="200" name="form.is_form_readonly"  data-size="10">
	                    	<option value="1"  <c:if test="${record.is_form_readonly == 1 }"> selected="selected" </c:if>>只读字段</option>
							<option value="0" selected="selected">非只读字段</option>
	                    </select>
					 </li>

					<%--  <li>
						<label>表单排序</label>
					 	<input type="text" name="f.form_sort" id="j_form_sort" value="${record.form_sort }" data-toggle="spinner" data-min="0" data-max="100" data-step="1" data-rule="integer">
					 </li> --%>

					 <%-- <li>
						<label>是否验证</label>
					 	<input type="radio" name="form.is_validate" id="j_is_validate" data-toggle="icheck" value="1"  data-label="是&nbsp;&nbsp;" <c:if test="${record.is_validate == 1 }"> checked="checked" </c:if>>
	                    <input type="radio" name="form.is_validate" id="j_is_validate" data-toggle="icheck" value="0" data-label="否" <c:if test="${record.is_validate == 0 }"> checked="checked" </c:if>>
					 </li> --%>

					 <li>
						<label>验证类型</label>
						<select data-toggle="selectpicker" data-width="200" name="form.validate_type"  data-size="10">
							<option value="">不验证</option>
							<option value="text">文本</option>
							<option value="number">数字</option>
							<option value="date">日期</option>
							<option value="require">必填</option>
							<option value="password">密码</option>
						</select>
					 </li>

					 <%-- <li>
						<label>是否必填</label>
					 	<input type="radio" name="f.is_required" id="j_custom_isrequired1" data-toggle="icheck" value="1"  data-label="是&nbsp;&nbsp;" <c:if test="${record.is_required == 1 }"> checked="checked" </c:if>>
	                    <input type="radio" name="f.is_required" id="j_custom_isrequired2" data-toggle="icheck" value="0" data-label="否" <c:if test="${record.is_required == 0 }"> checked="checked" </c:if>>
					 </li> --%>

					 <li>
						<label>长度验证</label>
					 	<input type="text" name="form.length_validate" id="j_length_validate" placeholder="最小值" size="6">
					 	-
						<input type="text" name="form.length_validate" id="j_length_validate" placeholder="最大值" size="6">
					 </li>

					 <!-- <li>
						<label>表单样式</label>
					 	<input type="text" name="form.from_style" placeholder="请输入表单样式">
					 </li> -->
					
					<li>
						<label>树形是否多选</label>
						<select data-toggle="selectpicker" data-width="200" name="form.chk_style"  data-size="10">
	                    	<option value="multiple"  <c:if test="${record.chk_style == 'multiple' }"> selected="selected" </c:if>>多选</option>
							<option value="radio" selected="selected">单选</option>
	                    </select>
					</li>
					<li>
						<label>下拉选择检索</label>
						<select data-toggle="selectpicker" data-width="200" name="form.select_search"  data-size="10">
	                    	<option value="true"  <c:if test="${record.select_search == 'true' }"> selected="selected" </c:if>>检索</option>
							<option value="false" selected="selected">不检索</option>
	                    </select>
					</li>
					<li>
						<label>下拉选择多选</label>
						<select data-toggle="selectpicker" data-width="200" name="form.select_multi"  data-size="10">
	                    	<option value="true"  <c:if test="${record.select_multi == 'true' }"> selected="selected" </c:if>>多选</option>
							<option value="false" selected="selected">单选</option>
	                    </select>
					</li>
					<li>
						<label>数据源</label>
						<select data-toggle="selectpicker" data-width="200" name="f.data_source"  data-size="10">
							<option value="">---请选择---</option>
							<c:forEach items="${dsList }" var="ds">
	                    	<option value="${ds.dataurl }"  <c:if test="${record.data_source == ds.dataurl }"> selected="selected" </c:if>>${ds.dsname }</option>
							</c:forEach>
	                    </select>
					</li>
					<li>
						<label>查找带回url</label>
						<input type="text" name="form.lookup_url" placeholder="请输入查找带回url,如/kconf/lookup" >
					</li>
				 </ul>
			 </fieldset>
			
			<!-- 操作按钮 -->
			<div style="text-align:right;">
				<button type="submit" class="btn" style="background: #14CAB4; color: white;">保存</button>
				&nbsp;&nbsp;
				<button type="button" class="btn btn-close" style="background: red; color: white;">取消</button>
			</div>
			<div class="clearfix">
			</div>
		</div>
	</div>
</form>
<script type="text/javascript">
// 	$(function(){
		$(".ess-form li", $.CurrentDialog).css("height","52px");
		$(".ess-form select[data-val]", $.CurrentDialog).val(function(){
			return $(this).data("val");
		});
// 		遍历json，给对应的文本赋值
		var temp_contentJson = ${record.settings};
		if(temp_contentJson){
			if(temp_contentJson.listview){
				$.each(temp_contentJson.listview, function(key,value){
					$("input:text[name='list."+key+"']", $.CurrentDialog).val(value);
// 					$("input:radio[name='list."+key+"'][value='"+value+"']", $.CurrentDialog).prop("checked",true);
					$("select[name='list."+key+"']", $.CurrentDialog).val(value);  
				});
			}
			
			if(temp_contentJson.formview){
				$.each(temp_contentJson.formview, function(key,value){
					$("input:text[name='form."+key+"']", $.CurrentDialog).val(value);
					$("select[name='form."+key+"']", $.CurrentDialog).val(value);  
// 					$("input:radio[name='form."+key+"'][value='"+value+"']", $.CurrentDialog).prop("checked",true);
				});
			}
		}
		
// 	});
</script>

 