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

import java.io.Serializable;

/**
 * @author Umut Utkan
 */
public class ApiVersion implements Serializable {

    private String label;

    private String version;

    private String url;


    public ApiVersion(String version, String label, String url) {
        this.version = version;
        this.label = label;
        this.url = url;
    }


    public String getLabel() {
        return label;
    }

    public String getVersion() {
        return version;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "ApiVersion{" +
                "label='" + label + '\'' +
                ", version='" + version + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
