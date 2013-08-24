angular.module('App')
  .controller('SignupCtrl', function($scope, angularFire) {
    var ref = new Firebase('https://zpn.firebaseio.com');
    window.auth = new FirebaseSimpleLogin(ref, function(err, user) { 
     if (err) { console.log(err); }
     if (user) {
       angularFire('https://zpn.firebaseio.com/users/' + user.id, $scope, 'user', user);
       localStorage.setItem('user', user.id);
     }
   });
   // onSuccess Callback
   //   This method accepts a `Position` object, which contains
   //   the current GPS coordinates
   //
   var onSuccess = function(position) {
       alert('Latitude: '          + position.coords.latitude          + '\n' +
             'Longitude: '         + position.coords.longitude         + '\n' +
             'Altitude: '          + position.coords.altitude          + '\n' +
             'Accuracy: '          + position.coords.accuracy          + '\n' +
             'Altitude Accuracy: ' + position.coords.altitudeAccuracy  + '\n' +
             'Heading: '           + position.coords.heading           + '\n' +
             'Speed: '             + position.coords.speed             + '\n' +
             'Timestamp: '         + position.timestamp                + '\n');
   };

   // onError Callback receives a PositionError object
   //
   function onError(error) {
       alert('code: '    + error.code    + '\n' +
             'message: ' + error.message + '\n');
   }

   navigator.geolocation.getCurrentPosition(onSuccess, onError);
   
   $scope.login = function(source) {
     auth.login(source);
   };
  });