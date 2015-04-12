package com.example.ms.maintenance;

public class Constants {

    /**
     * These define the URL parameters used in the route definition strings
     * (e.g. '{userId}').
     */
    public class Url {

        public static final String MAINTENANCE_ID = "maintenanceId";
    }

    /**
     * These define the route names used in naming each route definitions. These
     * names are used to retrieve URL patterns within the controllers by name to
     * create links in responses.
     */
    public class Routes {

        public static final String MAINTENANCE_INSTANCE = "maintenance.instance.route";
        public static final String MAINTENANCE_COLLECTION = "maintenance.collection.route";
    }
}
