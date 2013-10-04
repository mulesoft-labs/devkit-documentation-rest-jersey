package org.mule.examples.oauth1connectorexample.client;


import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.oauth.client.OAuthClientFilter;
import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthSecrets;
import org.mule.examples.oauth1connectorexample.Oauth1ExampleConnector;
import org.mule.examples.oauth1connectorexample.entities.AccountInfo;
import org.mule.examples.oauth1connectorexample.exception.Oauth1ConnectorExampleException;
import org.mule.examples.oauth1connectorexample.exception.Oauth1ConnectorExampleTokenExpiredException;

public class DropboxClient {

	private Client client;
    private WebResource apiResource;
    private Oauth1ExampleConnector connector;
	
	public DropboxClient(Oauth1ExampleConnector connector) {
        setConnector(connector);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        this.client = Client.create(clientConfig);

        this.apiResource = this.client.resource(getConnector().getApiUrl() + "/" + getConnector().getApiVersion());
	}
	
	
	/**
	 * Returns the Account Information of the user
	 * 
	 * @return The AccountInfo
	 * @throws Oauth1ConnectorExampleException If the response is an error or the response cannot be parsed as an AccountInfo
	 * @throws Oauth1ConnectorExampleTokenExpiredException If the current token used for the call to the service is no longer valid
	 */
	public AccountInfo getAccountInfo()
			throws Oauth1ConnectorExampleException, Oauth1ConnectorExampleTokenExpiredException {

		WebResource webResource = getApiResource().path("account").path("info");
		return execute(webResource, "GET", AccountInfo.class);
	}
	
	/**
	 * Adds the required Authorization Header in order to access the protected resources of the DropBox API
	 *
     * @param webResource The WebResource in which the header will be added
     */
	private WebResource addSignHeader(WebResource webResource) {

		OAuthParameters params = new OAuthParameters();
        params.signatureMethod("PLAINTEXT");
        params.consumerKey(getConnector().getConsumerKey());
        params.setToken(getConnector().getAccessToken());

        OAuthSecrets secrets = new OAuthSecrets();
        secrets.consumerSecret(getConnector().getConsumerSecret());
        secrets.setTokenSecret(getConnector().getAccessTokenSecret());

        OAuthClientFilter filter = new OAuthClientFilter(client.getProviders(), params, secrets);

        webResource.addFilter(filter);
        return webResource;
	}

    /**
     * Executes the Dropbox request
     *
     */
    private <T> T execute(WebResource webResource, String method, Class<T> returnClass) throws Oauth1ConnectorExampleTokenExpiredException,
            Oauth1ConnectorExampleException {
        ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_JSON).method(method, ClientResponse.class);

        if(clientResponse.getStatus() == 200) {
            return clientResponse.getEntity(returnClass);
        } else if (clientResponse.getStatus() == 401) {
            throw new Oauth1ConnectorExampleTokenExpiredException("The access token has expired; " +
                    clientResponse.getEntity(String.class));
        } else {
            throw new Oauth1ConnectorExampleException(
                    String.format("ERROR - statusCode: %d - message: %s",
                            clientResponse.getStatus(), clientResponse.getEntity(String.class)));
        }
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public WebResource getApiResource() {
        return addSignHeader(apiResource);
    }

    public void setApiResource(WebResource apiResource) {
        this.apiResource = apiResource;
    }

    public Oauth1ExampleConnector getConnector() {
        return connector;
    }

    public void setConnector(Oauth1ExampleConnector connector) {
        this.connector = connector;
    }
}
