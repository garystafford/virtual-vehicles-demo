package com.example.ms.valet.controller;

import com.example.ms.valet.Constants;
import com.example.ms.valet.domain.Valet;
import java.util.List;

import org.jboss.netty.handler.codec.http.HttpMethod;

import com.strategicgains.hyperexpress.RelTypes;
import com.strategicgains.hyperexpress.domain.Link;
import com.strategicgains.hyperexpress.domain.LinkableCollection;
import com.strategicgains.hyperexpress.util.LinkUtils;
import com.strategicgains.repoexpress.mongodb.MongodbEntityRepository;
import com.strategicgains.restexpress.Request;
import com.strategicgains.restexpress.Response;
import com.strategicgains.restexpress.common.query.QueryFilter;
import com.strategicgains.restexpress.common.query.QueryOrder;
import com.strategicgains.restexpress.common.query.QueryRange;
import com.strategicgains.restexpress.exception.BadRequestException;
import com.strategicgains.restexpress.query.QueryFilters;
import com.strategicgains.restexpress.query.QueryOrders;
import com.strategicgains.restexpress.query.QueryRanges;
import com.strategicgains.syntaxe.ValidationEngine;

public class ValetController {

    private MongodbEntityRepository<Valet> valets;

    public ValetController(MongodbEntityRepository<Valet> valetRepository) {
        super();
        this.valets = valetRepository;
    }

    public String create(Request request, Response response) {
        Valet valet = request.getBodyAs(Valet.class,
                "Valet details not provided");
        ValidationEngine.validateAndThrow(valet);
        Valet saved = valets.create(valet);

        // Construct the response for create...
        response.setResponseCreated();

        // Include the Location header...
        String locationPattern = request.getNamedUrl(HttpMethod.GET,
                Constants.Routes.VALET_INSTANCE);
        response.addLocationHeader(LinkUtils.formatUrl(locationPattern,
                Constants.Url.VALET_ID, saved.getId()));

        // Return the newly-created ID...
        return saved.getId();
    }

    public Valet read(Request request, Response response) {
        String id = request.getHeader(Constants.Url.VALET_ID,
                "No Valet ID supplied");
        Valet valet = valets.read(id);

        // Add 'self' link
        String selfPattern = request.getNamedUrl(HttpMethod.GET,
                Constants.Routes.VALET_INSTANCE);
        String selfUrl = LinkUtils.formatUrl(selfPattern,
                Constants.Url.VALET_ID, valet.getId());
        valet.addLink(new Link(RelTypes.SELF, selfUrl));

        return valet;
    }

    public LinkableCollection<Valet> readAll(Request request, Response response) {
        QueryFilter filter = QueryFilters.parseFrom(request);
        QueryOrder order = QueryOrders.parseFrom(request);
        QueryRange range = QueryRanges.parseFrom(request, 20);
        List<Valet> results = valets.readAll(filter, range, order);
        long count = valets.count(filter);
        response.setCollectionResponse(range, results.size(), count);

        // Add 'self' links
        String valetSelfPattern = request.getNamedUrl(HttpMethod.GET,
                Constants.Routes.VALET_INSTANCE);

        for (Valet result : results) {
            String selfUrl = LinkUtils.formatUrl(valetSelfPattern,
                    Constants.Url.VALET_ID, result.getId());
            result.addLink(new Link(RelTypes.SELF, selfUrl));
        }

        String selfUrl = request.getNamedUrl(HttpMethod.GET,
                Constants.Routes.VALET_COLLECTION);
        LinkableCollection<Valet> wrapper
                = new LinkableCollection<Valet>(results);
        wrapper.addLink(new Link(RelTypes.SELF, selfUrl));
        return wrapper;
    }

    public void update(Request request, Response response) {
        String id = request.getHeader(Constants.Url.VALET_ID);
        Valet valet = request.getBodyAs(Valet.class,
                "Valet details not provided");

        if (!id.equals(valet.getId())) {
            throw new BadRequestException("ID in URL and ID in Valet must match");
        }

        ValidationEngine.validateAndThrow(valet);
        valets.update(valet);
        response.setResponseNoContent();
    }

    public void delete(Request request, Response response) {
        String id = request.getHeader(Constants.Url.VALET_ID,
                "No Valet ID supplied");
        valets.delete(id);
        response.setResponseNoContent();
    }
}
