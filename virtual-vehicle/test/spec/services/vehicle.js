'use strict';

describe('Service: vehicle', function () {

  // load the service's module
  beforeEach(module('virtualVehicleApp'));

  // instantiate service
  var vehicle;
  beforeEach(inject(function (_vehicle_) {
    vehicle = _vehicle_;
  }));

  it('should do something', function () {
    expect(!!vehicle).toBe(true);
  });

});
