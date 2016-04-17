//= require_tree ../utils
//= require_tree . 
//= require_self

var mallotore = mallotore || {};

$(document).ready(function(){

	function ServerConfigurationCoordinator(creator, editor){
		creator.subscribeToAddedServer(addedServerHandler);

		function addedServerHandler(){
			editor.refreshTriggeredEvents();
		}
	}

	function createServerConfigurationCoordinator(){
		var creator = mallotore.servers.createServerConfigurationCreator();
		var editor = mallotore.servers.createServerConfigurationEditor();
		new ServerConfigurationCoordinator(creator, editor);
	}

	mallotore.temperature.createTemperatureConfigurationCoordinator();
	createServerConfigurationCoordinator();
});
