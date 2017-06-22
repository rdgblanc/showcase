'use strict';

/**
* Favorite service
*/
angular.module('showcase').factory('favoriteService', [
	'requestService',
	function(request) {
		var service = {
			getFavoritesByUser : function(userId, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/favorite/user/" + userId).then(successCallback, errorCallback);
			},
			getFavorite : function(id, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/favorite/" + id).then(successCallback, errorCallback);
			},
			createFavorite : function(favorite, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.post("/favorite/", favorite).then(successCallback, errorCallback);
			},
			removeFavorite : function(id, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.delete("/favorite/" + id).then(successCallback, errorCallback);
			}
		};

		return service;
	}
]);