'use strict';

/**
* User service
*/
angular.module('showcase').factory('userService', [
	'requestService',
	function(request) {
		var service = {
			getAllUsers : function(successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/user").then(successCallback, errorCallback);
			},
			getCurrentUser : function(successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/user/@self").then(successCallback, errorCallback);
			},
			getUserById : function(uid, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/user/" + id).then(successCallback, errorCallback);
			},
			storeUser : function(user, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.post("/user/", user).then(successCallback, errorCallback);
			},
			deleteUser : function(uid, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.remove("/user/" + uid).then(successCallback, errorCallback);
			},
			authenticate : function(user, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.post("/user/login", user).then(successCallback, errorCallback);
			}
		};

		return service;
	}
]);