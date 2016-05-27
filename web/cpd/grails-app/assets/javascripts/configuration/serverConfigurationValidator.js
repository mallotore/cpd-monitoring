window.mallotore = window.mallotore || {};

(function(mallotore){

	function ServerConfigurationValidator(notifier){

		this.validate = function(server){
			var result = true;
			if(!server.name){
				notifier.notifyError("Servidor", "El nombre es obligatorio");
				result = false;
			}
			if(!server.ip){
				notifier.notifyError("Servidor", "La ip es obligatoria");
				result = false;
			}
			if(!server.port){
				notifier.notifyError("Servidor", "El puerto es obligatorio");
				result = false;
			}
			if(!server.probeInterval){
				notifier.notifyError("Servidor", "El intervalo de sondeo es obligatorio");
				result = false;
			}
			if(server.diskPercentageAlert && !isIntFromString(server.diskPercentageAlert)){
				notifier.notifyError("Servidor", "El porcentage de disco para la alerta debe ser mayor que cero y sin decimales");
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

	function createServerConfigurationValidator(){
		var notifier =  mallotore.utils.notifier;
		return new ServerConfigurationValidator(notifier); 
	}

	mallotore.servers = mallotore.servers || {};
	mallotore.servers.createServerConfigurationValidator = createServerConfigurationValidator;
	
})(window.mallotore);