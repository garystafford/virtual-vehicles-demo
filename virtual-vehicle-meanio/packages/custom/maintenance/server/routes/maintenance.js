'use strict';

var maintenance = require('../controllers/maintenance');

module.exports = function(Maintenance, app, auth) {

    app.route('/virtual/maintenance/records')
        .get(maintenance.getMaintenanceRecords);
};
