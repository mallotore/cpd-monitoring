var mallotore = mallotore || {};

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

	function OverviewStatsChartsPresenter(){

		this.render = function(server, serverStats){
			_(serverStats.diskRootsSpace).forEach(function(diskRootSpace) {
				createDiskPercentage(server, diskRootSpace);
			});
			createRamMemoryPercentage(server, serverStats.memStats);
			createSwapMemoryPercentage(server, serverStats.memStats);
			createCpuTotalsPercentage(server, serverStats.cpuStats.totals);
		};
	}

	function createOverviewStatsChartsPresenter(){
		return new OverviewStatsChartsPresenter(); 
	}

	mallotore.stats = mallotore.stats || {};
	mallotore.stats.createOverviewStatsChartsPresenter = createOverviewStatsChartsPresenter;

})(mallotore);