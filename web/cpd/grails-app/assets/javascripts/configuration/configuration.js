//= require_tree ../lib 
//= require_tree . 
//= require_self

$(document).ready(function(){

	function EditableServerView(){
		var widgets = ['name_text', 'ip_text', 'port_text', 'service_text'];

		this.show = function(id){
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

		this.getServerConfiguration = function(id){
			return {
				id: id,
				name: $("#name_text" + "_" +id).val(),
				ip:$("#ip_text" + "_" +id).val(),
				port:$("#port_text" + "_" +id).val(),
				service:$("#service_text" + "_" +id).val(),
			};
		};
	}

	function ReadOnlyServerView(){
		var widgets = ['name_label', 'ip_label', 'port_label', 'service_label'];

		this.show = function(id){
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
	}

	function ServerConfigurationCoordinator(){
		var editableView = new EditableServerView();
		var readOnlyView = new ReadOnlyServerView();

		this.showReadOnlyView = function(id){
			readOnlyView.show(id);

		};
		this.hideReadOnlyView = function(id){
			readOnlyView.hide(id);
		};
		this.showEditableView = function(id){
			editableView.show(id);

		};
		this.hideEditableView = function(id){
			editableView.hide(id);

		};
		this.updateServerConfiguration = function(id){
			var server = editableView.getServerConfiguration(id);
			$.ajax({
			    url: "/configuration/servers/edit".replace("${id}", id),
			    data: JSON.stringify(server),
			    contentType : 'application/json',
			    type: 'PUT',
			    success: function(data) {
			        $.jGrowl.defaults.closerTemplate = '<div class="alert alert-info">Close All</div>';
                    $('#jGrowl-container1').jGrowl({
                        header: 'success Notification',
                        message: 'Hello world ',
                        group: 'alert-success',
                        life: 5000
                    });
			    },
			    error: function(xhr){
					$.jGrowl.defaults.closerTemplate = '<div class="alert alert-info">Close All</div>';
                    $('#jGrowl-container1').jGrowl({
                        header: 'error Notification',
                        message: 'Hello world ',
                        group: 'alert-error',
                        life: 5000
                    });
			    }
			});
		};
		this.deleteServerConfiguration = function(id){

			$.ajax({
			    url: "/configuration/servers/${id}/delete".replace("${id}", id),
			    type: 'DELETE',
			    success: function(data) {
			        $("#server_configuration_container_"+ id).remove();
			        $.jGrowl.defaults.closerTemplate = '<div class="alert alert-info">Close All</div>';
                    $('#jGrowl-container1').jGrowl({
                        header: 'success Notification',
                        message: 'Hello world ',
                        group: 'alert-success',
                        life: 5000
                    });
			    },
			    error: function(xhr){
					$.jGrowl.defaults.closerTemplate = '<div class="alert alert-info">Close All</div>';
                    $('#jGrowl-container1').jGrowl({
                        header: 'error Notification',
                        message: 'Hello world ',
                        group: 'alert-error',
                        life: 5000
                    });
			    }
			});
		};
	}

	var serverConfiguration = new ServerConfigurationCoordinator();
	

	$('button[id^="showEditServerButton_"]').click(function(event) {
	    event.preventDefault();
	    var id = this.id.substring('showEditServerButton_'.length, this.id.length);
	    serverConfiguration.hideReadOnlyView(id);
	    serverConfiguration.showEditableView(id);
	});

	$('button[id^="cancelServerEditionButton_"]').click(function(event) {
	    event.preventDefault();
	    var id = this.id.substring('cancelServerEditionButton_'.length, this.id.length);
	   	serverConfiguration.hideEditableView(id);
	    serverConfiguration.showReadOnlyView(id);
	});

	$('button[id^="updateServerButton_"]').click(function(event) {
	    event.preventDefault();
	    var id = this.id.substring('updateServerButton_'.length, this.id.length);
	    serverConfiguration.updateServerConfiguration(id);
	});

	$('button[id^="deleteServerButton_"]').click(function(event) {
	    event.preventDefault();
	    var id = this.id.substring('deleteServerButton_'.length, this.id.length);
		serverConfiguration.deleteServerConfiguration(id);
	});
});