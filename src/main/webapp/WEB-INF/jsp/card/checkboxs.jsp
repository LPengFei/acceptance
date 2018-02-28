<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: gaozhou
  Date: 2016/11/9
  Time: 下午7:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style type="text/css">
    .ess-form > ul > label {
        font-size: 14px;
    }
</style>

<div>
    <c:choose>
        <c:when test="${empty cards}">
            <h3>无</h3>
        </c:when>
        <c:otherwise>
            <ul>
                <c:forEach items="${cards}" var="card" varStatus="s">
                    <li style="width:auto;">
                        <input type="radio" name="record.cards" id="card${s.count}" data-toggle="icheck" data-label="${card.name}" value="${card.id}" ${card._selected ? "checked" : ""}>
                    </li>
                    <div class="clearfix"></div>
                </c:forEach>
            </ul>
            <div class="clearfix"></div>
            <c:if test="${showCardItemTypes}">
                <c:forEach items="${cards}" var="card" varStatus="s_card">
                    <div class="ess-form card-item-type-select" id="hidden_key_${card.id}" style="display: ${card._selected ? 'block' : 'none'}">
                        <ul>
                            <c:choose>
                                <c:when test="${card.flow eq 'middle_check'}">
                                    <li><label>选择中间验收项:</label></li>
                                </c:when>
                                <c:when test="${card.flow eq 'hidden_check'}">
                                    <li><label>选择隐蔽工程验收项:</label></li>
                                </c:when>
                            </c:choose>
                        </ul>
                        <div class="clearfix"></div>
                        <div>
                            <ul>
                                <c:forEach items="${card._cardItemTypes}" var="cardItemType" varStatus="s">
                                    <input type="checkbox" name="taskCard.${card.id}" value="${cardItemType.id}" data-toggle="icheck" data-rule="checked"
                                           data-label="${cardItemType.name}" ${cardItemType._selected ? "checked" : ""}>
                                    <br/>
                                    <br/>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </c:otherwise>
    </c:choose>
</div>

<script>
    <c:if test="${showCardItemTypes}">
        $(function(){
            var updateStatus = function($target) {
                $target.show().find("input").iCheck("enable");
                $target.siblings(".ess-form").hide().find("input").iCheck("disable");
            };
            updateStatus($('.card-item-type-select:not(:hidden)'));

            $('input[name="record.cards"]').on('ifChecked', function(event){
                updateStatus($("#hidden_key_" + event.target.value))
            });
            if ( $('input[name="record.cards"][checked]').size() == 0 ) {
                $('input[name="record.cards"]').eq(0).iCheck('check');
            } else {
                $('input[name="record.cards"][checked]').eq(0).iCheck('check');
            }
        })
    </c:if>
</script>
