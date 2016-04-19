//= require_tree ../lib/highcharts
//= require_tree ../utils
//= require_tree . 
//= require_self

var mallotore = mallotore || {};

$(document).ready(function(){

	function createDiskPercentage(server, diskRootSpace){
		var formatter = mallotore.utils.bytesFormatter;
		var usedSpace = diskRootSpace.totalSpace - diskRootSpace.freeSpace;
		var seriesData = [
			{name: "Libre", y: diskRootSpace.freeSpace}, 
			{name: "Ocupado", y: usedSpace}
		];
		var chart = new Highcharts.Chart({
	        chart: {
	        	renderTo : 'diskPercentage_' + server.id + diskRootSpace.path,
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false,
	            type: 'pie'
	        },
	        credits: {
	    		enabled: false
	  		},
	        title: {
	            text: 'Espacio en disco en ' + diskRootSpace.path + ' ' + formatter.format(diskRootSpace.totalSpace)
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
	            name: 'Espacio',
	            colorByPoint: true,
	            data: seriesData
	        }]
	    });
	}

	function OverviewStatsPresenter(client, notifier){
		
		function findServers(){
			client.get("/stats/overview/servers", {}, successCallback, errorCallback);
			return;

			function successCallback(servers){
				_(servers).forEach(function(server) {
					client.get("/stats/overview/servers/" + server.ip, {}, serverStatsSuccessCallback, serverStatsErrorCallback);
				    
				    function serverStatsSuccessCallback(data){
				    	var serverStats = data.serverStats;
				    	_(serverStats.diskRootsSpace).forEach(function(diskRootSpace) {
				    		createDiv(server.id, diskRootSpace.path);
							createDiskPercentage(server, diskRootSpace);
						});
					}

					function serverStatsErrorCallback(){
						notifier.notifyError("Estadísticas", "Error recibiendo estadísticas del servidor");
					}
				});
			}

			function errorCallback(xhr){
				notifier.notifyError("Estadísticas", "Error recibiendo los servidores");
			}
		}

		findServers();
	}

	function createDiv(id, path){
		$('#overviewStats_' + id).append('<div id="diskPercentage_' + id + path + '"'+ 'style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>');
	}

	function createOverviewStatsPresenter(){
		var client =  mallotore.utils.ajaxClient;
		var notifier = mallotore.utils.notifier;

		return new OverviewStatsPresenter(client, notifier); 
	}

	createOverviewStatsPresenter();
});