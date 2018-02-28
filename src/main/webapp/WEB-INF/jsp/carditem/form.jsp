<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>
<div class="bjui-pageContent">
    <form action="${ctx}/${appid }/${modelName }/save" data-toggle="validate" data-alertmsg="false">
        <div class="ess-form">
            <div class="panel-heading">
                <h3 class="panel-title">
                    <button class="list" disabled="disabled">
                        <span class="glyphicon glyphicon-th-large"></span>
                    </button>
                    <c:choose>
                        <c:when test="${not empty record.citid}">
                            <%--《${cardItemType.name}》的--%>
                            修改验收小项
                        </c:when>
                        <c:otherwise>
                            <%--为《${cardItemType.name}》--%>
                           添加验收小项
                        </c:otherwise>
                    </c:choose>

                </h3>
            </div>
            <div class="ess-form">
                <input type="hidden" name="record.${pkName}" value="${record[pkName] }">
                <input type="hidden" name="record.cid" value="${cardItemType.cid}">
                <input type="hidden" name="record.citid" value="${cardItemType.id}">
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
            </div>
            <!-- 操作按钮 -->
            <div style="text-align:center; padding-top: 20px;">
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
