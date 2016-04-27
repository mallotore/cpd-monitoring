//= require_tree ../lib/highcharts
//= require ../lib/moment.min.js
//= require_tree ../lib/react
//= require_tree ../utils
//= require overview-charts.js
//= require ../dist/overview-info.min.js
//= require_self

$(document).ready(function(){

	function OverviewStatsPresenter(chartsPresenter, client, notifier){
		
		function findServers(){
			client.get("/stats/overview/servers", {}, successCallback, errorCallback);
			return;

			function successCallback(servers){
				_(servers).forEach(function(server) {
					client.get("/stats/overview/servers/" + server.ip, {}, serverStatsSuccessCallback, serverStatsErrorCallback);
				    
				    function serverStatsSuccessCallback(data){
				    	var serverStats = data.serverStats;
				    	chartsPresenter.render(server, serverStats);
				    	mallotore.stats.renderOverviewNetStatsInformation(server, serverStats);
				    	mallotore.stats.renderOverviewOperatingSystemInfo(server, serverStats);
				    	mallotore.stats.renderOverviewStatsCreationDate(server, serverStats);
				    	mallotore.stats.renderOverviewStatsUptime(server, serverStats);
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

	function createOverviewStatsPresenter(){
		var client =  mallotore.utils.ajaxClient;
		var notifier = mallotore.utils.notifier;
		var chartsPresenter = mallotore.stats.createOverviewStatsChartsPresenter()

		return new OverviewStatsPresenter(chartsPresenter, client, notifier); 
	}

	createOverviewStatsPresenter();
});