window.mallotore = window.mallotore || {};

(function(mallotore){

	function TemperatureConfigurationViewerPresenter(view){
		this.show = function(){
			view.show();
		};

		this.hide = function(){
			view.hide();
		};

		this.refresh = function(value){
			view.refresh(value);
		};

		this.getTemperature = function(){
			return view.getTemperature();
		};

		this.highlight = function(){
			view.highlight();
		};
	}

	function TemperatureConfigurationViewerView(){
		this.show = function(){
			$("#temperature_interval_label").show();
			$("#temperature_alert_label").show();
			$("#connectivity_alert_temperature_label").show();
		};

		this.hide = function(){
			$("#temperature_interval_label").hide();
			$("#temperature_alert_label").hide();
			$("#connectivity_alert_temperature_label").hide();
		};

		this.refresh = function(temperature){
			$("#temperature_interval_label").text(temperature.intervalInSeconds);
			$("#temperature_alert_label").text(temperature.overTemperatureAlert);
			$("#connectivity_alert_temperature_label").text(temperature.connectivityAlert);
		};

		this.getTemperature = function(){
			return {
				intervalInSeconds: $("#temperature_interval_label").text(),
				overTemperatureAlert: $("#temperature_alert_label").text(),
				connectivityAlert: $("#connectivity_alert_temperature_label").text() == 'Desactivada' ? false : true
			}
		};

		this.highlight = function(){
			$("#temperature_interval_container").effect("highlight", {}, 3000);
			$("#temperature_interval_label").effect("highlight", {}, 3000);
			$("#temperature_alert_label").effect("highlight", {}, 3000);
			$("#connectivity_alert_temperature_label").effect("highlight", {}, 3000);
		};
	}

	function createTemperatureConfigurationViewerPresenter(){
		var view = new TemperatureConfigurationViewerView();

		return new TemperatureConfigurationViewerPresenter(view); 
	}

	mallotore.temperature = mallotore.temperature || {};
	mallotore.temperature.createTemperatureConfigurationViewerPresenter = createTemperatureConfigurationViewerPresenter;
	
})(window.mallotore);