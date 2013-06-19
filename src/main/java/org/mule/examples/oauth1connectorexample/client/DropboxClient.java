package org.mule.examples.oauth1connectorexample.client;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.mule.examples.oauth1connectorexample.entities.AccountInfo;
import org.mule.examples.oauth1connectorexample.exception.Oauth1ConnectorExampleException;
import org.mule.examples.oauth1connectorexample.exception.Oauth1ConnectorExampleTokenExpiredException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

public class DropboxClient {
	/// The client used to call the REST Service
	private Client jerseyClient;
	/// The Library used to map from JSON-Pojo & Pojo-JSON
	private ObjectMapper jacksonMapper;
	/// The Base url of the API
	private String apiUrl;
	/// The version of the API
	private String apiVersion;
	
	public DropboxClient() {
		this(null, null);
	}
	
	public DropboxClient(String apiUrl, String apiVersion) {
		this.apiUrl = StringUtils.isEmpty(apiUrl) ? "https://api.dropbox.com" : apiUrl;;
		this.apiVersion = StringUtils.isEmpty(apiVersion) ? "1" : apiVersion;
		
		jerseyClient = new Client();
		jacksonMapper = new ObjectMapper();
	}
	
	
	/**
	 * Returns the Account Information of the user
	 * 
	 * @return The AccountInfo
	 * @throws Oauth1ConnectorExampleException If the response is an error or the response cannot be parsed as an AccountInfo
	 * @throws Oauth1ConnectorExampleTokenExpiredException If the current token used for the call to the service is no longer valid
	 */
	public AccountInfo getAccountInfo(String apiKey, String apiSecret, String accessToken, String accessTokenSecret) 
			throws Oauth1ConnectorExampleException, Oauth1ConnectorExampleTokenExpiredException {
		
		URI uri = UriBuilder.fromPath(apiUrl).path("/{apiVersion}/account/info").build(apiVersion);
		WebResource wr = jerseyClient.resource(uri);
		WebResource.Builder b = addSignHeader(wr, apiKey, apiSecret, accessToken, accessTokenSecret);
		
		AccountInfo result = null;
		
		try {
			String res = b.type(MediaType.APPLICATION_JSON_TYPE).get(String.class);
			result = jacksonMapper.readValue(res, AccountInfo.class);
			
		} catch (UniformInterfaceException e) {
			ClientResponse cl = e.getResponse();
			int statusCode = cl.getStatus();
			String message = "";
			try {
				message = cl.getEntity(String.class);
			} catch (Throwable ex) {}
			
			// If the result code is 401 it means that the actual token passed in the call is no longer valid
			if (statusCode == 401) {
				throw new Oauth1ConnectorExampleTokenExpiredException("The access token has expired", e);
			} else {
				throw new Oauth1ConnectorExampleException(
						String.format("ERROR - statusCode: %d - message: %s", statusCode, message), 
						e);
			}
		} catch (JsonParseException e) {
			throw new Oauth1ConnectorExampleException("ERROR - Error Parsing the JSON", e);
		} catch (JsonMappingException e) {
			throw new Oauth1ConnectorExampleException("ERROR - Error Parsing the JSON", e);
		} catch (IOException e) {
			throw new Oauth1ConnectorExampleException(e);
		}
		
		return result;
	}
	
	/**
	 * Adds the required Authorization Header in order to access the protected resources of the DropBox API
	 * 
	 * @param wr The WebResource in which the header will be added
	 * @param apiKey The apiKey of the DropBox App
	 * @param apiSecret The apiSecret of the DropBox App
	 * @param accessToken The access token returned from the last call to the OAuth Dance
	 * @param accessTokenSecret The access token secret returned from the last call to the OAuth Dance
	 */
	private WebResource.Builder addSignHeader(WebResource wr, String apiKey, String apiSecret, String accessToken, String accessTokenSecret) {

		/* The authorization header that DropBox is expecting
		 * 
		 * Authorization: OAuth oauth_version="1.0", oauth_signature_method="PLAINTEXT", oauth_consumer_key="<app-key>", oauth_token="<access-token>, oauth_signature="<app-secret>&<access-token-secret>"
		 * 
		 */
		String authHeader = String.format("OAuth oauth_version=\"1.0\", oauth_signature_method=\"PLAINTEXT\", oauth_consumer_key=\"%s\", oauth_token=\"%s, oauth_signature=%s&%s\"", apiKey, accessToken, apiSecret, accessTokenSecret);
		return wr.header("Authorization", authHeader);
	}
}
