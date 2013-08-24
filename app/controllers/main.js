angular.module('App')
  .controller('MainCtrl', function($scope, angularFire) {
    $scope.page = 'signup';
    var ref = new Firebase('https://zpn.firebaseio.com');
    window.auth = new FirebaseSimpleLogin(ref, function(err, user) { 
     if (err) { console.log(err); }
     if (user) {
       angularFire('https://zpn.firebaseio.com/users/' + user.id, $scope, 'user', user);
       localStorage.setItem('user', user.id);
       $scope.page = 'checkin';
     }
   });

   $scope.login = function(source) {
     auth.login(source);
   };

  });