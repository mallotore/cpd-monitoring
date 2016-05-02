window.mallotore = window.mallotore || {};

(function(mallotore){
	
	function HistoricalStatsChartsPresenter(){

		this.renderTemperature = function(temperatureStats){
			var temperature = [
				{ name:  'Temperatura', data: [] }
			];
			_.map(temperatureStats, function(stats){
				var creationDate = dateToMilliseconds(stats.creationDate);
				temperature[0].data.push([creationDate, stats.temperature * 1]);
			});

			createLineChart(("temperature"), "Temperatura", temperature, formatter, 'ºC');

			function formatter(chart) {	 
	    		var dateString = moment(chart.point.x).format("DD-MM-YYYY hh:mm");               
	            return  '<b>'+ chart.series.name + '</b><br>' + dateString + " " + chart.point.y + "ºC";
		    }
		};

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
			var whoListStatsForChart = [];
			var activeServicesStats = [
					{name: "mysql", data: []},
            		{name: "sql", data: []},
            		{name: "oracle", data: []},
            		{name: "iis", data: []},
            		{name: "apache2", data: []},
            		{name: "tomcat", data: []},
            		{name: "ftp", data: []},
            		{name: "http", data: []}
			];

			_.map(serverStatsCollection, function(serverStats){
				var creationDate = dateToMilliseconds(serverStats.creationDate);
				createRamStats(creationDate, serverStats);
				createSwapStats(creationDate, serverStats);
				createCpuStats(creationDate, serverStats);
				createDiskSpaceStats(creationDate, serverStats);
				createWholistStats(creationDate, serverStats);
				createActiveServicesStats(creationDate, serverStats);
			});
			createLineChart(("swap_" + server.id), "SWAP", swapStats, tooltipGbFormatter, 'GB');
			createLineChart(("ram_" + server.id), "RAM", ramStats, tooltipGbFormatter, 'GB');
			createLineChart(("cpu_" + server.id), "CPU", cpuStats, cpuPercentageFormatter, 'Porcentaje');
			createLineChart(("diskSpace_" + server.id), "Espacio en disco", diskSpaceStats, tooltipGbFormatter, 'GB');
			createLineChart(("wholist_" + server.id), "Usuarios conectados", whoListStatsForChart, activeFormatter, 'Activo');
			createLineChart(("activeServices_" + server.id), "Servicios activos", activeServicesStats, activeFormatter, 'Activo');

			function createActiveServicesStats(creationDate, serverStats){
				activeServicesStats[0].data.push([creationDate, serverStats.activeServices.mysql ? 1 : 0]);
				activeServicesStats[1].data.push([creationDate, serverStats.activeServices.sql ? 1 : 0]);
				activeServicesStats[2].data.push([creationDate, serverStats.activeServices.oracle ? 1 : 0]);
				activeServicesStats[3].data.push([creationDate, serverStats.activeServices.iis ? 1 : 0]);
				activeServicesStats[4].data.push([creationDate, serverStats.activeServices.apche2 ? 1 : 0]);
				activeServicesStats[5].data.push([creationDate, serverStats.activeServices.tomcat ? 1 : 0]);
				activeServicesStats[6].data.push([creationDate, serverStats.activeServices.ftp ? 1 : 0]);
				activeServicesStats[7].data.push([creationDate, serverStats.activeServices.http ? 1 : 0]);
			}

			function createWholistStats(creationDate, serverStats){
				_(serverStats.wholistStats).forEach(function(wholist){

					var who = _.find(whoListStatsForChart, function(wholistForChart) { 
									return _.find(wholistForChart, function(whoForChart) {
										return whoForChart == (wholist.user + "-" + wholist.device); 
									});
					});
					if(who){
						_.remove(whoListStatsForChart, function(whoStats) { 
							return whoStats.name == who.name;
						});
						who.data.push([creationDate, 1]);
						whoListStatsForChart.push({'name': who.name, 'data':who.data})
					}else{
						whoListStatsForChart.push({'name': (wholist.user + "-" + wholist.device), 'data': [[creationDate, 1]]})
					}
				});
			}

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

			function formatToGB(bytes){
				//extract
				return  parseFloat((bytes / 1073741824).toFixed(2));
			}

			function activeFormatter(chart){
				var dateString = moment(chart.point.x).format("DD-MM-YYYY hh:mm");               
	            return  '<b>'+ chart.series.name + '</b><br>' + dateString + " " + formatPointY(chart.point.y);

	            function formatPointY(value){
	            	return value == 1 ? 'Conectado' : 'Desconectado';
	            }
			}
		};

		function dateToMilliseconds(dateString){
			return new Date(dateString).getTime(); 
		}
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