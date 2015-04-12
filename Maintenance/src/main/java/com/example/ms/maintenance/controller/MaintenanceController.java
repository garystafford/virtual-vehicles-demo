package com.example.ms.maintenance.controller;

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
import com.example.ms.maintenance.Constants;
import com.example.ms.maintenance.domain.Maintenance;
import com.strategicgains.syntaxe.ValidationEngine;

public class MaintenanceController {

    private MongodbEntityRepository<Maintenance> maintenances;

    public MaintenanceController(MongodbEntityRepository<Maintenance> maintenanceRepository) {
        super();
        this.maintenances = maintenanceRepository;
    }

    public String create(Request request, Response response) {
        Maintenance maintenance = request.getBodyAs(Maintenance.class, 
                "Maintenance details not provided");
        ValidationEngine.validateAndThrow(maintenance);
        Maintenance saved = maintenances.create(maintenance);

        // Construct the response for create...
        response.setResponseCreated();

        // Include the Location header...
        String locationPattern = request.getNamedUrl(HttpMethod.GET, 
                Constants.Routes.MAINTENANCE_INSTANCE);
        response.addLocationHeader(LinkUtils.formatUrl(locationPattern, 
                Constants.Url.MAINTENANCE_ID, saved.getId()));

        // Return the newly-created ID...
        return saved.getId();
    }

    public Maintenance read(Request request, Response response) {
        String id = request.getHeader(Constants.Url.MAINTENANCE_ID, 
                "No Maintenance ID supplied");
        Maintenance maintenance = maintenances.read(id);

        // Add 'self' link
        String selfPattern = request.getNamedUrl(HttpMethod.GET, 
                Constants.Routes.MAINTENANCE_INSTANCE);
        String selfUrl = LinkUtils.formatUrl(selfPattern, 
                Constants.Url.MAINTENANCE_ID, maintenance.getId());
        maintenance.addLink(new Link(RelTypes.SELF, selfUrl));

        return maintenance;
    }

    public LinkableCollection<Maintenance> readAll(Request request, Response response) {
        QueryFilter filter = QueryFilters.parseFrom(request);
        QueryOrder order = QueryOrders.parseFrom(request);
        QueryRange range = QueryRanges.parseFrom(request, 20);
        List<Maintenance> results = maintenances.readAll(filter, range, order);
        long count = maintenances.count(filter);
        response.setCollectionResponse(range, results.size(), count);

        // Add 'self' links
        String maintenanceSelfPattern = request.getNamedUrl(HttpMethod.GET, 
                Constants.Routes.MAINTENANCE_INSTANCE);

        for (Maintenance result : results) {
            String selfUrl = LinkUtils.formatUrl(maintenanceSelfPattern, 
                    Constants.Url.MAINTENANCE_ID, result.getId());
            result.addLink(new Link(RelTypes.SELF, selfUrl));
        }

        String selfUrl = request.getNamedUrl(HttpMethod.GET, 
                Constants.Routes.MAINTENANCE_COLLECTION);
        LinkableCollection<Maintenance> wrapper = 
                new LinkableCollection<Maintenance>(results);
        wrapper.addLink(new Link(RelTypes.SELF, selfUrl));
        return wrapper;
    }

    public void update(Request request, Response response) {
        String id = request.getHeader(Constants.Url.MAINTENANCE_ID);
        Maintenance maintenance = request.getBodyAs(Maintenance.class, 
                "Maintenance details not provided");

        if (!id.equals(maintenance.getId())) {
            throw new BadRequestException("ID in URL and ID in Maintenance must match");
        }

        ValidationEngine.validateAndThrow(maintenance);
        maintenances.update(maintenance);
        response.setResponseNoContent();
    }

    public void delete(Request request, Response response) {
        String id = request.getHeader(Constants.Url.MAINTENANCE_ID, 
                "No Maintenance ID supplied");
        maintenances.delete(id);
        response.setResponseNoContent();
    }
}
