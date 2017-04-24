'use strict';

/*
* Home controller
*/
angular.module('showcase').controller('showcaseHomeController', [
	'$scope', '$log',
	function($scope, $log) {
		$log.info('Controller initialized [ShowcaseHomeController]');
		$log.info('Controller execution ended [ShowcaseHomeController]');
	}
]);