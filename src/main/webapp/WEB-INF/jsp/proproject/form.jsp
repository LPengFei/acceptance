<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>


<script type="text/javascript">
//    function newProjectCallback(param) {
//        this.$element.navtab('closeCurrentTab');
//        var $tab = new $.fn.navtab.Constructor().tools.getTab("main");
//        var options = $tab.data("options");
//        options.projectId = param.projectId;
//        $tab.data("options", options);
//        this.$element.navtab("refresh", "main");
//    }
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/${appid }/${modelName }/save?projectId=${record[pkName]}" data-toggle="validate" data-alertmsg="false" enctype="multipart/form-data">
        <input type="hidden" name="record.${pkName}" value="${record[pkName] }">
        <div class="ess-form">
            <div class="panel-heading">
                <h3 class="panel-title">
                    <button class="list" disabled="disabled">
                        <span class="glyphicon glyphicon-th-large"></span>
                    </button>
                    基本信息
                </h3>
            </div>
            <div class="ess-form">
                <ul>
                    <c:forEach items="${fields }" var="f" >
                        <c:set var="fset" value="${f.settings.formview }"></c:set>
                        <c:set var="fname" value="${f.field_name }"></c:set>
                        <c:if test="${not empty fset.field_name }">
                            <c:set var="fname" value="${fset.field_name }"></c:set>
                        </c:if>

                        <c:set var="validate_type" value="${fset.validate_type eq 'require' ? 'required' : fset.validate_type}"></c:set>
                        <c:if test="${f.is_required eq '1' and not fn:contains(validate_type, 'required') }">
                            <c:set var="validate_type" value="${validate_type } required" />
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
                                   data-url='${fset.lookup_url }'
                                   <c:if test="${fset.is_form_readonly eq '1' }">readonly</c:if>
                                   <c:if test="${fset.select_multi }">multiple="multiple"</c:if>
                            />
                        </li>
                    </c:forEach>
                </ul>
                <div class="clearfix"></div>
            </div>
            <div class="panel-heading">
                <h3 class="panel-title">
                    <button class="list" disabled="disabled">
                        <span class="glyphicon glyphicon-th-large"></span>
                    </button>
                    建设规模
                </h3>
            </div>
            <div class="ess-form">
                <ul>
                    <c:forEach items="${scals }" var="scal" varStatus="index">
                        <li class="input_">
                            <label>${scal.name }</label>
                            <input type="hidden" name="scals[${index.count - 1}].id"
                                   value="${fn:length(scal.pid) > 0 ? scal.id : ''}"
                            />
                            <input type="hidden" name="scals[${index.count - 1}].sort" value="${scal.sort}">
                            <input type="hidden" name="scals[${index.count - 1}].key" value="${scal.key}">
                            <input type="hidden" name="scals[${index.count - 1}].name" value="${scal.name}">
                            <input type="text" name="scals[${index.count - 1}].initscal"
                                   id="j_input_${index.count}_initscal"
                                   value="${fn:length(scal.pid) > 0 ? scal.initscal : ''}"
                                   placeholder="请输入本期规模"
                            />
                            <div style="height: 5px;"></div>
                            <input type="text" name="scals[${index.count - 1}].finalscal"
                                   id="j_input_${index.count - 1}_finalscal"
                                   value="${fn:length(scal.pid) > 0 ? scal.finalscal : ''}"
                                   placeholder="请输入最终规模"
                            />
                        </li>
                    </c:forEach>
                </ul>
                <div class="clearfix"></div>
                <div style="height: 15px;"></div>
                <button type="button" class="btn-green" onclick="addLineInOut()">添加进出线</button>
                <div style="height: 15px;"></div>
                <div class="clearfix"></div>
                <ul id="line_in_out_list">
                    <c:forEach items="${scalLines }" var="scalLine" varStatus="index">
                        <li class="input_">
                            <input type="hidden" name="scalLines[${index.count - 1}].id" value="${scalLine.id}"/>
                            <input type="hidden" name="scalLines[${index.count - 1}].sort" value="-1">
                            <input type="hidden" name="scalLines[${index.count - 1}].key" value="-1">
                            <input type="text" name="scalLines[${index.count -1}].name"
                                   id="scal_lines_${index.count -1}_name" value="${scalLine.name}"
                                   placeholder="请输入进出线名称" class="form-control"/>
                            <button type="button" class="btn btn-default" aria-label="Left Align"
                                    onclick="removeLineInOut(this)">
                                <span class="glyphicon glyphicon-remove" aria-hidden="true" style="color: red;"></span>
                            </button>
                            <div style="height: 5px;"></div>
                            <input type="text" name="scalLines[${index.count - 1}].initscal"
                                   id="scal_lines_${index.count}_initscal"
                                   value="${scalLine.initscal}"
                                   placeholder="请输入本期规模"
                            />
                            <div style="height: 5px;"></div>
                            <input type="text" name="scalLines[${index.count - 1}].finalscal"
                                   id="scal_lines_${index.count - 1}_finalscal"
                                   value="${scalLine.finalscal}"
                                   placeholder="请输入最终规模"
                            />
                        </li>
                    </c:forEach>
                </ul>
                <div class="clearfix"></div>
            </div>

            <div class="panel-heading">
                <h3 class="panel-title">
                    <button class="list" disabled="disabled">
                        <span class="glyphicon glyphicon-th-large"></span>
                    </button>
                    上传附件
                </h3>
            </div>
            <div class="ess-form upload-project-doc">
                <%
                    request.setAttribute("doc_map", new HashMap<String, String>(){{
                        put("ysjh", "验收计划");
                        put("ysfa", "验收方案");
                        put("jlbg", "监理报告");
                    }});
                %>
                <c:set var="doc_array" value="${['ysjh', 'ysfa', 'jlbg']}"/>
                <ul>
                    <c:forEach items="${doc_array}" var="docName" varStatus="s">
                        <li>
                            <label>${doc_map[docName]}上传：</label>
                            <div style="display:inline-block; vertical-align:middle;">
                                <div
                                        data-toggle="upload"
                                        data-uploader="${ctx}/${appid }/${modelName }/upload"
                                        data-file-size-limit="1024000000"
                                        data-file-type-exts="*.jpg;*.png;*.gif;*.mpg;*.pdf;*.doc;*.xls;"
                                        data-multi="false"
                                        data-on-select="doc_on_select"
                                        data-on-upload-success="doc_upload_success"
                                        data-icon="cloud-upload"
                                        data-auto="false"
                                        data-button-text="${doc_map[docName]}"
                                        data-drag-drop="true"
                                        data-input-name="${docName}_doc"
                                        data-show-uploaded-percent="false"
                                        data-show-uploaded-size="false"></div>

                            </div>
                            <c:set var="name" value="${fn:split(record[docName], '/')}"></c:set>
                            <a href="${ctx}/${appid }/${modelName }/download?projectId=${record[pkName]}&type=${docName}" target="_blank">${name[fn:length(name) - 1]}</a>
                        </li>
                    </c:forEach>
                </ul>
                <div class="clearfix"></div>
            </div>

            <!-- 操作按钮 -->
            <div class=""
                 style="text-align:center; padding-top: 20px;">
                <button type="submit" class="btn" style="background: #14CAB4; color: white;">保存</button>
                <button type="button" class="btn-close" style="background: red; color: white;">取消</button>
            </div>
        </div>
    </form>
</div>


<c:choose>
    <c:when test="${not empty jsFile}">
        <script src="${ctx }${jsFile}"></script>
    </c:when>
    <c:otherwise>
        <script type="text/javascript">
            initForm();
        </script>
    </c:otherwise>
</c:choose>

<script type="text/javascript">
    function removeLineInOut(obj) {
        $(obj).parent().remove();
    }

    function addLineInOut() {
        var count = $("#line_in_out_list li").length;
//        console.log(count);
        var html = '<li class="input_">' +
                '<input type="hidden" name="scalLines[' + count + '].sort" value="-1">' +
                '<input type="hidden" name="scalLines[' + count + '].key" value="-1">' +
                '<input type="text" name="scalLines[' + count + '].name" id="scal_lines_' + count +'_name" value="" placeholder="请输入进出线名称" class="form-control"/>' +
                '<button type="button" class="btn btn-default" aria-label="Left Align" onclick="removeLineInOut(this)"> <span class="glyphicon glyphicon-remove" aria-hidden="true" style="color: red;"></span></button>' +
                '<div style="height: 5px;"></div>' +
                '<input type="text" name="scalLines[' + count + '].initscal" id="scal_lines_' + count +'_initscal" value="" placeholder="请输入本期规模"  class="form-control"/>' +
                '<div style="height: 5px;"></div>' +
                '<input type="text" name="scalLines[' + count + '].finalscal" id="scal_lines_' + count +'_finalscal" value="" placeholder="请输入最终规模"  class="form-control"/>' +
                '</li>';
        $("#line_in_out_list").append(html);
    }

    function doc_upload_success(file, data) {
        var json = $.parseJSON(data)
        $(this).bjuiajax('ajaxDone', json)
        if (json[BJUI.keys.statusCode] == BJUI.statusCode.ok) {
            $('#doc_pic').val(json.filename)
        }
    }
    function doc_on_select(file) {
        var items = this.$element.find('> .queue > .item:visible')
        if ( items.length > 1 ) {
            $(items[0]).remove()
        }
        $(items[0]).find('> .info > .up_confirm, > .progress').hide();
    }
</script>