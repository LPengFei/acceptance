<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>  
<%@ include file="../include/taglib.jsp" %>
	<div class="bjui-pageContent">
	<div class="ess-form">
		<form action="${ctx}/kconf/role/save"  data-toggle="validate" data-alertmsg="false">
			<input type="hidden" name="record.rid" value="${record.rid }">
			<ul>
	            <li class="input_selectpicker">
	                <label>角色名称</label>
	                <input type="text" name="record.rname" id="j_input_rname" value="${record.rname }" placeholder="请输入KEY">
	            </li> 
	            <li class="input_text">
	                <label>备注</label>
	                <input type="text" name="record.remark" id="j_input_remark" value="${record.remark }" placeholder="请输入KEY" >
	            </li> 
        	</ul>
			<div class="clearfix"></div>
				<c:forEach items="${authorityList }" var="authority">
					<div style="width:100%; font-size:12px; border-top:1px solid #ccc;padding-top:10px;">
					<p style="font-weight:bold">${authority.aurl } <input type="hidden" name="authorityid" value="${authority.aid }"/></p>
					<ul style="margin-top: 20px;">
						<c:forEach items="${authority.getChildAuthority() }" var="parent">
							<li style="width:200px;margin-right:15px; line-height:30px;">
								<c:set var="aid" value=",${parent.aid},"></c:set> 
								<input  style="width:12px;float:left;vertical-align: middle;margin-top:-1px; margin-right: 4px;"  type="checkbox" name="authorityid" value="${parent.aid }" <c:if test="${fn:contains(authorityids,aid)}"> checked="checked"</c:if>  />
								<c:if test="${empty parent.aname }">${parent.aurl }</c:if>
								<c:if test="${not empty parent.aname }">${parent.aname }</c:if>
							</li>
						</c:forEach>
					</ul>
					<div class="clearfix"></div>
					</div>
				</c:forEach>
		  	<!-- 操作按钮 -->
			<div style="text-align:right; padding-top: 20px;">
				<button type="submit" class="btn" style="background: #14CAB4; color: white;">保存</button>
				<button type="button" class="btn-close" style="background: red; color: white;">取消</button>
			</div>
		</form>
	</div>
</div>
