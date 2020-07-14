package cl.zpricing.avant.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import cl.zpricing.avant.exceptions.FuncionAreaNotFoundException;
import cl.zpricing.commons.utils.DateUtils;

/**
 * Clase en la cual se maneja los datos de una funcion en particular
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 09-02-2009 Oliver Cordero: version inicial.</li>
 *   <li>1.1 02-06-2009 Mario Lavandero: agregado metodo para obtener el total de asistencias de la funcion.</li>
 *   <li>1.1 28-04-2010 Nicolás Dujovne W.: se agrega atributo fechaContable, mascaraId y la lista de objetos FuncionArea.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */ 
public class Funcion extends DescripcionId{

	public Sala sala;
	/**
	 * Fecha y hora de comienzo de la función.
	 */
	public Date fecha;
	/**
	 * Fecha a la cual corresponde contablemente la función, por ejemplo, funciones de trasnoche después de las 00:00
	 * contablemente pertenecen al día anterior.
	 */
	public Date fechaContable;
	public Pelicula peliculaAsociada;
	public TipoFuncion tipoFuncion;
	public ArrayList<Asistencia> asistenciasDeFuncion;
	public String idExterno;
	private ArrayList<Marker> markersFecha;
	boolean cargada;
	/**
	 * Identificador de la ultima mascara cargada en el sistema del cliente para la funcion.
	 */
	private Mascara mascara;
	
	private String priceCardDescription;
	
	private boolean protegido;
	
	private boolean esNumerada;
	
	private boolean cuposCargados;
	
	private Integer exhibicion;
	private Integer semana;
	private Dia dia;
	private Hora hora;
	
	private Integer asistenciaProyectada;
	private Double porcentajeOcupacionProyectado;
	private boolean analizada;
	private boolean bloqueada;
	private List<FuncionArea> funcionesArea;
	
	/**
	 * Retorna cual fue la asistencia total para la funcion.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 02-06-2009 Mario Lavandero : Version Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @return
	 * @since 2.0
	 */
	public int getAsistenciaTotal()
	{
		Iterator<Asistencia> iter = this.getAsistenciasDeFuncion().iterator();
		int suma = 0;
		while(iter.hasNext())
		{
			suma+=iter.next().getAsistencia();
		}
		return suma;
	}
	
	public FuncionArea getFuncionArea(Area area) throws FuncionAreaNotFoundException {
		for (FuncionArea funcionArea : this.funcionesArea) {
			if (area.getId() == funcionArea.getArea().getId()) {
				return funcionArea;
			}
		}
		throw new FuncionAreaNotFoundException("Funcion [" + this.getId() + "] no tiene asignada FuncionArea para area [" + area.getId() + "]");
	}
	
	public List<FuncionArea> getFuncionesArea() {
		return funcionesArea;
	}
	public void setFuncionesArea(List<FuncionArea> funcionesArea) {
		this.funcionesArea = funcionesArea;
	}
	public boolean getCargada() {
		return cargada;
	}
	public void setCargada(boolean cargada) {
		this.cargada = cargada;
	}
	public ArrayList<Marker> getMarkersFecha() {
		return markersFecha;
	}
	public void setMarkersFecha(ArrayList<Marker> markersFecha) {
		this.markersFecha = markersFecha;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Date getFechaContable() {
		return fechaContable;
	}
	public void setFechaContable(Date fechaContable) {
		this.fechaContable = fechaContable;
	}
	public Sala getSala() {
		return sala;
	}
	public void setSala(Sala sala) {
		this.sala = sala;
	}
	public Pelicula getPeliculaAsociada() {
		return peliculaAsociada;
	}
	public void setPeliculaAsociada(Pelicula peliculaAsociada) {
		this.peliculaAsociada = peliculaAsociada;
	}
	public TipoFuncion getTipoFuncion() {
		return tipoFuncion;
	}
	public void setTipoFuncion(TipoFuncion tipoFuncion) {
		this.tipoFuncion = tipoFuncion;
	}
	public ArrayList<Asistencia> getAsistenciasDeFuncion() {
		return asistenciasDeFuncion;
	}
	public void setAsistenciasDeFuncion(ArrayList<Asistencia> asistenciasDeFuncion) {
		this.asistenciasDeFuncion = asistenciasDeFuncion;
	}
	public String getIdExterno() {
		return idExterno;
	}
	public void setIdExterno(String idExterno) {
		this.idExterno = idExterno;
	}
	public Mascara getMascara() {
		return mascara;
	}
	public void cambiaMascara(Mascara mascara) {
		this.cuposCargados = this.mascara != null && mascara.getId() == this.mascara.getId();
		this.mascara = mascara;
	}
	
	public String getPriceCardDescription() {
		return priceCardDescription;
	}
	public void setPriceCardDescription(String priceCardDescription) {
		this.priceCardDescription = priceCardDescription;
	}
	public boolean esTrasnoche() {
		return !DateUtils.esMismoDia(this.fecha, this.fechaContable);
	}
	public boolean isProtegido() {
		return protegido;
	}
	public void setProtegido(boolean protegido) {
		this.protegido = protegido;
	}
	public boolean esNumerada() {
		return esNumerada;
	}
	public void setEsNumerada(boolean esNumerada) {
		this.esNumerada = esNumerada;
	}
	
	public boolean tieneCuposCargados() {
		return cuposCargados;
	}
	public void setCuposCargados(boolean cuposCargados) {
		this.cuposCargados = cuposCargados;
	}

	public Integer getExhibicion() {
		return exhibicion;
	}
	public void setExhibicion(Integer exhibicion) {
		this.exhibicion = exhibicion;
	}
	public Dia getDia() {
		return dia;
	}
	public void setDia(Dia dia) {
		this.dia = dia;
	}
	public Hora getHora() {
		return hora;
	}
	public void setHora(Hora hora) {
		this.hora = hora;
	}
	public Integer getSemana() {
		return semana;
	}
	public void setSemana(Integer semana) {
		this.semana = semana;
	}
	public Integer getAsistenciaProyectada() {
		return asistenciaProyectada;
	}
	public void setAsistenciaProyectada(Integer asistenciaProyectada) {
		this.asistenciaProyectada = asistenciaProyectada;
	}
	public Double getPorcentajeOcupacionProyectado() {
		return porcentajeOcupacionProyectado;
	}
	public void setPorcentajeOcupacionProyectado(
			Double porcentajeOcupacionProyectado) {
		this.porcentajeOcupacionProyectado = porcentajeOcupacionProyectado;
	}
	public boolean isAnalizada() {
		return analizada;
	}
	public void setAnalizada(boolean analizada) {
		this.analizada = analizada;
	}
	public boolean isBloqueada() {
		return bloqueada;
	}
	public void setBloqueada(boolean bloqueada) {
		this.bloqueada = bloqueada;
	}
 }
