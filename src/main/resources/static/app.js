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
                        controller : 'menuCtrl'
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
            }).state("home.customers", {
                url: '/master/customers',
                views: {
                    'content@home': {
                        templateUrl: "view/masters/htmls/customers.html",
                    }
                }
            }).state("home.route", {
                url: '/master/route',
                views: {
                    'content@home': {
                        templateUrl: "view/masters/htmls/route.html",
                    }
                }
            }).state("home.categories", {
                url: '/master/categories',
                views: {
                    'content@home': {
                        templateUrl: "view/masters/htmls/categories.html",
                    }
                }
            }).state("home.products", {
                url: '/master/products',
                views: {
                    'content@home': {
                        templateUrl: "view/masters/htmls/products.html",
                    }
                }
            }).state("home.accounts", {
                url: '/master/accounts',
                views: {
                    'content@home': {
                        templateUrl: "view/masters/htmls/accounts.html",
                    }
                }
            }).state("home.companies", {
                url: '/master/companies',
                views: {
                    'content@home': {
                        templateUrl: "view/masters/htmls/companies.html",
                    }
                }
            });
});

app.run(['$rootScope', '$state', '$stateParams', function ($rootScope, $state, $stateParams) {
        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;
        $state.go("home.login");
    }]);