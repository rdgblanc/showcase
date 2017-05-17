'use strict';

/**
* Product service
*/
angular.module('showcase').factory('productService', [
	'requestService',
	function(request) {
		var service = {
			getProductsByUser : function(userId, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/product/user/" + userId).then(successCallback, errorCallback);
			},
			getProduct : function(id, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/product/" + id).then(successCallback, errorCallback);
			},
			createProduct : function(product, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.post("/product/", product).then(successCallback, errorCallback);
			},
			updateProduct : function(id, product, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.put("/product/" + id, product).then(successCallback, errorCallback);
			},
			deleteProduct : function(id, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.remove("/product/" + id).then(successCallback, errorCallback);
			}
		};

		return service;
	}
]);