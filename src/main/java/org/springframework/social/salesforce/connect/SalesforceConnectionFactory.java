package org.springframework.social.salesforce.connect;

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

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.salesforce.api.Salesforce;

/**
 * @author Umut Utkan
 */
public class SalesforceConnectionFactory extends OAuth2ConnectionFactory<Salesforce> {

    public SalesforceConnectionFactory(String clientId, String clientSecret) {
        super("salesforce", new SalesforceServiceProvider(clientId, clientSecret),
                new SalesforceAdapter());
    }

    public SalesforceConnectionFactory(String clientId, String clientSecret, String authorizeUrl, String tokenUrl) {
        super("salesforce", new SalesforceServiceProvider(clientId, clientSecret,
                authorizeUrl, tokenUrl), new SalesforceAdapter());
    }

}
