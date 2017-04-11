"use strict";
/*
* Home controller
*/
angular.module('showcase').controller("showcaseHomeController", [
	'$scope', '$log', '$route', '$location',
	function($scope, $log, $route, $location) {
		$log.info("ShowcaseHomeController initialized");

		var currentTab = '';

		/**
		* Reloads page.
		*
		* @param {String} path Path to reload.
		* @param {boolean} reload True if reload page, false otherwise.
		*/
		function _reload(path, reload) {
			if (reload) {
				$location.path(path);
			} else {
				$location.skipReload(path);
			}
		}

		/**
		 * Sets page according param.
		 */
		$scope.setPage = function() {
			$("#myTab li").removeClass("active");

			$log.info("$route.current.params.currentTab:::" + $route.current.params.currentTab);

			// Set tab that should active.
			if ($route.current.params.currentTab) {
				$log.info(">>>>>>>>>>>>> 111");
				//$log.info(angular.element());
				$("#" + $route.current.params.currentTab + "-tab").parent().addClass("active");
				//$route.current.params.currentTab = '';
				//_reload('/' + $route.current.params.currentTab, true);
			} else {
				$log.info(">>>>>>>>>>>>> 222");
				//_reload('/home');
				//$("#home-tab").parent().addClass("active");
			}
		}

		//$scope.setPage();

		$log.info("ShowcaseHomeController execution ended");
	}]);