package cl.zpricing.avant.etl.model;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

public class Cluster {
	private static final long serialVersionUID = 1L;
	private ObjectId id;
	private int clusterId;
	private String name;
	private String description;
	private Map<String, Object> products;
	private List<ClusterProduct> premieres;
	private List<ClusterProduct> showings;
	
	public int getClusterId() {
		return clusterId;
	}
	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Map<String, Object> getProducts() {
		return products;
	}
	public void setProducts(Map<String, Object> products) {
		this.products = products;
	}
	public List<ClusterProduct> getPremieres() {
		return premieres;
	}
	public void setPremieres(List<ClusterProduct> premieres) {
		this.premieres = premieres;
	}
	public List<ClusterProduct> getShowings() {
		return showings;
	}
	public void setShowings(List<ClusterProduct> showings) {
		this.showings = showings;
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
}