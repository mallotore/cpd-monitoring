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
			var cpuStats = [
					{name: "irqTime", data: []},
            		{name: "combined", data: []},
            		{name: "userTime", data: []},
            		{name: "stolenTime", data: []},
            		{name: "niceTime", data: []},
            		{name: "idleTime", data: []},
            		{name: "sysTime", data: []},
            		{name: "softIrqTime", data: []},
            		{name: "waitTime", data: []}
			];
			var diskSpaceStats = [
				{name: "Libre", data: []},
            	{name: "Ocupado", data: []}
			];
			_.map(serverStatsCollection, function(serverStats){
				var creationDate = dateToMilliseconds(serverStats.creationDate);
				createRamStats(creationDate, serverStats);
				createSwapStats(creationDate, serverStats);
				createCpuStats(creationDate, serverStats);
				createDiskSpaceStats(creationDate, serverStats);
			});
			createLineChart(("swap_" + server.id), "SWAP", swapStats, tooltipGbFormatter, 'GB');
			createLineChart(("ram_" + server.id), "RAM", ramStats, tooltipGbFormatter, 'GB');
			createLineChart(("cpu_" + server.id), "CPU", cpuStats, cpuPercentageFormatter, 'Porcentaje');
			createLineChart(("diskSpace_" + server.id), "Espacio en disco", diskSpaceStats, tooltipGbFormatter, 'GB');

			function createRamStats(creationDate, serverStats){
				ramStats[0].data.push([creationDate, formatToGB(serverStats.memStats.memFree)]);
				ramStats[1].data.push([creationDate, formatToGB(serverStats.memStats.memUsed)]);
			}

			function createSwapStats(creationDate, serverStats){
				swapStats[0].data.push([creationDate, formatToGB(serverStats.memStats.swapFree)]);
				swapStats[1].data.push([creationDate, formatToGB(serverStats.memStats.swapUsed)]);
			}

			function createCpuStats(creationDate, serverStats){
				cpuStats[0].data.push([creationDate, formatCPUStats(serverStats.cpuStats.totals.irqTime)]);
				cpuStats[1].data.push([creationDate, formatCPUStats(serverStats.cpuStats.totals.combined)]);
				cpuStats[2].data.push([creationDate, formatCPUStats(serverStats.cpuStats.totals.userTime)]);
				cpuStats[3].data.push([creationDate, formatCPUStats(serverStats.cpuStats.totals.stolenTime)]);
				cpuStats[4].data.push([creationDate, formatCPUStats(serverStats.cpuStats.totals.niceTime)]);
				cpuStats[5].data.push([creationDate, formatCPUStats(serverStats.cpuStats.totals.idleTime)]);
				cpuStats[6].data.push([creationDate, formatCPUStats(serverStats.cpuStats.totals.sysTime)]);
				cpuStats[7].data.push([creationDate, formatCPUStats(serverStats.cpuStats.totals.softIrqTime)]);
				cpuStats[8].data.push([creationDate, formatCPUStats(serverStats.cpuStats.totals.waitTime)]);

				function formatCPUStats(value){
					return value.replace("%", "") * 1;
				}
			}

			function createDiskSpaceStats(creationDate, serverStats){
				var usedSpace = serverStats.diskRootsSpace[0].totalSpace - serverStats.diskRootsSpace[0].freeSpace;
				diskSpaceStats[0].data.push([creationDate, formatToGB(serverStats.diskRootsSpace[0].freeSpace)]);
				diskSpaceStats[1].data.push([creationDate, formatToGB(usedSpace)]);
			}

			function cpuPercentageFormatter(chart){
				var dateString = moment(chart.point.x).format("DD-MM-YYYY hh:mm");               
	            return  '<b>'+ chart.series.name + '</b><br>' + dateString + " " + chart.point.y + "%";
			}

			function tooltipGbFormatter(chart) {	 
	    		var dateString = moment(chart.point.x).format("DD-MM-YYYY hh:mm");               
	            return  '<b>'+ chart.series.name + '</b><br>' + dateString + " " + chart.point.y + "GB";
		    }

			function dateToMilliseconds(dateString){
				return new Date(dateString).getTime(); 
			}

			function formatToGB(bytes){
				//extract
				return  parseFloat((bytes / 1073741824).toFixed(2));
			}
		};
	}

	function createLineChart(domId, title, seriesData, formatter, yAxisTitle){
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
	                text: 'Fecha'
	            }
	        },
	        yAxis: {
	            title: {
	                text: yAxisTitle
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