var express = require('express');
var request = require('request');
var Firebase = require('firebase');
var app = express();

app.use(express.static('public'));
app.use(express.bodyParser());

app.post('/api/checkin', function(req, res) {
  request.get('https://api.github.com/users/' + req.body.user, {json: true}, function(e,r,b) {
    //if (e) { return res.send(500, e); }
    try {
      var userRef = new Firebase('https://zpn.firebaseIO.com/users/' + b.id); 
      userRef.set(b);
      var checkinRef = new Firebase('https://zpn.firebaseIO.com/checkins/' + b.id); 
      checkinRef.set(req.body.checkin);
      var json = {};    
      json[b.id] = req.body.checkin;
      console.log(json);
      request.post('http://ec2-54-242-228-29.compute-1.amazonaws.com:3000/checkin', 
        { json: json } );
      res.send(b);
    } catch (err) {
      console.log(err);
    } finally {
      res.send({});
    }
    
  });
});

app.listen(process.env.PORT || 3000);
