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
            <a href="${ctx}/${appid }/proproject/issuesRemainPreview?projectId=${projectId}"
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
                       data-action="${ctx}/${appid}/proproject/saveIssueRemain?closeCurrent=false&nosetting=true"
                       data-callback="saveGroupCallback">
                    <thead>
                    <tr>
                        <th title="序号">
                        </th>
                        <th title="内容">
                        </th>
                        <th title="责任单位">
                            <input type="text" name="record.responsibility">
                        </th>
                        <th title="限期完成时间">
                            <input type="text" name="record.limittime" data-toggle="datepicker">
                        </th>
                        <th title="操作" data-addtool="false" width="100"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${resultIssues}" var="v" varStatus="s">
                        <tr>
                            <input type="hidden" name="record.id" value="${v.id}">
                            <td>${s.count}</td>
                            <td>${v.description}</td>
                            <td>${v.responsibility}</td>
                            <td>${v.limittime}</td>
                            <td data-noedit="true">
                                <button type="button" class="btn-green" data-toggle="doedit">编辑</button>
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