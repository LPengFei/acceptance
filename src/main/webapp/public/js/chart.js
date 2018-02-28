/**
 * 统计图
 */


/******* Higcharts js 饼图 ******/
var optionpie = {
	chart: {
		type: 'pie',
		plotShadow: false,
		style: {
			borderRadius: '5px',
		},
		backgroundColor: '#F4F9F9',
	},
	colors: ['#FFCB4F', '#FB8A04', '#F0514C'],

	legend: {
		enabled: false,
	},
	exporting: {
		enabled: false,
	},
	tooltip: {
		headerFormat: '',
		pointFormat: '{point.name}: {point.y} 项    占比：{point.percentage:.1f}%',
	},
	plotOptions: {
		pie: {
			allowPointSelect: true,
			cursor: 'pointer',
			size: '90%',
			dataLabels: {
				enabled: false,
				color: '#000000',
				connectorColor: '#000000',
				format: '{point.name}'
			}
		}
	}
};

/******* Higcharts js 柱状图 ******/



var optioncolumn = {
		chart: {
			type: 'column',
			backgroundColor: null,
		},

		yAxis: {
			min: 0,
			title: null,
			stackLabels: {
				enabled: true,
			},
			allowDecimals: false,
		},

		credits: {
			enabled: false
		},
		exporting: {
			enabled: false
		},
		legend: {
			enabled: false,
			shadow: false
		},
		
		tooltip: {
			useHTML: true,
			formatter: function() {
				var s = '<b>' + this.x + '</b>';
				s += '<br/><span style="color:' + this.series.color + ';"><i class="fa fa-stop" style="margin-right:5px;"></i>' + this.series.name + ':' + this.y + '</span>';
				return s;
			},
		},
		colors: ['#29AFED'],


	}
	/*** 线性统计图 line ***/

var optionline = {
	chart: {
		plotShadow: false,
		style: {
			borderRadius: '5px',
		},
		backgroundColor: '#F4F9F9',
	},
	subtitle: {
		enabled: false,
	},
	yAxis: {
		min: 0,
		title: null,
		stackLabels: {
			enabled: false,
		},
		allowDecimals: false,
	},

	tooltip: {
		useHTML: true,
		formatter: function() {
			var s = '<b>' + this.x + '</b>';
			$.each(this.points, function(index, object) {
				s += '<br/><sapn style="color:' + this.point.color + '"><i class="fa fa-stop" style="margin-right:5px;"></i>' + this.series.name + ':' + this.y + '</span>';
			});
			return s;
		},
		shared: true,
		valueSuffix: '项',
	},
	exporting: {
		enabled: false,
	},
	legend: {
		layout: 'vertical',
		align: 'right',
		verticalAlign: 'middle',
		borderWidth: 0
	},
	colors: ['#29AFED', '#9FE708', '#FF5948']
}




/**
 * 加载highchart的div。div必须是一下格式
 * <div class="chart-base-container" data-url="json/line.json">
		<h3>这里是一些其他内容不是不必须的</h3>
		<div class="chart"></div>
	</div>
 * @param {Object} baseContainer
 * @param {Object} options
 */
function loadHightDiv(baseContainer, options, clickFunction, isname, scroll, maxval) {
	var url = baseContainer.data("url");
	var container = baseContainer.find(".chart");
	var paras = baseContainer.find("input,select").serialize();
	loadAndDrawChart(container, url, paras, clickFunction, options, isname, scroll, maxval);
}

/**
 * 加载并画统计图
 *
 * @param $container
 *            放土统计图的jquery对象
 * @param url
 *            加载数据的url
 * @param paras
 *            url的参数
 * @param clickFunction
 *            点击图表的函数
 * @param options
 *            图标显示的参数，会覆盖默认设置
 * @param isname（String名称）
 * 			  选中的X轴名称变红
 * @param scroll（int指定具体数据值）
 * 			 是否出现滚动条
 * @param maxval（int指定具体数据值）
 * 			线性图中超出指定值点变红
 */
function loadAndDrawChart(container, url, paras, clickFunction, options, isname, scroll, maxval) {
	$.ajax({
		type: "get",
		url: url,
		data: paras,
		dataType: 'json',
		error: function(request) {
			alert("网络错误");
		},
		success: function(result) {
			if (result.status == 'ok') {

				drawChartWithData(container, result.data, clickFunction, options, isname, scroll, maxval);
			} else {
				container.html(result.data);
			}
		}
	});
}

/**
 * 画统计图
 *
 * @param $container
 *            放土统计图的jquery对象
 * @param result
 *            统计图的数据
 * @param clickFunction
 *            点击图表的函数
 * @param options
 *            图标显示的参数，会覆盖默认设置
 * @param isname（String名称）
 * 			  选中的X轴名称变红
 * @param scroll（int指定具体数据值）
 * 			 是否出现滚动条
 * @param maxval（int指定具体数据值）
 * 			线性图中超出指定值点变红

 */
function drawChartWithData($container, result, clickFunction, options, isname, scroll, maxval) {
	var paras = {}; // highcharts的参数
	// 根据图形类型整合数据
	if (result.type == "pie") { // 饼图
		paras = {
			title: {
				text: result.title,
				useHTML: true,
				align: 'center',
				floating: false,
				//x: 0,
				y: 30,
				style: {
					fontFamily: '微软雅黑',
					fontSize: '16px',
				}

			},
			series: [{
				type: 'pie',
				name: result.data.name,
				data: result.data
			}]
		};

		paras = $.extend({}, optionpie, paras);

	} else { // 线形图和符合图
		paras = {

			title: {
				text: result.title
			},
			plotOptions: {
				column: {
					stacking: 'normal',
					dataLabels: {
						enabled: false,
					}
				},
				series: {
					cursor: 'pointer',
					point: {
						events: {
							//click: clickFunction
							click : false

						}
					},
					borderColor: null,
				}
			},
			xAxis: [{
				categories: result.categories,
				scrollbar: {
					enabled: false,
				}
			}],
			series: result.series
		};
		paras = $.extend({}, optioncolumn, paras);
	}

	// 合并与用户的自定义参数
	if (!options) options = {};

	/*遍历搜索的是那个变电站，然后颜色和其他的不一样*/
	if (isname) {
		var c = paras.xAxis[0].categories;
		$.each(c, function(i) {
			if (isname == c[i]) {
				//匹配搜索的变电站名称一致的高亮红色
				c[i] = '<span style="color:red;font-weight:bold;">' + c[i] + '</span>';
				//paras.series[0].data[i]={y:paras.series[0].data[i],selected:true};//点击柱子变红
			}
			
		})
	}
	if (scroll && parseInt(scroll) > 0) {
		paras.xAxis[0].max = parseInt(scroll);
		paras.scrollbar = {
			enabled: true,
			barBackgroundColor: '#81CCC5',
			barBorderRadius: 5,
			barBorderWidth: 0,
			height: 8,
			buttonBackgroundColor: '#F4F9F9',
			buttonBorderWidth: 0,
			buttonArrowColor: '#F4F9F9',
			buttonBorderRadius: 7,
			rifleColor: '#81CCC5',
			trackBackgroundColor: 'white',
			trackBorderWidth: 1,
			trackBorderColor: '#F4F9F9',
			trackBorderRadius: 0,
		};
	}

	if ((maxval && paras.series[0].type == 'area' && parseInt(maxval) > 0)||(maxval && paras.series[0].type == 'line' && parseInt(maxval) > 0) ) {
		
		//获取整个series数组
		/*var objects = paras.series;
		$.each(objects, function(i) {
			//获取整个series里面的data数组
			var datas = paras.series[i].data;
			$.each(datas, function(y) {
				//比较data数组里面数据的大小
				if (paras.series[i].data[y] > maxval) {
					paras.series[i].data[y] = {
						y: paras.series[i].data[y],
						marker: {
							fillColor: '#000000',
						}
					}
				}
			});
		});*/
		//获取整个series[0]里面的data数组
		var datas = paras.series[0].data;
		$.each(datas, function(y) {
			//比较data数组里面数据的大小
			if (paras.series[0].data[y] > parseInt(maxval)) {
				paras.series[0].data[y] = {
					y: paras.series[0].data[y],
					marker: {
						fillColor: '#DE050B',
						states: {
							hover: {
								fillColor: '#DE050B',
							},
						},
					},
				}
			}
		});

	}

	options = $.extend({}, paras, options);
	$container.highcharts(options);
	
}