package cl.zpricing.avant.model;

public class GrupoPelicula extends DescripcionId {
	private String nombreOriginal;
	private boolean externalSourceDataLoaded;
	private String externalSourceId;

	public String getNombreOriginal() {
		return nombreOriginal;
	}
	public void setNombreOriginal(String nombreOriginal) {
		this.nombreOriginal = nombreOriginal;
	}
	public boolean isExternalSourceDataLoaded() {
		return externalSourceDataLoaded;
	}
	public void setExternalSourceDataLoaded(boolean externalSourceDataLoaded) {
		this.externalSourceDataLoaded = externalSourceDataLoaded;
	}
	public String getExternalSourceId() {
		return externalSourceId;
	}
	public void setExternalSourceId(String externalSourceId) {
		this.externalSourceId = externalSourceId;
	}
}
