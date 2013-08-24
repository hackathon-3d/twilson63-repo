angular.module('App')
  .controller('MainCtrl', function($scope, angularFire, $http) {
    navigator.geolocation.getCurrentPosition(function(position) {
      $scope.$apply(function() {
        $scope.checkin = {
           lat: position.coords.latitude,
           lng: position.coords.longitude,
           stmp: new Date()
         };
      });
    });

    angularFire('https://zpn.firebaseio.com/users', $scope, 'users', {});
    $scope.page = 'signup';

   $scope.login = function(source) {
     $http.post('/api/checkin', { user: $scope.user, checkin: $scope.checkin })
       .success(function(user) {
         $scope.page = 'main';
         // angularFire('https://zpn.firebaseio.com/checkins/' + user.id, $scope, 'checkin', {});
         // angularFire('https://zpn.firebaseio.com/users/' + user.id, $scope, 'user', {});
       });
   };

   $scope.rank = function() {
     $scope.page = 'rank';
   };

   $scope.grid = function() {
     $scope.page = 'main';
   };

   $scope.cohort = [
    {id: '21292', xtop: '145px', xleft: '150px'},
    {id: '21292', xtop: '195px', xleft: '220px'},
    {id: '21292', xtop: '295px', xleft: '150px'},
    {id: '21292', xtop: '215px', xleft: '70px'},
    {id: '21292', xtop: '203px', xleft: '150px'}
   ];

   $scope.who = function() {
     // set user
     alert('foo');
     $scope.page = "rank";
   };
   $scope.query = 'All';

   $scope.getProfile = function(id) {
     $scope.profile = $scope.users[id];
     $scope.page = 'profile';
   };
  });