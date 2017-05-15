!function() {
	'use strict';

	angular.module('JobMngApp').controller('TicketFormController', TicketFormController);

	TicketFormController.$inject = [ '$scope', '$log', '$window', '$location', '$http' ];

	function TicketFormController($scope, $log, $window, $location, $http) {

		var vm = this;

		vm.close = function() {
			$window.history.back();
		};

		vm.create = function() {

			let dataSave = {
				jobId : $("[name='jobId']").val(),
				assignToId : $("[name='assignTo']").val(),
				assignRemark : $("[name='assignRemark']").val()
			};

			$http.post("/ticket/create", dataSave).then(function(response) {

				if (response.status == 200) {
					$window.location.href = '/job/view/' + $("[name='jobId']").val();
				} else {
					alert(response.statusText + '\n' + response.data.message);
				}

			}, function(response) {
				alert('Server Unavailable');
			});

		};

		vm.takeTicket = function() {

			let ticketId = $("[name='ticketId']").val();

			$http.post("/ticket/" + ticketId + "/take").then(function(response) {

				if (response.status == 200) {
					$window.location.href = "/ticket/" + ticketId + "/view";
				} else {
					alert(response.statusText + '\n' + response.data.message);
				}

			}, function(response) {
				alert('Server Unavailable');
			});

		};

		vm.finishTicket = function() {

			let ticketId = $("[name='ticketId']").val();
			let resolveDescription = $("[name='ticketResovle']").val();

			$http.post("/ticket/" + ticketId + "/finish", resolveDescription).then(function(response) {

				if (response.status == 200) {
					$window.location.href = "/ticket/" + ticketId + "/view";
				} else {
					alert(response.statusText + '\n' + response.data.message);
				}

			}, function(response) {
				alert('Server Unavailable');
			});

		};

		vm.newTicket = function() {

			let ticketId = $("[name='ticketId']").val();
			let jobId = $("[name='jobId']").val();

			closeTicket(ticketId, function() {
				$window.location.href = "/ticket/create?jobid=" + jobId;
			});

		};

		vm.closeJob = function() {

			let ticketId = $("[name='ticketId']").val();
			let jobId = $("[name='jobId']").val();

			closeTicket(ticketId, function() {
				$window.location.href = "/job/view/" + jobId;
			});

		};

		function closeTicket(ticketId, callbackFunction) {

			$http.post("/ticket/" + ticketId + "/close").then(function(response) {

				if (response.status == 200) {

					if (typeof callbackFunction != 'undefined' && callbackFunction != null)
						callbackFunction();

				} else {
					alert(response.statusText + '\n' + response.data.message);
				}

			}, function(response) {
				alert('Server Unavailable');
			});

		}

	}

}();