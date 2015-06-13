package com.example.valet.objectid;

import com.example.authenticate.AuthenticateJwt;
import java.util.List;

import io.netty.handler.codec.http.HttpMethod;

import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;
import org.restexpress.query.QueryFilters;
import org.restexpress.query.QueryOrders;
import org.restexpress.query.QueryRanges;
import com.example.valet.Constants;

import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.builder.TokenBinder;
import com.strategicgains.hyperexpress.builder.TokenResolver;
import com.strategicgains.hyperexpress.builder.UrlBuilder;
import com.strategicgains.repoexpress.mongodb.Identifiers;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * This is the 'controller' layer, where HTTP details are converted to domain
 * concepts and passed to the service layer. Then service layer response
 * information is enhanced with HTTP details, if applicable, for the response.
 * <p/>
 * This controller demonstrates how to process an entity that is identified by a
 * MongoDB ObjectId.
 */
public class TransactionController {

    private static final UrlBuilder LOCATION_BUILDER = new UrlBuilder();
    private final TransactionService service;
    private final String authUrlAndAuthPort;

    /**
     *
     * @param valetService
     * @param authUrlAndAuthPort
     */
    public TransactionController(TransactionService valetService, String authUrlAndAuthPort) {
        super();
        this.service = valetService;
        this.authUrlAndAuthPort = authUrlAndAuthPort;
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public Transaction create(Request request, Response response) {
        if (AuthenticateJwt.authenticateJwt(request, authUrlAndAuthPort) != true) {
            response.setResponseStatus(HttpResponseStatus.UNAUTHORIZED);
            return null;
        }
        Transaction entity = request.getBodyAs(Transaction.class, "Resource details not provided");
        Transaction saved = service.create(entity);

        // Construct the response for create...
        response.setResponseCreated();

        // Bind the resource with link URL tokens, etc. here...
        TokenResolver resolver = HyperExpress.bind(Constants.Url.TRANSACTION_ID, Identifiers.MONGOID.format(saved.getId()));

        // Include the Location header...
        String locationPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.SINGLE_TRANSACTION);
        response.addLocationHeader(LOCATION_BUILDER.build(locationPattern, resolver));

        // Return the newly-created resource...
        return saved;
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public Transaction read(Request request, Response response) {
        if (AuthenticateJwt.authenticateJwt(request, authUrlAndAuthPort) != true) {
            response.setResponseStatus(HttpResponseStatus.UNAUTHORIZED);
            return null;
        }
        String id = request.getHeader(Constants.Url.TRANSACTION_ID, "No resource ID supplied");
        Transaction entity = service.read(Identifiers.MONGOID.parse(id));

        // enrich the resource with links, etc. here...
        HyperExpress.bind(Constants.Url.TRANSACTION_ID, Identifiers.MONGOID.format(entity.getId()));

        return entity;
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public List<Transaction> readAll(Request request, Response response) {
        if (AuthenticateJwt.authenticateJwt(request, authUrlAndAuthPort) != true) {
            response.setResponseStatus(HttpResponseStatus.UNAUTHORIZED);
            return null;
        }
        QueryFilter filter = QueryFilters.parseFrom(request);
        QueryOrder order = QueryOrders.parseFrom(request);
        QueryRange range = QueryRanges.parseFrom(request, 20);
        boolean countOnly = Boolean.parseBoolean(
                request.getQueryStringMap().getOrDefault("countOnly", "false"));
        List<Transaction> entities = service.readAll(filter, range, order);
        long count = service.count(filter);
        response.setCollectionResponse(range, entities.size(), count);

        // Bind the resources in the collection with link URL tokens, etc. here...
        HyperExpress.tokenBinder(new TokenBinder<Transaction>() {
            @Override
            public void bind(Transaction entity, TokenResolver resolver) {
                resolver.bind(Constants.Url.TRANSACTION_ID, Identifiers.MONGOID.format(entity.getId()));
            }
        });

        if (countOnly) { // only return count in Content-Range header
            entities.clear();
            return entities;
        }
        return entities;
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public Transaction update(Request request, Response response) {
        if (AuthenticateJwt.authenticateJwt(request, authUrlAndAuthPort) != true) {
            response.setResponseStatus(HttpResponseStatus.UNAUTHORIZED);
            return null;
        }
        String id = request.getHeader(Constants.Url.TRANSACTION_ID, "No resource ID supplied");
        Transaction entity = request.getBodyAs(Transaction.class, "Resource details not provided");
        entity.setId(Identifiers.MONGOID.parse(id));
        service.update(entity);

        // new per http://stackoverflow.com/a/827045/580268
        entity = service.read(Identifiers.MONGOID.parse(id));
        response.setResponseStatus(HttpResponseStatus.CREATED);

        // enrich the resource with links, etc. here...
        HyperExpress.bind(Constants.Url.TRANSACTION_ID, Identifiers.MONGOID.format(entity.getId()));

        return entity;

        // original response returned nothing
        //response.setResponseNoContent();
    }

    /**
     *
     * @param request
     * @param response
     */
    public void delete(Request request, Response response) {
        if (AuthenticateJwt.authenticateJwt(request, authUrlAndAuthPort) != true) {
            response.setResponseStatus(HttpResponseStatus.UNAUTHORIZED);
        }
        String id = request.getHeader(Constants.Url.TRANSACTION_ID, "No resource ID supplied");
        service.delete(Identifiers.MONGOID.parse(id));
        response.setResponseNoContent();
    }
}
