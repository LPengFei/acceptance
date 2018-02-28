<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>

<%--<style type="text/css">--%>
<%--.icon0 {--%>
<%--width: 66px;--%>
<%--height: 30px;--%>
<%--padding-left: 0px;--%>
<%--float: right;--%>
<%--line-height: 34px;--%>
<%--position: relative;--%>
<%--cursor: pointer;--%>
<%--}--%>

<%--.icon1 {--%>
<%--position: absolute;--%>
<%--left: 5px;--%>
<%--width: 30px;--%>
<%--height: 20px;--%>
<%--}--%>

<%--.icon2 {--%>
<%--position: absolute;--%>
<%--width: 16px;--%>
<%--height: 16px;--%>
<%--background-color: #C81623;--%>
<%--font-size: 12px;--%>
<%--line-height: 14px;--%>
<%--text-align: center;--%>
<%--color: #fff;--%>
<%--right: 20px;--%>
<%--top: -4px;--%>
<%--border-radius: 8px;--%>
<%--}--%>
<%--</style>--%>

<div class="bjui-pageContent">
    <div class="ess-form">
        <ul>
            <li>
                <label>编号</label>
                <div>
                    <label>${resultIssueImportant.no }</label>
                </div>
            </li>
            <div class="clearfix"></div>
            <li>
                <label>问题名称</label>
                <div>
                    <label>${resultIssueImportant.name }</label>
                </div>
            </li>
            <div class="clearfix"></div>
            <li>
                <label>发出单位/部门</label>
                <div>
                    <label>${resultIssueImportant.senddepart }</label>
                </div>
            </li>
            <li>
                <label>发出单位联系人</label>
                <div>
                    <label>${resultIssueImportant.sendcontact }</label>
                </div>
            </li>
            <li>
                <label>验收单位</label>
                <div>
                    <label>${resultIssueImportant.checkdepart }</label>
                </div>
            </li>
            <li>
                <label>验收人</label>
                <div>
                    <label>${resultIssueImportant.checkcontact }</label>
                </div>
            </li>
            <li>
                <label>接受单位/部门</label>
                <div>
                    <label>${resultIssueImportant.receivedepart }</label>
                </div>
            </li>
            <li>
                <label>接受单位联系人</label>
                <div>
                    <label>${resultIssueImportant.receivecontact }</label>
                </div>
            </li>
            <li>
                <label>项目名称</label>
                <div>
                    <label>${resultIssueImportant.project }</label>
                </div>
            </li>
            <li>
                <label>设备类别</label>
                <div>
                    <label>${resultIssueImportant.devicetype }</label>
                </div>
            </li>
            <li>
                <label>设备类别</label>
                <div>
                    <label>${resultIssueImportant.devicetype }</label>
                </div>
            </li>
            <li>
                <label>安装位置/运行编号(生产工号)</label>
                <div>
                    <label>${resultIssueImportant.installplace }</label>
                </div>
            </li>
            <li>
                <label>厂家及类型</label>
                <div>
                    <label>${resultIssueImportant.factory }</label>
                </div>
            </li>
            <div class="clearfix"></div>
            <li>
                <label>
                    问题描述
                </label>
                <div>
                    <label>
                        ${resultIssueImportant.description }
                        <c:if test="${not empty resultIssueImportant.imgs}">
                            <c:set var="images" value="${resultIssueImportant.imgs.split(',')}"/>
                            <div class="icon0">
                                <c:forEach var="image" items="${images}" varStatus="s">
                                    <a class="fancybox fancybox-buttons" href="${ctx}/upload/${image}" rel="gall11ery"
                                       style="visibility: ${s.count == 1 ? 'visible' : 'hidden'}">
                                            <span class="icon1" data-imgs="${resultIssueImportant.imgs}">
                                            <c:if test="${s.count == 1}">
                                                <img src="${ctx}/upload/${image}" style="height:100%; width:100%;">
                                            </c:if>
                                            </span>
                                        <span class="icon2">${fn:length(images)}</span>
                                    </a>
                                </c:forEach>
                            </div>
                        </c:if>

                    </label>
                </div>
            </li>
            <div class="clearfix"></div>
            <li>
                <label>整改建议</label>
                <div>
                    <label>${resultIssueImportant.suggest }</label>
                </div>
            </li>
        </ul>
        <div class="clearfix"></div>
    </div>
    <!-- 操作按钮 -->
    <div style="text-align:center; padding-top: 20px;">
        <button type="button" class="btn-close" style="background: red; color: white;">关闭</button>
    </div>
</div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">问题描述图片(鼠标放在图片上
                    就不会跳动咯哦~)</h4>
            </div>
            <div class="modal-body">
                <div id="my_carousel" class="carousel slide">
                    <!-- 放置图片 -->
                    <div class="carousel-inner" id="mycarousel"></div>
                    <a href="#my_carousel" data-slide="prev" id="prev"
                       class="carousel-control left"><span
                            class="glyphicon glyphicon-chevron-left"></span></a> <a
                        href="#my_carousel" data-slide="next" id="next"
                        class="carousel-control right"><span
                        class="glyphicon glyphicon-chevron-right"></span></a>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">关闭
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>


<script type="text/javascript">
    $(document).ready(function () {
        $('.fancybox').fancybox({
            padding: 0,
            openEffect: 'elastic'
        });
        $('.fancybox-buttons').fancybox({
            openEffect: 'none',
            closeEffect: 'none',

            prevEffect: 'none',
            nextEffect: 'none',

            afterLoad: function () {
                this.title = ['第 ', (this.index + 1), '/', this.group.length, ' 张'].join("");
            }
        });
    });
</script>