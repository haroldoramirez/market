angular
    .module
        ('market',
            ['ngRoute',
             'ngResource',
             'ui.bootstrap',
             'toastr',
             'ngAnimate',
             'angular-loading-bar',
             'mgcrea.ngStrap.datepicker',
             'ui.utils.masks'
            ]
        )
    .config(function ($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: '/assets/app/views/home.html',
                controller: 'HomeController'
            })
            .when('/paises/novo', {
                templateUrl: '/assets/app/views/paises/create.html',
                controller: 'PaisCreateController'
            })
            .when('/paises/detalhe/:id', {
                templateUrl: '/assets/app/views/paises/detail.html',
                controller: 'PaisDetailController'
            })
            .when('/paises/editar/:id', {
                templateUrl: '/assets/app/views/paises/edit.html',
                controller: 'PaisEditController'
            })
            .when('/paises', {
                templateUrl: '/assets/app/views/paises/list.html',
                controller: 'PaisListController'
            })
            .when('/estados/novo', {
                templateUrl: '/assets/app/views/estados/create.html',
                controller: 'EstadoCreateController'
            })
            .when('/estados/detalhe/:id', {
                templateUrl: '/assets/app/views/estados/detail.html',
                controller: 'EstadoDetailController'
            })
            .when('/estados/editar/:id', {
                templateUrl: '/assets/app/views/estados/edit.html',
                controller: 'EstadoEditController'
            })
            .when('/estados', {
                templateUrl: '/assets/app/views/estados/list.html',
                controller: 'EstadoListController'
            })
            .when('/usuarios/novo', {
                templateUrl: '/assets/app/views/usuarios/create.html',
                controller: 'UsuarioCreateController'
            })
            .when('/usuarios/detalhe/:id', {
                templateUrl: '/assets/app/views/usuarios/detail.html',
                controller: 'UsuarioDetailController'
            })
            .when('/usuarios/editar/:id', {
                templateUrl: '/assets/app/views/usuarios/edit.html',
                controller: 'UsuarioEditController'
            })
            .when('/usuarios', {
                templateUrl: '/assets/app/views/usuarios/list.html',
                controller: 'UsuarioListController'
            })
            .when('/cidades/novo', {
                templateUrl: '/assets/app/views/cidades/create.html',
                controller: 'CidadeCreateController'
            })
            .when('/cidades/detalhe/:id', {
                templateUrl: '/assets/app/views/cidades/detail.html',
                controller: 'CidadeDetailController'
            })
            .when('/cidades/editar/:id', {
                templateUrl: '/assets/app/views/cidades/edit.html',
                controller: 'CidadeEditController'
            })
            .when('/cidades', {
                templateUrl: '/assets/app/views/cidades/list.html',
                controller: 'CidadeListController'
            })
            .when('/clientes/novo', {
                templateUrl: '/assets/app/views/clientes/create.html',
                controller: 'ClienteCreateController'
            })
            .when('/clientes/detalhe/:id', {
                templateUrl: '/assets/app/views/clientes/detail.html',
                controller: 'ClienteDetailController'
            })
            .when('/clientes/editar/:id', {
                templateUrl: '/assets/app/views/clientes/edit.html',
                controller: 'ClienteEditController'
            })
            .when('/clientes', {
                templateUrl: '/assets/app/views/clientes/list.html',
                controller: 'ClienteListController'
            });
   }).config(function(toastrConfig) {
     angular.extend(toastrConfig, {
        allowHtml: false,
        autoDismiss: false,
        closeButton: true,
        closeHtml: '<button>&times;</button>',
        containerId: 'toast-container',
        extendedTimeOut: 5000,
        iconClasses: {
          error: 'toast-error',
          info: 'toast-info',
          success: 'toast-success',
          warning: 'toast-warning'
        },
        maxOpened: 0,
        messageClass: 'toast-message',
        newestOnTop: true,
        onHidden: null,
        onShown: null,
        positionClass: 'toast-bottom-right',
        preventDuplicates: false,
        preventOpenDuplicates: false,
        progressBar: false,
        tapToDismiss: true,
        target: 'body',
        templates: {
          toast: 'directives/toast/toast.html',
          progressbar: 'directives/progressbar/progressbar.html'
        },
        timeOut: 5000,
        titleClass: 'toast-title',
        toastClass: 'toast'
     });
});