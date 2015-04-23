'use strict';

var vehicles = require('../controllers/vehicles');

// Vehicles authorization helpers
//var hasAuthorization = function(req, res, next) {
//    if (!req.user.isAdmin) {
//        return res.status(401).send('User is not authorized');
//    }
//    next();
//};

module.exports = function (Vehicles, app, auth) {

    app.route('/virtual/vehicles')
        .get(vehicles.getVehicles);
    //.post(auth.requiresLogin, vehicles.create);
    //app.route('/vehicles/:vehicleId')
    //    .get(auth.isMongoId, vehicles.show)
    //    .put(auth.isMongoId, auth.requiresLogin, hasAuthorization, vehicles.update)
    //    .delete(auth.isMongoId, auth.requiresLogin, hasAuthorization, vehicles.destroy);

    // Finish with setting up the vehicleId param
    //app.param('vehicleId', vehicles.vehicle);
};
