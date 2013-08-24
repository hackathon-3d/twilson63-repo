angular.module('App',['firebase'])
  .config(function($routeProvider) {
    $routeProvider
      .when('/', {
        controller: 'MainCtrl',
        templateUrl: 'templates/main.html'
      });
  });
