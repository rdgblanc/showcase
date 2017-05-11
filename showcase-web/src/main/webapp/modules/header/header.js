'use strict';

/*
* Header controller
*/
angular.module('showcase').controller("showcaseHeaderController", [
	'$scope', '$log', 'userService',
	function($scope, $log, userService) {
		$log.info('Controller initialized [ShowcaseHeaderController]');

		$scope.user = {};
		$scope.showLoading = false;


		//$scope.userLogged = true;

		$scope.create = function() {
			$log.info('Criando usuário.. [ShowcaseHeaderController]');
			$log.info(JSON.stringify($scope.user));

			$scope.showLoading = true;
			userService.createUser($scope.user, function(response) {
				$log.info('Usuário criado com sucesso! [ShowcaseHeaderController]');
				$log.info(JSON.stringify(response));

				$scope.showLoading = false;
				$scope.message = {
					"type" : "success",
					"title" : "Ok!",
					"body" : "Cadastro efetuado com sucesso. Valide seu e-mail para ter acesso ao site."
				};

				setTimeout(function() {
					$('#modal-login').modal('hide');
				}, 5000);
			}, function(responseError) {
				$log.error("Error create user: " + JSON.stringify(responseError));
				$scope.setErrorMessage(responseError, "Não foi possível efetuar o cadastro, por favor tente novamente mais tarde.");
			});
		};

		$scope.update = function() {
			$log.info('Alterando usuário.. [ShowcaseHeaderController]');
			$log.info(JSON.stringify($scope.user));

			$scope.showLoading = true;
			userService.updateUser($scope.user.id, $scope.user, function(response) {
				$log.info('Usuário alterado com sucesso! [ShowcaseHeaderController]');
				$log.info(JSON.stringify(response));

				$scope.showLoading = false;
				$scope.message = {
					"type" : "success",
					"title" : "Ok!",
					"body" : "Cadastro alterado com sucesso!"
				};
				$scope.user = response;

				setTimeout(function() {
					$('#modal-personal').modal('hide');
				}, 5000);
			}, function(responseError) {
				$log.error("Error update user: " + JSON.stringify(responseError));
				$scope.setErrorMessage(responseError, "Não foi possível alterar o cadastro, por favor tente novamente mais tarde.");
			});
		};

		$scope.auth = function() {
			$log.info('Autenticando usuário.. [ShowcaseHeaderController]');
			$log.info(JSON.stringify($scope.user));
			$scope.showLoading = true;

			userService.authenticate($scope.user, function(response) {
				$log.info('Usuário logado!! [ShowcaseHeaderController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.user = response.data;
				}
				$scope.userLogged = true;
				$scope.showLoading = false;
				$('#modal-login').modal('hide');
			}, function(responseError) {
				$log.error("Error auth: " + JSON.stringify(responseError));
				$scope.setErrorMessage(responseError, "Não foi possível efetuar o login, por favor tente novamente mais tarde.");
			});
		};

		/*$scope.getCurrentUser = function() {
			$log.info('Obtendo informações do usuário logado.. [ShowcaseHeaderController]');
			userService.getCurrentUser(function(response) {
				$log.info('Informações obtidas do usuário logado!! [ShowcaseHeaderController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.user = response.data;
				}
			}, function(responseError) {
				$scope.userNotFound = true;
				$('#btnUpdateUser').prop('disabled', true);
				$log.error("Error getCurrentUser: " + JSON.stringify(responseError));
				$scope.setErrorMessage(responseError, "Não foi possível recuperar as informações do usuário logado.");
			});
		};*/

		$scope.setErrorMessage = function(responseError, defaultMessage) {
			var msg = defaultMessage;
			if (responseError && responseError.data && responseError.data.message) {
				msg = responseError.data.message;
			}

			$scope.showLoading = false;
			$scope.message = {
				"type" : "danger",
				"title" : "Ops!",
				"body" : msg
			};
		};

		$scope.openPersonal = function() {
			if ($scope.userLogged) {
				$('#modal-personal').modal('show');
			} else {
				$scope.reset();
				$('#modal-login').modal('show');
			}
		};

		$scope.reset = function () {
			$scope.user = {};
			$scope.message = {};
			$scope.showLoading = false;
			$scope.userNotFound = false;

			//registerForm.$setPristine();
      		//registerForm.$setUntouched();
		};

		$('#datepicker').datepicker({
			language: "pt-BR",
			format: "dd/mm/yyyy",
			autoclose : true,
			todayHighlight: true,
			toggleActive: true
		});
		//$('#datepicker').datepicker('setDate', $scope.filterDate);

		function resetAddressForm() {
			// Limpa valores do formulário de endereço.
			$("#endereco").val("");
			$("#bairro").val("");
			$("#cidade").val("");
			$("#estado").val("");
			//$scope.cepError = false;
		};

		$("#cep").keyup(function() {
			resetAddressForm();
			$scope.cepError = false;
		});

		//Quando o campo cep perde o foco.
		$("#cep").blur(function() {
			//Nova variável "cep" somente com dígitos.
			var cep = $(this).val().replace(/\D/g, '');

			//Verifica se campo cep possui valor informado.
			if (cep != "") {
				//Expressão regular para validar o CEP.
				var validacep = /^[0-9]{8}$/;

				//Valida o formato do CEP.
				if(validacep.test(cep)) {
					//Preenche os campos com "..." enquanto consulta webservice.
					$("#endereco").val("...");
					$("#bairro").val("...");
					$("#cidade").val("...");
					$("#estado").val("...");
					$scope.showLoading = true;

					//Consulta o webservice viacep.com.br/
					$.getJSON("//viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {
						$scope.showLoading = false;
						if (!("erro" in dados)) {
							//Atualiza os campos com os valores da consulta.
							$("#endereco").val(dados.logradouro);
							$("#bairro").val(dados.bairro);
							$("#cidade").val(dados.localidade);
							$("#estado").val(dados.uf);
						} else {
							//CEP pesquisado não foi encontrado.
							resetAddressForm();
							$scope.cepError = true;
							//$log.info("CEP não encontrado.");
							$scope.setErrorMessage(null, "CEP não encontrado.");
							$scope.$apply();
						}
					});
				} else {
					//CEP é inválido.
					resetAddressForm();
					$scope.cepError = true;
					//$log.info("Formato de CEP inválido.");
					$scope.setErrorMessage(null, "Formato de CEP inválido.");
					$scope.$apply();
				}
			} else {
				//cep sem valor, limpa formulário.
				resetAddressForm();
			}
		});

		$log.info('Controller execution ended [ShowcaseHeaderController]');
	}]
);