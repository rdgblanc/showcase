'use strict';

/*
* Home controller
*/
angular.module('showcase').controller('showcaseHomeController', [
	'$scope', '$log', 'categoryService','productService', 'negotiationService',
	function($scope, $log, categoryService, productService, negotiationService) {
		$log.info('Controller initialized [ShowcaseHomeController]');

		$scope.initialize = function() {
			//$log.info('Current product: ' + JSON.stringify($scope.$parent.currentProduct));
			$scope.getNegotiationByUser();
			//$scope.getCategories();
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

		$scope.getNegotiationByUser = function() {
			$log.info('Obtendo as negociações do usuário.. [ShowcaseHomeController]');
			$log.info(JSON.stringify($scope.$parent.currentUser));

			//$scope.showLoading = true;
			negotiationService.getNegotiationByUser($scope.$parent.currentUser.id, function(response) {
				$log.info('Negociações obtidas com sucesso! [ShowcaseHomeController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.negotiations = response.data;
				}

				$scope.getCategories();
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
					$scope.setNegotiations();
				}
			}, function(responseError) {
				$log.error("Error get products by category: " + JSON.stringify(responseError));
				//$scope.setErrorMessage(responseError, "Não foi possível recuperar os produtos da categoria, por favor tente novamente mais tarde.");
			});
		};

		$scope.selectProduct = function(product) {
			$log.info('Produto selecionado.. [ShowcaseHomeController]');
			$log.info(JSON.stringify(product));

			$scope.productSelected = product;
			$scope.showActionsDetails = false;
			setTimeout(function() {
				$scope.showActionsDetails = true;
				$scope.$digest();
			}, 200);
		};

		$scope.confirmNegotiation = function() {
			$log.info('Confirmando negociação.. [ShowcaseHomeController]');
			$log.info(JSON.stringify($scope.$parent.currentUser));
			$log.info(JSON.stringify($scope.productSelected));
			$scope.$parent.currentNegotiation.usuario = $scope.$parent.currentUser;
			$scope.$parent.currentNegotiation.produto = $scope.productSelected.product;
			$log.info(JSON.stringify($scope.$parent.currentNegotiation));

			$scope.showLoading = true;
			negotiationService.createNegotiation($scope.$parent.currentNegotiation, function(response) {
				$log.info('Negociação criada com sucesso! [ShowcaseHomeController]');
				$log.info(JSON.stringify(response));

				$scope.showLoading = false;
				$scope.message = {
					"type" : "success",
					"title" : "Ok!",
					"body" : "Negociação confirmada com sucesso!"
				};

				setTimeout(function() {
					$('#myModalNegotiation').modal('hide');
				}, 2000);
			}, function(responseError) {
				$log.error("Error create negociation: " + JSON.stringify(responseError));
				$scope.setErrorMessage(responseError, "Não foi possível confirmar a negociação, por favor tente novamente mais tarde.");
			});
		};

		$scope.setErrorMessage = function(responseError, defaultMessage) {
			var msg = defaultMessage;
			if (responseError && responseError.data && responseError.data.message) {
				msg = responseError.data.message;
			}

			$scope.showLoading = false;
			$scope.message = {
				"type" : "danger",
				"title" : "Ops!",
				"body" : msg
			};
		};

		$scope.setNegotiations = function() {
			$log.info('Validando negociações na lista de produtos..');
			if ($scope.products && $scope.negotiations) {
				for (var i = 0; i < $scope.products.length; i++) {
					var product = $scope.products[i].product;
					for (var j = 0; j < $scope.negotiations.length; j++) {
						var negotiation = $scope.negotiations[j];
						if (product.id === negotiation.produto.id) {
							$scope.products[i].hasNegotiation = true;
						}
					}
				}
			}
			$log.info(JSON.stringify($scope.products));
		};

		$('#myModalNegotiation').on('show.bs.modal', function() {
			$log.info('Current negotiation: ' + JSON.stringify($scope.$parent.currentNegotiation));

			var negotiation = {};
			$scope.setCurrentNegotiation(negotiation);
			$scope.$digest();
		});

		$('#radioBtnNegotiationTypeNeg a').on('click', function() {
			var sel = $(this).data('title');
			var tog = $(this).data('toggle');
			$('#'+tog).prop('value', sel);
			$scope.$parent.currentNegotiation.tipoNegociacao = sel;

			if ($scope.$parent.currentNegotiation.tipoNegociacao === 'EXCHANGE' || $scope.$parent.currentNegotiation.tipoNegociacao === 'EXCHANGE_OR_BUY') {
				$scope.showProducts = true;
			} else {
				$scope.showProducts = false;
			}

			if ($scope.$parent.currentNegotiation.tipoNegociacao === 'BUY' || $scope.$parent.currentNegotiation.tipoNegociacao === 'EXCHANGE_OR_BUY') {
				$scope.showPrice = true;
			} else {
				$scope.showPrice = false;
			}

			$scope.$digest();

			$('a[data-toggle="'+tog+'"]').not('[data-title="'+sel+'"]').removeClass('active').addClass('notActive');
			$('a[data-toggle="'+tog+'"][data-title="'+sel+'"]').removeClass('notActive').addClass('active');
		});

		$scope.initialize();

		$log.info('Controller execution ended [ShowcaseHomeController]');
	}
]);