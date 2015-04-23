'use strict';

var request = require('request');
require('request').debug = true;

var host = 'http://localhost:8585/virtual/valet/transactions';

exports.getValetTransactions = function (req, res) {
    request(host, function (error, response, body) {
        if (!error && response.statusCode === 200) {
            res.type('application/javascript');
            res.jsonp({
                "transactions": JSON.parse(body)._embedded.transactions
            });
        } else {
            console.error(error);
        }
    });
};