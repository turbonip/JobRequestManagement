!function() {
	'use strict';

	angular.module('JobMngApp').controller('TicketListController', TicketListController);

	TicketListController.$inject('$scope', '$log', '$window', '$location', '$http');

	function TicketListController($scope, $log, $window, $location, $http) {

		var vm = this;

		vm.ticketlist = [];

		vm.search = function() {

			let dateSend = {
				ticketId : $("[name='ticketId']").val(),
				jobId : $("[name='jobId']").val(),
				assignAtStart : $("[name='assignDateBegin']").val(),
				assignAtEnd : $("[name='assignDateEnd']").val(),
				promblemCatId : $("[name='problemCat']").val(),
				jobLocationId : $("[name='jobLocation']").val(),
				deviceInfoId : $("[name='deviceInfo']").val(),
				ticketStatusValue : $("[name='ticketStatus']").val()
			};

			$http.post('/ticket/search', dataSend)
				.then(function(response) {					
					
					vm.ticketlist = response.data;
					
			}, function(response) {
				$log.debug(response.statusTexts);
			});

		};

		vm.viewTicket = function(ticketId) {
			$log.debug(ticketId);
		};

		vm.viewJob = function(jobId) {
			$log.debug(jobId);
		};

		vm.takeTicket = function(ticketId) {
			$log.debug(ticketId);
		};

		vm.finishTicket = function(ticketId) {
			$log.debug(ticketId);
		};

		vm.verifyTicket = function(ticketId) {
			$log.debug(ticketId);
		};

	}

}();