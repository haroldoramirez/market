angular.module('market')
  .controller('CidadeCreateController', function ($scope, $location, Cidade, Estado, toastr) {

    $scope.init = function() {
        Estado.getAll(function(data) {
            $scope.estados = data;
        });
    };

    $scope.save = function() {
        Cidade.save($scope.cidade, function(data) {
            toastr.success('foi salva com Sucesso.', 'A cidade: ' + $scope.cidade.nome);
            $location.path('/cidades');
        }, function(data) {
            toastr.error(data.data, 'Não foi possível Salvar.');
        });
    };

    $scope.cancel = function() {
        $location.path('/cidades');
    };

  }).controller('CidadeListController', function ($scope, Cidade, $routeParams) {

    $scope.init = function() {
        $scope.nomeFiltro = '';

        Cidade.getAll(function(data) {
           $scope.cidades = data;
           $scope.quantidade = $scope.cidades.length;
        });
    };

    //* filtra por nome da cidade *//
    $scope.busca = function() {

       if ($scope.nomeFiltro) {
            Cidade.getFiltroCidade({filtro:$scope.nomeFiltro}, $scope.cidade, function(data) {
                $scope.cidades = data;
            });
       } else {
            Cidade.getAll(function(data) {
                $scope.cidades = data;
            });
       };
    };

  }).controller('CidadeDetailController', function ($scope, $modal, $routeParams, $location, Cidade, Estado, toastr) {

    $scope.init = function() {
        $scope.cidade = Cidade.get({id:$routeParams.id}, function(data) {
        $scope.estados = Estado.getAll();
        },function(data) {
            toastr.error(data.data);
        });
    };

    $scope.update = function() {
        Cidade.update({id:$routeParams.id}, $scope.cidade, function(data) {
            toastr.info('foi atualizada com Sucesso.', 'A cidade: ' + $scope.cidade.nome);
            $location.path('/cidades');
        },function(data) {
           toastr.error(data.data, 'Não foi possível Atualizar.');
        });
    };

    $scope.delete = function() {
        $scope.cidade = Cidade.get({id:$routeParams.id}, function(data) {
            $scope.cidadeExcluida = $scope.cidade.nome;
        });
        Cidade.delete({id:$routeParams.id}, function() {;
            toastr.warning('foi removida com Sucesso.', 'A cidade: ' + $scope.cidade.nome);
            $modalInstance.close();
            $location.path('/cidades');
        }, function(data) {
            $modalInstance.close();
            toastr.error(data.data, 'Não foi possível Remover.');
        });
    };

    $scope.cancel = function() {
       $location.path('/cidades');
    };

    $scope.open = function (size) {

        $scope.cidade = Cidade.get({id:$routeParams.id}, function(data) {
            $scope.cidadeExcluida = $scope.cidade.nome;
        });

        $modalInstance = $modal.open({
              templateUrl: 'modalConfirmacao.html',
              controller: 'CidadeDetailController',
              size: size,
        });
    };

    $scope.cancelModal = function () {
        $modalInstance.dismiss('cancelModal');
    };

  });