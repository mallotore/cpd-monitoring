window.mallotore = window.mallotore || {};

(function(mallotore){

	function ServerConfigurationEditor(editableView, readOnlyView, notifier, client){
		var validator = mallotore.servers.createServerConfigurationValidator();
		editableView.subscribeToShowEditServerRequestedEvent(showEditHandler);
		editableView.subscribeToEditServerRequestedEvent(editServerHandler);
		editableView.subscribeToDeleteServerRequestedEvent(deleteServerHandler);
		readOnlyView.subscribeToShowReadOnlyServerRequestedEvent(showReadOnlyServerHandler);

		this.refreshTriggeredEvents = function(){
			editableView.subscribeEvents();
			readOnlyView.subscribeEvents();
		};

		function showEditHandler(id){
			readOnlyView.hide(id);
			var server = readOnlyView.getServerConfiguration(id);
			editableView.show(server, id);
		}
		function editServerHandler(id){
			var server = editableView.getServerConfiguration(id);
			if(!validator.validate(server)){
				return;
			}
			client.put("/configuration/servers/edit".replace("${id}", id), server, successCallback, errorCallback);
			return;

			function successCallback(data){
		    	readOnlyView.refresh(server);
		    	editableView.hide(id);
		    	readOnlyView.show(id);
		    	notifier.notifySuccess("Servidor", "Actualizado correctamente");
			}

			function errorCallback(xhr){
				notifier.notifyError("Servidor", "Error en la actualización");
			}
		}
		function deleteServerHandler(id){
			client.delete("/configuration/servers/${id}/delete".replace("${id}", id),{}, successCallback, errorCallback);
			return;

			function successCallback(data){
				editableView.remove(id);
		        notifier.notifySuccess("Servidor", "Borrado correctamente");
			}

			function errorCallback(xhr){
				notifier.notifyError("Servidor", "Error en el borrado");
			}
		}
		function showReadOnlyServerHandler(id){
			editableView.hide(id);
			readOnlyView.show(id);
		}
	}

	function EditableServerView(){
		var widgets = ['name_text', 'ip_text', 'port_text', 'probeInterval_text', 'connectivityAlert_text', 'diskSpaceAlert_text'];
		var editServerRequestedHandler = function(){};
		var deleteServerRequestedHandler = function(){};
		var showEditServerRequestedHandler = function(){};

		this.subscribeToEditServerRequestedEvent = function(handler){
			editServerRequestedHandler = handler;
		};
		this.subscribeToDeleteServerRequestedEvent = function(handler){
			deleteServerRequestedHandler = handler;
		};
		this.subscribeToShowEditServerRequestedEvent = function(handler){
			showEditServerRequestedHandler = handler;
		};

		this.subscribeEvents = function (){
			$('button[id^="showEditServerButton_"]').on("click",function(event) {
			    event.preventDefault();
			    var id = this.id.substring('showEditServerButton_'.length, this.id.length);
			    showEditServerRequestedHandler(id);
			});

			$('button[id^="updateServerButton_"]').on("click",function(event) {
			    event.preventDefault();
			    var id = this.id.substring('updateServerButton_'.length, this.id.length);
			    editServerRequestedHandler(id);
			});

			$('button[id^="deleteServerButton_"]').on("click",function(event) {
			    event.preventDefault();
			    var id = this.id.substring('deleteServerButton_'.length, this.id.length);
				deleteServerRequestedHandler(id);
			});
		};

		this.show = function(server, id){
			$("#name_text_"+server.id).val(server.name);
			$("#ip_text_" +server.id).val(server.ip);
			$("#port_text_" +server.id).val(server.port);
			$("#probeInterval_text_" +server.id).val(server.probeInterval);
			$("#diskSpaceAlert_text_" + id).val(server.diskPercentageAlert);
			$("#connectivityAlert_text_" + id).prop('checked', server.connectivityAlert);
			_(widgets).forEach(function(widget){
				$("#"+widget + "_" +id).show();
			});
			$("#editButtons_" + id).show();
		};

		this.hide = function(id){
			_(widgets).forEach(function(widget){
				$("#"+widget + "_" +id).hide();
			});
			$("#editButtons_" + id).hide();
		};

		this.remove = function(id){
			var total = $("#serversTotal").text() * 1;
			total = (total - 1) > 0 ? (total - 1) : 0
			$("#serversTotal").text(total);
			$("#server_configuration_container_" +id).effect("highlight", {}, 3000);
	        $("#server_configuration_container_"+ id).fadeOut();
	        $("#server_configuration_container_"+ id).remove();
		};

		this.getServerConfiguration = function(id){
			return {
				id: id,
				name: $("#name_text_" +id).val(),
				ip:$("#ip_text_" +id).val(),
				port:$("#port_text_" +id).val(),
				probeInterval:$("#probeInterval_text_" +id).val(),
				diskPercentageAlert: $("#diskSpaceAlert_text_" + id).val(),
				connectivityAlert: $("#connectivityAlert_text_" + id).prop('checked')
			};
		};

		this.subscribeEvents();
	}

	function ReadOnlyServerView(){
		var disabledAlertText = "Desactivada";
		var widgets = ['name_label', 'ip_label', 'port_label', 'probeInterval_label', 'connectivityAlert_label', 'diskSpaceAlert_label'];
		var showReadOnlyServerHandler = function(){};

		this.subscribeToShowReadOnlyServerRequestedEvent = function(handler){
			showReadOnlyServerHandler = handler;
		};

		this.subscribeEvents = function (){
			$('button[id^="cancelServerEditionButton_"]').on("click",function(event) {
			    event.preventDefault();
			    var id = this.id.substring('cancelServerEditionButton_'.length, this.id.length);
			   	showReadOnlyServerHandler(id);
			});
		};

		this.show = function(id){
			$("#server_configuration_container_" +id).effect("highlight", {}, 3000);
			_(widgets).forEach(function(widget){
				$("#"+widget + "_" +id).show();
			});
			$("#actionButtons_" + id).show();
		};

		this.hide = function(id){
			_(widgets).forEach(function(widget){
				$("#"+widget + "_" +id).hide();
			});
			$("#actionButtons_" + id).hide();
		};

		this.refresh = function(server){
			$("#name_label_"+server.id).text(server.name);
			$("#ip_label_" +server.id).text(server.ip);
			$("#port_label_" +server.id).text(server.port);
			$("#probeInterval_label_" +server.id).text(server.probeInterval);
			var diskSpaceAlertText = server.diskPercentageAlert || disabledAlertText;
			$("#diskSpaceAlert_label_" +  server.id).text(diskSpaceAlertText);
			var connectivityAlerText = server.connectivityAlert ? 'Activada' : disabledAlertText;
			$("#connectivityAlert_label_" + server.id).text(connectivityAlerText);
		};

		this.getServerConfiguration = function(id){
			var diskPercentageAlert = $("#diskSpaceAlert_label_" + id).text() != disabledAlertText ? $("#diskSpaceAlert_label_" + id).text() : 0;
			var connectivityAlert = $("#connectivityAlert_label_" + id).text() != disabledAlertText ? $("#connectivityAlert_label_" + id).text() : false ;  
			return {
				id: id,
				name: $("#name_label_" +id).text(),
				ip:$("#ip_label_" +id).text(),
				port:$("#port_label_" +id).text(),
				probeInterval:$("#probeInterval_label_" +id).text(),
				diskPercentageAlert: diskPercentageAlert,
				connectivityAlert: connectivityAlert
			};
		};

		this.subscribeEvents();
	}

	function createServerConfigurationEditor(){
		var editableView = new EditableServerView();
		var readOnlyView = new ReadOnlyServerView();
		var notifier =  mallotore.utils.notifier;
		var client =  mallotore.utils.ajaxClient;

		return new ServerConfigurationEditor(editableView, readOnlyView, notifier, client); 
	}

	mallotore.servers = mallotore.servers || {};
	mallotore.servers.createServerConfigurationEditor = createServerConfigurationEditor;
	
})(window.mallotore);