'use strict';

/*
 * Defining the Package
 */
var Module = require('meanio').Module;

var Maintenance = new Module('maintenance');

/*
 * All MEAN packages require registration
 * Dependency injection is used to define required modules
 */
Maintenance.register(function(app, auth, database) {

  //We enable routing. By default the Package Object is passed to the routes
  Maintenance.routes(app, auth, database);

  //We are adding a link to the main menu for all authenticated users
  Maintenance.menus.add({
    title: 'Maintenance Records',
    link: 'all maintenance records',
    roles: ['authenticated'],
    menu: 'main'
  });
  
  Maintenance.aggregateAsset('css', 'maintenance.css');

  /**
    //Uncomment to use. Requires meanio@0.3.7 or above
    // Save settings with callback
    // Use this for saving data from administration pages
    Maintenance.settings({
        'someSetting': 'some value'
    }, function(err, settings) {
        //you now have the settings object
    });

    // Another save settings example this time with no callback
    // This writes over the last settings.
    Maintenance.settings({
        'anotherSettings': 'some value'
    });

    // Get settings. Retrieves latest saved settigns
    Maintenance.settings(function(err, settings) {
        //you now have the settings object
    });
    */

  return Maintenance;
});
