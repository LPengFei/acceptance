<%@ page import="com.cnksi.app.model.ResultIssue" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: gaozhou
  Date: 2016/12/29
  Time: 17:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>
<div class="col-md-4 ess-form">
    <div class="flow-statistics" style="height: 400px"></div>
</div>
<div class="col-md-4 ess-form">
    <h3 style="position: absolute; text-align: center;width: 100%;">工程缺陷分布图</h3>
    <div style="width: 100%; overflow-x: auto">
        <div class="project-statistics" style="width: 3060px;height: 400px"></div>
    </div>
</div>
<div class="col-md-4 ess-form">
    <div class="important-issue-statistics" style="height: 400px"></div>
</div>

<script>
    require.config({
        paths: {
            echarts: BJUI.PLUGINPATH + 'echarts'
        }
    });
    require(
        [
            'echarts',
            'echarts/config',
            'echarts/chart/pie',
            'echarts/chart/bar',
        ],
        function (ec, ecConfig) {
            var flows = ${flows}
            var issuesMap = ${issuesMap};
            var devices = ${devices};
            var devicesMap = ${devicesMap};

            $('.flow-statistics').each(function (_, v) {
                var option = {
                    title: {
                        x: '200',
                        text: '各验收阶段缺陷统计图',
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        x: 'left',
                        data: ['发现缺陷', '消除缺陷']
                    },
                    grid: {'x': 50, 'x2': 10, 'y2': 80},

                    xAxis: [
                        {
                            type: 'category',
                            axisLabel: {
                                show: true,
                                interval: 'auto',
                                rotate: 45,
                                margin: 8,
                            },
                            xAxisIndex: 1,
                            data: flows.map(function (v) {return v.name;})
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: '发现缺陷',
                            type: 'bar',
                            data: flows.map(function (v) {return issuesMap[v.key] ? issuesMap[v.key].length : 0;})
                        },
                        {
                            name: '消除缺陷',
                            type: 'bar',
                            data: flows.map(function (v) {
                                return issuesMap[v.key] ? issuesMap[v.key].filter(function (v1) {return v1.status === "cleared";}).length : 0;
                            })
                        }
                    ]
                };
                ec.init(v).setOption(option).on(ecConfig.EVENT.CLICK, function (param) {
                    $(this).navtab({
                        id:'issues-list',
                        url:'${ctx}/${appid}/proproject/issues?projectId=${projectId}',
                        title:'缺陷列表',
                        data: {
                            cleared: param.seriesIndex !== 0,
                            flow: flows.filter(function (v) {return v.name === param.name;}).map(function (v) {return v.key;})[0]
                        }
                    });
                });
            });

            $('.project-statistics').each(function (_, v) {
                var option = {
                    tooltip: {
                        trigger: 'axis'
                    },
                    grid: {'x': 50, 'x2': 10, 'y2': 80, 'width': 3000},
                    legend: {
                        x: 'left',
                        data: ['发现缺陷', '消除缺陷']
                    },

                    xAxis: [
                        {
                            type: 'category',
                            axisLabel: {
                                show: true,
                                interval: 'auto',
                                rotate: 45,
                                margin: 8,
                            },
                            xAxisIndex: 1,
                            data: devices.map(function (v) {return v;})
                        },
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: '发现缺陷',
                            type: 'bar',
                            data: devices.map(function (v) {return devicesMap[v] ? devicesMap[v].length : 0;})
                        },
                        {
                            name: '消除缺陷',
                            type: 'bar',
                            data: devices.map(function (v) {
                                return devicesMap[v] ? devicesMap[v].filter(function (v1) {return v1.status === "cleared";}).length : 0;
                            })
                        }
                    ]
                };

                ec.init(v).setOption(option).on(ecConfig.EVENT.CLICK, function (param) {
                    $(this).navtab({
                        id:'issues-list',
                        url:'${ctx}/${appid}/proproject/issues?projectId=${projectId}',
                        title:'缺陷列表',
                        data: {
                            cleared: param.seriesIndex !== 0,
                            devicename: param.name
                        }
                    });
                });
            });

            $('.important-issue-statistics').each(function (_, v) {
                var option = {
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    title: {
                        text: '工程重大问题分布图',
                        x: 'center'
                    },
//                    legend: {
//                        orient: 'vertical',
//                        x: 'left',
//                        data: ['断路器', '隔离开关', '电流互感器', '并联电容器']
//                    },
                    series: [
                        {
                            name: '',
                            type: 'pie',
                            radius: '80%',
                            center: ['50%', '60%'],
                            itemStyle: {
                                normal: {
                                    label: {
                                        position: 'inner'
                                    },
                                    labelLine: {
                                        show: false
                                    }
                                }
                            },
                            data: devices.map(function (v) {
                                var len = (devicesMap[v] || []).filter(function (v1) {return v1.isimport;}).length;
                                return {name : v + "(" + len + ")", value: len, key: v};
                            })
                        }
                    ]
                };
                ec.init(v).setOption(option).on(ecConfig.EVENT.CLICK, function (param) {
                    $(this).navtab({
                        id:'issues-list',
                        url:'${ctx}/${appid}/proproject/issues?projectId=${projectId}',
                        title:'缺陷列表',
                        data: {
                            isimport: true,
                            devicename: param.data.key
                        }
                    });
                });
            });
        }
    );
</script>