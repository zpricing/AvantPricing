package cl.zpricing.avant.webservices;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.FuncionArea;
import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.PeliculaDao;
import cl.zpricing.avant.webservices.model.CapacidadCupos;
import cl.zpricing.avant.webservices.model.CapacidadCuposPorPeliculaYDia;
import cl.zpricing.avant.webservices.model.RevenueManagementAvailabilityResponse;
import cl.zpricing.avant.webservices.model.RevenueManagementMovieAvailabilityResponse;
import cl.zpricing.commons.utils.Calendario;

@WebService(endpointInterface = "cl.zpricing.avant.webservices.ZhetaPricingServices", serviceName = "ZhetaPricingServices")
public class ZhetaPricingServicesImpl implements ZhetaPricingServices {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private FuncionDao funcionDao;
	private ComplejoDao complejoDao;
	private PeliculaDao peliculaDao;
	
	@Override
	public RevenueManagementAvailabilityResponse getRevenueManagementAvailability(String cinemaId, String sessionId) {
		try {
			RevenueManagementAvailabilityResponse response = new RevenueManagementAvailabilityResponse();
			Complejo complejo = complejoDao.obtenerComplejoPorIdExterno(cinemaId);
			Funcion funcion = funcionDao.obtenerFuncionPorIdExterno(complejo, sessionId);
			
			List<FuncionArea> funcionesArea = funcionDao.obtenerFuncionesAreaConTicketId(funcion);
			List<CapacidadCupos> listaCapacidades = new ArrayList<CapacidadCupos>();
			Iterator<FuncionArea> iterFuncionArea = funcionesArea.iterator();
			
			while (iterFuncionArea.hasNext()) {
				FuncionArea funcionArea = iterFuncionArea.next();
				CapacidadCupos capacidadCupos = new CapacidadCupos();
				capacidadCupos.setTicketTypeCode(funcionArea.getIdExterno());
				
				int disponible = funcionArea.getCapacidadDisponible() >= 0 ? funcionArea.getCapacidadDisponible() : 0;
				capacidadCupos.setPurchase(disponible);
				capacidadCupos.setBooking(disponible);
				
				listaCapacidades.add(capacidadCupos);
			}
			response.setDetalleCupos(listaCapacidades);
			return response;
		}
		catch (Exception e) {
			return new RevenueManagementAvailabilityResponse();
		}
	}
	
	@Override
	public RevenueManagementMovieAvailabilityResponse getRevenueManagementAvailabilityPerDay(String cinemaId, String movieId, String date) {
		RevenueManagementMovieAvailabilityResponse response = new RevenueManagementMovieAvailabilityResponse();
		log.debug("cinemaId : " + cinemaId);
		log.debug("movieId : " + movieId);
		log.debug("date : " + date);
		response.setMovieId(movieId);
		response.setCinemaId(cinemaId);
		
		try {
			log.debug("  Iniciando Try");
			Complejo complejo = complejoDao.obtenerComplejoPorIdExterno(cinemaId);
			log.debug("  Complejo: " + complejo.getId());
			Pelicula pelicula = peliculaDao.obtenerPorIdExternoDeComplejo(complejo, movieId);
			log.debug("  Pelicula: " + pelicula.getId());
			Calendario calendario = new Calendario(date);
			log.debug("  Fecha: " + calendario.getDate());
			
			List<Funcion> funciones = funcionDao.obtenerFunciones(complejo, pelicula, calendario.getDate());
			
			log.debug("  Cantidad de funciones : " + funciones.size());
			
			Iterator<Funcion> iterFunciones = funciones.iterator();
			while (iterFunciones.hasNext()) {
				Funcion funcion = iterFunciones.next();
				
				CapacidadCuposPorPeliculaYDia capacidadCuposPorFuncion = new CapacidadCuposPorPeliculaYDia();
				capacidadCuposPorFuncion.setSessionId(funcion.getIdExterno());
				
				List<FuncionArea> funcionesArea = funcionDao.obtenerFuncionesAreaConTicketId(funcion);
				Iterator<FuncionArea> iterFuncionArea = funcionesArea.iterator();
				
				while (iterFuncionArea.hasNext()) {
					FuncionArea funcionArea = iterFuncionArea.next();
					
					CapacidadCupos capacidadCupos = new CapacidadCupos();
					capacidadCupos.setTicketTypeCode(funcionArea.getIdExterno());
					int disponible = funcionArea.getCapacidadDisponible() >= 0 ? funcionArea.getCapacidadDisponible() : 0;
					capacidadCupos.setPurchase(disponible);
					capacidadCupos.setBooking(disponible);
					
					capacidadCuposPorFuncion.addCapacidadCupo(capacidadCupos);
				}
				response.addAvailabilityPerSession(capacidadCuposPorFuncion);
			}
			log.debug("  Cantidad de funciones XML: " + response.getSessionAvailability().size());
			response.setMessage("Success");
			
		} catch (Exception e) {
			response.setMessage(e.getMessage());
		} 
		return response;
	}
	
	@Override
	public String updateRevenueManagementAvailability(String cinemaId, String sessionId, String ticketId, String tipoTransaccion, int cantidad) {
		try {
			log.debug("cinemaId : " + cinemaId);
			log.debug("sessionId : " + sessionId);
			log.debug("ticketId : " + ticketId);
			log.debug("tipoTransaccion : " + tipoTransaccion);
			log.debug("cantidad : " + cantidad);
			Complejo complejo = complejoDao.obtenerComplejoPorIdExterno(cinemaId);
			Funcion funcion = funcionDao.obtenerFuncionPorIdExterno(complejo, sessionId);
			
			List<FuncionArea> funcionesArea = funcionDao.obtenerFuncionesAreaConTicketId(funcion);
			Iterator<FuncionArea> iterFuncionesArea = funcionesArea.iterator();
			
			while (iterFuncionesArea.hasNext()) {
				FuncionArea funcionArea = iterFuncionesArea.next();
				if (funcionArea.getIdExterno().equalsIgnoreCase(ticketId)) {
					funcionDao.actualizarFuncionAreaCuposOcupados(funcion, funcionArea.getArea(), cantidad);
					log.debug("RESULTADO OK");
					return "OK";
				}
			}
			log.debug("RESULTADO ERROR");
		}
		catch (Exception e) {
			log.error("Error en la actualizacion de cupos.");
			log.error("  Cinema Id : " + cinemaId);
			log.error("  Session Id : " + sessionId);
			log.error("  Ticket Id : " + ticketId);
			log.error("  Tipo Transaccion : " + tipoTransaccion);
			log.error("  Cantidad : " + cantidad);
		}
		return "ERROR";
	}

	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}

	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}

	public void setPeliculaDao(PeliculaDao peliculaDao) {
		this.peliculaDao = peliculaDao;
	}
}
