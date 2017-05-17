'use strict';

/**
* Category service
*/
angular.module('showcase').factory('categoryService', [
	'requestService',
	function(request) {
		var service = {
			getSubCategories : function(userId, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/category/subCategories/" + userId).then(successCallback, errorCallback);
			},
			getCategories : function(id, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/category/" + id).then(successCallback, errorCallback);
			}/*,
			createCategory : function(category, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.post("/category/", category).then(successCallback, errorCallback);
			},
			updateCategory : function(id, category, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.put("/category/" + id, category).then(successCallback, errorCallback);
			},
			deleteCategory : function(id, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.remove("/category/" + id).then(successCallback, errorCallback);
			}*/
		};

		return service;
	}
]);