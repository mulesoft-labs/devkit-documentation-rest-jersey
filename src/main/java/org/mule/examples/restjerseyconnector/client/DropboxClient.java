package org.mule.examples.restjerseyconnector.client;


import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.oauth.client.OAuthClientFilter;
import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthSecrets;
import org.mule.examples.restjerseyconnector.RestJerseyConnector;
import org.mule.examples.restjerseyconnector.entities.AccountInfo;
import org.mule.examples.restjerseyconnector.exception.RestJerseyConnectorException;
import org.mule.examples.restjerseyconnector.exception.RestJerseyConnectorTokenExpiredException;

public class DropboxClient {

	private Client client;
    private WebResource apiResource;
    private RestJerseyConnector connector;
	
	public DropboxClient(RestJerseyConnector connector) {
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
	 * @throws org.mule.examples.restjerseyconnector.exception.RestJerseyConnectorException If the response is an error or the response cannot be parsed as an AccountInfo
	 * @throws org.mule.examples.restjerseyconnector.exception.RestJerseyConnectorTokenExpiredException If the current token used for the call to the service is no longer valid
	 */
	public AccountInfo getAccountInfo()
			throws RestJerseyConnectorException, RestJerseyConnectorTokenExpiredException {

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
    private <T> T execute(WebResource webResource, String method, Class<T> returnClass) throws RestJerseyConnectorTokenExpiredException,
            RestJerseyConnectorException {
        ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_JSON).method(method, ClientResponse.class);

        if(clientResponse.getStatus() == 200) {
            return clientResponse.getEntity(returnClass);
        } else if (clientResponse.getStatus() == 401) {
            throw new RestJerseyConnectorTokenExpiredException("The access token has expired; " +
                    clientResponse.getEntity(String.class));
        } else {
            throw new RestJerseyConnectorException(
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

    public RestJerseyConnector getConnector() {
        return connector;
    }

    public void setConnector(RestJerseyConnector connector) {
        this.connector = connector;
    }
}
