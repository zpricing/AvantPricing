package cl.zpricing.avant.servicios.hoyts.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Distribuidor;
import cl.zpricing.avant.model.loadmanager.PriceCard;
import cl.zpricing.avant.servicios.hoyts.PriceCardDao;

public class PriceCardDaoImpl extends SqlMapClientDaoSupport implements PriceCardDao {
	protected Logger log = Logger.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceCard> obtenerPriceCards(Complejo complejo, String mascara, String timeSpan) {
		log.debug("Mascara  : [" + mascara + "]");
		log.debug("TimeSpan : [" + timeSpan + "]");
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("mascara", mascara);
		mapa.put("timeSpan", timeSpan);
		mapa.put("fecha", new Date());
		mapa.put("complejo", complejo.getId());
		return getSqlMapClientTemplate().queryForList("obtenerPriceCards", mapa);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceCard> obtenerPriceCardsConExcepciones(Complejo complejo, String mascara, String timeSpan, Distribuidor distribuidor) {
		log.debug("Mascara  : [" + mascara + "]");
		log.debug("TimeSpan : [" + timeSpan + "]");
		log.debug("distribuidor : [" + distribuidor.getNombre() + "]");
		
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("mascara", mascara);
		mapa.put("timeSpan", timeSpan);
		mapa.put("fecha", new Date());
		mapa.put("complejo", complejo.getId());
		mapa.put("distribuidor", distribuidor.getId());
		return getSqlMapClientTemplate().queryForList("obtenerPriceCardsConExcepciones", mapa);
	}

	@Override
	public List<PriceCard> obtenerPriceCards(Complejo complejo, String priceCardName) {
		log.debug("PriceCardName  : [" + priceCardName + "]");
		log.debug("Complejo  : [" + complejo.getId() + "]");
		
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("price_card", priceCardName);
		mapa.put("complejo", complejo.getId());
		mapa.put("fecha", new Date());
		return getSqlMapClientTemplate().queryForList("obtenerPriceCardsPorNombre", mapa);
	}

}
