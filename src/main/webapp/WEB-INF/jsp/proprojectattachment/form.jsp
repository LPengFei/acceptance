<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../include/taglib.jsp" %>


<script type="text/javascript">
    function doc_upload_success(file, data) {
        var json = $.parseJSON(data)
        $(this).bjuiajax('ajaxDone', json)
        if (json[BJUI.keys.statusCode] == BJUI.statusCode.ok) {
            $('#doc_pic').val(json.filename)
        }
    }
    function doc_on_select(file) {
        $("#j_input_name").val(file.name);
        var items = this.$element.find('> .queue > .item:visible')
        if ( items.length > 1 ) {
            $(items[0]).remove()
        }
        $(items[0]).find('> .info > .up_confirm, > .progress').hide();
    }
</script>

<div class="bjui-pageContent">

        <form action="${ctx}/${appid }/${modelName }/save?projectId=${projectId}"  data-toggle="validate" data-alertmsg="false" enctype="multipart/form-data">
            <input type="hidden" name="record.${pkName}" value="${record[pkName] }">
            <input type="hidden" name="record.pid" value="${projectId}">
            <div class="ess-form">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <button class="list" disabled="disabled">
                            <span class="glyphicon glyphicon-th-large"></span>
                        </button>
                       添加${attachType}
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

                        <c:set var="validate_type" value="${fset.validate_type }"></c:set>
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
                <hr>
                <div style="display:inline-block; vertical-align:middle;">
                    <div id="doc_pic_up"
                         data-toggle="upload"
                         data-uploader="${ctx}/${appid }/${modelName }/upload"
                         data-file-size-limit="1024000000"
                         data-file-type-exts="*.jpg;*.png;*.gif;*.mpg;*.pdf;*.doc;*.xls;"
                         data-multi="false"
                         data-on-select="doc_on_select"
                         data-on-upload-success="doc_upload_success"
                         data-icon="cloud-upload"
                         data-auto="false"
                         data-button-text="上传工程项目附件"
                         data-drag-drop="true"
                         data-show-uploaded-percent="false"
                         data-show-uploaded-size="false"
                    >
                    </div>
                </div>
                <span id="doc_span_pic"></span>

                </div>
                <!-- 操作按钮 -->
                <div style="text-align:right; padding-top: 20px;">
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
    <c:if test="${not empty attachType}">
        $('#j_input_type_select').change(function () {
            $('#j_input_type_select').selectpicker('val', '${attachType}');
        });
    </c:if>
</script>


