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
    {id: '21242', xtop: '145px', xleft: '150px'},
    {id: '21242', xtop: '195px', xleft: '220px'},
    {id: '21242', xtop: '295px', xleft: '150px'},
    {id: '21242', xtop: '215px', xleft: '70px'},
    {id: '21242', xtop: '203px', xleft: '150px'}
   ];
   
   $scope.who = function() {
     // set user
     alert('foo');
     $scope.page = "rank";
   };
   $scope.query = 'All';
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