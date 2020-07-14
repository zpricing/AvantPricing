package cl.zpricing.avant.etl.model;

import org.bson.types.ObjectId;

public class ClientSegment {
	private ObjectId id;
	private String origin = "dynamic";
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public void setId(String id) {
		this.id = new ObjectId(id);
	}
}
