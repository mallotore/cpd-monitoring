//= require_tree ../lib/highcharts
//= require ../lib/moment.min.js
//= require ${grails.util.Environment.currentEnvironment.toString() == 'DEVELOPMENT' ? '../lib/react/1-react.js' : '../lib/react/1-react.min.js'}
//= require ${grails.util.Environment.currentEnvironment.toString() == 'DEVELOPMENT' ? '../lib/react/2-react-dom.js' : '../lib/react/2-react-dom.min.js'}
//= require_tree ../utils
//= require ../dist/historical-info.min.js
//= require historical-charts.js
//= require_self

$(document).ready(function(){

	function HistoricalStatsPresenter(chartsPresenter, client, notifier){
		var servers;

		function retrieveStats(){
			renderServerStats();
			renderTemperatureStats();

			function renderServerStats(){
				client.get("/stats/historical/servers", {}, successCallback, errorCallback);
				

				function successCallback(receivedServers){
					servers = receivedServers;
					renderServersStats();

					function renderServersStats(){
						_(servers).forEach(function(server) {
							var url = "/stats/historical/servers/{ip}".replace("{ip}", server.ip);
							client.get(url, {}, serverStatsSuccessCallback, serverStatsErrorCallback);
						    
						    function serverStatsSuccessCallback(data){
						    	var serverStats = data.serverStats;
						    	mallotore.stats.renderHistoricalOperatingSystem(server, serverStats[0]);
						    	mallotore.stats.renderHistoricalNetStatsInformation(server, serverStats[0]);
						    	chartsPresenter.renderServerCharts(server, serverStats);
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

			function renderTemperatureStats(){
				client.get("/stats/historical/temperature", {}, successCallback, errorCallback);
				

				function successCallback(data){
					chartsPresenter.renderTemperature(data.temperatureStats);
				}

				function errorCallback(xhr){
					notifier.notifyError("Estadísticas", "Error recibiendo la temperatura");
				}	
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