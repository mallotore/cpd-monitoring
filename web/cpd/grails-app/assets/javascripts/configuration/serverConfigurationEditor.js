var mallotore = mallotore || {};

$(document).ready(function(){

	function ServerConfigurationEditor(editableView, readOnlyView, notifier, client){
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
		var widgets = ['name_text', 'ip_text', 'port_text', 'service_text'];
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
			$("#service_text_" +server.id).val(server.service);
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
			$("#server_configuration_container_" +id).toggle( "highlight" );
	        $("#server_configuration_container_"+ id).fadeOut();
	        $("#server_configuration_container_"+ id).remove();
		};

		this.getServerConfiguration = function(id){
			return {
				id: id,
				name: $("#name_text_" +id).val(),
				ip:$("#ip_text_" +id).val(),
				port:$("#port_text_" +id).val(),
				service:$("#service_text_" +id).val(),
			};
		};

		this.subscribeEvents();
	}

	function ReadOnlyServerView(){
		var widgets = ['name_label', 'ip_label', 'port_label', 'service_label'];
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
			_(widgets).forEach(function(widget){
				$("#server_configuration_container_" +id).toggle( "highlight" );
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
			$("#service_label_" +server.id).text(server.service);
		};

		this.getServerConfiguration = function(id){
			return {
				id: id,
				name: $("#name_label_" +id).text(),
				ip:$("#ip_label_" +id).text(),
				port:$("#port_label_" +id).text(),
				service:$("#service_label_" +id).text(),
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
});