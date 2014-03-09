package org.springframework.social.connect.support;

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

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * @author Umut Utkan
 */
public class RefreshingOAuth2ConnectionFactory<S> extends OAuth2ConnectionFactory<S> {

    public RefreshingOAuth2ConnectionFactory(String providerId, OAuth2ServiceProvider<S> soAuth2ServiceProvider, ApiAdapter<S> sApiAdapter) {
        super(providerId, soAuth2ServiceProvider, sApiAdapter);
    }


    @Override
    public Connection<S> createConnection(AccessGrant accessGrant) {
        return new RefreshingOAuth2Connection<S>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(), accessGrant.getExpireTime(), (OAuth2ServiceProvider<S>) getServiceProvider(), getApiAdapter());
    }

    @Override
    public Connection<S> createConnection(ConnectionData data) {
        return new RefreshingOAuth2Connection<S>(data, (OAuth2ServiceProvider<S>) getServiceProvider(), getApiAdapter());
    }

}
