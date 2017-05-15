'use strict';

/*
* Header controller
*/
angular.module('showcase').controller("showcaseHeaderController", [
	'$scope', '$log', 'userService', 'addressService',
	function($scope, $log, userService, addressService) {
		$log.info('Controller initialized [ShowcaseHeaderController]');

		$scope.user = {};
		$scope.showLoading = false;
		//$scope.userLogged = true;

		/* LOGIN MODAL */
		$scope.auth = function() {
			$log.info('Autenticando usuário.. [ShowcaseHeaderController]');
			$log.info(JSON.stringify($scope.user));
			$scope.showLoading = true;

			userService.authenticate($scope.user, function(response) {
				$log.info('Usuário logado!! [ShowcaseHeaderController]');
				$log.info(JSON.stringify(response));

				if (response && response.data) {
					$scope.currentUser = response.data;
				}
				$scope.userLogged = true;
				$scope.showLoading = false;
				$('#modal-login').modal('hide');
			}, function(responseError) {
				$log.error("Error auth: " + JSON.stringify(responseError));
				$scope.setErrorMessage(responseError, "Não foi possível efetuar o login, por favor tente novamente mais tarde.");
			});
		};

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

				/*setTimeout(function() {
					$('#modal-login').modal('hide');
				}, 5000);*/
			}, function(responseError) {
				$log.error("Error create user: " + JSON.stringify(responseError));
				$scope.setErrorMessage(responseError, "Não foi possível efetuar o cadastro, por favor tente novamente mais tarde.");
			});
		};
		/* LOGIN MODAL end */

		/* PERSONAL MODAL */
		$scope.update = function() {
			$log.info('Alterando usuário.. [ShowcaseHeaderController]');
			$log.info(JSON.stringify($scope.currentUser));
			$scope.currentUser.dtNascimento = $('#datepicker').datepicker('getDate');

			$scope.showLoading = true;
			userService.updateUser($scope.currentUser.id, $scope.currentUser, function(response) {
				$log.info('Usuário alterado com sucesso! [ShowcaseHeaderController]');
				$log.info(JSON.stringify(response));

				$scope.showLoading = false;
				$scope.message = {
					"type" : "success",
					"title" : "Ok!",
					"body" : "Cadastro alterado com sucesso!"
				};

				if (response && response.data) {
					$scope.currentUser = response.data;
				}

				/*setTimeout(function() {
					$('#modal-personal').modal('hide');
				}, 5000);*/
			}, function(responseError) {
				$log.error("Error update user: " + JSON.stringify(responseError));
				$scope.setErrorMessage(responseError, "Não foi possível alterar o cadastro, por favor tente novamente mais tarde.");
			});
		};

		$scope.getAddressByUser = function() {
			$log.info('Obtendo endereço do usuário.. [ShowcaseHeaderController]');
			$log.info(JSON.stringify($scope.currentUser));

			//$scope.showLoading = true;
			addressService.getAddressByUser($scope.currentUser.id, function(response) {
				$log.info('Endereço do usuário obtido com sucesso! [ShowcaseHeaderController]');
				$log.info(JSON.stringify(response));

				//$scope.showLoading = false;
				/*$scope.message = {
					"type" : "success",
					"title" : "Ok!",
					"body" : "Cadastro alterado com sucesso!"
				};*/

				if (response && response.data && response.data.length > 0) {
					$scope.address = response.data[0];
				}
			}, function(responseError) {
				$log.error("Error get address by user: " + JSON.stringify(responseError));
				$scope.setErrorMessage(responseError, "Não foi possível recuperar o endereço do usuário, por favor tente novamente mais tarde.");
			});
		};

		$scope.saveAddress = function() {
			if ($scope.address && $scope.address.id) {
				$scope.updateAddress();
			} else {
				$scope.createAddress();
			}
		};

		$scope.createAddress = function() {
			$log.info('Criando endereço do usuário.. [ShowcaseHeaderController]');

			if ($scope.address) {
				$scope.address.usuario = $scope.currentUser;
			}
			$log.info(JSON.stringify($scope.address));

			$scope.showLoading = true;
			addressService.createAddress($scope.address, function(response) {
				$log.info('Endereço criado com sucesso! [ShowcaseHeaderController]');
				$log.info(JSON.stringify(response));

				$scope.showLoading = false;
				$scope.message = {
					"type" : "success",
					"title" : "Ok!",
					"body" : "Endereço criado com sucesso!"
				};

				if (response && response.data) {
					$scope.address = response.data;
				}
			}, function(responseError) {
				$log.error("Error create address: " + JSON.stringify(responseError));
				$scope.setErrorMessage(responseError, "Não foi possível criar o endereço do usuário, por favor tente novamente mais tarde.");
			});
		};

		$scope.updateAddress = function() {
			$log.info('Alterando endereço do usuário.. [ShowcaseHeaderController]');
			$log.info(JSON.stringify($scope.address));

			$scope.showLoading = true;
			addressService.updateAddress($scope.address.id, $scope.address, function(response) {
				$log.info('Endereço alterado com sucesso! [ShowcaseHeaderController]');
				$log.info(JSON.stringify(response));

				$scope.showLoading = false;
				$scope.message = {
					"type" : "success",
					"title" : "Ok!",
					"body" : "Endereço alterado com sucesso!"
				};

				if (response && response.data) {
					$scope.address = response.data;
				}
			}, function(responseError) {
				$log.error("Error update address: " + JSON.stringify(responseError));
				$scope.setErrorMessage(responseError, "Não foi possível alterar o endereço do usuário, por favor tente novamente mais tarde.");
			});
		};

		$scope.updatePassword = function() {
			$log.info('Alterando senha do usuário.. [ShowcaseHeaderController]');
			$log.info(JSON.stringify($scope.currentUser));

			$scope.showLoading = true;
			userService.updatePassword($scope.currentUser.id, $scope.currentUser, function(response) {
				$log.info('Senha do usuário alterada com sucesso! [ShowcaseHeaderController]');
				$log.info(JSON.stringify(response));

				$scope.showLoading = false;
				$scope.message = {
					"type" : "success",
					"title" : "Ok!",
					"body" : "Senha alterada com sucesso!"
				};
			}, function(responseError) {
				$log.error("Error update user password: " + JSON.stringify(responseError));
				$scope.setErrorMessage(responseError, "Não foi possível alterar a senha, por favor tente novamente mais tarde.");
			});
		};
		/* PERSONAL MODAL end */

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

		/* RESETs */
		$scope.resetMessages = function() {
			$scope.message = {};
			$scope.showLoading = false;
		};

		$scope.resetModalLogin = function() {
			$scope.user = {};
			$scope.resetMessages();
		};

		$scope.resetLoginForm = function() {
			$scope.resetModalLogin();
			$scope.loginForm.$setPristine();
		};

		$scope.resetRegisterForm = function() {
			$scope.resetModalLogin();
			$scope.registerForm.$setPristine();
		};

		$scope.resetUpdateForm = function() {
			$scope.resetMessages();
			$scope.updateForm.$setPristine();
		};

		$scope.resetAddressForm = function() {
			$scope.resetMessages();
			$scope.addressForm.$setPristine();
		};

		$scope.resetPasswordForm = function() {
			$scope.resetMessages();
			$scope.passwordForm.$setPristine();
		};

		$('#modal-login').on('hide.bs.modal', function() {
			$scope.resetModalLogin();
			$('#tabLogin').trigger('click');
			$scope.loginForm.$setPristine();
			$scope.registerForm.$setPristine();
		});

		$('#modal-personal').on('hide.bs.modal', function() {
			$scope.resetMessages();
			$('#tabPersonal').trigger('click');
			$scope.updateForm.$setPristine();
			$scope.addressForm.$setPristine();
			$scope.passwordForm.$setPristine();
		});
		/* RESETs end */

		$('#modal-personal').on('show.bs.modal', function() {
			console.log($scope.currentUser);
			if ($scope.currentUser && $scope.currentUser.sexo) {
				if ($scope.currentUser.sexo === 'MALE') {
					$('#radioMale').trigger('click');
				} else if ($scope.currentUser.sexo === 'FEMALE') {
					$('#radioFemale').trigger('click');
				}
			}
			if ($scope.currentUser && $scope.currentUser.dtNascimento) {
				$('#datepicker').datepicker('setDate', new Date($scope.currentUser.dtNascimento));
			}

			$scope.getAddressByUser();
		});

		$("input[type=text], input[type=email], input[type=tel], input[type=password]").keyup(function() {
			$scope.resetMessages();
		});

		$('#radioBtn a').on('click', function() {
			var sel = $(this).data('title');
			var tog = $(this).data('toggle');
			$('#'+tog).prop('value', sel);
			$scope.currentUser.sexo = sel;

			$('a[data-toggle="'+tog+'"]').not('[data-title="'+sel+'"]').removeClass('active').addClass('notActive');
			$('a[data-toggle="'+tog+'"][data-title="'+sel+'"]').removeClass('notActive').addClass('active');
		});

		$('#datepicker').datepicker({
			language: 'pt-BR',
			format: 'dd/mm/yyyy',
			autoclose : true,
			changeMonth: true,
            changeYear: true,
            //yearRange: '1910:2010',
            //maxDate: '-18Y',
    		//minDate: '-20Y',
    		//defaultDate: '-18Y',
			//todayHighlight: true,
			toggleActive: true
		});

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
					$scope.address.endereco = null;
					$("#bairro").val("...");
					$scope.address.bairro = null;
					$("#cidade").val("...");
					$scope.address.cidade = null;
					$("#estado").val("...");
					$scope.address.estado = null;
					//$scope.showLoading = true;

					//Consulta o webservice viacep.com.br/
					$.getJSON("//viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {
						//$scope.showLoading = false;
						if (!("erro" in dados)) {
							//Atualiza os campos com os valores da consulta.
							$("#endereco").val(dados.logradouro);
							$scope.address.endereco = dados.logradouro;
							$("#bairro").val(dados.bairro);
							$scope.address.bairro = dados.bairro;
							$("#cidade").val(dados.localidade);
							$scope.address.cidade = dados.localidade;
							$("#estado").val(dados.uf);
							$scope.address.estado = dados.uf;
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