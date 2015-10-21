angular.module('market')
  .controller('EstadoCreateController', function ($scope, $location, Estado, Pais, toastr) {

      $scope.init = function() {
          Pais.getAll(function(data) {
              $scope.paises = data;
          });
      };

    $scope.save = function() {
        Estado.save($scope.estado, function(data) {
            toastr.success('foi salvo com Sucesso.', 'O estado: ' + $scope.estado.nome);
            $location.path('/estados');
        }, function(data) {
            toastr.error(data.data, 'Não foi possível Salvar.');
        });
    };

    $scope.cancel = function() {
        $location.path('/estados');
    };

  }).controller('EstadoListController', function ($scope, $routeParams, Estado) {

    $scope.init = function() {
        $scope.nomeFiltro = '';

        Estado.getAll(function(data) {
           $scope.estados = data;
           $scope.quantidade = $scope.estados.length;
        });
    };

    //* filtra por nome do estado *//
    $scope.busca = function() {

       if ($scope.nomeFiltro) {
            Estado.getFiltroEstado({filtro:$scope.nomeFiltro}, $scope.estado, function(data) {
                $scope.estados = data;
            });
       } else {
            Estado.getAll(function(data) {
                $scope.estados = data;
            });
       };
    };

  }).controller('EstadoDetailController', function ($scope, $modal, $routeParams, $location, Estado, Pais, toastr) {

    $scope.init = function() {
        $scope.estado = Estado.get({id:$routeParams.id}, function(data) {
        $scope.paises = Pais.getAll();
        },function(data) {
            toastr.error(data.data);
        });
    };

    $scope.delete = function() {
        $scope.estado = Estado.get({id:$routeParams.id}, function(data) {
            $scope.estadoExcluido = $scope.estado.nome;
        });
        Estado.delete({id:$routeParams.id}, function() {
            toastr.warning('foi removido com Sucesso.', 'O estado: ' + $scope.estadoExcluido);
            $modalInstance.close();
            $location.path('/estados');
        }, function(data) {
            $modalInstance.close();
            toastr.error(data.data, 'Não foi possível Remover.');
        });
    };

    $scope.open = function (size) {

        $modalInstance = $modal.open({
              templateUrl: 'modalConfirmacao.html',
              controller: 'EstadoDetailController',
              size: size,
        });
    };

    $scope.cancelModal = function () {
        $modalInstance.dismiss('cancelModal');
    };

  }).controller('EstadoEditController', function ($scope, $modal, $routeParams, $location, Estado, Pais, toastr) {


    $scope.init = function() {
        $scope.estado = Estado.get({id:$routeParams.id}, function(data) {
        $scope.paises = Pais.getAll();
        },function(data) {
            toastr.error(data.data);
        });
    };

    $scope.update = function() {
        Estado.update({id:$routeParams.id}, $scope.estado, function(data) {
            toastr.info('foi atualizado com Sucesso.', 'O estado: ' + $scope.estado.nome);
            $location.path('/estados');
        },function(data) {
           toastr.error(data.data, 'Não foi possível Atualizar.');
        });
    };

  });