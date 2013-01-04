package org.springframework.social.salesforce.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.salesforce.api.Salesforce;
import org.springframework.social.salesforce.api.impl.SalesforceTemplate;

/**
 * Salesforce ServiceProvider implementation.
 *
 * @author Umut Utkan
 */
public class SalesforceServiceProvider extends AbstractOAuth2ServiceProvider<Salesforce> {

    public SalesforceServiceProvider(String clientId, String clientSecret) {
        this(clientId, clientSecret,
                "https://login.salesforce.com/services/oauth2/authorize",
                "https://login.salesforce.com/services/oauth2/token");
    }

    public SalesforceServiceProvider(String clientId, String clientSecret, String authorizeUrl, String tokenUrl) {
        super(new SalesforceOAuth2Template(clientId, clientSecret, authorizeUrl, tokenUrl));
    }


    public Salesforce getApi(String accessToken) {
        SalesforceTemplate template = new SalesforceTemplate(accessToken);

        // gets the returned instance url and sets to Salesforce template as base url.
        String instanceUrl = ((SalesforceOAuth2Template) getOAuthOperations()).getInstanceUrl();
        if (instanceUrl != null) {
            template.setInstanceUrl(instanceUrl);
        }

        return template;
    }

    /**
     * Build a SalesforceTemplate based on the Username-Password OAuth authentication flow.
     * Described here: http://www.salesforce.com/us/developer/docs/api_rest/Content/intro_understanding_username_password_oauth_flow.htm
     */
    public Salesforce getApi(String username, String password, String securityToken) {
        AccessGrant accessGrant = getOAuthOperations().exchangeCredentialsForAccess(username, password + securityToken, null);
        SalesforceTemplate template = new SalesforceTemplate(accessGrant.getAccessToken());
        // gets the returned instance url and sets to Salesforce template as base url.
        String instanceUrl = ((SalesforceOAuth2Template) getOAuthOperations()).getInstanceUrl();
        if (instanceUrl != null) {
            template.setInstanceUrl(instanceUrl);
        }
        return template;
    }
}
