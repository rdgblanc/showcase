"use strict";

window.module = window.angular.module("showcase", ["ngAnimate", "ngRoute", "nya.bootstrap.select"]);
/*window.module.config(function($httpProvider) {
	$httpProvider.interceptors.push("httpInterceptor");
});*/

window.module.run(['$rootScope', '$location', '$route',
	function($rootScope, $location, $route) {
		$rootScope.checkActiveMenu = function() {
			var path = $location.$$path.split("/");
			$("#bs-megadropdown-tabs li").each(function(index) {
				var href = $(this).children("a").attr("href");
				var dropDown = $(this).hasClass("dropdown");
				if (href && href.length > 0 && !dropDown) {
					href = href.replace(/^#/g, "").split("/");
					for (var i = 0; i < href.length; i++) {
						if (href[i] != path[i]) {
							return true;
						}
					}

					$("#bs-megadropdown-tabs li").removeClass("active");
					$("#bs-megadropdown-tabs li").children("a").removeClass("act");
					$(this).addClass("active");
					$(this).children("a").addClass("act");

					return false;
				}
			});
		}

		// Event to activate automatically scroll.
		/*$rootScope.$on('showcase::activateScroll', function() {
			var $this = $('.scrollable');
		  $this.ace_scroll({
		    size: $this.attr('data-size') || 100,
		  });
		});*/

		/**
		 * Reloads path without refresh page.
		 *
		 * @param {String} path Path to reload.
		 * @return {Objetct} $location object updated.
		 */
		$location.skipReload = function (path) {
			var lastRoute = $route.current, un = $rootScope.$on('$locationChangeSuccess', function () {
				$route.current = lastRoute;
				un();
			});

			return $location.path.apply($location, [path]);
		};

		$rootScope.$on("$routeChangeSuccess", function(event, currentRoute) {
			$rootScope.checkActiveMenu();
		});

		$rootScope.$on("$includeContentLoaded", function(event, templateName){
			console.log('Template has loaded! ' + templateName);
		});
	}
]);

angular.module('showcase').controller("showcaseController", [
	'$scope', '$log', '$anchorScroll', '$location', '$route',
	function($scope, $log, $anchorScroll, $location, $route) {
		$log.info('Controller initialized [ShowcaseController]');

		$scope.viewsEnum = Object.freeze({
			HOME: 'pages/home/home.html',

			ABOUT: 'pages/about/about.html',
			ADS: 'pages/ads/ads.html',
			CONTACT: 'pages/contact/contact.html',
			FAQ: 'pages/faq/faq.html',
			GUIDELINE: 'pages/guideline/guideline.html',

			REGISTER_PRODUCT: 'pages/product/register-product.html'
		});

		$scope.productNegotiationTypeEnum = Object.freeze({
			DONATION: 			'Doação',
			LOAN: 				'Empréstimo',
			EXCHANGE: 			'Troca',
			SALE: 				'Venda',
			EXCHANGE_OR_SALE: 	'Troca ou Venda'
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

		$scope.currentView = $scope.viewsEnum.HOME;
		$scope.currentUser = null;
		//$scope.currentProduct = {};
		//$scope.currentProduct = {"id":1,"nome":"Tênis masculino","descricao":"Tênis masculino branco","marca":"Nike","preco":99.9,"quantidade":1,"estadoConservacao":"NEW","tipoNegociacao":"SALE","dtAtualizacao":"2017-05-16T21:15:11.000+0000","status":"ACTIVE","categoria":{"id":9,"nome":"Sapatos","categoriaPai":{"id":2,"nome":"Calçados"}},"usuario":{"id":1,"nome":"Silmara Santos","email":"s@s.com","sexo":"FEMALE","dtAtualizacao":"2017-05-16T21:43:00.000+0000","status":"ACTIVE","roles":["ROLE_USER","ROLE_ADMIN"]}}

		$scope.initialize = function() {
			var path = $location.$$path.split("/");
			$log.info(path);
			if (path[1] !== undefined) {
				var view = path[1].toUpperCase();
				$log.info('>>>' + view);
				$log.info('>>>' + $scope.viewsEnum[view]);
				if ($scope.viewsEnum[view] !== undefined) {
					$scope.switchView($scope.viewsEnum[view]);
				}
			}
		};

		$scope.checkActiveMenu = function() {
			$log.info('$scope.checkActiveMenu()');
			//var path = $location.$$path.split("/");
			$("#bs-megadropdown-tabs li").removeClass("active");
			$("#bs-megadropdown-tabs li").children("a").removeClass("act");

			switch($scope.currentView) {
				case $scope.viewsEnum.HOME:
					$("#nav-home").addClass("active");
					$("#nav-home").children("a").addClass("act");
					break;
				case $scope.viewsEnum.ABOUT:
					$("#nav-about").addClass("active");
					$("#nav-about").children("a").addClass("act");
					break;
			}

			//$route.reload();
			//$scope.$digest();
		};

		$scope.setCurrentProduct = function(product) {
			$log.info('Set current product.. "' + JSON.stringify(product) + '" [ShowcaseController]');
			$scope.currentProduct = product;
		};

		$scope.setCurrentUser = function(user) {
			$log.info('Set current user.. "' + JSON.stringify(user) + '" [ShowcaseController]');
			$scope.currentUser = user;
		};

		$scope.switchView = function(view) {
			$log.info('Switch view.. "' + view + '" [ShowcaseController]');
			$scope.currentView = view;
			$anchorScroll();
			//$scope.checkActiveMenu();
			//$log.info('switch view..2');
		};

		$scope.anchorScroll = function() {
			$anchorScroll();
		};

		$scope.initialize();

		$log.info('Controller execution ended [ShowcaseController]');
	}]);

var DEFAULT_ERROR_CALLBACK = function(responseData) {
	/*toaster.pop({
		"type" : "error",
		"title" : responseData.errorMessage
	});*/
	console.log(responseData);
};
