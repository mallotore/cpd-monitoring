var mallotore = mallotore || {};

(function(mallotore){

	function Notifier(){
		var self = {};
		self.notifySuccess = function(header, message){
			showNotification(header, message, 'success');
		};

		self.notifyError = function(header, message){
			showNotification(header, message, 'error');
		};

		function showNotification(header, message, type){
			$.jGrowl.defaults.closerTemplate = '<div class="alert alert-info">Cerrar todos</div>';
	        $('#jGrowl-container').jGrowl({
	            header: header,
	            message: message,
	            group: 'alert-'+type,
	            life: 5000
	        });
		}
		return self;
	}

	mallotore.utils = (mallotore.utils || {});
	mallotore.utils.notifier = new Notifier();

})(mallotore);