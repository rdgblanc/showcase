'use strict';

/*
* Negotiation controller
*/
angular.module('showcase').controller('showcaseNegotiationsController', [
	'$scope', '$log', 'negotiationService',
	function($scope, $log, negotiationService) {
		$log.info('Controller initialized [showcaseNegotiationsController]');

		$scope.initialize = function() {
			$scope.getNegotiationsByUserSeller();
		}

		$scope.getNegotiationsByUserSeller = function() {
			$log.info('Obtendo as negociações do usuário vendedor.. [showcaseNegotiationsController]');
			$log.info(JSON.stringify($scope.$parent.currentUser));

			//$scope.showLoading = true;
			negotiationService.getNegotiationsByUserSeller($scope.$parent.currentUser.id, function(response) {
				$log.info('Negociações do usuário vendedor obtidas com sucesso! [showcaseNegotiationsController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.negotiations = response.data;
				}
			}, function(responseError) {
				$log.error("Error get negotiations by user seller: " + JSON.stringify(responseError));
				//$scope.setErrorMessage(responseError, "Não foi possível recuperar as negociações do usuário vendedor, por favor tente novamente mais tarde.");
			});
		};

		$scope.showNegotiation = function(negotiation) {
			$scope.currentNegotiation = negotiation;
		};

		$scope.acceptNegotiation = function(negotiation) {
			$log.info('Aceitando a negociação.. [showcaseNegotiationsController]');
			$log.info(JSON.stringify(negotiation));

			//$scope.showLoading = true;
			negotiationService.updateNegotiationStatus(negotiation.id, 'ACCEPT', function(response) {
				$log.info('Negociação aceita com sucesso! [showcaseNegotiationsController]');
				$log.info(JSON.stringify(response));

				$scope.getNegotiationsByUserSeller();
			}, function(responseError) {
				$log.error("Error accept negotiation: " + JSON.stringify(responseError));
				//$scope.setErrorMessage(responseError, "Não foi possível aceitar a negociação, por favor tente novamente mais tarde.");
			});
		};

		$scope.denyNegotiation = function(negotiation) {
			$log.info('Cancelando a negociação.. [showcaseNegotiationsController]');
			$log.info(JSON.stringify(negotiation));

			//$scope.showLoading = true;
			negotiationService.updateNegotiationStatus(negotiation.id, 'CANCELED', function(response) {
				$log.info('Negociação cancelada com sucesso! [showcaseNegotiationsController]');
				$log.info(JSON.stringify(response));

				$scope.getNegotiationsByUserSeller();
			}, function(responseError) {
				$log.error("Error cancel negotiation: " + JSON.stringify(responseError));
				//$scope.setErrorMessage(responseError, "Não foi possível cancelar a negociação, por favor tente novamente mais tarde.");
			});
		};

		$scope.initialize();

		$log.info('Controller execution ended [showcaseNegotiationsController]');
	}
]);