package cl.zpricing.avant.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Grupo;
import cl.zpricing.avant.model.Sala;
import cl.zpricing.avant.model.TipoSala;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.GrupoDao;
import cl.zpricing.avant.servicios.SalaDao;
import cl.zpricing.avant.servicios.TipoSalaDao;
import cl.zpricing.avant.web.form.RoomAeForm;


/**
 * <b>Descripci�n de la Clase</b>
 * Controller de la administracion de salas
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 17-12-2008 Oliver: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */ 
public class RoomAeController extends SimpleFormController  {
	@SuppressWarnings("unused")
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private SalaDao salaDao;
	private ComplejoDao complejoDao;
	private GrupoDao grupoDao;
	private TipoSalaDao tipoSalaDao;
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(java.lang.Object)
	 */
	public ModelAndView onSubmit(Object command)throws ServletException {
		
		String id = ((RoomAeForm) command).getId();
		String numero = ((RoomAeForm) command).getNumero();
		String capacidad = ((RoomAeForm) command).getCapacidad();
		String complejoId = ((RoomAeForm) command).getComplejoAlCualPertenece();
		String tipoSalaId = ((RoomAeForm) command).getTipoSala();
		String grupoId = ((RoomAeForm) command).getGrupo();
		String idExterno = ((RoomAeForm) command).getIdExterno();
		String orden = ((RoomAeForm) command).getOrden();
		String tipoIng = ((RoomAeForm) command).getTipoIng();
		
		Sala sala = new Sala();
		Complejo complejo = new Complejo();
		TipoSala tipoSala = new TipoSala();
		Grupo grupo = new Grupo();
		
		complejo.setId(Integer.parseInt(complejoId));
		tipoSala.setId(Integer.parseInt(tipoSalaId));
		grupo.setId(Integer.parseInt(grupoId));
		
		sala.setNumero(numero);
		sala.setCapacidad(Integer.parseInt(capacidad));
		sala.setComplejoAlCualPertenece(complejo);
		sala.setTipoSala(tipoSala);
		sala.setGrupo(grupo);
		sala.setIdExterno(idExterno);
		sala.setOrden(Integer.parseInt(orden));
		
		/*****
		El truco de crear y modificar en un mismo formulario
		es el crear un parametro oculto en el formulario
		para que este sepa si lo tiene que crear o modificar
		uno ya existente, en este caso tipoIng
		con el se decide que hacer con el dao
		******/
		if(tipoIng.compareTo("add") == 0)
			salaDao.agregarSala(sala);
		else{
			sala.setId(Integer.parseInt(id));
			salaDao.actualizarSala(sala);
		}
		return new ModelAndView(new RedirectView(getSuccessView()));
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		
		String id_room = request.getParameter("id_room");
		
		RoomAeForm modifyform = new RoomAeForm();
		if(id_room == null){
			modifyform.setId("");
			modifyform.setNumero("");
			modifyform.setCapacidad("");
			modifyform.setComplejoAlCualPertenece("");
			modifyform.setTipoSala("");
			modifyform.setGrupo("");
			modifyform.setIdExterno("");
			modifyform.setOrden("");
			modifyform.setTipoIng("add");
		}else{
			Sala sala = new Sala();
		
			sala = salaDao.obtenerSala(Integer.parseInt(id_room));
		
			modifyform.setId(id_room);
			modifyform.setNumero(sala.getNumero());
			modifyform.setCapacidad(Integer.toString(sala.getCapacidad()));
			if(sala.getComplejoAlCualPertenece()!=null)
				modifyform.setComplejoAlCualPertenece(Integer.toString(sala.getComplejoAlCualPertenece().getId()));
			else
				modifyform.setComplejoAlCualPertenece("");
			if(sala.getTipoSala()!=null)
				modifyform.setTipoSala(Integer.toString(sala.getTipoSala().getId()));
			else
				modifyform.setTipoSala("");
			if(sala.getGrupo()!=null)
				modifyform.setGrupo(Integer.toString(sala.getGrupo().getId()));
			else
				modifyform.setGrupo("");
			modifyform.setIdExterno(sala.getIdExterno());
			modifyform.setOrden(Integer.toString(sala.getOrden()));
			modifyform.setTipoIng("modify");
		}
		return modifyform;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> refdata = new HashMap<String, Object>();
		
		List<Sala> salas = new ArrayList<Sala>();
		List<Complejo> complejos = new ArrayList<Complejo>();
		List<TipoSala> tipoSalas = new ArrayList<TipoSala>();
		List<Grupo> grupos = new ArrayList<Grupo>();
		
		salas = salaDao.obtenerTodas();
		complejos = complejoDao.complejosTodos();
		tipoSalas = tipoSalaDao.tipoSalaTodos();
		grupos = grupoDao.obtenerTodos();
			
		refdata.put("complejos", complejos);
		refdata.put("salas", salas);
		refdata.put("tipoSalas", tipoSalas);
		refdata.put("grupos", grupos);
		refdata.put("error", request.getParameter("error"));
		
		boolean anadir = true;
		if(request.getParameter("id_room") != null)
			anadir = false;

		refdata.put("anadir", anadir);
		return refdata;
	}
	
	/**
	 * @return the complejoDao
	 */
	public ComplejoDao getComplejoDao() {
		return complejoDao;
	}

	/**
	 * @param complejoDao the complejoDao to set
	 */
	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}

	/**
	 * @return the userDao
	 */
	public SalaDao getSalaDao() {
		return salaDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setSalaDao(SalaDao salaDao) {
		this.salaDao = salaDao;
	}
	
	public GrupoDao getGrupoDao() {
		return grupoDao;
	}

	public void setGrupoDao(GrupoDao grupoDao) {
		this.grupoDao = grupoDao;
	}

	/**
	 * @param tipoSalaDao
	 */
	public void setTipoSalaDao(TipoSalaDao tipoSalaDao) {
		this.tipoSalaDao = tipoSalaDao;
	}

	/**
	 * @return tipoSalaDao
	 */
	public TipoSalaDao getTipoSalaDao() {
		return tipoSalaDao;
	}
}