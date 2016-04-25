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

		this.getInterval = function(){
			return view.getInterval();
		};

		this.highlight = function(){
			view.highlight();
		};
	}

	function TemperatureConfigurationViewerView(){
		this.show = function(){
			$("#temperature_interval_label").show();
		};

		this.hide = function(){
			$("#temperature_interval_label").hide();
		};

		this.refresh = function(value){
			$("#temperature_interval_label").text(value);
		};

		this.getInterval = function(){
			return $("#temperature_interval_label").text();
		};

		this.highlight = function(){
			$("#temperature_interval_container").effect("highlight", {}, 3000);
			$("#temperature_interval_label").effect("highlight", {}, 3000);
		};
	}

	function createTemperatureConfigurationViewerPresenter(){
		var view = new TemperatureConfigurationViewerView();

		return new TemperatureConfigurationViewerPresenter(view); 
	}

	mallotore.temperature = mallotore.temperature || {};
	mallotore.temperature.createTemperatureConfigurationViewerPresenter = createTemperatureConfigurationViewerPresenter;
	
})(window.mallotore);