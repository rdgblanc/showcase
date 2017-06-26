'use strict';

/**
* Message service
*/
angular.module('showcase').factory('messageService', [
	'requestService',
	function(request) {
		var service = {
			getMessagesByUser : function(userId, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/message/user/" + userId).then(successCallback, errorCallback);
			},
			getMessagesByNegotiation : function(negotiationId, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/message/negotiation/" + negotiationId).then(successCallback, errorCallback);
			},
			getMessage : function(id, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/message/" + id).then(successCallback, errorCallback);
			},
			createMessage : function(message, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.post("/message/", message).then(successCallback, errorCallback);
			},
			updateMessage : function(id, message, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.put("/message/" + id, message).then(successCallback, errorCallback);
			}
		};

		return service;
	}
]);