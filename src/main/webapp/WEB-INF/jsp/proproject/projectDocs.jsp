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
        <div class="panel-heading">
            <h3 class="panel-title">
                <button class="list" disabled="disabled">
                    <span class="glyphicon glyphicon-th-large"></span>
                </button>
                工程规模及主要技术经济指标
            </h3>
        </div>
        <div class="ess-form">
            <h1>
                <a class="panel-title"
                   href="${ctx}/${appid }/proproject/docFile?projectId=${projectId}&key=gcgmjzyjsjjzb"
                   data-toggle="navtab"
                   data-id="doc-list"
                   data-title="工程规模及主要技术经济指标">
                    工程规模及主要技术经济指标
                </a>
            </h1>
            <div class="clearfix"></div>
        </div>

        <div class="panel-heading">
            <h3 class="panel-title">
                <button class="list" disabled="disabled">
                    <span class="glyphicon glyphicon-th-large"></span>
                </button>
                工程启动验收委员会鉴定书
            </h3>
        </div>
        <div class="ess-form">
            <h1>
                <a class="panel-title"
                   href="${ctx}/${appid }/proproject/docFile?projectId=${projectId}&key=gcqdyswyhjds"
                   data-toggle="navtab"
                   data-id="doc-list"
                   data-title="工程启动验收委员会鉴定书">
                    工程启动验收委员会鉴定书
                </a>
            </h1>
            <hr/>
            <ul>
                <li>
                    <a href="${ctx}/${appid }/proproject/docFile?projectId=${projectId}&key=gcqdyswyhwymd"
                       data-toggle="navtab"
                       data-id="doc-list"
                       data-title="工程启动验收委员会委员名单">
                        工程启动验收委员会委员名单
                    </a>
                </li>
                <li>
                    <a href="${ctx}/${appid }/proproject/docFile?projectId=${projectId}&key=gcqdsyzhzcymd"
                       data-toggle="navtab"
                       data-id="doc-list"
                       data-title="工程启动试运指挥组成员名单">
                        工程启动试运指挥组成员名单
                    </a>
                </li>
                <li>
                    <a href="${ctx}/${appid }/proproject/docFile?projectId=${projectId}&key=gcysjczcymd"
                       data-toggle="navtab"
                       data-id="doc-list"
                       data-title="工程验收检查组成员名单">
                        工程验收检查组成员名单
                    </a>
                </li>
                <li>
                    <a href="${ctx}/${appid }/proproject/issuesRemain?projectId=${projectId}&key=gcylwtclqd"
                       data-toggle="navtab"
                       data-id="doc-list"
                       data-title="工程遗留问题处理清单">
                        工程遗留问题处理清单
                    </a>
                </li>
                <li>
                    <a href="${ctx}/${appid }/proproject/docFile?projectId=${projectId}&key=gcjsygdwdbmd"
                       data-toggle="navtab"
                       data-id="doc-list"
                       data-title="工程建设有关单位代表名单">
                        工程建设有关单位代表名单
                    </a>
                </li>
            </ul>
            <div class="clearfix"></div>
        </div>

        <div class="panel-heading">
            <h3 class="panel-title">
                <button class="list" disabled="disabled">
                    <span class="glyphicon glyphicon-th-large"></span>
                </button>
                工程移交生产交接书
            </h3>
        </div>
        <div class="ess-form">
            <h1>
                <a class="panel-title"
                   href="${ctx}/${appid }/proproject/docFile?projectId=${projectId}&key=bdyjscjjs"
                   data-toggle="navtab"
                   data-id="doc-list"
                   data-title="工程移交生产交接书">
                    工程移交生产交接书
                </a>
            </h1>
            <hr/>
            <ul>
                <li>
                    <a href="${ctx}/${appid }/proproject/docFile?projectId=${projectId}&key=gcyjygdwdbmd"
                       data-toggle="navtab"
                       data-id="doc-list"
                       data-title="工程移交有关单位代表名单">
                        工程移交有关单位代表名单
                    </a>
                </li>
                <li>
                    <a href="${ctx}/${appid }/proproject/docFile?projectId=${projectId}&key=yjgcfw"
                       data-toggle="navtab"
                       data-id="doc-list"
                       data-title="移交工程范围">
                        移交工程范围
                    </a>
                </li>
                <li>
                    <a href="${ctx}/${appid }/proproject/docFile?projectId=${projectId}&key=yjzygqjqd"
                       data-toggle="navtab"
                       data-id="doc-list"
                       data-title="移交专用工器具清单">
                        移交专用工器具清单
                    </a>
                </li>
                <li>
                    <a href="${ctx}/${appid }/proproject/docFile?projectId=${projectId}&key=yjbpbjqd"
                       data-toggle="navtab"
                       data-id="doc-list"
                       data-title="移交备品备件清单">
                        移交备品备件清单
                    </a>
                </li>
                <li>
                    <a href="${ctx}/${appid }/proproject/docFile?projectId=${projectId}&key=xscyxdwyjzlqd"
                       data-toggle="navtab"
                       data-id="doc-list"
                       data-title="向生产运行单位移交资料清单">
                        向生产运行单位移交资料清单
                    </a>
                </li>
            </ul>
            <div class="clearfix"></div>
        </div>
    </div>

    <%--<div class="ess-form">--%>
    <%--<div class="jgyys-btn pull-right">--%>
    <%--<a href="javascript:;"><span>生成预览</span></a>--%>
    <%--</div>--%>
    <%--<ul class="input-doc">--%>
    <%--<li class="input_text">--%>
    <%--<label>输入1</label>--%>
    <%--<input type="text" name="1"--%>
    <%--value=""--%>
    <%--placeholder="请输入abc"--%>
    <%--&lt;%&ndash;data-rule="${validate_type }"&ndash;%&gt;--%>
    <%--&lt;%&ndash;data-ds="${ctx}${f.data_source }"&ndash;%&gt;--%>
    <%--&lt;%&ndash;data-toggle="${fset.view_type }"&ndash;%&gt;--%>
    <%--&lt;%&ndash;data-chk-style="${fset.chk_style }"&ndash;%&gt;--%>
    <%--&lt;%&ndash;data-live-search='${fset.select_search }'&ndash;%&gt;--%>
    <%--&lt;%&ndash;data-url='${fset.lookup_url }'&ndash;%&gt;--%>
    <%--&lt;%&ndash;<c:if test="${fset.is_form_readonly eq '1' }">readonly</c:if>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<c:if test="${fset.select_multi }">multiple="multiple"</c:if>&ndash;%&gt;--%>
    <%--/>--%>
    <%--</li>--%>
    <%--<li class="input_text">--%>
    <%--<label>输入2</label>--%>
    <%--<input type="text" name="2"--%>
    <%--value=""--%>
    <%--placeholder="请输入abc"--%>
    <%--/>--%>
    <%--</li>--%>
    <%--<li class="input_text">--%>
    <%--<label>输入2</label>--%>
    <%--<input type="text" name="2"--%>
    <%--value=""--%>
    <%--placeholder="请输入abc"--%>
    <%--/>--%>
    <%--</li>--%>
    <%--<li class="input_text">--%>
    <%--<label>输入2</label>--%>
    <%--<input type="text" name="2"--%>
    <%--value=""--%>
    <%--placeholder="请输入abc"--%>
    <%--/>--%>
    <%--</li>--%>
    <%--<li class="input_text">--%>
    <%--<label>输入2</label>--%>
    <%--<input type="text" name="2"--%>
    <%--value=""--%>
    <%--placeholder="请输入abc"--%>
    <%--/>--%>
    <%--</li>--%>
    <%--<li class="input_text">--%>
    <%--<label>输入2</label>--%>
    <%--<input type="text" name="2"--%>
    <%--value=""--%>
    <%--placeholder="请输入abc"--%>
    <%--/>--%>
    <%--</li>--%>
    <%--<li class="input_text">--%>
    <%--<label>输入2</label>--%>
    <%--<input type="text" name="2"--%>
    <%--value=""--%>
    <%--placeholder="请输入abc"--%>
    <%--/>--%>
    <%--</li>--%>
    <%--</ul>--%>
    <%--<div class="clearfix"></div>--%>
    <%--<div style="text-align:center; padding-top: 20px;">--%>
    <%--<button class="btn" style="background: #14CAB4; color: white;">保存</button>--%>
    <%--</div>--%>
</div>
</div>