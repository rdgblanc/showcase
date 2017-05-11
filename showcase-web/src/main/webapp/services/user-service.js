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
			getUserById : function(id, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/user/" + id).then(successCallback, errorCallback);
			},
			createUser : function(user, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.post("/user/", user).then(successCallback, errorCallback);
			},
			updateUser : function(id, user, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.put("/user/" + id, user).then(successCallback, errorCallback);
			},
			deleteUser : function(id, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.remove("/user/" + id).then(successCallback, errorCallback);
			},
			authenticate : function(user, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.post("/user/login", user).then(successCallback, errorCallback);
			},
			updatePassword : function(id, updateUserPasswordDTO, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.put("/user/" + id + "/password", updateUserPasswordDTO).then(successCallback, errorCallback);
			}
		};

		return service;
	}
]);