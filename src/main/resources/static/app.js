var app = angular.module('app', ['ui.router']);

app.config(function ($stateProvider, $urlRouterProvider) {
$urlRouterProvider.otherwise("/home");
    $stateProvider
            .state("home", {
                url: "/home",
                abstract: true,
                views: {
                    'header': {
                        templateUrl: 'view/header/header.html',
                    },
                    'menu': {
                        templateUrl: 'view/header/menu.html',
                    },
                    'footer': {
                        templateUrl: 'view/header/footer.html',
                    }
                }
            })
            .state("home.login", {
                url: '/login',
                views: {
                    'content@home': {
                        templateUrl: "view/login/login.html",
                    }
                }
            });
});

app.run(['$rootScope', '$state', '$stateParams', function ($rootScope, $state, $stateParams) {
        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;
        $state.go("home.login");
    }]);