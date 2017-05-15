'use strict';

/**
* Address service
*/
angular.module('showcase').factory('addressService', [
	'requestService',
	function(request) {
		var service = {
			getAddressByUser : function(userId, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/address/user/" + userId).then(successCallback, errorCallback);
			},
			getAddress : function(id, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/address/" + id).then(successCallback, errorCallback);
			},
			createAddress : function(address, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.post("/address/", address).then(successCallback, errorCallback);
			},
			updateAddress : function(id, address, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.put("/address/" + id, address).then(successCallback, errorCallback);
			},
			deleteAddress : function(id, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.remove("/address/" + id).then(successCallback, errorCallback);
			}
		};

		return service;
	}
]);