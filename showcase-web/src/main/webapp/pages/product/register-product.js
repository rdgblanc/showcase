'use strict';

/*
* Register Product controller
*/
angular.module('showcase').controller('showcaseRegisterProductController', [
	'$scope', '$log', '$timeout', 'categoryService', 'productService',
	function($scope, $log, $timeout, categoryService, productService) {
		$log.info('Controller initialized [ShowcaseRegisterProductController]');

		$scope.showLoading = false;

		$scope.productNegotiationTypeEnum = Object.freeze({
			DONATION: 			'Doação',
			LOAN: 				'Empréstimo',
			EXCHANGE: 			'Troca',
			SALE: 				'Venda',
			EXCHANGE_OR_SALE: 	'Troca ou Venda'
		});

		$scope.productConservationStateEnum = Object.freeze({
			NEW: 		'Novo',
			SEMI_NEW: 	'Seminovo',
			USED: 		'Usado'
		});

		$scope.initialize = function() {
			$log.info('Current product: ' + JSON.stringify($scope.$parent.currentProduct));

			if ($scope.$parent.currentProduct && $scope.$parent.currentProduct.estadoConservacao) {
				if ($scope.$parent.currentProduct.estadoConservacao === 'NEW') {
					$('#radioNew').trigger('click');
				} else if ($scope.$parent.currentProduct.estadoConservacao === 'SEMI_NEW') {
					$('#radioSemiNew').trigger('click');
				} else if ($scope.$parent.currentProduct.estadoConservacao === 'USED') {
					$('#radioUsed').trigger('click');
				}
			}

			if ($scope.$parent.currentProduct && $scope.$parent.currentProduct.tipoNegociacao) {
				if ($scope.$parent.currentProduct.tipoNegociacao === 'DONATION') {
					$('#radioDonation').trigger('click');
				} else if ($scope.$parent.currentProduct.tipoNegociacao === 'LOAN') {
					$('#radioLoan').trigger('click');
				} else if ($scope.$parent.currentProduct.tipoNegociacao === 'EXCHANGE') {
					$('#radioExchange').trigger('click');
				} else if ($scope.$parent.currentProduct.tipoNegociacao === 'SALE') {
					$('#radioSale').trigger('click');
				} else if ($scope.$parent.currentProduct.tipoNegociacao === 'EXCHANGE_OR_SALE') {
					$('#radioExchangeOrSale').trigger('click');
				}
			}

			$scope.categories = [
				{
					id: 3,
					nome: "Acessórios"
				},
				{
					id: 2,
					nome: "Calçados"
				},
				{
					id: 1,
					nome: "Roupas"
				}
			];
			

			$scope.subcategories = [
				{
					id: 9,
					nome: "Sandália"
				},
				{
					id: 10,
					nome: "Sapato"
				}
			];

			$scope.setCurrentProduct({});

			$timeout(function() {
				$scope.$watch('$parent.currentProduct.categoria.categoriaPai', function() {
					$log.info('>>> category changed.......');
					//$scope.getSubCategoriesByCategoryId();
				}, true);
			});
		};

		$scope.getCategories = function() {
			$log.info('Obtendo categorias.. [ShowcaseRegisterProductController]');

			//$scope.showLoading = true;
			categoryService.getCategories(function(response) {
				$log.info('Categorias obtidas com sucesso! [ShowcaseRegisterProductController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.categories = response.data;
				}
			}, function(responseError) {
				$log.error("Error get categories: " + JSON.stringify(responseError));
				//$scope.setErrorMessage(responseError, "Não foi possível recuperar as categorias, por favor tente novamente mais tarde.");
			});
		};

		$scope.getSubCategoriesByCategoryId = function() {
			$log.info('Obtendo subcategorias da categoria.. [ShowcaseRegisterProductController]');
			$log.info(JSON.stringify($scope.currentCategory));

			//$scope.showLoading = true;
			categoryService.getSubCategoriesByCategoryId(function(response) {
				$log.info('Subcategorias obtidas com sucesso! [ShowcaseRegisterProductController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.subcategories = response.data;
				}
			}, function(responseError) {
				$log.error("Error get categories: " + JSON.stringify(responseError));
				//$scope.setErrorMessage(responseError, "Não foi possível recuperar as categorias, por favor tente novamente mais tarde.");
			});
		};

		$scope.setCurrentCategory = function(category) {
			$log.info('Set current category.. "' + JSON.stringify(category) + '" [ShowcaseRegisterProductController]');
			$scope.currentCategory = category;
		};

		$('#radioBtnConservationState a').on('click', function() {
			var sel = $(this).data('title');
			var tog = $(this).data('toggle');
			$('#'+tog).prop('value', sel);
			$scope.$parent.currentProduct.estadoConservacao = sel;

			$('a[data-toggle="'+tog+'"]').not('[data-title="'+sel+'"]').removeClass('active').addClass('notActive');
			$('a[data-toggle="'+tog+'"][data-title="'+sel+'"]').removeClass('notActive').addClass('active');
		});

		$('#radioBtnNegotiationType a').on('click', function() {
			var sel = $(this).data('title');
			var tog = $(this).data('toggle');
			$('#'+tog).prop('value', sel);
			$scope.$parent.currentProduct.tipoNegociacao = sel;

			$('a[data-toggle="'+tog+'"]').not('[data-title="'+sel+'"]').removeClass('active').addClass('notActive');
			$('a[data-toggle="'+tog+'"][data-title="'+sel+'"]').removeClass('notActive').addClass('active');
		});

		//$(".selectpicker").selectpicker();

		$scope.initialize();

		$log.info('Controller execution ended [ShowcaseRegisterProductController]');
	}
]);