//= require_tree ../lib/highcharts
//= require ../lib/moment.min.js
//= require_tree ../utils
//= require historical-charts.js
//= require_self

$(document).ready(function(){

	function HistoricalStatsPresenter(chartsPresenter, client, notifier){
		var servers;

		function retrieveStats(){
			client.get("/stats/overview/servers", {}, successCallback, errorCallback);

			function successCallback(receivedServers){
				servers = receivedServers;
				renderServersStats();

				function renderServersStats(){
					_(servers).forEach(function(server) {
						var url = "/stats/historical/servers/{ip}".replace("{ip}", server.ip);
						client.get(url, {}, serverStatsSuccessCallback, serverStatsErrorCallback);
					    
					    function serverStatsSuccessCallback(data){
					    	var serverStats = data.serverStats;
					    	chartsPresenter.render(server, serverStats);
						}

						function serverStatsErrorCallback(){
							notifier.notifyError("Estadísticas", "Error recibiendo estadísticas del servidor");
						}
					});	
				}
			}

			function errorCallback(xhr){
				notifier.notifyError("Estadísticas", "Error recibiendo los servidores");
			}
		}

		retrieveStats();
	}

	function createHistoricalStatsPresenter(){
		var client =  mallotore.utils.ajaxClient;
		var notifier = mallotore.utils.notifier;
		var chartsPresenter = mallotore.stats.createHistoricalStatsChartsPresenter()

		return new HistoricalStatsPresenter(chartsPresenter, client, notifier); 
	}

	createHistoricalStatsPresenter();
});