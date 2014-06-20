var elasticsearch = require('elasticsearch');
var express = require('express');
var uuid = require('node-uuid');
var _ = require('underscore');
var app = express();

var elasticsearch = require('elasticsearch');
var client = new elasticsearch.Client({
	host: 'localhost:9200'
});

app.post('/score', function (req, res) {
	var name = req.query.name;
	var score = req.query.score;

	if (!name) {
		res.send(400, 'Name is required');
	} else if (!score) {
		res.send(400, 'Score is required');
	} else {
		var doc = {
	   		title: name.trim(),
	    	counter: score
	  	};
		client.create({
	  		index: 'hackaton',
	  		type: 'score',
	  		id: uuid.v4(),
	  		body: {
	   			title: name.trim(),
	    		counter: parseInt(score, 10)
	  		}
		}, function (error, response) {
	  		res.send(doc);
		});
	}
});

app.get('/', function (req, res) {
	client.search({
		index: 'hackaton',
		type: 'score',
		body: {
			sort: [{
				counter: 'desc'
			}]
		}
	}).then(function (resp) {
		var hits = resp.hits.hits;
		var scores = _.pluck(hits, '_source');
		res.send(scores);
	}, function (err) {
		res.send(500, err.message);
	});
});

// 10.22.0.69
app.listen(3000);