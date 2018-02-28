<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>

<script type="text/javascript">
    function milestoneCallback() {
        this.$element.navtab('closeCurrentTab');
        $('[data-id^="${record.pid}"].underline-nav').refresh();
    }
</script>
<div class="bjui-pageContent">
    <div class="ess-form">
        <form action="${ctx}/${appid }/${modelName }/save" data-toggle="validate" data-alertmsg="false" data-callback="milestoneCallback">
            <input type="hidden" name="record.${pkName}" value="${record[pkName] }">
            <input type="hidden" name="record.pid" value="${record.pid}">
            <ul>
                <c:forEach items="${fields }" var="f">
                    <c:set var="fset" value="${f.settings.formview }"></c:set>
                    <c:set var="fname" value="${f.field_name }"></c:set>
                    <c:if test="${not empty fset.field_name }">
                        <c:set var="fname" value="${fset.field_name }"></c:set>
                    </c:if>

                    <c:set var="validate_type" value="${fset.validate_type }"></c:set>
                    <c:if test="${f.is_required eq '1' and not fn:contains(validate_type, 'required') }">
                        <c:set var="validate_type" value="${validate_type } required"/>
                    </c:if>

                    <li class="input_${fset.view_type }">
                        <label>${f.field_alias }</label>
                        <input type="text" name="record.${fname }" id="j_input_${fname }"
                               value="${record[fname] }"
                               placeholder="请输入${f.field_alias }"
                               data-rule="${validate_type }"
                               data-ds="${ctx}${f.data_source}?flow=${flow}"
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
            <!-- 操作按钮 -->
            <div style="text-align:right; padding-top: 20px;">
                <button type="submit" class="btn" style="background: #14CAB4; color: white;">保存</button>
                <button type="button" class="btn-close" style="background: red; color: white;">取消</button>
            </div>
        </form>

    </div>
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
    $(function () {
        $("#j_input_checktype_select, #j_input_devicetype_select").change(function (event) {
            var checkStage = $('#j_input_checktype_select option:selected').val();
            var deviceType = $('#j_input_devicetype_select option:selected').val();
            if (checkStage && deviceType) {
                var doNotNeedDevice = checkStage.indexOf("feasible_check") >= 0 || checkStage.indexOf("arrive_check-count") >= 0;
                var $name = $('#j_input_name');
                var flowName = $('#j_input_checktype_select option:selected').text();
                var deviceTypeName = $('#j_input_devicetype_select option:selected').text();
                if ($name.val()) {
                    var newText = $name.val().replace($name.attr("flow"), flowName);
                    if (doNotNeedDevice) {
                        $name.val(newText.replace(/([^\w]*)-/, ""));
                        $('#j_input_devicetype_select').parent().hide();
                    } else {
                        $('#j_input_devicetype_select').parent().show();
                        if (newText.search("-") < 0) {
                            $name.val(deviceTypeName + "-" + flowName);
                        } else {
                            $name.val(newText.replace($name.attr("devicetype"), deviceTypeName));
                        }
                    }
                } else {
                    if (doNotNeedDevice) {
                        $('#j_input_devicetype_select').parent().hide();
                        $name.val(flowName);
                    } else {
                        $('#j_input_devicetype_select').parent().show();
                        $name.val(deviceTypeName + "-" + flowName);
                    }
                }
                $name.attr("flow", flowName);
                $name.attr("devicetype", deviceTypeName);
            }
        });
    });
</script>