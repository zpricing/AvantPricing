package cl.zpricing.avant.etl.model;

import java.util.List;

public class SessionPersonalizedSuggestions extends Session {
	private static final long serialVersionUID = 1L;
	private List<SecondSellingClient> suggestions;

	public List<SecondSellingClient> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(List<SecondSellingClient> suggestions) {
		this.suggestions = suggestions;
	}
}
