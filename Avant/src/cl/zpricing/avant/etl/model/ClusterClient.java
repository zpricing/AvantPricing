package cl.zpricing.avant.etl.model;

import java.util.List;

public class ClusterClient {
	private String name;
	private String email;
	private List<Tag> tags;
	private List<ClientSegment> segments;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public List<ClientSegment> getSegments() {
		return segments;
	}
	public void setSegments(List<ClientSegment> segments) {
		this.segments = segments;
	}
}
