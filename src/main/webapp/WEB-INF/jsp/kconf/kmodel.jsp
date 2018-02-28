<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%> 
<%@ page isELIgnored="false" %>
<%@ include file="../include/taglib.jsp" %>

<div class="bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx }/kconf/model" method="post">
		<input type="hidden" name="pageNumber" value="${pageNumber}">
		<input type="hidden" name="pageSize" value="${pageSize}">
		<input type="hidden" name="orderField" value="${orderField}">
		<input type="hidden" name="orderDirection" value="${orderDirection}">
		<div class="bjui-searchBar ess-searchBar">
			<div><label>模型名称：</label><input type="text" value="${mname }" name="mname" size="10">&nbsp;</div>
			<div><label>对应数据表：</label><input type="text" value="${mtable }" name="mtable" size="10">&nbsp; </div>
			<button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button>
			<button type="button"  data-url="${ctx}/kconf/model/edit" data-icon="plus" data-toggle="navtab" style="background: #14CAB4; color: white; float: right;" data-id="role-create" data-title="新增模型">新增</button>
		</div>
	</form>
</div>
 
 <div class="bjui-pageContent tableContent white ess-pageContent">
	<table data-toggle="tablefixed"   >
		 <thead>
			<tr>
			    <th width="50px;" class="center">序号</th>
			    <th>模型名称</th>
			    <th>对应数据表</th>
			    <th>备注</th>
			    <th>配置</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="r" varStatus="s">
				<tr>
				    <td width="50px;" class="center">${s.index + 1 }</td>
					<td>${r.mname }</td>
					<td>${r.mtable }</td>
					<td>${r.remark }</td>
					<td  style="padding:0px 10px;">
						<a href="${ctx }/kconf/model/delete?mid=${r.mid}" style="color:red;"  data-toggle="doajax" data-confirm-msg="确定要删除该行信息吗？">删除</a>&nbsp;
						<a href="${ctx}/kconf/model/edit?mid=${r.mid}" style="color:green;"  data-toggle="navtab" data-id="app-edit" data-title="${r.mname }编辑"  >编辑</a> &nbsp;
						<a href="${ctx}/kconf/model/copy/${r.mid}" data-toggle="doajax" data-confirm-msg="复制模型配置及字段配置">复制</a>&nbsp;
						<a href="${ctx}/kconf/field?mid=${r.mid}" data-toggle="navtab" data-id="fields-list" data-title="${r.mname}配置字段">配置字段</a>
				    </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
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