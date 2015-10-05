angular.module('market')
  .controller('PaisCreateController', function ($scope, $location, Pais, toastr) {

      $scope.save = function() {
          Pais.save($scope.pais, function(data) {
              toastr.success($scope.pais.nome, 'Salvo com sucesso.');
              $location.path('/paises');
          }, function(data) {
              toastr.error(data.data, 'Não foi possível Salvar.');
          });
      };

      $scope.cancel = function() {
          $location.path('/paises');
      };

  }).controller('PaisListController', function ($scope, $routeParams, Pais) {

      $scope.init = function() {
          $scope.nomeFiltro = '';

          Pais.getAll(function(data) {
             $scope.paises = data;
             $scope.quantidade = $scope.paises.length;
          }, function(data) {
               toastr.error(data.data, 'Não autorizado.');
          });
      };

      $scope.busca = function() {

         if ($scope.nomeFiltro) {
              Pais.getFiltroPais({filtro:$scope.nomeFiltro}, $scope.pais, function(data) {
                  $scope.paises = data;
              }, function(data) {
                    toastr.error(data.data,'Não autorizado.');
                 });
         } else {
              Pais.getAll(function(data) {
                  $scope.paises = data;
              });
         };
      };


  }).controller('PaisDetailController', function ($scope, $routeParams, $location, Pais, toastr, $modal) {

      $scope.init = function() {
          $scope.pais = Pais.get({id:$routeParams.id}, function(data) {
          },function(data) {
              toastr.error(data.data);
          });
      };

      $scope.update = function() {
          Pais.update({id:$routeParams.id}, $scope.pais, function(data) {
              toastr.info($scope.pais.nome, 'Atualizado com Sucesso.');
              $location.path('/paises');
          },function(data) {
             toastr.error(data.data, 'Não foi possível Atualizar.');
          });
      };

      $scope.delete = function() {

          $scope.pais = Pais.get({id:$routeParams.id}, function(data) {
              $scope.paisExcluido = $scope.pais.nome;
          });

          Pais.delete({id:$routeParams.id}, function() {
              toastr.warning($scope.paisExcluido, 'Removido com Sucesso.');
              $modalInstance.close();
              $location.path('/paises');
          }, function(data) {
              $modalInstance.close();
              toastr.error(data.data, 'Não foi possível Remover.');
          });
      };

      $scope.cancel = function() {
         $location.path('/paises');
      };

      $scope.open = function (size) {

          $modalInstance = $modal.open({
                templateUrl: 'modalConfirmacao.html',
                controller: 'PaisDetailController',
                size: size,
          });
      };

      $scope.cancelModal = function () {
          $modalInstance.dismiss('cancelModal');
      };

  });