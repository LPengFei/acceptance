<%--
  Created by IntelliJ IDEA.
  User: gaozhou
  Date: 2016/12/26
  Time: 下午4:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>
<c:forEach items="${issueItems}" var="v" varStatus="s">
    <tr>
        <td class="td_center" style="position: relative;">
            <span style="margin: auto auto; height: 100%; width: 100%;position: relative;">
                <span>${s.count}<span>
                <%--<c:if test="${v.isimport eq 1}">--%>
                    <%--<span data-toggle="tooltip" data-placement="right" title="重大问题"--%>
                          <%--style="color: red; position: absolute; width: 5px; height: 5px;top: 0px; right: 0px;cursor: pointer;">*</span>--%>
                <%--</c:if>--%>
            </span>
        </td>
        <td class="td_center">
            <fmt:formatDate value="${v.createtime}" pattern="yyyy-MM-dd"/>
        </td>
        <td>
                ${v.creator}
        </td>
        <td class="td_left">${v.description}</td>
        <td class="td_center">
            <c:choose>
                <c:when test="${v.level eq 2}">
                    问题
                </c:when>
                <c:when test="${v.level eq 4}">
                    重大问题
                </c:when>
                <c:when test="${v.level eq 6}">
                    一般缺陷
                </c:when>
                <c:when test="${v.level eq 8}">
                    严重缺陷
                </c:when>
                <c:otherwise>

                </c:otherwise>
            </c:choose>
        </td>
        <td class="td_center">
            <div class="center">
                <c:if test="${not empty v.imgs}">
                    <c:set var="images" value="${v.imgs.split(',')}"/>
                    <div class="icon0">
                        <c:forEach var="image" items="${images}" varStatus="s">
                            <a class="fancybox fancybox-buttons" href="${ctx}/upload/${image}"
                               data-fancybox-group="${flowKey}_${v.id}">
                                                <span class="icon1" data-imgs="${v.imgs}">
                                                <c:if test="${s.count == 1}">
                                                    <img src="${ctx}/upload/${image}" style="height:100%; width:100%;">
                                                </c:if>
                                                </span>
                                <span class="icon2">${fn:length(images)}</span>
                            </a>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
        </td>
        <td class="td_center">
            <c:if test="${not empty v._signature}">
                <div class="icon0">
                    <c:forEach var="sign" items="${v._signature}" varStatus="s">
                        <a class="fancybox fancybox-buttons" href="${ctx}/upload/${sign.signimg}"
                           data-fancybox-group="${flowKey}_sign_${v.id}">
                                        <span class="icon1" data-imgs="${v.fiximgs}">
                                        <c:if test="${s.count == 1}">
                                            <img src="${ctx}/upload/${sign.signimg}" style="height:100%; width:100%;">
                                        </c:if>
                                        </span>
                            <span class="icon2">${fn:length(v._signature)}</span>
                        </a>
                    </c:forEach>
                </div>
            </c:if>
        </td>
        <td class="td_left">${v.suggest}</td>
        <td class="td_center">
                ${v.status eq "cleared" ? '是': '否'}
            <c:if test="${v.status eq 'cleared' and not empty v.fiximgs}">
                <c:set var="images" value="${v.fiximgs.split(',')}"/>
                <div class="icon0">
                    <c:forEach var="image" items="${images}" varStatus="s">
                        <a class="fancybox fancybox-buttons" href="${ctx}/upload/${image}"
                           data-fancybox-group="${flowKey}_cleard_${v.id}">
                                        <span class="icon1" data-imgs="${v.fiximgs}">
                                        <c:if test="${s.count == 1}">
                                            <img src="${ctx}/upload/${image}" style="height:100%; width:100%;">
                                        </c:if>
                                        </span>
                            <span class="icon2">${fn:length(images)}</span>
                        </a>
                    </c:forEach>
                </div>
            </c:if>
        </td>
        <td class="td_center">
            <fmt:formatDate value="${v.fixtime}" pattern="yyyy-MM-dd"/>
        </td>
        <td class="td_center">${ v.checkresult eq null ? "" : (v.checkresult eq "Y" ? "通过" : "未通过") }</td>
        <td class="td_center"><a href="${ctx}/${appid }/resultissuelog?riid=${v.id}" data-toggle="navtab"
                                 data-id="resultissuelog-list"
                                 data-title="操作记录">操作记录</a></td>
        <td class="td_center">
            <c:if test="${v.tid ne null}">
                <a href="${ctx}/${appid }/task/info?id=${v.tid}&tab=0" data-toggle="navtab"
                   data-id="task-info"
                   data-title="详情任务">验收卡</a>
                &nbsp;&nbsp;&nbsp;
                <a href="${ctx}/${appid }/task/info?id=${v.tid}&tab=1" data-toggle="navtab"
                   data-id="task-info"
                   data-title="详情任务">验收记录</a>
            </c:if>
        </td>
    </tr>
</c:forEach>