!function() {
	'use strict';

	angular.module('JobMngApp').controller('TicketListController', TicketListController);

	TicketListController.$inject = ['$scope', '$log', '$window', '$location', '$http'];

	function TicketListController($scope, $log, $window, $location, $http) {

		var vm = this;
		vm.ticketlist = [];
		
		!function() {			
			$(document).ready(function(){
				
				$("#start").val($("#start").val());
				$("#finish").val($("#finish").val());
				
				$("#searchData").click();
			});			
		}();

		vm.search = function() {

			var dataSend = {
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
			$window.location = "/ticket/" + ticketId + "/view";
		};

		vm.viewJob = function(jobId) {
			$window.location = "/job/view/" + jobId;
		};

		vm.takeTicket = function(ticketId) {
			$window.location = "/ticket/" + ticketId + "/view";
		};

		vm.finishTicket = function(ticketId) {
			$log.debug(ticketId);
		};

		vm.verifyTicket = function(ticketId) {
			$log.debug(ticketId);
		};
		
		vm.toggleFilter = function() {
			$("#filterContent").collapse('toggle');
		}

	}

}();