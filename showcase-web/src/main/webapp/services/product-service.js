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
			getProductsByCategory : function(categoryId, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/product/category/" + categoryId).then(successCallback, errorCallback);
			},
			getProductsAnotherUserByCategory : function(categoryId, userId, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/product/category/" + categoryId + "/user/" + userId + "/another").then(successCallback, errorCallback);
			},
			getNewProducts : function(successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/product/new").then(successCallback, errorCallback);
			},
			getNewProductsAnotherUser : function(userId, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/product/new/user/" + userId + "/another").then(successCallback, errorCallback);
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
			},
			getImagesByProduct : function(productId, successCallback, errorCallback) {
				errorCallback = errorCallback || DEFAULT_ERROR_CALLBACK;
				return request.get("/product/" + productId + "/image").then(successCallback, errorCallback);
			}
		};

		return service;
	}
]);