package org.springframework.social.salesforce.client;

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

import java.util.Map;

import org.springframework.social.salesforce.api.Salesforce;
import org.springframework.social.salesforce.api.impl.SalesforceTemplate;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Default implementation of SalesforceFactory.
 *
 * @author Umut Utkan
 */
public class BaseSalesforceFactory implements SalesforceFactory {

    private final static String DEFAULT_AUTH_URL = "https://login.salesforce.com/services/oauth2/token";

    private String clientId;

    private String clientSecret;

    private String authorizeUrl = DEFAULT_AUTH_URL;

    private RestTemplate restTemplate;


    public BaseSalesforceFactory(String clientId, String clientSecret) {
        this(clientId, clientSecret, createRestTemplate());
    }

    public BaseSalesforceFactory(String clientId, String clientSecret, RestTemplate restTemplate) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.restTemplate = restTemplate;
    }


    public void setAuthorizeUrl(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }
    
    public String getAuthorizeUrl() {
      return (this.authorizeUrl == null) ? DEFAULT_AUTH_URL : this.authorizeUrl;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Salesforce create(String username, String password, String securityToken) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "password");
        map.add("client_id", this.clientId);
        map.add("client_secret", this.clientSecret);
        map.add("username", username);
        map.add("password", password + (securityToken == null ? "" : securityToken));

        Map<String, String> token = restTemplate.postForObject(this.authorizeUrl, map, Map.class);
        SalesforceTemplate template = new SalesforceTemplate(token.get("access_token"));
        String instanceUrl = token.get("instance_url");
        if (instanceUrl != null) {
            template.setInstanceUrl(instanceUrl);
        }
        return template;
    }

    private static RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(ClientHttpRequestFactorySelector.getRequestFactory());
        restTemplate.setErrorHandler(new ErrorHandler());
        return restTemplate;
    }

}
