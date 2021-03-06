'use strict';

/**
* Negotiation service
*/
angular.module('showcase').factory('negotiationService', [
	'requestService',
	function(request) {
		var service = {
			getNegotiationsByUserSeller : function(userId, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/negotiation/user/seller/" + userId).then(successCallback, errorCallback);
			},
			getNegotiationsByUserOrder : function(userId, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/negotiation/user/order/" + userId).then(successCallback, errorCallback);
			},
			getNegotiationsByProduct : function(productId, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/negotiation/product/" + productId).then(successCallback, errorCallback);
			},
			getNegotiation : function(id, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/negotiation/" + id).then(successCallback, errorCallback);
			},
			createNegotiation : function(product, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.post("/negotiation/", product).then(successCallback, errorCallback);
			},
			updateNegotiation : function(id, product, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.put("/negotiation/" + id, product).then(successCallback, errorCallback);
			},
			updateNegotiationStatus : function(id, status, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.put("/negotiation/" + id + "/" + status).then(successCallback, errorCallback);
			}
		};

		return service;
	}
]);