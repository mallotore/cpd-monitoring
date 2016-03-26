//= require_self

var mallotore = mallotore || {};

$(document).ready(function(){

	function ServerConfigurationCreator(view, notifier, client){
		
		view.subscribeToAddServerRequestedEvent(addServerRequestedHandler);
		view.subscribeToShowAddServerRequestedEvent(showAddServerRequestedHandler);
		view.subscribeToHideAddServerRequestedEvent(hideAddServerRequestedHandler);

		function addServerRequestedHandler(server){
			client.post("/configuration/servers", server, successCallback, errorCallback);
			return;

			function successCallback(data){
				view.hide();
              	view.addServer(server, data.server.id);
                notifier.notifySuccess("Servidor", "Creado correctamente");
			}

			function errorCallback(xhr){
				notifier.notifyError("Servidor", "Error en la creación");
			}
		}
		function hideAddServerRequestedHandler(){
			view.hide();
		}
		function showAddServerRequestedHandler(){
			view.show();
		}
	}

	function ServerConfigurationCreatorView(){
		var container = "addNewServerContainer";
		var widgets = ['name_addNewServer', 'ip_addNewServer', 'port_addNewServer', 'service_addNewServer'];
		var self = this;
		var addServerRequestedHandler = function(){};
		var showAddServerRequestedHandler = function(){};
		var hideAddServerRequestedHandler = function(){};

		this.subscribeToHideAddServerRequestedEvent = function(handler){
			hideAddServerRequestedHandler = handler;
		};
		this.subscribeToAddServerRequestedEvent = function(handler){
			addServerRequestedHandler = handler;
		};
		this.subscribeToShowAddServerRequestedEvent = function(handler){
			showAddServerRequestedHandler = handler;
		};

		this.addServer = function(server, id){
			addTemplate();
	    	$("#name_label_"+id).text(server.name);
			$("#ip_label_" +id).text(server.ip);
			$("#port_label_" +id).text(server.port);
			$("#service_label_" +id).text(server.service);
			$("#server_configuration_container_" + id).toggle( "highlight" );

			function addTemplate(){
				var html = $("#server_configuration_container_template").html();
          		var htmlBuilt = html.replace(new RegExp("#server.id#", 'g'), id);
	    		$('#serverConfigurationTable').append('<tr id="server_configuration_container_' + data.server.id + '" style="font-weight: normal !important;display:none;">'+htmlBuilt + '</tr>');
			}
		};

		this.show = function(){
			$("#"+container).show();
		};

		this.hide = function(){
			$("#"+container).hide();
		};

		function subscribeEvents(){
			$('#showAddNewServer').click(function(){
				showAddServerRequestedHandler();
			});

			$('#cancel_addNewServer').click(function(){
				hideAddServerRequestedHandler();
			});

			$('#save_addNewServer').click(function(){
				var server = {
					name: $("#name_addNewServer").val(),
					ip: $("#ip_addNewServer").val(),
					port: $("#port_addNewServer").val(),
					service: $("#service_addNewServer").val()
				}
				addServerRequestedHandler(server);
			});
		}

		subscribeEvents();
	}

	function createServerConfigurationCreator(){
		var view = new ServerConfigurationCreatorView();
		var notifier = mallotore.utils.notifier;
		new ServerConfigurationCreator(view, notifier);
	}

	createServerConfigurationCreator();
});