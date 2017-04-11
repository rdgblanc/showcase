"use strict";

window.module = window.angular.module("showcase", ["ngAnimate", "ngRoute"]);
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
	}
]);

angular.module('showcase').controller("showcaseController", [
	'$scope', '$log', '$anchorScroll', '$location', '$route',
	function($scope, $log, $anchorScroll, $location, $route) {
		$log.info('Showcase controller.....');

		$scope.viewsEnum = Object.freeze({
			HOME: 'pages/home/home.html',

			ABOUT: 'pages/about/about.html',
			GUIDELINE: 'pages/guideline/guideline.html',
			CONTACT: 'pages/contact/contact.html',
			FAQ: 'pages/faq/faq.html'
		});

		$scope.currentView = $scope.viewsEnum.HOME;

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

		$scope.switchView = function(view) {
			$log.info('switch view..1');
			$scope.currentView = view;
			$anchorScroll();
			//$scope.checkActiveMenu();
			$log.info('switch view..2');
		};

		$scope.anchorScroll = function() {
			$anchorScroll();
		};

		$scope.initialize();

	}]);

var DEFAULT_ERROR_CALLBACK = function(responseData) {
	toaster.pop({
		"type" : "error",
		"title" : responseData.errorMessage
	});
};
