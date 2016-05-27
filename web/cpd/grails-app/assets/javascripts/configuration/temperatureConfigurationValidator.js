window.mallotore = window.mallotore || {};

(function(mallotore){

	function TemperatureConfigurationValidator(notifier){

		this.validate = function(temperature){
			var result = true;
			if(!temperature.probeIntervalInSeconds && temperature.probeIntervalInSeconds < 1){
				notifier.notifyError("Temperatura", "El intervalo debe ser de un mínimo de 1 segundos y sin decimales");
				result = false;
			}
			if(temperature.overTemperatureAlert && !isIntFromString(temperature.overTemperatureAlert)){
				notifier.notifyError("Temperatura", "La alerta de sobrepaso de temperatura debe ser sin decimales");
				result = false;
			}
			return result;

			function isIntFromString(value){
				try{
					if(value.includes(",") || value.includes(".")){
						return false;
					}
					return Number.isInteger(parseInt(value));	
				}
				catch(e){
					return false;
				}
			}
		};
	}

	function createTemperatureConfigurationValidator(){
		var notifier =  mallotore.utils.notifier;
		return new TemperatureConfigurationValidator(notifier); 
	}

	mallotore.temperature = mallotore.temperature || {};
	mallotore.temperature.createTemperatureConfigurationValidator = createTemperatureConfigurationValidator;
	
})(window.mallotore);