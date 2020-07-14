package cl.zpricing.avant.servicios;

import java.util.Date;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Forecast;
import cl.zpricing.avant.model.Funcion;

public interface ForecastDao {

		public Integer getAsistenciaProyectada(Funcion funcion);
		
		public Forecast obtenerForecast(Funcion funcion, Date fechaUltimaCargaAsistencias) throws FuncionException ;
		
		public void agregarAsistenciaFuncion(Funcion funcion) throws FuncionException ;
		
		public void actualizarForecast(Complejo complejo);
		
		public void actualizarSimilitudCategoria(Complejo complejo);
		
		public void actualizarSimilitudDiaHora(Complejo complejo);
		
		public void actualizarSimilitudFormato(Complejo complejo);
		
		public void actualizarSimilitudGrupo(Complejo complejo);
		
		public void actualizarSimilitudIdioma(Complejo complejo);
		
		public void actualizarSimilitudRanking(Complejo complejo);
		
		public void actualizarSimilitudRating(Complejo complejo);
		
		public void actualizarSimilitudSemana(Complejo complejo);
		
		public void actualizarEstadisticas();
}
