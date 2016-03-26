var mallotore = mallotore || {};

(function(mallotore){

	function AjaxClient(){
		var self = {};

		self.get = function(url, data, successCallback, errorCallback){
			doAjaxCall(url, data, "GET", successCallback, errorCallback)
		};

		self.post = function(url, data, successCallback, errorCallback){
			var stringifiedData = data ? JSON.stringify(data) : {};
			doAjaxCall(url, stringifiedData, "POST", successCallback, errorCallback)
		};

		self.put = function(url, data, successCallback, errorCallback){
			var stringifiedData = data ? JSON.stringify(data) : {};
			doAjaxCall(url, stringifiedData, "PUT", successCallback, errorCallback)
		};

		self.delete = function(url, data, successCallback, errorCallback){
			var stringifiedData = data ? JSON.stringify(data) : {};
			doAjaxCall(url, stringifiedData, "DELETE", successCallback, errorCallback)
		};

		function doAjaxCall(url, data, type, successCallback, errorCallback){
			$.ajax({
			    url: url,
			    data: data,
			    contentType : 'application/json',
			    type: type,
			    success: function(data) {
			    	successCallback(data);
			    },
			    error: function(xhr){
					errorCallback(xhr)
			    }
			});
		}
		return self;
	}

	mallotore.utils = (mallotore.utils || {});
	mallotore.utils.ajaxClient = new AjaxClient();

})(mallotore);