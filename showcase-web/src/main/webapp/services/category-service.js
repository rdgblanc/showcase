'use strict';

/**
* Category service
*/
angular.module('showcase').factory('categoryService', [
	'requestService',
	function(request) {
		var service = {
			getCategories : function(successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/category/").then(successCallback, errorCallback);
			},
			getSubCategories : function(successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/category/subCategories/").then(successCallback, errorCallback);
			},
			getSubCategoriesByCategoryId : function(categoryId, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/category/subCategories/" + categoryId).then(successCallback, errorCallback);
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