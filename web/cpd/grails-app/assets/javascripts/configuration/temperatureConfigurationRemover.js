var mallotore = mallotore || {};

$(document).ready(function(){

	function TemperatureConfigurationRemoverPresenter(view, client, notifier){
		var removedTemperatureIntervalEventHandler = function(){};
		view.subscribeToRemoveTemperatureIntervalRequestedEvent(removeTemperatureIntervalRequestedHandler);

		function removeTemperatureIntervalRequestedHandler(intervalInSeconds){
			client.delete("/configuration/temperature/delete",{}, successCallback, errorCallback);
			return;

			function successCallback(data){
				view.remove();
				removedTemperatureIntervalEventHandler(data);
		        notifier.notifySuccess("Temperatura", "Borrado correctamente");
			}

			function errorCallback(xhr){
				notifier.notifyError("Temperatura", "Error en el borrado");
			}
		}

		this.show = function(){
			view.show();
		};

		this.hide = function(){
			view.hide();
		};

		this.subscribeToRemovedTemperatureIntervalEvent = function(handler){
			removedTemperatureIntervalEventHandler = handler;
		};
	}

	function TemperatureConfigurationRemoverView(){
		var removeTemperatureIntervalRequestedHandler = function(){};

		this.subscribeToRemoveTemperatureIntervalRequestedEvent = function(handler){
			removeTemperatureIntervalRequestedHandler = handler;
		};

		this.subscribeEvents = function (){
			$('#removeTemperatureIntervalButton').on("click",function(event) {
			    event.preventDefault();
				removeTemperatureIntervalRequestedHandler();
			});
		};

		this.show = function(){
			$("#removeTemperatureIntervalButton").show();
		};

		this.hide = function(){
			$("#removeTemperatureIntervalButton").hide();
		};

		this.remove = function(){
			$("#edit_temperature_interval_text").val("");
		};

		this.subscribeEvents();
	}

	function createTemperatureConfigurationRemoverPresenter(){
		var view = new TemperatureConfigurationRemoverView();
		var notifier =  mallotore.utils.notifier;
		var client =  mallotore.utils.ajaxClient;

		return new TemperatureConfigurationRemoverPresenter(view, client, notifier); 
	}

	mallotore.temperature = mallotore.temperature || {};
	mallotore.temperature.createTemperatureConfigurationRemoverPresenter = createTemperatureConfigurationRemoverPresenter;
});