angular.module('market')
  .controller('ClienteCreateController', function ($scope, $location, Cliente, Cidade, toastr) {

    $scope.init = function() {
        Cidade.getAll(function(data) {
            $scope.cidades = data;
        });
    };

    $scope.save = function() {
        Cliente.save($scope.cliente, function(data) {
            toastr.success('foi salvo com Sucesso.', 'O cliente: ' + $scope.cliente.nome);
            $location.path('/clientes');
        }, function(data) {
            toastr.error(data.data,'Não foi possível Salvar');
        });
    };

    $scope.cancel = function() {
        $location.path('/clientes');
    };

  }).controller('ClienteListController', function ($scope, Cliente, toastr, $routeParams) {

    $scope.init = function() {
        $scope.nomeFiltro = '';

        Cliente.getAll(function(data) {
           $scope.clientes = data;
           $scope.quantidade = $scope.clientes.length;
        });
    };

    $scope.busca = function() {

       if ($scope.nomeFiltro) {
            Cliente.getFiltroCliente({filtro:$scope.nomeFiltro}, $scope.cliente, function(data) {
                $scope.clientes = data;
            });
       } else {
            Cliente.getAll(function(data) {
                $scope.clientes = data;
            });
       };
    };

  }).controller('ClienteDetailController', function ($scope, $modal, $routeParams, $location, Cliente, Cidade, toastr) {

    $scope.init = function() {
        $scope.cliente = Cliente.get({id:$routeParams.id}, function(data) {
        $scope.cidades = Cidade.getAll();
        },function(data) {
            toastr.error(data.data);
        });
    };

    $scope.delete = function() {
        $scope.cliente = Cliente.get({id:$routeParams.id}, function(data) {
            $scope.clienteExcluido = $scope.cliente.nome;
        });
        Cliente.delete({id:$routeParams.id}, function() {
            toastr.warning('foi removido com Sucesso.', 'O cliente: ' + $scope.clienteExcluido);
            $modalInstance.close();
            $location.path('/clientes');
        }, function(data) {
            $modalInstance.close();
            toastr.error(data.data,'Não foi possível Remover');
        });
    };

    $scope.open = function (size) {

        $modalInstance = $modal.open({
              templateUrl: 'modalConfirmacao.html',
              controller: 'ClienteDetailController',
              size: size,
        });
    };

    $scope.cancelModal = function () {
        $modalInstance.dismiss('cancelModal');
    };

  }).controller('ClienteEditController', function ($scope, $modal, $routeParams, $location, Cliente, Cidade, toastr) {


    $scope.init = function() {
        $scope.cliente = Cliente.get({id:$routeParams.id}, function(data) {
        $scope.cidades = Cidade.getAll();
        },function(data) {
            toastr.error(data.data);
        });
    };

    $scope.update = function() {
        Cliente.update({id:$routeParams.id}, $scope.cliente, function(data) {
            toastr.info('foi atualizado com Sucesso.', 'O cliente: ' + $scope.cliente.nome);
            $location.path('/clientes');
        },function(data) {
           toastr.error(data.data, 'Não foi possível Atualizar.');
        });
    };

  });