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
			
			<!-- 角色菜单配置 -->
			<c:forEach items="${menuList }" var="menu">
				<div style="margin:5px 0px; font-size:12px; border-top:1px solid #ccc; clear: both;">
					<span style="font-weight: bold;line-height:40px;">菜单-${menu.mname } </span>
					<ul>
					 	<c:forEach items="${menu.getChildMenu()}" var="child">
							<li style="width:150px;margin-right:15px; line-height:30px;height:auto;">
								<c:set var="mid" value=",${child.menuid},"></c:set> 
								<input style="width:12px;float:left;vertical-align: middle;margin-top:-1px; margin-right: 4px;" type="checkbox" name="menuid" value="${child.menuid}" <c:if test="${fn:contains(menuids,mid)}"> checked="checked"</c:if>  />
								<c:if test="${empty child.mname }">${child.murl }</c:if>
								<c:if test="${not empty child.mname }">${child.mname }</c:if>
							</li>	
						</c:forEach>
					</ul>
				</div>
			</c:forEach>
			
		  	<!-- 操作按钮 -->
			<div style="text-align:right; padding-top: 20px;"  >
				<button type="submit" class="btn" style="background: #14CAB4; color: white;">保存</button>
				<button type="button" class="btn-close" style="background: red; color: white;">取消</button>
			</div>
		</form>
	</div>
</div>
