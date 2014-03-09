package org.springframework.social.salesforce.api;

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

/**
 * Defines operations for interacting with the Chatter API.
 *
 * @author Umut Utkan
 */
public interface ChatterOperations {

    /**
     * Retrieves current users's profile
     *
     * @return user profile
     */
    public SalesforceProfile getUserProfile();

    /**
     * Retrieves the given user's profile
     *
     * @param userId
     * @return user profile
     */
    public SalesforceProfile getUserProfile(String userId);

    /**
     * Retrieves current user's status
     *
     * @return status
     */
    public Status getStatus();

    /**
     * Retrieves the given user's status
     *
     * @param userId
     * @return status
     */
    public Status getStatus(String userId);

    /**
     * Updates current user's status with the given message
     *
     * @param message
     * @return status
     */
    public Status updateStatus(String message);

    /**
     * Updates the given user's status with the given message
     *
     * @param userId
     * @param message
     * @return status
     */
    public Status updateStatus(String userId, String message);

}
