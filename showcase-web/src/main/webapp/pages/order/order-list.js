'use strict';

/*
* Order controller
*/
angular.module('showcase').controller('showcaseOrderListController', [
	'$scope', '$log', 'negotiationService', 'messageService',
	function($scope, $log, negotiationService, messageService) {
		$log.info('Controller initialized [showcaseOrderListController]');

		$scope.initialize = function() {
			$scope.getNegotiationsByUserOrder();
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

		$scope.getMessagesByNegotiation = function() {
			$log.info('Obtendo as mensagens do negociação.. [showcaseNegotiationsController]');
			$log.info(JSON.stringify($scope.currentNegotiation));

			//$scope.showLoading = true;
			messageService.getMessagesByNegotiation($scope.currentNegotiation.id, function(response) {
				$log.info('Mensagens do negociação obtidas com sucesso! [showcaseNegotiationsController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.messages = response.data;
				}
			}, function(responseError) {
				$log.error("Error get messages by negotiation: " + JSON.stringify(responseError));
				//$scope.setErrorMessage(responseError, "Não foi possível recuperar as mensagens da negociação, por favor tente novamente mais tarde.");
			});
		};

		$scope.showNegotiation = function(negotiation) {
			$scope.currentNegotiation = negotiation;
		};

		$scope.showMessages = function(negotiation) {
			$scope.currentNegotiation = negotiation;
			$scope.getMessagesByNegotiation();
		};

		$("#btn-input").keypress(function(e) {
			if(e.which == 13) {
				$scope.createMessage();
			}
		});

		$scope.createMessage = function() {
			$log.info('Criando mensagem.. [showcaseNegotiationsController]');
			var message = {};
			message.texto = $('#btn-input').val();
			message.negociacao = $scope.currentNegotiation;
			message.usuario = $scope.$parent.currentUser;
			$log.info(JSON.stringify(message));

			$('#btn-input').val('');

			//$scope.showLoading = true;
			messageService.createMessage(message, function(response) {
				$log.info('Mensagem criada com sucesso! [showcaseNegotiationsController]');
				$log.info(JSON.stringify(response));

				$scope.getMessagesByNegotiation();
			}, function(responseError) {
				$log.error("Error create message: " + JSON.stringify(responseError));
				//$scope.setErrorMessage(responseError, "Não foi possível criar a mensagem, por favor tente novamente mais tarde.");
			});
		};

		$scope.cancelNegotiation = function(negotiation) {
			$log.info('Cancelando a negociação.. [showcaseOrderListController]');
			$log.info(JSON.stringify(negotiation));

			//$scope.showLoading = true;
			negotiationService.updateNegotiationStatus(negotiation.id, 'CANCELED', function(response) {
				$log.info('Negociação cancelada com sucesso! [showcaseOrderListController]');
				$log.info(JSON.stringify(response));

				$scope.getNegotiationsByUserOrder();
			}, function(responseError) {
				$log.error("Error cancel negotiation: " + JSON.stringify(responseError));
				//$scope.setErrorMessage(responseError, "Não foi possível cancelar a negociação, por favor tente novamente mais tarde.");
			});
		};

		$scope.initialize();

		$log.info('Controller execution ended [showcaseNegotiationsController]');
	}
]);