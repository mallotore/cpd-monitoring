window.mallotore = window.mallotore || {};

(function(mallotore){
	
	function HistoricalStatsChartsPresenter(){

		this.render = function(server, serverStatsCollection){
			var bytesFormatter = mallotore.utils.bytesFormatter;
			var swapStats = [
				{ name:  'Swap en uso', data: [] },
				{ name: 'Swap libre', data: [] }
			];
			var ramStats  = [
				{ name:  'RAM en uso', data: [] },
				{ name: 'RAM libre', data: [] }
			];
			_.map(serverStatsCollection, function(serverStats){
				var creationDate = dateToMilliseconds(serverStats.creationDate);
				swapStats[0].data.push([creationDate, formatToGB(serverStats.memStats.swapFree)]);
				swapStats[1].data.push([creationDate, formatToGB(serverStats.memStats.swapUsed)]);
				ramStats[0].data.push([creationDate, formatToGB(serverStats.memStats.memFree)]);
				ramStats[1].data.push([creationDate, formatToGB(serverStats.memStats.memUsed)]);
			});
			createLineChart(("swap_" + server.id), "SWAP", swapStats, formatter);
			createLineChart(("ram_" + server.id), "RAM", ramStats, formatter);
		};

		function formatter(chart) {	 
    		var dateString = moment(chart.point.x).format("DD-MM-YYYY hh:mm");               
            return  '<b>'+ chart.series.name + '</b><br>' + dateString + " " + chart.point.y + "GB";
	    }

		function dateToMilliseconds(dateString){
			return new Date(dateString).getTime(); 
		}

		function formatToGB(bytes){
			return  parseFloat((bytes / 1073741824).toFixed(2));
		}
	}

	function createLineChart(domId, title, seriesData, formatter){
		new Highcharts.Chart({
			chart: {
				renderTo: domId,
	            type: 'spline'
	        },
	        title: {
	            text: title
	        },
	        credits: {
	    		enabled: false
	  		},
	        xAxis: {
	            type: 'datetime',
	            dateTimeLabelFormats: {
	                month: '%e. %b',
	                year: '%b'
	            },
	            title: {
	                text: 'Date'
	            }
	        },
	        yAxis: {
	            title: {
	                text: 'GB'
	            },
	            min: 0
	        },
	        tooltip: {
	        	formatter: function() {	 
	        		return formatter(this);
	            }
	        },

	        plotOptions: {
	            spline: {
	                marker: {
	                    enabled: true
	                }
	            }
	        },
	        series: seriesData
        });
	}

	function createHistoricalStatsChartsPresenter(){
		return new HistoricalStatsChartsPresenter(); 
	}

	mallotore.stats = mallotore.stats || {};
	mallotore.stats.createHistoricalStatsChartsPresenter = createHistoricalStatsChartsPresenter;

})(window.mallotore);