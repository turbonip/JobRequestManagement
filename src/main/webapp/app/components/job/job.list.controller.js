!function() {
	'use strict';

	angular.module('JobMngApp').controller('JobListController', JobListController);

	JobListController.$inject = [ '$scope', '$log', '$window', '$location', '$http' ];

	function JobListController($scope, $log, $window, $location, $http) {

		var vm = this;
		vm.joblist = [];

		vm.search = function() {

			let searchData = {
				jobId : $("[name='jobId']").val(),
				problemCat : $("[name='problemCat']").val(),
				jobDateBegin : $("[name='jobDateBegin']").val(),
				jobDateEnd : $("[name='jobDateEnd']").val(),
				jobLocation : $("[name='jobLocation']").val(),
				jobStatus : $("[name='jobStatus']").val()
			};

			$log.debug(searchData);

			$http.post('/job/search', searchData).then(function(response) {
				$log.debug(response);
				vm.joblist = response.data;
			});

		};

		vm.view = function(id) {
			$window.location.href = '/job/view/' + id;
		};

		vm.create = function() {
			$window.location.href = '/job/create';
		};

		vm.edit = function(id) {
			$log.debug("edit" + id);
		};

	}

}();