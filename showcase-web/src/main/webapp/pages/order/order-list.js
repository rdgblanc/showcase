'use strict';

/*
* Negotiation controller
*/
angular.module('showcase').controller('showcaseOrderListController', [
	'$scope', '$log', 'negotiationService',
	function($scope, $log, negotiationService) {
		$log.info('Controller initialized [showcaseOrderListController]');

		$scope.initialize = function() {
			$scope.getNegotiationsByUserSeller();
		}

		$scope.getNegotiationsByUserOrder = function() {
			$log.info('Obtendo as negociações do usuário comprador.. [showcaseOrderListController]');
			$log.info(JSON.stringify($scope.$parent.currentUser));

			//$scope.showLoading = true;
			negotiationService.getNegotiationsByUserOrder($scope.$parent.currentUser.id, function(response) {
				$log.info('Negociações do usuário comprador obtidas com sucesso! [showcaseOrderListController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.negotiations = response.data;
				}
			}, function(responseError) {
				$log.error("Error get negotiations by user order: " + JSON.stringify(responseError));
				//$scope.setErrorMessage(responseError, "Não foi possível recuperar as negociações do usuário comprador, por favor tente novamente mais tarde.");
			});
		};

		$scope.showNegotiation = function(product) {
			/*$scope.setCurrentProduct(product);
			$scope.switchView($scope.viewsEnum.REGISTER_PRODUCT);*/
		};

		$scope.cancelNegotiation = function(negotiation) {
			$log.info('Cancelando a negociação.. [showcaseOrderListController]');
			$log.info(JSON.stringify(negotiation));

			//$scope.showLoading = true;
			negotiationService.updateNegotiationStatus(negotiation.id, 'CANCELED', function(response) {
				$log.info('Negociação cancelada com sucesso! [showcaseOrderListController]');
				$log.info(JSON.stringify(response));
			}, function(responseError) {
				$log.error("Error cancel negotiation: " + JSON.stringify(responseError));
				//$scope.setErrorMessage(responseError, "Não foi possível cancelar a negociação, por favor tente novamente mais tarde.");
			});
		};

		$scope.initialize();

		$log.info('Controller execution ended [showcaseNegotiationsController]');
	}
]);