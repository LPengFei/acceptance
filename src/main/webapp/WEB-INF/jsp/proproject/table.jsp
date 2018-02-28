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
        width: 80.8%;
    }
</style>


<div class="bjui-pageContent">
    <div class="ess-form">
        <div class="jgyys-btn pull-right">
            <a href="${ctx}/${appid }/proproject/docPreview?projectId=${projectId}&id=${docFile.id}" target="_doc_preview"><span>生成预览</span></a>
        </div>
        <div class="bjui-doc input-doc" >
            <h3 class="page-header">示例：mytabledit， 我的可编辑表格演示。</h3>
            <table class="table table-bordered table-hover table-striped table-top" data-toggle="tabledit">
                <thead>
                <tr>
                    <th title="文本框"><input type="text" name="test[#index#].a1" placeholder="文本框"></th>
                    <th title="复选框">
                        <input type="checkbox" name="test[#index#].a2" id="doc-test-a2-1[#index#]" data-toggle="icheck" value="k1" data-label="选项一">
                        <input type="checkbox" name="test[#index#].a2" id="doc-test-a2-2[#index#]" data-toggle="icheck" value="k2" data-label="选项二">
                    </th>
                    <th title="下拉菜单">
                        <select name="test[#index#].a3" data-toggle="selectpicker">
                            <option value="a">甲</option>
                            <option value="b">乙</option>
                            <option value="c">丙</option>
                        </select>
                    </th>
                    <th title="功能按钮"><button type="button" class="btn btn-default" data-toggle="dialog" data-url="doc/table/test.html" data-id="dialog-test" data-title="我的测试页面">打开测试</button></th>
                    <th title="" data-addtool="true" width="100">
                        <a href="ajaxDone2.html" class="btn btn-red row-del" data-confirm-msg="确定要删除该行信息吗？">删</a>
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>AAA</td>
                    <td data-val="k2"></td>
                    <td data-val="c"></td>
                    <td data-notread="true">--</td>
                    <td>--</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="clearfix"></div>
        <%--<div style="text-align:center; padding-top: 20px;">--%>
        <%--<button class="btn" style="background: #14CAB4; color: white;">保存</button>--%>
        <%--</div>--%>
    </div>
</div>