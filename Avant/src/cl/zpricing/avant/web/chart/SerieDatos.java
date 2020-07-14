/**
 * 
 */
package cl.zpricing.avant.web.chart;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cl.zpricing.avant.model.Marker;

/**
 * <b>Descripci�n de la Clase</b>
 * Clase que define una serie de datos para un grafico
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 06-01-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class SerieDatos implements Elemento {
	private List<String> info;
	private List<Marker> markers; 
	private List<Valor> valores;
	private String nombreSerie;
	private int ejeY=1;
	private String prefijo;
	private boolean existeEjeSecundario=false;
	private String color;
	private GregorianCalendar fecha;
	private Double pesoPel;
	private List<Double> pesosDias;
	private boolean serieResultado=false;
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.web.chart.Elemento#crearElemento(org.w3c.dom.Document)
	 */
	@Override
	public Element crearElemento(Document documento) {
		//create the root element <dataset>
		Element raizEle = documento.createElement("dataset");
		
		//Aqu� se agregan los parametros para las series de tiempo
		if(nombreSerie!= null)
			raizEle.setAttribute("seriesName", nombreSerie);
		else
			raizEle.setAttribute("seriesName", "Serie");
		
		if(existeEjeSecundario){
			if(ejeY == 1)
				raizEle.setAttribute("parentYAxis", "P");
			else
				raizEle.setAttribute("parentYAxis", "S");
		}
		
		if(color!=null)
			raizEle.setAttribute("color",color);
		
		if(prefijo!=null)
			raizEle.setAttribute("numberPrefix",prefijo);
		
		if(valores!=null){
			Iterator<Valor> it;
			if(this.serieResultado){
				it = valores.iterator();
			}else{
				it = this.getValoresPonderados().iterator();
			}
			while(it.hasNext()) {
				Valor b = it.next();
				Element ele = b.crearElemento(documento);
				raizEle.appendChild(ele);
			}
		}
		return raizEle;
	}
	
	/**
	 * @return the datos
	 */
	public List<Valor> getValores() {
		return valores;
	}

	/**
	 * @param datos the datos to set
	 */
	public void setValores(List<Valor> valores) {
		this.valores = valores;
	}
	
	/**
	 * @param nombreSerie the nombreSerie to set
	 */
	public void setNombreSerie(String nombreSerie) {
		this.nombreSerie = nombreSerie;
	}
	
	/**
	 * @return the nombreSerie
	 */
	public String getNombreSerie() {
		return nombreSerie;
	}

	/**
	 * @param ejeY the ejeY to set
	 */
	public void setEjeY(int ejeY) {
		this.ejeY = ejeY;
	}

	/**
	 * @param prefijo the prefijo to set
	 */
	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}

	/**
	 * @param existeEjeSecundario the existeEjeSecundario to set
	 */
	public void setExisteEjeSecundario() {
		this.existeEjeSecundario = true;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @return the fecha
	 */
	public GregorianCalendar getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(GregorianCalendar fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the pesoPel
	 */
	public Double getPesoPel() {
		return pesoPel;
	}

	/**
	 * @param pesoPel the pesoPel to set
	 */
	public void setPesoPel(Double pesoPel) {
		this.pesoPel = pesoPel;
	}

	/**
	 * @return the pesosDias
	 */
	public List<Double> getPesosDias() {
		return pesosDias;
	}

	/**
	 * @param pesosDias the pesosDias to set
	 */
	public void setPesosDias(List<Double> pesosDias) {
		this.pesosDias = pesosDias;
	}
	
	/**
	 * Sirve para generar los valores ponderadoros de una serie
	 * @return 
	 */
	public List<Valor> getValoresPonderados(){
		//si existen valores
		if(valores!=null){
			List<Valor> salida = new ArrayList<Valor>();
			Iterator<Valor> itVal = this.valores.iterator();
			//si no es serie resultado debe ponderar
			if(!this.serieResultado){
				//pondera solo si tiene ponderadores no nulos
				if(pesosDias!=null){
					Iterator<Double> itDias = this.pesosDias.iterator();
					//hay que ir dia por dia ponderando
					while(itVal.hasNext() && itDias.hasNext()){
						Valor val = itVal.next();
						Double pDia = itDias.next();
						
						//double peso = pDia*(this.pesoPel);
						double peso = pDia;
						int valor = (int)(val.getValor()*peso);
						
						Valor vPonderado = new Valor(valor);
						vPonderado.setMarkers(val.getMarkers());
						
						salida.add(vPonderado);
					}
					return salida;
				}
			}
			//si es una serie resultado o no tiene ponderadores nulos entregamos los valores originales
			while(itVal.hasNext()){
				Valor val = itVal.next();
				salida.add(val);
			}
			return salida;
		}
		return null;
		
	}
	
	/**
	 * Suma los valores ponderados de un grafico (si hay mas de una serie de resultado, la
	 * lista de valores final sera como si hubiesen sido una
	 * @param seriesDatos la lista de series de datos en la cual se planea hacer la ponderacion
	 * @return lista de valores 
	 */
	public static List<Valor> calcularResultado(List<SerieDatos> seriesDatos){
		Logger log = (Logger) Logger.getLogger("cl.zpricing.revman.web.prediction.PrediccionIncompletaController");
		
		log.debug("calcularResultado(). Recorriendo series.");
		SerieDatos serieDat;
		
		Iterator<SerieDatos> itDatos = seriesDatos.iterator();
		
		List<Double> valoresAnt = null; 
		List<Double> valoresAct = null;
		
		//Recorremos las series
		while(itDatos.hasNext()){
			serieDat = itDatos.next();

//			log.debug("Esta serie: " + serieDat.getNombreSerie());
			//Si no es serie de datos debe sumar su valor
			if(!serieDat.serieResultado){
//				log.debug(" > No es serie de resultado, así que sumamos su valor.");
				
				Iterator<Valor> itValor = serieDat.valores.iterator();
				Valor valSerie;
				
				Iterator<Double> itDias = serieDat.pesosDias.iterator();
				Double pDia;
				
				double pPel = serieDat.pesoPel.doubleValue();
//				log.debug(" > Peso de la película: "+ pPel);
				
				valoresAct = new ArrayList<Double>();
				
				if(valoresAnt!=null){
					Iterator<Double> itValorAnt = valoresAnt.iterator();
					Double valAnt;
					
					while(itValor.hasNext() && itDias.hasNext() && itValorAnt.hasNext()){
						valSerie = itValor.next();
						valAnt = itValorAnt.next();
						pDia = itDias.next();
						double pesoDia = pDia.doubleValue();
						double valorSerie = (double)valSerie.getValor();
						double valorAnt = valAnt.doubleValue();
						valoresAct.add(new Double(valorAnt+valorSerie*pPel*pesoDia));
					}
				}else{
					while(itValor.hasNext() && itDias.hasNext()){
						valSerie = itValor.next();
						pDia = itDias.next();
						double pesoDia = pDia.doubleValue();
						double valorSerie = (double)valSerie.getValor();
						valoresAct.add(new Double(valorSerie*pPel*pesoDia));
					}
				}
				valoresAnt = valoresAct;
			}
		}
//		log.debug("Valor: " + valoresAct);
		if(valoresAct!=null){
			List<Valor> salida = new ArrayList<Valor>();
			Iterator<Double> itValor = valoresAct.iterator();
			Double valorAct;
	
			while(itValor.hasNext()){
				valorAct = itValor.next();
				salida.add(new Valor(valorAct.longValue()));
			}
			return salida;
		}else
			return null;
	}
	
	/**
	 * Setea si una serie es resultado
	 */
	public void setSerieResultado(){
		this.serieResultado = true;
	}
	
	/**
	 * @return si una serie es resultado
	 */
	public boolean esSerieResultado(){
		return this.serieResultado;
	}

	/**
	 * @return the info
	 */
	public List<String> getInfo() {
		return info;
	}

	/**
	 * @param info the info to set
	 */
	public void setInfo(List<String> info) {
		this.info = info;
	}

	/**
	 * @return the markers
	 */
	public List<Marker> getMarkers() {
		return markers;
	}

	/**
	 * @param markers the markers to set
	 */
	public void setMarkers(List<Marker> markers) {
		this.markers = markers;
	}
	
}
