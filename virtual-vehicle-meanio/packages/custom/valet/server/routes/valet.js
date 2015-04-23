'use strict';

var valet = require('../controllers/valet');

module.exports = function(Valet, app, auth) {

    app.route('/virtual/valet/transactions')
        .get(valet.getValetTransactions);
};
