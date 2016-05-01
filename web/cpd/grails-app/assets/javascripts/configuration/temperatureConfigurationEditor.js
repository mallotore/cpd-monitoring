window.mallotore = window.mallotore || {};

(function(mallotore){

	function TemperatureConfigurationEditorPresenter(view, notifier, client){
		var editedTemperatureIntervalEventHandler = function(){};
		view.subscribeToEditTemperatureIntervalRequestedEvent(editTemperatureIntervalHandler);

		function editTemperatureIntervalHandler(temperature){
			var temperatureRequest = {
				probeIntervalInSeconds: temperature.intervalInSeconds,
				overTemperatureAlert: temperature.overTemperatureAlert,
			    connectivityAlert: temperature.connectivityAlert
			};
			client.put("/configuration/temperature/edit", temperatureRequest, successCallback, errorCallback);
			return;

			function successCallback(data){
				editedTemperatureIntervalEventHandler(temperature);
		    	notifier.notifySuccess("Temperatura", "Actualización correcta");
			}

			function errorCallback(xhr){
				notifier.notifyError("Temperatura", "Error en la actualización");
			}
		}
		
		this.subscribeToEditedTemperatureIntervalEvent = function(handler){
			editedTemperatureIntervalEventHandler = handler;
		};

		this.show = function(temperature){
			view.show(temperature);
		};

		this.hide = function(){
			view.hide();
		};
	}

	function TemperatureConfigurationEditorView(){
		var editTemperatureIntervalRequestedHandler = function(){};

		this.subscribeToEditTemperatureIntervalRequestedEvent = function(handler){
			editTemperatureIntervalRequestedHandler = handler;
		};

		this.subscribeEvents = function (){
			$('#editTemperatureIntervalButton').on("click",function(event) {
			    event.preventDefault();
			    var intervalInSeconds = $("#edit_temperature_interval_text").val();
			    var overTemperatureAlert = $("#edit_temperature_alert_text").val();
				var connectivityAlert = $("#connectivity_alert_temperature_text").prop('checked');
			    editTemperatureIntervalRequestedHandler({
			    	intervalInSeconds: intervalInSeconds,
			    	overTemperatureAlert: overTemperatureAlert,
			    	connectivityAlert: connectivityAlert
			    });
			});
		};

		this.show = function(temperature){
			$("#edit_temperature_interval_text").val(temperature.intervalInSeconds);
			$("#edit_temperature_alert_text").val(temperature.overTemperatureAlert);
			$("#connectivity_alert_temperature_text").prop('checked',temperature.connectivityAlert);
			$("#editTemperatureIntervalButton").show();
			$("#edit_temperature_interval_text").show();
			$("#edit_temperature_alert_text").show();
			$("#connectivity_alert_temperature_text").show();
		};

		this.hide = function(){
			$("#edit_temperature_interval_text").hide();
			$("#edit_temperature_alert_text").hide();
			$("#connectivity_alert_temperature_text").hide();
			$("#editTemperatureIntervalButton").hide();
		};

		this.subscribeEvents();
	}

	function createTemperatureConfigurationEditorPresenter(){
		var view = new TemperatureConfigurationEditorView();
		var notifier =  mallotore.utils.notifier;
		var client =  mallotore.utils.ajaxClient;

		return new TemperatureConfigurationEditorPresenter(view, notifier, client); 
	}

	mallotore.temperature = mallotore.temperature || {};
	mallotore.temperature.createTemperatureConfigurationEditorPresenter = createTemperatureConfigurationEditorPresenter;
	
})(window.mallotore);