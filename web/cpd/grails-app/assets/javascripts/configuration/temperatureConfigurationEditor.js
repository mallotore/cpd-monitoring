window.mallotore = window.mallotore || {};

(function(mallotore){

	function TemperatureConfigurationEditorPresenter(view, notifier, client){
		var editedTemperatureIntervalEventHandler = function(){};
		view.subscribeToEditTemperatureIntervalRequestedEvent(editTemperatureIntervalHandler);

		function editTemperatureIntervalHandler(intervalInSeconds){
			var temperature = {
				probeIntervalInSeconds: intervalInSeconds
			};
			client.put("/configuration/temperature/edit", temperature, successCallback, errorCallback);
			return;

			function successCallback(data){
				editedTemperatureIntervalEventHandler(intervalInSeconds);
		    	notifier.notifySuccess("Temperatura", "Actualización correcta");
			}

			function errorCallback(xhr){
				notifier.notifyError("Temperatura", "Error en la actualización");
			}
		}
		
		this.subscribeToEditedTemperatureIntervalEvent = function(handler){
			editedTemperatureIntervalEventHandler = handler;
		};

		this.show = function(intervalInSeconds){
			view.show(intervalInSeconds);
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
			    editTemperatureIntervalRequestedHandler(intervalInSeconds);
			});
		};

		this.show = function(intervalInSeconds){
			$("#edit_temperature_interval_text").val(intervalInSeconds);
			$("#editTemperatureIntervalButton").show();
			$("#edit_temperature_interval_text").show();
		};

		this.hide = function(){
			$("#edit_temperature_interval_text").hide();
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