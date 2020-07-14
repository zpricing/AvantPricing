package cl.zpricing.avant.web.form;

import cl.zpricing.avant.model.GrupoPelicula;

public class GrupoPeliculaForm {
	private Integer formPage;
	private Integer formScheduled;
	private Integer formUnclassified;
	private Integer formDataLoaded;
	private GrupoPelicula[] grupos;

	public GrupoPelicula[] getGrupos() {
		return grupos;
	}
	public void setGrupos(GrupoPelicula[] grupos) {
		this.grupos = grupos;
	}
	public Integer getFormPage() {
		return formPage;
	}
	public void setFormPage(Integer formPage) {
		this.formPage = formPage;
	}
	public Integer getFormScheduled() {
		return formScheduled;
	}
	public void setFormScheduled(Integer formScheduled) {
		this.formScheduled = formScheduled;
	}
	public Integer getFormUnclassified() {
		return formUnclassified;
	}
	public void setFormUnclassified(Integer formUnclassified) {
		this.formUnclassified = formUnclassified;
	}
	public Integer getFormDataLoaded() {
		return formDataLoaded;
	}
	public void setFormDataLoaded(Integer formDataLoaded) {
		this.formDataLoaded = formDataLoaded;
	}
}
