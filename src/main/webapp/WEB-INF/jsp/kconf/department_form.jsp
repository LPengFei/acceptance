<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>  
<%@ include file="../include/taglib.jsp" %>
	<div class="bjui-pageContent">
	<div class="ess-form" style="border: none;">
		<form action="${ctx}/kconf/department/save"  data-toggle="validate" data-alertmsg="false">
			<input type="hidden" name="record.did" value="${record.did }" />
			<ul>
				<c:forEach items="${fields }" var="f" >
					<c:if test="${f.is_form_view == 1 }"> 
						<c:set var="fset" value="${f.settings.formview }"></c:set>
			            <c:set var="fname" value="${f.field_name }"></c:set>
			            <c:if test="${not empty fset.field_name }">
			                <c:set var="fname" value="${fset.field_name }" />
			            </c:if>
						 <li class="input_${fset.view_type }">
			                <label>${f.field_alias }</label>
			                <input type="text" name="record.${fname }" id="j_input_${fname }"
			                    value="${record[fname] }"
			                    placeholder="请输入${f.field_alias }"
			                    data-rule="${validate_type }"
			                    data-ds="${ctx}${f.data_source }"
			                    data-toggle="${fset.view_type }"
			                    data-chk-style="${fset.chk_style }"
			                    data-live-search='${fset.select_search }'
			                    <c:if test="${fset.is_form_readonly eq '1' }">readonly</c:if>
			                    <c:if test="${fset.select_multi }">multiple="multiple"</c:if>
			                    />
			            </li> 
					</c:if>
				</c:forEach> 
			</ul>
			<div class="clearfix"></div>
			<!-- 操作按钮 -->
			<div style="text-align:right; margin-top: -50px;">
				<button type="submit" class="btn" style="background: #14CAB4; color: white;">保存</button>
			</div>
		</form>
	</div>
</div> 
<script type="text/javascript">
initForm();
</script>