
'use strict';

/*
* Home controller
*/
angular.module('showcase').controller('showcaseHomeController', [
	'$scope', '$log', 'categoryService','productService', 'negotiationService', 'favoriteService',
	function($scope, $log, categoryService, productService, negotiationService, favoriteService) {
		$log.info('Controller initialized [ShowcaseHomeController]');

		$scope.initialize = function() {
			$scope.getCategories();
			$scope.getNewProducts();
		};

		/* GET INFOS QNDO USUÁRIO não LOGADO **********************************/
		$scope.getCategories = function() {
			$log.info('Obtendo categorias.. [ShowcaseHomeController]');

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
				$log.error('Error get categories: ' + JSON.stringify(responseError) + ' [ShowcaseHomeController]');
			});
		};

		$scope.getProductsByCategory = function(category) {
			$log.info('Obtendo os produtos da categoria.. [ShowcaseHomeController]');
			$log.info(JSON.stringify(category));

			productService.getProductsByCategory(category.id, function(response) {
				$log.info('Produtos obtidos com sucesso! [ShowcaseHomeController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.products = response.data;
				}
			}, function(responseError) {
				$log.error('Error get products by category: ' + JSON.stringify(responseError) + ' [ShowcaseHomeController]');
			});
		};

		$scope.getNewProducts = function() {
			$log.info('Obtendo os novos produtos.. [ShowcaseHomeController]');

			productService.getNewProducts(function(response) {
				$log.info('Novos produtos obtidos com sucesso! [ShowcaseHomeController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.newProducts = response.data;
				}
			}, function(responseError) {
				$log.error('Error get new products: ' + JSON.stringify(responseError) + ' [ShowcaseHomeController]');
			});
		};
		/* //GET INFOS QNDO USUÁRIO não LOGADO **********************************/

		$scope.selectCategoryTab = function(category) {
			$log.info('Aba/Categoria selecionada.. [ShowcaseHomeController]');
			$log.info(JSON.stringify(category));

			$scope.currentCategoryTab = category;
			if ($scope.$parent.currentUser) {
				$scope.getProductsAnotherUserByCategory($scope.currentCategoryTab);
			} else {
				$scope.getProductsByCategory($scope.currentCategoryTab);	
			}
		};

		/* FUNÇÕES POPUP DETALHES E NEGOCIAÇÃO **********************************/
		$scope.selectProduct = function(product) {
			$log.info('Produto selecionado.. [ShowcaseHomeController]');
			$log.info(JSON.stringify(product));

			$scope.productSelected = product;

			// ** para mostrar os botões apenas qndo o popup estiver carregado
			$scope.showActionsDetails = false;
			setTimeout(function() {
				$scope.showActionsDetails = true;
				$scope.$digest();
			}, 200);
		};

		$scope.favorite = function(product) {
			if (product) {
				if (product.favorite) {
					$scope.productSelected = false;
					$scope.removeFavorite(product.favorite);
				} else {
					$scope.productSelected = true;
					$scope.createFavorite(product);
				}
			}
		};

		$scope.createFavorite = function(product) {
			$log.info('Favoritando.. [ShowcaseHomeController]');
			$scope.currentFavorite = {};
			$scope.currentFavorite.usuario = $scope.$parent.currentUser;
			$scope.currentFavorite.produto = product.product;
			$log.info(JSON.stringify($scope.currentFavorite));

			$scope.showLoading = true;
			favoriteService.createFavorite($scope.currentFavorite, function(response) {
				$log.info('Favorito criado com sucesso! [ShowcaseHomeController]');
				$log.info(JSON.stringify(response));

				$scope.showLoading = false;
				//$scope.favorites.push(response.data);
				//$scope.setFavoritesInProducts();
				$scope.getFavoritesByUser();
			}, function(responseError) {
				$log.error('Error create favorite: ' + JSON.stringify(responseError) + ' [ShowcaseHomeController]');
				//$scope.setErrorMessage(responseError, "Não foi possível criar o favorito, por favor tente novamente mais tarde.");
			});
		};

		$scope.removeFavorite = function(favorite) {
			$log.info('Removendo favorito.. [ShowcaseHomeController]');
			$log.info(JSON.stringify(favorite));

			$scope.showLoading = true;
			favoriteService.removeFavorite(favorite.id, function(response) {
				$log.info('Favorito removido com sucesso! [ShowcaseHomeController]');
				$log.info(JSON.stringify(response));

				$scope.showLoading = false;
				$scope.getFavoritesByUser();
			}, function(responseError) {
				$log.error('Error remove favorite: ' + JSON.stringify(responseError) + ' [ShowcaseHomeController]');
				//$scope.setErrorMessage(responseError, "Não foi possível criar o favorito, por favor tente novamente mais tarde.");
			});
		};

		$scope.confirmNegotiation = function() {
			$log.info('Confirmando negociação.. [ShowcaseHomeController]');
			$scope.currentNegotiation.usuario = $scope.$parent.currentUser;
			$scope.currentNegotiation.produto = $scope.productSelected.product;
			$log.info(JSON.stringify($scope.currentNegotiation));

			$scope.showLoading = true;
			negotiationService.createNegotiation($scope.currentNegotiation, function(response) {
				$log.info('Negociação criada com sucesso! [ShowcaseHomeController]');
				$log.info(JSON.stringify(response));

				$scope.showLoading = false;
				$scope.message = {
					"type" : "success",
					"title" : "Ok!",
					"body" : "Negociação confirmada com sucesso!"
				};
				$scope.negotiationConfirmed = true;
				$scope.getNegotiationsByUser();
			}, function(responseError) {
				$log.error('Error create negotiation: ' + JSON.stringify(responseError) + ' [ShowcaseHomeController]');
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
		/* //FUNÇÕES POPUP DETALHES E NEGOCIAÇÃO **********************************/

		/* GET INFOS QNDO USUÁRIO LOGADO **********************************/
		$scope.$on('loadHomeUserLogged', function() {
			$log.info('.. carregando a home com os dados para um usuário logado .. [ShowcaseHomeController]');
			$scope.getFavoritesByUser();
			$scope.getNegotiationsByUser();
			$scope.getProductsByUser();
		});

		$scope.getProductsByUser = function() {
			$log.info('Obtendo os produtos do usuário.. [ShowcaseHomeController]');
			$log.info(JSON.stringify($scope.$parent.currentUser));

			productService.getProductsByUser($scope.$parent.currentUser.id, function(response) {
				$log.info('Produtos do usuário obtidos com sucesso! [ShowcaseHomeController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.myProducts = response.data;
					$scope.getProductsAnotherUserByCategory($scope.currentCategoryTab);
					$scope.getNewProductsAnotherUser();
				}
			}, function(responseError) {
				$log.error('Error get products by user: ' + JSON.stringify(responseError) + ' [ShowcaseHomeController]');
			});
		};

		$scope.getFavoritesByUser = function() {
			$log.info('Obtendo os favoritos do usuário logado.. [ShowcaseHomeController]');
			$log.info(JSON.stringify($scope.$parent.currentUser));

			favoriteService.getFavoritesByUser($scope.$parent.currentUser.id, function(response) {
				$log.info('Favoritos obtidos com sucesso! [ShowcaseHomeController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.favorites = response.data;
				}
			}, function(responseError) {
				$log.error('Error get favorites by user: ' + JSON.stringify(responseError) + ' [ShowcaseHomeController]');
			});
		};

		$scope.getNegotiationsByUser = function() {
			$log.info('Obtendo as negociações do usuário logado.. [ShowcaseHomeController]');
			$log.info(JSON.stringify($scope.$parent.currentUser));

			negotiationService.getNegotiationsByUserOrder($scope.$parent.currentUser.id, function(response) {
				$log.info('Negociações obtidas com sucesso! [ShowcaseHomeController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.negotiations = response.data;
				}
			}, function(responseError) {
				$log.error('Error get negotiations by user: ' + JSON.stringify(responseError) + ' [ShowcaseHomeController]');
			});
		};

		$scope.getProductsAnotherUserByCategory = function(category) {
			$log.info('Obtendo os produtos da categoria selecionada que não sejam do usuário conectado.. [ShowcaseHomeController]');
			$log.info(JSON.stringify(category));
			$log.info(JSON.stringify($scope.$parent.currentUser));

			productService.getProductsAnotherUserByCategory(category.id, $scope.$parent.currentUser.id, function(response) {
				$log.info('Produtos de outros usuários obtidos com sucesso! [ShowcaseHomeController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.products = response.data;
					$scope.setNegotiationsInProducts($scope.products);
					$scope.setFavoritesInProducts($scope.products);
				}
			}, function(responseError) {
				$log.error('Error get products another user by category: ' + JSON.stringify(responseError) + ' [ShowcaseHomeController]');
			});
		};

		$scope.getNewProductsAnotherUser = function() {
			$log.info('Obtendo os novos produtos que não seajm do usuário conectado.. [ShowcaseHomeController]');
			$log.info(JSON.stringify($scope.$parent.currentUser));

			productService.getNewProductsAnotherUser($scope.$parent.currentUser.id, function(response) {
				$log.info('Novos produtos de outros usuários obtidos com sucesso! [ShowcaseHomeController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.newProducts = response.data;
					$scope.setNegotiationsInProducts($scope.newProducts);
					$scope.setFavoritesInProducts($scope.products);
				}
			}, function(responseError) {
				$log.error('Error get new products another user: ' + JSON.stringify(responseError) + ' [ShowcaseHomeController]');
			});
		};

		$scope.setFavoritesInProducts = function(products) {
			$log.info('Validando favoritos na lista de produtos.. [ShowcaseHomeController]');

			if (products && $scope.favorites) {
				for (var i = 0; i < products.length; i++) {
					var product = products[i].product;
					for (var j = 0; j < $scope.favorites.length; j++) {
						var favorite = $scope.favorites[j];
						if (product.id === favorite.produto.id) {
							products[i].favorite = favorite;
						}
					}
				}
			}

			$scope.$digest();
			$log.info('.. produtos indicando se é favorito ou não ' + JSON.stringify(products) + ' [ShowcaseHomeController]');
		};

		$scope.setNegotiationsInProducts = function(products) {
			$log.info('Validando negociações na lista de produtos.. [ShowcaseHomeController]');

			if (products && $scope.negotiations) {
				for (var i = 0; i < products.length; i++) {
					var product = products[i].product;
					for (var j = 0; j < $scope.negotiations.length; j++) {
						var negotiation = $scope.negotiations[j];
						if (product.id === negotiation.produto.id) {
							products[i].hasNegotiation = true;
						}
					}
				}
			}

			$scope.$digest();
			$log.info('.. produtos indicando se há negociação em andamento ou não ' + JSON.stringify(products) + ' [ShowcaseHomeController]');
		};
		/* //GET INFOS QNDO USUÁRIO LOGADO **********************************/

		$('#myModalNegotiation').on('hide.bs.modal', function() {
			$log.info('Current negotiation: ' + JSON.stringify($scope.currentNegotiation) + ' [ShowcaseHomeController]');
			$scope.currentNegotiation = null;
			$scope.negotiationConfirmed = false;
		});

		$('#radioBtnNegotiationTypeNeg a').on('click', function() {
			var sel = $(this).data('title');
			var tog = $(this).data('toggle');
			$('#'+tog).prop('value', sel);

			if (sel === 'EXCHANGE_OR_BUY') {
				$scope.showPrice = true;
				$scope.showProducts = true;
			} else if (sel === 'BUY') {
				$scope.showPrice = true;
			} else if (sel === 'EXCHANGE') {
				$scope.showProducts = true;
			} else {
				$scope.showPrice = false;
				$scope.showProducts = false;
			}

			$scope.currentNegotiation.tipoNegociacao = sel;
			$scope.$digest();

			$('a[data-toggle="'+tog+'"]').not('[data-title="'+sel+'"]').removeClass('active').addClass('notActive');
			$('a[data-toggle="'+tog+'"][data-title="'+sel+'"]').removeClass('notActive').addClass('active');
		});

		$scope.initialize();

		$log.info('Controller execution ended [ShowcaseHomeController]');
	}
]);