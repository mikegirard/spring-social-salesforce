package org.springframework.social.salesforce.api.impl;

/*
 * #%L
 * Spring Social Salesforce
 * %%
 * Copyright (C) 2014 the original author or authors.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.junit.Before;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.test.client.MockRestServiceServer;

/**
 * @author Umut Utkan
 */
abstract public class AbstractSalesforceTest {

    protected SalesforceTemplate salesforce;

    protected SalesforceTemplate unauthorizedSalesforce;

    protected MockRestServiceServer mockServer;

    protected HttpHeaders responseHeaders;


    @Before
    public void setup() {
        salesforce = new SalesforceTemplate("ACCESS_TOKEN");
        salesforce.setInstanceUrl("https://na7.salesforce.com");
        mockServer = MockRestServiceServer.createServer(salesforce.getRestTemplate());
        responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        unauthorizedSalesforce = new SalesforceTemplate();
        // create a mock server just to avoid hitting real twitter if something gets past the authorization check
        MockRestServiceServer.createServer(unauthorizedSalesforce.getRestTemplate());
    }

    protected Resource loadResource(String name) {
        return new ClassPathResource("/" + name, getClass());
    }

}
