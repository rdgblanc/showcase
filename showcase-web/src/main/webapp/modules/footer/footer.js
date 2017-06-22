'use strict';

/*
* Footer controller
*/
angular.module('showcase').controller("showcaseFooterController", [
	'$scope', '$log', 'categoryService',
	function($scope, $log, categoryService) {
		$log.info('Controller initialized [ShowcaseFooterController]');

		$scope.initialize = function() {
			$scope.getCategories();
		};

		$scope.getCategories = function() {
			$log.info('Obtendo categorias.. [ShowcaseFooterController]');

			categoryService.getCategories(function(response) {
				$log.info('Categorias obtidas com sucesso! [ShowcaseFooterController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.categories = response.data;
				}
			}, function(responseError) {
				$log.error("Error get categories: " + JSON.stringify(responseError) + " [ShowcaseFooterController]");
			});
		};

		$scope.initialize();

		$log.info('Controller execution ended [ShowcaseFooterController]');
	}]
);