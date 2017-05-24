'use strict';

/*
* Register Product controller
*/
angular.module('showcase').controller('showcaseRegisterProductController', [
	'$scope', '$log', '$timeout', 'categoryService', 'productService',
	function($scope, $log, $timeout, categoryService, productService) {
		$log.info('Controller initialized [ShowcaseRegisterProductController]');

		$scope.showLoading = false;

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

			// initialize with defaults
			var uploadUrl = $scope.$parent.currentProduct && $scope.$parent.currentProduct.id ? "http://localhost:8080/showcase/api/product/" + $scope.$parent.currentProduct.id + "/image" : "http://localhost:8080/showcase/api/product/{id}/image";
			$log.info('Upload image url...' + uploadUrl);
			$("#input-700").fileinput({
				uploadUrl: uploadUrl,
				uploadAsync: true,
				maxFileCount: 5
			});

			/*$scope.categories = [
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
					nome: "Sandália",
						categoriaPai: {
						id: 2,
						nome: "Calçados"
					}
				},
				{
					id: 10,
					nome: "Sapato",
					categoriaPai: {
						id: 2,
						nome: "Calçados"
					}
				}
			];*/

			if ($scope.$parent.currentProduct && $scope.$parent.currentProduct.categoria && $scope.$parent.currentProduct.categoria.categoriaPai) {
				$scope.currentCategory = $scope.$parent.currentProduct.categoria.categoriaPai;
			}

			$scope.getCategories();
			$scope.$watch('currentCategory', function() {
				if ($scope.currentCategory) {
					$log.info('Categoria selecionada...' + JSON.stringify($scope.currentCategory));
					$scope.currentSubCategory = null;
					$scope.getSubCategoriesByCategoryId($scope.currentCategory);
				}
			}, true);
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

		$scope.getSubCategoriesByCategoryId = function(category) {
			$log.info('Obtendo subcategorias da categoria.. [ShowcaseRegisterProductController]');
			$log.info(JSON.stringify(category));

			//$scope.showLoading = true;
			categoryService.getSubCategoriesByCategoryId(category.id, function(response) {
				$log.info('Subcategorias obtidas com sucesso! [ShowcaseRegisterProductController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.subcategories = response.data;
				}

				if ($scope.$parent.currentProduct && $scope.$parent.currentProduct.categoria) {
					$scope.currentSubCategory = $scope.$parent.currentProduct.categoria;
				}
			}, function(responseError) {
				$log.error("Error get categories: " + JSON.stringify(responseError));
				//$scope.setErrorMessage(responseError, "Não foi possível recuperar as categorias, por favor tente novamente mais tarde.");
			});
		};

		$scope.saveProduct = function() {
			if ($scope.$parent.currentProduct && $scope.$parent.currentProduct.id) {
				$scope.updateProduct();
			} else {
				$scope.createProduct();
			}
		};

		$scope.createProduct = function() {
			$log.info('Criando produto.. [ShowcaseRegisterProductController]');
			$scope.$parent.currentProduct.usuario = $scope.$parent.currentUser;
			$scope.$parent.currentProduct.categoria = $scope.currentSubCategory;
			$log.info(JSON.stringify($scope.$parent.currentProduct));

			$scope.showLoading = true;
			productService.createProduct($scope.$parent.currentProduct, function(response) {
				$log.info('Produto criado com sucesso! [ShowcaseRegisterProductController]');
				$log.info(JSON.stringify(response));

				$scope.showLoading = false;
				$scope.message = {
					"type" : "success",
					"title" : "Ok!",
					"body" : "Produto criado com sucesso!"
				};

				if (response && response.data) {
					$scope.setCurrentProduct(response.data);
				}

				var uploadUrl = "http://localhost:8080/showcase/api/product/" + response.data.id + "/image";
				$log.info('Upload image url changed...' + uploadUrl);
				$("#input-700").fileinput('refresh', {
					uploadUrl: uploadUrl,
					uploadAsync: true,
					maxFileCount: 5
				});
			}, function(responseError) {
				$log.error("Error create product: " + JSON.stringify(responseError));
				$scope.setErrorMessage(responseError, "Não foi possível criar o produto, por favor tente novamente mais tarde.");
			});
		};

		$scope.updateProduct = function() {
			$log.info('Alterando produto.. [ShowcaseRegisterProductController]');
			$scope.$parent.currentProduct.categoria = $scope.currentSubCategory;
			$log.info(JSON.stringify($scope.$parent.currentProduct));

			$scope.showLoading = true;
			productService.updateProduct($scope.$parent.currentProduct.id, $scope.$parent.currentProduct, function(response) {
				$log.info('Produto alterado com sucesso! [ShowcaseRegisterProductController]');
				$log.info(JSON.stringify(response));

				$scope.showLoading = false;
				$scope.message = {
					"type" : "success",
					"title" : "Ok!",
					"body" : "Produto alterado com sucesso!"
				};

				if (response && response.data) {
					$scope.setCurrentProduct(response.data);
				}

				// TODO salvar as imagens do produto
			}, function(responseError) {
				$log.error("Error update product: " + JSON.stringify(responseError));
				$scope.setErrorMessage(responseError, "Não foi possível alterar o produto, por favor tente novamente mais tarde.");
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