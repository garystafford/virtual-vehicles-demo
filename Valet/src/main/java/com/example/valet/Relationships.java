package com.example.valet;

import java.util.Map;

import org.restexpress.RestExpress;
import com.example.valet.objectid.Valet;
import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.RelTypes;

public abstract class Relationships {

    public static void define(RestExpress server) {
        Map<String, String> routes = server.getRouteUrlsByName();

        HyperExpress.relationships()
                .forCollectionOf(Valet.class)
                .rel(RelTypes.SELF, routes.get(Constants.Routes.VALET_COLLECTION))
                .withQuery("limit={limit}")
                .withQuery("offset={offset}")
                .rel(RelTypes.NEXT, routes.get(Constants.Routes.VALET_COLLECTION) + "?offset={nextOffset}")
                .withQuery("limit={limit}")
                .optional()
                .rel(RelTypes.PREV, routes.get(Constants.Routes.VALET_COLLECTION) + "?offset={prevOffset}")
                .withQuery("limit={limit}")
                .optional()
                .forClass(Valet.class)
                .rel(RelTypes.SELF, routes.get(Constants.Routes.SINGLE_VALET))
                .rel(RelTypes.UP, routes.get(Constants.Routes.VALET_COLLECTION));
    }
}
