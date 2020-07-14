package cl.zpricing.avant.web.form;

import java.util.ArrayList;

import cl.zpricing.avant.model.Pelicula;

public class AsistenciaPorPeliculaForm {
	private int peliculaId;
	private ArrayList<Pelicula> peliculas;

	public int getPeliculaId() {
		return peliculaId;
	}

	public void setPeliculaId(int peliculaId) {
		this.peliculaId = peliculaId;
	}

	public void setPeliculas(ArrayList<Pelicula> peliculas) {
		this.peliculas = peliculas;
	}

	public ArrayList<Pelicula> getPeliculas() {
		return peliculas;
	}
}
