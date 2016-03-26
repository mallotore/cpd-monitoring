//= require_self

$(document).ready(function(){

	function ServerConfigurationCreator(view){
		
		view.subscribeToAddServerRequestedEvent(addServerRequestedHandler);
		view.subscribeToShowAddServerRequestedEvent(showAddServerRequestedHandler);
		view.subscribeToHideAddServerRequestedEvent(hideAddServerRequestedHandler);

		function hideAddServerRequestedHandler(){
			view.hide();
		}

		function showAddServerRequestedHandler(){
			view.show();
		}

		function addServerRequestedHandler(server){
			$.post("/configuration/servers", server,
                  function(data) {
                     console.log(data);
     	            }
		    ).error(function(xhr){
					   console.log(xhr);
			})
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
		new ServerConfigurationCreator(view);
	}

	createServerConfigurationCreator();
});