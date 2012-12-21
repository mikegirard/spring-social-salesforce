package org.springframework.social.salesforce.api.impl;

import java.net.URI;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.salesforce.api.QueryOperations;
import org.springframework.social.salesforce.api.QueryResult;
import org.springframework.social.salesforce.api.Salesforce;
import org.springframework.social.support.URIBuilder;
import org.springframework.web.client.RestTemplate;

/**
 * Default implementation of QueryOperations.
 *
 * @author Umut Utkan
 */
public class QueryTemplate extends AbstractSalesForceOperations<Salesforce> implements QueryOperations {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryTemplate.class);

    private RestTemplate restTemplate;

    public QueryTemplate(Salesforce api, RestTemplate restTemplate) {
        super(api);
        this.restTemplate = restTemplate;
    }

    @Override
    public QueryResult query(String query) {
        requireAuthorization();
        URI uri = URIBuilder.fromUri(api.getBaseUrl() + "/" + API_VERSION + "/query").queryParam("q", query).build();
        return restTemplate.getForObject(uri, QueryResult.class);
    }

    @Override
    public QueryResult nextPage(String pathOrToken) {
        requireAuthorization();
        if (pathOrToken.contains("/")) {
            return restTemplate.getForObject(api.getInstanceUrl() + pathOrToken, QueryResult.class);
        } else {
            return restTemplate.getForObject(api.getBaseUrl() + "/" + API_VERSION + "/query/{token}", QueryResult.class, pathOrToken);
        }
    }

    /**
     * Pages through the result set and aggregates all sObjects into the single result set. <p/>
     * USUAL WARNINGS ABOUT RESULT SET SIZE APPLY
     * @see org.springframework.social.salesforce.api.QueryOperations#queryAll(java.lang.String)
     */
    @Override
    public QueryResult queryAll(String query) {

        QueryResult page = query(query);
        // set up the final result set. (mostly to get the done flag set properly)
        QueryResult results = new QueryResult(page.getTotalSize(), true);
        results.setRecords(page.getRecords());

        while (StringUtils.isNotBlank(page.getNextRecordsUrl())) {
            page = nextPage(page.getNextRecordsUrl());
            results.getRecords().addAll(page.getRecords());
        }
        return results;
    }

    /**
     * Returns an Iterable<QueryResult> to go through all the pages.
     * Execute query and fetch next page (if available) on Iterable.next().
     * @see QueryOperations#queryAllPages(String)
     */
    @Override
    public Iterable<QueryResult> queryAllPages(final String query) {
        return new Iterable<QueryResult>() {
            @Override
            public Iterator<QueryResult> iterator() {
                return new QueryResultIterator(query);
            }
        };
    }

    class QueryResultIterator implements Iterator<QueryResult> {

        public final String query;
        private String nextRecordsUrl;
        private boolean hasNext = true;

        public QueryResultIterator(String query) {
            this.query = query;
        }

        @Override
        public boolean hasNext() {
            return hasNext;
        }

        @Override
        public QueryResult next() {
            if (!hasNext)
                throw new NoSuchElementException("No more queryResult for query [" + query + "]");

            QueryResult queryResult;
            if (this.nextRecordsUrl == null) {
                LOGGER.debug("Execute query {}", query);
                queryResult = query(query);
            } else {
                LOGGER.debug("Fetch next page at {} for query [{}]", nextRecordsUrl, query);
                queryResult = nextPage(this.nextRecordsUrl);
            }

            nextRecordsUrl = queryResult.getNextRecordsUrl();
            this.hasNext = this.nextRecordsUrl != null;

            LOGGER.debug("Received {} items on {} for query [{}]", new Object[]{queryResult.getRecords().size(), queryResult.getTotalSize(), query});
            return queryResult;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
