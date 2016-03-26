//= require_tree ../lib 
//= require_tree ../utils
//= require_tree . 
//= require_self

var mallotore = mallotore || {};

$(document).ready(function(){

	function ConfigurationCoordinator(creator, editor){
		creator.subscribeToAddedServer(addedServerHandler);

		function addedServerHandler(){
			editor.refreshTriggeredEvents();
		}
	}

	function createConfigurationCoordinator(){
		var creator = mallotore.servers.createServerConfigurationCreator();
		var editor = mallotore.servers.createServerConfigurationEditor();
		new ConfigurationCoordinator(creator, editor);
	}

	createConfigurationCoordinator();
});
