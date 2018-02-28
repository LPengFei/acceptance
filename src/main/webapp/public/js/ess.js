$(function() {
	var option = {
		yAxis: {
			gridLineColor: '#F0F0F0',
			gridLineWidth: 2,
			title: {
				text: ""
			}
		},
		legend: {
			align: 'right',
			x: -30,
			verticalAlign: 'middle',
			y: 25,
			floating: false,
			shadow: false,
			layout: 'vertical'
		},
		plotOptions: {
			column: {
				stacking: 'normal',
				dataLabels: {
					enabled: true,
					color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
				}
			}
		},
		colors: ['#F96A6A', '#FF9147', '#FFC000', '#019CDF', '#95D7BB'],
		chart:{ 
			height:360,
			alignTicks: true,
			animation: true,
			backgroundColor: "#FFFFFF",
			borderColor: "#4572A7",
			borderRadius: 0,
			borderWidth: 0
		}
	}

	loadHightDiv($($(".chart-base-container")[0]), option);
})