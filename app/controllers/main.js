angular.module('App')
  .controller('MainCtrl', function($scope, angularFire) {
    $scope.page = 'signup';
    var ref = new Firebase('https://zpn.firebaseio.com');
    window.auth = new FirebaseSimpleLogin(ref, function(err, user) { 
     if (err) { console.log(err); }
     if (user) {
       angularFire('https://zpn.firebaseio.com/checkins/' + user.id, $scope, 'checkin', {});
       angularFire('https://zpn.firebaseio.com/users/' + user.id, $scope, 'user', user);
       navigator.geolocation.getCurrentPosition(function(position) {
         $scope.$apply(function() {
           $scope.checkin = {
              lat: position.coords.latitude,
              lng: position.coords.longitude,
              stmp: new Date()
            };
           $scope.page = 'main';
         });
       }, function(err) {
          alert(err);
       });
       
     }
   });

   $scope.login = function(source) {
     auth.login(source);
   };

   $scope.rank = function() {
     $scope.page = 'rank';
   };

   $scope.grid = function() {
     $scope.page = 'main';
   };
   
   $scope.cohort = [
    {xtop: '150px', xleft: '185px'},
    {xtop: '245px', xleft: '185px'},
    {xtop: '340px', xleft: '185px'},
    {xtop: '245px', xleft: '85px'},
    {xtop: '245px', xleft: '285px'}
    
   ];
   // $scope.checkin = function() {
   //   navigator.geolocation.getCurrentPosition(function(position) {
   //     var userId = localStorage.getItem('user');       localStorage.setItem('user', user.id);
   //     angularFire('https://zpn.firebaseio.com/checkins/' + userId, $scope, 'checkin', position);
   //     $scope.page = 'main';
   //   }, function(err) {
   //     alert(err);
   //   });
   // };

  });