<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="../include/taglib.jsp" %>
<script type="text/javascript">
	//删除回调
	$('#tabledit1').on('afterdelete.bjui.tabledit', function(e) {
	    var $tbody = $(e.relatedTarget)
	    
//	    console.log('你删除了一条数据，还有['+ $tbody.find('> tr').length +']条数据！')
	});
	
	//页面初始化之后，重新绑定 按钮 点击事件
	$.CurrentNavtab.on(BJUI.eventType.afterInitUI, function(e){
		$(e.target).find("a[data-toggle-old]").each(function(){
			$(this).attr("data-toggle", $(this).attr("data-toggle-old"));
		});
	});
	
</script>
<div class="bjui-pageHeader white">
	<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/kconf/user/user" method="post">
		<input type="hidden" name="pageNumber" value="${query.pageNumber }" /> 
		<input type="hidden" name="pageSize" value="${query.pageSize }" /> 
		<input type="hidden" name="orderField" value="${query.orderField }" />
		<input type="hidden" name="orderDirection" value="${query.orderDirection}">
		<div class="bjui-searchBar ess-searchBar ">
			<div><label>用户名称：</label><input type="text" value="${query.uname }" name="uname" size="10">&nbsp;</div>
			<div><label>用户账号：</label><input type="text" value="${query.uaccount }" name="uaccount" size="10">&nbsp; </div>
			<button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button>
			<button type="button" style="background: #14CAB4; color: white; float: right;" data-toggle="tableditadd" data-target="#tabledit1" data-num="1" data-icon="plus">新增用户</button>
		</div>
	</form>
</div>
<div class="bjui-pageContent tableContent  white ess-pageContent">
	<form action="${ctx}/kconf/user/save" id="j_custom_form" class="pageForm" data-toggle="validate" method="post">
		<table id="tabledit1" class="table table-bordered table-hover table-striped table-top" data-width="100%" data-toggle="tabledit" data-initnum="0" data-add-location="first"  data-action="${ctx}/kconf/user/save?record.did=${dept.did}" data-single-noindex="true">
			<thead>
				<tr data-idname="record.uid" >
					<th title="序号" class="center">
						<input type="text" class="no"  value="1" size="2" disabled>
					</th>
					<c:forEach items="${fields }" var="f">
						<c:if test="${f.is_list_view ==1 }">
							<c:choose>
								<c:when test="${f.field_name eq 'rname' }">
									<th title="${f.field_alias }">
										<select data-toggle="selectpicker" name="role_id"  data-size="10">
											<c:forEach items="${roles }" var="role">
												<option value="${role.rid }">${role.rname }</option>
											</c:forEach>
										</select>
									</th>
								</c:when>
								<c:otherwise>
									<th title="${f.field_alias }"><input type="text" name="record.${f.field_name }"></th>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach>
					<th title="角色">
						<select data-toggle="selectpicker" name="role_id"  data-size="10">
							<c:forEach items="${roles }" var="role">
								<option value="${role.rid }">${role.rname }</option>
							</c:forEach>
						</select>
					</th>
					<th  title="操作" width="150"   data-addtool="true" style="padding-left:10px;">
					    &nbsp;&nbsp; 
					    <a href="javascript:;" class="green" data-toggle="dosave">保存</a> &nbsp;&nbsp; 
					    <a href="${ctx}/kconf/user/delete?uid=${r.uid}" class="red row-del" data-confirm-msg="确定要删除该行信息吗？">删除</a>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list }" var="r" varStatus="s">
					<tr data-id="${r.uid }">
						<td class="center" width="50px">${s.index+1 }</td>
						<c:forEach items="${fields }" var="f">  
							<c:if test="${f.is_list_view ==1 }">
								<c:choose> 
										<c:when test="${f.field_name eq 'rname' }">
											<td data-val="${r.rid }">${r.rname }</td>
										</c:when>
										<c:otherwise>
											<kval:val model="${r }" field="${f }" />
										</c:otherwise>
								</c:choose> 
							</c:if>
						</c:forEach>
						<td data-val="${r._userRole.rid}"></td>
						<td data-noedit="true"  >
						 	&nbsp;&nbsp; 
	 						<a  href="javascript:;" class="green" data-toggle="doedit" >编辑</a>&nbsp;&nbsp;
							<a class="red row-del" data-confirm-msg="确定要删除该行信息吗？" href="${ctx }/kconf/user/delete?uid=${r.uid}">删除</a> 
							<a class="red" data-toggle="doajax" data-confirm-msg="是否重置密码？" href="${ctx }/kconf/user/resetPwd/${r.uid}">重置密码</a> 
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
</div>
<div class="bjui-pageFooter" style="margin-bottom:1px;">
	<div class="pages">
		<span>每页&nbsp;</span>
		<div class="selectPagesize">
			<select data-toggle="selectpicker" data-toggle-change="changepagesize">
				<option value="30">30</option>
				<option value="60">60</option>
				<option value="120">120</option>
				<option value="150">150</option>
			</select>
		</div>
		<span>&nbsp;条，共 ${page.totalRow } 条</span>
	</div>
	<div class="pagination-box" data-toggle="pagination" data-total="${page.totalRow }" data-page-size="${page.pageSize }" data-page-current="${page.pageNumber }" data-page-num="15"></div>
</div>
