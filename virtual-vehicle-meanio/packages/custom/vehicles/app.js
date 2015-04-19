'use strict';

/*
 * Defining the Package
 */
var Module = require('meanio').Module;

var Vehicles = new Module('vehicles');

/*
 * All MEAN packages require registration
 * Dependency injection is used to define required modules
 */
Vehicles.register(function(app, auth, database) {

  //We enable routing. By default the Package Object is passed to the routes
  Vehicles.routes(app, auth, database);

  //We are adding a link to the main menu for all authenticated users
  Vehicles.menus.add({
    title: 'vehicles example page',
    link: 'vehicles example page',
    roles: ['authenticated'],
    menu: 'main'
  });
  
  Vehicles.aggregateAsset('css', 'vehicles.css');

  /**
    //Uncomment to use. Requires meanio@0.3.7 or above
    // Save settings with callback
    // Use this for saving data from administration pages
    Vehicles.settings({
        'someSetting': 'some value'
    }, function(err, settings) {
        //you now have the settings object
    });

    // Another save settings example this time with no callback
    // This writes over the last settings.
    Vehicles.settings({
        'anotherSettings': 'some value'
    });

    // Get settings. Retrieves latest saved settigns
    Vehicles.settings(function(err, settings) {
        //you now have the settings object
    });
    */

  return Vehicles;
});
