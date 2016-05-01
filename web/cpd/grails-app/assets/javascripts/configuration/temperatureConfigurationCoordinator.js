window.mallotore = window.mallotore || {};

(function(mallotore){

	function TemperatureConfigurationCoordinator(creator, remover, editor, viewer, view){
		creator.subscribeToCreatedTemperatureIntervalEvent(createdTemperatureIntervalEventHandler);
		remover.subscribeToRemovedTemperatureIntervalEvent(removedTemperatureIntervalEventHandler);
		editor.subscribeToEditedTemperatureIntervalEvent(editedTemperatureIntervalEventHandler);
		view.subscribeToShowCreateTemperatureIntervalRequested(showCreateTemperatureIntervalEventHandler);
		view.subscribeToShowEditTemperatureIntervalRequested(showEditTemperatureIntervalEventHandler);
		view.subscribeToCancelCreationTemperatureIntervalRequested(cancelCreationTemperatureIntervalEventHandler);
		view.subscribeToCancelEditionTemperatureIntervalRequested(cancelEditionTemperatureIntervalEventHandler);

		function createdTemperatureIntervalEventHandler(temperature){
			creator.hide();
			view.hideCancelCreation();
			view.disableShowCreation();
			viewer.refresh({
				intervalInSeconds: temperature.intervalInSeconds,
		    	overTemperatureAlert: temperature.overTemperatureAlert || "Desactivada",
		    	connectivityAlert: temperature.connectivityAlert ? "Activada" : "Desactivada"
			});
			viewer.show();
			viewer.highlight();
			view.enableShowEdition();
			remover.show();
		}

		function editedTemperatureIntervalEventHandler(temperature){
			editor.hide();
			view.hideCancelEdition();
			viewer.refresh({
				intervalInSeconds: temperature.intervalInSeconds,
		    	overTemperatureAlert: temperature.overTemperatureAlert || "Desactivada",
		    	connectivityAlert: temperature.connectivityAlert ? "Activada" : "Desactivada"
			});
			viewer.show();
			viewer.highlight();
			view.enableShowEdition();
			remover.show();
		}

		function removedTemperatureIntervalEventHandler(){
			remover.hide();
			view.disableShowEdition();
			viewer.refresh({
				intervalInSeconds: "Sin definir",
		    	overTemperatureAlert: "Desactivada",
		    	connectivityAlert: "Desactivada"
			});
			viewer.show();
			viewer.highlight();
			view.enableShowCreation();
		}

		function showCreateTemperatureIntervalEventHandler(){
			viewer.hide();
			view.disableShowCreation();
			creator.show();
			view.showCancelCreation();
		}

		function showEditTemperatureIntervalEventHandler(){
			var temperature = viewer.getTemperature();
			viewer.hide();
			remover.hide();
			editor.show(temperature);
			view.disableShowEdition();
			view.showCancelEdition();
		}

		function cancelCreationTemperatureIntervalEventHandler(){
			creator.hide();
			view.hideCancelCreation();
			view.enableShowCreation();
			viewer.show();
		}

		function cancelEditionTemperatureIntervalEventHandler(){
			editor.hide();
			view.hideCancelEdition();
			view.enableShowEdition();
			remover.show();
			viewer.show();
		}
	}

	function TemperatureConfigurationCoordinatorView(){
		var showCreateTemperatureIntervalRequestedHandler = function(){};
		var showEditTemperatureIntervalRequestedHandler = function(){};
		var cancelCreationTemperatureIntervalRequestedHandler = function(){};
		var cancelEditionTemperatureIntervalRequestedHandler = function(){};

		this.subscribeEvents = function (){
			$("#showCreateTemperatureIntervalActionsButton").on("click",function(event) {
			    event.preventDefault();
			    showCreateTemperatureIntervalRequestedHandler();
			});
			$("#showEditTemperatureIntervalButton").on("click",function(event) {
			    event.preventDefault();
			    showEditTemperatureIntervalRequestedHandler();
			});
			$("#cancelTemperatureIntervalEditionButton").on("click",function(event) {
			    event.preventDefault();
			    cancelEditionTemperatureIntervalRequestedHandler();
			});
			$("#cancelTemperatureIntervalCreationButton").on("click",function(event) {
			    event.preventDefault();
			    cancelCreationTemperatureIntervalRequestedHandler();
			});
		};

		this.subscribeToShowCreateTemperatureIntervalRequested = function(handler){
			showCreateTemperatureIntervalRequestedHandler = handler;
		};

		this.subscribeToShowEditTemperatureIntervalRequested = function(handler){
			showEditTemperatureIntervalRequestedHandler = handler;
		};

		this.subscribeToCancelCreationTemperatureIntervalRequested = function(handler){
			cancelCreationTemperatureIntervalRequestedHandler = handler;
		};

		this.subscribeToCancelEditionTemperatureIntervalRequested = function(handler){
			cancelEditionTemperatureIntervalRequestedHandler = handler;
		};

		this.showCancelCreation = function(){
			$("#cancelTemperatureIntervalCreationButton").show();
		};

		this.hideCancelCreation = function(){
			$("#cancelTemperatureIntervalCreationButton").hide();
		};

		this.enableShowCreation = function(){
			$("#showCreateTemperatureIntervalActionsButton").show();
		};

		this.disableShowCreation = function(){
			$("#showCreateTemperatureIntervalActionsButton").hide();
		};

		this.enableShowEdition = function(){
			$("#showEditTemperatureIntervalButton").show();
		};

		this.disableShowEdition = function(){
			$("#showEditTemperatureIntervalButton").hide();
		};

		this.showCancelEdition = function(){
			$("#cancelTemperatureIntervalEditionButton").show();
		};

		this.hideCancelEdition = function(){
			$("#cancelTemperatureIntervalEditionButton").hide();
		};

		this.subscribeEvents();
	}

	function createTemperatureConfigurationCoordinator(){
		var view = new TemperatureConfigurationCoordinatorView();
		var creator = mallotore.temperature.createTemperatureConfigurationCreatorPresenter();
		var editor = mallotore.temperature.createTemperatureConfigurationEditorPresenter();
		var remover = mallotore.temperature.createTemperatureConfigurationRemoverPresenter();
		var viewer = mallotore.temperature.createTemperatureConfigurationViewerPresenter();

		return new TemperatureConfigurationCoordinator(creator, remover, editor, viewer, view); 
	}

	mallotore.temperature = mallotore.temperature || {};
	mallotore.temperature.createTemperatureConfigurationCoordinator = createTemperatureConfigurationCoordinator;
	
})(window.mallotore);