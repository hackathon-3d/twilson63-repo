var express = require('express');
var request = require('request');
var Firebase = require('firebase');
var app = express();

app.use(express.static('public'));
app.use(express.bodyParser());

app.post('/api/checkin', function(req, res) {
  request.get('https://api.github.com/users/' + req.body.user, {json: true}, function(e,r,b) {
    var userRef = new Firebase('https://zpn.firebaseIO.com/users/' + b.id); 
    userRef.set(b);
    var checkinRef = new Firebase('https://zpn.firebaseIO.com/checkins/' + b.id); 
    checkinRef.set(req.body.checkin);
    res.send(b);
  });
});

app.listen(process.env.PORT || 3000);

// var Firebase = require('firebase');
// var dataRef = new Firebase('https://zpn.firebaseIO.com/checkins');
// dataRef.on('child_changed', function(snapshot) {
//   console.log(snapshot.va());
// });
// 
// var dataRef = new Firebase('https://zpn.firebaseIO.com/checkins');
// dataRef.on('child_added', function(snapshot) {
//   console.log(snapshot.name());
// });