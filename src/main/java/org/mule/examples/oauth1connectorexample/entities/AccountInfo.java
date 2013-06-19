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
@JsonPropertyOrder({ "referral_link", "display_name", "uid", "country", "quota_info" })
public class AccountInfo {

	@JsonProperty("referral_link")
	private String referral_link;
	@JsonProperty("display_name")
	private String display_name;
	@JsonProperty("uid")
	private Integer uid;
	@JsonProperty("country")
	private String country;
	@JsonProperty("quota_info")
	private QuotaInfo quota_info;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("referral_link")
	public String getReferral_link() {
		return referral_link;
	}

	@JsonProperty("referral_link")
	public void setReferral_link(String referral_link) {
		this.referral_link = referral_link;
	}

	@JsonProperty("display_name")
	public String getDisplay_name() {
		return display_name;
	}

	@JsonProperty("display_name")
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	@JsonProperty("uid")
	public Integer getUid() {
		return uid;
	}

	@JsonProperty("uid")
	public void setUid(Integer uid) {
		this.uid = uid;
	}

	@JsonProperty("country")
	public String getCountry() {
		return country;
	}

	@JsonProperty("country")
	public void setCountry(String country) {
		this.country = country;
	}

	@JsonProperty("quota_info")
	public QuotaInfo getQuota_info() {
		return quota_info;
	}

	@JsonProperty("quota_info")
	public void setQuota_info(QuotaInfo quota_info) {
		this.quota_info = quota_info;
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