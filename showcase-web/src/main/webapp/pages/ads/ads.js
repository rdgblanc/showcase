'use strict';

/*
* Adverts controller
*/
angular.module('showcase').controller('showcaseAdsController', [
	'$scope', '$log', 'productService',
	function($scope, $log, productService) {
		$log.info('Controller initialized [ShowcaseAdsController]');

		$scope.initialize = function() {
			$scope.getProductsByUser();
		}

		$scope.getProductsByUser = function() {
			/*$scope.products = [$scope.$parent.currentProduct];
			return;*/
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

		$scope.getCategoryLabel = function(product) {
			return product.categoria.categoriaPai.nome + " - " + product.categoria.nome;
		};

		$scope.initialize();

		$log.info('Controller execution ended [ShowcaseAdsController]');
	}
]);