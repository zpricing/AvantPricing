package cl.zpricing.avant.model.loadmanager;


public class LayoutArea {
	private Integer tNum;
	private String descripcion;
	private Integer numSeatsTot;
	private Integer numSeatsHouse;
	private Integer numSeatsSpecial;
	private String catStrCode;
	private Integer complejoId;
	private Integer areaId;
	
	public Integer gettNum() {
		return tNum;
	}
	public void settNum(Integer tNum) {
		this.tNum = tNum;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Integer getNumSeatsTot() {
		return numSeatsTot;
	}
	public void setNumSeatsTot(Integer numSeatsTot) {
		this.numSeatsTot = numSeatsTot;
	}
	public Integer getNumSeatsHouse() {
		return numSeatsHouse;
	}
	public void setNumSeatsHouse(Integer numSeatsHouse) {
		this.numSeatsHouse = numSeatsHouse;
	}
	public Integer getNumSeatsSpecial() {
		return numSeatsSpecial;
	}
	public void setNumSeatsSpecial(Integer numSeatsSpecial) {
		this.numSeatsSpecial = numSeatsSpecial;
	}
	public String getCatStrCode() {
		return catStrCode;
	}
	public void setCatStrCode(String catStrCode) {
		this.catStrCode = catStrCode;
	}
	public Integer getComplejoId() {
		return complejoId;
	}
	public void setComplejoId(Integer complejoId) {
		this.complejoId = complejoId;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
}
