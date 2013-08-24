angular.module('App',['firebase'])
  .config(function($routeProvider) {
    $routeProvider
      .when('/', {
        controller: 'SignupCtrl',
        templateUrl: '/templates/signup.html'
      });
  });
