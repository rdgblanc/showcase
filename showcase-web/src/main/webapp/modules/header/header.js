'use strict';

/*
* Header controller
*/
angular.module('showcase').controller("showcaseHeaderController", [
	'$scope', '$log', 'userService',
	function($scope, $log, userService) {
		$log.info('Controller initialized [ShowcaseHeaderController]');

		$scope.user = {};

		$scope.save = function (user) {
			if (user && user.nome && user.email && user.senha) {
				$log.info('User save executed [ShowcaseHeaderController]');
				userService.storeUser(user, function(response) {
					$log.info('Usuário criado com sucesso! [ShowcaseHeaderController]');
					$scope.auth(user);
					//$modalInstance.close();
					//$('#modal-login').modal('hide');
				});
			}
		};

		$scope.auth = function(user) {
			if (user && user.email && user.senha) {
				$log.info('User logging.. [ShowcaseHeaderController]');
				userService.authenticate(user, function(response) {
					$log.info('Usuário logado!! [ShowcaseHeaderController]');
					$scope.userLogged = true;
					//$modalInstance.close();
					$('#modal-login').modal('hide');
				});
			}
		};

		$scope.reset = function () {
			$scope.user = {};
		};

		$log.info('Controller execution ended [ShowcaseHeaderController]');
	}]
);