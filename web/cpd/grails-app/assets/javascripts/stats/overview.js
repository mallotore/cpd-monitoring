//= require_tree ../lib/highcharts
//= require_tree ../utils
//= require_tree . 
//= require_self

var mallotore = mallotore || {};

$(document).ready(function(){

	function createDiskPercentage(){
		var chart = new Highcharts.Chart({
	        chart: {
	        	renderTo : 'diskPercentage',
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false,
	            type: 'pie'
	        },
	        credits: {
	    		enabled: false
	  		},
	        title: {
	            text: 'Espacio en disco'
	        },
	        tooltip: {
	            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
	                    style: {
	                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	                    }
	                }
	            }
	        },
	        series: [{
	            name: 'Brands',
	            colorByPoint: true,
	            data: [{
	                name: 'Microsoft Internet Explorer',
	                y: 56.33
	            }, {
	                name: 'Chrome',
	                y: 24.03,
	                sliced: true,
	                selected: true
	            }, {
	                name: 'Firefox',
	                y: 10.38
	            }, {
	                name: 'Safari',
	                y: 4.77
	            }, {
	                name: 'Opera',
	                y: 0.91
	            }, {
	                name: 'Proprietary or Undetectable',
	                y: 0.2
	            }]
	        }]
	    });
	}

	createDiskPercentage();
});