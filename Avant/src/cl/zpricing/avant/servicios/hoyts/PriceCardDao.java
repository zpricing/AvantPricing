package cl.zpricing.avant.servicios.hoyts;

import java.util.List;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Distribuidor;
import cl.zpricing.avant.model.loadmanager.PriceCard;

public interface PriceCardDao {
	public List<PriceCard> obtenerPriceCards(Complejo complejo, String mascara, String timeSpan);
	public List<PriceCard> obtenerPriceCardsConExcepciones(Complejo complejo, String mascara, String timeSpan, Distribuidor distribuidor);
	public List<PriceCard> obtenerPriceCards(Complejo complejo, String priceCardName);
}
