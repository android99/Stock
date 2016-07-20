package com.tom.stock.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "stockApi",
        version = "v1",
        resource = "stock",
        namespace = @ApiNamespace(
                ownerDomain = "backend.stock.tom.com",
                ownerName = "backend.stock.tom.com",
                packagePath = ""
        )
)
public class StockEndpoint {

    private static final Logger logger = Logger.getLogger(StockEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Stock.class);
    }

    /**
     * Returns the {@link Stock} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Stock} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "stock/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Stock get(@Named("id") String id) throws NotFoundException {
        logger.info("Getting Stock with ID: " + id);
        Stock stock = ofy().load().type(Stock.class).id(id).now();
        if (stock == null) {
            throw new NotFoundException("Could not find Stock with ID: " + id);
        }
        return stock;
    }

    /**
     * Inserts a new {@code Stock}.
     */
    @ApiMethod(
            name = "insert",
            path = "stock",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Stock insert(Stock stock) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that stock.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(stock).now();
        logger.info("Created Stock with ID: " + stock.getId());

        return ofy().load().entity(stock).now();
    }

    /**
     * Updates an existing {@code Stock}.
     *
     * @param id    the ID of the entity to be updated
     * @param stock the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Stock}
     */
    @ApiMethod(
            name = "update",
            path = "stock/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Stock update(@Named("id") String id, Stock stock) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(stock).now();
        logger.info("Updated Stock: " + stock);
        return ofy().load().entity(stock).now();
    }

    /**
     * Deletes the specified {@code Stock}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Stock}
     */
    @ApiMethod(
            name = "remove",
            path = "stock/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") String id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Stock.class).id(id).now();
        logger.info("Deleted Stock with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "stock",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Stock> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Stock> query = ofy().load().type(Stock.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Stock> queryIterator = query.iterator();
        List<Stock> stockList = new ArrayList<Stock>(limit);
        while (queryIterator.hasNext()) {
            stockList.add(queryIterator.next());
        }
        return CollectionResponse.<Stock>builder().setItems(stockList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String id) throws NotFoundException {
        try {
            ofy().load().type(Stock.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Stock with ID: " + id);
        }
    }
}