'use strict';

/*
* Favorites controller
*/
angular.module('showcase').controller('showcaseFavoritesController', [
	'$scope', '$log', 'favoriteService',
	function($scope, $log, favoriteService) {
		$log.info('Controller initialized [showcaseFavoritesController]');

		$scope.initialize = function() {
			$scope.getFavoritesByUser();
		}

		$scope.getFavoritesByUser = function() {
			$log.info('Obtendo os favoritos do usuário logado.. [showcaseFavoritesController]');
			$log.info(JSON.stringify($scope.$parent.currentUser));

			favoriteService.getFavoritesByUser($scope.$parent.currentUser.id, function(response) {
				$log.info('Favoritos obtidos com sucesso! [showcaseFavoritesController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.favorites = response.data;
				}
			}, function(responseError) {
				$log.error('Error get favorites by user: ' + JSON.stringify(responseError) + ' [showcaseFavoritesController]');
			});
		};

		$scope.showProduct = function(product) {
			$scope.productSelected = product;
		};

		$scope.removeFavorite = function(favorite) {
			$log.info('Removendo favorito.. [showcaseFavoritesController]');
			$log.info(JSON.stringify(favorite));

			favoriteService.removeFavorite(favorite.id, function(response) {
				$log.info('Favorito removido com sucesso! [showcaseFavoritesController]');
				$log.info(JSON.stringify(response));

				$scope.getFavoritesByUser();
			}, function(responseError) {
				$log.error('Error remove favorite: ' + JSON.stringify(responseError) + ' [showcaseFavoritesController]');
				//$scope.setErrorMessage(responseError, "Não foi possível criar o favorito, por favor tente novamente mais tarde.");
			});
		};

		$scope.getCategoryLabel = function(product) {
			return product.categoria.categoriaPai.nome + " - " + product.categoria.nome;
		};

		$scope.selectProduct = function(product) {
			$log.info('Produto selecionado.. [showcaseFavoritesController]');
			$log.info(JSON.stringify(product));

			$scope.productSelected = product;

			// ** para mostrar os botões apenas qndo o popup estiver carregado
			$scope.showActionsDetails = false;
			setTimeout(function() {
				$scope.showActionsDetails = true;
				$scope.$digest();
			}, 200);
		};

		$scope.initialize();

		$log.info('Controller execution ended [showcaseFavoritesController]');
	}
]);