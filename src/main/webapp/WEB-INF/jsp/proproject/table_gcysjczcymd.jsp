<%--
  Created by IntelliJ IDEA.
  User: gaozhou
  Date: 2016/12/23
  Time: 上午11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>


<style type="text/css">
    .ess-form > .input-doc {
        width: 100%;
    }
</style>

<div class="bjui-pageHeader">
    <div class="bjui-searchBar ess-searchBar">
        <button type="button" style="background: #14CAB4; color: white; float: right;">
            <a href="${ctx}/${appid }/proproject/docPreview?projectId=${projectId}&id=${docFile.id}"
               target="_doc_preview" style="text-decoration: none; color: #fff;"><span>生成预览</span></a>
        </button>
    </div>
</div>


<div class="bjui-pageContent">
    <form data-toggle="validate" method="post">
        <div class="ess-form">
            <div class="bjui-doc input-doc">
                <%--<h3 class="page-header">示例：mytabledit， 我的可编辑表格演示。</h3>--%>
                <table class="table table-bordered table-hover table-striped " data-toggle="tabledit"
                       data-initnum="0" data-single-noindex="true"
                       data-action="${ctx}/${appid }/proproject/saveGroup?projectId=${projectId}&id=${docFile.id}&closeCurrent=false&nosetting=true"
                       data-callback="saveGroupCallback">
                    <thead>
                    <tr>
                        <th title="工程验收检查组">
                            <select name="group.key_" data-toggle="selectpicker">
                                <c:forEach var="r" items="${groupFields}" varStatus="s">
                                    <option value="${r.key_}">${r.name}</option>
                                </c:forEach>
                            </select>
                        </th>
                        <c:forEach var="r" items="${groupValues}" varStatus="s">
                            <c:if test="${r.type ne 'sign'}">
                                <th title="${r.name}">
                                    <input type="text" name="record.${r.key_}">
                                </th>
                            </c:if>
                        </c:forEach>
                        <th title="" data-addtool="true" width="100">
                            <a href="javascript:;" class="btn btn-green" data-toggle="dosave">保存</a>
                            <a href="javascript:;" class="btn btn-red row-del" data-confirm-msg="确定要删除该行信息吗？">删</a>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="v" varStatus="s">
                        <tr>
                            <input type="hidden" name="group.id" value="${v.group.id}">
                            <td data-val="${v.group.key_}"></td>
                                <%--<td>${v.xm}</td>--%>
                                <%--<td>${v.dw}</td>--%>
                                <%--<td>${v.zw}</td>--%>
                            <c:forEach var="r" items="${groupValues}" varStatus="s">
                                <c:if test="${r.type ne 'sign'}">
                                    <td>${v.get(r.key_)}</td>
                                </c:if>
                            </c:forEach>
                            <td data-noedit="true">
                                <button type="button" class="btn-green" data-toggle="doedit">编辑</button>
                                <a href="${ctx}/${appid }/proproject/deleteGroup?groupId=${v.group.id}"
                                   class="btn btn-red row-del" data-confirm-msg="确定要删除该行信息吗？">删</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="clearfix"></div>
        </div>
    </form>
</div>


<script>
    function saveGroupCallback() {
        $.CurrentNavtab.navtab("refresh");
    }
</script>