angular.module('market')
    .service('Pais',['$resource', 'BaseUrl',
    function($resource, BaseUrl){
      return $resource(BaseUrl + '/paises/:id', {}, {
         getAll: {method: 'GET', url: BaseUrl + '/paises', isArray: true},
         update: {method: 'PUT', url: BaseUrl + '/paises/:id', isArray: false},
         getFiltroPais: {method: 'GET', url: BaseUrl + '/paises/filtro/:filtro', isArray: true}
      });
    }])
    .service('Estado',['$resource', 'BaseUrl',
      function($resource, BaseUrl){
        return $resource(BaseUrl + '/estados/:id', {}, {
           getAll: {method: 'GET', url: BaseUrl + '/estados', isArray: true},
           update: {method: 'PUT', url: BaseUrl + '/estados/:id', isArray: false},
           getFiltroEstado: {method: 'GET', url: BaseUrl + '/estados/filtro/:filtro', isArray: true}
        });
    }])
    .service('Usuario',['$resource', 'BaseUrl',
      function($resource, BaseUrl){
        return $resource(BaseUrl + '/usuarios/:id', {}, {
           getAll: {method: 'GET', url: BaseUrl + '/usuarios', isArray: true},
           update: {method: 'PUT', url: BaseUrl + '/usuarios/:id', isArray: false},
           getFiltroUsuario: {method: 'GET', url: BaseUrl + '/usuarios/filtro/:filtro', isArray: true}
        });
    }])
    .service('Cidade',['$resource', 'BaseUrl',
      function($resource, BaseUrl){
        return $resource(BaseUrl + '/cidades/:id', {}, {
           getAll: {method: 'GET', url: BaseUrl + '/cidades', isArray: true},
           update: {method: 'PUT', url: BaseUrl + '/cidades/:id', isArray: false},
           getFiltroCidade: {method: 'GET', url: BaseUrl + '/cidades/filtro/:filtro', isArray: true}
        });
    }]);