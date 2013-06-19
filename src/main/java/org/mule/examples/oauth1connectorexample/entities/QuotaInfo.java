package org.mule.examples.oauth1connectorexample.entities;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({ "shared", "quota", "normal" })
public class QuotaInfo {

	@JsonProperty("shared")
	private Double shared;
	@JsonProperty("quota")
	private Double quota;
	@JsonProperty("normal")
	private Double normal;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("shared")
	public Double getShared() {
		return shared;
	}

	@JsonProperty("shared")
	public void setShared(Double shared) {
		this.shared = shared;
	}

	@JsonProperty("quota")
	public Double getQuota() {
		return quota;
	}

	@JsonProperty("quota")
	public void setQuota(Double quota) {
		this.quota = quota;
	}

	@JsonProperty("normal")
	public Double getNormal() {
		return normal;
	}

	@JsonProperty("normal")
	public void setNormal(Double normal) {
		this.normal = normal;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperties(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}