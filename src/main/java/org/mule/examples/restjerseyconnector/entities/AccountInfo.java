package org.mule.examples.restjerseyconnector.entities;

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
	private String referralLink;
	@JsonProperty("display_name")
	private String displayName;
	@JsonProperty("uid")
	private Integer uid;
	@JsonProperty("country")
	private String country;
	@JsonProperty("quota_info")
	private QuotaInfo quotaInfo;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("referral_link")
	public String getReferralLink() {
		return referralLink;
	}

	@JsonProperty("referral_link")
	public void setReferralLink(String referralLink) {
		this.referralLink = referralLink;
	}

	@JsonProperty("display_name")
	public String getDisplayName() {
		return displayName;
	}

	@JsonProperty("display_name")
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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
	public QuotaInfo getQuotaInfo() {
		return quotaInfo;
	}

	@JsonProperty("quota_info")
	public void setQuotaInfo(QuotaInfo quotaInfo) {
		this.quotaInfo = quotaInfo;
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