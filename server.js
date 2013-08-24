var express = require('express');
var app = express();

app.use(express.static('public'));

app.listen(process.env.PORT || 3000);

var Firebase = require('firebase');
var dataRef = new Firebase('https://zpn.firebaseIO.com/checkins');
dataRef.on('child_changed', function(snapshot) {
  console.log(snapshot.va());
});

var dataRef = new Firebase('https://zpn.firebaseIO.com/checkins');
dataRef.on('child_added', function(snapshot) {
  console.log(snapshot.name());
});