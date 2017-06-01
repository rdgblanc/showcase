'use strict';

/*
* Home controller
*/
angular.module('showcase').controller('showcaseHomeController', [
	'$scope', '$log', 'categoryService','productService',
	function($scope, $log, categoryService, productService) {
		$log.info('Controller initialized [ShowcaseHomeController]');

		$scope.initialize = function() {
			//$log.info('Current product: ' + JSON.stringify($scope.$parent.currentProduct));
			$scope.getCategories();
		};

		$scope.getCategories = function() {
			$log.info('Obtendo categorias.. [ShowcaseHomeController]');

			//$scope.showLoading = true;
			categoryService.getCategories(function(response) {
				$log.info('Categorias obtidas com sucesso! [ShowcaseHomeController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.categories = response.data;
					if ($scope.categories && $scope.categories.length > 0) {
						$scope.selectCategoryTab($scope.categories[0]);
					}
				}
			}, function(responseError) {
				$log.error("Error get categories: " + JSON.stringify(responseError));
				//$scope.setErrorMessage(responseError, "Não foi possível recuperar as categorias, por favor tente novamente mais tarde.");
			});
		};

		$scope.selectCategoryTab = function(category) {
			$log.info('Categoria/Aba selecionada.. [ShowcaseHomeController]');
			$log.info(JSON.stringify(category));

			$scope.currentCategoryTab = category;
			$scope.getProductsByCategory($scope.currentCategoryTab);
		};

		$scope.getProductsByCategory = function(category) {
			$log.info('Obtendo os produtos da categoria.. [ShowcaseHomeController]');
			$log.info(JSON.stringify(category));

			//$scope.showLoading = true;
			productService.getProductsByCategory(category.id, function(response) {
				$log.info('Produtos obtidos com sucesso! [ShowcaseHomeController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.products = response.data;
				}
			}, function(responseError) {
				$log.error("Error get products by category: " + JSON.stringify(responseError));
				//$scope.setErrorMessage(responseError, "Não foi possível recuperar os produtos da categoria, por favor tente novamente mais tarde.");
			});
		};

		$scope.openProductDetails = function(product) {
			$log.info('Produto selecionado.. [ShowcaseHomeController]');
			$log.info(JSON.stringify(product));

			$scope.productSelected = product;
		};

		$scope.initialize();

		$log.info('Controller execution ended [ShowcaseHomeController]');
	}
]);