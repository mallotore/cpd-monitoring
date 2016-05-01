window.mallotore = window.mallotore || {};

(function(mallotore){

	function TemperatureConfigurationCreatorPresenter(view, client, notifier){
		var createdTemperatureIntervalEventHandler = function(){};
		view.subscribeToCreateTemperatureIntervalRequestedEvent(createTemperatureIntervalRequestedHandler);

		function createTemperatureIntervalRequestedHandler(temperature){
			var temperatureRequest = {
					probeIntervalInSeconds: temperature.intervalInSeconds,
					overTemperatureAlert:temperature.overTemperatureAlert,
					connectivityAlert: temperature.connectivityAlert
			};
			client.post("/configuration/temperature", temperatureRequest, successCallback, errorCallback);
			return;

			function successCallback(data){
				createdTemperatureIntervalEventHandler(temperature);
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
			    var overTemperatureAlert = $("#create_temperature_alert_text").val();
			    var connectivityAlert = $("#connectivity_alert_temperature_text").prop('checked');
			    createTemperatureIntervalRequestedHandler(
			    	{intervalInSeconds: intervalInSeconds,
			    	overTemperatureAlert: overTemperatureAlert,
			    	connectivityAlert: connectivityAlert
			    });
			});
		};

		this.show = function(){
			$("#createTemperatureIntervalButton").show();
			$("#create_temperature_interval_text").show();
			$("#create_temperature_alert_text").show();
			$("#connectivity_alert_temperature_text").prop('checked', false);
			$("#connectivity_alert_temperature_text").show();
		};

		this.hide = function(){
			$("#create_temperature_interval_text").val("");
			$("#create_temperature_alert_text").val("");
			$("#connectivity_alert_temperature_text").prop('checked', false);
			$("#create_temperature_alert_text").hide();
			$("#connectivity_alert_temperature_text").hide();
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
	
})(window.mallotore);