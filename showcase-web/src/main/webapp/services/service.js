'use strict';

/**
* Request service
*/
angular.module('showcase').service('requestService',[
	'$http', 'config',
	function($http, config) {

		/**
		 * Gets request.
		 *
		 * @param  {String} url Url to get.
		 * @param  {Object} options Some configurations for request.
		 * @return {Promise} $http promise resolved.
		 */
		function get(url, options) {
			return $http.get(config.ENDPOINTS.SHOWCASE + url);
		}

		/**
		 * Posts request.
		 *
		 * @param  {String} url Url to post.
		 * @param  {Object} data Data that to passt to api.
		 * @param  {Object} options Some configurations for request.
		 * @return {Promise} $http promise resolved.
		 */
		function post(url, data, options) {
			return $http.post(config.ENDPOINTS.SHOWCASE + url, data, options);
		}

		/**
		 * Deletes request.
		 *
		 * @param  {String} url Url to delete.
		 * @return {Promise} $http promise resolved.
		 */
		function remove(url) {
			return $http.delete(config.ENDPOINTS.SHOWCASE + url);
		}

		return {
			get: get,
			post: post,
			remove: remove
		}
	}
]);