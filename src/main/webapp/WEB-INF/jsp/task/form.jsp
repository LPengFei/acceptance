<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>

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


<div class="bjui-pageContent">

    <form action="${ctx}/${appid }/${modelName }/save" data-toggle="validate" data-alertmsg="false" id="taskForm">
        <input type="hidden" name="record.${pkName}" value="${record[pkName] }">
        <input type="hidden" name="record.pid" value="${projectId}">
        <input type="hidden" name="record.pname" value="${projectName}">

        <div class="ess-form">
            <div class="panel-heading">
                <h3 class="panel-title">
                    <button class="list" disabled="disabled">
                        <span class="glyphicon glyphicon-th-large"></span>
                    </button>
                    任务信息
                </h3>
            </div>
            <div class="ess-form">
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
                <%--<hr>--%>
                <%--<div class="clearfix"></div>--%>
                <%--<ul>--%>
                <%--<li><label>验收标准卡:</label></li>--%>
                <%--</ul>--%>
                <%--<div class="clearfix"></div>--%>
                <%--<div id="cardTypeCheckboxs" style="overflow: hidden; min-height: 60px;"></div>--%>
                <%--<hr>--%>
            </div>
            <c:set var="taskCard" value="${taskCards[0]}"/>
            <c:set var="card" value="${cards[0]}"/>
            <c:set var="need_checkbox_fields"
                   value="${['showgcmc', 'showsjdw', 'showysdw', 'showysrq', 'showsccj', 'showsbxh', 'showscgh', 'showccbh', 'showbdsmc', 'showsbmcbh', 'showjldw', 'showsgdw', 'showazdw']}"/>
            <div class="panel-heading">
                <h3 class="panel-title">
                    <button class="list" disabled="disabled">
                        <span class="glyphicon glyphicon-th-large"></span>
                    </button>
                    标准卡信息
                </h3>
            </div>
            <div class="ess-form">
                <input type="hidden" name="taskCard.id" value="${taskCard.id}">
                <input type="hidden" name="taskCard.tid" value="${taskCard.tid}">
                <input type="hidden" name="taskCard.cid" value="${taskCard.cid}">
                <input type="hidden" name="taskCard.status" value="${taskCard.status}">
                <input type="hidden" name="taskCard.cname" value="${taskCard.cname}">
                <input type="hidden" name="taskCard.flow" value="${taskCard.flow}">
                <input type="hidden" name="taskCard.type" value="${taskCard.type}">
                <input type="hidden" name="taskCard.dtid" value="${taskCard.dtid}">
                <input type="hidden" name="taskCard.citids" value="${taskCard.citids}">
                <ul>
                    <c:forEach var="item" items="${need_checkbox_fields}" varStatus="s">
                        <c:if test="${card.get(item) eq 1}">
                            <li class="input_text">
                                <label>${tc_fields.get(fn:substring(item, 4, -1)).get("field_alias")}</label>
                                <input type="text" name="taskCard.${fn:substring(item, 4, -1)}" id="j_input_${item}"
                                       value="${taskCard.get(fn:substring(item, 4, -1))}"
                                       placeholder="请输入${tc_fields.get(fn:substring(item, 4, -1)).get("field_alias")}"
                                       data-toggle="text"
                                />
                            </li>
                        </c:if>
                    </c:forEach>
                </ul>
                <div class="clearfix"></div>
            </div>
            <!-- 操作按钮 -->
            <div style="text-align:center; padding-top: 20px;">
                <button type="submit" class="btn" style="background: #14CAB4; color: white;">保存</button>
                <button type="button" class="btn-close" style="background: red; color: white;">取消</button>
            </div>
        </div>
    </form>
</div>

<script>
    $("#j_input_type_select,#j_input_devicetype_select").change(function (event) {
        if (event.target.id === "j_input_type_select") {
            var now = new Date();
            var chineseDate = [now.getFullYear(), "年", (now.getMonth() + 1), "月", now.getDate(), "日"].join("");
            $('#j_input_name').val(chineseDate + $("#j_input_type_select option:selected").text() + "任务");
        }
        var url = ["${ctx}/app/card/selectCardsByCardTypeKeyAndDeviceTypeName?cardTypeKey=", $("#j_input_type_select").val(),
            "&deviceTypeName=", $("#j_input_devicetype_select").val(),
            "&taskId=", "${record[pkName]}"].join("")
        if ($("#j_input_type_select option:selected").val() && $("#j_input_devicetype_select option:selected").val()) {
            $(this).bjuiajax('doLoad', {
                target: $("#cardTypeCheckboxs"),
                url: url
            })
        }
    })
</script>

