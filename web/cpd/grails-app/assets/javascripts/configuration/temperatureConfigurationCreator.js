var mallotore = mallotore || {};

$(document).ready(function(){

	function TemperatureConfigurationCreatorPresenter(view, client, notifier){
		var createdTemperatureIntervalEventHandler = function(){};
		view.subscribeToCreateTemperatureIntervalRequestedEvent(createTemperatureIntervalRequestedHandler);

		function createTemperatureIntervalRequestedHandler(intervalInSeconds){
			var temperature = {
					probeIntervalInSeconds: intervalInSeconds
			};
			client.post("/configuration/temperature", temperature, successCallback, errorCallback);
			return;

			function successCallback(data){
				createdTemperatureIntervalEventHandler(intervalInSeconds);
                notifier.notifySuccess("Temperatura", "Creado correctamente");
			}

			function errorCallback(xhr){
				notifier.notifyError("Temperatura", "Error en la creación");
			}
		}

		this.show = function(){
			view.show();
		};

		this.hide = function(){
			view.hide();
		};

		this.subscribeToCreatedTemperatureIntervalEvent = function(handler){
			createdTemperatureIntervalEventHandler = handler;
		};
	}

	function TemperatureConfigurationCreatorView(){
		var createTemperatureIntervalRequestedHandler = function(){};

		this.subscribeToCreateTemperatureIntervalRequestedEvent = function(handler){
			createTemperatureIntervalRequestedHandler = handler;
		};

		this.subscribeEvents = function (){
			$("#createTemperatureIntervalButton").on("click",function(event) {
			    event.preventDefault();
			    var intervalInSeconds = $("#create_temperature_interval_text").val();
			    createTemperatureIntervalRequestedHandler(intervalInSeconds);
			});
		};

		this.show = function(){
			$("#createTemperatureIntervalButton").show();
			$("#create_temperature_interval_text").show();
		};

		this.hide = function(){
			$("#create_temperature_interval_text").val("")
			$("#create_temperature_interval_text").hide();
			$("#createTemperatureIntervalButton").hide();
		};

		this.subscribeEvents();
	}

	function createTemperatureConfigurationCreatorPresenter(){
		var view = new TemperatureConfigurationCreatorView();
		var notifier =  mallotore.utils.notifier;
		var client =  mallotore.utils.ajaxClient;

		return new TemperatureConfigurationCreatorPresenter(view, client, notifier); 
	}

	mallotore.temperature = mallotore.temperature || {};
	mallotore.temperature.createTemperatureConfigurationCreatorPresenter = createTemperatureConfigurationCreatorPresenter;
});