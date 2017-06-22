'use strict';

/*
* Register Product controller
*/
angular.module('showcase').controller('showcaseRegisterProductController', [
	'$scope', '$log', '$timeout', 'categoryService', 'productService',
	function($scope, $log, $timeout, categoryService, productService) {
		$log.info('Controller initialized [ShowcaseRegisterProductController]');

		var uploadUrl;
		$scope.showLoading = false;

		$scope.initialize = function() {
			$log.info('Current product: ' + JSON.stringify($scope.$parent.currentProduct));

			if ($scope.$parent.currentProduct) {
				if ($scope.$parent.currentProduct.estadoConservacao) {
					if ($scope.$parent.currentProduct.estadoConservacao === 'NEW') {
						$('#radioNew').trigger('click');
					} else if ($scope.$parent.currentProduct.estadoConservacao === 'SEMI_NEW') {
						$('#radioSemiNew').trigger('click');
					} else if ($scope.$parent.currentProduct.estadoConservacao === 'USED') {
						$('#radioUsed').trigger('click');
					}
				}

				if ($scope.$parent.currentProduct.tipoNegociacao) {
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

				if ($scope.$parent.currentProduct.categoria && $scope.$parent.currentProduct.categoria.categoriaPai) {
					//$scope.currentCategory = $scope.$parent.currentProduct.categoria.categoriaPai;
					$scope.currentCategory = null;
					$scope.getSubCategoriesByCategory($scope.$parent.currentProduct.categoria.categoriaPai);
				}

				$scope.getImagesByProduct($scope.$parent.currentProduct);
			}

			// initialize with defaults
			uploadUrl = "http://localhost:8080/showcase/api/product/image";
			$log.info('Upload image url...' + uploadUrl);
			$("#input-700").fileinput({
				uploadUrl: uploadUrl,
				uploadAsync: true,
				maxFileCount: 10,
				showUpload: false,
				showRemove: false,
				allowedFileExtensions: ['png', 'jpg', 'jpeg'],
				layoutTemplates: {
					actionUpload: ''
				},
				uploadExtraData: function(previewId, index) {
					return {productId: $scope.productId};
				}
			});

			$scope.getCategories();
			$scope.$watch('currentCategory', function() {
				if ($scope.currentCategory) {
					$log.info('Categoria selecionada...' + JSON.stringify($scope.currentCategory));
					$scope.currentSubCategory = null;
					$scope.getSubCategoriesByCategory($scope.currentCategory);
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

		$scope.getSubCategoriesByCategory = function(category) {
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
					$scope.currentCategory = $scope.$parent.currentProduct.categoria.categoriaPai;
					$scope.currentSubCategory = $scope.$parent.currentProduct.categoria;
				}
			}, function(responseError) {
				$log.error("Error get subcategories by category: " + JSON.stringify(responseError));
				//$scope.setErrorMessage(responseError, "Não foi possível recuperar as categorias, por favor tente novamente mais tarde.");
			});
		};

		$scope.getImagesByProduct = function(product) {
			$log.info('Obtendo as imagens do produto.. [ShowcaseRegisterProductController]');
			$log.info(JSON.stringify(product));

			//$scope.showLoading = true;
			productService.getImagesByProduct(product.id, function(response) {
				$log.info('Imagens obtidas com sucesso! [ShowcaseRegisterProductController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.setImages(response.data);
				}
			}, function(responseError) {
				$log.error("Error get images by product: " + JSON.stringify(responseError));
				//$scope.setErrorMessage(responseError, "Não foi possível recuperar as categorias, por favor tente novamente mais tarde.");
			});
		};

		$scope.setImages = function(images) {
			var initPreview = [];
			var initPreviewCfg = [];

			for (var i = 0; i < images.length; i++) {
				var imageUrl = "http://localhost/showcase-web/src/main/webapp/assets/upload/" + images[i].caminho;
				initPreview.push(imageUrl);

				var imageDeleteUrl = "http://localhost:8080/showcase/api/product/image/" + images[i].id;
				initPreviewCfg.push({width: "50px", url: imageDeleteUrl, key: images[i].id});
			}

			$("#input-700").fileinput('refresh', {
				uploadUrl: uploadUrl,
				uploadAsync: true,
				maxFileCount: 10,
				showUpload: false,
				showRemove: false,
				overwriteInitial: false,
				initialPreview: initPreview,
				initialPreviewAsData: true, // identify if you are sending preview data only and not the raw markup
				initialPreviewFileType: 'image', // image is the default and can be overridden in config below
				initialPreviewConfig: initPreviewCfg,
				allowedFileExtensions: ['png', 'jpg', 'jpeg'],
				layoutTemplates: {
					actionUpload: ''
				},
				uploadExtraData: function(previewId, index) {
					return {productId: $scope.productId};
				}
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
					$scope.productId = response.data.id;
					$('#input-700').fileinput('upload');
				}
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

					var files = $('#input-700').fileinput('getFileStack');
					$log.info('Files to upload.. ' + JSON.stringify(files));
					if (files && files.length > 0) {
						$scope.productId = response.data.id;
						$('#input-700').fileinput('upload');
					}
				}
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

		$scope.initialize();

		$log.info('Controller execution ended [ShowcaseRegisterProductController]');
	}
]);