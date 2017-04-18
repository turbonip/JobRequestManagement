!function() {
	'use strict';

	angular.module('JobMngApp').controller('JobDetailController', JobDetailController);

	JobDetailController.$inject = [ '$scope', '$log', '$window', '$location', '$http' ];

	function JobDetailController($scope, $log, $window, $location, $http) {

		var vm = this;
		vm.productionLineList = {};

		vm.onLocationChange = function(locationIdSelected) {

			$http.get("/productionline/getByLocation", {
				params : {
					locationId : locationIdSelected
				}
			}).then(function(response) {

				vm.productionLineList = response.data;

			});

		};

		vm.save = function() {

			let mode = $("[name='formMode']").val();

			let dataSave = {

				jobId : $("[name='jobId']").val(),

				locationId : $("[name='jobLocation']").val(),

				productLineId : $("[name='jobProductLine']").val(),

				problemCatId : $("[name='problemCat']").val(),

				deviceInfoId : $("[name='deviceInfo']").val(),

				jobDescription : $("[name='jobDescription']").val(),

				jobStatus : $("[name='jobStatus']").val(),

				jobResolve : $("[name='jobResolve']").val()

			};

			$http.post('/job/save/' + mode, dataSave).then(function(response) {
				$window.location.href = '/job/';
			});

		};

		vm.close = function() {
			$window.location.href = '/job/';
		};

		vm.closeJob = function() {

			let dataSend = {
				jobId : $("[name='jobId']").val(),
				jobResolve : $("[name='jobResolve']").val()
			};

			$http.post('/job/close/', dataSave).then(function(response) {

				if (response.status == 200) {
					$window.location.href = '/job/view/' + $("[name='jobId']").val();
				} else {
					alert(response.data);
				}

			}, function(response) {
				alert(response.statusText);
			});

		};

		vm.createTicket = function(jobId) {

			$window.location.href = "/ticket/create?jobid=" + jobId;
		};

	}

}();
