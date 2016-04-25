window.mallotore = window.mallotore || {};

(function(mallotore){

	function BytesFormatter(){

		this.format = function(bytes) {
		   if(bytes == 0) return '0 Byte';
		   var k = 1000;
		   var dm = 3;
		   var sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
		   var i = Math.floor(Math.log(bytes) / Math.log(k));
		   return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
		};
	}

	mallotore.utils = (mallotore.utils || {});
	mallotore.utils.bytesFormatter = new BytesFormatter();

})(window.mallotore);