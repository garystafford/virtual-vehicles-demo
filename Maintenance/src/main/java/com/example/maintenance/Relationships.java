package com.example.maintenance;

import java.util.Map;

import org.restexpress.RestExpress;
import com.example.maintenance.objectid.Maintenance;
import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.RelTypes;

public abstract class Relationships {

    public static void define(RestExpress server) {
        Map<String, String> routes = server.getRouteUrlsByName();

        HyperExpress.relationships()
                .forCollectionOf(Maintenance.class)
                .rel(RelTypes.SELF, routes.get(Constants.Routes.MAINTENANCE_COLLECTION))
                .withQuery("limit={limit}")
                .withQuery("offset={offset}")
                .rel(RelTypes.NEXT, routes.get(Constants.Routes.MAINTENANCE_COLLECTION) + "?offset={nextOffset}")
                .withQuery("limit={limit}")
                .optional()
                .rel(RelTypes.PREV, routes.get(Constants.Routes.MAINTENANCE_COLLECTION) + "?offset={prevOffset}")
                .withQuery("limit={limit}")
                .optional()
                .forClass(Maintenance.class)
                .rel(RelTypes.SELF, routes.get(Constants.Routes.SINGLE_MAINTENANCE))
                .rel(RelTypes.UP, routes.get(Constants.Routes.MAINTENANCE_COLLECTION));
    }
}
