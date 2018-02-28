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

<script type="text/javascript" src="${ctx }/public/libraries/underlineTab/underlineTab.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/public/css/underlineTab.css"/>

<style type="text/css">
    header.title-color {
        height: 35px;
        line-height: 35px;

        background-color: #1abc9c;
        margin-top: 15px;
        color: white;
        border: 1px solid #ddd;
        border-bottom: 0px solid transparent;
        padding: 0px;
        border-top-left-radius: 3px;
        border-top-right-radius: 3px;
    }

    .project-name {
        padding-left: 10px;
        height: 100%;
        line-height: inherit;
        display: inline;
    }

    .glyphicon {;
        cursor: pointer
    }

    .nav-cursor {
        color: white;
        float: right;
        height: 100%;
        line-height: inherit;
        margin-right: 10px;
    }

    .project-container {
        /*position: relative;*/
        /*top: -1px;*/
        overflow: hidden;
        width: 100%;
        display: block;
        background: white;
        border: 1px solid #E0E2E5;
        border-top-width: 0px;
    }

    .project-containers {
        padding: 0px 20px 100px 20px;
    }
    .project {
        margin-bottom: 15px;
    }
</style>


<div class="bjui-pageContent" style="overflow-x: hidden">
    <div class="project-containers">
        <c:forEach items="${list}" var="record" varStatus="s">
            <div class="project" data-url="${ctx}/${appid}/statistic/projectStatistics?projectId=${record.id}">
                <header class="panel-heading title-color">
                    <h3 class="panel-title project-name">${record.name}</h3>
                    <span class="glyphicon nav-cursor glyphicon-chevron-down"></span>
                </header>
                <div class="project-container" style="display: none">
                    <table class="table table-bordered project-scale">
                        <thead>
                        <tr>
                            <th title="工程名称" width="30%">工程名称</th>
                            <th title="验收开始时间">验收开始时间</th>
                            <th title="验收结束时间">验收结束时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td width="30%">${record.name}</td>
                            <td>2015-1-1</td>
                            <td>2016-1-1</td>
                        </tr>
                        </tbody>
                    </table>
                    <div class="col-md-12 statistics-chart"></div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<script>
    $(function () {
        var showPanel = function () {
            var $container = this.find('.project-container');
            var $cursor = this.find('.nav-cursor');
            if (!$container.is(':visible')) {
                if ($cursor.hasClass('glyphicon-chevron-down')) {
                    this.find('.nav-cursor')
                        .removeClass('glyphicon-chevron-down glyphicon-chevron-up')
                        .addClass('glyphicon-chevron-up');
                }
                $container.slideDown("fast");
                $(this).bjuiajax("doLoad", {
                    target: this.find('.project-container .statistics-chart').get(0),
                    url: this.data("url")
                });
            }
        };
        var hidePanel = function () {
            var $container = this.find('.project-container');
            var $cursor = this.find('.nav-cursor');
            if ($container.is(':visible')) {
                if ($cursor.hasClass('glyphicon-chevron-up')) {
                    this.find('.nav-cursor')
                        .removeClass('glyphicon-chevron-down glyphicon-chevron-up')
                        .addClass('glyphicon-chevron-down');
                }
                $container.slideUp("fast", function () {
                    $container.hide();
                });
            }
        };
        $('.project').find('.nav-cursor').click(function (e) {
            var $project = $(e.target).closest('.project');
            $project.siblings().find('.project-container').filter(function (_, v) {
                return $(v).is(':visible');
            }).map(function (_, v) {
                $(v).closest('.project').find('.nav-cursor').click();
            });
            var $container = $project.find('.project-container');
            if ($container.is(':visible')) {
                hidePanel.apply($project);
            } else {
                showPanel.apply($project);
            }
        }).eq(0).click();
    });
</script>
