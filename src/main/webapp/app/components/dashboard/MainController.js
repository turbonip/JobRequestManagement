(function() {
	'use strict';

	angular.module('PayrollApp').controller('DashboardController',
			 ['$log','$scope', DashboardController]);

	function DashboardController($log, $scope) {
		var vm = this;
		vm.test = "5555";
		console.log(vm);
	}
})();
