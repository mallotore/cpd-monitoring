//= require_tree ../lib/highcharts
//= require ../lib/moment.min.js
//= require_tree ../lib/react
//= require_tree ../utils
//= require overview-charts.js
//= require ../dist/overview-info.min.js
//= require_self

$(document).ready(function(){

	function OverviewStatsPresenter(view, chartsPresenter, client, notifier){
		var servers;
		var overviewStatsInterval = function(){};
		view.subscribeToEditIntervalRequestedEvent(editIntervalRequestedHandler);

		function editIntervalRequestedHandler(intervalInSeconds){
			clearInterval(overviewStatsInterval);
			updateStatsWithInterval(intervalInSeconds);
			notifier.notifySuccess("Refresco", "Intervalo actualizado a " + intervalInSeconds + " segundos");
		}
		
		function findServers(){
			client.get("/stats/overview/servers", {}, successCallback, errorCallback);

			function successCallback(receivedServers){
				servers = receivedServers;
				var defaultIntervalInSeconds = view.getInterval();
				updateStats();
				updateStatsWithInterval(defaultIntervalInSeconds);
			}

			function errorCallback(xhr){
				notifier.notifyError("Estadísticas", "Error recibiendo los servidores");
			}
		}

		function updateStatsWithInterval(intervalInSeconds){
			overviewStatsInterval = setInterval( updateStats, intervalInSeconds * 1000);
		}

		function updateStats(){
			_(servers).forEach(function(server) {
				var url = "/stats/overview/servers/{ip}/{port}".replace("{ip}", server.ip)
															   .replace("{port}", server.port);
				client.get(url, {}, serverStatsSuccessCallback, serverStatsErrorCallback);
			    
			    function serverStatsSuccessCallback(data){
			    	var serverStats = data.serverStats;
			    	chartsPresenter.render(server, serverStats);
			    	mallotore.stats.renderOverviewNetStatsInformation(server, serverStats);
			    	mallotore.stats.renderOverviewOperatingSystemInfo(server, serverStats);
			    	mallotore.stats.renderOverviewStatsCreationDate(server, serverStats);
			    	mallotore.stats.renderOverviewStatsUptime(server, serverStats);
				}

				function serverStatsErrorCallback(){
					mallotore.stats.renderOverviewStatsUptime(server, {uptimeStats:{error: "Servidor o agente caido"}});
					notifier.notifyError("Estadísticas", "Error recibiendo estadísticas del servidor");
				}
			});	
		}

		findServers();
	}

	function OverviewStatsView(){
		var editIntervalRequestedHandler = function(){};
		var self = this;

		this.subscribeToEditIntervalRequestedEvent = function(handler){
			editIntervalRequestedHandler = handler;
		};

		this.subscribeEvents = function (){
			$('#updateRefreshInterval').on("click",function(event) {
			    event.preventDefault();
			    editIntervalRequestedHandler(self.getInterval());
			});
		};

		this.getInterval = function(){
			return $("#overviewStatsInterval").val();
		};

		this.subscribeEvents();
	}

	function createOverviewStatsPresenter(){
		var client =  mallotore.utils.ajaxClient;
		var notifier = mallotore.utils.notifier;
		var view = new OverviewStatsView();
		var chartsPresenter = mallotore.stats.createOverviewStatsChartsPresenter()

		return new OverviewStatsPresenter(view, chartsPresenter, client, notifier); 
	}

	createOverviewStatsPresenter();
});