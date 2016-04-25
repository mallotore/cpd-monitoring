window.mallotore = window.mallotore || {};

(function(mallotore){

	function ServerConfigurationCreator(view, notifier, client){
		var addedServerHandler = function(){};
		view.subscribeToAddServerRequestedEvent(addServerRequestedHandler);
		view.subscribeToShowAddServerRequestedEvent(showAddServerRequestedHandler);
		view.subscribeToHideAddServerRequestedEvent(hideAddServerRequestedHandler);

		this.subscribeToAddedServer = function(handler){
			addedServerHandler = handler;
		};

		function addServerRequestedHandler(server){
			client.post("/configuration/servers", server, successCallback, errorCallback);
			return;

			function successCallback(data){
				view.hide();
              	view.addServer(server, data.server.id);
                notifier.notifySuccess("Servidor", "Creado correctamente");
                addedServerHandler();
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
			$("#serversTotal").text($("#serversTotal").text() * 1 + 1);
	    	$("#name_label_"+id).text(server.name);
			$("#ip_label_" +id).text(server.ip);
			$("#port_label_" +id).text(server.port);
			$("#probeInterval_label_" +id).text(server.probeInterval);
			var diskSpaceAlertText = server.diskPercentageAlert || 'Desactivada';
			$("#diskSpaceAlert_label_" + id).text(diskSpaceAlertText);
			var connectivityAlerText = server.connectivityAlert ? 'Activada' : 'Desactivada';
			$("#connectivityAlert_label_" + id).text(connectivityAlerText);
			$("#server_configuration_container_" + id).effect("highlight", {}, 3000);;

			function addTemplate(){
				var html = $("#server_configuration_container_template").html();
          		var htmlBuilt = html.replace(new RegExp("#server.id#", 'g'), id);
	    		$('#serverConfigurationTable').append('<tr id="server_configuration_container_' + id + '" style="font-weight: normal !important;display:none;">'+htmlBuilt + '</tr>');
			}
		};

		this.show = function(){
			$("#"+container).show();
		};

		this.hide = function(){
			$("#name_addNewServer").val('');
			$("#ip_addNewServer").val('');
			$("#port_addNewServer").val('');
			$("#probeInterval_addNewServer").val('');
			$("#diskSpaceAlert_addNewServer").val('');
			$("#connectivityAlert_addNewServer").prop('checked', false);
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
					probeInterval: $("#probeInterval_addNewServer").val(),
					diskPercentageAlert: $("#diskSpaceAlert_addNewServer").val(),
					connectivityAlert: $("#connectivityAlert_addNewServer").prop('checked')
				}
				addServerRequestedHandler(server);
			});
		}

		subscribeEvents();
	}

	function createServerConfigurationCreator(){
		var view = new ServerConfigurationCreatorView();
		var notifier = mallotore.utils.notifier;
		var client = mallotore.utils.ajaxClient;
		return new ServerConfigurationCreator(view, notifier, client);
	}

	mallotore.servers = mallotore.servers || {};
	mallotore.servers.createServerConfigurationCreator = createServerConfigurationCreator;
	
})(window.mallotore);