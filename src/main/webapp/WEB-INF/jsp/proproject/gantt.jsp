<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>


<html>
<head>
    <script src="${ctx }/public/libraries/BJUI/js/jquery-1.7.2.min.js"></script>
    <script src="${ctx }/public/codebase/sources/dhtmlxgantt.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" href="${ctx }/public/codebase/dhtmlxgantt.css" type="text/css" media="screen"
          title="no title"
          charset="utf-8">
    <script src="${ctx }/public/codebase/locale/locale_cn.js" charset="utf-8"></script>
</head>
<body>
<div class="gantt_here" style='width:100%; height:100%;'>
</div>
</body>
</html>


<script type="text/javascript">
    gantt.config.grid_width = 380;
    gantt.config.add_column = false;
    gantt.config.show_links = false;
    gantt.config.show_progress = false;
    gantt.config.show_task_cells = false;
    gantt.config.drag_progress = false;
    gantt.config.drag_resize = false;
    gantt.config.drag_move = false;

    var colHeader = '<div class="gantt_grid_head_cell gantt_grid_head_add" onclick="gantt.createTask()"></div>';
    colContent = function (task) {
        return "";
    };
    gantt.config.columns = [
        {name: "text", label: "项目名称", tree: true, width: '*'},
        {
            name: "start", label: "开始时间", width: 80, align: "center",
            template: function (item) {
                return new Date(item.start_date);
            }
        },
        {
            name: "end", label: "结束时间", align: "center", width: 80,
            template: function (item) {
                var date = new Date(item.start_date);
                date.setDate(date.getDate() + Number(item.duration));
                return date;
            }
        },
        {
            name: "buttons",
            label: colHeader,
            width: 20,
            template: colContent
        }
    ];

    $('.gantt_here:first-child').each(function (_, gantt_here) {
        gantt.init(gantt_here);
        var url = "${ctx}/${appid}/proprogress/gantt?projectId=${projectId}";
        gantt.load(url);
        new gantt.dataProcessor(url).init(gantt);

        var refresh = function () {
            gantt.clearAll();
            gantt.init(gantt_here);
            gantt.load(url);
        }
        gantt.attachEvent("onAfterTaskAdd", refresh);
        gantt.attachEvent("onAfterTaskDelete", refresh);
        gantt.attachEvent("onAfterTaskUpdate", refresh);
    });
</script>