'use strict';

/*
* Main controller
*/
window.module = window.angular.module("showcase", ["ngAnimate", "ngRoute", "nya.bootstrap.select"]);
/*window.module.config(function($httpProvider) {
	$httpProvider.interceptors.push("httpInterceptor");
});*/

/*window.module.run(['$rootScope', '$location', '$route',
	function($rootScope, $location, $route) {
		$rootScope.$on("$routeChangeSuccess", function(event, currentRoute) {
			$rootScope.checkActiveMenu();
		});

		$rootScope.$on("$includeContentLoaded", function(event, templateName){
			console.log('Template has loaded! ' + templateName);
		});
	}
]);*/

angular.module('showcase').controller("showcaseController", [
	'$scope', '$log', '$anchorScroll', '$location', '$route', 'userService',
	function($scope, $log, $anchorScroll, $location, $route, userService) {
		$log.info('Controller initialized [ShowcaseController]');

		$scope.viewsEnum = Object.freeze({
			HOME: 'pages/home/home.html',

			ABOUT: 'pages/about/about.html',
			CONTACT: 'pages/contact/contact.html',
			FAQ: 'pages/faq/faq.html',
			GUIDELINE: 'pages/guideline/guideline.html',

			FAVORITES: 'pages/favorites/favorites.html',
			NEGOTIATIONS: 'pages/negotiations/negotiations.html',
			ORDER_LIST: 'pages/order-list/order-list.html',
			PRODUCTS: 'pages/products/products.html',

			ADS: 'pages/ads/ads.html',
			REGISTER_PRODUCT: 'pages/products/register-product.html'
		});

		$scope.productNegotiationTypeEnum = Object.freeze({
			DONATION: 			'Doação',
			LOAN: 				'Empréstimo',
			EXCHANGE: 			'Troca',
			SALE: 				'Venda',
			EXCHANGE_OR_SALE: 	'Troca ou Venda'
		});

		$scope.negotiationNegotiationTypeEnum = Object.freeze({
			DONATION: 			'Doação',
			LOAN: 				'Empréstimo',
			EXCHANGE: 			'Troca',
			BUY: 				'Compra',
			EXCHANGE_OR_BUY: 	'Troca ou Compra'
		});

		$scope.productConservationStateEnum = Object.freeze({
			NEW: 		'Novo',
			SEMI_NEW: 	'Seminovo',
			USED: 		'Usado'
		});

		$scope.productStatusEnum = Object.freeze({
			INACTIVE: 	'Inativo',
			ACTIVE: 	'Ativo',
			DENOUNCED: 	'Denunciado',
			BLOCKED: 	'Bloqueado'
		});

		$scope.negotiationStatusEnum = Object.freeze({
			IN_PROGRESS: 	'Em progresso',
			ACCEPT: 		'Aceita',
			CANCELED: 		'Cancelada',
			DENOUNCED: 		'Denunciada',
			FINISHED: 		'Finalizada'
		});

		$scope.imagesUploadPath = 'http://localhost/showcase-web/src/main/webapp/assets/upload/';

		$scope.welcomeTokenValidated = false;
		$scope.currentView = $scope.viewsEnum.HOME;
		$scope.currentUser = null;
		//$scope.currentUser = {"id":1,"nome":"Silmara Santos","email":"s@s.com","sexo":"MALE","dtAtualizacao":"2017-05-17T23:47:53.000+0000","status":"ACTIVE","roles":["ROLE_USER","ROLE_ADMIN"]};
		//$scope.currentProduct = {};
		//$scope.currentProduct = {"id":1,"nome":"Tênis masculino","descricao":"Tênis masculino branco","marca":"Nike","preco":99.9,"quantidade":1,"estadoConservacao":"NEW","tipoNegociacao":"SALE","dtAtualizacao":"2017-05-16T21:15:11.000+0000","status":"ACTIVE","categoria":{"id":9,"nome":"Sapatos","categoriaPai":{"id":2,"nome":"Calçados"}},"usuario":{"id":1,"nome":"Silmara Santos","email":"s@s.com","sexo":"FEMALE","dtAtualizacao":"2017-05-16T21:43:00.000+0000","status":"ACTIVE","roles":["ROLE_USER","ROLE_ADMIN"]}}

		$scope.initialize = function() {
			var path = $location.$$path.split("/");
			$log.info(path);
			if (path[1] !== undefined) {
				if (path[1] === 'token' && path[2] !== undefined) {
					if (path[2] === 'welcome' && path[3] !== undefined) {
						$scope.validateWelcomeToken(path[3]);
						return;
					}
				}

				var view = path[1].toUpperCase();
				$log.info('ViewsEnum[view]: ' + $scope.viewsEnum[view]);
				if ($scope.viewsEnum[view] !== undefined) {
					$scope.switchView($scope.viewsEnum[view]);
				}
			}
		};

		$scope.validateWelcomeToken = function(token) {
			$log.info('Validando token informado.. [ShowcaseController]');
			$log.info(JSON.stringify(token));

			userService.validateWelcomeToken(token, function(response) {
				$log.info('Token de boas vindas validado com sucesso!! [ShowcaseController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.welcomeTokenValidated = true;
				}
			}, function(responseError) {
				$log.error('Error validate welcome token: ' + JSON.stringify(responseError) + ' [ShowcaseController]');
				//$scope.setErrorMessage(responseError, "Não foi possível validar o token de boas vindas, por favor tente novamente mais tarde.");
			});
		};

		$scope.setCurrentProduct = function(product) {
			$log.info("USAR ROOTSCOPE BROADCAST E ON PARA ENVIAR O PRODUTO DE UM CONTROLLER PARA O OUTRO...");
			$log.info('Set current product.. "' + JSON.stringify(product) + '" [ShowcaseController]');
			$scope.currentProduct = product;
		};

		$scope.setCurrentUser = function(user) {
			$log.info('Set current user.. "' + JSON.stringify(user) + '" [ShowcaseController]');
			$scope.welcomeTokenValidated = false;
			$scope.currentUser = user;
		};

		$scope.switchView = function(view) {
			$log.info('Switch view.. "' + view + '" [ShowcaseController]');
			$scope.currentView = view;
			$anchorScroll();
		};

		$scope.anchorScroll = function() {
			$anchorScroll();
		};

		$scope.initialize();

		$log.info('Controller execution ended [ShowcaseController]');
	}]
);

var DEFAULT_ERROR_CALLBACK = function(responseData) {
	console.log(responseData);
};