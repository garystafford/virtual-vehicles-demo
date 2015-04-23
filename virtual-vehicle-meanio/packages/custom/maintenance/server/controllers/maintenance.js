'use strict';

var request = require('request');
require('request').debug = true;

var host = 'http://localhost:8583/virtual/maintenance/records';

exports.getMaintenanceRecords = function (req, res) {
    request(host, function (error, response, body) {
        if (!error && response.statusCode === 200) {
            res.type('application/javascript');
            res.jsonp({
                "records": JSON.parse(body)._embedded.records
            });
        } else {
            console.error(error);
        }
    });
};