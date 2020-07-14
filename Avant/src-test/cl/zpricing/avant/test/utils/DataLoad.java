package cl.zpricing.avant.test.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.Mascara;
import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.model.Sala;
import cl.zpricing.commons.utils.DateUtils;

public class DataLoad extends SqlMapClientDaoSupport {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private final int distribuidorId = 3;
	private final int formatoId = 1;
	private final int areaSuperEconomico = 36;
	private final int areaEconomico = 37;
	private final int areaLight = 38;
	private final int areaAdulto = 39;
	private final int areaUpselling = 40;
	
	private final int pelFormatoNormal = 1;
	private final int pelFormato3D = 2;
	private final int pelFormato4DX = 4;
	
	private final int idiomaId = 1;
	
	public void cleanTables() {
		getSqlMapClientTemplate().delete("cleanTables");
	}
	
	public int crearPriceCard(String code, String descripcion, int complejoId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("price_card_code", code);
		params.put("descripcion", descripcion);
		params.put("complejo_id", complejoId);
		int id = (Integer) getSqlMapClientTemplate().insert("insertPriceCard", params);
		return id;
	}
	
	public int crearMascaraLastMinute(int complejoId) {
		return this.crearMascara("LM", complejoId);
	}
	
	public int crearMascara(String descripcion, int complejoId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("descripcion", descripcion);
		params.put("complejo_id", complejoId);
		int id = (Integer) getSqlMapClientTemplate().insert("insertMascara", params);
		return id;
	}
	
	public int crearPelicula35mm(String nombre, String codigo, int grupoPeliculaId, int complejoId, int rankingId) {
		return this.crearPelicula(nombre, codigo, grupoPeliculaId, complejoId, this.pelFormatoNormal, rankingId);
	}
	public int crearPelicula3D(String nombre, String codigo, int grupoPeliculaId, int complejoId, int rankingId) {
		return this.crearPelicula(nombre, codigo, grupoPeliculaId, complejoId, this.pelFormato3D, rankingId);
	}
	public int crearPelicula4DX(String nombre, String codigo, int grupoPeliculaId, int complejoId, int rankingId) {
		return this.crearPelicula(nombre, codigo, grupoPeliculaId, complejoId, this.pelFormato4DX, rankingId);
	}
	
	private int crearPelicula(String nombre, String codigo, int grupoPeliculaId, int complejoId, int pelFormatoId, int rankingId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nombre", nombre);
		params.put("formato_id", this.formatoId);
		params.put("pel_formato_id", pelFormatoId);
		params.put("ranking_id", rankingId);
		params.put("distribuidor_id", this.distribuidorId);
		params.put("grupo_pelicula_id", grupoPeliculaId);
		params.put("idioma_id", this.idiomaId);
		int id = (Integer) getSqlMapClientTemplate().insert("insertPelicula", params);
		
		Map<String, Object> paramsPeliculaComplejo = new HashMap<String, Object>();
		paramsPeliculaComplejo.put("pelicula_id", id);
		paramsPeliculaComplejo.put("complejo_id", complejoId);
		paramsPeliculaComplejo.put("codigo_externo", codigo);
		getSqlMapClientTemplate().insert("insertPeliculaComplejo", paramsPeliculaComplejo);
		
		return id;
	}
	
	public int crearFuncion(int complejoId, int salaId, Date fecha, String hora, int peliculaId, int priceCardId, int mascaraId, String funcionIdExterno) throws ParseException {
		Map<String, Object> paramsFuncion = new HashMap<String, Object>();
		paramsFuncion.put("sala_id", salaId);
		paramsFuncion.put("complejo_id", complejoId);
		paramsFuncion.put("pelicula_id", peliculaId);
		String time = DateUtils.format_yyyyMMdd.format(fecha) + " " + hora;
		paramsFuncion.put("fecha", DateUtils.format_yyyyMMdd_HHmmss.parse(time));
		paramsFuncion.put("fecha_contable", fecha);
		paramsFuncion.put("price_card_id", priceCardId);
		paramsFuncion.put("mascara_id", mascaraId);
		paramsFuncion.put("funcion_id_externo", funcionIdExterno);
		paramsFuncion.put("porcentaje_ocupacion_proyectado", 15.0);
		
		int id = (Integer) getSqlMapClientTemplate().insert("insertFuncionConDatosDeCine", paramsFuncion);
		
		return id;
	}
	
	public int crearFuncion(int complejoId, int salaId, Date fecha, String hora, int peliculaId) throws ParseException {
		Map<String, Object> paramsFuncion = new HashMap<String, Object>();
		paramsFuncion.put("sala_id", salaId);
		paramsFuncion.put("complejo_id", complejoId);
		paramsFuncion.put("pelicula_id", peliculaId);
		String time = DateUtils.format_yyyyMMdd.format(fecha) + " " + hora;
		paramsFuncion.put("fecha", DateUtils.format_yyyyMMdd_HHmmss.parse(time));
		paramsFuncion.put("fecha_contable", fecha);
		int id = (Integer) getSqlMapClientTemplate().insert("insertFuncion", paramsFuncion);
		
		return id;
	}
	
	public int crearTicketAdulto(String descripcion, String ticketTypeCode, int complejoId) {
		return this.crearTicket(descripcion, ticketTypeCode, this.areaAdulto, complejoId);
	}
	public int crearTicketLight(String descripcion, String ticketTypeCode, int complejoId) {
		return this.crearTicket(descripcion, ticketTypeCode, this.areaLight, complejoId);
	}
	public int crearTicketEconomico(String descripcion, String ticketTypeCode, int complejoId) {
		return this.crearTicket(descripcion, ticketTypeCode, this.areaEconomico, complejoId);
	}
	public int crearTicketSuperEconomico(String descripcion, String ticketTypeCode, int complejoId) {
		return this.crearTicket(descripcion, ticketTypeCode, this.areaSuperEconomico, complejoId);
	}
	public int crearTicketUpSelling(String descripcion, String ticketTypeCode, int complejoId) {
		return this.crearTicket(descripcion, ticketTypeCode, this.areaUpselling, complejoId);
	}

	private int crearTicket(String descripcion, String ticketTypeCode, int areaId, int complejoId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("descripcion", descripcion);
		params.put("area_id", areaId);
		int ticketId = (Integer) getSqlMapClientTemplate().insert("insertarTicket", params);
		this.crearTicketComplejo(descripcion, ticketTypeCode, ticketId, complejoId);
		return ticketId;
	}
	
	private void crearTicketComplejo(String descripcion, String ticketTypeCode, int ticketId, int complejoId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("descripcion", descripcion);
		params.put("ticket_id_externo", ticketTypeCode);
		params.put("complejo_id", complejoId);
		params.put("ticket_id", ticketId);
		getSqlMapClientTemplate().insert("insertarTicketComplejo", params);
	}
	
	public void asociarTicketPriceCard(int priceCardId, int ticketId, BigDecimal price, BigDecimal bfee) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("price_card_id", priceCardId);
		params.put("ticket_id", ticketId);
		params.put("price", price);
		params.put("bfee", bfee);
		getSqlMapClientTemplate().insert("insertarPriceCardTicket", params);
	}
	
	public void asociarTicketMascara(int ticketId, int mascaraId, int capacidad, int diasAntesExpira, int salaId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ticket_id", ticketId);
		params.put("sala_id", salaId);
		params.put("mascara_id", mascaraId);
		params.put("capacidad", capacidad);
		params.put("dias_antes_expira", diasAntesExpira);
		getSqlMapClientTemplate().insert("insertarAreaGrupoMascara", params);
	}
	
	public void asociarFuncionSecondSelling(int funcionId, int secondSellingFuncionId, int orden) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("funcion_id", funcionId);
		params.put("second_selling_funcion_id", secondSellingFuncionId);
		params.put("second_selling_orden", orden);
		
		getSqlMapClientTemplate().insert("asociarFuncionSecondSelling", params);
	}
	
	public void asociarFuncionSecondSellingPersonalized(int funcionId, int secondSellingFuncionId, String clienteId, int orden) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("funcion_id", funcionId);
		params.put("second_selling_funcion_id", secondSellingFuncionId);
		params.put("cliente_id", clienteId);
		params.put("second_selling_orden", orden);
		
		getSqlMapClientTemplate().insert("asociarFuncionSecondSellingPersonalized", params);
	}
	
	public int insertarZonaGeografica(String nombre) {
		Random random = new Random();
		int zonaGeograficaId = random.nextInt();
		zonaGeograficaId = zonaGeograficaId < 0 ? zonaGeograficaId * -1 : zonaGeograficaId;
		this.insertarZonaGeografica(zonaGeograficaId, nombre);
		return zonaGeograficaId;
	}
	
	public void insertarZonaGeografica(int id, String nombre) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("nombre", nombre);
		getSqlMapClientTemplate().insert("insertarZonaGeografica", params);
	}
	
	public int insertarComplejo(String nombre, String idExterno, int zonaGeograficaId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nombre", nombre);
		params.put("zona_geografica_id", zonaGeograficaId);
		params.put("id_externo", idExterno);
		int id = (Integer) getSqlMapClientTemplate().insert("insertarComplejo", params);
		return id;
	}
	
	public int insertarGrupoDeSala(int complejoId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("complejo_id", complejoId);
		int id = (Integer) getSqlMapClientTemplate().insert("insertarGrupoSala", params);
		return id;
	}
	
	public int insertarSala(int grupoSalaId, int complejoId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("complejo_id", complejoId);
		params.put("grupo_sala_id", grupoSalaId);
		int id = (Integer) getSqlMapClientTemplate().insert("insertarSala", params);
		return id;
	}
	
	public int insertarGrupoPelicula(String nombre) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nombre", nombre);
		int id = (Integer) getSqlMapClientTemplate().insert("insertarGrupoPelicula", params);
		return id;
	}
	
	public int insertarComuna(String nombre, int zonaGeograficaId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nombre", nombre);
		params.put("zona_geografica_id", zonaGeograficaId);
		int id = (Integer) getSqlMapClientTemplate().insert("insertarComuna", params);
		return id;
	}
	
	public int insertarCliente(String rut, String email, String nombre, int comunaId ) {
		int clienteId = this.insertarCliente(rut, email, nombre);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cliente_id", clienteId);
		params.put("comuna_id", comunaId);
		getSqlMapClientTemplate().insert("actualizarClienteComuna", params);
		return clienteId;
	}
	
	public int insertarCliente(String rut, String email, String nombre ) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("rut", rut);
		params.put("email", email);
		params.put("nombre", nombre);
		int id = (Integer) getSqlMapClientTemplate().insert("insertarCliente", params);
		return id;
	}
	
	public void insertarTransaccionTemporalClienteCineticket(String rut, String email, String nombre, String comuna, String codigoExternoComplejo, Date fechaCompra) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("rut", rut);
		params.put("email", email);
		params.put("nombre", nombre);
		params.put("comuna", comuna);
		params.put("codigo_cine", codigoExternoComplejo);
		params.put("fecha_compra", fechaCompra);
		getSqlMapClientTemplate().insert("insertarTransaccionTemporalClienteCineticket", params);
	}
	
	public List<Map<String, Object>> obtenerClienteZonaGeografica() {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("obtenerClienteZonaGeografica");
	}
	
	public void populateClusters() {
		getSqlMapClientTemplate().insert("populateClusters");
		getSqlMapClientTemplate().insert("testPopulateClusterClients");
		getSqlMapClientTemplate().insert("testPopulateClusterMovies");
	}

	public void asociarCineCliente(int clienteId, int complejoId, double factorPreferencia) {
		log.debug("asociarCineCliente");
		log.debug("  clienteId : " + clienteId);
		log.debug("  complejoId : " + complejoId);
		log.debug("  factorPreferencia : " + factorPreferencia);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cliente_id", clienteId);
		params.put("complejo_id", complejoId);
		params.put("factor_preferencia", factorPreferencia);
		getSqlMapClientTemplate().insert("asociarClienteComplejo", params);
	}
	
	public void asociarClientePelicula(int clienteId, int peliculaId) {
		log.debug("asociarClientePelicula");
		log.debug("  clienteId : " + clienteId);
		log.debug("  peliculaId : " + peliculaId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cliente_id", clienteId);
		params.put("pelicula_id", peliculaId);
		getSqlMapClientTemplate().insert("asociarClientePeliculaVista", params);
	}
	
	public int crearCategoria(String nombre) {
		return this.crearCategoria(nombre, 0.0);
	}
	
	public int crearCategoria(String nombre, double attendanceWeight) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nombre", nombre);
		params.put("attendance_weight", attendanceWeight);
		int id = (Integer) getSqlMapClientTemplate().insert("insertarCategoria", params);
		return id;
	}
	
	public void asociarClienteCategoria(int clienteId, int categoriaId, int orden) {
		log.debug("asociarClienteCategoria");
		log.debug("  clienteId : " + clienteId);
		log.debug("  categoriaId : " + categoriaId);
		log.debug("  orden : " + orden);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cliente_id", clienteId);
		params.put("categoria_id", categoriaId);
		params.put("orden", orden);
		getSqlMapClientTemplate().insert("asociarClienteCategoria", params);
	}
	
	public void asociarGrupoPeliculaCategoria(int grupoPeliculaId, int categoriaId) {
		log.debug("asociarClienteCategoria");
		log.debug("  grupoPeliculaId : " + grupoPeliculaId);
		log.debug("  categoriaId : " + categoriaId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("grupo_pelicula_id", grupoPeliculaId);
		params.put("categoria_id", categoriaId);
		getSqlMapClientTemplate().insert("asociarGrupoPeliculaCategoria", params);
	}
	
	public void asociarSimilitudCategorias(int categoria_1, int categoria_2, double similitud) {
		log.debug("asociarClienteCategoria");
		log.debug("  categoria_1 : " + categoria_1);
		log.debug("  categoria_2 : " + categoria_2);
		log.debug("  similitud : " + similitud);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("categoria_1", categoria_1);
		params.put("categoria_2", categoria_2);
		params.put("similitud", similitud);
		getSqlMapClientTemplate().insert("asociarSimilitudCategorias", params);
	}
	
	public void crearSemanaCine(Date fechaDesde, Date fechaHasta) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fecha_desde", fechaDesde);
		params.put("fecha_hasta", fechaHasta);
		getSqlMapClientTemplate().insert("crearSemanaCine", params);
	}
}
