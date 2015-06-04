package com.example.valet;

import java.util.Map;

import org.restexpress.RestExpress;
import com.example.valet.objectid.Transaction;
import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.RelTypes;

/**
 *
 * @author gstafford
 */
public abstract class Relationships {

    /**
     *
     * @param server
     */
    public static void define(RestExpress server) {
        Map<String, String> routes = server.getRouteUrlsByName();

        HyperExpress.relationships()
                .forCollectionOf(Transaction.class)
                .rel(RelTypes.SELF, routes.get(Constants.Routes.TRANSACTION_COLLECTION))
                .withQuery("limit={limit}")
                .withQuery("offset={offset}")
                .rel(RelTypes.NEXT, routes.get(Constants.Routes.TRANSACTION_COLLECTION) + "?offset={nextOffset}")
                .withQuery("limit={limit}")
                .optional()
                .rel(RelTypes.PREV, routes.get(Constants.Routes.TRANSACTION_COLLECTION) + "?offset={prevOffset}")
                .withQuery("limit={limit}")
                .optional()
                .forClass(Transaction.class)
                .rel(RelTypes.SELF, routes.get(Constants.Routes.SINGLE_TRANSACTION))
                .rel(RelTypes.UP, routes.get(Constants.Routes.TRANSACTION_COLLECTION));
    }
}
