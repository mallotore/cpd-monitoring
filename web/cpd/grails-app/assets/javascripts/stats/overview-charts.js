window.mallotore = window.mallotore || {};

(function(mallotore){

	function createDiskPercentage(server, diskRootSpace){
		var formatter = mallotore.utils.bytesFormatter;
		var usedSpace = diskRootSpace.totalSpace - diskRootSpace.freeSpace;
		var domId = 'diskPercentage_' + server.id + diskRootSpace.path;
		var title = 'Espacio en ' + diskRootSpace.path + ' ' + formatter.format(diskRootSpace.totalSpace);
		var name = 'Espacio';
		var seriesData = [
			{name: "Libre", y: diskRootSpace.freeSpace}, 
			{name: "Ocupado", y: usedSpace}
		];
		createDiskPercentageContainer();
		createPieChart(domId, title, name, seriesData);

		function createDiskPercentageContainer(){
			$('#diskPercentage_' + server.id).append('<div id="' + domId + '" style="height: 250px;"></div>');
		}
	}

	function createRamMemoryPercentage(server, memStats){
		var formatter = mallotore.utils.bytesFormatter;
		var seriesData = [
			{name: "Libre", y: memStats.memFree}, 
			{name: "Ocupado", y: memStats.memUsed}
		];
		var domId = 'ramPercentage_' + server.id;
		var name = 'RAM';
		var title = 'RAM ' + formatter.format(memStats.memTotal);
		createPieChart(domId, title, name, seriesData);
	}

	function createSwapMemoryPercentage(server, memStats){
		var formatter = mallotore.utils.bytesFormatter;
		var seriesData = [
			{name: "Libre", y: memStats.swapFree}, 
			{name: "Ocupado", y: memStats.swapUsed}
		];
		var domId = 'swapPercentage_' + server.id;
		var name = 'SWAP';
		var title = 'SWAP ' + formatter.format(memStats.swapTotal);
		createPieChart(domId, title, name, seriesData);
	}

	function createCpuTotalsPercentage(server, cpuStats){
		var seriesData = [
			{name: "IrqTime", y: format(cpuStats.irqTime)}, 
			{name: "Combined", y: format(cpuStats.combined)}, 
			{name: "UserTime", y: format(cpuStats.userTime)},
			{name: "StolenTime", y: format(cpuStats.stolenTime)}, 
			{name: "NiceTime", y: format(cpuStats.niceTime)}, 
			{name: "IdleTime", y: format(cpuStats.idleTime)},
			{name: "SysTime", y: format(cpuStats.sysTime)}, 
			{name: "SoftIrqTime", y: format(cpuStats.softIrqTime)}, 
			{name: "WaitTime", y: format(cpuStats.waitTime)}
		];
		var domId = 'cpuPercentage_' + server.id;
		var name = 'CPU';
		var title = 'CPU ';
		createPieChart(domId, title, name, seriesData);

		function format(value){
			return value.replace("%", "") * 1;
		}
	}

	function createWhoList(server, whoListStats){
		var ySeriesData = _.map(whoListStats, function(who) {
			var begin =  moment(who.time, "YYYY-MM-DDTHH:mm:ss");
			var end = moment();
			var duration = moment.duration(end.diff(begin));
			var hours = duration.asHours();
			return [who.user, hours];
		});
		var xSeriesData = _.map(whoListStats, function(who) {
			return [(who.user + "-" + who.device)];
		});
		var domId = 'whoList_' + server.id;
		var name = 'Usuarios conectados';
		var title = 'Usuarios conectados';
		createColumnChart(domId, title, name, xSeriesData, ySeriesData);
	}

	function createPieChart(domId, title, name, seriesData){
		new Highcharts.Chart({
	        chart: {
	        	renderTo : domId,
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false,
	            type: 'pie'
	        },
	        credits: {
	    		enabled: false
	  		},
	        title: {
	            text: title
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
	            name: name,
	            colorByPoint: true,
	            data: seriesData
	        }]
	    });
	}

	function createColumnChart(domId, title, name, xSeriesData, ySeriesData){
		new Highcharts.Chart({
	        chart: {
	        	renderTo : domId,
	            type: 'column'
	        },
	        credits: {
	    		enabled: false
	  		},
	        title: {
	            text: title
	        },
	        xAxis: {
	            type: 'Usuarios',
	            categories: xSeriesData,
	            labels: {
	                rotation: -45,
	                style: {
	                    fontSize: '12px',
	                    fontFamily: 'Verdana, sans-serif'
	                }
	            }
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: 'Duración de la sesión'
	            }
	        },
	        legend: {
	            enabled: false
	        },
	        tooltip: {
	            pointFormat: '<b>{point.y: .1f} horas</b>'
	        },
	        series: [{
		            name: name,
		            data: ySeriesData,
		            dataLabels: {
		                enabled: true,
		                rotation: -90,
		                color: '#FFFFFF',
		                align: 'right',
		                format: '{point.y: .1f}',
		                y: 10,
		                style: {
		                    fontSize: '13px',
		                    fontFamily: 'Verdana, sans-serif'
		                }
		            }
		        }]
		    });
	}

	function OverviewStatsChartsPresenter(){

		this.render = function(server, serverStats){
			_(serverStats.diskRootsSpace).forEach(function(diskRootSpace) {
				createDiskPercentage(server, diskRootSpace);
			});
			createRamMemoryPercentage(server, serverStats.memStats);
			createSwapMemoryPercentage(server, serverStats.memStats);
			createCpuTotalsPercentage(server, serverStats.cpuStats.totals);
			createWhoList(server, serverStats.wholistStats);
		};
	}

	function createOverviewStatsChartsPresenter(){
		return new OverviewStatsChartsPresenter(); 
	}

	mallotore.stats = mallotore.stats || {};
	mallotore.stats.createOverviewStatsChartsPresenter = createOverviewStatsChartsPresenter;

})(window.mallotore);