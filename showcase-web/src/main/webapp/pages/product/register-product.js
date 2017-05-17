'use strict';

/*
* Register Product controller
*/
angular.module('showcase').controller('showcaseRegisterProductController', [
	'$scope', '$log', 'categoryService', 'productService',
	function($scope, $log, categoryService, productService) {
		$log.info('Controller initialized [ShowcaseRegisterProductController]');

		$scope.productStatusEnum = Object.freeze({
			INACTIVE: 'Inativo',
			ACTIVE: 'Ativo',
			DENOUNCED: 'Denunciado',
			BLOCKED: 'Bloqueado'
		});

		$scope.getProductsByUser = function() {
			$log.info('Obtendo produtos do usuário.. [ShowcaseAdsController]');
			$log.info(JSON.stringify($scope.$parent.currentUser));

			//$scope.showLoading = true;
			productService.getProductsByUser($scope.$parent.currentUser.id, function(response) {
				$log.info('Produtos do usuário obtido com sucesso! [ShowcaseAdsController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.products = response.data;
				}
			}, function(responseError) {
				$log.error("Error get products by user: " + JSON.stringify(responseError));
				//$scope.setErrorMessage(responseError, "Não foi possível recuperar os produtos do usuário, por favor tente novamente mais tarde.");
			});
		};

		$scope.editProduct = function(product) {
			$scope.setCurrentProduct(product);
			$scope.switchView($scope.viewsEnum.REGISTER_PRODUCT);
		};

		$scope.deleteProduct = function(product) {
			$log.info('Removendo o produto.. [ShowcaseAdsController]');
			$log.info(JSON.stringify(product));

			//$scope.showLoading = true;
			productService.deleteProduct(product.id, function(response) {
				$log.info('Produto removido com sucesso! [ShowcaseAdsController]');
				$log.info(JSON.stringify(response));

				$scope.getProductsByUser();
			}, function(responseError) {
				$log.error("Error delete product: " + JSON.stringify(responseError));
				//$scope.setErrorMessage(responseError, "Não foi possível remover o produto, por favor tente novamente mais tarde.");
			});
		};

		/*$scope.getCategories = function() {
			$log.info('Obtendo categorias.. [ShowcaseAdsController]');

			//$scope.showLoading = true;
			categoryService.getCategories(function(response) {
				$log.info('Categorias obtidas com sucesso! [ShowcaseAdsController]');
				$log.info(JSON.stringify(response));

				if (response && response.data && response.data.length > 0) {
					$scope.address = response.data[0];
				}
			}, function(responseError) {
				$log.error("Error get categories by user: " + JSON.stringify(responseError));
				//$scope.setErrorMessage(responseError, "Não foi possível recuperar as categorias, por favor tente novamente mais tarde.");
			});
		};*/

		$scope.getProductsByUser();

		$log.info('Controller execution ended [ShowcaseAdsController]');
	}
]);