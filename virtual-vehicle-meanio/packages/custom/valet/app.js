'use strict';

/*
 * Defining the Package
 */
var Module = require('meanio').Module;

var Valet = new Module('valet');

/*
 * All MEAN packages require registration
 * Dependency injection is used to define required modules
 */
Valet.register(function(app, auth, database) {

  //We enable routing. By default the Package Object is passed to the routes
  Valet.routes(app, auth, database);

  //We are adding a link to the main menu for all authenticated users
  Valet.menus.add({
    title: 'Valet Transactions',
    link: 'all valet',
    roles: ['authenticated'],
    menu: 'main'
  });
  
  Valet.aggregateAsset('css', 'valet.css');

  /**
    //Uncomment to use. Requires meanio@0.3.7 or above
    // Save settings with callback
    // Use this for saving data from administration pages
    Valet.settings({
        'someSetting': 'some value'
    }, function(err, settings) {
        //you now have the settings object
    });

    // Another save settings example this time with no callback
    // This writes over the last settings.
    Valet.settings({
        'anotherSettings': 'some value'
    });

    // Get settings. Retrieves latest saved settigns
    Valet.settings(function(err, settings) {
        //you now have the settings object
    });
    */

  return Valet;
});
