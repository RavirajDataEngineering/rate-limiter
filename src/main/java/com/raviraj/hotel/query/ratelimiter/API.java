package com.raviraj.hotel.query.ratelimiter;

public class API {

	
	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public APILimit getLimit() {
		return limit;
	}

	public void setLimit(APILimit limit) {
		this.limit = limit;
	}

	private String apiId;
	
	private APILimit limit;

	public API(String apiId, APILimit limit) {
		super();
		this.apiId = apiId;
		this.limit = limit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apiId == null) ? 0 : apiId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		API other = (API) obj;
		if (apiId == null) {
			if (other.apiId != null)
				return false;
		} else if (!apiId.equals(other.apiId))
			return false;
		return true;
	}
	
	
}
